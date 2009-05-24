/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.monitoring;

public class ExecutionTimer {

    private long start;
    private long end;

    public ExecutionTimer() {
        reset();
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end() {
        end = System.currentTimeMillis();
    }

    public long duration() {
        return (end - start);
    }

    public void reset() {
        start = 0;
        end = 0;
    }
    
}
