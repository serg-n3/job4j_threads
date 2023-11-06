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

}