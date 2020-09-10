package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PrimesFinderTool {

    public static List<PrimeThread> primeThreadList;
    public static int threadsNumber;
    public static int minValue;
    public static int maxValue;
    public static int threadJumps;

    public static void main(String[] args) {

        int maxPrim = 1000;
        int finished = 0;
        threadsNumber = 4;
        minValue = 0;
        threadJumps = maxPrim / threadsNumber;
        primeThreadList = new CopyOnWriteArrayList<>();

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
                    //check every 10ms if the idle status (10 seconds without mouse activity) was reached
                    if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement() > 10000) {
                        Thread.sleep(10);
                        for (PrimeThread primeThread : primeThreadList) {
                            primeThread.pauseThread(true);
                        }
                        System.out.println("Idle CPU ");
                    } else {
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
