package persistence;

import org.junit.jupiter.api.Test;
import persistence.Loader;
import model.Memories;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class LoaderTest {

    @Test
    void loadMemoriesTestNonExistentFile() {
        Loader loader = new Loader("./data/nonExistentFile.json");
        try {
            Memories ms = loader.loadMemories();
            fail();
        } catch (IOException e) {
            // expecting ioexception
        }
    }

    @Test
    void loadMemoriesTestEmptyMemories() {
        Loader loader = new Loader("./data/loadMemoriesTestEmptyMemories.json");
        try {
            Memories ms = loader.loadMemories();
            assertEquals(0, ms.getSize());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void loadMemoriesTestGeneralCase() {
        Loader loader = new Loader("./data/loadMemoriesTestGeneralCase.json");
        try {
            Memories ms = loader.loadMemories();
            assertEquals(2, ms.getSize());
            assertEquals("test one", ms.getMemory(0).getTitle());
            assertEquals("", ms.getMemory(0).getDescription());
            assertEquals("", ms.getMemory(0).getLocation());
            assertEquals("01/01/2000", ms.getMemory(0).getDate());
            assertEquals("test two", ms.getMemory(1).getTitle());
            assertEquals("this is a test", ms.getMemory(1).getDescription());
            assertEquals("UBC Vancouver", ms.getMemory(1).getLocation());
            assertEquals("01/01/2000", ms.getMemory(1).getDate());
        } catch (IOException e) {
            fail();
        }
    }
}
