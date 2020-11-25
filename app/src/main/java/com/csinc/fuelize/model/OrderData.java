package com.csinc.fuelize.model;

import androidx.annotation.NonNull;

public class OrderData implements Comparable {

    private String orderId;
    private String orderCRMNumber;
    private String orderSapID;
    private String orderCustomerID;
    private String orderCustomerName;
    private String orderCustomerAddress;
    private String roName;
    private String roAddress;
    private String status;
    private String orderQuantity;
    private String decantQty;
    private String orderUnitPrice;
    private String latitude;
    private String longitude;
    private String ROLatitude;
    private String ROLongitude;
    private String DecantLatitude;
    private String DecantLongitude;
    private String orderInvoiceNumber;
    private String orderTotalizerStart;
    private String orderTotalizerEnd;
    private String odometerStart;
    private String odometerEnd;
    private String orderTransactionStatus;
    private String orderRFID;
    private String roRFID;
    private String masterTag;
    private double orderDistance;
    private String Comments;
    private String Otp;
    private String OtpCode;
    private String time;

    public OrderData() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCRMNumber() {
        return orderCRMNumber;
    }

    public void setOrderCRMNumber(String orderCRMNumber) {
        this.orderCRMNumber = orderCRMNumber;
    }

    public String getOrderSapID() {
        return orderSapID;
    }

    public void setOrderSapID(String orderSapID) {
        this.orderSapID = orderSapID;
    }

    public String getOrderCustomerID() {
        return orderCustomerID;
    }

    public void setOrderCustomerID(String orderCustomerID) {
        this.orderCustomerID = orderCustomerID;
    }

    public String getOrderCustomerName() {
        return orderCustomerName;
    }

    public void setOrderCustomerName(String orderCustomerName) {
        this.orderCustomerName = orderCustomerName;
    }

    public String getOrderCustomerAddress() {
        return orderCustomerAddress;
    }

    public void setOrderCustomerAddress(String orderCustomerAddress) {
        this.orderCustomerAddress = orderCustomerAddress;
    }

    public String getRoName() {
        return roName;
    }

    public void setRoName(String roName) {
        this.roName = roName;
    }

    public String getRoAddress() {
        return roAddress;
    }

    public void setRoAddress(String roAddress) {
        this.roAddress = roAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getDecantQty() {
        return decantQty;
    }

    public void setDecantQty(String decantQty) {
        this.decantQty = decantQty;
    }

    public String getOrderUnitPrice() {
        return orderUnitPrice;
    }

    public void setOrderUnitPrice(String orderUnitPrice) {
        this.orderUnitPrice = orderUnitPrice;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getROLatitude() {
        return ROLatitude;
    }

    public void setROLatitude(String latitude) {
        this.ROLatitude = latitude;
    }

    public String getROLongitude() {
        return ROLongitude;
    }

    public void setROLongitude(String longitude) {
        this.ROLongitude = longitude;
    }

    public String getDecantLatitude() {
        return DecantLatitude;
    }

    public void setDecantLatitude(String decantLatitude) {
        DecantLatitude = decantLatitude;
    }

    public String getDecantLongitude() {
        return DecantLongitude;
    }

    public void setDecantLongitude(String decantLongitude) {
        DecantLongitude = decantLongitude;
    }

    public String getOrderInvoiceNumber() {
        return orderInvoiceNumber;
    }

    public void setOrderInvoiceNumber(String orderInvoiceNumber) {
        this.orderInvoiceNumber = orderInvoiceNumber;
    }

    public String getOrderTotalizerStart() {
        return orderTotalizerStart;
    }

    public void setOrderTotalizerStart(String orderTotalizerStart) {
        this.orderTotalizerStart = orderTotalizerStart;
    }

    public String getOrderTotalizerEnd() {
        return orderTotalizerEnd;
    }

    public void setOrderTotalizerEnd(String orderTotalizerEnd) {
        this.orderTotalizerEnd = orderTotalizerEnd;
    }

    public String getOdometerStart() {
        return odometerStart;
    }

    public void setOdometerStart(String odometerStart) {
        this.odometerStart = odometerStart;
    }

    public String getOdometerEnd() {
        return odometerEnd;
    }

    public void setOdometerEnd(String odometerEnd) {
        this.odometerEnd = odometerEnd;
    }

    public String getOrderTransactionStatus() {
        return orderTransactionStatus;
    }

    public void setOrderTransactionStatus(String orderTransactionStatus) {
        this.orderTransactionStatus = orderTransactionStatus;
    }

    public String getOrderRFID() {
        return orderRFID;
    }

    public void setOrderRFID(String orderRFID) {
        this.orderRFID = orderRFID;
    }

    public String getRoRFID() {
        return roRFID;
    }

    public void setRoRFID(String roRFID) {
        this.roRFID = roRFID;
    }

    public String getMasterTag() {
        return masterTag;
    }

    public void setMasterTag(String masterTag) {
        this.masterTag = masterTag;
    }

    public double getOrderDistance() {
        return orderDistance;
    }

    public void setOrderDistance(double orderDistance) {
        this.orderDistance = orderDistance;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public String getOtpCode() {
        return OtpCode;
    }

    public void setOtpCode(String otpCode) {
        OtpCode = otpCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        double compare_age = ((OrderData) o).getOrderDistance();
        /* For Ascending order*/
        return (int) (orderDistance - compare_age);

        /* For Descending order do like this */
        //return compare age-this.student age;
    }
}
