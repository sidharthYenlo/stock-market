package com.sidharth.demo.springcloud.core.ServiceImpl;

import com.sidharth.demo.springcloud.core.Dto.PriceDTO;
import com.sidharth.demo.springcloud.core.Dto.StockDTO;
import com.sidharth.demo.springcloud.core.Model.Price;
import com.sidharth.demo.springcloud.core.Model.Stocks;
import com.sidharth.demo.springcloud.core.Repo.PriceRepo;
import com.sidharth.demo.springcloud.core.Service.PriceService;
import com.sun.jmx.snmp.Timestamp;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepo priceRepo;

    @Override
    public PriceDTO getLatestPrices(long stockId) {
        List<Price> priceList=priceRepo.findById(stockId,1);
        return  priceList.size()>0 ? priceEntityToDTO(priceList.get(0)) : null;
    }

    @Override
    public List<PriceDTO> getAllPrices(long stockId) {
        List<Price> priceList=priceRepo.findAllById(stockId);
        return   priceList.size()>0 ?priceEntityListToDTOList(priceList) : null;
    }

    @Override
    public List<PriceDTO> getLimitedPrices(long stockId,int numberOfEntries) {
        List<Price> priceList=priceRepo.findById(stockId,numberOfEntries);
        return   priceList.size()>0 ?priceEntityListToDTOList(priceList) : null;
    }

    @Override
    public PriceDTO updatePrices(PriceDTO priceDTO,long stockId) {
        Price price = priceDTOtoEntity(priceDTO);
        priceRepo.createPrice(stockId,price.getPrice());
        List<Price> priceList=priceRepo.findById(stockId,1);
        Price updatePrice=priceList.size()>0 ? (priceList.get(0)) : null;
        return priceEntityToDTO(updatePrice);
    }

    @Override
    public List<PriceDTO> getPricesBetweenTime(long startTime, long endTime,long stockId) {
return null;
    }


    @Override
    public PriceDTO priceEntityToDTO(Price price){

        if (null!=price) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            PriceDTO priceDTO = modelMapper.map(price, PriceDTO.class);
            return priceDTO;
        }
        return null;
    }

    @Override
    public Price priceDTOtoEntity(PriceDTO priceDTO){
        if(null!=priceDTO) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            Price price = modelMapper.map(priceDTO, Price.class);
            return price;
        }
        return null;
    }


    @Override
    public List<PriceDTO> priceEntityListToDTOList(List<Price> priceList){

        if(null!=priceList) {
            List<PriceDTO> priceDTOList = new ArrayList<>();
            priceList.forEach(
                    price -> {
                        priceDTOList.add(priceEntityToDTO(price));
                    }
            );
            return priceDTOList;
        }
        return null;
    }
}
