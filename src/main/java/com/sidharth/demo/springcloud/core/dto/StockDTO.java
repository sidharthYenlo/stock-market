package com.sidharth.demo.springcloud.core.dto;

import org.hibernate.validator.constraints.NotBlank;


public class StockDTO {

    long id;
    @NotBlank
    String stockName;
    @NotBlank
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
