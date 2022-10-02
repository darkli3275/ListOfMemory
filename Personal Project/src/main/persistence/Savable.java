package persistence;

import org.json.JSONObject;

public interface Savable {

    // EFFECTS: returns a JSON representation of this
    JSONObject toJson();
}
