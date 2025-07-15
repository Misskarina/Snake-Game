import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    int tileSize = 20, gridSize = 20, length = 5;
    int[] x = new int[400], y = new int[400];
    int foodX, foodY;
    char direction = 'R';
    boolean running = true;
    Timer timer;
    Random random = new Random();

    public SnakeGame() {
        setPreferredSize(new Dimension(tileSize * gridSize, tileSize * gridSize));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        placeFood();
        timer = new Timer(80, this);
        timer.start();
    }

    void placeFood() {
        foodX = random.nextInt(gridSize) * tileSize;
        foodY = random.nextInt(gridSize) * tileSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!running) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", 100, 200);
            return;
        }

        g.setColor(Color.RED);
        g.fillOval(foodX, foodY, tileSize, tileSize);

        for (int i = 0; i < length; i++) {
            g.setColor(i == 0 ? Color.GREEN : Color.LIGHT_GRAY);
            g.fillRect(x[i], y[i], tileSize, tileSize);
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + (length - 5), 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U': y[0] -= tileSize; break;
            case 'D': y[0] += tileSize; break;
            case 'L': x[0] -= tileSize; break;
            case 'R': x[0] += tileSize; break;
        }

        // Check food collision
        if (x[0] == foodX && y[0] == foodY) {
            length++;
            placeFood();
        }

        // Check self collision
        for (int i = 1; i < length; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        // Check wall collision
        if (x[0] < 0 || y[0] < 0 || x[0] >= tileSize * gridSize || y[0] >= tileSize * gridSize) {
            running = false;
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    if (direction != 'D') direction = 'U'; break;
            case KeyEvent.VK_DOWN:  if (direction != 'U') direction = 'D'; break;
            case KeyEvent.VK_LEFT:  if (direction != 'R') direction = 'L'; break;
            case KeyEvent.VK_RIGHT: if (direction != 'L') direction = 'R'; break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
