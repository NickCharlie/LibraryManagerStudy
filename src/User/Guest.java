package User;

import Books.Book;
import Controller.Controller;
import Static.Static;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author NickMo
 * @date 2023-12-02 19:11
 * @description 访客类，包含借书，还书操作
 */
public class Guest extends User {

    public Guest() {
        super();
    }

    public Guest(Static.Permissions permissions) {
        super(permissions);
    }

    public Guest(String uuid, String username, Static.Permissions permissions) {
        super(uuid, username, permissions);
    }

    public Guest(String uuid, String phone, String username, Static.Permissions permissions) {
        super(uuid, phone, username, permissions);
    }

    public Guest(String username, String phone, int permissions) {
        super(username, phone, permissions);
    }

    private Map<Book, Boolean> borrowBooks(CopyOnWriteArrayList<Book> books) {

        Map<Book, Boolean> flag = new HashMap<>();

        if (books.size() > 3) {
            System.out.println("无法借阅这么多的书! 每个用户借书的上限是3本，请放回多拿的书!");
            return new HashMap<>();
        }

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
