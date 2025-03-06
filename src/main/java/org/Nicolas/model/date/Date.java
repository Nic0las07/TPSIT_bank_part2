package org.Nicolas.model.date;

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

    public void setDay(int day){
        this.day = day;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public void setYear(int year){
        this.year = year;
    }

    public String getTime(){
        return this.day + "/" + this.month + "/" + this.year;
    }

    public void advancement(int addDays, int addMonths, int addYears) {
        this.year += addYears;

        int[] monthDaysNormalYear = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int[] monthDaysLeapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        this.month += addMonths - 1;

        if(this.month > 12){
            this.year += this.month / 12;
            this.month = this.month % 12;
        }

        this.day += addDays;
        for(int i = 0; i < this.month; i++){
            if((this.year % 4 == 0 && this.year % 100 != 0) || this.year % 400 == 0){
                this.day += monthDaysLeapYear[i];
            }else{
                this.day += monthDaysNormalYear[i];
            }
        }
        this.month = 1;

        while(true){
            if((this.year % 4 == 0 && this.year % 100 != 0) || this.year % 400 == 0){
               if(this.day > 366){
                   this.year++;
                   this.day -= 366;
               }else{
                   break;
               }
            }else if(this.day > 365){
                this.year++;
                this.day -= 365;
            }else{
                break;
            }
        }

        for(int i = 0; i < monthDaysNormalYear.length; i++){
            if((this.year % 4 == 0 && this.year % 100 != 0) || this.year % 400 == 0){
                if(this.day > monthDaysLeapYear[i]){
                    this.day -= monthDaysLeapYear[i];
                    this.month++;
                }else{
                    break;
                }
            }else{
                if(this.day > monthDaysNormalYear[i]){
                    this.day -= monthDaysNormalYear[i];
                    this.month++;
                }else{
                    break;
                }
            }
        }
    }

    public int getDifferenceMonths(Date previousDate){
        return (this.month - previousDate.month) + (this.year - previousDate.year) * 12;
    }
}

