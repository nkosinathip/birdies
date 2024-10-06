import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoseScreen extends JPanel implements ActionListener {
    private JButton playButton;

    public LoseScreen(int score) {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel;
        if (score >= 10 && score < 20) {
            titleLabel = new JLabel("Game Score: " + score + " Bronze reward", SwingConstants.CENTER);
        } else if (score >= 20 && score < 30) {
            titleLabel = new JLabel("Game Score: " + score + " Silver reward", SwingConstants.CENTER);
        } else if (score >= 30 && score < 40) {
            titleLabel = new JLabel("Game Score: " + score + " Gold reward", SwingConstants.CENTER);
        } else {
            titleLabel = new JLabel("Game Score: " + score + " Platinum reward", SwingConstants.CENTER);
        }
        
        titleLabel.setFont(new Font("Courier", Font.BOLD, 18));
        add(titleLabel, BorderLayout.CENTER); // Change location

        // Play button
        playButton = new JButton("Replay");
        playButton.setFont(new Font("Courier", Font.PLAIN, 18)); // Change font size here
        playButton.addActionListener(this);
        add(playButton, BorderLayout.SOUTH); // Change location
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // When the Play button is pressed, switch to the FlappyBird game
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new BirdGame()); // Change to BirdGame if that is your main game class
        frame.revalidate(); // Refresh the frame to show the new content
    }
}
