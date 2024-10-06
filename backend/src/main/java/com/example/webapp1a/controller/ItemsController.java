package com.example.webapp1a.controller;

import java.io.IOException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigData.Option;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/")
    public String home(Model model){
        return "index";
    }    
  
    @GetMapping("/page")
    public String newItem(Model model){
        model.addAttribute("stock","");
        model.addAttribute("status","");
        return "new_item";
    }

    @PostMapping("/new")
    public String newItem(Model model, Item item, MultipartFile imageField) throws IOException{
        if(item.getStock() > 5){
            model.addAttribute("stock","In stock");
        } else {
            model.addAttribute("stock","Only 5 items left");
        }
        item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        itemService.add(item);
        model.addAttribute("status","item created"); 
        return "new_item";
    }

    @PostMapping("/{id}/update")
    public String itemUpdating(Model model, Item itemUpdated, @PathVariable Integer id, MultipartFile imageField) throws IOException{

        Optional<Item> item = itemService.findById(id);
        model.addAttribute("new_stock","");
        if(item.isPresent()){
            if(!imageField.isEmpty()){
                itemUpdated.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
            }
            itemService.update(item.get().getId(), itemUpdated);
            model.addAttribute("status","item updated");

            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            model.addAttribute("size",item.get().getSize());
            model.addAttribute("type",item.get().getType());
            model.addAttribute("stock",item.get().getStock());
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
        model.addAttribute("new_stock","");
        model.addAttribute("status","");
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()) {
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
            model.addAttribute("size",item.get().getSize());
            model.addAttribute("type",item.get().getType());
            model.addAttribute("stock",item.get().getStock());
            model.addAttribute("description",item.get().getDescription());
        } else {
            return "error";
        }
        return "edition";
    }
}
