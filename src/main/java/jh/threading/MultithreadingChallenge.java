package jh.threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class MultithreadingChallenge {
    static final class SharedInteger {
        private AtomicInteger i;

        SharedInteger() {
            i = new AtomicInteger(0);
        }

        void increment() {
            i.getAndIncrement();
        }

        int get() {
            return i.get();
        }
    }

    static final class LockOrdering {
        private final ReentrantLock a;
        private final ReentrantLock b;

        LockOrdering() {
            a = new ReentrantLock();
            b = new ReentrantLock();
        }

        void opA() throws InterruptedException {
            try {
                a.lock();
                Thread.sleep(5_000);
                b.lock();
                assert a.isHeldByCurrentThread() && b.isHeldByCurrentThread();
            } finally {
                b.unlock();
                a.unlock();
            }
        }

        void opB() throws InterruptedException {
            try {
                b.lock();
                Thread.sleep(5_000);
                a.lock();
                assert a.isHeldByCurrentThread() && b.isHeldByCurrentThread();
            } finally {
                a.unlock();
                b.unlock();
            }
        }
    }

    static void problem1() throws InterruptedException {
        final int threads = 10;
        final int incrementsPerThread = 1_000_000;
        final int expected = threads * incrementsPerThread;
        final CountDownLatch cdl = new CountDownLatch(threads);
        final SharedInteger x = new SharedInteger();
        for (int i = 0; i < threads; i++) {
            (new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < incrementsPerThread; i++) {
                                    x.increment();
                                }
                            } finally {
                                cdl.countDown();
                            }
                        }
                    }))
                    .start();
        }
        cdl.await();
        assert expected == x.get();
    }

    static void problem2() throws InterruptedException {
        final CountDownLatch cdl = new CountDownLatch(2);
        final LockOrdering lo = new LockOrdering();
        (new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lo.opA();
                        } catch (final InterruptedException e) {
                        } finally {
                            cdl.countDown();
                        }
                    }
                }))
                .start();
        (new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lo.opB();
                        } catch (final InterruptedException e) {
                        } finally {
                            cdl.countDown();
                        }
                    }
                }))
                .start();
        cdl.await();
    }

    public static void main(final String[] args) throws InterruptedException {
        problem1();
        problem2();
    }
}
