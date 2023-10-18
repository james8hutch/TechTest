package jh.shape;

import jh.utils.TestUtils;
import org.junit.jupiter.api.Test;

public class CubeTest {
    @Test
    void testVolumes() {
        Cube a = new Cube(1.0);
        Cube b = new Cube(5.0);

        TestUtils.verify(a.getVolume(), 1.0);
        TestUtils.verify(b.getVolume(), 125.0);
    }
}
