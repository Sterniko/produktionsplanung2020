package de.adventureworks.produktionsplanung.model.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class BusinessCalendar {

    private boolean arrHolidays[][];
    /**
     * Constructor BusinessCalendar
     * gets Holidays for a year dummy 2019
     */
    public BusinessCalendar() {
        getHolidaysForYear(2019);
    }
    public boolean[][] getHolidays() {
        return this.arrHolidays;
    }

    /**
     * return true if date is saturday
     * @param date to check
     * @return
     */
    public boolean isSaturday(LocalDate date){
        if(date.getDayOfWeek().getValue() == 6){
            return true;
        }
        return false;
    }
    /**
     * returns boolean if LocalDate is a workingDay
     * @param date to check
     */
    public boolean isWorkingDay(LocalDate date){
        int day = date.getDayOfWeek().getValue();

        //Samstag & Sonntag
        if(day == 6 || day == 7){
            return false;
        }
        if(isHoliday(date)){
            return false;
        }
        return true;
    }

    /**
     * calculates the LocalDate for a given duration
     * @param localDate toStart
     * @param duration timeToAdd
     * @return LocalDate
     */
    public  LocalDate calculateDuration(LocalDate localDate, int duration){
        //TODO methode implementieren

        return localDate.plusDays((long)duration);
    }

    /**
     * @param date to check
     * @return if date is a holiday
     */
    public boolean isHoliday(LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();

        if (this.arrHolidays[month][day]) {
            return true;
        }
        return false;
    }

    /**
     * gets Holidays[month][day] and saves it in array for given year
     * @param year for given year
     */
    private void getHolidaysForYear(int year)
    {
        this.arrHolidays = new boolean[13][32];
        int a,b,c,month;
        a = year % 19;
        b = year % 4;
        c = year % 7;
        month = 0;

        int m = (8 * (year / 100) + 13) / 25 - 2;
        int s = year / 100 - year / 400 - 2;
        m = (15 + s - m) % 30;
        int n = (6 + s) % 7;

        int d = (m + 19 * a) % 30;

        if ( d == 29 )
            d = 28;
        else if (d == 28 && a >= 11)
            d = 27;

        int e = (2 * b + 4 * c + 6 * d + n) % 7;

        int day = 21 + d + e + 1;

        if (day > 31)
        {
            day = day % 31;
            month = 4;
        }
        else {
            month = 3;
        }


        GregorianCalendar gc_ostersonntag = new GregorianCalendar(year, month, day);
        this.arrHolidays[month][day] = true;

        //TODO: Localdate benutzen
        GregorianCalendar gc_ostermontag = new GregorianCalendar(gc_ostersonntag.get(Calendar.YEAR), gc_ostersonntag.get(Calendar.MONTH), (gc_ostersonntag.get(Calendar.DATE) + 1));
        GregorianCalendar gc_karfreitag = new GregorianCalendar(gc_ostersonntag.get(Calendar.YEAR), gc_ostersonntag.get(Calendar.MONTH), (gc_ostersonntag.get(Calendar.DATE) - 2));
        GregorianCalendar gc_christihimmelfahrt = new GregorianCalendar(gc_ostersonntag.get(Calendar.YEAR), gc_ostersonntag.get(Calendar.MONTH), (gc_ostersonntag.get(Calendar.DATE) + 39));
        GregorianCalendar gc_pfingstsonntag = new GregorianCalendar(gc_ostersonntag.get(Calendar.YEAR), gc_ostersonntag.get(Calendar.MONTH), (gc_ostersonntag.get(Calendar.DATE) + 49));
        GregorianCalendar gc_pfingstmontag = new GregorianCalendar(gc_ostersonntag.get(Calendar.YEAR), gc_ostersonntag.get(Calendar.MONTH), (gc_ostersonntag.get(Calendar.DATE) + 50));
        GregorianCalendar gc_frohnleichnahm = new GregorianCalendar(gc_ostersonntag.get(Calendar.YEAR), gc_ostersonntag.get(Calendar.MONTH), (gc_ostersonntag.get(Calendar.DATE) + 60));

        this.arrHolidays[gc_ostermontag.get(Calendar.MONTH)][gc_ostermontag.get(Calendar.DAY_OF_MONTH)] = true;
        this.arrHolidays[gc_karfreitag.get(Calendar.MONTH)][gc_karfreitag.get(Calendar.DAY_OF_MONTH)] = true;
        this.arrHolidays[gc_christihimmelfahrt.get(Calendar.MONTH)][gc_christihimmelfahrt.get(Calendar.DAY_OF_MONTH)] = true;
        this.arrHolidays[gc_pfingstsonntag.get(Calendar.MONTH)][gc_pfingstsonntag.get(Calendar.DAY_OF_MONTH)] = true;
        this.arrHolidays[gc_pfingstmontag.get(Calendar.MONTH)][gc_pfingstmontag.get(Calendar.DAY_OF_MONTH)] = true;
        this.arrHolidays[gc_frohnleichnahm.get(Calendar.MONTH)][gc_frohnleichnahm.get(Calendar.DAY_OF_MONTH)] = true;

        //Wiedervereiningung,Weihnachten,Silvester,Neujahr
        this.arrHolidays[10][1] = true;
        this.arrHolidays[12][24] = true;
        this.arrHolidays[12][25] = true;
        this.arrHolidays[12][26] = true;
        this.arrHolidays[12][31] = true;
        this.arrHolidays[1][1] = true;
        this.arrHolidays[5][1] = true;

    }
    /**
     * Return number of workingdays For a given Month and Year
     */
    public int getWorkingDaysOutOfMonthAndYear(int month, int year){
        int firstDay = 0;
        int workingDays = 0;

        //Calendar to get first day of week of month
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(year,month -1 ,firstDay);
        int startDayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK);

        //YearMonth to get Length Of Current Month
        YearMonth yearMonth = YearMonth.of(year,month);
        int days = yearMonth.lengthOfMonth();

        //count Days till WE
        while(startDayOfWeek < 6){
            workingDays++;
            days--;
            startDayOfWeek++;
        }
        //Sunday
        if(startDayOfWeek == 7){
            days-=1;
        }//Saturday
        else {
            days -= 2;
        }
        //get Weeks
        int weeks = days / 7;
        //days that dont fit into weeks
        int restDays = days % 7;

        for(int i = 0 ; i < weeks; i++){
            workingDays += 5;
        }
        //get rest working Days
        if(restDays > 5){
            workingDays += 5;
        }
        else if (restDays > 6){
            workingDays += 5;
        }
        else{
            workingDays += restDays;
        }

        workingDays -= getHolidaysForMonth(month);

        return workingDays;
    }

    /**
     *
     * @param date to Check in which week
     * @return week of year as Long
     */
    public Long getCalendarWeekFromDate(LocalDate date){
        Calendar cal = Calendar.getInstance();
        Date udate = java.sql.Date.valueOf(date);
        cal.setTime(udate);
        Long week = Long.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
        return week;
    }

    /**
     *
     * @param month to check for holiday
     * @return how many days in month are holiday
     */
    private int getHolidaysForMonth(int month){
        int offDays = 0;
        for(int i = 0 ; i < 32; i++){
            if(this.arrHolidays[month][i]){
                offDays++;
            }
        }
        return offDays;
    }

}