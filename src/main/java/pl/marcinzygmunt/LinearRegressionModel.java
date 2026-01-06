package pl.marcinzygmunt;

package demo.ml;

import processing.core.PVector;

import java.util.List;

public class LinearRegressionModel {

    private float a;
    private float b;

    private float learningRate;
    private float errorThreshold;

    private int currentIndex = 0;
    private boolean converged = false;

    public LinearRegressionModel(float learningRate, float errorThreshold) {
        this.learningRate = learningRate;
        this.errorThreshold = errorThreshold;
        reset();
    }

    public void reset() {
        a = (float) (Math.random() * 2 - 1);
        b = (float) (Math.random() * 200);
        currentIndex = 0;
        converged = false;
    }

    public void step(List<PVector> points) {
        if (points.isEmpty() || converged) return;

        PVector p = points.get(currentIndex);

        float x = p.x;
        float y = p.y;

        float yPred = predict(x);
        float error = y - yPred;

        a += error * x * learningRate;
        b += error * learningRate;

        if (Math.abs(error) < errorThreshold) {
            converged = true;
        }

        currentIndex = (currentIndex + 1) % points.size();
    }

    public float predict(float x) {
        return a * x + b;
    }

    public boolean isConverged() {
        return converged;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }
}

