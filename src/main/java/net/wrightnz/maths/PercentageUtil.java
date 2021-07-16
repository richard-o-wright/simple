/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.maths;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author t466225
 */
public class PercentageUtil {
    
    public static double howManyNeeded(double percentage) { 
        double sampleSize = 100;
        if (percentage % 2 == 0) {
            sampleSize = 50.0;
        }
        else if (percentage % 3 == 0) {
            sampleSize = 33.33;
        }
        else if (percentage % 4 == 0) {
            sampleSize = 25.0;
        }
        else if (percentage % 5 == 0) {
            sampleSize = 20.0;
        }
        else if (percentage % 10 == 0) {
            sampleSize = 10.0;
        }
        else if (percentage % 50 == 0) {
            sampleSize = 2.0;
        }
        else if (percentage % 100 == 0) {
            sampleSize = 1.0;
        }
        return sampleSize;
    }
    
    public static void main(String[] args){
        for(double p = 100; p > 0; p--){
            double y = howManyNeeded(p);
            double succeeded = (p / 100.0) * y;    
            double result = (succeeded / y) * 100.0;
            BigDecimal r = new BigDecimal(result);
            r = r.setScale(0, RoundingMode.HALF_UP);
            // System.out.println(p + "% requires set size: " + y + " of which " + succeeded + " pass");
            if(p != r.doubleValue()){
                System.out.println("######### Oh shit it doesn't work!");   
            }
        }
    }

}
