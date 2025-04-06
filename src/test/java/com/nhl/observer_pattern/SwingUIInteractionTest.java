package com.nhl.observer_pattern;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.JOptionPane;

import static org.mockito.Mockito.mockStatic;

class SwingUIInteractionTest
{

    @Test
    void showMessage_whenCalled_shouldDisplayMessageDialog()
    {
        SwingUIInteraction uiInteraction = new SwingUIInteraction();

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            uiInteraction.showMessage("Test Message", "Test Title", JOptionPane.INFORMATION_MESSAGE);

            mockedJOptionPane.verify(() ->
                    JOptionPane.showMessageDialog(
                            null,
                            "Test Message",
                            "Test Title",
                            JOptionPane.INFORMATION_MESSAGE
                    )
            );
        }
    }
} 