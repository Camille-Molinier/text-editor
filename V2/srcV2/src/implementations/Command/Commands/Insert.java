package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;
import java.util.ArrayList;
import java.util.Arrays;

public class Insert implements Command {
  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  // position parameter
  private final int position;
  // insertion content
  private final String insert;
  private final Receiver receiver;
  private final Originator originator;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  public Insert(String s, int pos) {
    position = pos;
    insert = s;
    receiver = new Engine();
    originator = new MyOriginator();
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void execute() {
    receiver.insert(insert, position);
    originator.save(SimpleBuffer.getInstance().getContent(),
        new ArrayList<String>(Arrays.asList("Insert", insert, position + "")));
  }
}
