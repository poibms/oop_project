package com.example.project.Model;

public class Trip {
    private String start, finish;
    private Integer capacity, price;

    public Trip(String start, String finish, Integer capacity, Integer price) {
        this.start = start;
        this.finish = finish;
        this.capacity = capacity;
        this.price = price;
    }

    //get
    public String getStart() {return start;}
    public String getFinish() {return finish;}
    public int getCapacity() {return capacity;}
    public int getPrice() {return price;}

    //set
    public void setStart(String start) {this.start = start;}
    public void setFinish(String finish) {this.finish = finish;}
    public void setCapacity(Integer capacity) {this.capacity = capacity;}
    public void setPrice(Integer price) {this.price = price;}

}
