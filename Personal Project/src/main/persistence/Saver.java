package persistence;

import org.json.JSONObject;
import java.io.*;
import model.Memories;

// Code referenced: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a saver which can save a JSON representation of memories to a file
public class Saver {
    private String saveLocation;
    private PrintWriter printWriter;

    // EFFECTS: constructs a saver which can save data to saveLocation file
    public Saver(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    // MODIFIES: this
    // EFFECTS: opens saver to write to its assigned file
    public void open() throws FileNotFoundException {
        printWriter = new PrintWriter(saveLocation);
    }

    // MODIFIES: this
    // EFFECTS: writes a JSON representation of ms to save file
    public void save(Memories ms) {
        JSONObject jsonObject = ms.toJson();
        printWriter.print(jsonObject.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes saver
    public void close() {
        printWriter.close();
    }
}
