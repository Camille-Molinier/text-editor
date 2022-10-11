package implementations.Command;

import Interfaces.Command;
import Interfaces.Invoker;

public class DummyInvoker implements Invoker {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  private Command command;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public DummyInvoker() {
    // Nothing interesting here
  }

  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public void setCommand(Command c) {
    command = c;
  }

  @Override
  public void executeCommand() {
    // make sure there is a command
    if (command != null) {
      command.execute();
    }
    // reset command
    command = null;
  }
}
