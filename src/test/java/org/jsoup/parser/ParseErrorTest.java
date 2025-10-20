package org.jsoup.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ParseErrorTest {

    @Test
    void returnValueCanNotBeEmpty() {

        ParseError error = new ParseError(8, "Sample error");

        String cursor = error.getCursorPos();
        assertEquals("8", cursor);
    }

    @Test
    void testParseErrorConstructor() {
        int pos = 8;

        String detail = "some error detail";

        ParseError error = new ParseError(pos, "Error at position %d: %s", pos, detail);

        assertEquals(error.getCursorPos(), "8"); // checks if cursorPos is correctly set

    }

}
