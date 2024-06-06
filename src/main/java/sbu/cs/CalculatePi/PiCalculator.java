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

}