package com.sidharth.demo.springcloud.core.Model;

import javax.persistence.*;

@Entity
@Table(name = "Stocks")
public class Stocks {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
    String stockName;
    String stockCode;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
