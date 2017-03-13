package com.example.oniichan.lunchlist;


public class Restaurant {
  private String name = "";
  private String address = "";
  private String type = "";
  private String discount = "";

  public String getDiscount() {
    return discount;
  }

  public void setDiscount(String discount) {
    this.discount = discount;
  }

  public String getName() {
    return (name);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return (address);
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getType() {
    return (type);
  }

  public void setType(String type) {
    this.type = type;
  }

  public String toString() {
    return (getName());
  }
}