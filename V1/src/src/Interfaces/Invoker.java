package Interfaces;

public interface Invoker {
    /**
     * Set specific command
     * @param c : Command type to prepare
     */
    void setCommand(Command c);

    /**
     * Call execute methode for prepared command
     */
    void  executeCommand();

    /**
     * Indicate if a command is ready to be execute
     * @return true if command != null, false otherwise
     */
    boolean isReady();
}
