package implementations.Memento;

import Interfaces.Memento;

public class Snapshot implements Memento {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // Saved content
  private  String content;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public Snapshot(String cont){
    content = cont;
  }

  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public String getContent() {
    return content;
  }

  @Override
  public boolean equals(Memento memento) {
    return content==memento.getContent();
  }
}
