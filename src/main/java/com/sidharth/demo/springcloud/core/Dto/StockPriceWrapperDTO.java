package com.sidharth.demo.springcloud.core.Dto;

import com.sidharth.demo.springcloud.core.validator.IsCorrectStock;

import java.util.List;

@IsCorrectStock
public class StockPriceWrapperDTO {
    List<PriceDTO> priceDTOList;
    PriceDTO priceDTO;
    StockDTO stockDTO;


    public StockPriceWrapperDTO() {
    }

    public StockPriceWrapperDTO(List<PriceDTO> priceDTOList, StockDTO stockDTO) {
        this.priceDTOList = priceDTOList;
        this.stockDTO = stockDTO;
        this.priceDTO=priceDTOList.size()>0 ?
               priceDTOList.get(0): null;
    }

    public StockPriceWrapperDTO(PriceDTO priceDTO, StockDTO stockDTO) {
        this.stockDTO = stockDTO;
        this.priceDTO = priceDTO;
    }

    public List<PriceDTO> getPriceDTOList() {
        return priceDTOList;
    }

    public void setPriceDTOList(List<PriceDTO> priceDTOList) {
        this.priceDTOList = priceDTOList;
    }

    public PriceDTO getPriceDTO() {
        return priceDTO;
    }

    public void setPriceDTO(PriceDTO priceDTO) {
        this.priceDTO = priceDTO;
    }

    public StockDTO getStockDTO() {
        return stockDTO;
    }

    public void setStockDTO(StockDTO stockDTO) {
        this.stockDTO = stockDTO;
    }
}
