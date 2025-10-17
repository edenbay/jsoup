package org.jsoup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UnsupportedMimeTypeExceptionTest {

    private UnsupportedMimeTypeException ex() {
        return new UnsupportedMimeTypeException(
                "boom", "text/weird", "http://example.com/x");
    }

    @Test
    void returnsMimeTypeAndIsNotEmpty() {
        UnsupportedMimeTypeException e = ex();
        assertEquals("text/weird", e.getMimeType());
        assertNotEquals("", e.getMimeType()); // dödar "return ''"-mutanten
    }

    @Test
    void returnsUrlAndIsNotEmpty() {
        UnsupportedMimeTypeException e = ex();
        assertEquals("http://example.com/x", e.getUrl());
        assertNotEquals("", e.getUrl()); // täcker + dödar mutanten
    }

    @Test
    void toStringContainsKeyDetails() {
        UnsupportedMimeTypeException e = ex();
        String s = e.toString();
        // vi låser inte exakt format – bara viktiga delar
        assertTrue(s.contains("text/weird"));
        assertTrue(s.contains("http://example.com/x"));
        assertFalse(s.isEmpty()); // dödar "return ''"-mutanten
    }
}
