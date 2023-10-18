package jh.threading;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MultithreadingChallengeTest {
    @Test
    void problem1() throws Exception {
        MultithreadingChallenge.problem1();
    }

    @Test
    @Timeout(value=12)
    void problem2() throws Exception {
        MultithreadingChallenge.problem2();
    }
}
