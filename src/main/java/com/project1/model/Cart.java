package com.project1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name="cart", schema="public")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int cartId;
    private double total;
    @ManyToMany
    @JoinTable(name = "cartlist",
            joinColumns = @JoinColumn(name = "cartId", referencedColumnName = "cartId"),
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemId"))
    private List<Item> itemList;

}
