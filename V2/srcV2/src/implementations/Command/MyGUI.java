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

public class MyGUI extends JFrame implements GUI, ActionListener, KeyListener {
  /************************************************************************************************/
  /*                                          Attributes                                          */
  /************************************************************************************************/
  // instance
  private static volatile MyGUI instance;
  // invoker
  private final Invoker invoker;
  // buffer instance
  private final Buffer buffer;
  // caret position
  private int caretPosition;
  // Second caret position
  private int secondCaretPosition;

  /*------------------------------ Swing components ------------------------------*/
  // text Area
  private JTextArea textArea;
  // script naming field
  private JTextField scriptNaming;
  // script naming label
  private JLabel namingLabel;
  // selection label
  private JLabel selection;
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
    buffer.addContent("¦", caretPosition);
    update();
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

    // instantiate scrollPane
    JScrollPane scrollPane = new JScrollPane(textArea);
    // setup dimension
    scrollPane.setPreferredSize(new Dimension(785, 510));
    // change scrollbar size
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 500));

    // create menu bar
    menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(800, 20));

    // create menus
    editMenu = new JMenu("edit");
    scriptMenu = new JMenu("script");
    systemMenu = new JMenu("system");

    // create copy item
    copyItem = new JMenuItem("copy");
    // add MyGUI as action listener
    copyItem.addActionListener(this);
    // set display icon
    ImageIcon copyIcon = new ImageIcon("./out/production/srcV2/dat/copy.png");
    copyItem.setIcon(copyIcon);
    // set shortcut => crtl + F1
    copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    // create cut item
    cutItem = new JMenuItem("cut");
    // add MyGUI as action listener
    cutItem.addActionListener(this);
    // set display icon
    ImageIcon cutIcon = new ImageIcon("./out/production/srcV2/dat/cut.png");
    cutItem.setIcon(cutIcon);
    // set shortcut => crtl + F2
    cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    // create paste item
    pasteItem = new JMenuItem("paste");
    // add MyGUI as action listener
    pasteItem.addActionListener(this);
    // set display icon
    ImageIcon pasteIcon = new ImageIcon("./out/production/srcV2/dat/paste.png");
    pasteItem.setIcon(pasteIcon);
    // set shortcut => crtl + F3
    pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    // create undo item
    undoItem = new JMenuItem("undo");
    // add MyGUI as action listener
    undoItem.addActionListener(this);
    // set display icon
    ImageIcon undoIcon = new ImageIcon("./out/production/srcV2/dat/undo.png");
    undoItem.setIcon(undoIcon);
    // set shortcut => crtl + F3
    undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    // create redo item
    redoItem = new JMenuItem("redo");
    // add MyGUI as action listener
    redoItem.addActionListener(this);
    // set display icon
    ImageIcon redoIcon = new ImageIcon("./out/production/srcV2/dat/redo.png");
    redoItem.setIcon(redoIcon);
    // set shortcut => crtl + F3
    redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    // add items in edit menu
    editMenu.add(copyItem);
    editMenu.add(cutItem);
    editMenu.add(pasteItem);
    editMenu.addSeparator();
    editMenu.add(undoItem);
    editMenu.add(redoItem);

    // create start script item
    startItem = new JMenuItem("start");
    // add MyGUI as action listener
    startItem.addActionListener(this);
    // set display icon
    ImageIcon startIcon = new ImageIcon("./out/production/srcV2/dat/script.png");
    startItem.setIcon(startIcon);

    // create load script menu
    loadItem = new JMenu("load");
    // add MyGUI as action listener
    loadItem.addActionListener(this);
    // set display icon
    ImageIcon loadIcon = new ImageIcon("./out/production/srcV2/dat/execute.png");
    loadItem.setIcon(loadIcon);

    // add items in script menu
    scriptMenu.add(startItem);
    scriptMenu.add(loadItem);

    // load script list
    scripts = new ArrayList<JMenuItem>();
    // update load script submenus
    setScriptList();

    // create exit item
    exitItem = new JMenuItem("exit");
    // set display icon
    ImageIcon exitIcon = new ImageIcon("./out/production/srcV2/dat/exit.png");
    exitItem.setIcon(exitIcon);
    // add MyGUI as action listener
    exitItem.addActionListener(this);

    // add item in system menu
    systemMenu.add(exitItem);

    // add menus in menu bar
    menuBar.add(editMenu);
    menuBar.add(scriptMenu);
    menuBar.add(systemMenu);

    // add menu bar in JFrame
    this.setJMenuBar(menuBar);

    // create selection label
    JLabel select = new JLabel("selection : ");
    // center label
    select.setHorizontalAlignment(SwingConstants.CENTER);

    // create selection content label
    selection = new JLabel("");

    // create script namin label
    namingLabel = new JLabel("Script name : ");
    // set label invisible
    namingLabel.setVisible(false);

    // create script naming text field
    scriptNaming = new JTextField();
    // add MyGUI as action listener
    scriptNaming.addActionListener(this);
    // set invisible
    scriptNaming.setVisible(false);

    // setup bottom panel
    JPanel backPanel = new JPanel(new GridLayout(1, 2));
    backPanel.setPreferredSize(new Dimension(800, 20));

    // add element in layout
    backPanel.add(select);
    backPanel.add(selection);
    backPanel.add(namingLabel);
    backPanel.add(scriptNaming);

    // main frame panel
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(backPanel, BorderLayout.SOUTH);

    // add main frame panel in frame
    this.add(panel);

    // set look to be more beautifully
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // set canvas as visible
    this.setVisible(true);
  }

  /**
   * Set load item submenu from
   */
  public void setScriptList() {
    // clear script list
    scripts.clear();
    // remove all current submenus
    loadItem.removeAll();
    // script folder path
    File folder = new File("./out/production/srcV2/scripts");
    // parse folder content to list
    File[] files = folder.listFiles();

    // if folder is not empty
    if (!(files == null)) {
      // run for each files
      for (File f : files) {
        // create new item
        JMenuItem item = new JMenuItem(f.getName());
        // add MyGUI as action listener
        item.addActionListener(this);
        // set icon
        ImageIcon fileIcon = new ImageIcon("./out/production/srcV2/dat/file.png");
        item.setIcon(fileIcon);
        // add item to script list
        scripts.add(item);
        // add item to load submenu
        loadItem.add(item);
      }
    }
  }

  /************************************************************************************************/
  /*                                 ActionListener implementation                                */
  /************************************************************************************************/
  @Override
  public void actionPerformed(ActionEvent e) {
    // erase carret display
    buffer.deleteContent(caretPosition, caretPosition);

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

      // set and execute set command
      invoker.setCommand(new Copy(start + 1, stop));
      invoker.executeCommand();
      // add carret display
      buffer.addContent("¦", caretPosition);
      // reset second carret position
      secondCaretPosition = caretPosition;
      // update selection label content
      selection.setText(buffer.getContent().substring(secondCaretPosition, caretPosition));
      // update GUI
      update();
    }

    // if cut submenu is triggered
    if (e.getSource() == cutItem) {
      int start = caretPosition;
      int stop = secondCaretPosition;
      // assert start < stop
      if (stop < start) {
        int tmp = start;
        start = stop;
        stop = tmp;
      }

      // set and execute command
      invoker.setCommand(new Cut(start, stop));
      invoker.executeCommand();
      // set carrets positions
      caretPosition = caretPosition - (stop - start);
      secondCaretPosition = caretPosition;
      // add carret display
      buffer.addContent("¦", caretPosition);
      // set selection label content
      selection.setText(buffer.getContent().substring(secondCaretPosition, caretPosition));
      // update GUI
      update();
    }

    // if paste submenu is triggered
    if (e.getSource() == pasteItem) {
      // if there is a selection => replace command
      if (caretPosition != secondCaretPosition) {
        invoker.setCommand(new Replace(caretPosition, secondCaretPosition));
      } else { // no selection => paste command
        invoker.setCommand(new Paste(caretPosition));
        // set carets positions
        caretPosition += SimpleClipboard.getInstance().getContent().length();
        secondCaretPosition = caretPosition;
      }

      // execute set command
      invoker.executeCommand();
      // add caret display
      buffer.addContent("¦", caretPosition);
      // update GUI display
      update();
    }

    // if undo submenu is triggered
    if (e.getSource() == undoItem) {
      // set and execute command
      invoker.setCommand(new Undo());
      invoker.executeCommand();

      // set carets positions in case of out of bounds
      if (caretPosition > buffer.getContent().length()) {
        caretPosition = buffer.getContent().length();
        secondCaretPosition = caretPosition;
      }

      // add caret display
      buffer.addContent("¦", caretPosition);
      // update GUI display
      update();
    }

    // if redo submenu is triggered
    if (e.getSource() == redoItem) {
      // set and execute command
      invoker.setCommand(new Redo());
      invoker.executeCommand();

      // add caret display
      buffer.addContent("¦", caretPosition);
      // update GUI display
      update();
    }

    // if script submenu is triggered
    if (e.getSource() == startItem) {
      // if start menu is waiting for action
      if (startItem.getText().equals("start")) {
        // set script naming elements visible
        scriptNaming.setVisible(true);
        namingLabel.setVisible(true);
        // change script submenu item to save item and change icon
        startItem.setText("save");
        ImageIcon saveIcon = new ImageIcon("./out/production/srcV2/dat/save.png");
        startItem.setIcon(saveIcon);
      } else { // if script menu is in save mode
        // change text and icon
        startItem.setText("start");
        ImageIcon scriptIcon = new ImageIcon("./out/production/srcV2/dat/script.png");
        startItem.setIcon(scriptIcon);
        // make script naming elements invisible
        scriptNaming.setVisible(false);
        namingLabel.setVisible(false);
      }

      // set and execute command with Script singleton instance
      invoker.setCommand(Script.getInstance(scriptNaming.getText()));
      invoker.executeCommand();

      // set carets
      caretPosition = buffer.getContent().length();
      secondCaretPosition = caretPosition;

      // add caret display
      buffer.addContent("¦", caretPosition);
      // update GUI display
      update();
    }

    // if load submenu is triggered
    if (scripts.contains(e.getSource())) { // if source is from scripts item list
      // set and execute command
      invoker.setCommand(new Load(SimpleBuffer.getInstance().getContent(), caretPosition,
          scripts.get(scripts.indexOf(e.getSource())).getText()));
      invoker.executeCommand();

      // add caret display
      buffer.addContent("¦", caretPosition);
      // update GUI display
      update();
    }

    // if exit is trigger
    if (e.getSource() == exitItem) {
      // quit item
      System.exit(0);
    }

    // if script naming field is trigger
    if (e.getSource() == scriptNaming) {
      // set naming element invisible
      scriptNaming.setVisible(false);
      namingLabel.setVisible(false);

      // update GUI display
      update();
    }
  }

  /************************************************************************************************/
  /*                                  KeyListener implementation                                  */
  /************************************************************************************************/
  @Override
  public void keyPressed(KeyEvent e) {
    // delete caret display
    buffer.deleteContent(caretPosition, caretPosition);

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
        if (secondCaretPosition == caretPosition) {
          secondCaretPosition++;
        }
        caretPosition++;
      }
      selection.setText(buffer.getContent().substring(secondCaretPosition, caretPosition));
    }
    // if key is left arrow
    if (e.getKeyCode() == 37) {
      // assert caret position is not at min position
      if (caretPosition > 0) {
        if (caretPosition == secondCaretPosition) {
          secondCaretPosition--;
        }
        caretPosition--;
      }
      selection.setText(buffer.getContent().substring(secondCaretPosition, caretPosition));
    }
    // if key is up arrow
    if (e.getKeyCode() == 38) {
      // assert second caret < caret
      if (secondCaretPosition < caretPosition) {
        secondCaretPosition++;
      }
      selection.setText(buffer.getContent().substring(secondCaretPosition, caretPosition));
    }
    // if key is down null
    if (e.getKeyCode() == 40) {
      // assert carret is not at position 0
      if (secondCaretPosition > 0) {
        secondCaretPosition--;
      }
      selection.setText(buffer.getContent().substring(secondCaretPosition, caretPosition));
    }

    // execute command
    invoker.executeCommand();
    // insert caret display
    buffer.addContent("¦", caretPosition);
    // update GUI display
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
   * @return true for [a-z] [0-9] & é " ' ( - è _ ç à ) = + ° $ £ * µ % ! § : / ; . , ? ~#{[|`\^@]}
   * false otherwise
   */
  private boolean isTextChar(int code) {
    return (65 <= code && 95 >= code) || (48 <= code && 57 >= code) || (code == 522) || (code == 61)
        || (code == 515) ||
        (code == 151) || (code == 0) || (code == 517) || (code == 513) || (code == 59) || (code
        == 44) || (code == 32) || (code == 10);
  }

  /************************************************************************************************/
  /*                                      GUI implementation                                      */
  /************************************************************************************************/
  @Override
  public void update() {
    // set texterea contet with buffer content
    textArea.setText(buffer.getContent());

    // update script list
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
  }
}
