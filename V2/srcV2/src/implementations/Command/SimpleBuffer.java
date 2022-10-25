package implementations.Command;

import Interfaces.Buffer;
import java.util.Objects;

public class SimpleBuffer implements Buffer {
  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  // instance of SimpleBuffer
  private static volatile SimpleBuffer instance;
  // content string
  private String content;

  /************************************************************************************************/
  /*                                          Constructor                                         */
  /************************************************************************************************/
  private SimpleBuffer() {
    content = "";
  }

  /**
   * Get instance from SimpleBuffer singleton
   *
   * @return static volatile SimpleBuffer singleton instance
   */
  public static SimpleBuffer getInstance() {
    if (instance == null) {
      instance = new SimpleBuffer();
    }
    return instance;
  }

  /************************************************************************************************/
  /*                                            Methods                                           */
  /************************************************************************************************/
  @Override
  public void addContent(String s, int position) {
    // if content is empty, insert directly
    if (content.equals("")) {
      content += s;
    } else {
      // create empty string
      String tempString = "";

      // add char from 0 to insert position
      for (int i = 0; i < position; i++) {
        tempString += content.charAt(i);
      }

      // insert string
      tempString += s;

      // add the rest of content
      for (int i = position; i < content.length(); i++) {
        tempString += content.charAt(i);
      }

      // update content
      content = tempString;
    }
  }

  @Override
  public void deleteContent(int start, int stop) {
    // make sure content is not empty
    if (!content.equals("")) {
      // make sure start<stop
      if (start > stop) {
        int tmp = stop;
        stop = start;
        start = tmp;
      }

      // create empty string
      String tempString = "";

      // insert all char before start
      for (int i = 0; i < start; i++) {
        tempString += content.charAt(i);
      }

      // insert all char after stop
      for (int i = stop + 1; i < content.length(); i++) {
        tempString += content.charAt(i);
      }
      content = tempString;
    }
  }

  @Override
  public String getContent() {
    return content;
  }

}
