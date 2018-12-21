package com.example.phamngocan.ar_sql;

import android.util.Log;
import android.util.Pair;

import java.lang.annotation.IncompleteAnnotationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOManager {

    // Database Connection
    public Connection conn = null;
    public Statement stmt = null;
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

    public Pair<Boolean,String> open(){
        if(this.conn == null && this.stmt==null){


            try {
                Log.d("AAA","open: " + Instance.loginName+" " +Instance.pass);
                Instance.serverip="192.168.56.1"+":"+Instance.serverport;
                Instance.DB_URL = "jdbc:jtds:sqlserver://" + Instance.serverip + ";"
                        + "databaseName=" + Instance.dbName + ";user=" + Instance.loginName + ";password="
                        + Instance.pass + ";";
                this.conn = DriverManager.getConnection(Instance.DB_URL);
                this.stmt = this.conn.createStatement();

            } catch (SQLException e) {
                Log.d("AAA","error open: " + e.getMessage());
                e.printStackTrace();
                return new Pair<>(false,e.getMessage());
            }

        }
        Log.d("AAA","open: " + Instance.DB_URL);
        return new Pair<>(true,"");

    }
    public void close(){
        if(this.conn!=null){
            try {
                stmt.close();
                conn.close();
                stmt = null;
                conn  = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
