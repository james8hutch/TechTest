package jh.shape;

public class Tetrahedron implements Measurable {
    private double edge;
    public Tetrahedron(double edge) {
        this.edge = edge;
    }
    @Override
    public double getVolume() {
        return Math.pow(edge, 3) / (6.0 * Math.pow(2.0, 0.5));
    }
}
