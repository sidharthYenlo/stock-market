package com.sidharth.demo.springcloud.core.service;

import com.sidharth.demo.springcloud.core.dto.StockDTO;
import com.sidharth.demo.springcloud.core.model.Stocks;
import com.sidharth.demo.springcloud.exception.DuplicateEntityFoundException;
import com.sidharth.demo.springcloud.exception.EntityNotFoundException;

import java.util.List;

public interface StockService {
    public List<StockDTO> getAllStocks();
    public StockDTO addStocks(StockDTO stockDTO) throws DuplicateEntityFoundException;
    public StockDTO updateStocks(StockDTO stockDTO) throws EntityNotFoundException;
    public StockDTO getStocksById(long id) throws EntityNotFoundException;
    public StockDTO getStocksByName(String stockName) throws EntityNotFoundException;
    public void deleteStock(StockDTO stockDTO) throws EntityNotFoundException;
    public StockDTO stockEntityToDTO(Stocks stocks);
    public Stocks stockDTOtoEntity(StockDTO stockDTO);


}
