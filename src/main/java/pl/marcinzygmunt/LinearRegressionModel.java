package pl.marcinzygmunt;

import java.util.List;

public class LinearRegressionModel {

    private float a;
    private float b;

    private float learningRate;
    private final float errorThreshold;

    private int currentIndex = 0;
    private boolean converged = false;

    public LinearRegressionModel(float learningRate, float errorThreshold) {
        this.learningRate = learningRate;
        this.errorThreshold = errorThreshold;
        reset();
    }

    public void reset() {
        a = (float) (Math.random() * 2 - 1);
        b = (float) (Math.random() * 2 - 1);
        currentIndex = 0;
        converged = false;
    }

    /**
     * One SGD step.
     * Data must be normalized to [-0.5, 0.5]
     */
    public void step(List<float[]> data) {
        if (data.isEmpty() || converged) return;

        float[] p = data.get(currentIndex);
        float x = p[0];
        float y = p[1];

        float yPred = predict(x);
        float error = y - yPred;

        a += error * x * learningRate;
        b += error * learningRate;

        // stop condition
        if (Math.abs(error) < errorThreshold) {
            converged = true;
        }

        currentIndex = (currentIndex + 1) % data.size();
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

    public float getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(float learningRate) {
        this.learningRate = learningRate;
    }
}
