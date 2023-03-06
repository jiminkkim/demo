package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;
import com.example.demo.SocketThreadServer;

@SpringBootApplication
public class DemoApplication {

    private static final Logger logger = Logger.getLogger(DemoApplication.class);
    private static final int PORT_NUMBER = 4432;
    public static void main(String[] args) throws IOException {
        logger.info(":::                                                :::");
        logger.info(":::       Socket Application  Process Start        :::");
        logger.info(":::                                                :::");

        try {
            ServerSocket server = new ServerSocket(PORT_NUMBER);
            while (true) {
                Socket connection = server.accept();
                Thread task = new SocketThreadServer(connection);
                task.start();
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
