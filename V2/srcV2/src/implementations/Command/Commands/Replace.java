package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;
import java.util.ArrayList;
import java.util.Arrays;

public class Replace implements Command {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // start position parameter
    private int start;
    // End position parameter
    private int stop;
    private Receiver receiver;
    private Originator originator;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Replace(int begin, int end) {
        start = begin;
        stop = end;

        // make sure start < stop
        if(start>stop){
            int tmp = stop;
            stop = start;
            start = tmp;
        }

        receiver = new Engine();
        originator = new MyOriginator();
    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.delete(start, stop-1);
        receiver.insertClipboard(start);
        originator.save(SimpleBuffer.getInstance().getContent(),
            new ArrayList<String>(Arrays.asList("Replace", start+"", stop+"")));
    }
}
