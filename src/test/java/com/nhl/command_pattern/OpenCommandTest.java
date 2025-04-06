package com.nhl.command_pattern;

import com.nhl.Slide;
import com.nhl.XMLAccessor;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Frame;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenCommandTest {

    private OpenCommand command;
    
    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private Frame mockParent;
    
    @Mock
    private Slide mockSlide;
    
    @Mock
    private XMLAccessor mockAccessor;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        command = new OpenCommand(mockPresentation, mockParent);
    }
    
    @Test
    void constructor_whenCalled_shouldInitializeFields() {
        OpenCommand command = new OpenCommand(mockPresentation, mockParent);
        assertNotNull(command);
    }
    
    @Test
    void execute_whenFileSelected_shouldLoadPresentation() {
        try (MockedStatic<JFileChooser> mockedFileChooser = mockStatic(JFileChooser.class)) {
            // Setup mocks
            JFileChooser mockChooser = mock(JFileChooser.class);
            mockedFileChooser.when(JFileChooser::new).thenReturn(mockChooser);
            when(mockChooser.showOpenDialog(mockParent)).thenReturn(JFileChooser.APPROVE_OPTION);
            when(mockChooser.getSelectedFile()).thenReturn(new java.io.File("test.xml"));
            
            // Execute
            boolean result = command.execute();
            
            // Verify
            assertTrue(result);
            verify(mockPresentation).clear();
            verify(mockPresentation).setTitle(anyString());
        }
    }
    
    @Test
    void execute_whenUserCancels_shouldReturnFalse() {
        try (MockedStatic<JFileChooser> mockedFileChooser = mockStatic(JFileChooser.class)) {
            // Setup mocks
            JFileChooser mockChooser = mock(JFileChooser.class);
            mockedFileChooser.when(JFileChooser::new).thenReturn(mockChooser);
            when(mockChooser.showOpenDialog(mockParent)).thenReturn(JFileChooser.CANCEL_OPTION);
            
            // Execute
            boolean result = command.execute();
            
            // Verify
            assertFalse(result);
            verify(mockPresentation, never()).clear();
        }
    }
    
    @Test
    void execute_whenErrorOccurs_shouldShowErrorDialog() {
        try (MockedStatic<JFileChooser> mockedFileChooser = mockStatic(JFileChooser.class);
             MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            // Setup mocks
            JFileChooser mockChooser = mock(JFileChooser.class);
            mockedFileChooser.when(JFileChooser::new).thenReturn(mockChooser);
            when(mockChooser.showOpenDialog(mockParent)).thenReturn(JFileChooser.APPROVE_OPTION);
            when(mockChooser.getSelectedFile()).thenReturn(new java.io.File("nonexistent.xml"));
            
            // Execute
            boolean result = command.execute();
            
            // Verify
            assertFalse(result);
            mockedJOptionPane.verify(() -> 
                JOptionPane.showMessageDialog(
                    mockParent,
                    "Error loading presentation",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
            );
        }
    }
    
    @Test
    void undo_whenPresentationWasLoaded_shouldRestorePreviousState() {
        // Setup mocks
        Vector<Slide> previousSlides = new Vector<>();
        previousSlides.add(mockSlide);
        String previousTitle = "Previous Title";
        
        // Set previous state
        command = spy(new OpenCommand(mockPresentation, mockParent));
        doReturn(previousSlides).when(command).getPreviousSlides();
        doReturn(previousTitle).when(command).getPreviousTitle();
        
        // Execute
        boolean result = command.undo();
        
        // Verify
        assertTrue(result);
        verify(mockPresentation).clear();
        verify(mockPresentation).setTitle(previousTitle);
        verify(mockPresentation).setSlideNumber(0);
    }
    
    @Test
    void undo_whenNoPreviousState_shouldReturnFalse() {
        // Execute
        boolean result = command.undo();
        
        // Verify
        assertFalse(result);
        verify(mockPresentation, never()).clear();
    }
    
    @Test
    void getDescription_whenCalled_shouldReturnCorrectDescription() {
        assertEquals("Open Presentation", command.getDescription());
    }
} 