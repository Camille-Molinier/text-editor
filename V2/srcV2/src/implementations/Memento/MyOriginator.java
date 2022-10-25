package implementations.Memento;

import Interfaces.CareTaker;
import Interfaces.Memento;
import Interfaces.Originator;
import java.util.List;

public class MyOriginator implements Originator {

  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  // current state
  private Memento currentState;
  // careTaker
  private final CareTaker careTaker;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  public MyOriginator() {
    careTaker = StackCareTaker.getInstance();
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void save(String content, List<String> command) {
    // create new Memento object with data to save
    Memento memento = new Snapshot(content, command);
    // add Memento in the CareTaker storage
    careTaker.addMemento(memento);
    // set originator current state
    currentState = memento;
  }

  @Override
  public Memento restore() {
    // get current memento (will be popped)
    currentState = careTaker.getMemento();
    // in case of null memento (empty careTaker)
    if (currentState == null) {
      //return  null object
      return null;
    }
    // otherwise, return got memento
    return currentState;
  }

  @Override
  public Memento respawn() {
    // revive last undone command
    currentState = careTaker.renew();
    return currentState;
  }
}
