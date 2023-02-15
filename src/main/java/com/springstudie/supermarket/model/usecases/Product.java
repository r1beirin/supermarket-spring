package com.springstudie.supermarket.model.usecases;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameProduct;
    private double valueProduct;
    private String descriptionProduct;
    private LocalDate expirationProductAt;

    public Product(){}

    public Product(String nameProduct, double valueProduct, String descriptionProduct, LocalDate expirationProductAt) {
        this.nameProduct = nameProduct;
        this.valueProduct = valueProduct;
        this.descriptionProduct = descriptionProduct;
        this.expirationProductAt = expirationProductAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public double getValueProduct() {
        return valueProduct;
    }

    public void setValueProduct(double valueProduct) {
        this.valueProduct = valueProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public LocalDate getExpirationProductAt() {
        return expirationProductAt;
    }

    public void setExpirationProductAt(LocalDate expirationProductAt) {
        this.expirationProductAt = expirationProductAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.valueProduct, valueProduct) == 0 && Objects.equals(id, product.id) && Objects.equals(nameProduct, product.nameProduct) && Objects.equals(descriptionProduct, product.descriptionProduct) && Objects.equals(expirationProductAt, product.expirationProductAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameProduct, valueProduct, descriptionProduct, expirationProductAt);
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", nameProduct:'" + nameProduct + '\'' +
                ", valueProduct:" + valueProduct +
                ", descriptionProduct:'" + descriptionProduct + '\'' +
                ", expirationProductAt:" + expirationProductAt +
                '}';
    }
}
