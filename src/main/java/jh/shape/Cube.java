package jh.shape;

public class Cube implements Measurable {
    private double width;
    public Cube(double width) {
        this.width = width;
    }
    @Override
    public double getVolume() {
        return Math.pow(width, 3);
    }
}
