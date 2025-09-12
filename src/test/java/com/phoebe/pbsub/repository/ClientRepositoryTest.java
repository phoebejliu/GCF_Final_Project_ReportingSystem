package com.phoebe.pbsub.repository;

import com.phoebe.pbsub.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for ClientRepository
 */
@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void findByNameContainingIgnoreCase_ShouldReturnMatchingClients() {
        // Given
        Client client1 = new Client("Goldman Sachs", "gs@example.com");
        Client client2 = new Client("JPMorgan Chase", "jpm@example.com");
        Client client3 = new Client("BlackRock Inc", "br@example.com");
        
        entityManager.persistAndFlush(client1);
        entityManager.persistAndFlush(client2);
        entityManager.persistAndFlush(client3);

        // When
        List<Client> result = clientRepository.findByNameContainingIgnoreCase("goldman");

        // Then
        assertEquals(1, result.size());
        assertEquals("Goldman Sachs", result.get(0).getName());
    }


    @Test
    void findByIdWithSubscriptions_WhenClientExists_ShouldReturnClientWithSubscriptions() {
        // Given
        Client client = new Client("Test Client", "test@example.com");
        entityManager.persistAndFlush(client);

        // When
        Optional<Client> result = clientRepository.findByIdWithSubscriptions(client.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals("Test Client", result.get().getName());
        assertNotNull(result.get().getSubscriptions());
    }

    @Test
    void findByIdWithSubscriptions_WhenClientNotExists_ShouldReturnEmpty() {
        // When
        Optional<Client> result = clientRepository.findByIdWithSubscriptions(999L);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldPersistClient() {
        // Given
        Client client = new Client("New Client", "new@example.com");

        // When
        Client savedClient = clientRepository.save(client);

        // Then
        assertNotNull(savedClient.getId());
        assertEquals("New Client", savedClient.getName());
        assertEquals("new@example.com", savedClient.getEmail());
    }

    @Test
    void delete_ShouldRemoveClient() {
        // Given
        Client client = new Client("To Delete", "delete@example.com");
        entityManager.persistAndFlush(client);
        Long clientId = client.getId();

        // When
        clientRepository.deleteById(clientId);
        entityManager.flush();

        // Then
        Optional<Client> result = clientRepository.findById(clientId);
        assertFalse(result.isPresent());
    }

    @Test
    void count_ShouldReturnCorrectCount() {
        // Given
        Client client1 = new Client("Client 1", "client1@example.com");
        Client client2 = new Client("Client 2", "client2@example.com");
        
        entityManager.persistAndFlush(client1);
        entityManager.persistAndFlush(client2);

        // When
        long count = clientRepository.count();

        // Then
        assertEquals(2, count);
    }
}
