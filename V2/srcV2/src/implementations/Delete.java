package implementations;

import Interfaces.Command;
import Interfaces.Receiver;

public class Delete implements Command {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // position parameter
    private int position;
    private Receiver receiver;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Delete(int pos) {
        position = pos;
        receiver = new Engine();
    }
    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.delete(position-1, position);
    }
}
