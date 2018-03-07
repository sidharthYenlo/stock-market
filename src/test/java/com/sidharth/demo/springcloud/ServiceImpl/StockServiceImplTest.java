package com.sidharth.demo.springcloud.ServiceImpl;

import com.sidharth.demo.springcloud.core.dto.StockDTO;
import com.sidharth.demo.springcloud.core.model.Stocks;
import com.sidharth.demo.springcloud.core.repo.PriceRepo;
import com.sidharth.demo.springcloud.core.repo.StocksRepo;
import com.sidharth.demo.springcloud.core.service.PriceService;
import com.sidharth.demo.springcloud.core.service.StockService;
import com.sidharth.demo.springcloud.exception.DuplicateEntityFoundException;
import com.sidharth.demo.springcloud.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/** 
* StockServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>Feb 23, 2018</pre> 
* @version 1.0 
*/

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceImplTest {


    @Autowired
    PriceService priceService;
    @Autowired
    StockService stockService;

    @Autowired
    PriceRepo priceRepo;

    @Autowired
    StocksRepo stocksRepo;

    String stockName1 = "IBM";
    String stockName2 = "Cognizant";
    String stockCode1 = "IBM";
    String stockCode2 = "Cog";
    int numberOfEntries=2;

@Before
public void before() throws Exception {
    priceRepo.deleteAll();
    stocksRepo.deleteAll();

    stocksRepo.createStocks(stockName1,stockCode1);
    stocksRepo.createStocks(stockName2,stockCode2);


} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: addStocks(StockDTO stockDTO) 
* 
*/ 
@Test
public void testAddStocks() throws Exception {
    String stockName="Microsoft";
    String stockCode="Mir";
    int stockId=999999999;
    StockDTO stockDTO = new StockDTO();
    stockDTO.setStockName(stockName);
    stockDTO.setStockCode(stockCode);
    stockDTO.setId(stockId);
    stockService.addStocks(stockDTO);
    assertEquals(stocksRepo.findStocksByStockCode(stockCode).getStockCode(),stockCode);
    assertEquals(stocksRepo.findStocksByStockCode(stockCode).getStockName(),stockName);
    assertNotEquals(stocksRepo.findStocksByStockCode(stockCode).getId(),stockId);

    Throwable duplicateEntriesFoundException = assertThrows(DuplicateEntityFoundException.class,() -> {
        throw new DuplicateEntityFoundException(Stocks.class,"Stock Name",stockName);
    });
    try{
        stockService.addStocks(stockDTO);
    }catch (DuplicateEntityFoundException e){
        assertEquals(e.getMessage(),duplicateEntriesFoundException.getMessage());
    }

} 

/** 
* 
* Method: updateStocks(StockDTO stockDTO) 
* 
*/ 
@Test
public void testUpdateStocks() throws Exception {

    //Update Valid Entries
    String stockName="Cong";
    String stockCode="Cog";
    int stockId=999999999;
    StockDTO stockDTO = new StockDTO();
    stockDTO.setStockName(stockName);
    stockDTO.setStockCode(stockCode);
    stockDTO.setId(stockId);
    stockService.updateStocks(stockDTO);
    assertEquals(stocksRepo.findStocksByStockCode(stockCode).getStockCode(),stockCode);
    assertEquals(stocksRepo.findStocksByStockCode(stockCode).getStockName(),stockName);
    assertNotEquals(stocksRepo.findStocksByStockCode(stockCode).getId(),stockId);

    //Update invalid Entries
    String stockNameInvalid="dummy";
    String stockCodeInvalid="dummy";
    StockDTO stockDTOInvalid = new StockDTO();
    stockDTOInvalid.setStockName(stockNameInvalid);
    stockDTOInvalid.setStockCode(stockCodeInvalid);
    stockDTOInvalid.setId(stockId);
    Throwable entityNotFoundException = assertThrows(EntityNotFoundException.class,() -> {
        throw new EntityNotFoundException(Stocks.class,"Stock Code",stockCodeInvalid);
    });
    try {
        stockService.updateStocks(stockDTO);
    }catch (EntityNotFoundException e){
        assertEquals("Stocks was not found for parameters {Stock Code=dummy}",entityNotFoundException.getMessage());
    }


} 

/** 
* 
* Method: getStocksById(long id) 
* 
*/ 
@Test
public void testGetStocksById() throws Exception {
    int stockId=999999999;
    Throwable entityNotFoundException = assertThrows(EntityNotFoundException.class,() -> {
        throw new EntityNotFoundException(Stocks.class,"Stock Id",String.valueOf(stockId));
    });
    try{
        stockService.getStocksById(stockId);
    }catch (EntityNotFoundException e){
        assertEquals("Stocks was not found for parameters {Stock Id=999999999}",entityNotFoundException.getMessage());
    }
    assertEquals(stockService.getStocksById(1).getStockCode(),stockCode1);
    assertEquals(stockService.getStocksById(2).getStockCode(),stockCode2);

} 

/** 
* 
* Method: getAllStocks() 
* 
*/ 
@Test
public void testGetAllStocks() throws Exception {

    assertEquals(stockService.getAllStocks().size(),numberOfEntries);


} 

/** 
* 
* Method: getStocksByName(String stockName) 
* 
*/ 
@Test
public void testGetStocksByName() throws Exception {
    String randomName="randomName";
    assertEquals(stockService.getStocksByName(stockName1).getStockName(),stockName1);
    assertEquals(stockService.getStocksByName(stockName2).getStockName(),stockName2);

    Throwable entityNotFoundException = assertThrows(EntityNotFoundException.class,() -> {
        throw new EntityNotFoundException(Stocks.class,"Stock Name",String.valueOf(randomName));
    });
    try{
        stockService.getStocksByName(randomName);
    }catch (EntityNotFoundException e){
        assertEquals("Stocks was not found for parameters {Stock Name=randomName}",entityNotFoundException.getMessage());
    }

}


    /**
     *
     * Method: deleteStock(StockDTO stockDTO)
     *
     */
    @Test
    public void testDeleteStock() throws Exception {
        StockDTO stockDTO1 = new StockDTO();
        StockDTO stockDTO2 = new StockDTO();

        stockDTO1.setStockCode(stockCode1);
        stockDTO1.setStockName(stockName1);
        stockDTO2.setStockCode(stockCode2);
        stockDTO2.setStockName(stockName2);

        stockService.deleteStock(stockDTO1);
        assertNull(stocksRepo.findStocksByStockName(stockDTO1.getStockName()));
        assertNotNull(stockService.getStocksByName(stockDTO2.getStockName()));

        Throwable entityNotFoundException = assertThrows(EntityNotFoundException.class,() -> {
            throw new EntityNotFoundException(Stocks.class,"Stock Name",String.valueOf(stockName1));
        });
        try{
            stockService.getStocksByName(stockName1);
        }catch (EntityNotFoundException e){
            assertEquals("Stocks was not found for parameters {Stock Name="+stockName1+"}",entityNotFoundException.getMessage());
        }
    }


    /**
* 
* Method: stockEntityToDTO(Stocks stocks) 
* 
*/ 
@Test
public void testStockEntityToDTO() throws Exception {
    Stocks stocks1 = new Stocks();
    Stocks stocks2 = new Stocks();

    stocks1.setStockCode(stockCode1);
    stocks1.setStockName(stockName1);
    stocks2.setStockCode(stockCode2);
    stocks2.setStockName(stockName2);

    assertEquals(stockService.stockEntityToDTO(stocks1).getStockCode(),stocks1.getStockCode());
    assertEquals(stockService.stockEntityToDTO(stocks2).getStockCode(),stocks2.getStockCode());
    assertNull(stockService.stockEntityToDTO(null));

} 

/** 
* 
* Method: stockDTOtoEntity(StockDTO stockDTO) 
* 
*/ 
@Test
public void testStockDTOtoEntity() throws Exception {

    StockDTO stockDTO1 = new StockDTO();
    StockDTO stockDTO2 = new StockDTO();

    stockDTO1.setStockCode(stockCode1);
    stockDTO1.setStockName(stockName1);
    stockDTO2.setStockCode(stockCode2);
    stockDTO2.setStockName(stockName2);

    assertEquals(stockService.stockDTOtoEntity(stockDTO1).getStockCode(),stockDTO1.getStockCode());
    assertEquals(stockService.stockDTOtoEntity(stockDTO2).getStockCode(),stockDTO2.getStockCode());
    assertNull(stockService.stockDTOtoEntity(null));

} 

} 
