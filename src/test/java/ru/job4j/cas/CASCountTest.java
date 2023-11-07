package ru.job4j.cas;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class CASCountTest {

    @Test
    void when1Increment() {
        CASCount casCount = new CASCount();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(1);

    }

    @Test
    void when4Increment() {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        casCount.increment();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(4);

    }

    @Test
    void whenTwoTreads() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                casCount.increment();
            }
        });
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                casCount.increment();
            }
        });
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        assertThat(casCount.get()).isEqualTo(6);
    }

}