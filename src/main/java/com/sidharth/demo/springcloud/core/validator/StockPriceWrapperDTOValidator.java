package com.sidharth.demo.springcloud.core.validator;

import com.sidharth.demo.springcloud.core.Dto.StockDTO;
import com.sidharth.demo.springcloud.core.Dto.StockPriceWrapperDTO;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author sidharthdash ON 2/26/18
 */
@Component
public class StockPriceWrapperDTOValidator implements ConstraintValidator<IsCorrectStock,StockPriceWrapperDTO> {
    @Override
    public void initialize(IsCorrectStock isCorrectStock) {

    }

    @Override
    public boolean isValid(StockPriceWrapperDTO stockPriceWrapperDTO, ConstraintValidatorContext constraintValidatorContext) {
        if(stockPriceWrapperDTO==null){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Stock values cannot be null")
                    .addPropertyNode("stockDTO").addConstraintViolation();
            return false;
        }
        if(stockPriceWrapperDTO.getStockDTO().getStockCode()==null || stockPriceWrapperDTO.getStockDTO().getStockCode().isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Stock code cannot be empty")
                    .addPropertyNode("stockDTO").addConstraintViolation();
            return false;
        }
        if(stockPriceWrapperDTO.getStockDTO().getStockCode().length()!=3){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Stock code must be three characters")
                    .addPropertyNode("stockDTO").addConstraintViolation();
            return false;
        }
        if(stockPriceWrapperDTO.getStockDTO().getStockName()==null || stockPriceWrapperDTO.getStockDTO().getStockName().isEmpty()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Stock name cannot be empty")
                    .addPropertyNode("stockDTO").addConstraintViolation();
            return false;
        }
        if(stockPriceWrapperDTO.getPriceDTO().getPrice()<0){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Stock price cannot be negative")
                    .addPropertyNode("stockDTO").addConstraintViolation();
            return false;
        }

        return true;
    }
}
