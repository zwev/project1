package com.project1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name="users", schema="public")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;
    private String userName;
    private String email;
    private String country;
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cartId", referencedColumnName = "cartId")
    private Cart cart = new Cart();


    //@OneToMany(mappedBy = "user")
    //@OrderBy("number")
    //public List<Cart> getItems() { return items; }
    //public void setItems(List<Cart> items) { this.items = items; }
    //private List<Cart> items;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name="cartId", referencedColumnName = "cartId")
    //private List<Cart> items;



}
