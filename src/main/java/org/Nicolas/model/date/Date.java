package org.Nicolas.model.date;

import static java.lang.Math.abs;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(){
        this.day = 1;
        this.month = 1;
        this.year = 2025;
    }

    public Date(int day){
        this.day = day;
        this.month = 1;
        this.year = 2025;
    }

    public Date(int day, int month){
        this.day = day;
        this.month = month;
        this.year = 2025;
    }

    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(Date date){
        this.day = date.day;
        this.month = date.month;
        this.year = date.year;
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

    public void advancement(int addDays, int addMonths, int addYears) {
        this.year += addYears;

        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        this.day += addDays;
        while (this.day > monthDays[this.month - 1]) {
            this.day -= monthDays[this.month - 1];
            this.month++;
            if (this.month > 12) {
                this.month = 1;
                this.year++;
            }
        }

        this.month += addMonths;
        while (this.month > 12) {
            this.month -= 12;
            this.year++;
        }
    }

    public String getTime(){
        return this.day + "/" + this.month + "/" + this.year;
    }

    public int getDifferenceMonths(Date previousDate){
        return (this.month - previousDate.month) + (this.year - previousDate.year) * 12;
    }
}

