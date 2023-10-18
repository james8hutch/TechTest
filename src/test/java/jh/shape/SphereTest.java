package jh.shape;

import jh.utils.TestUtils;
import org.junit.jupiter.api.Test;

public class SphereTest {
    @Test
    void testVolumes() {
        Sphere c = new Sphere(1);
        Sphere d = new Sphere(5);

        TestUtils.verify(c.getVolume(), 4.19);
        TestUtils.verify(d.getVolume(), 523.6);
    }
}
