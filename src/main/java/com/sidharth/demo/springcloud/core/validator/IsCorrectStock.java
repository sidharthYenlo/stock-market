package com.sidharth.demo.springcloud.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author sidharthdash ON 2/26/18
 */

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { StockPriceWrapperDTOValidator.class })
public @interface IsCorrectStock {
    String message() default "Stock is incorrect";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
