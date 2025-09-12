package com.phoebe.pbsub.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoebe.pbsub.entity.Client;
import com.phoebe.pbsub.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for ClientApi
 */
@WebMvcTest(ClientApi.class)
class ClientApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getClient_WhenClientExists_ShouldReturnClient() throws Exception {
        // Given
        Client client = new Client("Test Client", "test@example.com");
        client.setId(1L);
        
        when(clientService.get(1L)).thenReturn(client);

        // When & Then
        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Client"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void createClient_WithValidData_ShouldReturnCreatedClient() throws Exception {
        // Given
        Client newClient = new Client("New Client", "new@example.com");
        newClient.setId(1L);
        
        when(clientService.save(any(Client.class))).thenReturn(newClient);

        // When & Then
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newClient)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Client"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    void createClient_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given
        Client invalidClient = new Client("", "invalid-email");

        // When & Then
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateClient_WithValidData_ShouldReturnUpdatedClient() throws Exception {
        // Given
        Client updatedClient = new Client("Updated Client", "updated@example.com");
        updatedClient.setId(1L);
        
        when(clientService.save(any(Client.class))).thenReturn(updatedClient);

        // When & Then
        mockMvc.perform(put("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedClient)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Client"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void deleteClient_WhenClientExists_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNoContent());
    }


    @Test
    void getClientStats_ShouldReturnStatistics() throws Exception {
        // Given
        when(clientService.count()).thenReturn(5L);

        // When & Then
        mockMvc.perform(get("/api/clients/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalClients").value(5));
    }
}
