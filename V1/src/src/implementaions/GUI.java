package implementaions;

import Interfaces.Command;
import Interfaces.Invoker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BorderFactory.createEmptyBorder;

public class GUI extends JFrame implements Invoker, ActionListener {

    // Current command
    private Command command;

    // Button
    JButton button;

    public GUI(){
        setupCanvas();
    }

    /**
     * Setup canvas composition and style using Swing
     */
    private void setupCanvas(){
        // Set JFrame to stop program on tab close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set canvas size
        this.setSize(600, 600);
        // Set canvas title
        this.setTitle("Text editor");
        this.getContentPane().setBackground(new Color(34,31,31));
        // Set layout to row layout
        this.setLayout(new FlowLayout());

        // Add Button
        button = new JButton("Groink");
        button.addActionListener(this);
        this.add(button);

        // Instantiate text area
        JTextArea textArea = new JTextArea();
        textArea.setAutoscrolls(false);  // Cancel autoscroll
        textArea.setLineWrap(true);  // Auto newline
        textArea.setBackground(new Color(39,39,39)); // Background color
        textArea.setForeground(Color.WHITE); // Font color
        textArea.setMargin(new Insets(10, 10, 10 ,10)); // Margins


        // Instantiate scrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(585, 500)); // Dimension
        scrollPane.getVerticalScrollBar().setBackground(new Color(44, 44, 44)); // Background color
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 500)); // Size

        // Insert scroll pane in JFrame
        this.add(scrollPane);

        // Set canvas as visible
        this.setVisible(true);
    }

    @Override
    public void setCommand(Command c) {

    }

    @Override
    public void executeCommand() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            System.out.println("Groink");
        }
    }
}
