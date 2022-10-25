package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Memento;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;
import java.util.ArrayList;
import java.util.Arrays;

public class Redo implements Command {
  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  private final Receiver receiver;
  private final Originator originator;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  public Redo() {
    receiver = new Engine();
    originator = new MyOriginator();
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void execute() {
    Memento respawned = originator.respawn();
    receiver.delete(0, SimpleBuffer.getInstance().getContent().length());
    receiver.insert(respawned.getContent(), 0);
    originator.save(SimpleBuffer.getInstance().getContent(),
        new ArrayList<String>(Arrays.asList("Redo")));
  }
}
