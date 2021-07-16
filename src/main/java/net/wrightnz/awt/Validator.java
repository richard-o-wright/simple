/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.awt;

/**
 *
 * @author Richard Wright
 */
public interface Validator<T> {

    void validate(T obj) throws InvalidDataException;
    
}
