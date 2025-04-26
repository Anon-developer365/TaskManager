package org.example.taskmanager.config;



public class DatabaseConfig {
    private String url;

    public DatabaseConfig() {
        String JBDC = "org.h2.Driver";
        url = "jdbc:h2:file:database:/Taskmanager";
    }
}
