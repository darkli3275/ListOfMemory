package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import exceptions.InvalidDateException;
import model.Memories;
import model.Memory;
import static ui.ScrapbookApp.*;

// A page for editing a memory's field
public class MemoryEditingPage {
    private JFrame frame;
    private JLayeredPane pane;
    private Memories loadedMemories;
    private Memory memory;
    private int memoryIndex;
    private String field;

    // REQUIRES: frame, loadedMemories, and m are not null
    //           && m is in loadedMemories
    //           && memoryIndex is m's index in loadedMemories
    //           && field is a lowercase string representation of m's field to be edited
    // MODIFIES: this
    // EFFECTS: Creates a page for memory editing and displays it
    public MemoryEditingPage(JFrame frame, Memories loadedMemories, Memory m, int memoryIndex, String field) {
        this.frame = frame;
        this.loadedMemories = loadedMemories;
        this.memory = m;
        this.memoryIndex = memoryIndex;
        this.field = field;
        setUpPane();
        frame.setContentPane(pane);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds memory editing page components to pane
    private void setUpPane() {
        pane = new JLayeredPane();
        pane.setBounds(0, 0, WIDTH, HEIGHT);

        setUpText();
        setUpEditor();
        setUpButtons();
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds text components to pane
    private void setUpText() {
        JLabel title = new JLabel(memory.getTitle(), JLabel.CENTER);
        title.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 100));
        title.setOpaque(false);
        title.setBounds(WIDTH / 2 - WIDTH / 4, vertBorder, WIDTH / 2, HEIGHT / 8);

        JLabel editMessage = new JLabel("Enter a new " + field + " for " + memory.getTitle(), JLabel.CENTER);
        editMessage.setFont(new Font(title.getFont().getFontName(), Font.PLAIN, 30));
        editMessage.setOpaque(false);
        editMessage.setBounds(WIDTH / 2 - WIDTH / 4, HEIGHT / 4, WIDTH / 2, HEIGHT / 10);

        JLabel instructions = new JLabel("Press enter to save your changes.", JLabel.CENTER);
        instructions.setFont(new Font(instructions.getFont().getFontName(), Font.PLAIN, 20));
        instructions.setOpaque(false);
        instructions.setBounds(WIDTH / 2 - WIDTH / 4, HEIGHT - HEIGHT / 8, WIDTH / 2, HEIGHT / 10);

        pane.add(editMessage);
        pane.add(title);
        pane.add(instructions);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds components directly relating to memory editing to pane
    private void setUpEditor() {
        JTextArea textArea = new JTextArea();
        textArea.setOpaque(true);
        textArea.setBounds(horzBorder, HEIGHT / 4 + HEIGHT / 10, WIDTH - horzBorder * 2, HEIGHT / 2);

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleMemoryChange(textArea.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pane.add(textArea);
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds button components to pane
    private void setUpButtons() {
        JButton backToMemoryButton = createSmallButton("Back", horzBorder, vertBorder);
        backToMemoryButton.addActionListener(e -> back());

        pane.add(backToMemoryButton);
    }

    // EFFECTS: Displays the memory page associated with this memory
    private void back() {
        new MemoryPage(frame, loadedMemories, memory, memoryIndex);
    }

    // EFFECTS: Displays the memory page associated with this memory with an invalid date warning
    private void backWithInvalidDate() {
        new MemoryPage(frame, loadedMemories, memory, memoryIndex, true);
    }

    // REQUIRES: text is not null
    // EFFECTS: Handles memory editing based on field given on initialization
    private void handleMemoryChange(String text) {
        switch (field) {
            case "title":
                handleMemoryTitleEditing(text);
                break;
            case "description":
                handleMemoryDescriptionEditing(text);
                break;
            case "location":
                handleMemoryLocationEditing(text);
                break;
            case "date":
                handleMemoryDateEditing(text);
                break;
            default:
                back();
        }
    }

    // REQUIRES: text is not null
    // MODIFIES: Memory
    // EFFECTS: Sets m's title to text and goes to previous screen
    private void handleMemoryTitleEditing(String text) {
        memory.setTitle(text);
        back();
    }

    // REQUIRES: text is not null
    // MODIFIES: Memory
    // EFFECTS: Sets m's description to text and goes to previous screen
    private void handleMemoryDescriptionEditing(String text) {
        memory.setDescription(text);
        back();
    }

    // REQUIRES: text is not null
    // MODIFIES: Memory
    // EFFECTS: Sets m's description and goes to previous screen
    private void handleMemoryLocationEditing(String text) {
        memory.setLocation(text);
        back();
    }

    // REQUIRES: text is not null
    // MODIFIES: Memory
    // EFFECTS: Sets m's date to text and goes to previous screen,
    //          goes to previous screen with invalid date warning if text is not a valid date in format MM/DD/YYYY
    private void handleMemoryDateEditing(String text) {
        try {
            memory.setValidDate(text);
            back();
        } catch (InvalidDateException e) {
            backWithInvalidDate();
        }
    }
}
