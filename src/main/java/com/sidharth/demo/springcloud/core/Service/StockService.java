package com.sidharth.demo.springcloud.core.Service;

import com.sidharth.demo.springcloud.core.Dto.StockDTO;
import com.sidharth.demo.springcloud.core.Model.Stocks;
import com.sidharth.demo.springcloud.exception.DuplicateEntityFoundException;
import com.sidharth.demo.springcloud.exception.EntityNotFoundException;

import java.util.ArrayList;
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
