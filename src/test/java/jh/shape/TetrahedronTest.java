package jh.shape;

import jh.utils.TestUtils;
import org.junit.jupiter.api.Test;

public class TetrahedronTest {
    @Test
    void testVolumes() {
        Tetrahedron e = new Tetrahedron(1);
        Tetrahedron f = new Tetrahedron(5);

        TestUtils.verify(e.getVolume(), 0.12);
        TestUtils.verify(f.getVolume(), 14.73);
    }
}
