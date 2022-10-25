package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;
import java.util.ArrayList;
import java.util.Arrays;

public class Paste implements Command {
  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  // start position parameter
  private final int position;
  private final Receiver receiver;
  private final Originator originator;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  public Paste(int pos) {
    position = pos;

    receiver = new Engine();
    originator = new MyOriginator();
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void execute() {
    receiver.insertClipboard(position);
    originator.save(SimpleBuffer.getInstance().getContent(),
        new ArrayList<String>(Arrays.asList("Paste", position + "")));
  }
}
