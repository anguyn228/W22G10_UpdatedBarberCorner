package com.example.barbercornerproj.model;

public class RatingModel {
    private int barberId;
    private int cusId;
    private int ratingId;
    private int rating;
    private String comment;


    public int getBarberId() {
        return barberId;
    }

    public RatingModel(int barberId, int cusId, int rating, String comment) {
        this.barberId = barberId;
        this.cusId = cusId;
        this.rating = rating;
        this.comment = comment;
    }

    public RatingModel(int ratingId, int barberId, int cusId, int rating, String comment) {
        this.barberId = barberId;
        this.cusId = cusId;
        this.ratingId = ratingId;
        this.rating = rating;
        this.comment = comment;
    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
