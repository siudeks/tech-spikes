package net.onlex;

import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PanacheTest {
   
    @Inject
    ClientRepository repository;

    @Test
    @Transactional
    void should_insert_and_log_sql_parameters() {
        var entity = new Client();
        entity.id = 1;
        entity.name = "my name";
        repository.persistAndFlush(entity);
    }
}

