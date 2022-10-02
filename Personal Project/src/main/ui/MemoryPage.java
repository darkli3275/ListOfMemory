package ui;

import javax.swing.*;
import java.awt.*;
import model.Memory;
import model.Memories;
import static ui.ScrapbookApp.*;

// A page displaying a memory
public class MemoryPage {
    private JFrame frame;
    private JLayeredPane pane;
    private Memories loadedMemories;
    private Memory memory;
    private int memoryIndex;
    private final String invalidDateMessage = "The date you entered was invalid. "
            + "Please enter a valid date in the form: \"MM/DD/YYYY\"";

    // REQUIRES: frame, loadedMemories, and m are not null
    //           && m is in loadedMemories
    //           && memoryIndex is m's index in loadedMemories
    // MODIFIES: this
    // EFFECTS: Creates a memory page and displays it
    public MemoryPage(JFrame frame, Memories loadedMemories, Memory m, int memoryIndex) {
        this.frame = frame;
        this.loadedMemories = loadedMemories;
        this.memory = m;
        this.memoryIndex = memoryIndex;

        setUpPane();
        frame.setContentPane(pane);
        frame.setVisible(true);
    }

    // REQUIRES: frame is not null
    // MODIFIES: this
    // EFFECTS: Creates a memory page with an invalid date warning and displays it
    public MemoryPage(JFrame frame, Memories loadedMemories, Memory m, int memoryIndex, boolean invalidDateWarning) {
        this.frame = frame;
        this.loadedMemories = loadedMemories;
        this.memory = m;
        this.memoryIndex = memoryIndex;

        setUpPane();
        if (invalidDateWarning) {
            JLabel dateWarning = new JLabel(invalidDateMessage, JLabel.CENTER);
            dateWarning.setFont(new Font(dateWarning.getFont().getFontName(), Font.PLAIN, 20));
            dateWarning.setOpaque(true);
            dateWarning.setBounds(0, HEIGHT - horzBorder - HEIGHT / 20,
                    WIDTH, HEIGHT / 10);
            pane.add(dateWarning);
        }
        frame.setContentPane(pane);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds memory page components to pane
    private void setUpPane() {
        pane = new JLayeredPane();
        pane.setBounds(0, 0, WIDTH, HEIGHT);

        setUpMemoryInfo();
        setUpButtons();
    }

    // EFFECTS: Adds components regarding memory information to pane
    private void setUpMemoryInfo() {
        displayMemoryTitle();
        displayMemoryDescription();
        displayMemoryLocation();
        displayMemoryDate();
    }

    // REQUIRES: memory.getTitle() is not null
    // MODIFIES: this
    // EFFECTS: Creates and adds memory title component to pane
    private void displayMemoryTitle() {
        JButton titleButton = new JButton(memory.getTitle());
        titleButton.setFont(new Font(titleButton.getFont().getFontName(), Font.PLAIN, 50));
        titleButton.setOpaque(true);
        titleButton.setBounds(WIDTH / 2 - WIDTH / 6, vertBorder, WIDTH / 3, HEIGHT / 6);
        titleButton.addActionListener(e ->
                new MemoryEditingPage(frame, loadedMemories, memory, memoryIndex, "title"));

        pane.add(titleButton);
    }

    // REQUIRES: memory.getDescription() is not null
    // MODIFIES: this
    // EFFECTS: Creates and adds memory description component to pane
    private void displayMemoryDescription() {
        JButton descriptionButton = new JButton(memory.getDescription());
        descriptionButton.setFont(new Font(descriptionButton.getFont().getFontName(), Font.PLAIN, 15));
        descriptionButton.setOpaque(true);
        descriptionButton.setBounds(horzBorder, vertBorder + HEIGHT / 6 + smallButtonHeight * 2,
                WIDTH - horzBorder * 2,
                HEIGHT - vertBorder - HEIGHT / 6 - smallButtonHeight * 4 - vertBorder);
        descriptionButton.addActionListener(e ->
                new MemoryEditingPage(frame, loadedMemories, memory, memoryIndex, "description"));

        pane.add(descriptionButton);
    }

    // REQUIRES: memory.getLocation() is not null
    // MODIFIES: this
    // EFFECTS: Creates and adds memory location component to pane
    private void displayMemoryLocation() {
        JButton locationButton = new JButton(memory.getLocation());
        locationButton.setFont(new Font(locationButton.getFont().getFontName(), Font.PLAIN, 20));
        locationButton.setOpaque(true);
        locationButton.setBounds(WIDTH / 2 - WIDTH / 3, vertBorder + HEIGHT / 6,
                WIDTH / 3, smallButtonHeight);
        locationButton.addActionListener(e ->
                new MemoryEditingPage(frame, loadedMemories, memory, memoryIndex, "location"));

        pane.add(locationButton);
    }

    // REQUIRES: memory.getDate() is not null
    // MODIFIES: this
    // EFFECTS: Creates and adds memory date component to pane
    private void displayMemoryDate() {
        JButton dateButton = new JButton(memory.getDate());
        dateButton.setFont(new Font(dateButton.getFont().getFontName(), Font.PLAIN, 20));
        dateButton.setOpaque(true);
        dateButton.setBounds(WIDTH / 2, vertBorder + HEIGHT / 6,
                WIDTH / 3, smallButtonHeight);
        dateButton.addActionListener(e ->
                new MemoryEditingPage(frame, loadedMemories, memory, memoryIndex, "date"));

        pane.add(dateButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds memory page buttons to pane
    private void setUpButtons() {
        JButton backToMemoriesButton = createSmallButton("Back", horzBorder, vertBorder);
        backToMemoriesButton.addActionListener(e -> new MemoriesPage(frame, loadedMemories, memoryIndex));

        JButton deleteButton = createSmallButton("Del",
                WIDTH - horzBorder, HEIGHT - vertBorder - smallButtonHeight);
        deleteButton.addActionListener(e -> handleMemoryDeleted(memoryIndex));

        pane.add(backToMemoriesButton);
        pane.add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: Removes memory at memoryIndex in loadedMemories and goes to MemoriesPage
    private void handleMemoryDeleted(int memoryIndex) {
        loadedMemories.removeMemory(memoryIndex);
        new MemoriesPage(frame, loadedMemories, memoryIndex);
    }

}
