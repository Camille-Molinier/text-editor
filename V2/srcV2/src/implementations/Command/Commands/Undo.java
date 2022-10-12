package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Memento;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;

public class Undo implements Command {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  private Receiver receiver;
  private Originator originator;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public Undo() {
    receiver = new Engine();
    originator = new MyOriginator();
  }
  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public void execute() {
    Memento restore = originator.restore();
    receiver.delete(0, SimpleBuffer.getInstance().getContent().length() - 1);
    receiver.insert(restore.getContent(), 0);
  }
}
