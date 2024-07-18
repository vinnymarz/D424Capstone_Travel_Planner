package com.example.capstone.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class Car {
    @PrimaryKey(autoGenerate = true)
    private int carID;
    private String carTitle;
    private int vacationID;
    private String carDate;

    // Constructor for the Car class, initializing its properties
    public Car(int carID, String carTitle, int vacationID, String carDate) {
        this.carID = carID;
        this.carTitle = carTitle;
        this.vacationID = vacationID;
        this.carDate = carDate;
    }

    public int getCarID() { return carID; }

    public void setCarID(int carID) {this.carID = carID;}

    public String getCarTitle() { return carTitle; }

    public void setCarTitle(String carTitle) {this.carTitle = carTitle;}

    public int getVacationID() { return vacationID; }

    public void setVacationID(int vacationID) {this.vacationID = vacationID;}

    public String getCarDate() { return carDate; }

    public void setCarDate(String carDate) {this.carDate = carDate;}
}
