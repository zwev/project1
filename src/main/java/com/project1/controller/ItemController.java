package com.project1.controller;

import com.project1.dao.ItemDAO;
import com.project1.model.Item;
import com.project1.services.ItemService;
import com.project1.util.NegCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    @Autowired(required = true)
    ItemService itemService;
    @Autowired()
    ItemDAO itemDAO;
    @Autowired()
    Item item;
    //@Autowired()        //required = false keeps autowired from throwing an exception
    //PasswordHashing passwordHashing;
    //@Autowired()
    //GenerateRandomNumber randomNumber;
    @Autowired()
    NegCheck negCheck;


    public ItemController(){
        System.out.println("Item controller called");
    }
    @PostConstruct //lifecycle method called after constructor called
    public void callMeFirst(){
        System.out.println("PostConstruct calls me right after construction");
    }
    @PreDestroy //lifecycle method called when GC runs
    public void callMeLast(){
        System.out.println("PreDestroy calls me last");
    }
    @GetMapping("/home")
    public String home(){
        return "Welcome to the BuhhStore!! Please enter your credentials";
    }
    @GetMapping("{PiD}")
    public ResponseEntity<Item> getProduct(@PathVariable("PiD")int itemId){
        ResponseEntity responseEntity = null;
        Item product1 = new Item();
        if(itemService.isItem(itemId)){
            product1 = itemService.getItem(itemId);
            responseEntity = new ResponseEntity<String>("Successfully located products at ID :" + item.getItemId()+"\n"+product1.toString(), HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>("Cannot find product at ID: "+item.getItemId()+", check that it exists.", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @GetMapping("byname/{productName}")
    public ResponseEntity<Item> getProduct(@PathVariable("itemName")String itemName){
        ResponseEntity responseEntity = null;
        List<Item> item1 = new ArrayList<>();
        if(itemService.getItem(itemName).size()>0){
            item1 = itemService.getItem(itemName);
            responseEntity = new ResponseEntity<String>("Successfully located your products :" + item.getItemId()+"\n"+item1.toString(), HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>("Cannot find products with name : "+itemName+", check that it exists.", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    //@GetMapping("/price/{plow}/{phigh}")
    //public Item getItem(@PathVariable("plow")int plow, @PathVariable("phigh") int phigh){
        //System.out.println("fetching products between "+plow+" and "+phigh);
        //Item item = new Item(-1,"buhh", 23,(phigh+plow/2));
      //  return item;
    //}
    @GetMapping("priceunder/{price}")
    public ResponseEntity<Item> getlowPrice(@PathVariable("price")int price){
        ResponseEntity responseEntity = null;
        List<Item> item1 = new ArrayList<>();
        if(itemService.findByPriceUnder(price).size()>0){
            item1 = itemService.getItemUnderQoh(price);                                            //add lambda function for \n after every list element - Iterator class?
            responseEntity = new ResponseEntity<String>("Successfully located low inv Products :" +"\n"+item1.toString(), HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>("Cannot find products with QoH under : "+price+", check that they need restocking.", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @GetMapping("lowinv/{qoh}")
    public ResponseEntity<Item> getlowProduct(@PathVariable("qoh")int qoh){
        ResponseEntity responseEntity = null;
        List<Item> item1 = new ArrayList<>();
        if(itemService.getItemUnderQoh(qoh).size()>0){
            item1 = itemService.getItemUnderQoh(qoh);                                            //add lambda function for \n after every list element - Iterator class?
            responseEntity = new ResponseEntity<String>("Successfully located low inv Products :" +"\n"+item1.toString(), HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>("Cannot find products with QoH under : "+qoh+", check that they need restocking.", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @DeleteMapping("/delete/{PiD}")                 //DELETE - cannot call from browser
    public ResponseEntity<String> deleteProduct(@PathVariable("PiD")int PiD){
        boolean result = itemService.deleteItem(PiD);
        ResponseEntity responseEntity = null;
        if(result){
            responseEntity = new ResponseEntity<String>("Successfully Deleted your product:" + item.getItemId(), HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<String>("Cannot delete product at ID: "+item.getItemId()+", check that it exists.", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @PostMapping("/add")                   //Post
    public ResponseEntity<String> addProduct(@RequestBody Item item){
        boolean result = itemService.addItem(item);
        LOGGER.info("Info - Attempting to write to DB");
        ResponseEntity responseEntity = null;
        if (result) {
            LOGGER.info("Data Successfully Stored");
            responseEntity = new ResponseEntity("Successfully added product "+item.getItemName()+" to the DB", HttpStatus.OK);
        } else {
            LOGGER.warn("Data Entry Error");
            responseEntity = new ResponseEntity("Cant add "+item.getItemName()+" check that Price/QoH are valid and ID is empty", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
    @PutMapping("{PiD}")                 //PUT
    public ResponseEntity<String> updateProduct(@RequestBody Item item) {
        boolean result = itemService.updateItem(item);
        ResponseEntity responseEntity = null;
        if (result) {
            responseEntity = new ResponseEntity<String>
                    ("Successfully Saved your product:" + item.getItemId(), HttpStatus.OK); //send 202 with response
        } else {
            responseEntity = new ResponseEntity<String>
                    ("Cannot update product at ID: "+item.getItemId()+", check that it exists and Price/QoH are valid", HttpStatus.NOT_ACCEPTABLE);//send 404
        }
        return responseEntity;
    }
}
