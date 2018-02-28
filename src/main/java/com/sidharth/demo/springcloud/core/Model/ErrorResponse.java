package com.sidharth.demo.springcloud.core.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author sidharthdash ON 2/26/18
 */


@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ErrorResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}