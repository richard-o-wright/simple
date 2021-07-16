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
public class DateHourMinuteSecondSelect extends DateTimeSelect{
    
    protected static final String SECOND_SUFFIX = "second";

    protected Select second;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateHourMinuteSecondSelect(String prefix, Date initialDate){
        super(prefix, initialDate);
    }

    public DateHourMinuteSecondSelect(String prefix){
        this(prefix, new Date());
    }

    @Override
    protected void initSelects(){
        super.initSelects();
        second = new Select();
        second.setName(parameterPrefix + SECOND_SUFFIX);
    }

    @Override
    protected void updateSelects(Date date){
        super.updateSelects(date);
        second.setItems(getSeconds());
    }
    
    @Override
    public void setSelected(Map<String, String[]> params) throws ParseException{
        if(Util.getFirstValue(params, parameterPrefix + YEAR_SUFFIX) != null){
            setSelected(Util.getFirstValue(params, parameterPrefix + YEAR_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + MONTH_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + DAY_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + HOUR_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + MINUTE_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + SECOND_SUFFIX));
        }
        else{
            setSelected(new Date());
        }
    }
    
    
    public void setSelected(String y, String m, String d, String h, String min, String seconds) throws ParseException{
        Date date = dateFormat.parse(y + "-" + m + "-" + d + " " + h + ":" + min + ":" + seconds);
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
        int seconds = calendar.get(Calendar.SECOND);
        second.setSelectedValue(make2DigitString(seconds));
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
        String seconds = second.getSelectedValue().toString();
        if(isAllowNoneSelect && "0".equals(y) && "0".equals(m) && "0".equals(d) && "0".equals(h) && "0".equals(m) && "0".equals(seconds)){
            return null;
        }
        else{
            String dateString = y + "-" + m + "-" + d + " " + h + ":" + min + ":" + seconds;
            return dateFormat.parse(dateString);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(":");
        sb.append(second.toString());
        return sb.toString();
    }
    
    /**
     * Main method for unit testing only
     * @param args
     */
    public static void main(String[] args)throws Exception{
        DateHourMinuteSecondSelect dateTimeSelect = new DateHourMinuteSecondSelect("test.");
        dateTimeSelect.setSelected(new Date());
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);
        /*
        System.out.println("=================================================");
        dateTimeSelect.setSelected("2009", "01", "01", "00", "00", "59");
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);   

        System.out.println("=================================================");
        dateTimeSelect.setSelected("2007", "12", "12", "23", "55", "15");
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);

        System.out.println("=================================================");
        dateTimeSelect.setIsAllowNoneSelect(true);
        dateTimeSelect.setSelected("2011", "09", "13", "13", "03", "00");
        System.out.println(dateTimeSelect.getSelectedDate());
        System.out.println(dateTimeSelect);
         */
    }
    
    
    @Override
    protected ValueDescriptions getMinutes(){
        ValueDescriptions mins = new ValueDescriptions();
        if(isAllowNoneSelect){
            mins.put("0", " ");
        }
        for(int m = 0; m < 60; m++){
            String minString = make2DigitString(m);
            mins.put(minString, minString);
        }
        return mins;
    }
    
    protected ValueDescriptions getSeconds(){
        ValueDescriptions seconds = new ValueDescriptions();
        if(isAllowNoneSelect){
            seconds.put("0", " ");
        }
        for(int s = 0; s < 60; s++){
            String secString = make2DigitString(s);
            seconds.put(secString, secString);
        }
        return seconds;
    }
    
}
