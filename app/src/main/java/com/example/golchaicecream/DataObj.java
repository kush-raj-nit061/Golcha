package com.example.golchaicecream;

public class DataObj {
    long invoiceNo;

    public DataObj() {
    }

    public long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Double getFuelQty() {
        return fuelQty;
    }

    public void setFuelQty(Double fuelQty) {
        this.fuelQty = fuelQty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    String customerName;
    long date;
    String fuelType;
    Double fuelQty;
    Double amount;
}
