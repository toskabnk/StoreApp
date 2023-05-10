package com.svalero.storeapp.domain;

public class ReviewDTO {
    private long customerId;
    private long productId;
    private float rating;
    private String comment;

    public ReviewDTO(long customerReview, long productReview, float rating, String comment) {
        this.customerId = customerReview;
        this.productId = productReview;
        this.rating = rating;
        this.comment = comment;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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
}
