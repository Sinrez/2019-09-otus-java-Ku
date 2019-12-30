package ru.otus.homework;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(
                "8080",
                "127.0.0.1",
                Main.class.getClassLoader().getResource("realm.properties").getFile(),
                Main.class.getClassLoader().getResource("templates").getFile()
        );
        server.start();
    }
}
