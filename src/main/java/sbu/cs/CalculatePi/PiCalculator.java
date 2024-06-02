package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import static java.lang.Math.*;

public class PiCalculator extends Thread {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     * Experiment with different algorithms to find accurate results.
     * <p>
     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all the test cases provided in the test folder.
     *
     * @param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     **/
    private final int floatingPoint;
    private BigDecimal answer;
    List<Thread> threads;

    public PiCalculator(int floatingPoint) { // where we are initialize the variables (constructor)
        this.floatingPoint = floatingPoint; // each time we set the floating point which we initialize it base on that floating point which the user will give us
        this.threads = new ArrayList<>(); // each time that we get an object from the class we will have a new list of threads
        this.answer = BigDecimal.ZERO; // each time which we call this class the value of answer will become zero (rest)
    }

    @Override
    public void run() {
        MathContext mc = new MathContext(1000); //In the provided code, the MathContext object is created with a precision of 1000, which means that the result of the BigDecimal operations will be calculated with a precision of up to 1000 digits. This high precision is necessary to ensure accurate calculation of the value of pi.
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal sixteen = new BigDecimal("16");
        for (int i = 0; i < 1000 ; i++) { // we are calculate the sigma to i = 1000 because we will have correct result
            BigDecimal temp = BigDecimal.ONE.divide(sixteen.pow(i), mc).multiply(new BigDecimal("4").divide(new BigDecimal(8 * i + 1), mc).subtract(new BigDecimal("2").divide(new BigDecimal(8 * i + 4), mc)).subtract(new BigDecimal("1").divide(new BigDecimal(8 * i + 5), mc)).subtract(new BigDecimal("1").divide(new BigDecimal(8 * i + 6), mc)));
            sum = sum.add(temp);
        }
        synchronized (this) { // we lock the code to avoid race condition
            answer = answer.add(sum);
        }
    }
    public String calculate() {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {thread.join();} catch (Exception e){e.printStackTrace();}
        }
        answer = answer.setScale(floatingPoint , RoundingMode.DOWN);
        return answer.toString();
    }
    public static void main(String[] args) {
        // Use the main function to test the code yourself
        PiCalculator piCalculator = new PiCalculator(2);
        piCalculator.addThread(new PiCalculator(2));
        piCalculator.addThread(new PiCalculator(2));
        System.out.println(piCalculator.calculate());
    }

    public void addThread(PiCalculator piCalculator) {
        Thread thread = new Thread(piCalculator);
        threads.add(thread);
    }
}


    //about big decimal : 1. they are final(which means that we can not change its value) 2. big decimal is a class in java which it means when we want to use it we have to get an object from it
    // π = Σ (1 / (16^i)) * (4 / (8*i + 1) - 2 / (8*i + 4) - 1 / (8*i + 5) - 1 / (8*i + 6))