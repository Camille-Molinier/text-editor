package implementations;

import Interfaces.Command;
import Interfaces.Receiver;

public class Copy implements Command {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // start position parameter
    private int start;
    // stop position parameter
    private int stop;
    private Receiver receiver;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Copy(int begin, int end) {
        start = begin;
        stop = end;
        receiver = new Engine();
    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.copy(start, stop);
    }
}
