package com.svalero.storeapp.domain;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Favourites {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idProduct;
    private String productName;
    private String username;

    public Favourites(long id, long idProduct, String productName, String username) {
        this.id = id;
        this.idProduct = idProduct;
        this.productName = productName;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Favourites{" +
                "id=" + id +
                ", idProduct=" + idProduct +
                ", productName='" + productName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
