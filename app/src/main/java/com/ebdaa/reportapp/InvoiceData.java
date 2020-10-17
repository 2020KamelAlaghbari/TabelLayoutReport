package com.ebdaa.reportapp;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceData {

    public int id;
    public int invoiceNumber;
    public Date invoiceDate;
    public String customerName;
    public String customerAddress;
    public BigDecimal invoiceAmount;
    public BigDecimal amountDue;
}
