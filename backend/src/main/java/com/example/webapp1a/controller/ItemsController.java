package com.example.webapp1a.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.webapp1a.model.Clothes;
import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Shoe;
import com.example.webapp1a.model.Size;
import com.example.webapp1a.model.Stock;
import com.example.webapp1a.service.ItemService;
import com.example.webapp1a.service.SizeService;
import com.example.webapp1a.service.StockService;
import com.example.webapp1a.stockEditionFactoryMethod.StockFactory;
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
    public String home(){
        return "new_clothes_stock";//return "index";   
    }    
  
    @GetMapping("/clothes/page")
    public String clothesPage(Model model){
        StockFactoryManager stockFactoryManager = new StockFactoryManager();
        model.addAttribute("genders", stockFactoryManager.getGenders());
        model.addAttribute("types", stockFactoryManager.getFactories().keySet());
        return "new_clothes";
    }

    public void addNewItem(Item item, MultipartFile imageField) throws IOException{
        item.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
        item.setCode(UUID.randomUUID().toString().toUpperCase().substring(0, 7));
        itemService.add(item); 
    }

    @PostMapping("/clothes/new")
    public String newClothesPage(Item item, MultipartFile imageField) throws IOException{
        addNewItem(item, imageField);
        return "new_clothes_stock";
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

    @PostMapping("/clothes/new/stock")
    public String newClothesStockPage(Clothes item, String label, Integer stock) throws IOException{
        List<Item> items = itemService.findAll();
        if(!items.isEmpty()){

            Stock<?> clothes = addNewStock(items.get(0), item, label, stock);
            stockService.addStock((Clothes)clothes);
            //new stock added successfully
            return "new_clothes_stock";
        }
        
        //error adding new stock
        return "error";       
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

    @GetMapping("/{id}/stocks/{index}/delete")
    public String deleteItemStockPage(@PathVariable Integer id, @PathVariable Integer index){
        deleteItemStock(id, index);
        return "new_clothes_stock";
    }

   





    @GetMapping("/shoes/page")
    public String newShoesPage(Model model){
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


    /**
     * 
     * find out the item by the identifier on the stock table
     */
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
    public String removeItem(Model model, @PathVariable Integer id){
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

            //load sizes from sizeRepo
            //Page<Size> sizes = sizeService.findByStock();

            model.addAttribute("addStock",false);
            
            model.addAttribute("code",item.get().getCode());
            model.addAttribute("name",item.get().getName());
            model.addAttribute("price",item.get().getPrice());
            model.addAttribute("gender",item.get().getGender());
  
            model.addAttribute("type",item.get().getType());
            model.addAttribute("description",item.get().getDescription());
            //model.addAttribute("sizes",sizes);
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
