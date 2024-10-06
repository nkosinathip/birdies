import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class BirdGame extends JPanel implements ActionListener, KeyListener {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 640;

    // Game state
    private Bird character;
    private int horizontalSpeed = -4; // Speed of pipe movement
    private int verticalSpeed = 0; // Speed of bird's vertical movement
    private int gravity = 1;

    private ArrayList<Pipe> pipeList;

    private Timer mainGameLoop;
    private Timer pipeTimer;
    private boolean isGameOver = false;
    private double playerScore = 0;

    // Images
    private Image backgroundImage;
    private Image birdImage;
    private Image upperPipeImage;
    private Image lowerPipeImage;

    // Bird properties
    private int birdX = FRAME_WIDTH / 8;
    private int birdY = FRAME_HEIGHT / 2;
    private int birdWidth = 64;
    private int birdHeight = 54;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Pipe properties
    private int pipeX = FRAME_WIDTH;
    private int pipeY = 0;
    private int pipeWidth = 64;
    private int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean hasPassed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    BirdGame() {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Initialize bird
        character = new Bird(birdImage);
        pipeList = new ArrayList<>();

        // Pipe placement timer
        pipeTimer = new Timer(2000, e -> createPipes());
        pipeTimer.start();

        // Main game loop timer
        mainGameLoop = new Timer(1000 / 60, this);
        mainGameLoop.start();
    }

    private int generateRandomY() {
        return (int) (pipeY - pipeHeight / 3 - Math.random() * (pipeHeight / 2));
    }

    private void createPipes() {
        int randomPipeY = generateRandomY();
        int gap = FRAME_HEIGHT / 3;

        Pipe upperPipe = new Pipe(upperPipeImage);
        Pipe lowerPipe = new Pipe(lowerPipeImage);

        upperPipe.y = randomPipeY;
        lowerPipe.y = upperPipe.y + pipeHeight + gap;

        pipeList.add(upperPipe);
        pipeList.add(lowerPipe);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        // Draw background
        g.drawImage(backgroundImage, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);

        // Draw bird
        g.drawImage(birdImage, character.x, character.y, character.width, character.height, null);

        // Draw pipes
        for (Pipe pipe : pipeList) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Draw score
        g.setColor(Color.white);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        if (isGameOver) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new LoseScreen((int) (playerScore)));
            frame.revalidate();
        } else {
            g.drawString(String.valueOf((int) playerScore), 10, 35);
        }
    }

    private void updatePosition() {
        // Update bird's position
        verticalSpeed += gravity;
        character.y += verticalSpeed;
        character.y = Math.max(character.y, 0); // Prevent bird from flying off the top
        character.y = Math.min(character.y, 552); // Prevent bird from flying off the bottom
        
        // Update pipe positions
        for (Pipe pipe : pipeList) {
            pipe.x += horizontalSpeed;

            if (!pipe.hasPassed && character.x > pipe.x + pipe.width) {
                playerScore += 0.5; // Increment score for passing pipes
                pipe.hasPassed = true;
            }

            if (detectCollision(character, pipe)) {
                isGameOver = true;
            }
        }

        // Check if bird hits the ground
        if (character.y > 551) {
            isGameOver = true;
        }
    }

    private boolean detectCollision(Bird bird, Pipe pipe) {
        return bird.x < pipe.x + pipe.width && bird.x + bird.width > pipe.x &&
                bird.y < pipe.y + pipe.height && bird.y + bird.height > pipe.y;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        updatePosition();
        repaint();
        if (isGameOver) {
            pipeTimer.stop();
            mainGameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            verticalSpeed = -15; // Jump effect
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
