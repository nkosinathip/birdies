import javax.swing.*;

public class App {
    public static void main(String[] args) {
        final int WIDTH = 400;
        final int HEIGHT = 700;

        JFrame frame = new JFrame("The Birdie");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AAB menuPanel = new AAB();
        frame.setContentPane(menuPanel);
        frame.setVisible(true);
    }
}
