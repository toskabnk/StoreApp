package com.svalero.storeapp.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Review implements Serializable {
    private long id;
    private Person customerReview;
    private Product productReview;
    private float rating;
    private String comment;
    private String date;

    public Review(long id, Person customerReview, Product productReview, float rating, String comment, String date) {
        this.id = id;
        this.customerReview = customerReview;
        this.productReview = productReview;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getCustomerReview() {
        return customerReview;
    }

    public void setCustomerReview(Person customerReview) {
        this.customerReview = customerReview;
    }

    public Product getProductReview() {
        return productReview;
    }

    public void setProductReview(Product productReview) {
        this.productReview = productReview;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", customerReview=" + customerReview +
                ", productReview=" + productReview +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
