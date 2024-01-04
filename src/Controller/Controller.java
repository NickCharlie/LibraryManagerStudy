package Controller;

import Books.Book;
import Database.Database;
import Database.DatabaseQuery;
import User.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author NickMo
 * @date 2023-12-01 23:12
 * @description 中控台类，详细注释以后写
 */
public class Controller {

    private final static Controller INSTANCE = new Controller();
    private final static Database dataBase_Obj = Database.getInstance();

    private Controller() {

    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public void addBooks(CopyOnWriteArrayList<Book> books) {

    }

    public DatabaseUtil getDatabaseUtil_Obj() {
        return databaseUtil_Obj;
    }

    public final DatabaseUtil databaseUtil_Obj = new DatabaseUtil();
    /**
     * @description 对数据库类的具体实现进行再一次封装，用户类调用该类进行数据库操作
     * */
    public static class DatabaseUtil {

        public final DatabaseQuery databaseQuery_Obj = DatabaseQuery.getInstance();

        public List<String> getAllTables() {
            return dataBase_Obj.getAllTables();
        }

        public List<String> getAllColumns(String tableName) {
            return dataBase_Obj.getAllColumns(tableName);
        }

        public Boolean addTable(String tableName) {
            return dataBase_Obj.addTable(tableName);
        }

        public Boolean delTable(String tableName) {
            return dataBase_Obj.delTable(tableName);
        }

        public Boolean addColumn(String tableName,List<String> Columns) {
            return dataBase_Obj.addColumn(tableName, Columns);
        }

        public Boolean deleteColumn(String tableName,List<String> Columns) {
            return dataBase_Obj.deleteColumn(tableName, Columns);
        }

        public <T> boolean addField(String tableName, String Column, T Field) {
            return dataBase_Obj.addField(tableName, Column, Field);
        }

        public <T> boolean addRecord(T ClassName) {
            return dataBase_Obj.addRecord(ClassName);
        }

        public boolean delRecord(String TableName,String Column,String Field) {
            return dataBase_Obj.delRecord(TableName, Column, Field);
        }

        public <T> boolean modifyRecord(String Table , String Column, T Field , T NewField, Integer... ID) {
            return dataBase_Obj.modifyRecord(Table, Column, Field, NewField, ID);
        }

        public <T> List<T> getRow(String TableName,String Column, Class<T> clazz) {
            return dataBase_Obj.getRow(TableName, Column, clazz);
        }

        public List<User> getAllUsers() {
            return databaseQuery_Obj.getAllUsers();
        }

        public List<User> queryUser(String Column, String Field, boolean isFuzzySearch) {
            return databaseQuery_Obj.queryUser(Column, Field, isFuzzySearch);
        }

        public <T> T getSingleRecord(String Table, String column, int targetId) {
            return dataBase_Obj.getSingleRecord(Table, column, targetId);
        }

        public <T> T getSingleRecord(String Table, String column, String uuid) {
            return dataBase_Obj.getSingleRecord(Table, column, uuid);
        }

        public <T> Map<String, T> getLineRecord(String Table, int targetId) {
            return dataBase_Obj.getLineRecord(Table, targetId);
        }

        public <T> Map<String, T> getLineRecord(String Table,  String uuid) {
            return dataBase_Obj.getLineRecord(Table, uuid);
        }

        public boolean delLineRecord(String Table, String uuid) {
            return dataBase_Obj.delLineRecord(Table, uuid);
        }
    }

}
