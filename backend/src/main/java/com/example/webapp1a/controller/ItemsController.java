package com.example.webapp1a.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigData.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.service.ItemService;
import com.example.webapp1a.service.StockService;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private StockService stockService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){
        model.addAttribute("addStockC",false);
        model.addAttribute("addStockS",false);
    }
    
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("addStockC",false);
        model.addAttribute("addStockS",false);
        return "index";
    }    
  
    @GetMapping("/clothes/page")
    public String newClothesPage(Model model){
        model.addAttribute("addStockC",false); 
        return "new_clothes";
    }

    @PostMapping("/clothes/new")
    public String newClothes(Model model, Item item, MultipartFile imageField) throws IOException{
  
        item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        itemService.add(item);
        model.addAttribute("addStockC",true); 
        return "new_clothes";
    }

    @PostMapping("/clothes/stock/new")
    public String newClothesStock(Model model, Clothes clothes, Pageable page) throws IOException{
        List<Item> item = itemService.findAll();
        //get those clothes stocks whose id_item is null
        Page<Clothes> clothesStock = stockService.findAllClothes(page);
        for(Clothes c: clothesStock) {
            //check the size
            if(clothes.getSize().equals(c.getSize())){
                //check the stock entered is under the avaialable, if so, save the new stock object into the db and reload the clothes page
                if(clothes.getStock() <=  c.getStock()){
                    clothes.setItem(item.get(0));
                    stockService.addClothes(clothes);
                    model.addAttribute("addStockC",true); 
                    return "new_clothes";
                }
            }
        }
        //check the stock entered is under the avaialable, if not, do not save the new stock object into the db and return to the main page 
        model.addAttribute("addStockC",false); 
        return "index";       
    }

    @GetMapping("/shoes/page")
    public String newShoesPage(Model model){
        model.addAttribute("addStockS",false); 
        return "new_shoes";
    }

    @PostMapping("/shoes/new")
    public String newShoe(Model model, Item item, MultipartFile imageField) throws IOException{
  
        item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        itemService.add(item);
        model.addAttribute("addStockS",true); 
        return "new_shoes";
    }

    @PostMapping("/shoes/stock/new")
    public String newShoesStock(Model model, Shoe shoe, Pageable page) throws IOException{
        List<Item> item = itemService.findAll();
        //get those shoe stocks whose id_item is null
        Page<Shoe> shoeStock = stockService.findAllShoe(page);
        for(Shoe s: shoeStock) {
            //check the size
            if(shoe.getSize().equals(s.getSize())){
                //check the stock entered is under the avaialable, if so, save the new stock object into the db and reload the clothes page
                if(shoe.getStock() <=  s.getStock()){
                    shoe.setItem(item.get(0));
                    stockService.addShoe(shoe);
                    model.addAttribute("addStockS",true); 
                    return "new_shoes";
                }
            }
        }
        //check the stock entered is under the avaialable, if not, do not save the new stock object into the db and return to the main page 
        model.addAttribute("addStockS",false); 
        return "index";     
    }

    /*private void showSizes(Model model, Optional<Item> item){
        if(item.get().getSizes() != null && item.get().getSizes()[0] != null){
            model.addAttribute("sizeS",true);
            model.addAttribute("size1",item.get().getSizes()[0]);   
        } else {
            model.addAttribute("size1",""); 
        }
        if(item.get().getSizes() != null && item.get().getSizes()[1] != null){
            model.addAttribute("sizeM",true);
            model.addAttribute("size2",item.get().getSizes()[1]);   
        } else {
            model.addAttribute("size2",""); 
        }
        if(item.get().getSizes() != null && item.get().getSizes()[2] != null){
            model.addAttribute("sizeL",true);
            model.addAttribute("size3",item.get().getSizes()[2]);   
        } else {
            model.addAttribute("size3",""); 
        }
        if(item.get().getSizes() != null && item.get().getSizes()[3] != null){
            model.addAttribute("sizeXL",true);
            model.addAttribute("size4",item.get().getSizes()[3]);   
        } else {
            model.addAttribute("size4",""); 
        } 
        if(item.get().getSizes() == null){
            model.addAttribute("size1","null"); 
        }
    }

    private void showStocks(Model model, Optional<Item> item){
        if(item.get().getStocks() != null && item.get().getStocks()[0] != null){
            model.addAttribute("stock1",item.get().getStocks()[0]);   
        } else {
            model.addAttribute("stock1",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[1] != null){
            model.addAttribute("stock2",item.get().getStocks()[1]);   
        } else {
            model.addAttribute("stock2",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[2] != null){
            model.addAttribute("stock3",item.get().getStocks()[2]);   
        } else {
            model.addAttribute("stock3",0); 
        }
        if(item.get().getStocks() != null && item.get().getStocks()[3] != null){
            model.addAttribute("stock4",item.get().getStocks()[3]);   
        } else {
            model.addAttribute("stock4",0); 
        }
    }*/

    @PostMapping("/{id}/update")
    public String itemUpdating(Model model, Item itemUpdated, @PathVariable Integer id, MultipartFile imageField) throws IOException{

        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            if(!imageField.isEmpty()){
                itemUpdated.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            }
            
            //itemService.update(item.get().getId(), itemUpdated);
            model.addAttribute("status","item updated");
            
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            //showSizes(model, item);
            //showStocks(model, item);
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
            return "edition";
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}/delete")
    public String removeReview(Model model, @PathVariable Integer id){
        Optional<Item> item = itemService.findById(id);
 
        if(item.isPresent()) {
            itemService.deleteById(item.get().getId());
            return "index";
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String itemPage(Model model, @PathVariable Integer id, Pageable page){
        model.addAttribute("status","");
        Optional<Item> item = itemService.findById(id);

        if(item.isPresent()) {

            Page<Stock<?>> stock = stockService.findByItem(item.get(), page);

            List<String> sizes = new ArrayList<String>();
            for(Stock<?> stockAux : stock){
                String size = stockAux.getSize().toString().substring(5);
                sizes.add(size);
            }

            model.addAttribute("code",item.get().getCode());
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            
            //showSizes(model,item);
            //showStocks(model, item);
  
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
            model.addAttribute("sizes",sizes);
            model.addAttribute("stock",stock);
        } else {
            return "error";
        }
        return "edition";
    }
}
