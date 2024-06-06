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

    //--------------------------------------------threads' task-------------------------------------------------
    @Override
    public void run() {
        MathContext mc = new MathContext(floatingPoint + 10);
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal sixteen = new BigDecimal("16");
        for (int i = start; i < start + range; i++) {
            BigDecimal temp = BigDecimal.ONE.divide(sixteen.pow(i), mc)
                    .multiply(
                            new BigDecimal("4").divide(new BigDecimal(8 * i + 1), mc)
                                    .subtract(new BigDecimal("2").divide(new BigDecimal(8 * i + 4), mc))
                                    .subtract(new BigDecimal("1").divide(new BigDecimal(8 * i + 5), mc))
                                    .subtract(new BigDecimal("1").divide(new BigDecimal(8 * i + 6), mc))
                    );
            sum = sum.add(temp);
        }
        updateAnswer(sum);
    }
    private static synchronized void updateAnswer(BigDecimal sum) {
        answer = answer.add(sum);
    }
    public static synchronized void resetAnswer() {
        answer = BigDecimal.ZERO;
    }
    //--------------------------------------------threads' task---------------------------------------------------

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