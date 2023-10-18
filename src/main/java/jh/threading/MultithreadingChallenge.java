package jh.threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

class MultithreadingChallenge {
    static final class SharedInteger {
        private int i;

        SharedInteger() {
            i = 0;
        }

        // My understanding is that the problem occurs because two threads access the variable at the same time
        // before trying to increment.
        // e.g. let's say Thread A accesses i and gets 5. Before Thread A can increment, Thread B also looks
        // at the value and also gets 5. Thread A then increments 5 to 6. Thread B also increments from 5 to 6.
        // So we've called the method twice in reality, but the value has only been increased by 1.
        // Easiest way to solve is to use syncronized. This will put a lock object around the method,
        // and only one thread can access at a time.
        // We can also use AtomicInteger. I will show this in a separate PR.
        // As regards strengths and weaknesses of the two approaches, I have read that AtomicInteger may be slightly
        // faster. I will place some very unscientific timings in my README file.
        // Interestingly, the problem1 test appears to take twice as long with AtomicInteger...
        synchronized void increment() {
            i++;
        }

        int get() {
            return i;
        }
    }

    static final class LockOrdering {
        private final ReentrantLock a;
        private final ReentrantLock b;

        LockOrdering() {
            a = new ReentrantLock();
            b = new ReentrantLock();
        }

        // Here we are faced with a deadlock problem. opA gets lock a, while opB gets lock b. They both
        // then wait forever (or until they're killed) in a hopeless effort to get the other lock.
        // To solve we can once again use synchronized.
        // Alternatively, my first thought was to add a third ReentrantLock to cover both locks, but it
        // amounts to the same thing. I'll add another PR to show this.
        synchronized void opA() throws InterruptedException {
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

        synchronized void opB() throws InterruptedException {
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
