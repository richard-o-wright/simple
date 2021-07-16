/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.awt;

/**
 *
 * @author Richard Wright
 */
public class DateValidator implements Validator<String> {

    private String message;
    private String dateFormatString;

    public DateValidator(String dateFormatString, String message) {
        this.message = message;
        this.dateFormatString = dateFormatString;
    }

    @Override
    public void validate(String value) throws InvalidDataException {
        if (value == null 
                || "".equals(value) 
                || "null".equals(value) 
                || dateFormatString.equals(value)) {
            throw new InvalidDataException(message);
        }
    }

}
