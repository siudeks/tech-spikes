package net.onlex;

import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.LockModeType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PanacheTest {
   

    @Inject
    ClientRepository clientRepository;

    @Test
    void should_version_using_managed_entity() throws InterruptedException {
        var eId = UUID.randomUUID();
        var entity1 = new MyClient();
        entity1.id = eId;
        entity1.name = "my name";
        entity1.version = 0;
        clientRepository.persistAndFlush(entity1).await().indefinitely();
        
        var newEntityCopy1 = clientRepository.findById(eId, LockModeType.OPTIMISTIC_FORCE_INCREMENT).await().indefinitely();
        var newEntityCopy2 = clientRepository.findById(eId, LockModeType.OPTIMISTIC_FORCE_INCREMENT).await().indefinitely();
        newEntityCopy1.name = "my name 1";
        newEntityCopy2.name = "my name 2";
        clientRepository.persistAndFlush(newEntityCopy1).await().indefinitely();
        clientRepository.persistAndFlush(newEntityCopy2).await().indefinitely();

        clientRepository.flush().await().indefinitely();

        var finalEntity = clientRepository.findById(eId, LockModeType.OPTIMISTIC).await().indefinitely();
        Assertions.assertThat(finalEntity.name).isEqualTo("my name 2");
        // Assertions.assertThat(newEntityCopy2.version).isEqualTo(1);
    }
}


