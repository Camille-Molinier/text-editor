package implementations;

import Interfaces.Command;
import Interfaces.Invoker;

public class DummyInvoker implements Invoker {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    private Command command;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public DummyInvoker(){

    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void setCommand(Command c) {
        command = c;
    }

    @Override
    public void executeCommand() {
        if(command!=null){
            command.execute();
        }
        command = null;
    }

    @Override
    public boolean isReady() {
        return command!=null;
    }
}
