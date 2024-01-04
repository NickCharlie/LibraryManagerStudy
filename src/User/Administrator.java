package User;

import Books.Book;
import Controller.Controller;
import Static.Static;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Administrator extends User {
    public Administrator() {
        super();
    }

    public Administrator(Static.Permissions permissions) {
        super(permissions);
    }

    public Administrator(String uuid, String username, Static.Permissions permissions) {
        super(uuid, username, permissions);
    }

    public Administrator(String uuid, String phone, String username, Static.Permissions permissions) {
        super(uuid, phone, username, permissions);
    }

    public Administrator(String username, String phone, int permissions) {
        super(username, phone, permissions);
    }

    public boolean addUser(Guest guest) {
        return Controller.getInstance().getDatabaseUtil_Obj().addRecord(guest);
    }

    public boolean delUser(String Field) {

        boolean flag = false;
        List<String> columns = Controller.getInstance().getDatabaseUtil_Obj().getAllColumns(Static.DATABASE_USER_TABLE);

        for (String column : columns) {

            List<User> users = Controller.getInstance().getDatabaseUtil_Obj().queryUser(column, Field, false);

            if (users.isEmpty()) {
                continue;
            }

            for (User user : users) {
                flag = true;
                Controller.getInstance().getDatabaseUtil_Obj().delRecord(Static.DATABASE_USER_TABLE, column, user.getUuid());
            }
        }
        return flag;
    }

    public <T> boolean updateUser(Guest guest) {
        List<User> users = Controller.getInstance().getDatabaseUtil_Obj().getAllUsers();

        Controller.DatabaseUtil databaseUtil = Controller.getInstance().getDatabaseUtil_Obj();

        for (User user : users) {
            if (user.Permissions == Static.Permissions.SUPERUSER
                    || user.Permissions == Static.Permissions.ADMINISTRATOR) {
                continue;
            }
            List<String> Columns = databaseUtil.getAllColumns(Static.DATABASE_USER_TABLE);

            boolean flag = false;
            Map<String, T> tmp = new HashMap<>();
            List<String> keys = new ArrayList<>();
            tmp.put("uuid", (T) guest.getUuid());
            tmp.put("username", (T) guest.getUsername());
            tmp.put("phone", (T) guest.getPhone());
            tmp.put("Permissions", (T)guest.getPermissions());

            for (String column : Columns) {

                for (Map.Entry<String, T> entry : tmp.entrySet()) {
                    keys.add(entry.getKey());
                }
                if (listsEqual(Columns, keys)) {
                    // TODO: 2023-12-08 懵逼了，不会写了，更新用户数据
                }
            }
        }
        return false;
    }

    private static boolean listsEqual(List<String> list1, List<String> list2) {
        // 将List转换为数组，然后使用Arrays.equals比较数组内容
        return Arrays.equals(list1.toArray(), list2.toArray());
    }

    public boolean addBooks(CopyOnWriteArrayList<Book> books) {
        for (Book book : books) {
            Controller.getInstance().getDatabaseUtil_Obj().addRecord(book);
        }
        return true;
    }

    public Map<Book, Boolean> delBooks(CopyOnWriteArrayList<Book> books) {

        List<String> Tables = Controller.getInstance().getDatabaseUtil_Obj().getAllTables();

        String bookTable = null;
        List<String> columns = null;
        Map<Book, Boolean> resultMap = new HashMap<>();

        for (String t : Tables) {
            if (!t.equals(Static.DATABASE_BOOKS_TABLE)) {
                continue;
            }
            bookTable = t;
        }

        for (Book book : books) {
            if (!Controller.getInstance().getDatabaseUtil_Obj().delLineRecord(bookTable, book.getUuid())) {
                resultMap.put(book, false);
                continue;
            }
            resultMap.put(book, true);
        }

        return resultMap;
    }

    public Map<Book, Boolean> decBooks(CopyOnWriteArrayList<Book> books) {

        Controller.DatabaseUtil databaseUtil = Controller.getInstance().getDatabaseUtil_Obj();
        Map<Book, Boolean> flag = new HashMap<>();

        for (Book book : books) {
            int bookNum = databaseUtil.getSingleRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, book.getUuid());
            flag.put(book, databaseUtil.modifyRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, bookNum, bookNum - 1, 0));
        }

        return flag;
    }

    public Map<Book, Boolean> incBooks(CopyOnWriteArrayList<Book> books) {

        Controller.DatabaseUtil databaseUtil = Controller.getInstance().getDatabaseUtil_Obj();
        Map<Book, Boolean> flag = new HashMap<>();

        for (Book book : books) {
            int bookNum = databaseUtil.getSingleRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, book.getUuid());
            flag.put(book, databaseUtil.modifyRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, bookNum, bookNum + 1, 0));
        }

        return flag;
    }

    private Map<Book, Boolean> borrowBooks(CopyOnWriteArrayList<Book> books) {

        Map<Book, Boolean> flag = new HashMap<>();

        for (Book book : books) {

            Controller.DatabaseUtil databaseUtil = Controller.getInstance().getDatabaseUtil_Obj();

            int bookNum = databaseUtil.getSingleRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, book.getUuid());
            flag.put(book, databaseUtil.modifyRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, bookNum, bookNum - 1, 0));
        }
        return flag;
    }

    private Map<Book, Boolean> returnBooks(CopyOnWriteArrayList<Book> books) {

        Map<Book, Boolean> flag = new HashMap<>();

        for (Book book : books) {

            Controller.DatabaseUtil databaseUtil = Controller.getInstance().getDatabaseUtil_Obj();

            int bookNum = databaseUtil.getSingleRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, book.getUuid());
            flag.put(book, databaseUtil.modifyRecord(Static.DATABASE_BOOKS_TABLE, Static.DATABASE_BOOKS_NUM, bookNum, bookNum + 1, 0));
        }
        return flag;
    }

    private <T> boolean returnBooks(T[] unknownFieldList) {
        for (T t : unknownFieldList) {
            // TODO: 2023-12-05 通知书籍增加对应书籍的数量
        }
        return true;
    }

}
