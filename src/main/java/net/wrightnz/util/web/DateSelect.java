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
public class DateSelect {
    
    protected static final String YEAR_SUFFIX = "year";
    protected static final String MONTH_SUFFIX = "month";
    protected static final String DAY_SUFFIX = "day";

    protected String parameterPrefix;
    
    protected Select year;
    protected Select month;
    protected Select day;

    private int numberOfYears = 5;

    protected boolean isAllowNoneSelect = false;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Create a new instance of DateSelect. The new instance will have it's html
     * form parameter names prefixed with the specified parameterPrefix.
     *
     * @param parameterPrefix
     * @param initialDate
     */
    public DateSelect(String parameterPrefix) throws ParseException{
        this(parameterPrefix, false);
    }

    /**
     * Create a new instance of DateSelect. The new instance will have it's html
     * form parameter names prefixed with the specified prefix and display an
     * have the specified initialDate selected.
     * 
     * @param prefix
     * @param initialDate
     */
    public DateSelect(String parameterPrefix, Date initialDate){
        this(parameterPrefix, initialDate, false);
    }

    /**
     * Create a new instance of DateSelect. The new instance will have it's html
     * form parameter names prefixed with the specified prefix.
     * @param prefix
     * @param isAllowNoneSelect true if no date can be select in the widget i.e.
     *        year, month and day are empty.
     */
    public DateSelect(String parameterPrefix, boolean isAllowNoneSelect){
        this(parameterPrefix, new Date(), false);
    }


    /**
     * Create a new instance of DateSelect. The new instance will have it's html
     * form parameter names prefixed with the specified prefix and display an
     * have the specified initialDate selected.
     *
     * @param parameterPrefix
     * @param initialDate
     * @param isAllowNoneSelect true if no date can be select in the widget i.e.
     *        year, month and day are empty.
     */
    public DateSelect(String parameterPrefix, Date initialDate, boolean isAllowNoneSelect){
        this.parameterPrefix = parameterPrefix;
        this.isAllowNoneSelect = isAllowNoneSelect;
        this.initSelects();
        this.updateSelects(initialDate);
        this.setSelected(initialDate);
    }


    protected void initSelects(){
        year = new Select();
        year.setName(parameterPrefix + YEAR_SUFFIX);
        month = new Select();
        month.setName(parameterPrefix + MONTH_SUFFIX);
        day = new Select();
        day.setName(parameterPrefix + DAY_SUFFIX);
    }

    protected void updateSelects(Date date){
        year.setItems(getYears(date));
        month.setItems(getMonths()); 
        day.setItems(getDays());
    }
    
    public void setSelected(Map<String, String[]> params) throws ParseException{
        if(Util.getFirstValue(params, parameterPrefix + YEAR_SUFFIX) != null){
            setSelected(Util.getFirstValue(params, parameterPrefix + YEAR_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + MONTH_SUFFIX),
                Util.getFirstValue(params, parameterPrefix + DAY_SUFFIX));
        }
        else{
            setSelected(new Date());
        }
    }
    
    public void setSelected(String y, String m, String d) throws ParseException{
        Date date = dateFormat.parse(y + "-" + m + "-" + d);
        setSelected(date);
    }   
    
    public void setSelected(Date date){
        if(date == null){
            date = new Date();
        }
        updateSelects(date);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int y = calendar.get(Calendar.YEAR);
        int mon = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        year.setSelectedValue(String.valueOf(y));
        month.setSelectedValue(make2DigitString(mon));
        day.setSelectedValue(make2DigitString(d));
    }
    
    /**
     * @return the time currently select in this widget.
     * @throws ParseException if the select date in invalid e.g. 2012-02-31
     */
    public Date getSelectedDate() throws ParseException{
        String y = year.getSelectedValue().toString();
        String m = month.getSelectedValue().toString();
        String d = day.getSelectedValue().toString();
        if(isIsAllowNoneSelect() && "0".equals(y) &&  "0".equals(m) &&  "0".equals(d)){
            return null;
        }
        else{
            String dateString = y + "-" + m + "-" + d;
            return dateFormat.parse(dateString);
        }
    }

    /**
     * Get the number of years to display in the year selection list
     * @return the numberOfYears
     */
    public int getNumberOfYears() {
        return numberOfYears;
    }

    /**
     * Set the number of years to display in the year selection list.
     * @param numberOfYears the numberOfYears to set
     */
    public void setNumberOfYears(int numberOfYears) throws ParseException {
        this.numberOfYears = numberOfYears;
        year.setItems(getYears(this.getSelectedDate()));
    }

    /**
     *
     * @return true if the user can select no date i.e. empty year, month and
     *         day. Note is no date is selected getSelectDate() will return
     *         null.
     */
    public boolean isIsAllowNoneSelect() {
        return isAllowNoneSelect;
    }

    /**
     * Sets isAllowNoneSelect.
     * If isAllowNoneSelect is true the user can select no date i.e. empty year,
     * month and day. Note is no date is selected getSelectDate() will return
     * null.
     * @param isAllowNoneSelect
     */
    public void setIsAllowNoneSelect(boolean isAllowNoneSelect) {
        this.isAllowNoneSelect = isAllowNoneSelect;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(year.toString());
        sb.append("-");
        sb.append(month.toString());
        sb.append("-");
        sb.append(day.toString());
        return sb.toString();
    }
    
    /**
     * Main method for unit testing only
     * @param args
     */
    public static void main(String[] args)throws Exception{
        DateSelect dateTimeSelect = new DateSelect("test.");
        System.out.println(dateFormat.format(dateTimeSelect.getSelectedDate()));
        System.out.println(dateTimeSelect);   

        System.out.println("=================================================");
        dateTimeSelect.setNumberOfYears(10);
        dateTimeSelect.setSelected("2009", "12", "12");
        System.out.println(dateTimeSelect);

        System.out.println("=================================================");
        dateTimeSelect.setNumberOfYears(3);
        dateTimeSelect.setSelected("2002", "01", "21");
        System.out.println(dateTimeSelect);

        System.out.println("=================================================");
        DateSelect pastdate = new DateSelect("pastdate.", dateFormat.parse("2002-01-01"));
        pastdate.setNumberOfYears(3);
        pastdate.setSelected("2002", "01", "21");
        System.out.println(pastdate);
        
        System.out.println("=================================================");
        DateSelect noneSelect = new DateSelect("noneSelect.", true);
        noneSelect.setNumberOfYears(3);
        noneSelect.setSelected("2010", "01", "30");
        System.out.println(noneSelect);
    }
    
    protected ValueDescriptions getYears(Date starting){
        ValueDescriptions years = new ValueDescriptions();
        Calendar cal = GregorianCalendar.getInstance();
        if(starting!= null){
            cal.setTime(starting);
        } 
        cal.add(Calendar.YEAR, -1);
        if(isAllowNoneSelect){
            years.put("0", " ");
        }
        while(cal.before(GregorianCalendar.getInstance())){
            years.put(String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.YEAR)));
            cal.add(Calendar.YEAR, 1);
        }
        for(int c = 0 ; c < numberOfYears; c++){
            years.put(String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.YEAR)));
            cal.add(Calendar.YEAR, 1);
        }
        return years;
    }
    
    protected ValueDescriptions getMonths(){
        ValueDescriptions months = new ValueDescriptions();
        if(isAllowNoneSelect){
            months.put("0", " ");
        }
        for(int m = 1; m < 13; m++){
            months.put(make2DigitString(m), make2DigitString(m));
        }
        return months;
    }
    
    protected ValueDescriptions getDays(){
        ValueDescriptions days = new ValueDescriptions();
        if(isAllowNoneSelect){
            days.put("0", " ");
        }
        for(int d = 1; d < 32; d++){
            days.put(make2DigitString(d), make2DigitString(d));
        }
        return days;
    }
    
    protected String make2DigitString(int i){
        String value = String.valueOf(i);
        if(value.length() > 2){
            throw new RuntimeException("Numbers larger the 2 digits are not supported");
        }
        if(value.length() == 1){
            return "0" + value;
        }
        return value;
    }
    
}
