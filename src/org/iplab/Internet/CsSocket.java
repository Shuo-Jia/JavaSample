package org.iplab.Internet;

import com.sun.corba.se.spi.activation.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by js982 on 2017/8/29.
 */
public class CsSocket {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = null;
                frame = new InterruptibleSocketFrame();
                frame.setTitle("InterruptibleSocketApp");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class InterruptibleSocketFrame extends JFrame{
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 60;

    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JButton serverstartButton;
    private JTextArea message;
    private ServerApp server;
    private Thread connectThread;

    public InterruptibleSocketFrame(){
        JPanel northPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        message = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(message));

        serverstartButton = new JButton("serverstart");
        interruptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking");
        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);
        northPanel.add(serverstartButton);
        pack();

        serverstartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverstartButton.setEnabled(false);
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
                cancelButton.setEnabled(true);

                server = new ServerApp();
                new Thread(server).start();
            }
        });

        interruptibleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interruptibleButton.setEnabled(false);
                blockingButton.setEnabled(false);
                cancelButton.setEnabled(true);
                connectThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            connectInterruptiobly();
                        }
                        catch (IOException e){
                            message.append("\nInterruptibleSocketApp.connectTnterruptibly:" + e);
                        }
                    }
                });
                connectThread.start();
            }
        });

        blockingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interruptibleButton.setEnabled(false);
                blockingButton.setEnabled(false);
                cancelButton.setEnabled(true);
                connectThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            connetBlocking();
                        }
                        catch(IOException e){
                            message.append("\ninterruptibleSocketApp.connectBlocking:" + e);
                        }
                    }
                });
                connectThread.start();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectThread.interrupt();
                cancelButton.setEnabled(false);
            }
        });
    }

    public void connectInterruptiobly() throws IOException{
        message.append("Interruptible:\n");
        try(SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8189))){
            in = new Scanner(channel);
            while(!Thread.currentThread().isInterrupted()){
                message.append("InterReading\n");
                if(in.hasNextLine()){
                    String line = in.nextLine();
                    message.append(line);
                    message.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    message.append("Channel closed");
                    interruptibleButton.setEnabled(true);
                    blockingButton.setEnabled(true);
                }
            });
        }
    }

    public void connetBlocking() throws IOException{
        message.append("Blocking:\n");
        try(Socket sock = new Socket("localhost", 8189)){
            in = new Scanner(sock.getInputStream());
            while(!Thread.currentThread().isInterrupted()){
                message.append("BlockReading:\n");
                if(in.hasNextLine()){
                    String line = in.nextLine();
                    message.append(line);
                    message.append("\n");
                }
            }
        }
        finally {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    message.append("Socket closed");
                    interruptibleButton.setEnabled(true);
                    blockingButton.setEnabled(true);
                }
            });
        }
    }
    class ServerApp implements Runnable{

        @Override
        public void run() {
            try{
                ServerSocket s = new ServerSocket(8189);
                while(true){
                    Socket incoming = s.accept();
                    Runnable r = new ServerHandler(incoming);
                    Thread t = new Thread(r);
                    t.start();
                }
            } catch (IOException e) {
                message.append("\nServer.run" + e);
            }
        }
    }

    class ServerHandler implements Runnable{
        private Socket incoming;
        private int counter;

        public ServerHandler(Socket i){
            incoming = i;
        }

        @Override
        public void run() {
            try{
                try{
                    OutputStream outStream = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(outStream, true /*autoFlush*/);
                    while(counter < 100){
                        Thread.sleep(100);
                        counter++;
                        if(counter <= 20)
                            out.println(counter);
                        Thread.sleep(1000);
                    }
                }
                finally {
                    incoming.close();
                    message.append("Closeing server\n");
                }
            }
            catch (Exception e){
                message.append("\nServerHandler.run:" + e);
            }
        }
    }
}