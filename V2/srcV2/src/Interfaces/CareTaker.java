package Interfaces;

import java.util.List;

public interface CareTaker {

  /**
   * Add memento in storage
   *
   * @param memento : Memento
   */
  void addMemento(Memento memento);

  /**
   * Return current memento
   *
   * @return top : Memento
   * @warning : use pop()
   */
  Memento getMemento();

  /**
   * Return current memento
   *
   * @return top : Memento
   * @warning : use peek()
   */
  Memento showMemento();

  /**
   * Get list of memento
   *
   * @param memento : start point
   * @return sublist starting from memento
   */
  List<Memento> getMementoList(Memento memento);

  /**
   * Renew last undone state
   *
   * @return memento : old memento
   */
  Memento renew();
}
