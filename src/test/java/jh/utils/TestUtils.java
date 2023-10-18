package jh.utils;

import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Component;

@Component
public class TestUtils {
    public static void verify(double expected, double actual) {
        Assertions.assertEquals(expected, actual, 0.01);
    }
}
