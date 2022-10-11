package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;
import java.util.ArrayList;
import java.util.Arrays;

public class Cut implements Command {
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
    public Cut(int begin, int end) {
        start = begin;
        stop = end;

        // make sure start and stop are positives
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
        receiver.delete(start, stop);
        originator.save(SimpleBuffer.getInstance().getContent(),
            new ArrayList<String>(Arrays.asList("Cut", start+"", stop+"")));
    }
}
