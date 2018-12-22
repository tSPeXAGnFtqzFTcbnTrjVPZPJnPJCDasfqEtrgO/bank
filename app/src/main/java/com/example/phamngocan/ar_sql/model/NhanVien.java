package com.example.phamngocan.ar_sql.model;

public class NhanVien {
    private String hoten,diachi,manv,phai,sodt,macn;
    private String hotenT,diachiT,manvT,phaiT,sodtT,macnT;

    public NhanVien(String hoten, String diachi, String manv, String phai, String sodt, String macn) {
        this.hoten = hoten;
        this.diachi = diachi;
        this.manv = manv;
        this.phai = phai;
        this.sodt = sodt;
        this.macn = macn;

        this.hotenT = hoten;
        this.diachiT = diachi;
        this.manvT = manv;
        this.phaiT = phai;
        this.sodtT = sodt;
        this.macnT = macn;
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

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
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

    public String getManvT() {
        return manvT;
    }

    public void setManvT(String manvT) {
        this.manvT = manvT;
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
