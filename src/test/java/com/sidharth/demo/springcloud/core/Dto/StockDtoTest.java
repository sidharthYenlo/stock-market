package com.sidharth.demo.springcloud.core.Dto;

import com.sidharth.demo.springcloud.core.Model.Price;
import com.sidharth.demo.springcloud.core.Model.Stocks;
import com.sun.jmx.snmp.Timestamp;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;

public class StockDtoTest {
    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertStockEntityToStockDto_thenCorrect() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Price price = new Price();
        price.setId(1);
        price.setPrice(123.122);


        Stocks stocks = new Stocks();
        stocks.setId(1);
        stocks.setStockCode("Mir");
        stocks.setStockName("Microsoft");



        ArrayList<Price> priceArrayList = new ArrayList<>();
        priceArrayList.add(price);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        StockDTO stockDTO= modelMapper.map(stocks, StockDTO.class);


        assertEquals(stockDTO.getId(),stocks.getId());
        assertEquals(stockDTO.getStockName(),stocks.getStockName());


    }
    @Test
    public void whenConvertStockDtoToStockEntity_thenCorrect() {


        StockDTO stockDTO = new StockDTO();
        stockDTO.setStockName("Mircosoft");
        stockDTO.setStockCode("Mir");
        stockDTO.setId(1);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Stocks stocks= modelMapper.map(stockDTO, Stocks.class);


        assertEquals(stockDTO.getId(),stocks.getId());
        assertEquals(stockDTO.getStockName(),stocks.getStockName());



    }

}
