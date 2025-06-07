package main;

import javax.swing.*;
import javax.swing.SwingUtilities;
import model.*;

public class App{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                try {
                    LoginPage page = new LoginPage();
                    page.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
