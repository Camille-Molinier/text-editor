package implementations;

import Interfaces.Buffer;
import Interfaces.GUI;
import Interfaces.Invoker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyGUI extends JFrame implements GUI, ActionListener, KeyListener{
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // instance
    private static volatile MyGUI instance;
    // text Area
    private JTextArea textArea;
    // carret position feild
    private JTextField caretPositionField;
    // copy button
    private JButton copyButton;
    // cut button
    private JButton cutButton;
    // replace button
    private JButton pasteButton;
    // invoker
    private Invoker invoker;
    // buffer instance
    private Buffer buffer;
    // informative label
    private JLabel infLabel;

    // caret position label
    private JLabel caretPosLabel;
    private  int caretPosition;

    // 2nd caret position label
    private JLabel secondCaretPosLabel;

    // 2nd carret position feild
    private JTextField secondCaretPositionField;


    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    private MyGUI(){
        buffer = SimpleBuffer.getInstance();
        invoker = new DummyInvoker();
        caretPosition = 0;
        setupCanvas();
    }

    /**
     * Return GUI singleton instance
     * @return
     */
    public static MyGUI getInstance(){
        if (instance==null){
            instance = new MyGUI();
        }
        return instance;
    }

    /**
     * Setup canvas composition and style using Swing
     */
    private void setupCanvas(){
        // set JFrame to stop program on tab close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set canvas size
        this.setSize(600, 600);
        // set canvas title
        this.setTitle("Text editor");
        // change border color

        // disable window resizing
        this.setResizable(false);
        // change content pane color
        this.getContentPane().setBackground(new Color(34,31,31));
        // set layout to row layout
        this.setLayout(new FlowLayout());
        // give focus to JFrame
        this.setFocusable(true);
        // get key focus to JFrame
        this.setFocusTraversalKeysEnabled(false);
        // add key listener
        this.addKeyListener(this);

        // create informative label
        infLabel = new JLabel("label");
        infLabel.setForeground(new Color(150,150,150));

        // create caret position label
        caretPosLabel = new JLabel("Caret position");
        caretPosLabel.setForeground(new Color(150,150,150));

        // create caret position label
        secondCaretPosLabel = new JLabel("Caret position");
        secondCaretPosLabel.setForeground(new Color(150,150,150));

        // create copy button
        copyButton = new JButton("Copy");
        // add JFrame listener in button
        copyButton.addActionListener(this);
        // add button in JFrame
        //this.add(copyButton);

        // create copy button
        cutButton = new JButton("Cut");
        // add JFrame listener in button
        cutButton.addActionListener(this);
        // add button in JFrame
        //this.add(cutButton);

        // create copy button
        pasteButton = new JButton("Paste");
        // add JFrame listener in button
        pasteButton.addActionListener(this);
        // add button in JFrame
        //this.add(pasteButton);

        // configure text field for caret positioning
        caretPositionField = new JTextField();
        // set textfield size
        caretPositionField.setPreferredSize(new Dimension(100, 25));
        // add JFrame listener in textfield
        caretPositionField.addActionListener(this);
        // add textfield in JFrame
        //this.add(jTextField);

        // configure second text field for caret positioning
        secondCaretPositionField = new JTextField();
        // set textfield size
        secondCaretPositionField.setPreferredSize(new Dimension(100, 25));
        // add JFrame listener in textfield
        secondCaretPositionField.addActionListener(this);

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
        textArea.setBackground(new Color(39,39,39));
        // font color
        textArea.setForeground(Color.WHITE);
        // margins
        textArea.setMargin(new Insets(10, 10, 10 ,10));
        // add JFrame listener in textarea
        textArea.addKeyListener(this);

        // instantiate scrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        // setup dimension
        scrollPane.setPreferredSize(new Dimension(585, 525));
        // change background color
        scrollPane.getVerticalScrollBar().setBackground(new Color(44, 44, 44));
        // change scrollbar size
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 500));
        // change border color
        scrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.darkGray));
        // insert scroll pane in JFrame
        //this.add(scrollPane);

        // insert components in layout
        this.add(caretPosLabel);
        this.add(caretPositionField);
        this.add(secondCaretPosLabel);
        this.add(secondCaretPositionField);
        this.add(infLabel);
        this.add(copyButton);
        this.add(cutButton);
        this.add(pasteButton);
        this.add(scrollPane);

        // set canvas as visible
        this.setVisible(true);
    }

    /****************************************************************************************************/
    /*                                   ActionListener implementation                                  */
    /****************************************************************************************************/
    @Override
    public void actionPerformed(ActionEvent e) {
        // if button is triggered
        if(e.getSource() == copyButton){
            int start = caretPosition;
            int stop = parseString2Int(secondCaretPositionField.getText());
            invoker.setCommand(new Copy(start, stop));
        }
        // if textefield triggered
        if(e.getSource()== caretPositionField){
            moveCursor(caretPositionField.getText());
        }
        invoker.executeCommand();
        update();
    }

    void moveCursor(String s){
        int content = parseString2Int(s);
        // make sure position is not out of range
        if (content <= buffer.getContent().length()) {
            caretPosition = content;
        } else { // Otherwise => set caret position at content end
            caretPosition = buffer.getContent().length();
        }
        // update textField display
        caretPositionField.setText(caretPosition + "");
    }

    int parseString2Int(String s) {
        if(s.isEmpty()){
            return 0;
        }
        boolean error = false;
        for (int i=0; i<s.length(); i++) {
            if (!(s.charAt(i) == '0' || s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3' || s.charAt(i) == '4' ||
                    s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7' || s.charAt(i) == '8' || s.charAt(i) == '9')) {
                caretPosition = 0;
                caretPositionField.setText(caretPosition+"");
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
        //System.out.println(e.getKeyCode());
        if(isTextChar(e.getKeyCode())){
            invoker.setCommand(new Insert(e.getKeyChar()+"", caretPosition));
            caretPosition ++;
        } else if (e.getKeyCode() == 8) {
            if(caretPosition>0) {
                invoker.setCommand(new Delete(caretPosition));
                caretPosition--;
            }
        }
        invoker.executeCommand();
        update();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    /**
     * Indicate if a code is a texte character
     * @param code : char code
     * @return true for [a-z] [0-9] & é " ' ( - è _ ç à ) = + ° $ £ * µ % ! § : / ; . , ?
     *         false otherwise
     */
    private boolean isTextChar(int code) {
        return (65<=code && 95>=code) || (48<=code && 57>=code) || (code==522) || (code==61) || (code==515) ||
                (code==151) || (code==0) || (code==517) || (code==513) || (code==59) || (code==44) || (code==32);
    }

    /****************************************************************************************************/
    /*                                        GUI implementation                                        */
    /****************************************************************************************************/
    @Override
    public void update() {
        textArea.setText(buffer.getContent());
        caretPositionField.setText(caretPosition+"");
    }
}
