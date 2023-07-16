package ui;

import model.EventLog;
import model.Memories;
import persistence.Loader;
import persistence.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Scrapbook application
public class ScrapbookApp {

    protected static final int WIDTH = 1280;                                    // WIDTH >= 0
    protected static final int HEIGHT = 720;                                    // HEIGHT >= 0
    protected static int smallButtonWidth = WIDTH / 15;                         // smallButtonWidth >= 0
    protected static int smallButtonHeight = HEIGHT / 15;                       // smallButtonHeight >= 0
    protected static final int horzBorder = WIDTH / 25;                         // horzBorder >= 0
    protected static final int vertBorder = HEIGHT / 25;                        // vertBorder >= 0
    private static final String SAVE_LOCATION = "Personal Project/data/SavedMemories.json";
    private JFrame frame;
    protected static JLabel bg;

    // EFFECTS: runs the application
    public ScrapbookApp() {
        runScrapbook();
    }

    // MODIFIES: this
    // EFFECTS: creates the application window and starts the application at the welcome screen
    private void runScrapbook() {
        frame = new JFrame("The Journeyman's Scrapbook");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                quit();
            }
        });
        new WelcomePage(frame);
    }

    // REQUIRES: x > 0 && y > 0
    // EFFECTS: returns a small button without an associated action centered on x, y
    protected static JButton createSmallButton(String name, int x, int y) {
        JButton newButton = new JButton(name);
        newButton.setFont(new Font(newButton.getFont().getFontName(), Font.PLAIN, 20));
        newButton.setOpaque(true);
        newButton.setBounds(x - WIDTH / 40, y - HEIGHT / 40, smallButtonWidth, smallButtonHeight);
        return newButton;
    }

    // EFFECTS: saves ms to SAVE_LOCATION file
    protected static void saveMemories(Memories ms) throws FileNotFoundException {
        Saver saver = new Saver(SAVE_LOCATION);
        saver.open();
        saver.save(ms);
        saver.close();
    }

    // EFFECTS: returns memories loaded from SAVE_LOCATION, loads an empty list of memories if file doesn't exist
    protected static Memories loadMemories() {
        Loader loader = new Loader(SAVE_LOCATION);
        try {
            return loader.loadMemories();
        } catch (IOException e) {
            return new Memories();
        }
    }

    // EFFECTS: prints the EventLog to console and exits the application
    protected static void quit() {
        EventLog.getInstance().iterator().forEachRemaining(e -> System.out.println(e.getDescription()));
        System.exit(0);
    }
}
