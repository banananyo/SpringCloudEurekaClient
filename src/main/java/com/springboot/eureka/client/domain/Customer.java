package com.springboot.eureka.client.domain;

/**
 * Created by Admin on 25/4/2560.
 */
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private Double balance;

    public Customer(Long id, String firstName, String lastName, Double balance){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    @Override
    public String toString(){
        return "id: "+this.id + " first name: " + this.firstName + " last name: " + this.lastName + " balance: "+ this.balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
