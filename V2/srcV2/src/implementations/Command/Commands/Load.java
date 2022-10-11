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
  // buffer content
  private String content;
  // script name
  private String script;
  // current position
  private int position;
  // command
  private Command command;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public Load(String cont, int pos, String scriptName) {
    script = scriptName;
    position = pos;
    content = cont;
    receiver = new Engine();
    originator = new MyOriginator();
  }
  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public void execute() {
    // try to open file and execute script
    try {
      // open file
      File file = new File("./out/production/srcV2/scripts/" + script);
      // create scanner to browse file
      Scanner sc = new Scanner(file);

      // browse file
      while (sc.hasNextLine()) {
        // summon command from line
        summonCommand(sc.nextLine());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    // save state
    originator.save(SimpleBuffer.getInstance().getContent(),
        new ArrayList<String>(Arrays.asList("Load", content, position + "", script)));
  }

  /**
   * Execute commands from line
   *
   * @param line
   */
  private void summonCommand(String line) {
    // parse list to get line in list of string list
    List<List<String>> lineList = getLineList(line);

    for (List<String> stringLine : lineList) {
      // switch first element
      switch (stringLine.get(0)) {
        // case copy function
        case "Copy":
          command = new Copy(Integer.parseInt(stringLine.get(1)),
              Integer.parseInt(stringLine.get(2)));
          break;
        // case cut function
        case "Cut":
          command = new Cut(Integer.parseInt(stringLine.get(1)),
              Integer.parseInt(stringLine.get(2)));
          break;
        // case delete function
        case "Delete":
          command = new Delete(Integer.parseInt(stringLine.get(1)));
          break;
        // case insert function
        case "Insert":
          command = new Insert(stringLine.get(1), Integer.parseInt(stringLine.get(2)));
          break;
        // case paste function
        case "Paste":
          command = new Paste(Integer.parseInt(stringLine.get(1)));
          break;
        // case redo function
        case "Redo":
          command = new Redo();
          break;
        // case replace function
        case "Replace":
          command = new Replace(Integer.parseInt(stringLine.get(1)),
              Integer.parseInt(stringLine.get(2)));
          break;
      }
      // execute command
      command.execute();
    }
  }

  /**
   * Get string list from line
   *
   * @param line string to parse
   * @return List<List < String>>
   */
  private List<List<String>> getLineList(String line) {
    // remove '[' and ']' at beginning and ending
    String content = line.substring(1, line.length() - 1);
    // split lines with \n
    List<String> split = Arrays.asList(content.split("\n"));

    // creat emty list
    List<List<String>> lineList = new ArrayList<List<String>>();
    for (String s : split) {
      // add parsed string in list
      lineList.add(Arrays.asList(s.split(", ")));
    }
    return lineList;
  }
}
