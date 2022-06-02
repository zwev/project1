package com.project1.dao;


import com.project1.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDAO extends JpaRepository<Item, Integer> {

    @Query("select i from Item i where itemName = ?1")
    public List<Item> getItemByName(String itemName);
    @Query("select c from Item c where inv < ?1")
    public List<Item> findByInvUnder(int qoh);

    @Query("select i from Item i where price>?1 and price<?2")
    public List<Item> findByPriceRange(int minprice, int maxprice);

    @Query("select i from Item i where price < ?1")
    public List<Item> findByPriceUnder(int price);
}
