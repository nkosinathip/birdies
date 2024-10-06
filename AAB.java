import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AAB extends JPanel implements ActionListener {
    private JButton startButton;

    public AAB() {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("The Birdie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);
        
        startButton = new JButton("Press To Play");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.setPreferredSize(new Dimension(150, 40)); // Adjusted size
        startButton.setBackground(Color.CYAN); // Changed color to cyan
        startButton.setForeground(Color.BLACK); // Changed text color to black
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Adjusted border thickness
        startButton.addActionListener(this);
        add(startButton, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new BirdGame());
        frame.revalidate();
    }
}
