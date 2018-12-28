package com.example.pointme.classes;

public class PointmeDate implements Comparable<PointmeDate>{

    private int Day;
    private int Month;
    private int Year;
    private int Hour;
    private int Minute;

    public PointmeDate(){}

    public PointmeDate(int Day, int Month, int Year, int Hour, int Minute){
        this.Day = Day;
        this.Month = Month;
        this.Year = Year;
        this.Hour = Hour;
        this.Minute = Minute;
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

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        this.Hour = hour;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minute) {
        this.Minute = minute;
    }

    @Override
    public int compareTo(PointmeDate o) {
        int compare = compareNum(this.Year, o.getYear());
        if (compare == 0){
            compare = compareNum(this.Month, o.getMonth());
            if (compare == 0){
                compare = compareNum(this.Day, o.getDay());
                if (compare == 0){
                    compare = compareNum(this.Hour, o.getHour());
                    if (compare == 0){
                        compare = compareNum(this.Minute, o.getMinute());
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
        temp = String.valueOf(Hour);
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;
        temp = String.valueOf(Minute);
        if(temp.length() == 1){
            temp = "0" + temp;
        }
        string+=temp;

        return string;
    }

    public static PointmeDate StringToDate(String string){
        PointmeDate date = new PointmeDate();
        date.setYear(Integer.parseInt(string.substring(0,4)));
        date.setMonth(Integer.parseInt(string.substring(4,6)));
        date.setDay(Integer.parseInt(string.substring(6,8)));
        date.setHour(Integer.parseInt(string.substring(8,10)));
        date.setMinute(Integer.parseInt(string.substring(10)));
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
