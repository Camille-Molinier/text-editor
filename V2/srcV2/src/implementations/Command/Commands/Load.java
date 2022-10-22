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
  private final Originator originator;
  // buffer content
  private final String content;
  // script name
  private final String script;
  // current position
  private final int position;
  // command
  private Command command;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public Load(String cont, int pos, String scriptName) {
    script = scriptName;
    position = pos;
    content = cont;
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
      System.out.println(e.getMessage());
    }
    // save state
    originator.save(SimpleBuffer.getInstance().getContent(),
        new ArrayList<String>(Arrays.asList("Load", content, position + "", script)));
  }

  /**
   * Execute commands from line
   *
   * @param line : String
   */
  private void summonCommand(String line) {
    // parse list to get line in list of string list
    List<List<String>> lineList = getLineList(line);

    for (List<String> stringLine : lineList) {
      // switch first element
      switch (stringLine.get(0)) {
        // case copy function
        case "Copy" -> command = new Copy(Integer.parseInt(stringLine.get(1)),
            Integer.parseInt(stringLine.get(2)));

        // case cut function
        case "Cut" -> command = new Cut(Integer.parseInt(stringLine.get(1)),
            Integer.parseInt(stringLine.get(2)));

        // case delete function
        case "Delete" -> command = new Delete(Integer.parseInt(stringLine.get(1)));

        // case insert function
        case "Insert" ->
            command = new Insert(getString(stringLine.get(1)), Integer.parseInt(stringLine.get(2)));

        // case paste function
        case "Paste" -> command = new Paste(Integer.parseInt(stringLine.get(1)));

        // case redo function
        case "Redo" -> command = new Redo();

        // case replace function
        case "Replace" -> command = new Replace(Integer.parseInt(stringLine.get(1)),
            Integer.parseInt(stringLine.get(2)));
      }
      // execute command
      command.execute();
    }
  }

  private String getString(String s) {
    if(s.equals("^")){return "\n";}
    return s;
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
    String[] split = content.split("\n");

    // creat emty list
    List<List<String>> lineList = new ArrayList<List<String>>();
    for (String s : split) {
      // add parsed string in list
      lineList.add(Arrays.asList(s.split(", ")));
    }
    return lineList;
  }
}
