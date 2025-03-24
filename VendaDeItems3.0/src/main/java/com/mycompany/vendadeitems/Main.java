
    package com.mycompany.vendadeitems;

public class Main {

    public static void main(String[] args) {
        // Inicia a aplicação abrindo a tela de login
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Teladelogin().setVisible(true);
            }
        });
    }
}
