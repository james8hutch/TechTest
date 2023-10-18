package jh.shape;

public class Sphere implements Measurable {
    private double radius;
    public Sphere(double radius) {
        this.radius = radius;
    }
    @Override
    public double getVolume() {
        return (4.0/3.0) * Math.PI * Math.pow(radius, 3);
    }
}
