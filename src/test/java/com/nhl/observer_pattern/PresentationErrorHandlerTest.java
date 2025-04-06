package com.nhl.observer_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.JOptionPane;

import static org.mockito.Mockito.*;

class PresentationErrorHandlerTest
{
    private PresentationErrorHandler errorHandler;

    @Mock
    private IUIInteraction mockUIInteraction;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        errorHandler = new PresentationErrorHandler(mockUIInteraction);
    }

    @Test
    void handleInvalidSlideNumber_whenNegativeNumberProvided_shouldShowWarningAndRedirectToFirstPage()
    {
        errorHandler.handleInvalidSlideNumber(-1, 0, 5);
        verify(mockUIInteraction).showMessage(
                "Invalid page number! Redirecting to the first page.",
                "Invalid Page",
                JOptionPane.WARNING_MESSAGE
        );
    }

    @Test
    void handleInvalidSlideNumber_whenNumberTooHighProvided_shouldShowWarningAndRedirectToLastPage()
    {
        errorHandler.handleInvalidSlideNumber(6, 0, 5);
        verify(mockUIInteraction).showMessage(
                "Invalid page number! Redirecting to the last page.",
                "Invalid Page",
                JOptionPane.WARNING_MESSAGE
        );
    }

    @Test
    void handleInvalidSlideNumber_whenValidNumberProvided_shouldNotShowMessage()
    {
        errorHandler.handleInvalidSlideNumber(3, 0, 5);
        verify(mockUIInteraction, never()).showMessage(anyString(), anyString(), anyInt());
    }

    @Test
    void handleInvalidSlideNumber_whenEmptyPresentation_shouldNotShowMessage()
    {
        errorHandler.handleInvalidSlideNumber(0, 0, 0);
        verify(mockUIInteraction, never()).showMessage(anyString(), anyString(), anyInt());
    }
} 