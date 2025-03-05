package org.Nicolas.model;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(){
        this.day = 1;
        this.month = 1;
        this.year = 2025;
    }

    public Date(Date date){
        this.day = date.day;
        this.month = date.month;
        this.year = date.year;
    }

    public void advancement(int day, int month, int year){
        if(this.day + day > 31){
            this.day = (this.day + day % 365) % 30;
            this.month += (this.day + day) / 31;
        }else{
            this.day += day;
        }

        if(this.month + month > 12){
            this.month = (this.month + month) - (((this.month + month) / 12) * 12);
            this.year += (this.month + month) / 12;
        }else{
            this.month += month;
        }

        this.year += year;
    }

    public void getTime(){
        System.out.println(this.day + "/" + this.month + "/" + this.year);
    }

    public int getDifferenceMonths(Date previousDate){
        return (this.month - previousDate.month) + (this.year - previousDate.year) * 12;
    }
}

