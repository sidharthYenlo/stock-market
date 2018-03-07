package com.sidharth.demo.springcloud.core.serviceimpl;

import com.sidharth.demo.springcloud.core.dto.StockDTO;
import com.sidharth.demo.springcloud.core.model.Stocks;
import com.sidharth.demo.springcloud.core.repo.StocksRepo;
import com.sidharth.demo.springcloud.core.service.StockService;
import com.sidharth.demo.springcloud.exception.DuplicateEntityFoundException;
import com.sidharth.demo.springcloud.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StocksRepo stocksRepo;


    @Override
    public StockDTO addStocks(StockDTO stockDTO) throws DuplicateEntityFoundException{
        Stocks stocks = stockDTOtoEntity(stockDTO);
        if(null!=stocks) {
            if(null== stocksRepo.findStocksByStockCode(stocks.getStockCode())) {
                stocksRepo.createStocks(stocks.getStockName(), stocks.getStockCode());
                return stockEntityToDTO(stocksRepo.findStocksByStockCode(stocks.getStockCode()));
            }else {
                throw new DuplicateEntityFoundException(Stocks.class,"Stock Name",stocks.getStockName());
            }
        }
        return null;
    }

    @Override
    public StockDTO updateStocks(StockDTO stockDTO) throws EntityNotFoundException{
        Stocks stocks = stockDTOtoEntity(stockDTO);
        if(null!=stocks) {
            if(null!=stocksRepo.findStocksByStockCode(stocks.getStockCode())) {
                stocksRepo.updateStockName(stocks.getStockName(), stocksRepo.findStocksByStockCode(stocks.getStockCode()).getId());
                return stockEntityToDTO(stocksRepo.findStocksByStockCode(stocks.getStockCode()));
            }else {
                throw new EntityNotFoundException(Stocks.class,"Stock Code",stocks.getStockCode());
            }
        }
        return null;
    }

    @Override
    public StockDTO getStocksById(long id) throws EntityNotFoundException{
        if(null!=stockEntityToDTO(stocksRepo.findById(id))) {
            return stockEntityToDTO(stocksRepo.findById(id));
        }else{
            throw new EntityNotFoundException(Stocks.class,"Stock Id",String.valueOf(id));
        }
    }

    @Override
    public List<StockDTO> getAllStocks() {
        List<Stocks> stocksList=stocksRepo.findAll();
        List<StockDTO> stocksDTOList = new ArrayList<>();
        stocksList.forEach(
                stocks -> stocksDTOList.add(stockEntityToDTO(stocks))
        );

        return stocksDTOList;
    }


    @Override
    public StockDTO getStocksByName(String stockName) throws EntityNotFoundException{
        if(null!=stockName && !stockName.isEmpty()) {
            if(null!=stocksRepo.findStocksByStockName(stockName)) {
                return stockEntityToDTO(stocksRepo.findStocksByStockName(stockName));
            }else{
                throw new EntityNotFoundException(Stocks.class,"Stock Name",stockName);
            }
        }
        return null;
    }

    @Override
    public void deleteStock(StockDTO stockDTO) throws  EntityNotFoundException{
        Stocks stocks = stockDTOtoEntity(stockDTO);
        if(null!=stocks && null != stocksRepo.findStocksByStockCode(stocks.getStockCode())) {
            stocksRepo.removeStock(stocks.getStockCode());
        }else {
            throw new EntityNotFoundException(Stocks.class,"Stock Code",stockDTO.getStockCode());
        }
    }

    public StockDTO stockEntityToDTO(Stocks stocks){

        if (null!=stocks) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(stocks, StockDTO.class);

        }
        return null;
    }

    public Stocks stockDTOtoEntity(StockDTO stockDTO){
        if(null!=stockDTO) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(stockDTO, Stocks.class);

        }
        return null;
    }
}
