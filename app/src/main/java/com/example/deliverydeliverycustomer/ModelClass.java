package com.example.deliverydeliverycustomer;

public class ModelClass {
    String status;
    String date;
    String imageurl;
    String offeredAmount;

    public ModelClass() {
    }

    public ModelClass(String status, String date, String imageurl, String offeredAmount) {
        this.status = status;
        this.date = date;
        this.imageurl = imageurl;
        this.offeredAmount = offeredAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getOfferedAmount() {
        return offeredAmount;
    }

    public void setOfferedAmount(String offeredAmount) {
        this.offeredAmount = offeredAmount;
    }
}
