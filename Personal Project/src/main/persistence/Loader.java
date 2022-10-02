package persistence;

import model.Memories;
import model.Memory;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;

// Code referenced: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a loader which loads memories from a JSON file
public class Loader {
    private String fileToLoad;

    // EFFECTS: constructs a loader to load memories from fileToLoad
    public Loader(String fileToLoad) {
        this.fileToLoad = fileToLoad;
    }

    // EFFECTS: loads and returns memories from save file
    public Memories loadMemories() throws IOException {
        String data = readFile(fileToLoad);
        JSONObject jsonObject = new JSONObject(data);
        return parseJsonForMemories(jsonObject);
    }

    // EFFECTS: reads fileToLoad and returns string representation of its contents
    private String readFile(String fileToLoad) throws IOException {
        StringBuilder dataAccumulator = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileToLoad))) {
            stream.forEach(s -> dataAccumulator.append(s));
        }
        return dataAccumulator.toString();
    }

    // EFFECTS: parses JSON object for memories and returns memories
    private Memories parseJsonForMemories(JSONObject jsonObject) {
        Memories ms = new Memories();
        JSONArray jsonArray = jsonObject.getJSONArray("memoryList");
        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            ms.addMemory(loadMemory(json));
        }
        return ms;
    }

    // EFFECTS: parses JSON object for memory and returns memory
    private Memory loadMemory(JSONObject jsonMemory) {
        Memory m = new Memory(jsonMemory.getString("title"));
        m.setDescription(jsonMemory.getString("description"));
        m.setLocation(jsonMemory.getString("location"));
        m.setDate(jsonMemory.getString("date"));
        return m;
    }
}
