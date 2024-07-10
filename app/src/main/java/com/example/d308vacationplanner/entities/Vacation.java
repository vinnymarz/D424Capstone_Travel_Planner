package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    public int vacationId;
    private String vacationTitle;
    public double vacationCost;


    public Vacation(int vacationId, String vacationTitle, double vacationCost) {
        this.vacationId = vacationId;
        this.vacationTitle = vacationTitle;
        this.vacationCost = vacationCost;
    }

    public String toString() {
        return vacationTitle;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public double getVacationCost() {
        return vacationCost;
    }

    public void setVacationCost(double vacationCost) {
        this.vacationCost = vacationCost;
    }
}
