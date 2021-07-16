/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.awt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;

/**
 *
 * @author Richard Wright
 */
public class DateTextField extends JTextField{
    
    public static final String DEFAULT_DATE_FORMAT_STR = "dd-MM-yyyy";
    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_FORMAT_STR);
    
    private Validator validator;
    private String dateFormatString;
    private DateFormat dateFormat;
    
    public DateTextField(){
        this(DEFAULT_DATE_FORMAT, DEFAULT_DATE_FORMAT_STR);
    }
    
    public DateTextField(DateFormat dateFormat, String dateFormatString){
        super(dateFormatString);
        this.dateFormat = dateFormat;
        this.dateFormatString = dateFormatString;
        this.init();
    }
    
    public DateTextField(Validator validator, String dateFormatString){
        super(dateFormatString);
        this.validator = validator;
        this.init();
    }
    
    private void init() {
        this.addCaretListener(new javax.swing.event.CaretListener() {
            
            @Override
            public void caretUpdate(CaretEvent e) {
                DateTextField.this.setBackground(UIManager.getColor("TextField.background"));
            }
        });
    }
    
    public void setDate(Date date) {
        if (date != null) {
            this.setText(dateFormat.format(date));
        } else {
            this.setText(dateFormatString);
        }
    }
    
    /**
     * 
     * @return the date currently entered into this DateTextField or null if
     * the value in these field is the date format string.
     * @throws ParseException if the value in this DateTextField is not the 
     * date format string and cannot be parsed as a valid date.
     */
    public Date getDate() throws ParseException {
        if (getText().isEmpty()) {
            return null;
        }
        
        if (!dateFormatString.equals(getText())) {
            return dateFormat.parse(getText());
        }

        return null;
    }
    
    /**
     * 
     * @return Date derived for the value displayed in this TextField.
     * @throws InvalidDataException if no date is entered
     * @throws ParseException  if a date is entered but cannot be parsed.
     */
    public Date getValidDate() throws InvalidDataException, ParseException{
        validator.validate(getText());
        return dateFormat.parse(getText());
    }
    
    /**
     * Reset the value shown to the date format string.
     */
    public void clear(){
        setText(dateFormatString);
    }

}
