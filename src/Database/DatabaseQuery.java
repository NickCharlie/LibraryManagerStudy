package Database;

import Books.Book;
import Static.Static;
import User.User;
import Util.LogUtils;
import Util.Table;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;

public class DatabaseQuery {

    private final static DatabaseQuery INSTANCE = new DatabaseQuery();
    public static DatabaseQuery getInstance() {
        return INSTANCE;
    }
    private DatabaseQuery() {
        LogUtils.init();
    }

    //获取所有书籍
    /**
     * @return 返回书籍列表
     */
    public List<Book> getAllBooks()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> books = new CopyOnWriteArrayList<Book>();
        try{
            //建立数据库连接
            //connection = DriverManager.getConnection(Static.jdbcUrl, Static.username, Static.password);
            connection = C3p0Util.getConnection();
            LogUtils.info("Connected","DatabaseQuery");

            String query = "SELECT * FROM tb_Book";
            //执行查询操作
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            //遍历查询结果
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String location = resultSet.getString("location");
                int number = resultSet.getInt("number");
                books.add(new Book(name, type, number, location));

            }
            return books;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            LogUtils.info("Disconnected","DatabaseQuery");
            C3p0Util.release(connection, preparedStatement, resultSet);
        }
        return books;
    }



    //获取所有用户
    /**
     * @return 返回用户列表
     */
    public List<User> getAllUsers()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new CopyOnWriteArrayList<User>();
        try{
            //建立数据库连接
            //connection = DriverManager.getConnection(Static.jdbcUrl, Static.username, Static.password);
            connection = C3p0Util.getConnection();
            LogUtils.info("Connected","DatabaseQuery");

            String query = "SELECT * FROM tb_User";
            //执行查询操作
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            //遍历查询结果
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String username = resultSet.getString("username");
                String phone = resultSet.getString("phone");
                int permissions = resultSet.getInt("permissions");
                users.add(new User(uuid, username,phone, permissions));

            }
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            LogUtils.info("Disconnected","DatabaseQuery");
            C3p0Util.release(connection, preparedStatement, resultSet);
        }
        return users;
    }

   //查找用户
    /**
     * @param Column 要查找的用户的属性名
     * @param Field 要查找的用户的属性值
     * @param isFuzzySearch 是否模糊查找true模糊 false精确
     * @return 返回用户列表
     */
    public List<User> queryUser(String Column, String Field, boolean isFuzzySearch)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new CopyOnWriteArrayList<User>();
        //建立数据库连接
        try {
            connection = C3p0Util.getConnection();
            LogUtils.info("Connected","DatabaseQueryQuery");

            if (connection == null) {
                LogUtils.error("NullPointerError", "Database connection is null");
                return new CopyOnWriteArrayList<User>() {};
            }

            //执行查询操作
            if(isFuzzySearch)
            {
                String query = "SELECT * FROM tb_User WHERE "+Column+" LIKE ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "%"+Field+"%");
                resultSet = preparedStatement.executeQuery();
            }else {
                String query = "SELECT * FROM tb_User WHERE "+Column+" = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, Field);
                resultSet = preparedStatement.executeQuery();
            }

            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String username = resultSet.getString("username");
                String phone = resultSet.getString("phone");
                int permissions = resultSet.getInt("permissions");
                users.add(new User(uuid, username, phone,permissions));
            }
            return users;
        }catch (Exception e)
        {
            LogUtils.error("queryUser", e.toString());
            e.printStackTrace();
        }

        return users;
    }







}
