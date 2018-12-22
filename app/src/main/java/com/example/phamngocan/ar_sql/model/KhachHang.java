package com.example.phamngocan.ar_sql.model;

public class KhachHang {
    String cmnd,hoten,diachi,phai,sodt,macn;
    String cmndT,hotenT,diachiT,phaiT,sodtT,macnT;

    public KhachHang(String cmnd, String hoten, String diachi, String phai, String sodt, String macn) {
        this.cmnd = cmnd;
        this.hoten = hoten;
        this.diachi = diachi;
        this.phai = phai;
        this.sodt = sodt;
        this.macn = macn;

        this.cmndT = cmnd;
        this.hotenT = hoten;
        this.diachiT = diachi;
        this.phaiT = phai;
        this.sodtT = sodt;
        this.macnT = macn;

    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getPhai() {
        return phai;
    }

    public void setPhai(String phai) {
        this.phai = phai;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getMacn() {
        return macn;
    }

    public void setMacn(String macn) {
        this.macn = macn;
    }

    public String getCmndT() {
        return cmndT;
    }

    public void setCmndT(String cmndT) {
        this.cmndT = cmndT;
    }

    public String getHotenT() {
        return hotenT;
    }

    public void setHotenT(String hotenT) {
        this.hotenT = hotenT;
    }

    public String getDiachiT() {
        return diachiT;
    }

    public void setDiachiT(String diachiT) {
        this.diachiT = diachiT;
    }

    public String getPhaiT() {
        return phaiT;
    }

    public void setPhaiT(String phaiT) {
        this.phaiT = phaiT;
    }

    public String getSodtT() {
        return sodtT;
    }

    public void setSodtT(String sodtT) {
        this.sodtT = sodtT;
    }

    public String getMacnT() {
        return macnT;
    }

    public void setMacnT(String macnT) {
        this.macnT = macnT;
    }
}

