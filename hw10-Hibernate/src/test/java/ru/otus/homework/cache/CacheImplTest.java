package ru.otus.homework.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.TestService;

import java.util.Collections;

class CacheImplTest {
    private Cache<Long, User> cache;
    private TestService testService;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        cache = new CacheImpl<>();
        testService = new TestService(this);
        expectedUser = new User(
                "test",
                testService.generateNumeric(),
                new AddressDataSet("test"),
                Collections.singleton(
                        new PhoneDataSet("test")
                )
        );
    }

    @Test
    void put_get() {
        cache.put(1L, expectedUser);
        User actualUser = cache.get(1L);
        testService.assertEquals(expectedUser, actualUser);
    }

    @Test
    void put_remove_get_getAll_error() {
        cache.put(1L, expectedUser);
        cache.remove(1L);
        Assertions.assertNull(cache.get(1L));
        Assertions.assertEquals(0, cache.getAll().size());
    }
}