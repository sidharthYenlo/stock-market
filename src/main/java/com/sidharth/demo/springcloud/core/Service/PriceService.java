package com.sidharth.demo.springcloud.core.Service;


import com.sidharth.demo.springcloud.core.Dto.PriceDTO;
import com.sidharth.demo.springcloud.core.Model.Price;
import com.sun.jmx.snmp.Timestamp;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
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
