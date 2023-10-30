package ru.job4j.thread;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenTestTrue() throws InterruptedException {
        var sbq = new SimpleBlockingQueue<>(10);
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (queue.getSize() != 0 || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }

}