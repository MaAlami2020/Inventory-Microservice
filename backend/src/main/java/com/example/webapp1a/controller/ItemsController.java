package com.example.webapp1a.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigData.Option;
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

import com.example.webapp1a.model.Item;
import com.example.webapp1a.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    public void addAttribute(Model model, HttpServletRequest request){
        model.addAttribute("sizeS",false);
        model.addAttribute("sizeM",false);
        model.addAttribute("sizeL",false);
        model.addAttribute("sizeXL",false);
    }
    
    @GetMapping("/")
    public String home(Model model){
        return "index";
    }    
  
    @GetMapping("/page")
    public String newItemPage(Model model){
        model.addAttribute("status","");
        return "new_item";
    }

    @PostMapping("/new")
    public String newItem(Model model, Item item, MultipartFile imageField) throws IOException{
        /*if(item.getSize().equals("S")){
            item.setSizes(0, "S");
        }
        if(item.getSize().equals("M")){
            item.setSizes(1, "M");
        }
        if(item.getSize().equals("L")){
            item.setSizes(2, "L");
        }
        if(item.getSize().equals("XL")){
            item.setSizes(3, "XL");
        }

        if(item.getStock() > 5){
            model.addAttribute("stock","In stock");
        } else {
            model.addAttribute("stock","Only 5 items left");
        }*/
        item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        itemService.add(item);
        model.addAttribute("status","item created"); 
        return "new_item";
    }

    private void showSizes(Model model, Optional<Item> item){
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
    }

    @PostMapping("/{id}/update")
    public String itemUpdating(Model model, Item itemUpdated, @PathVariable Integer id, MultipartFile imageField) throws IOException{

        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            if(!imageField.isEmpty()){
                itemUpdated.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            }
            
            itemService.update(item.get().getId(), itemUpdated);
            model.addAttribute("status","item updated");
            
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            showSizes(model, item);
            showStocks(model, item);
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
    public String itemPage(Model model, @PathVariable Integer id){
        model.addAttribute("status","");
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()) {
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            
            showSizes(model,item);
            showStocks(model, item);
  
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
        } else {
            return "error";
        }
        return "edition";
    }
}
