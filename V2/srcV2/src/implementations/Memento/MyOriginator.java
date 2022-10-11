package implementations.Memento;

import Interfaces.CareTaker;
import Interfaces.Memento;
import Interfaces.Originator;
import java.util.List;

public class MyOriginator implements Originator {

  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // Curent state
  private Memento currentState;
  // CareTaker
  private CareTaker careTaker;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/

  public MyOriginator() {
    careTaker = StackCareTaker.getInstance();
  }

  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public void save(String content, List<String> command) {
    Memento memento = new Snapshot(content, command);
    careTaker.addMemento(memento);
    currentState = memento;
  }

  @Override
  public Memento restore() {
    currentState = careTaker.getMemento();
    if(currentState==null){
      return null;
    }
    return currentState;
  }

  @Override
  public Memento respawn() {
    currentState = careTaker.renew();
    return currentState;
  }
}
