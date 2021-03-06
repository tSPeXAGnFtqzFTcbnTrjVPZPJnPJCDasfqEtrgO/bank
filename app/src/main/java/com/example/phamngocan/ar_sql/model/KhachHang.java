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


    public boolean checkNull(){
        if(this.hotenT.equals("") || this.diachiT.equals("") || this.cmndT.equals("") || this.macnT.equals("")
                || this.sodtT.equals("")){
            return true;
        }
        return false;
    }
    public String getHo(){
        return this.hoten.split(" ")[0];
    }
    public String getTen(){
        String[] st = this.hoten.split(" ");
        if(st.length == 1) return st[0];
        else return this.hoten.substring(st[0].length()+2);
    }
    public void update(){
        this.hoten = this.hotenT;
        this.diachi = this.diachiT;
        this.cmnd = this.cmndT;
        this.phai = this.phaiT;
        this.sodt = this.sodtT;
        this.macn = this.macnT;
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

