package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenTestTrue() throws InterruptedException {
        var sbq = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> IntStream.range(0, 10).forEach(value -> {
            try {
                sbq.offer(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        Thread consumer = new Thread(() -> IntStream.range(0, 7).forEach(value -> {
            try {
                sbq.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(sbq.getSize()).isEqualTo(3);

    }

}