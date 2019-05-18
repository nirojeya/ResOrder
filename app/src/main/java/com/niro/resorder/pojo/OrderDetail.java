package com.niro.resorder.pojo;

public class OrderDetail {

    private String itemNumber;
    private String orderId;
    private String itemDesc;
    private double itemQty;
    private double itemPrice;
    private double sellingPrice;
    private int batchNo;
    private String uom = "no";
    private double itemDiscount = 0.0;
    private int vatCode = 0;


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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getItemQty() {
        return itemQty;
    }

    public void setItemQty(double itemQty) {
        this.itemQty = itemQty;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public double getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public int getVatCode() {
        return vatCode;
    }

    public void setVatCode(int vatCode) {
        this.vatCode = vatCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}


























