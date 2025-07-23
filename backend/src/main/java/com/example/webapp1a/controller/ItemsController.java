package com.example.webapp1a.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.model.Size;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.service.ItemService;
import com.example.webapp1a.service.SizeService;
import com.example.webapp1a.service.StockService;
import com.example.webapp1a.stockEditionFactoryMethod.StockFactoryManager;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private StockService stockService;

    @Autowired
    private SizeService sizeService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){
    }
    
    @GetMapping("/")
    public String home(Model model){
        return "index";   
    }   
    
    public void addNewItem(Item item, MultipartFile imageField) throws IOException{
        item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        item.setCode(UUID.randomUUID().toString().toUpperCase().substring(0, 7));
        itemService.add(item); 
    }

    public Stock<?> addNewStock(Item item, Stock<?> stock, String label, Integer amount) throws IOException{
        Size size = new Size();
        size.setCode(UUID.randomUUID().toString().toUpperCase().substring(0, 5));
        size.setLabel(label);
        sizeService.add(size);  

        stock.setSize(size);

        stock.setCode(UUID.randomUUID().toString().toUpperCase().substring(0, 7));
        stock.setStock(amount);
        
        //filtering of the last item keeped in the db
        stock.setItem(item);
        return stock;
    }

    /**
     * 
     * @param model
     * @param id
     * @param page
     * @return screen to update an item
     */
    @GetMapping("/{id}")
    public String itemPage(Model model, @PathVariable Integer id, Pageable page){
        Optional<Item> item = itemService.findById(id);

        if(item.isPresent()) {
            StockFactoryManager stockFactoryManager = new StockFactoryManager();
            model.addAttribute("genders", stockFactoryManager.getGenders());
            model.addAttribute("types", stockFactoryManager.getFactories().keySet());
            model.addAttribute("category", stockFactoryManager.getFactories().get(item.get().getType()));
            return "edition";
        } else {
            return "//localhost:8443/error";
        }
    }

    /*
     * updating operation
     */
    @PostMapping("/{id}/update")
    public String itemUpdating(Model model, Item item, @PathVariable Integer id, MultipartFile imageField) throws IOException{
        Optional<Item> oldItem = itemService.findById(id);
        if(oldItem.isPresent()){
            StockFactoryManager stockFactoryManager = new StockFactoryManager();

            if(imageField != null && !imageField.isEmpty()){
                item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
                oldItem.get().setImageFile(item.getImageFile());
            }
            itemService.save(oldItem.get(), item);
            model.addAttribute("genders", stockFactoryManager.getGenders());
            model.addAttribute("types", stockFactoryManager.getFactories().keySet());
            model.addAttribute("category", stockFactoryManager.getFactories().get(oldItem.get().getType()));
            return "edition";
        } else {
            return "//localhost:8443/error";
        }
    }

    //CLOTHES SECTION!!!

    @GetMapping("{id}/clothes/stock")
    public String clothesStockPage(Model model, @PathVariable Integer id) {
        return "new_clothes_stock";
    }
    
    @GetMapping("/clothes/page")
    public String clothesPage(Model model){
        StockFactoryManager stockFactoryManager = new StockFactoryManager();
        model.addAttribute("genders", stockFactoryManager.getGenders());
        model.addAttribute("types", stockFactoryManager.getFactories().keySet());
        return "new_clothes";
    }

    @PostMapping("/clothes/new")
    public String newClothesPage(Model model, Item item, MultipartFile imageField) throws IOException{
        addNewItem(item, imageField);
        model.addAttribute("id", item.getId());
        return "new_clothes_stock";
    }  

    @PostMapping("{id}/clothes/stock/new")
    public String newClothesStockPage(Model model, @PathVariable Integer id, Clothes clothes, String label, Integer stock) throws IOException{
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            Stock<?> product = addNewStock(item.get(), clothes, label, stock);
            stockService.addStock((Clothes)product);
            //new stock added successfully
            return "new_clothes_stock";
        }
        //error adding new stock
        return "//localhost:8443/error";       
    }

    //SHOES SECTION!!!

    @GetMapping("{id}/shoes/stock")
    public String shoesStockPage(Model model, @PathVariable Integer id) {
        return "new_shoes_stock";
    }

    @GetMapping("/shoes/page")
    public String shoesPage(Model model){
        StockFactoryManager stockFactoryManager = new StockFactoryManager();
        model.addAttribute("genders", stockFactoryManager.getGenders());
        model.addAttribute("types", stockFactoryManager.getFactories().keySet());
        return "new_shoes";
    }

    @PostMapping("/shoes/new")
    public String newShoesPage(Model model, Item item, MultipartFile imageField) throws IOException{
        addNewItem(item, imageField);
        model.addAttribute("id", item.getId());
        return "new_shoes_stock";
    }

    @PostMapping("{id}/shoes/stock/new")
    public String newShoesStock(Model model, @PathVariable Integer id, Shoe shoes, String label, Integer stock) throws IOException{
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            Stock<?> product = addNewStock(item.get(), shoes, label, stock);
            stockService.addStock((Shoe)product);
            //new stock added successfully
            return "new_shoes_stock";
        }
        //error adding new stock
        return "//localhost:8443/error";       
    }

    public void deleteItemStock(Integer id, Integer index){
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            Optional<Stock<?>> stock = stockService.findById(index);
            if(stock.isPresent()){
                stockService.deleteById(index);
            }
        }
    }

    /*
     * clothes erasing operation
     */
    @GetMapping("/{id}/clothes/{index}/delete")
    public String deleteClothesStockPage(@PathVariable Integer id, @PathVariable Integer index){
        deleteItemStock(id, index);
        return "new_clothes_stock";
    }

    @GetMapping("/{id}/shoes/{index}/delete")
    public String deleteShoesStockPage(@PathVariable Integer id, @PathVariable Integer index){
        deleteItemStock(id, index);
        return "new_shoes_stock";
    }

    /*
     * erasing operation of an item category
     */
    @GetMapping("/{id}/delete")
    public String removeItem(Model model, @PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
 
        if(item.isPresent()) {
            itemService.deleteById(item.get().getId());
            return "index";
        } else {
            return "//localhost:8443/error";
        }
    }
}
