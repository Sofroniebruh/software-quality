package com.nhl;

import com.nhl.command_pattern.GoToCommand;
import com.nhl.command_pattern.NextSlideCommand;
import com.nhl.command_pattern.PrevSlideCommand;
import com.nhl.observer_pattern.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testNextSlideCommand()
    {
        NextSlideCommand next = new NextSlideCommand(presentation);
        next.execute();
        assertEquals(1, presentation.getSlideNumber());
        next.undo();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testPrevSlideCommand()
    {
        presentation.setSlideNumber(1);
        PrevSlideCommand prev = new PrevSlideCommand(presentation);
        prev.execute();
        assertEquals(0, presentation.getSlideNumber());
        prev.undo();
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testGoToCommandUndo()
    {
        presentation.setSlideNumber(0);
        TestableGoToCommand goTo = new TestableGoToCommand(presentation, "2");
        goTo.execute();
        assertEquals(1, presentation.getSlideNumber());
        goTo.undo();
        assertEquals(0, presentation.getSlideNumber());
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
        public void execute()
        {
            previousSlideNumber = getPresentation().getSlideNumber();
            int pageNumber = Integer.parseInt(testInput);
            getPresentation().setSlideNumber(pageNumber - 1);
        }
    }
}
