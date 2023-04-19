package ru.clevertec.controller;

import org.springframework.web.bind.annotation.*;
import ru.clevertec.entity.Tag;
import ru.clevertec.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final ServiceInterface<Tag> serviceInterface;

    public TagController(ServiceInterface<Tag> serviceInterface){
        this.serviceInterface = serviceInterface;
    }

    @GetMapping("/")
    public List<Tag> getTags(){
        return serviceInterface.getAll();
    }

    @PostMapping("/")
    public Tag addTag(@RequestBody Tag tag){
        serviceInterface.create(tag);
        return tag;
    }

    @PutMapping("/")
    public Tag updateTag(@RequestBody Tag tag){
        serviceInterface.update(tag);
        return tag;
    }

    @DeleteMapping("/{id}")
    public String deleteTag(@PathVariable int id){
        serviceInterface.delete(id);
        return "Tag with id "+id+" has been deleted";
    }
}