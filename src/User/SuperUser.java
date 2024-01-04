package User;

import Books.Book;
import Controller.Controller;
import Static.Static;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author NickMo
 * @date 2023-12-06 23:47
 */
public class SuperUser extends User {
    public SuperUser() {
        super();
    }

    public SuperUser(Static.Permissions permissions) {
        super(permissions);
    }

    public SuperUser(String uuid, String username, Static.Permissions permissions) {
        super(uuid, username, permissions);
    }

    public SuperUser(String uuid, String phone, String username, Static.Permissions permissions) {
        super(uuid, phone, username, permissions);
    }

    public SuperUser(String username, String phone, int permissions) {
        super(username, phone, permissions);
    }

    public boolean addUser(Administrator administrator) {
        return Controller.getInstance().getDatabaseUtil_Obj().addRecord(administrator);
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
