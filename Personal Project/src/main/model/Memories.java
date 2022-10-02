package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Savable;
import java.util.List;
import java.util.ArrayList;

// Represents a list of memories
public class Memories implements Savable {
    List<Memory> memoryList;

    // EFFECTS: creates a new, empty list of memories
    public Memories() {
        memoryList = new ArrayList<>();
    }

    // MODIFIES: this, EventLog
    // EFFECTS: adds m to the end of the list of memories,
    //          adds an event to EventLog
    public void addMemory(Memory m) {
        memoryList.add(m);
        EventLog.getInstance().logEvent(new Event("1 memory has been added"));
    }

    // REQUIRES: 0 < indexNum < memoryList.size() - 1
    // MODIFIES: this, EventLog
    // EFFECTS: removes the memory at indexNum from the list of memories,
    //          adds an event to EventLog
    public void removeMemory(int indexNum) {
        memoryList.remove(indexNum);
        EventLog.getInstance().logEvent(new Event("1 memory has been removed"));
    }

    // REQUIRES: 0 < indexNum < memoryList.size() - 1
    // EFFECTS: returns the memory at given indexNum from the list of memories
    public Memory getMemory(int indexNum) {
        return memoryList.get(indexNum);
    }

    // EFFECTS: returns size of list of memories
    public int getSize() {
        return memoryList.size();
    }


    // Code referenced: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: return a JSON representation of this
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memoryList", memoryList);
        return jsonObject;
    }

    // Code referenced: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns a Json representation of memoryList in this
    protected JSONArray memoryListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Memory m: memoryList) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    }
}
