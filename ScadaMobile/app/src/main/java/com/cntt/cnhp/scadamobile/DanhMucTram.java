package com.cntt.cnhp.scadamobile;

import java.sql.Date;

public class DanhMucTram {

    private int id;
    private String ten;
    private String dia_chi;
    private int nm_do;
    private int id_tql;
    private double vido;
    private double kinhdo;
    private int stt;
    private int trang_thai;
    private String thoi_gian;
    private int id_chinhanh;



    public DanhMucTram() {
    }

    public DanhMucTram(int id, String ten, String dia_chi, int nm_do, int id_tql, double vido, double kinhdo, int stt, int trang_thai, String thoi_gian, int id_chinhanh) {
        this.id = id;
        this.ten = ten;
        this.dia_chi = dia_chi;
        this.nm_do = nm_do;
        this.id_tql = id_tql;
        this.vido = vido;
        this.kinhdo = kinhdo;
        this.stt = stt;
        this.trang_thai = trang_thai;
        this.thoi_gian = thoi_gian;
        this.id_chinhanh = id_chinhanh;
    }

    public int getId_chinhanh() {
        return id_chinhanh;
    }

    public void setId_chinhanh(int id_chinhanh) {
        this.id_chinhanh = id_chinhanh;
    }

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }


    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public int getNm_do() {
        return nm_do;
    }

    public void setNm_do(int nm_do) {
        this.nm_do = nm_do;
    }

    public int getId_tql() {
        return id_tql;
    }

    public void setId_tql(int id_tql) {
        this.id_tql = id_tql;
    }

    public double getVido() {
        return vido;
    }

    public double getKinhdo() {
        return kinhdo;
    }

    public void setVido(double vido) {
        this.vido = vido;
    }

    public void setKinhdo(double kinhdo) {
        this.kinhdo = kinhdo;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }


}
