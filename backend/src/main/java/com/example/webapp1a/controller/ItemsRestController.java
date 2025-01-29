package com.example.webapp1a.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.model.Size;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.service.ItemService;
import com.example.webapp1a.service.SizeService;
import com.example.webapp1a.service.StockService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;



@RestController
@RequestMapping("/api/inventory")
public class ItemsRestController {

    //admin add items
    @Autowired
    private ItemService itemService;

    @Autowired
    private StockService stockService;

    @Autowired
    private SizeService sizeService;

    
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        itemService.add(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/sizes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Size> addSize(@RequestBody Size size) {
        sizeService.add(size);
        return new ResponseEntity<>(size, HttpStatus.OK);
    }

    @GetMapping("/sizes")
    public ResponseEntity<Page<Size>> getSizes(Pageable page){
        Page<Size> sizes = sizeService.findAll(page);
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }

    /**delete method to remove a size from the size table and to unliked of the stock table */

    /**post method for an item and its stock object */

    @PostMapping("/items/clothes/stock")
    public ResponseEntity<Clothes> addClothesStock(@RequestBody Clothes clothes) {
        Optional<Clothes> existingClothes = stockService.findByCodeC(clothes.getCode());
        if(!existingClothes.isPresent()) {
            stockService.addClothes(clothes);
            return new ResponseEntity<>(clothes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/items/shoes/stock")
    public ResponseEntity<Shoe> addShoeStock(@RequestBody Shoe shoe) {
        Optional<Shoe> existingShoe = stockService.findByCodeS(shoe.getCode());
        if(!existingShoe.isPresent()) {
            stockService.addShoe(shoe);
            return new ResponseEntity<>(shoe, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/clothes/stock")
    public ResponseEntity<Page<Clothes>> getClothesStock(Pageable page){
        Page<Clothes> clothes = stockService.findAllClothes(page);
        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @GetMapping("/shoes/stock")
    public ResponseEntity<Page<Shoe>> getShoesStock(Pageable page){
        Page<Shoe> shoes = stockService.findAllShoe(page);
        return new ResponseEntity<>(shoes, HttpStatus.OK);
    }

    @GetMapping("/stocks")
    public ResponseEntity<Page<Stock<?>>> getAllStocks(Pageable page){
        Page<Stock<?>> stocks = stockService.findAllStocks(page);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * stocks of an item
     * @param page
     * @param id
     * @return
     */
    @GetMapping("/stocks/item/{id}")
    public ResponseEntity<Page<Stock<?>>> getStockItem(Pageable page, @PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            Page<Stock<?>> stocks = stockService.findByItem(item.get(), page);
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/items/{id}/update")
    public ResponseEntity<Item> itemUpdating(@RequestBody Item itemUpdated, @PathVariable Integer id, MultipartFile imageField) throws  IOException{
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            if(!imageField.isEmpty()){
                itemUpdated.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            }
            //itemService.update(item.get().getId(), itemUpdated);
            return new ResponseEntity<>(itemUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Item> deleteItemById(@PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            itemService.deleteById(id);
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/items/{id}/delete")
    public ResponseEntity<Page<Item>> deleteItemById(@PathVariable Integer id, Pageable page){
        itemService.deleteById(id);
        return new ResponseEntity<>(itemService.findAll(page), HttpStatus.OK);
    }

    @PostMapping("/items/{id}/image")
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

    /*@PutMapping("/item/{id}")
    public ResponseEntity<Item> editItem(@RequestBody Item newItem, @PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            itemService.update(id,newItem);
            return new ResponseEntity<>(newItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    

    @GetMapping("/items")
    public Page<Item> getItems(Pageable page){
        return itemService.findAll(page);
    }*/

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            return new ResponseEntity<>(item.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     
}
