package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;
import java.util.ArrayList;
import java.util.Arrays;

public class Delete implements Command {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // position parameter
  private final int position;
  private final Receiver receiver;
  private final Originator originator;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  public Delete(int pos) {
    position = pos;
    receiver = new Engine();
    originator = new MyOriginator();
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void execute() {
    receiver.delete(position - 1, position - 1);
    originator.save(SimpleBuffer.getInstance().getContent(),
        new ArrayList<String>(Arrays.asList("Delete", position + "")));
  }
}
