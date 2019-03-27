package com.example.pointme.models;

public class PointmeDate implements Comparable<PointmeDate>{

    private int Day;
    private int Month;
    private int Year;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public PointmeDate(){}

    public PointmeDate(int Day, int Month, int Year, int startHour, int startMinute, int endHour, int endMinute){
        this.Day = Day;
        this.Month = Month;
        this.Year = Year;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        this.Day = day;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        this.Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int hour) {
        this.startHour = hour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int minute) {
        this.startMinute = minute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public void addDuration(String duration){
        this.endMinute = this.startMinute + Integer.parseInt(duration.substring(2));
        int temp = 0;
        if (this.endMinute >= 60){
            this.endMinute -= 60;
            temp = 1;
        }
        this.endHour = this.startHour + temp + Integer.parseInt(duration.substring(0,2));
        if (this.endHour >= 24)
            this.endHour -= 24;
    }

    @Override
    public int compareTo(PointmeDate o) {
        int compare = compareNum(this.Year, o.getYear());
        if (compare == 0){
            compare = compareNum(this.Month, o.getMonth());
            if (compare == 0){
                compare = compareNum(this.Day, o.getDay());
                if (compare == 0){
                    compare = compareNum(this.startHour, o.getStartHour());
                    if (compare == 0){
                        compare = compareNum(this.startMinute, o.getStartMinute());
                        return compare;
                    }else{
                        return compare;
                    }
                }else{
                    return compare;
                }
            }else{
                return compare;
            }
        }else{
            return compare;
        }
    }

    public String toString(){
        String string = "", temp;
        temp = String.valueOf(Year);
        string+=temp;
        temp = String.valueOf(Month);
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;
        temp = String.valueOf(Day);
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;
        temp = String.valueOf(startHour);
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;
        temp = String.valueOf(startMinute);
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;
        if (!(endHour<0) && !(endMinute <0)) {
            temp = String.valueOf(endHour);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            string += temp;
            temp = String.valueOf(endMinute);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            string += temp;
        }

        return string;
    }

    public static PointmeDate StringToDate(String string){
        PointmeDate date = new PointmeDate();
        date.setYear(Integer.parseInt(string.substring(0,4)));
        date.setMonth(Integer.parseInt(string.substring(4,6)));
        date.setDay(Integer.parseInt(string.substring(6,8)));
        date.setStartHour(Integer.parseInt(string.substring(8,10)));
        date.setStartMinute(Integer.parseInt(string.substring(10,12)));
        if (string.length() > 12) {
            date.setEndHour(Integer.parseInt(string.substring(12, 14)));
            date.setEndMinute(Integer.parseInt(string.substring(14)));
        }
        return date;
    }

    private static int compareNum(int Num1, int Num2){
        if (Num1 > Num2){
            return 1;
        }else if (Num2 > Num1){
            return -1;
        }
        return 0;
    }
}
