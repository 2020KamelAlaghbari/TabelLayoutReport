package com.ebdaa.reportapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Invoices {

    public ArrayList< InvoiceData> getInvoices() {
        ArrayList< InvoiceData> data = new ArrayList<>();

        for (int i = 0; i < 215; i++) {
            InvoiceData row = new InvoiceData();
            row.id = (i + 1);
            row.invoiceNumber = row.id;
            row.amountDue = BigDecimal.valueOf(20.00 * i);
            row.invoiceAmount = BigDecimal.valueOf(120.00 * (i + 1));
            row.invoiceDate = new Date();
            row.customerName = "حوالة مرسلة الى الأخ محمد علي قاسم مقابل ترقية النظام الحالي الى نظام شركات وعليه تم الاتفاق";
            row.customerAddress = "1112, Hash Avenue, NYC";

            data.add(row);
        }
        return data;

    }
}
