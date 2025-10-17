package org.jsoup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jsoup.helper.ValidationException;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ParserTest {
    @Test
    void nullTagThrows() {
        assertThrows(ValidationException.class, () -> Parser.htmlParser().tagSet(null));
    }

    @Test
    void nullStringThrowsInUnescapedEntitites() {
        assertThrows(ValidationException.class, () -> Parser.htmlParser().unescapeEntities(null, false));
    }

    @Test
    void unescapedEntityReturnsInputString() {
        String expected = "unescaped";
        String actual = Parser.htmlParser().unescapeEntities(expected, false);

        assertEquals(expected, actual);
        assertNotEquals("", actual);
    }

    @ParameterizedTest
    @ValueSource(strings = { "&", "escaped&" })
    void escapedEntitiesAreEscaped(String input) {
        String expected = input.replace("&", "");

        String actual = Parser.htmlParser().unescapeEntities(expected, false);

        assertEquals(expected, actual);

    }

}
