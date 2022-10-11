package implementations.Memento;

import Interfaces.Memento;
import java.util.List;

public class Snapshot implements Memento {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // Saved content
  private  String content;
  private List<String> command;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public Snapshot(String cont, List<String> comm){
    content = cont;
    command = comm;
  }

  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public String getContent() {
    return content;
  }

  @Override
  public List<String> getCommand() {
    return command;
  }

  @Override
  public boolean equals(Memento memento) {
    return content==memento.getContent() && command.equals(memento.getCommand());
  }
}
