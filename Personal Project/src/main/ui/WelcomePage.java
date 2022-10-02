package ui;

import javax.swing.*;
import java.awt.*;
import static ui.ScrapbookApp.*;

// The first page of the application that displays on startup
public class WelcomePage {

    private JFrame frame;
    private JLayeredPane pane;

    // REQUIRES: frame is not null
    // MODIFIES: this
    // EFFECTS: Creates a welcome page and displays it
    public WelcomePage(JFrame frame) {
        this.frame = frame;
        setUpPane();
        frame.setContentPane(pane);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the components of welcome page
    private void setUpPane() {
        pane = new JLayeredPane();
        pane.setBounds(0, 0, WIDTH, HEIGHT);

        setUpButtons();
        setUpText();
        setUpBg();
    }

    // REQUIRES: "./data/WelcomeImage.jpg" exists
    // MODIFIES: this, ScrapbookApp
    // EFFECTS: Loads and adds a background to pane
    private void setUpBg() {
        // Background Image
        bg = new JLabel(new ImageIcon("./data/WelcomeImage.jpg"), JLabel.CENTER);
        bg.setOpaque(true);
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        pane.add(bg);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the text component(s) to pane
    private void setUpText() {
        JLabel welcomeWelcome = new JLabel("Welcome to the Journeyman's Scrapbook!", JLabel.CENTER);
        welcomeWelcome.setFont(new Font(welcomeWelcome.getFont().getFontName(), Font.PLAIN, 50));
        welcomeWelcome.setOpaque(false);
        welcomeWelcome.setBounds(0, HEIGHT / 4, WIDTH, HEIGHT / 4);
        pane.add(welcomeWelcome);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds the button component(s) to pane
    private void setUpButtons() {
        JButton enterButton = new JButton("Load Your Memories");
        enterButton.setFont(new Font(enterButton.getFont().getFontName(), Font.PLAIN, 30));
        enterButton.setOpaque(true);
        enterButton.setBounds(WIDTH / 2 - WIDTH / 6, HEIGHT / 2, WIDTH / 3, HEIGHT / 6);
        enterButton.addActionListener(e -> new MemoriesPage(frame, loadMemories(), 0));

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font(quitButton.getFont().getFontName(), Font.PLAIN, 30));
        quitButton.setOpaque(true);
        quitButton.setBounds(WIDTH / 2 - WIDTH / 12, HEIGHT / 2 + HEIGHT / 5, WIDTH / 6, HEIGHT / 6);
        quitButton.addActionListener(e -> quit());

        pane.add(enterButton);
        pane.add(quitButton);
    }
}
