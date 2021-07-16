/*
 * Queue.java
 *
 * Created on 13 May 2003, 21:16
 */
package net.wrightnz.util;

/**
 *
 * @author  Richard Wright (with special thanks to Lance Ewing)
 */
public class Queue {

    private final static int MAX = 6000;
    private final static int EMPTY = -1;
    private int buf[] = new int[MAX];
    private int rpos = 0;
    private int spos = 0;

    /** 
     * Creates a new instance of Queue
     */
    public Queue() {
    }

    public void qstore(int value) {
        if (((spos + 1) == rpos) || (((spos + 1) == MAX) && (rpos == 0))) {
            return;
        }
        buf[spos] = value;
        spos++;
        if (spos == MAX) {
            spos = 0;  /* loop back */
        }
    }

    public int qretrieve() {
        if (rpos == MAX) {
            rpos = 0;  /* loop back */
        }
        if (rpos == spos) {
            return EMPTY;
        }
        rpos++;
        return buf[rpos - 1];
    }
}
