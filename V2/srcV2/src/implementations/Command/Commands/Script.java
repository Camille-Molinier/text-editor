package implementations.Command.Commands;

import Interfaces.CareTaker;
import Interfaces.Command;
import Interfaces.Memento;
import Interfaces.Originator;
import Interfaces.Receiver;
import implementations.Command.Engine;
import implementations.Memento.MyOriginator;
import implementations.Memento.StackCareTaker;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

public class Script implements Command {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  private Receiver receiver;
  private Originator originator;
  private CareTaker careTaker;
  private boolean isSaving;
  private String scriptName;
  private Memento startPoint;
  private static volatile Script instance;

  /****************************************************************************************************/
  /*                                            Constructor                                           */

  /****************************************************************************************************/
  private Script() {
    isSaving = true;
    receiver = new Engine();
    originator = new MyOriginator();
    careTaker = StackCareTaker.getInstance();
  }

  public static Script getInstance(String name) {
    if (instance == null) {
      instance = new Script();
    }
    instance.scriptName = name;
    return instance;
  }

  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public void execute() {
    if(isSaving){
      prepare();
      isSaving = false;
    } else {
      save();
      isSaving = true;
    }
  }

  /**
   * Initiate script
   */
  private void prepare() {
    startPoint = careTaker.showMemento();
    isSaving = false;
  }

  /**
   * Save script in text file
   */
  private void save(){
    List<Memento> scriptList = careTaker.getMementoList(startPoint);

    try {
      System.out.println();
      File output = new File("./out/production/srcV2/scripts/"+ scriptName);
      output.getParentFile().mkdirs();
      output.createNewFile();

      Writer writer = new FileWriter(output);

      for(Memento memento : scriptList) {
        writer.write(memento.getContent()+"\n");
      }
      writer.close();
    }
      catch (Exception e) {
      System.out.println("Error saving script");
      e.printStackTrace();
    }
  }
}
