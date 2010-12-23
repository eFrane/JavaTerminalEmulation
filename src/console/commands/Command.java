package console.commands;

import console.Console;

abstract public class Command {
  Console console;

  public void setConsole(Console c) {
    this.console = c;
  }

  abstract public int execute(String[] args);
}
