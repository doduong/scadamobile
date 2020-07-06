package com.cntt.cnhp.scadamobile;

public class DanhSachThanhVien {

    private int id;
    private String thanhvien_name;
    private int donvi;
    private int nhomquyen_id;
    private int ms_tql;
    private String chuthich;
    private String tendangnhap;
    private String matkhau;
    private int ms_chinhanh;
    private int phutrachchinh;

    public DanhSachThanhVien() {
    }

    public DanhSachThanhVien(int id, String thanhvien_name, int don_vi, int nhomquyen_id, int ms_tql, String chuthich, String tendangnhap, String matkhau, int ms_chinhanh, int phutrachchinh) {
        this.id = id;
        this.thanhvien_name = thanhvien_name;
        this.donvi = don_vi;
        this.nhomquyen_id = nhomquyen_id;
        this.ms_tql = ms_tql;
        this.chuthich = chuthich;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.ms_chinhanh = ms_chinhanh;
        this.phutrachchinh = phutrachchinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThanhvien_name() {
        return thanhvien_name;
    }

    public void setThanhvien_name(String thanhvien_name) {
        this.thanhvien_name = thanhvien_name;
    }

    public int getDon_vi() {
        return donvi;
    }

    public void setDon_vi(int don_vi) {
        this.donvi = don_vi;
    }

    public int getNhomquyen_id() {
        return nhomquyen_id;
    }

    public void setNhomquyen_id(int nhomquyen_id) {
        this.nhomquyen_id = nhomquyen_id;
    }

    public int getMs_tql() {
        return ms_tql;
    }

    public void setMs_tql(int ms_tql) {
        this.ms_tql = ms_tql;
    }

    public String getChuthich() {
        return chuthich;
    }

    public void setChuthich(String chuthich) {
        this.chuthich = chuthich;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getMs_chinhanh() {
        return ms_chinhanh;
    }

    public void setMs_chinhanh(int ms_chinhanh) {
        this.ms_chinhanh = ms_chinhanh;
    }

    public int getPhutrachchinh() {
        return phutrachchinh;
    }

    public void setPhutrachchinh(int phutrachchinh) {
        this.phutrachchinh = phutrachchinh;
    }
}
