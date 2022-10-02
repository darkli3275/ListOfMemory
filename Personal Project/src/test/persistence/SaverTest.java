package persistence;

import org.junit.jupiter.api.Test;
import model.Memories;
import model.Memory;

import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class SaverTest {

    @Test
    void openTestInvalidFile() {
        try {
            Memories ms = new Memories();
            Saver saver = new Saver("./data/\0");
            saver.open();
            fail();
        } catch (FileNotFoundException e) {
            // expecting IOException
        }
    }

    @Test
    void saveEmptyMemoriesTest() {
        try {
            Memories ms = new Memories();
            Saver saver = new Saver("./data/saveEmptyMemoriesTest.json");
            saver.open();
            saver.save(ms);
            saver.close();
            Loader loader = new Loader("./data/saveEmptyMemoriesTest.json");
            Memories loadedMemories = loader.loadMemories();
            assertEquals(0, loadedMemories.getSize());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void saveGeneralMemoriesTest() {
        try {
            Memories ms = new Memories();
            ms.addMemory(new Memory("test uno"));
            ms.addMemory(new Memory("test dos"));
            Saver saver = new Saver("./data/saveGeneralMemoriesTest.json");
            saver.open();
            saver.save(ms);
            saver.close();
            Loader loader = new Loader("./data/saveGeneralMemoriesTest.json");
            Memories loadedMemories = loader.loadMemories();
            assertEquals(2, loadedMemories.getSize());
            assertEquals("test uno", loadedMemories.getMemory(0).getTitle());
            assertEquals("", loadedMemories.getMemory(0).getDescription());
            assertEquals("", loadedMemories.getMemory(0).getLocation());
            assertEquals("01/01/2000", loadedMemories.getMemory(0).getDate());
            assertEquals("test dos", loadedMemories.getMemory(1).getTitle());
            assertEquals("", loadedMemories.getMemory(1).getDescription());
            assertEquals("", loadedMemories.getMemory(1).getLocation());
            assertEquals("01/01/2000", loadedMemories.getMemory(1).getDate());
        } catch (IOException e) {
            fail();
        }
    }
}
