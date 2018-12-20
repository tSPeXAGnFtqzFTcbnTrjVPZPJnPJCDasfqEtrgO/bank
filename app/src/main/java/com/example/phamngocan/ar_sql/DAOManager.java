package com.example.phamngocan.ar_sql;

import android.util.Log;

import java.lang.annotation.IncompleteAnnotationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOManager {

    // Database Connection
    Connection conn = null;
    Statement stmt = null;
    private static DAOManager daoManager = null;
    public static DAOManager getInstance(){
        if(daoManager == null){
            daoManager = new DAOManager();
        }
        return daoManager;
    }
    public DAOManager() {
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName(Instance.JDBC_DRIVER);//.newInstance();
            Log.i("AAA", "connecting");
        } catch (ClassNotFoundException e) {
            Log.e("AAA", e.getMessage());
            e.printStackTrace();

        }
    }

    public void open(){
        if(this.conn == null && this.stmt==null){
            try {
                this.conn = DriverManager.getConnection(Instance.DB_URL);
                this.stmt = this.conn.createStatement();
            } catch (SQLException e) {
                Log.d("AAA","error open: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void close(){
        if(this.conn!=null){
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
