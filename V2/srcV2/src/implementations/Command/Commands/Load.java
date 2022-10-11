package implementations.Command.Commands;

import Interfaces.Command;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Command.SimpleBuffer;
import implementations.Memento.MyOriginator;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Load implements Command {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  private Receiver receiver;
  private Originator originator;
  private String preString;
  private String postString;
  private String script;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public Load(String content, int pos, String scriptName) {
    preString = content.substring(0, pos);
    postString = content.substring(pos, content.length());
    script = scriptName;

    receiver = new Engine();
    originator = new MyOriginator();
  }
  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public void execute() {
    try {
      File file = new File("./out/production/srcV2/scripts/" + script);
      Scanner sc = new Scanner(file);

      while (sc.hasNextLine()) {
        receiver.delete(0, SimpleBuffer.getInstance().getContent().length());
        receiver.insert(sc.nextLine(), 0);
      }
    } catch (Exception e) {System.out.println(e);}
    //originator.save(SimpleBuffer.getInstance().getContent(),
     //   new ArrayList<String>(Arrays.asList("Load", start+"", stop+"")));
  }
}
