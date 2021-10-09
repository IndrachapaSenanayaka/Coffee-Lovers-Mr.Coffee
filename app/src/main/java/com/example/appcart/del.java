package com.example.appcart;

public class del {
    private String name;
    private String sno;
    private String hno;
    private String pno;

    public del(String name, String sno, String hno, String pno) {
        this.name = name;
        this.sno = sno;
        this.hno = hno;
        this.pno = pno;
    }

    public  del(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getHno() {
        return hno;
    }

    public void setHno(String hno) {
        this.hno = hno;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }
}
