package edu.eci.arsw.primefinder;

import java.math.BigInteger;

/**
 *
 * @author Juan Sebastián Díaz
 */
public class PrimeThread extends Thread {

    private int minValue;
    private int maxValue;
    public boolean paused;
    public int threadFinished;

    public PrimeThread(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
        paused = false;
        threadFinished = 0;

    }

    public void pauseThread(boolean paused) {
        this.paused = paused;
    }

    public synchronized void resumeThread(boolean paused) {
        this.paused = paused;
        notifyAll();
    }

    public int getThreadFinished() {
        return threadFinished;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    @Override
    public void run() {

        if (!paused) {
            PrimesResultSet prs = new PrimesResultSet("Juan Sebastián Diaz");            
            PrimeFinder.findPrimes(new BigInteger(String.valueOf(minValue)), new BigInteger(String.valueOf(maxValue)), prs);
            System.out.println("Prime numbers found: ");
            System.out.println(prs.getPrimes());
            threadFinished = 1;
        } else {
            synchronized (this) {
                try {                    
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
