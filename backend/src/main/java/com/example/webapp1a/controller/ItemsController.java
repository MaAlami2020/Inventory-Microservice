package com.example.webapp1a.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.service.ItemService;
import com.example.webapp1a.service.StockService;
import com.example.webapp1a.sizeFactoryMethod.Size;
import com.example.webapp1a.stockEditionFactoryMethod.StockFactory;
import com.example.webapp1a.stockEditionFactoryMethod.StockFactoryManager;



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
        model.addAttribute("addStock",false);
    }
    
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("addStockC",false);
        model.addAttribute("addStockS",false);
        model.addAttribute("addStock",false);
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
        if(!item.isEmpty()){
        //get those clothes stocks whose id_item is null
        //Page<Clothes> clothesStock = stockService.findAllClothes(page);
        //for(Clothes c: clothesStock) {
            //check the size
            //if(clothes.getSize().equals(c.getSize())){
                //check the stock entered is under the avaialable, if so, save the new stock object into the db and reload the clothes page
                //if(clothes.getStock() <=  c.getStock()){

                    //filtering of the last item keeped in the db
                    clothes.setItem(item.get(0));
                    stockService.addClothes(clothes);
                    model.addAttribute("addStockC",true); 
                    //new stock added successfully
                    return "new_clothes";
        }
        
                //}
            //}
        //}
        //check the stock entered is under the avaialable, if not, do not save the new stock object into the db and return to the main page 
        model.addAttribute("addStockC",false); 
        //error adding new stock
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
        if(!item.isEmpty()){
        //get those shoe stocks whose id_item is null
        //Page<Shoe> shoeStock = stockService.findAllShoe(page);
        //for(Shoe s: shoeStock) {
            //check the size
            //if(shoe.getSize().equals(s.getSize())){
                //check the stock value entered is lower than the max avaialability, if so, save the new stock object into the db and reload the clothes page
                //if(shoe.getStock() <=  s.getStock()){
                    shoe.setItem(item.get(0));
                    stockService.addShoe(shoe);
                    model.addAttribute("addStockS",true); 
                    return "new_shoes";
                //}
            //}
        }
        //check the stock entered is under the avaialable, if not, do not save the new stock object into the db and return to the main page 
        model.addAttribute("addStockS",false); 
        return "index";     
    }

    /** 
     * se cumple el principio SOLID Abierto/Cerrado (OCP):
     * Agregar una nueva propiedad implica crear una nueva clase de estrategia sin modificar las existentes.

    
     * se cumple el principio SOLID de Responsabilidad Única (SRP):
     * Cada clase de estrategia maneja exclusivamente la actualización de un atributo.

     * se cumple la propiedad de escalabilidad:
     * Fácil de extender cuando se añaden nuevos campos del objeto Item en el futuro.

     * se cumple la propiedad de antenibilidad:
     * La lógica de cada campo está separada y es independiente
    */
    @PostMapping("/{id}/update")
    public String itemUpdating(Model model, Item itemUpdated, @PathVariable Integer id, MultipartFile imageField) throws IOException{

        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()){
            if(imageField != null && !imageField.isEmpty()){
                itemUpdated.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
                item.get().setImageFile(itemUpdated.getImageFile());
            }
            
            itemService.save(item.get(), itemUpdated);
            
            model.addAttribute("addStock",true); 
            model.addAttribute("status","item updated");
            
            model.addAttribute("code",item.get().getCode());
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
       
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
            return "edition";
        } else {
            return "error";
        }
    }

    @PostMapping("/{id}/stock/update")
    public String itemStockUpdating(Model model, String code, Size size, Integer stock, @PathVariable Integer id, Pageable page) {

        //looking for the item based on the id
        //apply the stock object info to the item founded
        Optional<Item> item = itemService.findById(id);
        if(item.isPresent()) {

            Page<Stock<?>> stocks = stockService.findByItem(item.get(), page);

            List<String> sizes = new ArrayList<String>();
            String auxSize="";
            model.addAttribute("shoe",false);
            for(Stock<?> stockAux : stocks){
                /*if(stockAux.getSize().toString().length()>=5){
                    //numeric sizes will begin by SIZE_, the rest with its normal name
                    model.addAttribute("shoe",true);
                    size = stockAux.getSize().toString().substring(5);
                } else {
                    size = stockAux.getSize().toString(); 
                }
                sizes.add(size);*/
            }

            model.addAttribute("addStock",false);
            
            model.addAttribute("code",item.get().getCode());
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
  
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
            model.addAttribute("sizes",sizes);
            model.addAttribute("stock",stocks);

            //create the particular stock object
            Stock<?> concreteStock = StockFactoryManager.createStock(item.get().getType(), item.get(), code, size, stock);
            //save stock object into the db
            stockService.addStock(concreteStock);
            model.addAttribute("addStock",true);
            return "edition";
        }
        model.addAttribute("addStock",false);
        return "index";
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
            String size="";
            model.addAttribute("shoe",false);
            for(Stock<?> stockAux : stock){
                /*if(stockAux.getSize().toString().length()>=5){
                    //numeric sizes will begin by SIZE_, the rest with its normal name
                    model.addAttribute("shoe",true);
                    size = stockAux.getSize().toString().substring(5);
                } else {
                    size = stockAux.getSize().toString(); 
                }
                sizes.add(size);*/
            }

            model.addAttribute("addStock",false);
            
            model.addAttribute("code",item.get().getCode());
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
  
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
            model.addAttribute("sizes",sizes);
            model.addAttribute("stock",stock);

            StockFactoryManager stockFactoryManager = new StockFactoryManager();
            //variable that contains the factories mapping
            Map<String, StockFactory> factories = stockFactoryManager.getFactories();
            //change from mapping to listing to show them in the html file
            List<String> auxFactories = new ArrayList<String>();

            for(String auxFactory: factories.keySet()){
                auxFactories.add(auxFactory);
            }
            model.addAttribute("factories",auxFactories);
            
        } else {
            return "error";
        }
        return "edition";
    }
}
