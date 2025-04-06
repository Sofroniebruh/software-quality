package com.nhl.command_pattern;

import com.nhl.Slide;
import com.nhl.SlideItem;
import com.nhl.TextItem;
import com.nhl.XMLAccessor;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import javax.swing.JOptionPane;
import java.awt.Frame;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditCommandTest {

    private EditCommand command;
    
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
        command = new EditCommand(mockPresentation, mockParent);
    }
    
    @Test
    void constructor_whenCalled_shouldInitializeFields() {
        EditCommand command = new EditCommand(mockPresentation, mockParent);
        assertNotNull(command);
    }
    
    @Test
    void execute_whenTextItemSelected_shouldAddTextItem() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            // Setup mocks
            when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);
            when(mockSlide.getSlideItems()).thenReturn(new java.util.Vector<>());
            mockedJOptionPane.when(() -> JOptionPane.showOptionDialog(
                    any(), anyString(), anyString(), anyInt(), anyInt(), any(), any(), any()
            )).thenReturn(0); // Select text option
            
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString()))
                    .thenReturn("Test Text");
            
            mockedJOptionPane.when(() -> JOptionPane.showOptionDialog(
                    any(), anyString(), anyString(), anyInt(), anyInt(), any(), any(), any()
            )).thenReturn(0); // Select level 1
            
            // Execute
            boolean result = command.execute();
            
            // Verify
            assertTrue(result);
            verify(mockSlide).append(any(TextItem.class));
            verify(mockParent).repaint();
        }
    }
    
    @Test
    void execute_whenImageItemSelected_shouldAddImageItem() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            // Setup mocks
            when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);
            when(mockSlide.getSlideItems()).thenReturn(new java.util.Vector<>());
            mockedJOptionPane.when(() -> JOptionPane.showOptionDialog(
                    any(), anyString(), anyString(), anyInt(), anyInt(), any(), any(), any()
            )).thenReturn(1); // Select image option
            
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString()))
                    .thenReturn("test-image.jpg");
            
            mockedJOptionPane.when(() -> JOptionPane.showOptionDialog(
                    any(), anyString(), anyString(), anyInt(), anyInt(), any(), any(), any()
            )).thenReturn(0); // Select level 1
            
            // Execute
            boolean result = command.execute();
            
            // Verify
            assertTrue(result);
            verify(mockSlide).append(any(SlideItem.class));
            verify(mockParent).repaint();
        }
    }
    
    @Test
    void execute_whenNoSlideSelected_shouldShowError() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            // Setup mocks
            when(mockPresentation.getCurrentSlide()).thenReturn(null);
            
            // Execute
            boolean result = command.execute();
            
            // Verify
            assertFalse(result);
            mockedJOptionPane.verify(() -> 
                JOptionPane.showMessageDialog(
                    mockParent, 
                    "Please select a slide first", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                )
            );
        }
    }
    
    @Test
    void execute_whenUserCancels_shouldReturnFalse() {
        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class)) {
            // Setup mocks
            when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);
            mockedJOptionPane.when(() -> JOptionPane.showOptionDialog(
                    any(), anyString(), anyString(), anyInt(), anyInt(), any(), any(), any()
            )).thenReturn(JOptionPane.CANCEL_OPTION);
            
            // Execute
            boolean result = command.execute();
            
            // Verify
            assertFalse(result);
        }
    }
    
    @Test
    void undo_whenItemWasAdded_shouldRemoveItem() {
        // Setup mocks
        when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);
        when(mockSlide.removeItem(anyInt())).thenReturn(true);
        
        // Execute
        boolean result = command.undo();
        
        // Verify
        assertTrue(result);
        verify(mockSlide).removeItem(anyInt());
        verify(mockParent).repaint();
    }
    
    @Test
    void undo_whenNoItemWasAdded_shouldReturnFalse() {
        // Setup mocks
        when(mockPresentation.getCurrentSlide()).thenReturn(mockSlide);
        
        // Execute
        boolean result = command.undo();
        
        // Verify
        assertFalse(result);
        verify(mockSlide, never()).removeItem(anyInt());
    }
    
    @Test
    void getDescription_whenCalled_shouldReturnCorrectDescription() {
        assertEquals("Edit Slide", command.getDescription());
    }
} 