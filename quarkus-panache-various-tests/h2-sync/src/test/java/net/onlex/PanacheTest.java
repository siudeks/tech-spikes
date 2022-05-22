package net.onlex;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PanacheTest {
   

    @Inject
    ClientRepository clientRepository;

    @Test
    @Transactional
    void should_version_using_managed_entity() throws InterruptedException {
        var eId = UUID.randomUUID();
        var entity1 = new MyClient();
        entity1.id = eId;
        entity1.name = "my name";
        entity1.version = 0;
        clientRepository.persistAndFlush(entity1);
        
        var newEntityCopy1 = clientRepository.findById(eId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        var newEntityCopy2 = clientRepository.findById(eId, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        newEntityCopy1.name = "my name 1";
        newEntityCopy2.name = "my name 2";
        clientRepository.persistAndFlush(newEntityCopy1);
        clientRepository.persistAndFlush(newEntityCopy2);

        clientRepository.flush();

        var finalEntity = clientRepository.findById(eId, LockModeType.OPTIMISTIC);
        Assertions.assertThat(finalEntity.name).isEqualTo("my name 2");
        // Assertions.assertThat(newEntityCopy2.version).isEqualTo(1);
    }}


