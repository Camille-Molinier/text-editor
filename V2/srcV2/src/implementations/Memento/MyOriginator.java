package implementations.Memento;

import Interfaces.CareTaker;
import Interfaces.Memento;
import Interfaces.Originator;

public class MyOriginator implements Originator {

  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // Curent state
  private String currentState;
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
  public void save(String content) {
    Memento memento = new Snapshot(content);
    careTaker.addMemento(memento);
    currentState = content;
  }

  @Override
  public String restore() {
    Memento currentMemento = careTaker.getMemento();
    if(currentMemento==null){
      return "";
    }
    currentState = currentMemento.getContent();
    return currentState;
  }

  @Override
  public String respawn() {
    currentState = careTaker.renew().getContent();
    return currentState;
  }
}
