package com.example.webapp1a.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.net.URI;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.service.ItemService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;



@RestController
@RequestMapping("/databases")
public class ItemsRestController {

    //admin add items
    @Autowired
    private ItemService itemService;

    
    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        itemService.add(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /*@DeleteMapping("/item/{id}")
    public ResponseEntity<Item> deleteItemById(@PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            itemService.deleteById(id);
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Item> editItem(@RequestBody Item newItem, @PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            itemService.update(id,newItem);
            return new ResponseEntity<>(newItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @PostMapping("/item/{id}/image")
    public ResponseEntity<Item> addItemImage(@PathVariable Integer id, @RequestParam MultipartFile itemImage) throws IOException{
        
        Optional<Item> item = itemService.findById(id);

        URI location = fromCurrentRequest().build().toUri();
        
        item.get().setImageFile(BlobProxy.generateProxy(itemImage.getInputStream(), itemImage.getSize()));
        
        itemService.add(item.get());

        if(item.isPresent()){
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/items")
    public Page<Item> getItems(Pageable page){
        return itemService.findAll(page);
    }

    @GetMapping("/items/{name}")
    public Page<Item> getItemsByName(@PathVariable String name, Pageable page){
        return itemService.findByName(name, page);
    }

    @GetMapping("/items/{id}/image")
    public ResponseEntity<Object> downloadImage(@PathVariable Integer id) throws SQLException {

        Optional<Item> item = itemService.findById(id);

        if(item.isPresent() && item.get().getImageFile() != null){
            Resource file = new InputStreamResource(item.get().getImageFile().getBinaryStream());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .contentLength(item.get().getImageFile().length())
                .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
