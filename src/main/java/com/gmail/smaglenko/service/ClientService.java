package com.gmail.smaglenko.service;

public interface ClientService {
    void clientConnection(String address, int port);

    void reading(String line);

    void closeConnection();
}
