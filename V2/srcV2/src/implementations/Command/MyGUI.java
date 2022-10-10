package implementations.Command;

import Interfaces.Buffer;
import Interfaces.GUI;
import Interfaces.Invoker;

import implementations.Command.Commands.Copy;
import implementations.Command.Commands.Cut;
import implementations.Command.Commands.Delete;
import implementations.Command.Commands.Insert;
import implementations.Command.Commands.Paste;
import implementations.Command.Commands.Redo;
import implementations.Command.Commands.Replace;
import implementations.Command.Commands.Script;
import implementations.Command.Commands.Undo;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyGUI extends JFrame implements GUI, ActionListener, KeyListener {
  /****************************************************************************************************/
  /*                                            Attributes                                            */
  /****************************************************************************************************/
  // instance
  private static volatile MyGUI instance;
  // invoker
  private Invoker invoker;
  // buffer instance
  private Buffer buffer;
  // caret position
  private int caretPosition;
  // Second caret position
  private int secondCaretPosition;

  /*------------------------------ Swing components ------------------------------*/
  // text Area
  private JTextArea textArea;
  // copy button
  private JButton copyButton;
  // cut button
  private JButton cutButton;
  // paste button
  private JButton pasteButton;
  // undo button
  private JButton undoButton;
  // redo button
  private JButton redoButton;
  // script button
  private JButton scriptButton;
  // execute script button
  private JButton executeScript;
  // caret position label
  private JLabel caretPosLabel;
  // caret position feild
  private JTextField caretPositionField;
  // 2nd caret position label
  private JLabel secondCaretPosLabel;
  // 2nd carret position feild
  private JTextField secondCaretPositionField;
  // clipborad indicator label
  private JLabel clipLabel;
  // clipboard content label
  private JLabel clipContentLabel;
  // Script combo box
  private JComboBox scriptSelector;
  // Script naming field
  private JTextField scriptNaming;
  // script naming label
  private JLabel namingLabel;

  /****************************************************************************************************/
  /*                                            Constructor                                           */

  /****************************************************************************************************/
  private MyGUI() {
    buffer = SimpleBuffer.getInstance();
    invoker = new DummyInvoker();
    caretPosition = 0;
    secondCaretPosition = 0;
    setupCanvas();
  }

  /**
   * Return GUI singleton instance
   *
   * @return
   */
  public static MyGUI getInstance() {
    if (instance == null) {
      instance = new MyGUI();
    }
    return instance;
  }

  /**
   * Setup canvas composition and style using Swing
   */
  private void setupCanvas() {
    // set JFrame to stop program on tab close
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // set canvas size
    this.setSize(800, 600);
    // set canvas title
    this.setTitle("Text editor");
    // change spawning position
    this.setLocationRelativeTo(null);

    // disable window resizing
    this.setResizable(false);
    // change content pane color
    this.getContentPane().setBackground(new Color(34, 31, 31));
    // set layout to row layout
    this.setLayout(new FlowLayout());
    // give focus to JFrame
    this.setFocusable(true);
    // get key focus to JFrame
    this.setFocusTraversalKeysEnabled(false);
    // add key listener
    this.addKeyListener(this);

    // create caret position label
    caretPosLabel = new JLabel("Caret position");
    caretPosLabel.setForeground(new Color(150, 150, 150));

    // create caret position label
    secondCaretPosLabel = new JLabel("2nd caret position");
    secondCaretPosLabel.setForeground(new Color(150, 150, 150));

    // create clipborad indicator label
    clipLabel = new JLabel("Clipboard content : ");
    clipLabel.setForeground(new Color(150, 150, 150));

    // create clipborad content label
    clipContentLabel = new JLabel("");
    clipContentLabel.setForeground(new Color(150, 150, 150));

    // create script naming label
    namingLabel = new JLabel("Script name");
    namingLabel.setForeground(new Color(150, 150, 150));

    // create copy button
    copyButton = new JButton("Copy");
    // add JFrame listener in button
    copyButton.addActionListener(this);
    copyButton.setBackground(new Color(150, 150, 150));
    copyButton.setBorder(new LineBorder(new Color(39, 39, 39)));

    // create copy button
    cutButton = new JButton("Cut");
    // add JFrame listener in button
    cutButton.addActionListener(this);
    cutButton.setBackground(new Color(150, 150, 150));
    cutButton.setBorder(new LineBorder(new Color(39, 39, 39)));

    // create copy button
    pasteButton = new JButton("Paste");
    // add JFrame listener in button
    pasteButton.addActionListener(this);
    pasteButton.setBackground(new Color(150, 150, 150));
    pasteButton.setBorder(new LineBorder(new Color(39, 39, 39)));

    // create undo button
    undoButton = new JButton("Undo");
    // add JFrame listener in button
    undoButton.addActionListener(this);
    undoButton.setBackground(new Color(150, 150, 150));
    undoButton.setBorder(new LineBorder(new Color(39, 39, 39)));

    // create undo button
    redoButton = new JButton("Redo");
    // add JFrame listener in button
    redoButton.addActionListener(this);
    redoButton.setBackground(new Color(150, 150, 150));
    redoButton.setBorder(new LineBorder(new Color(39, 39, 39)));

    // creat script button
    scriptButton = new JButton("Script");
    // add JFrame listener in button
    scriptButton.addActionListener(this);
    scriptButton.setBackground(new Color(150, 150, 150));
    scriptButton.setBorder(new LineBorder(new Color(39, 39, 39)));

    // creat script loading button
    executeScript = new JButton("Load");
    // add JFrame listener in button
    executeScript.addActionListener(this);
    executeScript.setBackground(new Color(150, 150, 150));
    executeScript.setBorder(new LineBorder(new Color(39, 39, 39)));

    // Script selector
    scriptSelector = new JComboBox();
    setScriptList();

    // configure text field for caret positioning
    caretPositionField = new JTextField();
    caretPositionField.setBackground(new Color(39, 39, 39));
    caretPositionField.setForeground(new Color(150, 150, 150));
    caretPositionField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    // set textfield size
    caretPositionField.setPreferredSize(new Dimension(100, 25));
    // add JFrame listener in textfield
    caretPositionField.addActionListener(this);

    // configure second text field for caret positioning
    secondCaretPositionField = new JTextField();
    secondCaretPositionField.setBackground(new Color(39, 39, 39));
    secondCaretPositionField.setForeground(new Color(150, 150, 150));
    secondCaretPositionField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    // set textfield size
    secondCaretPositionField.setPreferredSize(new Dimension(100, 25));
    // add JFrame listener in textfield
    secondCaretPositionField.addActionListener(this);

    // configure text field for caret positioning
    scriptNaming = new JTextField();
    scriptNaming.setBackground(new Color(39, 39, 39));
    scriptNaming.setForeground(new Color(150, 150, 150));
    scriptNaming.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    // set textfield size
    scriptNaming.setPreferredSize(new Dimension(100, 25));
    // add JFrame listener in textfield
    scriptNaming.addActionListener(this);

    // instantiate text area
    textArea = new JTextArea();
    // make text area not usable
    textArea.setEditable(false);
    // enable cursor visibility
    textArea.getCaret().setVisible(true);
    // disable autoscroll
    textArea.setAutoscrolls(false);
    // auto newline
    textArea.setLineWrap(true);
    // background color
    textArea.setBackground(new Color(39, 39, 39));
    // font color
    textArea.setForeground(Color.WHITE);
    // margins
    textArea.setMargin(new Insets(10, 10, 10, 10));
    // add JFrame listener in textarea
    textArea.addKeyListener(this);

    // instantiate scrollPane
    JScrollPane scrollPane = new JScrollPane(textArea);
    // setup dimension
    scrollPane.setPreferredSize(new Dimension(785, 550));
    // change background color
    scrollPane.getVerticalScrollBar().setBackground(new Color(44, 44, 44));
    // change scrollbar size
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 500));
    // change border color
    scrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
    // insert scroll pane in JFrame
    //this.add(scrollPane);

    // Creat panel
    JPanel panel = new JPanel();
    panel.setBackground(new Color(39, 39, 39));
    panel.setPreferredSize(new Dimension(775, 75));
    panel.setLayout(new GridLayout(4, 4));

    // insert components in panel
    panel.add(caretPosLabel, BorderLayout.WEST);
    panel.add(caretPositionField, BorderLayout.CENTER);
    panel.add(copyButton);
    panel.add(undoButton);
    panel.add(secondCaretPosLabel, BorderLayout.WEST);
    panel.add(secondCaretPositionField, BorderLayout.CENTER);
    panel.add(cutButton);
    panel.add(redoButton);
    panel.add(clipLabel, BorderLayout.SOUTH);
    panel.add(clipContentLabel, BorderLayout.SOUTH);
    panel.add(pasteButton);
    panel.add(scriptButton);
    panel.add(namingLabel);
    panel.add(scriptNaming);
    panel.add(scriptSelector);
    panel.add(executeScript);

    // add panel and scrollPane in frame
    this.add(panel);
    this.add(scrollPane);
    // set canvas as visible
    this.setVisible(true);
  }

  public void setScriptList() {
    scriptSelector.removeAllItems();
    File folder = new File("./out/production/srcV2/scripts");
    File[] files = folder.listFiles();

    if (files == null) {
      scriptSelector.addItem("");
    } else {
      for (File f : files) {
        scriptSelector.addItem(f.getName());
      }
    }

  }

  /****************************************************************************************************/
  /*                                   ActionListener implementation                                  */

  /****************************************************************************************************/
  @Override
  public void actionPerformed(ActionEvent e) {
    // if copy button is triggered
    if (e.getSource() == copyButton) {
      int start = caretPosition;
      int stop = secondCaretPosition;

      // make sure start>stop
      if (stop < start) {
        int tmp = start;
        start = stop;
        stop = tmp;
      }

      invoker.setCommand(new Copy(start, stop));
    }
    // if cut button is triggered
    if (e.getSource() == cutButton) {
      int start = caretPosition;
      int stop = secondCaretPosition;

      if (stop < start) {
        int tmp = start;
        start = stop;
        stop = tmp;
      }

      invoker.setCommand(new Cut(start, stop));
      caretPosition = caretPosition - (stop - start);
    }
    // if paste button is triggered
    if (e.getSource() == pasteButton) {
      if (caretPosition != secondCaretPosition) {
        invoker.setCommand(new Replace(caretPosition, secondCaretPosition));
      } else {
        invoker.setCommand(new Paste(caretPosition));
      }
    }
    // if undo button is triggered
    if (e.getSource() == undoButton) {
      invoker.setCommand(new Undo());
    }
    // if redo button is triggered
    if (e.getSource() == redoButton) {
      invoker.setCommand(new Redo());
    }
    // if script button is triggered
    if (e.getSource() == scriptButton) {
      if(scriptButton.getText()=="Script"){scriptButton.setText("Save");}
      else {scriptButton.setText("Script");}
      invoker.setCommand(Script.getInstance(scriptNaming.getText()));
      setScriptList();
    }
    // if script button is triggered
    if (e.getSource() == executeScript) {
      //TODO : load script
    }
    // if first caret textefield triggered
    if (e.getSource() == caretPositionField) {
      moveCursor(caretPositionField.getText(), 1);
    }
    // if second caret textfield is triggered
    if (e.getSource() == secondCaretPositionField) {
      moveCursor(secondCaretPositionField.getText(), 2);
    }
    invoker.executeCommand();
    update();
  }

  private void moveCursor(String s, int cursor) {
    int content = parseString2Int(s);
    // make sure position is not out of range
    if (content <= buffer.getContent().length()) {
      // if cursor = 1
      if (cursor == 1) {
        caretPosition = content;
      } else {
        secondCaretPosition = content;
      }
    } else { // Otherwise => set caret position at content end
      if (cursor == 1) {
        caretPosition = buffer.getContent().length();
      } else {
        secondCaretPosition = buffer.getContent().length();
      }
    }
  }

  private int parseString2Int(String s) {
    if (s.isEmpty()) {
      return 0;
    }
    boolean error = false;
    for (int i = 0; i < s.length(); i++) {
      if (!(s.charAt(i) == '0' || s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3'
          || s.charAt(i) == '4' ||
          s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7' || s.charAt(i) == '8'
          || s.charAt(i) == '9')) {
        caretPosition = 0;
        caretPositionField.setText(caretPosition + "");
        error = true;
        return buffer.getContent().length();
      }
    }
    // if no error => parse string to int and return
    return Integer.parseInt(s);
  }

  /****************************************************************************************************/
  /*                                    KeyListener implementation                                    */

  /****************************************************************************************************/
  @Override
  public void keyPressed(KeyEvent e) {
    if (isTextChar(e.getKeyCode())) {
      invoker.setCommand(new Insert(e.getKeyChar() + "", caretPosition));
      caretPosition++;
    } else if (e.getKeyCode() == 8) {
      if (caretPosition > 0) {
        invoker.setCommand(new Delete(caretPosition));
        caretPosition--;
      }
    }
    invoker.executeCommand();
    update();
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  /**
   * Indicate if a code is a texte character
   *
   * @param code : char code
   * @return true for [a-z] [0-9] & é " ' ( - è _ ç à ) = + ° $ £ * µ % ! § : / ; . , ? false
   * otherwise
   */
  private boolean isTextChar(int code) {
    return (65 <= code && 95 >= code) || (48 <= code && 57 >= code) || (code == 522) || (code == 61)
        || (code == 515) ||
        (code == 151) || (code == 0) || (code == 517) || (code == 513) || (code == 59) || (code
        == 44) || (code == 32);
  }

  /****************************************************************************************************/
  /*                                        GUI implementation                                        */

  /****************************************************************************************************/
  @Override
  public void update() {
    // set texterea contet with buffer content
    textArea.setText(buffer.getContent());
    // assert both carets are positives and not out buffer range
    if (caretPosition < 0) {
      caretPosition = 0;
    }
    if (caretPosition > buffer.getContent().length()) {
      caretPosition = buffer.getContent().length();
    }
    if (secondCaretPosition < 0) {
      secondCaretPosition = 0;
    }
    if (secondCaretPosition > buffer.getContent().length()) {
      secondCaretPosition = buffer.getContent().length();
    }

    // set carets labels
    caretPositionField.setText(caretPosition + "");
    secondCaretPositionField.setText(secondCaretPosition + "");
    // set clipborad label
    clipContentLabel.setText(SimpleClipboard.getInstance().getContent());
  }
}
