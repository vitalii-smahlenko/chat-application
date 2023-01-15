package com.gmail.smaglenko.service.impl;

import com.gmail.smaglenko.model.User;
import com.gmail.smaglenko.service.ClientService;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientServiceImp implements ClientService {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream out;
    private User user;

    public ClientServiceImp(String address, int port) {
        clientConnection(address, port);
        try {
            user = new User();
            System.out.format("Helli, enter user name.%n");
            changeUseName(user, input.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String line = "";
        reading(line);
        closeConnection();
    }

    public static void main(String[] args) {
        ClientServiceImp clientServiceImp = new ClientServiceImp("127.0.0.1", 5000);
    }

    @Override
    public void clientConnection(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.format("Connected address: %s, port: %s.%n", address, port);
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("Can't connect to address: " + address + " , port: "
                    + port + ".", e);
        }
    }

    @Override
    public void reading(String line) {
        while (!line.equals("Bye")) {
            try {
                line = input.readLine();
                if (line.equals("Change user name")) {
                    System.out.format("Enter user name.%n");
                    changeUseName(user, input.readLine());
                    continue;
                }
                out.writeUTF(user.getName() + ": " + line);
            } catch (Exception e) {
                throw new RuntimeException("Can't read line:" + line, e);
            }
        }
    }

    @Override
    public void closeConnection() {
        try {
            input.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException("Can't close connection", e);
        }
    }

    private void changeUseName(User user, String name) {
        user.setName(name);
        System.out.format("Current user name %s.%n", user.getName());
    }
}
