package com.nucsaping.service;

import com.nucsaping.entity.Invoice;
import com.nucsaping.exception.InvoiceNotFoundException;
import com.nucsaping.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice saveInvoice(Invoice inv) {
        return invoiceRepository.save(inv);
    }

    @Override
    @CachePut(value = "Invoice", key = "#id")
    public Invoice updateInvoice(Invoice invoice, Integer id) {

        Invoice existing = getOneInvoice(id);

        existing.setAmount(invoice.getAmount());
        existing.setName(invoice.getName());

        return saveInvoice(existing);
    }

    @Override
//    @CacheEvict(value = "Invoice", key = "#id")
//    @Caching(
//            evict = {@CacheEvict("Invoice"), @CacheEvict(value="Invoice", key="#id")
//            })
    @CacheEvict(value = "Invoice", allEntries = true)
    public void deleteInvoice(Integer id) {
        Invoice existing = getOneInvoice(id);

        invoiceRepository.delete(existing);
    }

    // the value of key=#id must be the same with the parameter name, which is id without the # sign.
    // otherwise, it will cause an error
    @Override
    @Cacheable(value = "Invoice", key = "#id")
    public Invoice getOneInvoice(Integer id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (!invoice.isPresent()) {
            throw new InvoiceNotFoundException("Invoice not found");
        }

        return invoice.get();
    }

    @Override
//    @Cacheable(value = "Invoice")
    @CacheEvict(value = "Invoice", allEntries = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}
