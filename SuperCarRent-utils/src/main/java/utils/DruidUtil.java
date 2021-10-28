package utils;
/**
 * 数据库连接池
 *
 */

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class DruidUtil {

    static DataSource dataSource = null;

    private DruidUtil() { }

    static {//初始化数据库连接池参数

        //使用类加载器，加载配置文件
        InputStream is = DruidUtil.class.getClassLoader().getResourceAsStream("db.properties");
        //创建properties对象
        Properties pro = new Properties();
        //将输入流加载进ps
        try {
            pro.load(is);
            //创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接池方法
    public static DataSource getDataSource() {
        return dataSource;
    }

    //获取连接方法
    public static Connection getConnection() {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //释放资源，关闭连接
    public static void closeAll(ResultSet res, Statement sta, Connection con) {

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (sta != null) {
                sta.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (res != null) {
                res.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        DataSource dataSource = DruidUtil.getDataSource();
//        System.out.println(dataSource);
//    }


}
