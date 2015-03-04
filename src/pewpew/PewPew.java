package pewpew;

import java.io.IOException;
import javax.swing.JFrame;

public class PewPew {

    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame("Pew pew!");
        window.setContentPane(new Game());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}