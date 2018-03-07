package com.sidharth.demo.springcloud.core.serviceimpl;

import com.sidharth.demo.springcloud.core.dto.PriceDTO;
import com.sidharth.demo.springcloud.core.model.Price;
import com.sidharth.demo.springcloud.core.repo.PriceRepo;
import com.sidharth.demo.springcloud.core.service.PriceService;
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
        return  priceList.isEmpty() ?  new PriceDTO() :priceEntityToDTO(priceList.get(0)) ;
    }

    @Override
    public List<PriceDTO> getAllPrices(long stockId) {
        List<Price> priceList=priceRepo.findAllById(stockId);
        return   priceList.isEmpty() ?new ArrayList<>():priceEntityListToDTOList(priceList);
    }

    @Override
    public List<PriceDTO> getLimitedPrices(long stockId,int numberOfEntries) {
        List<Price> priceList=priceRepo.findById(stockId,numberOfEntries);
        return   priceList.isEmpty() ? new ArrayList<>():priceEntityListToDTOList(priceList);
    }

    @Override
    public PriceDTO updatePrices(PriceDTO priceDTO,long stockId) {
        Price price = priceDTOtoEntity(priceDTO);
        priceRepo.createPrice(stockId,price.getPrice());
        List<Price> priceList=priceRepo.findById(stockId,1);
        Price updatePrice=priceList.isEmpty() ?  null : (priceList.get(0));
        return priceEntityToDTO(updatePrice);
    }

    @Override
    public List<PriceDTO> getPricesBetweenTime(long startTime, long endTime,long stockId) throws UnsupportedOperationException{
      return new ArrayList<>();
    }


    @Override
    public PriceDTO priceEntityToDTO(Price price){

        if (null!=price) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(price, PriceDTO.class);
        }
        return new PriceDTO();
    }

    @Override
    public Price priceDTOtoEntity(PriceDTO priceDTO){
        if(null!=priceDTO) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(priceDTO, Price.class);
        }
        return new Price();
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
        return new ArrayList<>();
    }
}
