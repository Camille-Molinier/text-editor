package Interfaces;

public interface Invoker {

  /**
   * Set specific command
   *
   * @param c : Command type to prepare
   */
  void setCommand(Command c);

  /**
   * Call execute methode for prepared command
   */
  void executeCommand();
}
