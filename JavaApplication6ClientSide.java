/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6clientside;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
/**
 *
 * @author rosep
 */
public class JavaApplication6ClientSide {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            String ip = JOptionPane.showInputDialog("Which server do you want to connect to?");
            Socket s = new Socket(ip, 5190);
            Scanner sin = new Scanner(s.getInputStream());
            PrintStream sout = new PrintStream(s.getOutputStream());
            JFrame jf = new JFrame("");
            jf.setVisible(true);
            jf.setSize(700,400);
            jf.setResizable(true);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel pane = new JPanel();
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
            JTextField inField = new JTextField();
            inField.setEditable(true);
            inField.setSize(350, 200);
            pane.add(inField);
            JTextArea outField = new JTextArea("INPUT USERNAME FIRST\n", 5,20);
            JScrollPane scroll = new JScrollPane (outField);
            
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            outField.setEditable(false);
            //outField.setBackground(Color.green);
            //outField.setSize(350, 200);
            pane.add(outField);
            JButton send = new JButton("SEND");
            send.addActionListener(new ButtonPress(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    String text = inField.getText();
                    sout.println(text);
                    inField.setText("");
                }
            });
            pane.add(send);
            jf.add(pane);
            new output(sin, outField).start();
        } catch (IOException e) {
            System.out.println("ERRRROROROROROR");
            System.exit(1);
        }
    }
    private static class output extends Thread{
        Scanner sin = null;
        JTextArea text = null;
        output(Scanner newIn, JTextArea tb){sin=newIn; text=tb;}
        @Override
        public void run(){
            String tt="\r\n";
            while(true){
                if(sin.hasNextLine()){
                    tt =sin.nextLine()+"\r\n";
                    //System.out.println(tt);
                    text.append(tt);
                }
            }
        }
    
    }
    private static class ButtonPress implements ActionListener {

        public ButtonPress() {}
        @Override
        public void actionPerformed(ActionEvent ae){}
    }
    
}