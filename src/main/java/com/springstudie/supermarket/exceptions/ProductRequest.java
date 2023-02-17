package com.springstudie.supermarket.exceptions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;


public class ProductRequest {
    @NotEmpty(message = "Name is obrigatory.")
    @NotNull(message = "Name can't be null.")
    private String nameProduct;
    @PositiveOrZero(message = "The value is positive or zero.")
    @NotNull(message = "Value can't be null.")
    private double valueProduct;
    @NotEmpty(message = "Description of product is obrigatory.")
    @NotNull(message = "Description can't be null.")
    private String descriptionProduct;
    @NotEmpty(message = "Date is expiration product is obrigatory.")
    @NotNull(message = "Date can't be null.")
    private LocalDate expirationProductAt;
}
