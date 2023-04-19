package ru.clevertec.controller;

import org.springframework.web.bind.annotation.*;
import ru.clevertec.entity.GiftCertificate;
import ru.clevertec.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final ServiceInterface<GiftCertificate> service;

    public GiftCertificateController(ServiceInterface<GiftCertificate> service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<GiftCertificate> getAll() {
        return service.getAll();
    }

    @PostMapping("/")
    public GiftCertificate add(@RequestBody GiftCertificate certificate) {
        service.create(certificate);
        return certificate;
    }

    @PutMapping("/")
    public GiftCertificate update(@RequestBody GiftCertificate certificate) {
        service.update(certificate);
        return certificate;
    }

    @DeleteMapping("/id")
    public String delete(@PathVariable int id){
        service.delete(id);
        return "Gift certificate with id "+id+" has been deleted";
    }
}
