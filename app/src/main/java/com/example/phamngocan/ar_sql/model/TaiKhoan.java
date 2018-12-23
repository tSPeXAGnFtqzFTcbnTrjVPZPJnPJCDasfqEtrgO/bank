package com.example.phamngocan.ar_sql.model;

import android.util.Log;

import java.util.StringTokenizer;

public class TaiKhoan {
    String sotk,cmnd,macn;
    String soTien;
    long soTienInt;

    public TaiKhoan(String sotk, String cmnd, String macn, String soTien) {
        this.sotk = sotk;
        this.cmnd = cmnd;
        this.macn = macn;
        this.soTien = soTien;

        convertMoney();
    }
    void convertMoney(){
        StringTokenizer st = new StringTokenizer(this.soTien,".");
        if(st.hasMoreTokens()) {
            this.soTienInt = Long.parseLong(st.nextToken());
        }else{
            this.soTienInt = 0;
        }

    }

    public long getSoTienInt() {
        return soTienInt;
    }

    public void setSoTienInt(long soTienInt) {
        this.soTienInt = soTienInt;
    }

    public String getSotk() {
        return sotk;
    }

    public void setSotk(String sotk) {
        this.sotk = sotk;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getMacn() {
        return macn;
    }

    public void setMacn(String macn) {
        this.macn = macn;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }
}
