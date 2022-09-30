package implementations;

import Interfaces.Command;
import Interfaces.Receiver;

public class Cut implements Command {
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
    public Cut(int begin, int end) {
        start = begin;
        stop = end;

        if(start<0){start=0;}
        if(stop<0){stop=0;}

        receiver = new Engine();
    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.copy(start, stop);
        receiver.delete(start, stop);
    }
}
