package com.nhl.observer_pattern;

import javax.swing.JOptionPane;

public class SwingUIInteraction implements IUIInteraction
{
    @Override
    public void showMessage(String message, String title, int messageType)
    {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
} 