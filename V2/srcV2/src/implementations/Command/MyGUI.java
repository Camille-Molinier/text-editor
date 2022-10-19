package implementations.Command;

import Interfaces.Buffer;
import Interfaces.GUI;
import Interfaces.Invoker;

import implementations.Command.Commands.Copy;
import implementations.Command.Commands.Cut;
import implementations.Command.Commands.Delete;
import implementations.Command.Commands.Insert;
import implementations.Command.Commands.Load;
import implementations.Command.Commands.Paste;
import implementations.Command.Commands.Redo;
import implementations.Command.Commands.Replace;
import implementations.Command.Commands.Script;
import implementations.Command.Commands.Undo;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

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
  // script button
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
  // script naming field
  private JTextField scriptNaming;
  // script naming label
  private JLabel namingLabel;
  // informative label
  private JLabel informativeLabel;
  // main menu bar
  JMenuBar menuBar;
  // edit menu
  JMenu editMenu;
  // script menu
  JMenu scriptMenu;
  // system menu
  JMenu systemMenu;
  // exit menu button
  JMenuItem exitItem;
  // copy menu button
  JMenuItem copyItem;
  // cut menu button
  JMenuItem cutItem;
  // paste menu button
  JMenuItem pasteItem;
  // undo menu button
  JMenuItem undoItem;
  // redo menu button
  JMenuItem redoItem;
  // script start menu button
  JMenuItem startItem;
  // script load menu button
  JMenu loadItem;
  // List of scripts submenus => dynamic list of existing scripts
  ArrayList<JMenuItem> scripts;

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
   * Get instance from MyGUI singleton
   *
   * @return static volatile MyGUI singleton instance
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
    //this.getContentPane().setBackground(new Color(34, 31, 31));
    // set layout to row layout
    this.setLayout(new FlowLayout());
    // give focus to JFrame
    this.setFocusable(true);
    // get key focus to JFrame
    this.setFocusTraversalKeysEnabled(false);
    // add key listener
    this.addKeyListener(this);
    // change image icon
    ImageIcon icon = new ImageIcon("./out/production/srcV2/dat/shark-emoji.png");
    this.setIconImage(icon.getImage());


    // create caret position label
    caretPosLabel = new JLabel("Caret position         :");

    // create caret position label
    secondCaretPosLabel = new JLabel("2nd caret position :");

    // create clipborad indicator label
    clipLabel = new JLabel("Clipboard content :");

    // create clipborad content label
    clipContentLabel = new JLabel("");

    // create script naming label
    namingLabel = new JLabel("Script name           :");

    // create clipborad indicator label
    informativeLabel = new JLabel("");

    // configure text field for caret positioning
    caretPositionField = new JTextField();
    // add JFrame listener in textfield
    caretPositionField.addActionListener(this);

    // configure second text field for caret positioning
    secondCaretPositionField = new JTextField();
    // add JFrame listener in textfield
    secondCaretPositionField.addActionListener(this);

    // configure text field for caret positioning
    scriptNaming = new JTextField();
    // add JFrame listener in textfield
    scriptNaming.addActionListener(this);

    // instantiate text area
    textArea = new JTextArea();
    // make text area not usable
    textArea.setEditable(false);
    // disable autoscroll
    textArea.setAutoscrolls(false);
    // auto newline
    textArea.setLineWrap(true);
    // margins
    textArea.setMargin(new Insets(10, 10, 10, 10));
    // add JFrame listener in textarea
    textArea.addKeyListener(this);
    textArea.addCaretListener(new CaretListener() {
      @Override
      public void caretUpdate(CaretEvent e) {
        System.out.println(e.getDot() + " | " + e.getMark() + " | " + e.getSource());
      }
    });

    // instantiate scrollPane
    JScrollPane scrollPane = new JScrollPane(textArea);
    // setup dimension
    scrollPane.setPreferredSize(new Dimension(785, 550));
    // change scrollbar size
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 500));

    // create panel
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(775, 80));
    panel.setLayout(new GridLayout(2, 2, 20, 5));

    // insert components in panel
    JPanel cursorPanel = new JPanel(new GridLayout(2, 2, 10, 5));
    cursorPanel.add(caretPosLabel);
    cursorPanel.add(caretPositionField);
    cursorPanel.add(secondCaretPosLabel);
    cursorPanel.add(secondCaretPositionField);

    panel.add(cursorPanel);

    JPanel othersPanel = new JPanel(new GridLayout(2, 2, 0, 0));
    othersPanel.add(clipLabel);
    othersPanel.add(clipContentLabel);
    othersPanel.add(namingLabel);
    othersPanel.add(scriptNaming);
    panel.add(othersPanel);

    JPanel infoPanel = new JPanel(new GridLayout(1, 1, 10, 5));
    infoPanel.add(informativeLabel);
    panel.add(infoPanel);

    // create menu bar
    menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(800, 20));

    editMenu = new JMenu("edit");
    scriptMenu = new JMenu("script");
    systemMenu = new JMenu("system");

    copyItem = new JMenuItem("copy");
    copyItem.addActionListener(this);
    ImageIcon copyIcon = new ImageIcon("./out/production/srcV2/dat/copy.png");
    copyItem.setIcon(copyIcon);


    cutItem = new JMenuItem("cut");
    cutItem.addActionListener(this);
    ImageIcon cutIcon = new ImageIcon("./out/production/srcV2/dat/cut.png");
    cutItem.setIcon(cutIcon);

    pasteItem = new JMenuItem("paste");
    pasteItem.addActionListener(this);
    ImageIcon pasteIcon = new ImageIcon("./out/production/srcV2/dat/paste.png");
    pasteItem.setIcon(pasteIcon);

    undoItem = new JMenuItem("undo");
    undoItem.addActionListener(this);
    ImageIcon undoIcon = new ImageIcon("./out/production/srcV2/dat/undo.png");
    undoItem.setIcon(undoIcon);

    redoItem = new JMenuItem("redo");
    redoItem.addActionListener(this);
    ImageIcon redoIcon = new ImageIcon("./out/production/srcV2/dat/redo.png");
    redoItem.setIcon(redoIcon);

    editMenu.add(copyItem);
    editMenu.add(cutItem);
    editMenu.add(pasteItem);
    editMenu.addSeparator();
    editMenu.add(undoItem);
    editMenu.add(redoItem);

    startItem = new JMenuItem("start");
    startItem.addActionListener(this);
    ImageIcon startIcon = new ImageIcon("./out/production/srcV2/dat/script.png");
    startItem.setIcon(startIcon);

    loadItem = new JMenu("load");
    loadItem.addActionListener(this);
    ImageIcon loadIcon = new ImageIcon("./out/production/srcV2/dat/execute.png");
    loadItem.setIcon(loadIcon);

    scriptMenu.add(startItem);
    scriptMenu.add(loadItem);
    scripts = new ArrayList<JMenuItem>();
    setScriptList();

    exitItem = new JMenuItem("exit");
    ImageIcon exitIcon = new ImageIcon("./out/production/srcV2/dat/exit.png");
    exitItem.setIcon(exitIcon);
    exitItem.addActionListener(this);
    systemMenu.add(exitItem);

    menuBar.add(editMenu);
    menuBar.add(scriptMenu);
    menuBar.add(systemMenu);

    this.setJMenuBar(menuBar);

    // add panel and scrollPane in frame
    this.add(panel);
    this.add(scrollPane);

    // set look to be more beautifully
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {System.out.println(e);}

    // set canvas as visible
    this.setVisible(true);
  }

  public void setScriptList() {
    scripts.clear();
    loadItem.removeAll();
    File folder = new File("./out/production/srcV2/scripts");
    File[] files = folder.listFiles();

    if (!(files == null)) {
      for (File f : files) {
        JMenuItem item = new JMenuItem(f.getName());
        item.addActionListener(this);
        ImageIcon fileIcon = new ImageIcon("./out/production/srcV2/dat/file.png");
        item.setIcon(fileIcon);
        scripts.add(item);
        loadItem.add(item);
      }
    }
  }

  /****************************************************************************************************/
  /*                                   ActionListener implementation                                  */
  /****************************************************************************************************/
  @Override
  public void actionPerformed(ActionEvent e) {
    // if copy submenu is triggered
    if (e.getSource() == copyItem) {
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

    // if cut submenu is triggered
    if (e.getSource() == cutItem) {
      int start = caretPosition;
      int stop = secondCaretPosition;

      if (stop < start) {
        int tmp = start;
        start = stop;
        stop = tmp;
      }

      invoker.setCommand(new Cut(start, stop));
      caretPosition = caretPosition - (stop - start);
      secondCaretPosition = caretPosition;
    }

    // if paste submenu is triggered
    if (e.getSource() == pasteItem) {
      if (caretPosition != secondCaretPosition) {
        invoker.setCommand(new Replace(caretPosition, secondCaretPosition));
      } else {
        invoker.setCommand(new Paste(caretPosition));
      }
    }

    // if undo submenu is triggered
    if (e.getSource() == undoItem) {
      invoker.setCommand(new Undo());
    }

    // if redo submenu is triggered
    if (e.getSource() == redoItem) {
      invoker.setCommand(new Redo());
    }

    // if script submenu is triggered
    if (e.getSource() == startItem) {
      if (startItem.getText() == "start") {
        startItem.setText("save");
        ImageIcon saveIcon = new ImageIcon("./out/production/srcV2/dat/save.png");
        startItem.setIcon(saveIcon);
        if(scriptNaming.getText().isEmpty()) {
          informativeLabel.setText("enter script name");
        }
      } else {
        startItem.setText("start");
        ImageIcon scriptIcon = new ImageIcon("./out/production/srcV2/dat/script.png");
        startItem.setIcon(scriptIcon);
        informativeLabel.setText("");
      }
      invoker.setCommand(Script.getInstance(scriptNaming.getText()));
      //setScriptList();
    }

    // if load submenu is triggered
    if (scripts.contains(e.getSource())) {
      invoker.setCommand(new Load(SimpleBuffer.getInstance().getContent(), caretPosition,
          scripts.get(scripts.indexOf(e.getSource())).getText()));
    }

    // if first caret textefield triggered
    if (e.getSource() == caretPositionField) {
      moveCursor(caretPositionField.getText(), 1);
    }

    // if second caret textfield is triggered
    if (e.getSource() == secondCaretPositionField) {
      moveCursor(secondCaretPositionField.getText(), 2);
    }

    // if exit is trigger
    if (e.getSource() == exitItem) {
      System.exit(0);
    }

    if (e.getSource()==scriptNaming) {
      informativeLabel.setText("");
    }

    // execute set command
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
    for (int i = 0; i < s.length(); i++) {
      if (!(s.charAt(i) == '0' || s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3'
          || s.charAt(i) == '4' ||
          s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7' || s.charAt(i) == '8'
          || s.charAt(i) == '9')) {
        caretPosition = 0;
        caretPositionField.setText(caretPosition + "");
        secondCaretPosition = 0;
        secondCaretPositionField.setText(secondCaretPosition + "");
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
    // if key is a text char
    if (isTextChar(e.getKeyCode())) {
      // insert char
      invoker.setCommand(new Insert(e.getKeyChar() + "", caretPosition));
      caretPosition++;
      secondCaretPosition++;
    }
    // if key is delete key
    if (e.getKeyCode() == 8) {
      if (caretPosition > 0) {
        // delete content at caret position
        invoker.setCommand(new Delete(caretPosition));
        caretPosition--;
        secondCaretPosition--;
      }
    }
    // if key is right arrow
    if (e.getKeyCode() == 39) {
      // assert caret position is not at max position
      if (caretPosition < buffer.getContent().length()) {
        caretPosition++;
        secondCaretPosition++;
      }
    }
    // if key is left arrow
    if (e.getKeyCode() == 37) {
      // assert caret position is not at min position
      if (caretPosition > 0) {
        caretPosition--;
        secondCaretPosition--;
      }
    }

    // execute command
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
        == 44) || (code == 32) || (code == 10);
  }

  /****************************************************************************************************/
  /*                                        GUI implementation                                        */
  /****************************************************************************************************/
  @Override
  public void update() {
    // set texterea contet with buffer content
    textArea.setText(buffer.getContent());
    setScriptList();
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
