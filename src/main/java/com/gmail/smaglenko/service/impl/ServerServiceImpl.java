package com.gmail.smaglenko.service.impl;

import com.gmail.smaglenko.service.ServerService;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerServiceImpl implements ServerService {
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;

    public ServerServiceImpl(int port) {
        startServer(port);
        String line = "";
        readMessage(line);
        closeConnection();
    }

    public static void main(String[] args) {
        ServerServiceImpl serverServiceImpl = new ServerServiceImpl(5000);
    }

    @Override
    public void startServer(int port) {
        try {
            server = new ServerSocket(port);
            System.out.format("Server started port: %s.%n", port);
            System.out.format("Waiting for a client ...%n");
            socket = server.accept();
            System.out.format("Client accepted%n");
            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException("Can't start server.", e);
        }
    }

    @Override
    public void readMessage(String line) {
        while (!line.contains("Bye")) {
            try {
                line = in.readUTF();
                System.out.format("%s%n", line);
            } catch (Exception e) {
                throw new RuntimeException("Can't read message.", e);
            }
        }
    }

    @Override
    public void closeConnection() {
        try {
            System.out.format("Closing connection...%n");
            socket.close();
            in.close();
            System.out.format("Connection closed.%n");
        } catch (Exception e) {
            throw new RuntimeException("Can't close connection.", e);
        }
    }
}
