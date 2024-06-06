package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Math.*;
public class PiCalculator extends Thread {

    private final int floatingPoint;
    private final int start;
    private final int range;
    private static BigDecimal answer = BigDecimal.ZERO; // we aet the first value for the answer to 0
    private static final Object lock = new Object();

    //------------------------------------------------constructor-----------------------------------------------
    public PiCalculator(int floatingPoint, int start, int range) {
        this.floatingPoint = floatingPoint;
        this.start = start;
        this.range = range;
    }
    //---------------------------------------------constructor--------------------------------------------------

    //-------------------------------------------calculate method-------------------------------------------------
    public static String calculate (int floatingPoint, int numThreads) {
        resetAnswer();
        int totalTerms = floatingPoint * 10;
        int range = totalTerms / numThreads;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int start = i * range;
            PiCalculator piCalculator = new PiCalculator(floatingPoint, start, range);
            threads.add(piCalculator);
            piCalculator.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        answer = answer.setScale(floatingPoint, RoundingMode.DOWN);
        return answer.toString();
    }
    //-------------------------------------------calculate method-------------------------------------------------
}