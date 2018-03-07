package com.sidharth.demo.springcloud.core.service;


import com.sidharth.demo.springcloud.core.dto.PriceDTO;
import com.sidharth.demo.springcloud.core.model.Price;
import java.util.List;

public interface PriceService {

    public PriceDTO getLatestPrices(long stockId);
    public List<PriceDTO> getAllPrices(long stockId);
    public List<PriceDTO> getLimitedPrices(long stockId,int numberOfEntries);
    public PriceDTO updatePrices(PriceDTO priceDTO,long stockId);
    public List<PriceDTO> getPricesBetweenTime(long startTime,long endTime,long stockId);
    public PriceDTO priceEntityToDTO(Price price);
    public Price priceDTOtoEntity(PriceDTO priceDTO);
    public List<PriceDTO> priceEntityListToDTOList(List<Price> priceList);
}
