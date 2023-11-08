package ru.job4j.cache;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {
    @Test
    void whenAdd() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        boolean rsl = cache.add(base);
        assertTrue(rsl);
    }

    @Test
    void whenUpdate() {
        Base base = new Base(1, 0);
        Cache cache = new Cache();
        base.setName("One");
        cache.add(base);
        Base base1 = new Base(1, 0);
        base1.setName("Two");
        cache.update(base1);
        assertThat(cache.get(1).getName()).isEqualTo("Two");
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base1 = new Base(2, 1);
        cache.add(base);
        cache.add(base1);
        cache.delete(base);
        assertThat(cache.get(1)).isNull();
    }
}