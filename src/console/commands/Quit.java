package console.commands;

class Quit extends Command {
  public int execute(String[] args) {
    c.writeln("Exiting...", false);
    System.exit(0);
    return 0;
  }
}
