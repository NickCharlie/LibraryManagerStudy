package Static;

import java.lang.reflect.Method;

/**
 * @author NickMo
 * @date 2023-12-01 22:28
 * @description 该类为全局单例的类，在整个程序中只有一个实例。该类的作用是存储一些静态的东西，方便随时调用。
 */

public class Static {

    /**
     * 单例模式的实现
     * */
    private final static Static INSTANCE = new Static();

    private Static(){

    }

    public static Static getInstance() {
        return INSTANCE;
    }

    //数据库查询对象
    public static Database.DatabaseQuery databaseQuery = Database.DatabaseQuery.getInstance();

    public static final String DATABASE_USER_TABLE = "tb_User";
    public static final String DATABASE_USER_COLUMN_ID = "id";
    public static final String DATABASE_USER_COLUMN_NAME = "username";

    public static final String DATABASE_BOOKS_TABLE = "tb_Book";
    public static final String DATABASE_BOOKS_NUM = "num";

    // 根据属性名查找对应的getter方法
    public static Method findGetterMethod(Class<?> clazz, String fieldName) throws NoSuchMethodException {
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return clazz.getMethod(getterName);
    }


    public enum Permissions {
        SUPERUSER,
        ADMINISTRATOR,
        GUEST
    }
}
