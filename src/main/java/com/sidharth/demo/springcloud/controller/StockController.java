package com.sidharth.demo.springcloud.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidharth.demo.springcloud.core.Dto.PriceDTO;
import com.sidharth.demo.springcloud.core.Dto.StockDTO;
import com.sidharth.demo.springcloud.core.Dto.StockPriceWrapperDTO;
import com.sidharth.demo.springcloud.core.Model.ErrorResponse;
import com.sidharth.demo.springcloud.core.Model.Price;
import com.sidharth.demo.springcloud.core.ServiceImpl.PriceServiceImpl;
import com.sidharth.demo.springcloud.core.ServiceImpl.StockServiceImpl;
import com.sidharth.demo.springcloud.exception.DuplicateEntityFoundException;
import com.sidharth.demo.springcloud.exception.EntityNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Controller
@RequestMapping("/stocks")
public class StockController {
    @Autowired
    StockServiceImpl stockService;
    @Autowired
    PriceServiceImpl priceService;

    @Autowired
    private ObjectMapper objectMapper;
    private static Log logger = LogFactory.getLog(StockController.class);

    @RequestMapping( value = "/add",method = RequestMethod.POST)
    public @ResponseBody StockPriceWrapperDTO addStock (@Valid @RequestBody StockPriceWrapperDTO stockPriceWrapperDTO) throws DuplicateEntityFoundException{

            //StockPriceWrapperDTO stockPriceWrapperDTO = objectMapper.readValue(stockPriceWrapperDTOJson, StockPriceWrapperDTO.class);
            StockDTO stockDTO = stockService.addStocks(stockPriceWrapperDTO.getStockDTO());
            PriceDTO priceDTO = priceService.updatePrices(stockPriceWrapperDTO.getPriceDTO(),stockDTO.getId());
             return new StockPriceWrapperDTO( priceDTO,stockDTO);
    }


    @RequestMapping(value = "/put/{stockId}",method = RequestMethod.PUT)
    public @ResponseBody
    StockPriceWrapperDTO updateStock (@PathVariable("stockId") long stockId,@RequestBody StockPriceWrapperDTO stockPriceWrapperDTO) throws EntityNotFoundException{
            StockDTO stockDTO = stockService.getStocksById(stockId);
            if(null!=stockDTO) {
                PriceDTO priceDTO = priceService.updatePrices(stockPriceWrapperDTO.getPriceDTO(), stockId);
                if (null != priceDTO)
                    return new StockPriceWrapperDTO(priceDTO, stockDTO);
            }
            return null;

    }


    @RequestMapping(value = "/get/{stockId}/history",method = RequestMethod.GET)
    public @ResponseBody
    StockPriceWrapperDTO getStockAllPrice (@PathVariable("stockId") long stockId) throws EntityNotFoundException{

        StockDTO stockDTO = stockService.getStocksById(stockId);
        List<PriceDTO> priceDTOList=priceService.getAllPrices(stockId);
        if(null!=stockDTO & null!=priceDTOList)
            return new StockPriceWrapperDTO(priceDTOList,stockDTO);

        return null;
    }


    @RequestMapping(value = "/get/{stockId}/history/{startTime}/{endTime}",method = RequestMethod.GET)
    public @ResponseBody
    StockPriceWrapperDTO getStockAllPriceBetweenTime (@PathVariable("stockId") long stockId
    ,@PathVariable("startTime") long startTime,@PathVariable("endTime") long endTime) throws EntityNotFoundException{
        StockDTO stockDTO = stockService.getStocksById(stockId);
        List<PriceDTO> priceDTOList=priceService.getPricesBetweenTime(startTime,endTime,stockId);
        if(null!=stockDTO & null!=priceDTOList)
         return new StockPriceWrapperDTO(priceDTOList,stockDTO);

        return null;
    }


    @RequestMapping(value = "/get/{stockId}",method = RequestMethod.GET)
    public @ResponseBody
    StockPriceWrapperDTO getStock (@PathVariable("stockId") long stockId) throws EntityNotFoundException{
        StockDTO stockDTO=stockService.getStocksById(stockId);
        PriceDTO priceDTO=priceService.getLatestPrices(stockId);
        if(null!=stockDTO & null!=priceDTO)
        return new StockPriceWrapperDTO(priceDTO,stockDTO);

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
