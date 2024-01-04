package Database;
import Util.LogUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3p0Util {
    private static DataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource();
    }

    //从连接池中获取连接
    public static java.sql.Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //释放连接回连接池
    public static void release(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                LogUtils.error("rs.close", e.toString());
                e.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                LogUtils.error("stmt.close", e.toString());
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                LogUtils.error("conn.close", e.toString());
                e.printStackTrace();
            }
            conn = null;
        }
    }

}

