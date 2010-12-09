package console;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Console extends JTextArea {
  private static final long serialVersionUID = -5603283231339635077L;

  int currentOffset = 0;
  int currentLineOffset = 0;

  ConsoleKeyListener listener = new ConsoleKeyListener();

  String delimiter = " > ";
  String currentLineContent = "";

  LinkedList<Class<?>> commands = new LinkedList<Class<?>>();

  public Console(int rows, int columns) {
    super(rows, columns);
    addKeyListener(listener);
    setAutoscrolls(true);
    setLineWrap(true);
    setWrapStyleWord(true);
    setFont(new Font("Monaco", 0, 12));
    reset();
    loadCommands();
  }

  public void reset() {
    setText("");
    currentOffset = 0;
    write(delimiter);
    currentLineOffset = 0;
    currentLineContent = "";
  }

  public synchronized void write(String toWrite) {
    setOffsetToEnd();
    try {
      getDocument().insertString(currentOffset, toWrite, null);
    } catch (BadLocationException e) {
      // silence is golden
    }
    currentOffset += toWrite.length();
    setCaretPosition(currentOffset);
  }

  public synchronized void writeln(String toWrite) {
    writeln(toWrite, true);
  }

  public synchronized void writeln(String toWrite, boolean withDelimiter) {
    if (withDelimiter) {
      write(toWrite+"\n"+delimiter);
    } else {
      try {
        getDocument().insertString(0, toWrite+"\n", null);
      } catch (BadLocationException e) {
        // silence...
      }
      currentOffset += toWrite.length()+1;
      setCaretPosition(currentOffset);
    }
  }

  public String getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }

  public int getCurrentOffset() {
    return currentOffset;
  }

  public int getCurrentLineOffset() {
    return currentLineOffset;
  }

  public void setOffsetToEnd() {
    Document doc = getDocument();
    currentOffset = doc.getLength();
  }

  protected void loadCommands() {
    writeln("Loading commands...", false);

    PackageInspector pi = new PackageInspector("console.commands");
    commands = pi.getClasses("console.commands.Command");
  }

  public synchronized void executeCommand() {
    // TODO: give command to world

    currentLineOffset = 0;
    currentLineContent = "";
  }

  protected class ConsoleKeyListener implements KeyListener {
    public void keyPressed(KeyEvent e) {
      Console c = (Console) e.getSource();
      if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
        if (c.currentLineOffset == 0) {
          e.consume();
        }
      }
    }

    public void keyReleased(KeyEvent e) {
      Console c = (Console) e.getSource();
      if (!e.isActionKey())
        switch (e.getKeyCode()) {
          case KeyEvent.VK_ENTER:
            c.executeCommand();
            c.write(c.delimiter);
            break;

          case KeyEvent.VK_BACK_SPACE:
            if (c.currentLineOffset > 0) c.currentLineOffset--;
            try {
              c.currentLineContent.substring(0,
                  c.currentLineContent.length()-1);
            } catch (StringIndexOutOfBoundsException e1) {
              // silence is golden
            }
            break;

          default:
            c.currentLineOffset++;
            c.currentLineContent += String.valueOf(e.getKeyChar());
        }
      c.currentOffset = c.getCaretPosition();
    }

    public void keyTyped(KeyEvent e) {}
  }
}
