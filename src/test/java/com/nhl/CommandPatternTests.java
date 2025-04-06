package com.nhl;

import com.nhl.command_pattern.GoToCommand;
import com.nhl.command_pattern.NextSlideCommand;
import com.nhl.command_pattern.PrevSlideCommand;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.swing.JOptionPane;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandPatternTests
{
    private Presentation presentation;

    @BeforeEach
    public void setUp()
    {
        presentation = new Presentation();
        Slide slide1 = new Slide();
        slide1.setTitle("Slide 1");
        Slide slide2 = new Slide();
        slide2.setTitle("Slide 2");
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(0);
    }

    @Test
    public void nextSlideCommand_whenExecuted_shouldMoveToNextSlide()
    {
        NextSlideCommand next = new NextSlideCommand(presentation);
        next.execute();
        assertEquals(1, presentation.getSlideNumber());
        next.undo();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void prevSlideCommand_whenExecuted_shouldMoveToPreviousSlide()
    {
        presentation.setSlideNumber(1);
        PrevSlideCommand prev = new PrevSlideCommand(presentation);
        prev.execute();
        assertEquals(0, presentation.getSlideNumber());
        prev.undo();
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void goToCommand_whenExecuted_shouldMoveToSpecifiedSlide()
    {
        presentation.setSlideNumber(0);
        TestableGoToCommand goTo = new TestableGoToCommand(presentation, "2");
        goTo.execute();
        assertEquals(1, presentation.getSlideNumber());
        goTo.undo();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void goToCommand_whenDialogShowsValidInput_shouldNavigateToSpecifiedSlide()
    {
        presentation.setSlideNumber(0);

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn("2");

            GoToCommand goTo = new GoToCommand(presentation);
            boolean result = goTo.execute();

            mockedJOptionPane.verify(() -> JOptionPane.showInputDialog(GoToCommand.PAGENR));

            assertEquals(1, presentation.getSlideNumber());
            assertTrue(result);
        }
    }

    @Test
    public void goToCommand_whenDialogShowsInvalidInput_shouldNotNavigateAndReturnFalse()
    {
        presentation.setSlideNumber(0);

        try (MockedStatic<JOptionPane> mockedJOptionPane = mockStatic(JOptionPane.class))
        {
            mockedJOptionPane.when(() -> JOptionPane.showInputDialog(anyString())).thenReturn("invalid");

            mockedJOptionPane.when(() -> JOptionPane.showConfirmDialog(any(), anyString(), anyString(), anyInt()))
                    .thenReturn(JOptionPane.NO_OPTION);

            GoToCommand goTo = new GoToCommand(presentation);
            boolean result = goTo.execute();

            mockedJOptionPane.verify(() -> JOptionPane.showInputDialog(GoToCommand.PAGENR));
            mockedJOptionPane.verify(() -> JOptionPane.showConfirmDialog(any(), anyString(), anyString(), anyInt()));

            assertEquals(0, presentation.getSlideNumber());
            assertFalse(result);
        }
    }

    @Test
    public void getDescription_whenCalled_shouldReturnCorrectDescription()
    {
        GoToCommand goTo = new GoToCommand(presentation);
        assertEquals("Go to Slide", goTo.getDescription());
    }

    private static class TestableGoToCommand extends GoToCommand
    {
        private String testInput;

        public TestableGoToCommand(Presentation presentation, String input)
        {
            super(presentation);
            this.testInput = input;
        }

        @Override
        public boolean execute()
        {
            int pageNumber = Integer.parseInt(testInput);
            getPresentation().setSlideNumber(pageNumber - 1);
            return true;
        }
    }
}
