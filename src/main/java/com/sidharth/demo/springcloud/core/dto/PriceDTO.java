package com.sidharth.demo.springcloud.core.dto;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.sql.Timestamp;

/**
 * StockServiceImpl Tester.
 *
 * @author <Authors name>
 * @since <pre>Feb 23, 2018</pre>
 * @version 1.0
 */

public class PriceDTO {
    @NotBlank
    @Min(0)
    double price;
    Timestamp updatedTimeStamp;


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(Timestamp updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }
}
