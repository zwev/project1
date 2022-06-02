package com.project1.services;

import com.project1.model.Item;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ItemService {
    public boolean isItem(int productId);

    public Item getItem(int productId);

    public List<Item> getItems();

    public List<Item> getItem(String itemName);

    //List<Item> filterItemPrice(int minprice, int maxprice);

    List<Item> findByPriceUnder(@Param("price") int price);

    public List<Item> getItemUnderQoh(int qoh);

    public boolean deleteItem(int piD);

    public boolean addItem(Item item);

    public boolean updateItem(Item item);
}
