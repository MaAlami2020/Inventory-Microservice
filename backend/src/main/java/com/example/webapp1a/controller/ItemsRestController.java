package com.example.webapp1a.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.ItemDTO;
import com.example.webapp1a.model.ItemToBuy;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.model.Size;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.service.ItemService;
import com.example.webapp1a.service.ItemToBuyService;
import com.example.webapp1a.service.SizeService;
import com.example.webapp1a.service.StockService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
public class ItemsRestController {

    //admin add items
    @Autowired
    private ItemService itemService;

    @Autowired
    private StockService stockService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    //LOGGED WITH REDDIS

    @GetMapping("/redis/items/{id}")
    public ResponseEntity<Item> getItemByIdReddis(@PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            return new ResponseEntity<>(item.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //LOGGED WITH jwt
    
    @Operation(summary = "Post a new item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post a new item", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item posted", content = @Content)
    })
    @PostMapping("/api/inventory/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        itemService.add(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Operation(summary = "Post a new size")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post a new size", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Size.class))
        }),
        @ApiResponse(responseCode = "404", description = "No size posted", content = @Content)
    })
    @PostMapping("/api/inventory/sizes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Size> addSize(@RequestBody Size size) {
        sizeService.add(size);
        return new ResponseEntity<>(size, HttpStatus.OK);
    }

    @Operation(summary = "Get sizes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get sizes", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Size.class))
        }),
        @ApiResponse(responseCode = "404", description = "No size founded", content = @Content)
    })
    @GetMapping("/api/inventory/sizes")
    public ResponseEntity<Page<Size>> getSizes(Pageable page){
        Page<Size> sizes = sizeService.findAll(page);
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }

    /**delete method to remove a size from the size table and to unliked of the stock table */

    /**post method for an item and its stock object */

    @Operation(summary = "Post a new stock of type clothes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post a new stock of type clothes", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Clothes.class))
        }),
        @ApiResponse(responseCode = "404", description = "No clothes posted", content = @Content)
    })
    @PostMapping("/api/inventory/items/clothes/stock")
    public ResponseEntity<Clothes> addClothesStock(@RequestBody Clothes clothes) {
        Optional<Clothes> existingClothes = stockService.findByCodeC(clothes.getCode());
        if(!existingClothes.isPresent()) {
            stockService.addClothes(clothes);
            return new ResponseEntity<>(clothes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Post a new stock of type shoes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post a new stock of type shoes", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Shoe.class))
        }),
        @ApiResponse(responseCode = "404", description = "No shoes posted", content = @Content)
    })
    @PostMapping("/api/inventory/items/shoes/stock")
    public ResponseEntity<Shoe> addShoeStock(@RequestBody Shoe shoe) {
        Optional<Shoe> existingShoe = stockService.findByCodeS(shoe.getCode());
        if(!existingShoe.isPresent()) {
            stockService.addShoe(shoe);
            return new ResponseEntity<>(shoe, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Get stock of type shoes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get stock of type shoes", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Shoe.class))
        }),
        @ApiResponse(responseCode = "404", description = "No shoes founded", content = @Content)
    })
    @GetMapping("/api/inventory/clothes/stock")
    public ResponseEntity<Page<Clothes>> getClothesStock(Pageable page){
        Page<Clothes> clothes = stockService.findAllClothes(page);
        return new ResponseEntity<>(clothes, HttpStatus.OK);
    }

    @Operation(summary = "Get stock of type shoes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post a new stock of type shoes", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Shoe.class))
        }),
        @ApiResponse(responseCode = "404", description = "No shoes founded", content = @Content)
    })
    @GetMapping("/api/inventory/shoes/stock")
    public ResponseEntity<Page<Shoe>> getShoesStock(Pageable page){
        Page<Shoe> shoes = stockService.findAllShoe(page);
        return new ResponseEntity<>(shoes, HttpStatus.OK);
    }

    @Operation(summary = "Get stocks")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post a new stocks", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Stock.class))
        }),
        @ApiResponse(responseCode = "404", description = "No stock founded", content = @Content)
    })
    @GetMapping("/api/inventory/stocks")
    public ResponseEntity<Page<Stock<?>>> getAllStocks(Pageable page){
        Page<Stock<?>> stocks = stockService.findAllStocks(page);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @Operation(summary = "Post an item, stock and size")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post an item, stock and size", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item founded", content = @Content)
    })
    @GetMapping("/api/inventory/items/{id1}/stocks/{id2}/sizes/{id3}")
    public ResponseEntity<Item> addSizeAndStockToItem(@PathVariable Integer id1, @PathVariable Integer id2, @PathVariable Integer id3) {
        Optional<Item> item = itemService.findById(id1);
        Optional<Stock<?>> stock = stockService.findById(id2);
        Optional<Size> size = sizeService.findById(id3);

        if(item.isPresent() && stock.isPresent() && size.isPresent()){
            stock.get().setSize(size.get());
            stock.get().setItem(item.get());
            stockService.addStock(stock.get());
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    

    /**
     * stocks of an item
     * @param page
     * @param id
     * @return
     */
    @Operation(summary = "Get an item stocks")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get an item stocks", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item stocks founded", content = @Content)
    })
    @GetMapping("/api/inventory/stocks/item/{id}")
    public ResponseEntity<Page<Stock<?>>> getStockItem(Pageable page, @PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            Page<Stock<?>> stocks = stockService.findByItem(item.get(), page);
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update an item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update an item", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item updated", content = @Content)
    })
    @PutMapping("/api/inventory/items/{id}/update")
    public ResponseEntity<Item> itemUpdating(@RequestBody ItemDTO itemUpdated, @PathVariable Integer id) throws  IOException{
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            itemService.update(item.get(), itemUpdated);
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a stock constraints")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete a stock constraints", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Stock.class))
        }),
        @ApiResponse(responseCode = "404", description = "No stock deleted", content = @Content)
    })
    @DeleteMapping("/api/inventory/stocks/{id}")
    public ResponseEntity<Stock<?>> deleteRelationshipItemStock(@PathVariable Integer id){
        Optional<Stock<?>> stock = stockService.findById(id);
        if(stock.isPresent()){
            stock.get().setItem(null);
            stock.get().setSize(null);
            stockService.addStock(stock.get());
            return new ResponseEntity<>(stock.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete a stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete a stock", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Stock.class))
        }),
        @ApiResponse(responseCode = "404", description = "No stock deleted", content = @Content)
    })
    @DeleteMapping("/api/inventory/stocks/{id}/delete")
    public ResponseEntity<Stock<?>> deleteItemStock(@PathVariable Integer id){
        Optional<Stock<?>> stock = stockService.findById(id);
        if(stock.isPresent()){
            stockService.deleteById(id);
            return new ResponseEntity<>(stock.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Delete an item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Delete an item", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item deleted", content = @Content)
    })
    @DeleteMapping("/api/inventory/items/{id}/delete")
    public ResponseEntity<Page<Item>> deleteItem(Pageable page, @PathVariable Integer id, Throwable ex){
        Optional<Item> item = itemService.findById(id);
        List<ItemToBuy> itemsToBuy = itemToBuyService.findAll();
        (itemsToBuy).forEach(itb -> {
            if(itb.getItems().contains(item.get())) {
                throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "unable to delete item with existing purchases",
                    ex
                );
            }
        });
        if(item.isPresent()){
            itemService.deleteById(item.get().getId());
            return new ResponseEntity<>(itemService.findAll(page),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Post an item image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post an item image", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item image posted", content = @Content)
    })
    @PostMapping("/api/inventory/items/{id}/image")
    public ResponseEntity<Item> addItemImage(@PathVariable Integer id, @RequestParam MultipartFile itemImage) throws IOException{
        
        Optional<Item> item = itemService.findById(id);

        URI location = fromCurrentRequest().build().toUri();
        
        if(itemImage != null){
            item.get().setImageFile(BlobProxy.generateProxy(itemImage.getInputStream(), itemImage.getSize()));
        }
        
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

    @Operation(summary = "Get an item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get an item", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
        }),
        @ApiResponse(responseCode = "404", description = "No item founded", content = @Content)
    })
    @GetMapping("/api/inventory/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            return new ResponseEntity<>(item.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
     
}
