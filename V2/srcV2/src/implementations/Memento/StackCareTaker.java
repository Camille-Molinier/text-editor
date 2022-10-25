package implementations.Memento;

import Interfaces.CareTaker;
import Interfaces.Memento;
import java.util.List;
import java.util.Stack;

public class StackCareTaker implements CareTaker {
  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  // command list
  private final Stack<Memento> commands;
  // old commands
  private final Stack<Memento> old;
  // instance
  private static volatile StackCareTaker instance;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  private StackCareTaker() {
    commands = new Stack<Memento>();
    old = new Stack<Memento>();
  }

  /**
   * Get instance from StackCareTaker singleton
   *
   * @return static volatile StackCareTaker singleton instance
   */
  public static StackCareTaker getInstance() {
    if (instance == null) {
      instance = new StackCareTaker();
    }
    return instance;
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void addMemento(Memento memento) {
    // if there is an old memento and it's equals to the memento to add, pop the old memento
    if (!old.isEmpty()) {
      if (memento.equals(old.peek())) {
        old.pop();
      }
    }
    // add memento to memento stack
    commands.add(memento);
  }

  @Override
  public Memento getMemento() {
    // if memento stack is not empty
    if (!commands.isEmpty()) {
      // pop memento
      Memento ret = commands.pop();
      // add to old memento stack
      old.add(ret);
      // return new memento stack top
      return showMemento();
    }
    // otherwise, return null instance
    return null;
  }

  @Override
  public Memento showMemento() {
    // if commands stack is not empty
    if (!commands.isEmpty()) {
      // peek top element
      return commands.peek();
    }
    // otherwise, return null instance
    return null;
  }

  @Override
  public List<Memento> getMementoList(Memento memento) {
    // Find memento location
    int loc = commands.indexOf(memento);
    // Return list from start to memento
    return commands.subList(loc + 1, commands.size());
  }

  @Override
  public Memento renew() {
    // if old memento stack is not empty
    if (!old.isEmpty()) {
      // pop top element
      Memento oldCommand = old.pop();
      // add it to command stack
      commands.add(oldCommand);
      // return old command
      return oldCommand;
    }
    // otherwise, return command top without modifications
    return commands.peek();
  }
}
