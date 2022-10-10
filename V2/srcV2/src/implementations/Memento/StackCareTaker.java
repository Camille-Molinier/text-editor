package implementations.Memento;

import Interfaces.CareTaker;
import Interfaces.Memento;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackCareTaker implements CareTaker {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // Command list
  private Stack<Memento> commands;
  // old commands
  private Stack<Memento> old;
  // Instance
  private static volatile StackCareTaker instance;


  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  private StackCareTaker() {
    commands = new Stack<Memento>();
    old = new Stack<Memento>();
  }

  public static StackCareTaker getInstance() {
    if(instance==null) {
      instance = new StackCareTaker();
    }
    return instance;
  }

  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public void addMemento(Memento memento) {
    if(!old.isEmpty()){
      if(memento.equals(old.peek())){
        old.pop();
      }
    }
    commands.add(memento);
  }

  @Override
  public Memento getMemento() {
    if(!commands.isEmpty()) {
      Memento ret = commands.pop();
      old.add(ret);
      return showMemento();
    }
    return null;
  }

  @Override
  public Memento showMemento(){
    if(!commands.isEmpty()){
      return commands.peek();
    }
    return null;
  }

  @Override
  public List<Memento> getMementoList(Memento memento) {
    // Find memento location
    int loc = commands.indexOf(memento);
    // Return list from start to memento
    return commands.subList(loc+1, commands.size());
  }

  @Override
  public Memento renew() {
    if(!old.isEmpty()) {
      Memento tmp = old.pop();
      commands.add(tmp);
      return tmp;
    }
    return commands.peek();
  }
}
