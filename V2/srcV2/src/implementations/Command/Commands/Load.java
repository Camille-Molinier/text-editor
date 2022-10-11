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
import java.util.LinkedList;
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
  private int position;
  private Command command;

  /****************************************************************************************************/
  /*                                            Constructor                                           */
  /****************************************************************************************************/
  public Load(String content, int pos, String scriptName) {
    preString = content.substring(0, pos);
    postString = content.substring(pos, content.length());
    script = scriptName;
    position = pos;

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
        summonCommand(sc.nextLine());
      }
    } catch (Exception e) {System.out.println(e);}
    originator.save(SimpleBuffer.getInstance().getContent(),
      new ArrayList<String>(Arrays.asList("Load", preString+postString, position+"", script)));
  }

  private void summonCommand(String line){
    List<List<String>> lineList = getLineList(line);

    for(List<String> stringLine : lineList) {
      switch (stringLine.get(0)) {
        case "Copy" :
          command = new Copy(Integer.parseInt(stringLine.get(1)), Integer.parseInt(stringLine.get(2)));
          break;
        case "Cut" :
          command = new Cut(Integer.parseInt(stringLine.get(1)), Integer.parseInt(stringLine.get(2)));
          break;
        case "Delete" :
          command = new Delete(Integer.parseInt(stringLine.get(1)));
          break;
        case "Insert" :
          command = new Insert(stringLine.get(1), Integer.parseInt(stringLine.get(2)));
          break;
        case "Paste" :
          command = new Paste(Integer.parseInt(stringLine.get(1)));
          break;
        case "Redo" :
          command = new Redo();
          break;
        case "Replace" :
          command = new Replace(Integer.parseInt(stringLine.get(1)), Integer.parseInt(stringLine.get(2)));
          break;
      }
      command.execute();
    }
  }

  private List<List<String>> getLineList(String line){
    String content = line.substring(1, line.length()-1);
    List<String> splited = Arrays.asList(content.split("\n"));

    List<List<String>> lineList = new ArrayList<List<String>>();
    for (String s : splited) {
      lineList.add(Arrays.asList(s.split(", ")));
    }
    return lineList;
  }
}
