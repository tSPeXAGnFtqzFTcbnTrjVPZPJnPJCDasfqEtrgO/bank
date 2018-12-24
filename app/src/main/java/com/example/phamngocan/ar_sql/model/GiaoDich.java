package com.example.phamngocan.ar_sql.model;

public class GiaoDich {
    String ngay,loaigd;
    long soduSau,sotien,soduDau;

    public GiaoDich(String ngay, String loaigd, int sotien) {
        this.ngay = ngay;
        this.loaigd = loaigd;
        this.sotien = sotien;
    }

    public void setSoduDau(long soduDau) {
        this.soduDau = soduDau;
    }

    public long getSoduDau() {
//        if(loaigd.equals("GT")||loaigd.equals("NT")){
//            this.soduDau = this.soduSau - this.sotien;
//        }else if(loaigd.equals("RT") || loaigd.equals("CT")){
//            this.soduDau = this.soduSau-this.sotien;
//        }
        return this.soduDau;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getLoaigd() {
        if(this.loaigd.equals("GT")){
            return "Gửi";
        }
        if(this.loaigd.equals("RT")){
            return "Rút";
        }
        if(this.loaigd.equals("NT")){
            return "Nhận";
        }
        if(this.loaigd.equals("CT")){
            return "Chuyển";
        }
        return loaigd;
    }

    public void setLoaigd(String loaigd) {
        this.loaigd = loaigd;
    }

    public long getSoduSau() {
        return soduSau;
    }

    public void setSoduSau(long soduSau) {
        this.soduSau = soduSau;
    }

    public long getSotien() {
        return sotien;
    }

    public void setSotien(long sotien) {
        this.sotien = sotien;
    }
}
