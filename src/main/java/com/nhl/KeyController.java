package com.nhl;

import com.nhl.command_pattern.Command;
import com.nhl.command_pattern.ExitCommand;
import com.nhl.command_pattern.NextSlideCommand;
import com.nhl.command_pattern.PrevSlideCommand;
import com.nhl.observer_pattern.Presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyController extends KeyAdapter
{
    private Command nextSlideCommand;
    private Command prevSlideCommand;
    private Command exitCommand;

    public KeyController(Presentation presentation)
    {
        this.nextSlideCommand = new NextSlideCommand(presentation);
        this.prevSlideCommand = new PrevSlideCommand(presentation);
        this.exitCommand = new ExitCommand(presentation);
    }

    public Command getNextSlideCommand()
    {
        return this.nextSlideCommand;
    }

    public void setNextSlideCommand(Command nextSlideCommand)
    {
        this.nextSlideCommand = nextSlideCommand;
    }

    public Command getPrevSlideCommand()
    {
        return this.prevSlideCommand;
    }

    public void setPrevSlideCommand(Command prevSlideCommand)
    {
        this.prevSlideCommand = prevSlideCommand;
    }

    public Command getExitCommand()
    {
        return this.exitCommand;
    }

    public void setExitCommand(Command exitCommand)
    {
        this.exitCommand = exitCommand;
    }

    public void keyPressed(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case '+':
                this.nextSlideCommand.execute();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case '-':
                this.prevSlideCommand.execute();
                break;
            case 'q':
            case 'Q':
                this.exitCommand.execute();
                break;
            default:
                break;
        }
    }
}
