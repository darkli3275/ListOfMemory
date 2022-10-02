package model;

import exceptions.InvalidDateException;
import org.json.JSONObject;
import persistence.Savable;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

// Represents a memory having a title, description, location, and date.
public class Memory implements Savable {
    private String title;
    private String description;
    private String location;
    private String date;        // date formatted mm/dd//yyyy, future dates and negative years allowed
    public static final String DEFAULT_DATE = "01/01/2000";

    // EFFECTS: creates a new memory given a title and gives it a blank description, location, and a default date
    public Memory(String title) {
        this.title = title;
        this.description = "description";
        this.location = "location, location";
        this.date = DEFAULT_DATE;
    }

    // EFFECTS: returns true if date is valid, false otherwise; negative years will be valid
    public static boolean isDateValid(String date) {
        DateTimeFormatter dateString = DateTimeFormatter.ofPattern("MM/dd/uuuu",
                Locale.CANADA).withResolverStyle(ResolverStyle.STRICT);
        try {
            dateString.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets title of memory to given title
    public void setTitle(String title) {
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: sets description of memory to given description
    public void setDescription(String description) {
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: sets location of memory to given location
    public void setLocation(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: sets date of memory to given date if valid
    public void setValidDate(String date) throws InvalidDateException {
        if (isDateValid(date)) {
            this.date = date;
        } else {
            throw new InvalidDateException();
        }
    }

    // Code referenced: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: returns a JSON representation of this memory
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);
        jsonObject.put("description", description);
        jsonObject.put("location", location);
        jsonObject.put("date", date);
        return jsonObject;
    }

    // MODIFIES: this
    // EFFECTS: sets date of memory to given date regardless of validity
    public void setDate(String date) {
        this.date = date;
    }

    // Simple field getter methods below:
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}

/* image feature removed to reduce scope of project

    private Image img; //arbitrary image, no size restriction for now

    // MODIFIES: this
    // EFFECTS: sets the image associated with this memory to the given image
    public void setImg(Image img) {
        this.img = img;
    }

    removed to reduce scope of project
    public Image getImg() {
        return img;
    }
*/
