package com.example.phamngocan.ar_sql;

import java.util.ArrayList;

public class Instance {
    public static ArrayList<String> columnNV = new ArrayList<>();
    public static String dbName = "NGANHANG";
    public static String serverport="21111";
    public static String instance= "MSSQLSERVER";
    public static String serverip="192.168.56.1"+":"+serverport;
    public static String user = "sa";
    public static String pass = "andt";


    // JDBC Driver Name And Database URL
    public static String JDBC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    public static String DB_URL = "jdbc:jtds:sqlserver://" + serverip + ";"
            + "databaseName=" + dbName + ";user=" + user + ";password="
            + pass + ";";
    //jdbc:mysql://hostname:port/databaseName

    //  Database Credentials


}
