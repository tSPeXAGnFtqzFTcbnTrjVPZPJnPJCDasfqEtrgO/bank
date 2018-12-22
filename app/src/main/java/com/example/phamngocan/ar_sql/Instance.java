package com.example.phamngocan.ar_sql;

import com.example.phamngocan.ar_sql.model.KhachHang;
import com.example.phamngocan.ar_sql.model.NhanVien;

import java.util.ArrayList;

public class Instance {
    public static ArrayList<String> columnNV = new ArrayList<>();
    public static ArrayList<String> chiNhanhList = new ArrayList<>();
    public static ArrayList<NhanVien> nhanvienList = new ArrayList<>();
    public static ArrayList<KhachHang> khachHangList = new ArrayList<>();
    public static String nhom = "";
    public static String userName = "",hoten="";
    public static String[] port = {"20000","20001","20002"};

    public static String dbName = "NGANHANG";
    public static String serverport="20000";
    public static String instance= "MSSQLSERVER";
    public static String serverip="192.168.56.1"+":"+serverport;
    public static String loginName = "sa";
    public static String pass = "andt";


    // JDBC Driver Name And Database URL
    public static String JDBC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
    public static String DB_URL = "jdbc:jtds:sqlserver://" + serverip + ";"
            + "databaseName=" + dbName + ";user=" + loginName + ";password="
            + pass + ";";
    //jdbc:mysql://hostname:port/databaseName

    //  Database Credentials

}
