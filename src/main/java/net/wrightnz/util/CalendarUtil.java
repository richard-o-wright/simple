/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util;

import java.util.Calendar;

/**
 *
 * @author T466225
 */
public class CalendarUtil {

    /**
     * Set hour, minute, second and millisecond fields in the input calendar to
     * zero.
     * 
     * @param calendar
     */
    public static void zeroHoursMinutesSecounds(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}
