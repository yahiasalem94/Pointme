package com.example.pointme.utils;

import android.animation.TypeEvaluator;

public class DoubleArrayEvaluator implements TypeEvaluator<double[]> {

    private double[] mArray;

    public DoubleArrayEvaluator() {
    }


    public DoubleArrayEvaluator(double[] reuseArray) {
        mArray = reuseArray;
    }

    @Override
    public double[] evaluate(float fraction, double[] startValue, double[] endValue) {
        double[] array = mArray;
        if (array == null) {
            array = new double[startValue.length];
        }

        for (int i = 0; i < array.length; i++) {
            double start = startValue[i];
            double end = endValue[i];
            array[i] = start + (fraction * (end - start));
        }
        return array;
    }
}