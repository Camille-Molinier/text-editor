package implementations.Command;

import Interfaces.Clipboard;

public class SimpleClipboard implements Clipboard {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // instance of SimpleBuffer
  private static volatile SimpleClipboard instance;
  // content string
  private String content;

  /****************************************************************************************************/
  /*                                            Constructor                                           */

  /****************************************************************************************************/
  private SimpleClipboard() {
    content = "";
  }

  /**
   * Get instance from SimpleClipboard singleton
   *
   * @return static volatile SimpleClipboard singleton instance
   */
  public static SimpleClipboard getInstance() {
    if (instance == null) {
      instance = new SimpleClipboard();
    }
    return instance;
  }

  /****************************************************************************************************/
  /*                                              Methods                                             */
  /****************************************************************************************************/
  @Override
  public String getContent() {
    return content;
  }

  @Override
  public void setContent(String s) {
    content = s;
  }
}
