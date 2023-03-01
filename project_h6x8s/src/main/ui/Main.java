package ui;

import java.io.IOException;

// represent the beginning of Books Management System Application.
// Creating an instance of the BookManagementApp class.
public class Main {
    public static void main(String[] args) {
        try {
            new BookManagementApp();
        } catch (IOException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
