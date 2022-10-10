package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;

public class Delete implements Command {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // position parameter
    private int position;
    private Receiver receiver;
    private Originator originator;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Delete(int pos) {
        position = pos;
        receiver = new Engine();
        originator = new MyOriginator();
    }
    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void execute() {
        receiver.delete(position-1, position);
        originator.save(SimpleBuffer.getInstance().getContent());
    }
}
