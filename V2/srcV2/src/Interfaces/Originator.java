package Interfaces;

public interface Originator {

  /**
   * Save content
   */
  void save(String content);

  /**
   * Restore previous state
   * @return new current state
   */
  String restore();

  /**
   * Restore from old commands
   * @return new current state
   */
  String respawn();
}
