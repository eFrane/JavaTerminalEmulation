package console.commands;

import console.Console;

abstract class Command {
  Console c;

  public void setConsole(Console c) {
    this.c = c;
  }

  abstract public int execute(String[] args);
}
