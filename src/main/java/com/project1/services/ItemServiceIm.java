package com.project1.services;

import com.project1.controller.ItemController;
import com.project1.dao.ItemDAO;
import com.project1.model.Item;
import com.project1.util.NegCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceIm implements ItemService{
    private static final Logger log = LoggerFactory.getLogger(ItemController.class);
    @Autowired()
    ItemDAO itemDAO;
    @Autowired()
    NegCheck negCheck;
    @Autowired @Override
    public boolean addItem(Item item) {
        if(negCheck.negcheck(item.getInv(), item.getPrice())==true){
            System.out.println("adding product "+item.getItemName());
            itemDAO.save(item);
            return true;
        }
        else return false;
    }
    @Override
    public boolean deleteItem(int productId) {
        if(itemDAO.existsById(productId)){
            itemDAO.deleteById(productId);
            return true;
        }
        else return false;
    }
    @Override
    public boolean updateItem(Item item) {
        if(negCheck.negcheck(item.getInv(), item.getPrice())==true && itemDAO.existsById(item.getItemId())){
            System.out.println("adding product "+item.getItemName());
            itemDAO.save(item);
            return true;
        }
        else return false;
    }
    @Override
    public Item getItem(int itemId) {
        Item pr = itemDAO.getReferenceById(itemId);
        return pr;
    }
    @Override
    public boolean isItem(int itemId) {
        return itemDAO.existsById(itemId);
    }
    @Override
    public List<Item> getItems() {
        return itemDAO.findAll();
    }
    //not created by default
    @Override
    public List<Item> getItem(String itemName) {
        return itemDAO.getItemByName(itemName);
    }
    //@Override
    //public List<Item> filterItemPrice(int minprice, int maxprice) {
    //    return itemDAO.findByPriceRange(minprice, maxprice);
    //}

    @Override
    public List<Item> findByPriceUnder(int price) {
        return itemDAO.findByPriceUnder(price);
    }

    @Override
    public List<Item> getItemUnderQoh(int inv) {
        return itemDAO.findByInvUnder(inv);
    }
}
