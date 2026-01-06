package pl.marcinzygmunt;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class LinearRegressionSketch extends PApplet {

    LinearRegressionModel model;

    // normalized data: [xNorm, yNorm]
    List<float[]> data = new ArrayList<>();

    boolean training = true;

    // safe learning rate bounds
    float minLR = 0.001f;
    float maxLR = 0.2f;

    @Override
    public void settings() {
        size(900, 600);
    }

    @Override
    public void setup() {
        frameRate(60);
        model = new LinearRegressionModel(0.05f, 0.001f);
        generateInitialData();
    }

    @Override
    public void draw() {
        background(255);

        drawAxes();

        if (training && !model.isConverged()) {
            model.step(data);
        }

        drawPoints();
        drawLine();
        drawInfo();
    }

    // ------------------------------------------------------------
    // Data
    // ------------------------------------------------------------

    void generateInitialData() {
        data.clear();

        for (int i = 0; i < 25; i++) {
            float x = random(100, width - 100);
            float y = 0.7f * x + 80 + random(-60, 60);

            data.add(new float[] {
                    normalizeX(x),
                    normalizeY(y)
            });
        }
    }

    // ------------------------------------------------------------
    // Normalization
    // ------------------------------------------------------------

    float normalizeX(float x) {
        return (x - width / 2f) / width;
    }

    float normalizeY(float y) {
        return (y - height / 2f) / height;
    }

    float denormalizeX(float xNorm) {
        return xNorm * width + width / 2f;
    }

    float denormalizeY(float yNorm) {
        return yNorm * height + height / 2f;
    }

    // ------------------------------------------------------------
    // Drawing
    // ------------------------------------------------------------

    void drawPoints() {
        noStroke();
        fill(50, 120, 255);

        for (float[] p : data) {
            ellipse(
                    denormalizeX(p[0]),
                    denormalizeY(p[1]),
                    7, 7
            );
        }
    }

    void drawLine() {
        stroke(220, 50, 50);
        strokeWeight(2);

        float x1 = -0.5f;
        float x2 = 0.5f;

        float y1 = model.predict(x1);
        float y2 = model.predict(x2);

        line(
                denormalizeX(x1), denormalizeY(y1),
                denormalizeX(x2), denormalizeY(y2)
        );
    }

    void drawAxes() {
        stroke(220);
        line(0, height / 2f, width, height / 2f);
        line(width / 2f, 0, width / 2f, height);
    }

    void drawInfo() {
        fill(0);
        textSize(14);

        text(
                String.format("y = %.3fx + %.3f",
                        model.getA(), model.getB()),
                20, 25
        );

        text(
                String.format("learning rate: %.4f",
                        model.getLearningRate()),
                20, 45
        );

        if (model.isConverged()) {
            fill(0, 150, 0);
            text("MODEL CONVERGED", 20, 65);
        }

        fill(0);
        text(
                "SPACE – pause | R – reset | ↑ ↓ – learning rate | click – add point",
                20, height - 20
        );
    }

    // ------------------------------------------------------------
    // Interaction
    // ------------------------------------------------------------

    @Override
    public void mousePressed() {
        data.add(new float[] {
                normalizeX(mouseX),
                normalizeY(mouseY)
        });
    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            training = !training;
        }

        if (key == 'r' || key == 'R') {
            model.reset();
        }

        if (keyCode == UP) {
            float lr = model.getLearningRate() * 1.2f;
            model.setLearningRate(min(lr, maxLR));
        }

        if (keyCode == DOWN) {
            float lr = model.getLearningRate() * 0.8f;
            model.setLearningRate(max(lr, minLR));
        }
    }
}
