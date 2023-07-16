package ui;

import model.Memories;
import model.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import static ui.ScrapbookApp.*;

// A page displaying all memories of a list
public class MemoriesPage {

    private JFrame frame;
    private JLayeredPane pane;
    private Memories loadedMemories;
    private int numMemoriesToDisplay = 4;
    private static int startingIndexForDisplay;

    // REQUIRES: frame is not null
    //           && loadedMemories is not null
    //           && 0 <= startingIndexForDisplay < loadedMemories.getSize()
    // MODIFIES: this
    // EFFECTS: Creates a page with loadedMemories and displays it
    public MemoriesPage(JFrame frame, Memories loadedMemories, int startingIndexForDisplay) {
        this.frame = frame;
        this.loadedMemories = loadedMemories;
        this.startingIndexForDisplay = startingIndexForDisplay;
        setUpPane(loadedMemories);
        frame.setContentPane(pane);
        frame.setVisible(true);
        pane.grabFocus();
    }

    // REQUIRES: ms is not null
    // MODIFIES: this
    // EFFECTS: Sets up the components of memories page
    private void setUpPane(Memories ms) {
        pane = new JLayeredPane();
        pane.setBounds(0, 0, WIDTH, HEIGHT);

        setUpKeyActionHandlers();
        displayMemories(ms, startingIndexForDisplay);
        setUpButtons();
        setUpText();
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds text to pane
    private void setUpText() {
        JLabel memoriesWelcome = new JLabel("Welcome to your memories!", JLabel.CENTER);
        memoriesWelcome.setFont(new Font(memoriesWelcome.getFont().getFontName(), Font.PLAIN, 50));
        memoriesWelcome.setBounds(0, 0, WIDTH, HEIGHT / 8);

        JLabel instructions = new JLabel("Type a number to add new memories or press the +", JLabel.CENTER);
        instructions.setFont(new Font(instructions.getFont().getFontName(), Font.PLAIN, 20));
        instructions.setBounds(0, HEIGHT / 16, WIDTH, HEIGHT / 8);

        pane.add(instructions);
        pane.add(memoriesWelcome);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds buttons to pane
    private void setUpButtons() {
        JButton backToWelcomeButton = createSmallButton("Back",
                horzBorder,
                vertBorder);
        backToWelcomeButton.addActionListener(e -> new WelcomePage(frame));

        JButton addNewMemoryButton = createSmallButton("+",
                WIDTH - horzBorder - smallButtonWidth / 2,
                vertBorder);
        addNewMemoryButton.addActionListener(e -> addNewMemory());

        JButton prevButton = createSmallButton("Prev",
                horzBorder,
                HEIGHT - vertBorder - HEIGHT / 20 - smallButtonHeight / 2);
        prevButton.addActionListener(e -> displayPrevMemories());

        JButton nextButton = createSmallButton("Next",
                WIDTH - horzBorder - smallButtonWidth / 2,
                HEIGHT - vertBorder - smallButtonHeight / 2 - HEIGHT /20);
        nextButton.addActionListener(e -> displayNextMemories());

        JButton saveButton = createSmallButton("Save",
                WIDTH / 2,
                HEIGHT - vertBorder - smallButtonHeight / 2 - HEIGHT / 20);
        saveButton.addActionListener(e -> saveButtonAction());

        pane.add(saveButton);
        pane.add(nextButton);
        pane.add(prevButton);
        pane.add(backToWelcomeButton);
        pane.add(addNewMemoryButton);
    }

    // MODIFIES: this
    // EFFECTS: Adds functionality for key presses to pane
    private void setUpKeyActionHandlers() {
        pane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() >= 48 && e.getKeyCode() <= 57) {
                    addMultipleNewMemories(e.getKeyCode() - 48);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    // EFFECTS: saves loadedMemories and displays message confirming whether save succeeded or failed
    private void saveButtonAction() {
        try {
            saveMemories(loadedMemories);
            saveSuccessConfirmation();
        } catch (FileNotFoundException e) {
            saveFailedConfirmation();
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds save successful message to pane
    private void saveSuccessConfirmation() {
        JLabel saveConfirmation = new JLabel("Successfully Saved!", JLabel.CENTER);
        saveConfirmation.setFont(new Font(saveConfirmation.getFont().getFontName(), Font.PLAIN, 20));
        saveConfirmation.setOpaque(true);
        saveConfirmation.setBounds(WIDTH / 4, HEIGHT - HEIGHT / 50 - 60 - HEIGHT / 10,
                WIDTH / 2, HEIGHT / 10);
        pane.add(saveConfirmation);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds save failed message to pane
    private void saveFailedConfirmation() {
        JLabel saveConfirmation = new JLabel("We encountered a problem trying to save your memories.",
                JLabel.CENTER);
        saveConfirmation.setFont(new Font(saveConfirmation.getFont().getFontName(), Font.PLAIN, 20));
        saveConfirmation.setOpaque(true);
        saveConfirmation.setBounds(WIDTH / 4, HEIGHT - HEIGHT / 50 - 60 - HEIGHT / 10,
                WIDTH / 2, HEIGHT / 10);
        pane.add(saveConfirmation);
    }

    // MODIFIES: this
    // EFFECTS: Creates new memory and adds it to loadedMemories,
    //          makes new MemoriesPage with updated loadedMemories
    private void addNewMemory() {
        loadedMemories.addMemory(new Memory("New Memory"));
        new MemoriesPage(frame, loadedMemories, startingIndexForDisplay);
    }

    private void addMultipleNewMemories(int times) {
        for (int i = 0; i < times; i++) {
            loadedMemories.addMemory(new Memory("New Memory"));
        }
        new MemoriesPage(frame, loadedMemories, startingIndexForDisplay);
    }

    // MODIFIES: this
    // EFFECTS: Displays numMemoriesToDisplay previous memories,
    //          makes new MemoriesPage with updated startingIndexForDisplay
    private void displayPrevMemories() {
        startingIndexForDisplay = startingIndexForDisplay - numMemoriesToDisplay;
        if (startingIndexForDisplay < 0) {
            startingIndexForDisplay = 0;
        }
        new MemoriesPage(frame, loadedMemories, startingIndexForDisplay);
    }

    // MODIFIES: this
    // EFFECTS: Displays the next numMemoriesToDisplay memories,
    //          makes new MemoriesPage with updated startingIndexForDisplay
    private void displayNextMemories() {
        startingIndexForDisplay = startingIndexForDisplay + numMemoriesToDisplay;
        pane.repaint();
        new MemoriesPage(frame, loadedMemories, startingIndexForDisplay);
    }

    // REQUIRES: m is not null AND start >= 0
    // MODIFIES: this
    // EFFECTS: Adds numMemoriesToDisplay memories beginning at index start to pane
    private void displayMemories(Memories m, int start) {
        int spacing = WIDTH / ((numMemoriesToDisplay + 1) * (numMemoriesToDisplay + 1));

        for (int i = start; i < numMemoriesToDisplay + startingIndexForDisplay; i++) {
            int count = i - start;
            JButton memoryButton;
            if (i < m.getSize()) {
                memoryButton = new JButton(m.getMemory(i).getTitle());
                Memory memoryI = loadedMemories.getMemory(i);
                int indexI = i;
                memoryButton.addActionListener(e -> new MemoryPage(frame, loadedMemories, memoryI, indexI));
            } else {
                memoryButton = new JButton("Empty");
                memoryButton.addActionListener(e -> pane.grabFocus());
            }

            memoryButton.setFont(new Font(memoryButton.getFont().getFontName(), Font.PLAIN, 30));
            memoryButton.setOpaque(true);
            memoryButton.setBounds(spacing + (WIDTH / 5 + spacing) * count, HEIGHT / 2 - HEIGHT / 4,
                    WIDTH / 5, HEIGHT / 2);
            pane.add(memoryButton);
        }
    }
}
