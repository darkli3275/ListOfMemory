package model;

import exceptions.InvalidDateException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {

    Memory testBlankMemory;
    String arbitraryString;
    String arbitraryStringTwo;

    @BeforeEach
    public void setUp() {
        arbitraryString = "arbitrary string";
        arbitraryStringTwo = "second arbitrary string";
        testBlankMemory = new Memory("");
        assertEquals("", testBlankMemory.getTitle());
    }

    @Test
    public void isDateValidTestValid() {
        String testDate = "01/01/2000";
        assertTrue(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestInvalidString() {
        String testDate = arbitraryString;
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestInvalidSeparatorsTest() {
        String testDate = "01-01-2000";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestInvalidFormatTest() {
        String testDate = "1/1/2000";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestInvalidDayNum() {
        String testDate = "01/32/2000";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestNegativeDayNum() {
        String testDate = "01/-32/2000";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestInvalidMonthNum() {
        String testDate = "13/01/2000";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestNegativeMonthNum() {
        String testDate = "-13/01/2000";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestNegativeYearNum() {
        String testDate = "12/01/-2000";
        assertTrue(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestFutureDate() {
        String testDate = "01/01/2050";
        assertTrue(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestThirtyFirstMonth() {
        String testDate = "01/31/2000";
        assertTrue(Memory.isDateValid(testDate));
    }

    @Test
    public void isDateValidTestThirtiethMonth() {
        String testDate = "09/31/2000";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    //year divisible by 4 so is a leap year
    public void isDateValidTestLeapYearDateOne() {
        String testDate = "02/29/2004";
        assertTrue(Memory.isDateValid(testDate));
    }

    @Test
    //year divisible by 4, but year divisible by 100 so is not a leap year
    public void isDateValidTestLeapYearDateTwo() {
        String testDate = "02/29/1800";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    //year divisible by 4, and year divisible by 100, but year divisible by 400 so is a leap year
    public void isDateValidTestLeapYearDateThree() {
        String testDate = "02/29/2000";
        assertTrue(Memory.isDateValid(testDate));
    }

    @Test
    //arbitrary non-leap year date test
    public void isDateValidTestNotLeapYearDate() {
        String testDate = "02/29/2001";
        assertFalse(Memory.isDateValid(testDate));
    }

    @Test
    public void setTitleTest() {
        testBlankMemory.setTitle(arbitraryString);
        assertEquals(arbitraryString, testBlankMemory.getTitle());
        testBlankMemory.setTitle(arbitraryStringTwo);
        assertEquals(arbitraryStringTwo, testBlankMemory.getTitle());
    }

    @Test
    public void setDescriptionTest() {
        testBlankMemory.setDescription(arbitraryString);
        assertEquals(arbitraryString, testBlankMemory.getDescription());
        testBlankMemory.setDescription(arbitraryStringTwo);
        assertEquals(arbitraryStringTwo, testBlankMemory.getDescription());
    }

    @Test
    public void setLocationTest() {
        testBlankMemory.setLocation(arbitraryString);
        assertEquals(arbitraryString, testBlankMemory.getLocation());
        testBlankMemory.setLocation(arbitraryStringTwo);
        assertEquals(arbitraryStringTwo, testBlankMemory.getLocation());
    }

    @Test
    public void setValidDateTestValid() {
        String validDateOne = "01/31/2000";
        String validDateTwo = "09/01/2003";
        try {
            testBlankMemory.setValidDate(validDateOne);
            assertEquals(validDateOne, testBlankMemory.getDate());
            testBlankMemory.setValidDate(validDateTwo);
            assertEquals(validDateTwo,testBlankMemory.getDate());
        } catch (InvalidDateException e) {
            fail();
        }
    }

    @Test
    public void setValidDateTestInvalid() {
        String invalidDate = "02/29/1800";
        String validDate = "01/01/2000";
        try {
            testBlankMemory.setDate(validDate);
            testBlankMemory.setValidDate(invalidDate);
            fail();
        } catch (InvalidDateException e) {
            // date is invalid, but it is assigned anyway
            assertEquals(validDate, testBlankMemory.getDate());
        }
    }

    @Test
    public void setDateTest() {
        String testDate = "99-99-9999";
        testBlankMemory.setDate(testDate);
        assertEquals(testDate, testBlankMemory.getDate());
    }

    @Test
    public void toJsonBlankMemoryTest() {
        assertEquals("{\"date\":\"01/01/2000\",\"description\":\"\",\"location\":\"\",\"title\":\"\"}",
                testBlankMemory.toJson().toString());
    }

    @Test
    public void memoryToJsonTest() {
        JSONObject testJson = new JSONObject();
        testJson.put("title", "Test Memory");
        testJson.put("description", "JSON test");
        testJson.put("location", "UBC Vancouver");
        testJson.put("date", "07/30/2022");
        testBlankMemory.setTitle("Test Memory");
        testBlankMemory.setDescription("JSON test");
        testBlankMemory.setLocation("UBC Vancouver");
        testBlankMemory.setDate("07/30/2022");
        assertEquals(testJson.toString(), testBlankMemory.toJson().toString());
    }
    /* image-associated features removed to reduce scope of project
    import java.awt.image.BufferedImage;

    Image blankTestImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

    @Test
    public void setBlankTestImage() {
        testBlankMemory.setImg(blankTestImage);
        assertEquals(blankTestImage, testBlankMemory.getImg());
    }
    */
}