package com.project1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name="order", schema="public")
public class Order {
    @Id
    private int Id;
    private int userId;
    private int itemId;
    private String country;

    //@ManyToOne()
    //@JoinTable(name = "orderlist", joinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemId"), inverseJoinColumns =@JoinColumn(name="", name="" ))
    //private List<Order> orderList;

}
