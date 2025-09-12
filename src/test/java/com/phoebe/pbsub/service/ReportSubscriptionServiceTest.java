package com.phoebe.pbsub.service;

import com.phoebe.pbsub.entity.Client;
import com.phoebe.pbsub.entity.ReportSubscription;
import com.phoebe.pbsub.entity.enums.*;
import com.phoebe.pbsub.exception.DuplicateSubscriptionException;
import com.phoebe.pbsub.exception.SubscriptionNotFoundException;
import com.phoebe.pbsub.repository.ReportSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReportSubscriptionService
 */
@ExtendWith(MockitoExtension.class)
class ReportSubscriptionServiceTest {

    @Mock
    private ReportSubscriptionRepository subscriptionRepository;

    @InjectMocks
    private ReportSubscriptionService subscriptionService;

    private Client testClient;
    private ReportSubscription testSubscription;

    @BeforeEach
    void setUp() {
        testClient = new Client("Test Client", "test@example.com");
        testClient.setId(1L);
        
        testSubscription = new ReportSubscription(
            testClient, 
            ReportType.TRADE_CONFIRM, 
            Frequency.DAILY, 
            ReportFormat.PDF, 
            DeliveryMethod.EMAIL
        );
        testSubscription.setId(1L);
    }

    @Test
    void findAll_ShouldReturnAllSubscriptions() {
        // Given
        List<ReportSubscription> subscriptions = Arrays.asList(testSubscription);
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        // When
        List<ReportSubscription> result = subscriptionService.findAll();

        // Then
        assertEquals(1, result.size());
        assertEquals(ReportType.TRADE_CONFIRM, result.get(0).getReportType());
        verify(subscriptionRepository).findAll();
    }


    @Test
    void get_WhenSubscriptionExists_ShouldReturnSubscription() {
        // Given
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(testSubscription));

        // When
        ReportSubscription result = subscriptionService.get(1L);

        // Then
        assertEquals(ReportType.TRADE_CONFIRM, result.getReportType());
        assertEquals(Frequency.DAILY, result.getFrequency());
        verify(subscriptionRepository).findById(1L);
    }

    @Test
    void get_WhenSubscriptionNotExists_ShouldThrowException() {
        // Given
        when(subscriptionRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.get(999L));
        verify(subscriptionRepository).findById(999L);
    }

    @Test
    void save_WhenNewSubscription_ShouldSaveSuccessfully() {
        // Given
        ReportSubscription newSubscription = new ReportSubscription(
            testClient, 
            ReportType.DAILY_PNL, 
            Frequency.DAILY, 
            ReportFormat.CSV, 
            DeliveryMethod.EMAIL
        );
        when(subscriptionRepository.findByClientIdAndReportType(1L, ReportType.DAILY_PNL))
            .thenReturn(Collections.emptyList());
        when(subscriptionRepository.save(any(ReportSubscription.class))).thenReturn(newSubscription);

        // When
        ReportSubscription result = subscriptionService.save(newSubscription);

        // Then
        assertEquals(ReportType.DAILY_PNL, result.getReportType());
        verify(subscriptionRepository).findByClientIdAndReportType(1L, ReportType.DAILY_PNL);
        verify(subscriptionRepository).save(newSubscription);
    }

    @Test
    void save_WhenDuplicateSubscription_ShouldThrowException() {
        // Given
        ReportSubscription duplicateSubscription = new ReportSubscription(
            testClient, 
            ReportType.TRADE_CONFIRM, 
            Frequency.DAILY, 
            ReportFormat.PDF, 
            DeliveryMethod.EMAIL
        );
        when(subscriptionRepository.findByClientIdAndReportType(1L, ReportType.TRADE_CONFIRM))
            .thenReturn(Arrays.asList(testSubscription));

        // When & Then
        assertThrows(DuplicateSubscriptionException.class, () -> 
            subscriptionService.save(duplicateSubscription));
        verify(subscriptionRepository).findByClientIdAndReportType(1L, ReportType.TRADE_CONFIRM);
        verify(subscriptionRepository, never()).save(any(ReportSubscription.class));
    }

    @Test
    void save_WhenUpdatingExistingSubscription_ShouldSaveSuccessfully() {
        // Given
        testSubscription.setId(1L); // Existing subscription
        when(subscriptionRepository.save(any(ReportSubscription.class))).thenReturn(testSubscription);

        // When
        ReportSubscription result = subscriptionService.save(testSubscription);

        // Then
        assertEquals(ReportType.TRADE_CONFIRM, result.getReportType());
        verify(subscriptionRepository, never()).findByClientIdAndReportType(anyLong(), any(ReportType.class));
        verify(subscriptionRepository).save(testSubscription);
    }

    @Test
    void delete_WhenSubscriptionExists_ShouldDeleteSubscription() {
        // Given
        when(subscriptionRepository.existsById(1L)).thenReturn(true);

        // When
        subscriptionService.delete(1L);

        // Then
        verify(subscriptionRepository).existsById(1L);
        verify(subscriptionRepository).deleteById(1L);
    }

    @Test
    void delete_WhenSubscriptionNotExists_ShouldThrowException() {
        // Given
        when(subscriptionRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(SubscriptionNotFoundException.class, () -> subscriptionService.delete(999L));
        verify(subscriptionRepository).existsById(999L);
        verify(subscriptionRepository, never()).deleteById(anyLong());
    }

    @Test
    void findByClientId_ShouldReturnClientSubscriptions() {
        // Given
        List<ReportSubscription> subscriptions = Arrays.asList(testSubscription);
        when(subscriptionRepository.findByClientId(1L)).thenReturn(subscriptions);

        // When
        List<ReportSubscription> result = subscriptionService.findByClientId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals(ReportType.TRADE_CONFIRM, result.get(0).getReportType());
        verify(subscriptionRepository).findByClientId(1L);
    }
}
