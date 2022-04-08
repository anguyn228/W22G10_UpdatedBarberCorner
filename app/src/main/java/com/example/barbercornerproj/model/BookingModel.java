package com.example.barbercornerproj.model;

import com.example.barbercornerproj.DatabaseHelper;

public class BookingModel {
    private int bookingId;
    private int userId;
    private int barberId;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    public BookingModel(int userId, int barberId, String dateTime) {
        this.userId = userId;
        this.barberId = barberId;
        String date = dateTime.split(" ")[0];
        String time = dateTime.split(" ")[1];
        System.out.println(date + " + " + time);
        this.day = Integer.parseInt(date.split("-")[2]);
        this.month = Integer.parseInt(date.split("-")[1]);
        this.year = Integer.parseInt(date.split("-")[0]);
        this.hour = Integer.parseInt(time.split(":")[0]);
        this.minute = Integer.parseInt(time.split(":")[1]);
        System.out.println(day + "-" + month + "-" + year + " " + hour + ":" + minute);
    }

    public BookingModel(int bookingId, int userId, int barberId, String dateTime) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.barberId = barberId;
        String date = dateTime.split(" ")[0];
        String time = dateTime.split(" ")[1];
        System.out.println(date + " + " + time);
        this.day = Integer.parseInt(date.split("-")[2]);
        this.month = Integer.parseInt(date.split("-")[1]);
        this.year = Integer.parseInt(date.split("-")[0]);
        this.hour = Integer.parseInt(time.split(":")[0]);
        this.minute = Integer.parseInt(time.split(":")[1]);
        System.out.println(day + "-" + month + "-" + year + " " + hour + ":" + minute);
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBarberId() {
        return barberId;
    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String toString(DataModel barber) {
        String str =
                "Barber: " + barber.getName() +
                ", Date: " + day + "-" + month + "-" + year + ", Time: " + hour + ":" + minute;
        return str;
    }
}
