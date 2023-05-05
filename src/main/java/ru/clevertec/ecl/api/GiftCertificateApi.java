package ru.clevertec.ecl.api;

import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.util.Fields;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public interface GiftCertificateApi {

    @GetMapping
    public List<GiftCertificate> getGiftCertificates();

    @GetMapping("/{id}")
    public GiftCertificate getById(@PathVariable int id);

    @PostMapping
    public GiftCertificate add(@RequestBody GiftCertificate certificate);


    @PutMapping("/{id}")
    GiftCertificate update(@PathVariable int id, @RequestBody GiftCertificate certificate);

    @DeleteMapping("/{id}")
    String delete(@PathVariable int id);

    @GetMapping("/find")
    List<GiftCertificate> findTags(@RequestParam(name = Fields.NAME, required = false) String name,
                                   @RequestParam(name = Fields.SORT, required = false) String sort,
                                   @RequestParam(name = Fields.SORT_TYPE, required = false) String sortType,
                                   @RequestParam(name = Fields.DESCRIPTION, required = false) String description,
                                   @RequestParam(name = Fields.ORDER, required = false) String order,
                                   @RequestParam(name = Fields.TAG_NAME, required = false) String tagName);
}
