package com.nhl;

import com.nhl.command_pattern.*;
import com.nhl.observer_pattern.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuController extends MenuBar
{
    private static final long serialVersionUID = 227L;
    private Frame parent;
    private Presentation presentation;
    private CommandInvoker commandInvoker;

    public MenuController(Frame frame, Presentation pres)
    {
        this(frame, pres, new CommandInvoker());
    }

    public MenuController(Frame frame, Presentation pres, CommandInvoker invoker)
    {
        this.parent = frame;
        this.presentation = pres;
        this.commandInvoker = invoker;
        setupMenu();
    }

    protected void setupMenu()
    {
        add(createFileMenu());
        add(createViewMenu());
        add(createEditMenu());
        add(createHelpMenu());
    }

    protected Menu createFileMenu()
    {
        Menu fileMenu = new Menu("File");

        fileMenu.add(createMenuItem("New", e -> executeCommand(new NewCommand(presentation, parent))));
        fileMenu.add(createMenuItem("Open", e -> executeCommand(new OpenCommand(presentation, parent))));
        fileMenu.add(createMenuItem("Save", e -> executeCommand(new SaveCommand(presentation, parent))));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Edit", e -> executeCommand(new EditCommand(presentation, parent))));
        fileMenu.add(createMenuItem("New Slide", e -> executeCommand(new AddNewSlideCommand(presentation))));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Undo", e ->
        {
            if (this.commandInvoker.canUndo())
            {
                this.commandInvoker.undo();
                this.parent.repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(parent, "Nothing to undo", "Undo", JOptionPane.INFORMATION_MESSAGE);
            }
        }));
        fileMenu.add(createMenuItem("Redo", e ->
        {
            if (commandInvoker.canRedo())
            {
                commandInvoker.redo();
                parent.repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(parent, "Nothing to redo", "Redo", JOptionPane.INFORMATION_MESSAGE);
            }
        }));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Exit", e -> executeCommand(new ExitCommand(presentation))));

        return fileMenu;
    }

    protected Menu createViewMenu()
    {
        Menu viewMenu = new Menu("View");
        viewMenu.add(createMenuItem("Next", e -> executeCommand(new NextSlideCommand(presentation))));
        viewMenu.add(createMenuItem("Previous", e -> executeCommand(new PrevSlideCommand(presentation))));
        viewMenu.add(createMenuItem("Go to", e -> executeCommand(new GoToCommand(presentation))));

        return viewMenu;
    }

    protected Menu createEditMenu()
    {
        Menu editMenu = new Menu("Edit");
        editMenu.add(createMenuItem("Add Slide", e -> executeCommand(new AddNewSlideCommand(presentation))));

        return editMenu;
    }

    protected Menu createHelpMenu()
    {
        Menu helpMenu = new Menu("Help");
        helpMenu.add(createMenuItem("About", e -> executeCommand(new AboutCommand(parent))));

        return helpMenu;
    }

    protected MenuItem createMenuItem(String label, ActionListener listener)
    {
        MenuItem menuItem = new MenuItem(label);
        menuItem.addActionListener(listener);

        return menuItem;
    }

    protected void executeCommand(Command command)
    {
        commandInvoker.executeCommand(command);
    }
}
