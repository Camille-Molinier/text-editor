package implementations.Command.Commands;

import Interfaces.CareTaker;
import Interfaces.Command;
import Interfaces.Memento;
import implementations.Memento.StackCareTaker;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

public class Script implements Command {
  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  private final CareTaker careTaker;
  private boolean isSaving;
  private String scriptName;
  private Memento startPoint;
  private static volatile Script instance;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  private Script() {
    isSaving = true;
    careTaker = StackCareTaker.getInstance();
  }

  public static Script getInstance(String name) {
    if (instance == null) {
      instance = new Script();
    }
    instance.scriptName = name;
    return instance;
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void execute() {
    if (isSaving) {
      prepare();
      isSaving = false;
    } else {
      List<Memento> scriptList = careTaker.getMementoList(startPoint);
      save(scriptList);
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
  private void save(List<Memento> scriptList) {
    try {
      // create new file
      File output = new File("./out/production/srcV2/scripts/" + scriptName);

      // create wiriter
      Writer writer = new FileWriter(output);

      // write all commands in file with \n separator
      for (Memento memento : scriptList) {
        String command = memento.getCommand().toString();
        if (command.contains("\n")) {
          command = command.replace("\n", "^");
        }
        writer.write(command + "\n");
      }
      writer.close();
    } catch (Exception e) {
      System.out.println("Error saving script");
      e.printStackTrace();
    }

  }
}
