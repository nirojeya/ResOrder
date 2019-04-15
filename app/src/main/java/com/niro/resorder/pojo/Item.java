package com.niro.resorder.pojo;

public class Item {

    private String itemNumber;
    private String itemDesc;
    private double itemPrice;
    private double itemQty;
    private double sellingPrice;
    private String itemImg;
    private String itemCategory;
    private String itemSubCategory;
    private int bid;

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getSelingPrice() {
        return sellingPrice;
    }

    public void setSelingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemSubCategory() {
        return itemSubCategory;
    }

    public void setItemSubCategory(String itemSubCategory) {
        this.itemSubCategory = itemSubCategory;
    }

    public double getItemQty() {
        return itemQty;
    }

    public void setItemQty(double itemQty) {
        this.itemQty = itemQty;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}
