package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.math.BigInteger;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * @author Juan Sebastián Díaz
 */

public class PrimesFinderTool {

    public static CopyOnWriteArrayList<PrimeThread> primeThreadList;
    public static int threadsNumber;
    public static int minValue;
    public static int maxValue;
    public static int threadJumps;

    public static void main(String[] args) {
        
        primeThreadList = new CopyOnWriteArrayList<>();
        threadsNumber = 4;
        int maxPrim = 100; int finished = 0;        
        minValue = 0;
        threadJumps = maxPrim / threadsNumber;        

        for (int i = 0; i < threadsNumber; i++) {
            minValue = maxValue;
            maxValue = minValue + threadJumps;
            if (i == threadsNumber - 1) {
                maxValue = maxPrim;
            }
            PrimeThread primeThread = new PrimeThread(minValue, maxValue);
            primeThreadList.add(primeThread);
            primeThread.start();
        }

        while (finished != threadsNumber) {
            for (PrimeThread primeThread : primeThreadList) {
                if (primeThread.getThreadFinished() == 1) {
                    primeThread.pauseThread(false);
                    finished += primeThread.getThreadFinished();
                }
            }
            if (finished == threadsNumber) {
                System.out.println("Prime Search Ended");
            } else {
                try {
                    //check every 1000ms if the idle status (10 seconds without mouse activity) was reached
                    if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement() > 10000) {
                        Thread.sleep(1000);
                        for (PrimeThread primeThread : primeThreadList) {
                            primeThread.pauseThread(true);
                        }
                        System.out.println("Idle CPU ");
                    } else {
                        Thread.sleep(1000);
                        for (PrimeThread primeThread : primeThreadList) {
                            primeThread.resumeThread(false);
                        }
                        System.out.println("User working again!");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
