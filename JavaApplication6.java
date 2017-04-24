/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;
import java.net.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author rosep
 */
public class JavaApplication6 {

    public static void main(String[] args) {
        ServerSocket ss=null;
        try{
            ss = new ServerSocket(5190);
            ArrayList<PrintStream> souts = new ArrayList();
            int id = 0;
            while(true){
                System.out.println("Waiting for a connection on port 5190");
                Socket client = ss.accept();
                souts.add(new PrintStream(client.getOutputStream()));
                Scanner sin = new Scanner(client.getInputStream());
                String uName = sin.nextLine();
                new ProcessClient(client,uName, souts, id++).start();
            }
        }
        catch (IOException e){
            System.out.println("Could not get the socket to work!");
            System.exit(1);
        }
    }
    


static class ProcessClient extends Thread{
    Socket client;
    String user;
    int id;
    ArrayList<PrintStream> outs;
    ProcessClient(Socket newclient, String uName, ArrayList<PrintStream> outputs, int newID){client=newclient; user = uName; outs = outputs; id = newID;}
    @Override
    public void run(){
        try{
            System.out.println(user+": Got connection on port 5190 from: "+client.getInetAddress().toString());
            Scanner sin = new Scanner(client.getInputStream());
            String line;
            while (!client.isInputShutdown()){
                line = user + ": " + sin.nextLine();
                System.out.print(line);
                for(PrintStream sout: outs){
                    sout.println(line);
                }
            }
            client.close();
        }
        catch(IOException e){}//Who cares, it was just one client!
    }

}
}