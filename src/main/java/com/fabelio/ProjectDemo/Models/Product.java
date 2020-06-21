package com.fabelio.ProjectDemo.Models;

import java.math.BigDecimal;

public class Product {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Dimension dimension;
    private String[] color;
    private String material;
    private Integer stock;
    private String imagePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Product() {
        super();
    }

    public Product(Integer id, String name, BigDecimal price, Dimension dimension, String[] color, String material, Integer stock, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dimension = dimension;
        this.color = color;
        this.material = material;
        this.stock = stock;
        this.imagePath = imagePath;
    }
}
