package Interfaces;

public interface Memento {

  /**
   * Return captured buffer state
   * @return memento content
   */
  String getContent();

  /**
   * tell if two mementos are identic
   * @param memento
   * @return true if mement content = this content
   */
  boolean equals(Memento memento);
}
