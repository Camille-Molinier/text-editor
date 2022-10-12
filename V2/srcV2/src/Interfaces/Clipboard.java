package Interfaces;

public interface Clipboard {

  /**
   * Get clipboard content
   *
   * @return content String
   */
  String getContent();

  /**
   * Overwrite clipboard content
   *
   * @param s new content
   */
  void setContent(String s);
}
