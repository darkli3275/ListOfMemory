package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MemoriesTest {

    Memories testMemories;
    Memory testMemoryOne;
    Memory testMemoryTwo;

    @BeforeEach
    public void setUp() {
        testMemories = new Memories();
        testMemoryOne = new Memory("test one");
        testMemoryTwo = new Memory("test two");
    }

    @Test
    public void constructorTest() {
        assertEquals(0, testMemories.getSize());
    }

    @Test
    public void addOneMemoryTest() {
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
    }

    @Test
    public void addTwoMemoryTest() {
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
        testMemories.addMemory(testMemoryTwo);
        assertEquals(2, testMemories.getSize());
        assertEquals(testMemoryTwo, testMemories.getMemory(1));
    }

    @Test
    public void removeOneMemoryTest() {
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
        testMemories.removeMemory(0);
        assertEquals(0, testMemories.getSize());
    }

    @Test
    public void removeTwoMemoryTest() {
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
        testMemories.addMemory(testMemoryTwo);
        assertEquals(2, testMemories.getSize());
        assertEquals(testMemoryTwo, testMemories.getMemory(1));
        testMemories.removeMemory(0);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryTwo, testMemories.getMemory(0));
        testMemories.removeMemory(0);
        assertEquals(0, testMemories.getSize());
    }

    @Test
    public void memoryListToJsonTest() {
        testMemories.addMemory(testMemoryOne);
        testMemories.addMemory(testMemoryTwo);
        JSONArray testJson = new JSONArray();
        testJson.put(testMemoryOne.toJson());
        testJson.put(testMemoryTwo.toJson());
        assertEquals(testJson.toString(), testMemories.memoryListToJson().toString());
    }

    @Test
    public void memoriesToJsonTest() {
        testMemories.addMemory(testMemoryOne);
        testMemories.addMemory(testMemoryTwo);
        ArrayList<Memory> testMemoryList = new ArrayList<>();
        testMemoryList.add(testMemoryOne);
        testMemoryList.add(testMemoryTwo);
        JSONObject testJson = new JSONObject();
        testJson.put("memoryList",testMemoryList);
        assertEquals(testJson.toString(), testMemories.toJson().toString());
    }

/*
    File saveFile = new File(Memories.saveLocation);
    @Test
    public void loadMemoriesTestExpectFileNotFoundException() {
        // deletes any existing save file before testing loading
        saveFile.delete();
        //Memories.loadMemories should return a Memories with an empty list of Memory items
        testMemories = Memories.loadMemories();
        assertEquals(0, testMemories.getSize());
    }

    @Test
    public void saveMemoriesTestFileAlreadyExists() {
        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            // do nothing if file already exists
        }
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
        testMemories.addMemory(testMemoryTwo);
        assertEquals(2, testMemories.getSize());
        assertEquals(testMemoryTwo, testMemories.getMemory(1));
        try {
            testMemories.saveMemories();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void saveMemoriesTestSaveFileDoesNotExist() {
        saveFile.delete();
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
        testMemories.addMemory(testMemoryTwo);
        assertEquals(2, testMemories.getSize());
        assertEquals(testMemoryTwo, testMemories.getMemory(1));
        try {
            testMemories.saveMemories();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void saveMemoriesAndLoadMemoriesTestExpectSuccess() {
        // creates fresh save file to set up for loadMemories test
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
        testMemories.addMemory(testMemoryTwo);
        assertEquals(2, testMemories.getSize());
        assertEquals(testMemoryTwo, testMemories.getMemory(1));
        try {
            testMemories.saveMemories();
        } catch (IOException e) {
            fail();
        }

        // test begins
        testLoadMemories = Memories.loadMemories();
        for (int i = 0; i <testMemories.getSize(); i++) {
            assertEquals(testMemories.getMemory(i).getTitle(), testLoadMemories.getMemory(i).getTitle());
            assertEquals(testMemories.getMemory(i).getDescription(), testLoadMemories.getMemory(i).getDescription());
            assertEquals(testMemories.getMemory(i).getLocation(), testLoadMemories.getMemory(i).getLocation());
            assertEquals(testMemories.getMemory(i).getDate(), testLoadMemories.getMemory(i).getDate());
        }
    }

    @Test
    public void loadMemoriesTestExpectInvalidDate() {
        testMemoryOne.setDate("not a real date");
        // creates fresh save file to set up for loadMemories test
        testMemories.addMemory(testMemoryOne);
        assertEquals(1, testMemories.getSize());
        assertEquals(testMemoryOne, testMemories.getMemory(0));
        testMemories.addMemory(testMemoryTwo);
        assertEquals(2, testMemories.getSize());
        assertEquals(testMemoryTwo, testMemories.getMemory(1));
        try {
            testMemories.saveMemories();
        } catch (IOException e) {
            fail();
        }

        // test begins
        testLoadMemories = Memories.loadMemories();
        for (int i = 0; i < testMemories.getSize(); i++) {
            assertEquals(testMemories.getMemory(i).getTitle(), testLoadMemories.getMemory(i).getTitle());
            assertEquals(testMemories.getMemory(i).getDescription(), testLoadMemories.getMemory(i).getDescription());
            assertEquals(testMemories.getMemory(i).getLocation(), testLoadMemories.getMemory(i).getLocation());
            if (Memory.isDateValid(testLoadMemories.getMemory(i).getDate())) {
                assertEquals(testMemories.getMemory(i).getDate(), testLoadMemories.getMemory(i).getDate());
            } else {
                assertEquals("not a real date", testLoadMemories.getMemory(i).getDate());
            }
        }
    }*/
}
