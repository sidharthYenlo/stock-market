package com.sidharth.demo.springcloud.core.Model;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "Price")
public class Price {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
    long stockId;
    double price;
    Timestamp updatedTimeStamp;


    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public void setUpdatedTimeStamp(Timestamp updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public Timestamp getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
