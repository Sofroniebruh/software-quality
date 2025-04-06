package com.nhl.command_pattern;

public interface Command
{
    boolean execute();
    boolean undo();
    String getDescription();
}
