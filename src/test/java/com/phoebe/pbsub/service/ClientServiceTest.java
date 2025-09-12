package com.phoebe.pbsub.service;

import com.phoebe.pbsub.entity.Client;
import com.phoebe.pbsub.exception.ClientNotFoundException;
import com.phoebe.pbsub.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ClientService
 */
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client testClient;

    @BeforeEach
    void setUp() {
        testClient = new Client("Test Client", "test@example.com");
        testClient.setId(1L);
    }

    @Test
    void findAll_ShouldReturnAllClients() {
        // Given
        List<Client> clients = Arrays.asList(testClient);
        when(clientRepository.findAll()).thenReturn(clients);

        // When
        List<Client> result = clientService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Client", result.get(0).getName());
        verify(clientRepository).findAll();
    }


    @Test
    void get_WhenClientExists_ShouldReturnClient() {
        // Given
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));

        // When
        Client result = clientService.get(1L);

        // Then
        assertEquals("Test Client", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(clientRepository).findById(1L);
    }

    @Test
    void get_WhenClientNotExists_ShouldThrowException() {
        // Given
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ClientNotFoundException.class, () -> clientService.get(999L));
        verify(clientRepository).findById(999L);
    }

    @Test
    void save_ShouldReturnSavedClient() {
        // Given
        Client newClient = new Client("New Client", "new@example.com");
        when(clientRepository.save(any(Client.class))).thenReturn(testClient);

        // When
        Client result = clientService.save(newClient);

        // Then
        assertEquals("Test Client", result.getName());
        verify(clientRepository).save(newClient);
    }

    @Test
    void delete_WhenClientExists_ShouldDeleteClient() {
        // Given
        when(clientRepository.existsById(1L)).thenReturn(true);

        // When
        clientService.delete(1L);

        // Then
        verify(clientRepository).existsById(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test
    void delete_WhenClientNotExists_ShouldThrowException() {
        // Given
        when(clientRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(ClientNotFoundException.class, () -> clientService.delete(999L));
        verify(clientRepository).existsById(999L);
        verify(clientRepository, never()).deleteById(anyLong());
    }

    @Test
    void searchByName_ShouldReturnMatchingClients() {
        // Given
        String searchTerm = "Test";
        List<Client> clients = Arrays.asList(testClient);
        when(clientRepository.findByNameContainingIgnoreCase(searchTerm)).thenReturn(clients);

        // When
        List<Client> result = clientService.searchByName(searchTerm);

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Client", result.get(0).getName());
        verify(clientRepository).findByNameContainingIgnoreCase(searchTerm);
    }

    @Test
    void count_ShouldReturnClientCount() {
        // Given
        when(clientRepository.count()).thenReturn(5L);

        // When
        long result = clientService.count();

        // Then
        assertEquals(5L, result);
        verify(clientRepository).count();
    }
}
