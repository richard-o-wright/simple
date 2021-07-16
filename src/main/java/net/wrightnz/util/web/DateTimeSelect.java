/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import net.wrightnz.util.Util;

/**
 *
 * @author Richard Wright
 */
public class DateTimeSelect extends DateSelect{
    
    protected static final String HOUR_SUFFIX = "hour";
    protected static final String MINUTE_SUFFIX = "minute";
        
    protected Select hour;
    protected Select minute;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public DateTimeSelect(String prefix, Date initialDate){
        super(prefix, initialDate);
    }

    public DateTimeSelect(String prefix){
        this(prefix, new Date());
    }

    @Override
    protected void initSelects(){
        super.initSelects();
        hour = new Select();
        hour.setName(parameterPrefix + HOUR_SUFFIX);
        minute = new Select();
        minute.setName(parameterPrefix + MINUTE_SUFFIX);
    }

    @Override
    protected void updateSelects(Date date){
        super.updateSelects(date);
        hour.setItems(getHours());
        minute.setItems(getMinutes());
    }
    
    @Override
    public void setSelected(Map<String, String[]> params) throws ParseException{
        if(Util.getFirstValue(params, parameterPrefix + YEAR_SUFFIX) != null){
            setSelected(Util.getFirstValue(params, parameterPrefix + YEAR_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + MONTH_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + DAY_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + HOUR_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + MINUTE_SUFFIX));
        }
        else{
            setSelected(new Date());
        }
    }
    
    
    public void setSelected(String y, String m, String d, String h, String min) throws ParseException{
        Date date = dateFormat.parse(y + "-" + m + "-" + d + " " + h + ":" + min);
        setSelected(date);
    }   
    
    @Override
    public void setSelected(Date date){
        super.setSelected(date);
        if(date == null){
            date = new Date();
        }
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        hour.setSelectedValue(make2DigitString(h));
        minute.setSelectedValue(make2DigitString(min));
    }
    

    /**
     * 
     * @return
     * @throws ParseException
     */
    @Override
    public Date getSelectedDate() throws ParseException{
        String y = year.getSelectedValue().toString();
        String m = month.getSelectedValue().toString();
        String d = day.getSelectedValue().toString();
        String h = hour.getSelectedValue().toString();
        String min = minute.getSelectedValue().toString();
        if(isAllowNoneSelect && "0".equals(y) && "0".equals(m) && "0".equals(d) && "0".equals(h) && "0".equals(m)){
            return null;
        }
        else{
            String dateString = y + "-" + m + "-" + d + " " + h + ":" + min;
            return dateFormat.parse(dateString);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("&nbsp;");
        sb.append(hour.toString());
        sb.append(":");
        sb.append(minute.toString());
        return sb.toString();
    }
    
    /**
     * Main method for unit testing only
     * @param args
     */
    public static void main(String[] args)throws Exception{
        DateTimeSelect dateTimeSelect = new DateTimeSelect("test.");
        dateTimeSelect.setSelected(new Date());
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);

        System.out.println("=================================================");
        dateTimeSelect.setSelected("2009", "01", "01", "00", "00");
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);   


        System.out.println("=================================================");
        dateTimeSelect.setSelected("2007", "12", "12", "23", "55");
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);

        System.out.println("=================================================");
        dateTimeSelect.setIsAllowNoneSelect(true);
        dateTimeSelect.setSelected("2005", "09", "13", "13", "03");
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);
    }
    

    protected ValueDescriptions getHours(){
        ValueDescriptions hours = new ValueDescriptions();
        if(isAllowNoneSelect){
            hours.put("0", " ");
        }
        for(int h = 0; h < 24; h++){
            hours.put(make2DigitString(h), make2DigitString(h));
        }
        return hours;
    }
    
    protected ValueDescriptions getMinutes(){
        ValueDescriptions mins = new ValueDescriptions();
        if(isAllowNoneSelect){
            mins.put("0", " ");
        }
        for(int m = 0; m < 60; m = m + 5){
            mins.put(make2DigitString(m), make2DigitString(m));
        }
        return mins;
    }
    
}
