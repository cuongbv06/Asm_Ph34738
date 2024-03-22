package com.sonans.lab1_and102;

public class OtoModel {

    private  String _id;
    private String ten;
    private String mau;
    private int gia;
    private String image;

    public OtoModel() {
    }

    public OtoModel(String ten, String mau, int gia) {
        this.ten = ten;
        this.mau = mau;
        this.gia = gia;
    }

    public OtoModel(String ten, String mau, int gia, String image) {
        this.ten = ten;
        this.mau = mau;
        this.gia = gia;
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
