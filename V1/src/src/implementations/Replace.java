package implementations;

import Interfaces.Command;
import Interfaces.Receiver;

public class Replace implements Command {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // start position parameter
    private int start;
    // End position parameter
    private int stop;

    private Receiver receiver;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Replace(int begin, int end) {
        start = begin;
        stop = end;

        if(start>stop){
            int tmp = stop;
            stop = start;
            start = tmp;
        }

        receiver = new Engine();
    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.delete(start, stop-1);
        System.out.println(start);
        receiver.insertClipboard(start);
    }
}
