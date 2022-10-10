package implementations.Command.Commands;

import Interfaces.Buffer;
import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;

public class Copy implements Command {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // start position parameter
    private int start;
    // stop position parameter
    private int stop;
    private Receiver receiver;
    private Originator originator;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Copy(int begin, int end) {
        start = begin-1;
        stop = end;

        // make sure start and stop are positive
        if(start<0){start=0;}
        if(stop<0){stop=0;}

        receiver = new Engine();
        originator = new MyOriginator();
    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.copy(start, stop);
        originator.save(SimpleBuffer.getInstance().getContent());
    }
}
