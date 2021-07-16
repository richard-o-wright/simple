/*
 * CalenderWidget.java
 *
 * Created on 6 December 2005, 10:44
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package net.wrightnz.util.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.GregorianCalendar;
import net.wrightnz.util.CalendarUtil;

/**
 * Class which renders an Calendar Widget to HTML form.
 * 
 * @author t466225
 */
public class CalendarWidget implements java.io.Serializable{
    
    private List<DateRange> dateRanges;
    private int[] inactiveDays = {Calendar.SATURDAY, Calendar.SUNDAY};
    private boolean hasDayLinksEnabled = false;
    
    /** Creates a new instance of CalanderWidget */
    public CalendarWidget() {
        dateRanges = new ArrayList<DateRange>();
    }
    
    public void setInactiveDays(int[] inactiveDays){
        this.inactiveDays = inactiveDays;
    }
    
    /**
     * Add a new date range to display on the calendar is a specified colour.
     * Date ranges must be added to the list in order. That is
     * the first from date in the list is the lowest value and
     * the last to date is the greatest value.
     *
     */
    public void addHighlightDateRange(Date from, Date to, String colour){
        dateRanges.add(new DateRange(zeroHoursMinutesSecondsMills(from), 
                                     zeroHoursMinutesSecondsMills(to), 
                                     colour));
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Calendar calendar = new GregorianCalendar();
        Date firstFrom = new Date();
        Date lastTo = new Date();
        if (dateRanges.size() > 0) {
            firstFrom = dateRanges.get(0).getFrom();
            lastTo = dateRanges.get(dateRanges.size() - 1).getTo();
        }
        calendar.setTime(firstFrom);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int noMonths = monthsBetween(firstFrom, lastTo);
        sb.append("<table class=\"calendarContainer\"><tr>");
        for (int i = 0; i <= noMonths; i++) {
            sb.append("<td class=\"calendarContainer\">");
            buildCalendar(sb, calendar);
            sb.append("</td>");
        }
        sb.append("</tr></table>");
        return sb.toString();
    }
    
    private void buildCalendar(StringBuilder sb, Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);  
        int offSet = getOffSet(calendar);
        int daysInMonth = getDaysInMonth(month, year);
        sb.append("<strong>" + getMonthName(calendar.getTime()) + "</strong><br/>\n");
        sb.append("<table class=\"calendar\" cellspacing=\"0\">\n");
        sb.append("<tr><th>Mon</th><th>Tue</th><th>Wed</th><th>Thu</th><th>Fri</th><th>Sat</th><th>Sun</th></tr>\n");
        for(int s = 0; s < offSet - 1 ; s++){
            sb.append("<td>&nbsp;</td>");
        }
        for(int i = 0; i < daysInMonth; i++){         
            writeCalendarCell(sb, calendar);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if(((i + offSet) % 7) == 0){
                sb.append("</tr>\n<tr>");
            }
        }
        int r = 0;
        while((daysInMonth + offSet + r - 1) % 7 != 0){
            sb.append("<td>&nbsp;</td>");
            r++;
        }
        sb.append("</tr></table>\n");
   }
    
    private void writeCalendarCell(StringBuilder sb, Calendar day){
        int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
        Date date = day.getTime();
        boolean rendered = false;
        for(DateRange dateRange : dateRanges){
            if(dateRange.inRange(date)){
                if(isInactiveDay(dayOfWeek)){
                    appendCellHTML(sb, day, "#999999");
                }
                else{
                    appendCellHTML(sb, day, dateRange.getColour());
                }
                rendered = true;
                break;
            }
        }
        if(!rendered){
            if(isInactiveDay(dayOfWeek)){             
                appendCellHTML(sb, day, "#dddddd");
            }
            else{
                appendCellHTML(sb, day, "");
            }
        }
    }
    
    
    private void appendCellHTML(StringBuilder sb, Calendar date, String colour){
        int day = date.get(Calendar.DAY_OF_MONTH);      
        if(this.hasDayLinksEnabled){
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String dateString = df.format(date.getTime());
            sb.append("<td bgcolor=\"" + colour + "\"><a href=\"?selecteddate=" + dateString + "\">" + day + "</a></td>");
        }
        else{
            sb.append("<td bgcolor=\"" + colour + "\">" + day + "</td>");
        } 
    }
    
    private boolean isInactiveDay(int dayOfWeek){
        for(int i = 0; i < inactiveDays.length; i++){
            if(dayOfWeek == inactiveDays[i]){
               return true;
            }
        }
        return false;
    }

    protected int getOffSet(Calendar calendar){
        Calendar tempCal = new GregorianCalendar();
        tempCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        tempCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        tempCal.set(Calendar.DAY_OF_MONTH, 0);
        return tempCal.get(Calendar.DAY_OF_WEEK);  
    }
       
   private String getMonthName(Date date){
      DateFormat df = new SimpleDateFormat("MMMMM");
      return df.format(date);
   }
   
   /**
    * Returns the number of days in a month (m) for a specified year
    * (y), can handle leap years.
    */
   private int getDaysInMonth(int month, int year){
      int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
      if (month == 1){
          if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
            return 29;
          }
          else{
            return 28;
          }
      }
      else {
         return daysInMonth[month];
      }
   }
   
   private int monthsBetween(Date start, Date end){
       Calendar startc = new GregorianCalendar();
       startc.setTime(start);
       Calendar endc = new GregorianCalendar();
       endc.setTime(end);
       int startMonth = startc.get(Calendar.MONTH);
       int startYear = startc.get(Calendar.YEAR);
       int endMonth = endc.get(Calendar.MONTH);
       int endYear = endc.get(Calendar.YEAR);
       if(startYear == endYear){
           return endMonth - startMonth;
       }
       else if(startYear + 1 == endYear){
           endMonth = endMonth + 12;
           return endMonth - startMonth;
       } 
       else{
           throw new RuntimeException("Cannot handle case where date ranges span more the 2 years");
       }
   }
   
   public static void main(String[] args){
        CalendarWidget calWidg = new CalendarWidget();
        Date now = new Date();
        calWidg.addHighlightDateRange(now, now, "#000000");
        int year = 2005;
        for(int month = 0; month < 12; month++){
            // System.out.print(calWidg.getOffSet(year, month) + " = ");
            Calendar cal = new GregorianCalendar();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            System.out.println(calWidg.getOffSet(cal));
        }
        System.out.println(calWidg);
    }

    public boolean hasDayLinksEnabled() {
        return hasDayLinksEnabled;
    }

    public void setHasDayLinksEnabled(boolean hasDayLinksEnabled) {
        this.hasDayLinksEnabled = hasDayLinksEnabled;
    }
    
    
    private Date zeroHoursMinutesSecondsMills(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        CalendarUtil.zeroHoursMinutesSecounds(calendar);
        return  calendar.getTime();
    }
    
}

class DateRange implements java.io.Serializable{
    
    private Date from;
    private Date to;
    private String colour;
    
    public DateRange(Date from, Date to, String colour){
        this.from = from;
        this.to = to;
        this.colour = colour;
    }
    
    /**
     * 
     * @param date
     * @return true if the specified date is within this date range. Equal to
     *         the from date is considered to be within the range.
     */
    public boolean inRange(Date date){
        if(date.equals(from)){
            return true;
        }
        else if(date.after(from) && date.before(to)){
            return true;
            
        }
        else{
            return false;
        }
    }
    
    public Date getFrom(){
        return this.from;
    }
    
    public Date getTo(){
        return this.to;
    }
    
    public String getColour(){
        return this.colour;
    }
    
}
