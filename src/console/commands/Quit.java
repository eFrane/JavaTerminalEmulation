package console.commands;

class Quit extends Command {
  public int execute(String[] args) {
    console.writeln("Exiting...", false);
    System.exit(0);
    return 0;
  }
}
