package com.gmail.smaglenko.service;

public interface ServerService {
    void startServer(int port);

    void readMessage(String line);

    void closeConnection();
}
