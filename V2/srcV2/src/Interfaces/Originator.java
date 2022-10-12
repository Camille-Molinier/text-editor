package Interfaces;

import java.util.List;

public interface Originator {

  /**
   * Save content
   */
  void save(String content, List<String> command);

  /**
   * Restore previous tate
   *
   * @return new current state
   */
  Memento restore();

  /**
   * Restore from old commands
   *
   * @return new current state
   */
  Memento respawn();
}
