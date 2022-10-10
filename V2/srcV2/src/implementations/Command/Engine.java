package implementations.Command;

import Interfaces.Buffer;
import Interfaces.Clipboard;
import Interfaces.Receiver;

public class Engine implements Receiver {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // clipborad
    private Clipboard clipboard;

    // buffer
    private Buffer buffer;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    public Engine(){
        clipboard = SimpleClipboard.getInstance();
        buffer = SimpleBuffer.getInstance();
    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void copy(int start, int stop) {
        String copy = "";
        // if all cursor == 0 => return empty string
        if(start==stop && stop==0) {
            copy = "";
        } else {
            // if there is nothing in buffer => return empty string
            if (buffer.getContent().isEmpty()) {
                copy = "";
            } else {
                if(stop>=buffer.getContent().length()){stop--;}
                // add buffer string between start and stop
                for (int i = start; i <= stop; i++) {
                    copy += buffer.getContent().charAt(i);
                }
            }
        }
        // update clipboard
        clipboard.setContent(copy);
    }

    @Override
    public void insert(String c, int position) {
        buffer.addContent(c, position);
    }

    @Override
    public void insertClipboard(int position) {
        buffer.addContent(clipboard.getContent(), position);
    }

    @Override
    public void delete(int start, int end) {
        buffer.deleteContent(start, end);
    }
}
