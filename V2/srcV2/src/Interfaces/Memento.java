package Interfaces;

import java.util.List;

public interface Memento {

  /**
   * Return captured buffer state
   *
   * @return memento content
   */
  String getContent();

  /**
   * Retunr stored command
   *
   * @return command : Command
   */
  List<String> getCommand();

  /**
   * tell if two mementos are identic
   *
   * @param memento
   * @return true if mement content = this content
   */
  boolean equals(Memento memento);
}
