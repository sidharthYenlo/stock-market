package com.sidharth.demo.springcloud.controller;


import com.sidharth.demo.springcloud.core.dto.PriceDTO;
import com.sidharth.demo.springcloud.core.dto.StockDTO;
import com.sidharth.demo.springcloud.core.dto.StockPriceWrapperDTO;
import com.sidharth.demo.springcloud.core.model.ErrorResponse;
import com.sidharth.demo.springcloud.core.serviceimpl.PriceServiceImpl;
import com.sidharth.demo.springcloud.core.serviceimpl.StockServiceImpl;
import com.sidharth.demo.springcloud.exception.DuplicateEntityFoundException;
import com.sidharth.demo.springcloud.exception.EntityNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Configuration
@Controller
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    StockServiceImpl stockService;
    @Autowired
    PriceServiceImpl priceService;

    private static Log logger = LogFactory.getLog(StockController.class);

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    StockPriceWrapperDTO addStock (@Valid @RequestBody StockPriceWrapperDTO stockPriceWrapperDTO) throws DuplicateEntityFoundException{

            StockDTO stockDTO = stockService.addStocks(stockPriceWrapperDTO.getStockDTO());
            PriceDTO priceDTO = priceService.updatePrices(stockPriceWrapperDTO.getPriceDTO(),stockDTO.getId());
             return new StockPriceWrapperDTO( priceDTO,stockDTO);
    }


    @RequestMapping(value = "/{stockId}",method = RequestMethod.PUT)
    public @ResponseBody
    StockPriceWrapperDTO updateStock (@PathVariable("stockId") long stockId,@RequestBody StockPriceWrapperDTO stockPriceWrapperDTO) throws EntityNotFoundException{
            StockDTO stockDTO = stockService.getStocksById(stockId);
            if(null!=stockDTO) {
                PriceDTO priceDTO = priceService.updatePrices(stockPriceWrapperDTO.getPriceDTO(), stockId);
                if (null != priceDTO)
                    return new StockPriceWrapperDTO(priceDTO, stockDTO);
            }
            if(logger.isDebugEnabled())
            logger.debug("Stock for StockId "+stockId+ " is not found ");

            return null;

    }


    @RequestMapping(value = "/{stockId}/history",method = RequestMethod.GET)
    public @ResponseBody
    StockPriceWrapperDTO getStockAllPrice (@PathVariable("stockId") long stockId) throws EntityNotFoundException{

        StockDTO stockDTO = stockService.getStocksById(stockId);
        List<PriceDTO> priceDTOList=priceService.getAllPrices(stockId);
        if(null!=stockDTO && null!=priceDTOList)
            return new StockPriceWrapperDTO(priceDTOList,stockDTO);

        if(logger.isDebugEnabled())
            logger.debug("Stock for StockId "+stockId+ " is not found ");

        return null;
    }

    @RequestMapping(value = "/{stockId}",method = RequestMethod.GET)
    public @ResponseBody
    StockPriceWrapperDTO getStock (@PathVariable("stockId") long stockId) throws EntityNotFoundException{
        StockDTO stockDTO=stockService.getStocksById(stockId);
        PriceDTO priceDTO=priceService.getLatestPrices(stockId);
        if(null!=stockDTO && null!=priceDTO)
        return new StockPriceWrapperDTO(priceDTO,stockDTO);

        if(logger.isDebugEnabled())
            logger.debug("Stock for StockId "+stockId+ " is not found ");
        return null;
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<StockDTO> getAllStock (){
        return stockService.getAllStocks();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {

        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(exception.getMessage());
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setMessage(errorMsg);

        return errorResponse;
    }



}
