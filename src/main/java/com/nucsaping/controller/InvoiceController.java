package com.nucsaping.controller;

import com.nucsaping.entity.Invoice;
import com.nucsaping.service.InvoiceService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/saveInvoice")
    public Invoice saveInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @GetMapping("/allInvoice")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> allInvoices = invoiceService.getAllInvoices();

        return new ResponseEntity<>(allInvoices, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getOneInvoice(@PathVariable Integer id) {
        Invoice invoice = invoiceService.getOneInvoice(id);

        return new ResponseEntity<>(invoice, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice, @PathVariable Integer id) {
        Invoice updateInvoice = invoiceService.updateInvoice(invoice, id);

        return new ResponseEntity<>(updateInvoice, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteInvoice(id);

        return "Employee with id: " + id + " has been deleted.";
    }
}
