package com.cntt.cnhp.scadamobile;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {

    //static String ip = "192.168.11.18:1433"; //server IP
    static String ip = "113.160.111.205:1433"; //server IP
    static String classs = "net.sourceforge.jtds.jdbc.Driver"; //khóa này không thay đổi
    static String db = "baocao_scada_db";//tên database
    static String un = "sa_baocao"; //user đăng nhập vào SQL server mặc định là sa
    static String password = "Cntt54dth88";// mật khẩu đăng nhập

    static String z;
    static Boolean isSuccess;

    @SuppressLint("NewApi")
    public static Connection Connect() throws SQLException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName(classs).newInstance();
            //databaseName=
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";instanceName=SQLEXPRESS;databaseName=" + db + ";integratedSecurity=true;user=" + un + ";password=" + password + ";";
            //ConnURL="jdbc:mysql://{hostname}:{port}"
            conn = DriverManager.getConnection(ConnURL);
            Log.e("CONN", ConnURL);
        }catch(SQLException se) {

            Log.e("ERROR1","Không thể tải lớp Driver! "+ se.getMessage());
            ///Connection conn2 = DriverManager.getConnection(url, username, password);
        } catch(ClassNotFoundException e){
            Log.e("ERROR2", "Xuất hiện vấn đề truy cập trong khi tải" + e.getMessage());

        }catch (Exception e){
            Log.e("ERROR3", "Không thể khởi tạo Driver!" + e.getMessage());

        }
        return conn;
    }
}
