package implementations;

import Interfaces.Buffer;
import Interfaces.Clipboard;
import Interfaces.Receiver;

public class Engine implements Receiver {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // Clipborad
    private Clipboard clipboard;

    // Buffer
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
        // create empty string
        String copy = "";
        if(start==stop && stop==0) {
            copy = "";
        } else {
            if (buffer.getContent().isEmpty()) {
                copy = "";
            } else {
                // add buffer string between start and stop
                for (int i = start; i <= stop; i++) {
                    copy += buffer.getContent().charAt(i);
                }
            }
        }
        // update clipboard
        clipboard.setContent(copy);
        System.out.println(clipboard.getContent());
    }

    @Override
    public void paste(int position) {

    }

    @Override
    public void insert(String c, int position) {
        buffer.addContent(c, position);
    }

    @Override
    public void delete(int debut, int fin) {
        buffer.deleteContent(debut, fin);
    }
}
