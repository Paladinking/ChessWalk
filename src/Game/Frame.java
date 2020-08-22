package Game;

import Game.assetClasses.SoundManager;

import javax.swing.*;
import java.awt.*;

class Frame extends JFrame{
    private Frame(){
        setupFrame();
    }
    private void setupFrame(){

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Board());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);


    }
    public static void main(String[] args){
        try {
            EventQueue.invokeLater(()->{
                Frame f = new Frame();

                f.setVisible(true);
            });
        } catch (Exception ignored){}
    }


}

