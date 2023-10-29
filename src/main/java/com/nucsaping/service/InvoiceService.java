package com.nucsaping.service;

import com.nucsaping.entity.Invoice;

import java.util.List;

public interface InvoiceService {

    public Invoice saveInvoice(Invoice inv);

    public Invoice updateInvoice(Invoice invoice, Integer id);

    public void deleteInvoice(Integer invId);

    public Invoice getOneInvoice(Integer id);

    public List<Invoice> getAllInvoices();

}
