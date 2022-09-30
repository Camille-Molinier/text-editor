package implementations;

import Interfaces.Command;
import Interfaces.Receiver;

public class Insert implements Command {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // position parameter
    private int position;

    // insertion centent
    private String insert;
    private Receiver receiver;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Insert(String s, int pos) {
        position = pos;
        insert = s;
        receiver = new Engine();
    }
    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.insert(insert, position);
    }
}
