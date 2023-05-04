package com.svalero.storeapp.domain;

public class Product {
    private long id;
    private String name;
    private String descprition;
    private float price;
    private String category;

    public Product(long id, String name, String descprition, float price, String category) {
        this.id = id;
        this.name = name;
        this.descprition = descprition;
        this.price = price;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescprition() {
        return descprition;
    }

    public void setDescprition(String descprition) {
        this.descprition = descprition;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descprition='" + descprition + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}


