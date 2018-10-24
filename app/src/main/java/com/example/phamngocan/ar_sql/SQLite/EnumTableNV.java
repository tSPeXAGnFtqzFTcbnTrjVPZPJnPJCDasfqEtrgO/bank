package com.example.phamngocan.ar_sql.SQLite;

public enum  EnumTableNV {

    CMND, HO, TEN, DIACHI, PHAI, NGAYCAP, SODT, MACN;
    public static int size = EnumTableNV.values().length;

    @Override
    public String toString() {
        return this.name();
    }
}
