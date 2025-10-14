package org.jsoup.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookieUtilTest {

    @Test
    void parseCookie() {
        HttpConnection.Response res = new HttpConnection.Response();

        CookieUtil.parseCookie("foo=bar qux; Domain=.example.com; Path=/; Secure", res);
        CookieUtil.parseCookie("bar=foo qux", res);
        CookieUtil.parseCookie("=bar; Domain=.example.com; Path=/; Secure", res);
        CookieUtil.parseCookie("; Domain=.example.com; Path=/", res);
        CookieUtil.parseCookie("", res);
        CookieUtil.parseCookie(null, res);

        assertEquals(3, res.cookies().size());
        assertEquals("bar qux", res.cookies.get("foo"));
        assertEquals("foo qux", res.cookies.get("bar"));
        assertEquals(".example.com", res.cookies.get("; Domain")); // no actual cookie name or val
    }

    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Cookies
    // We added to try and cover CookieUtil 108
    @Test
    void parseCookie_coversNullEmptyAndEmptyName() {
        HttpConnection.Response res = new HttpConnection.Response();
        // ignore if empty or null
        CookieUtil.parseCookie(null, res);
        CookieUtil.parseCookie("", res);
        assertTrue(res.cookies().isEmpty(), "No cookies should be added for null or empty input");
    }

    @Test
    void parseCookie_setsEmptyValueAndOverwritesDuplicates() {
        HttpConnection.Response res = new HttpConnection.Response();

        // Empty value
        CookieUtil.parseCookie("foo=", res);
        assertEquals("", res.cookies.get("foo"));

        // double valute -> pick the last one
        CookieUtil.parseCookie("foo=bar; Path=/", res);
        assertEquals("bar", res.cookies.get("foo"));
    }

    @Test
    void parseCookie_handlesNameWithoutEqualsAndLeadingSemicolonDomainCase() {
        HttpConnection.Response res = new HttpConnection.Response();

        // no '=' -> name=all token, value=""
        CookieUtil.parseCookie("abc", res);
        assertEquals("", res.cookies.get("abc"));

        // if leading with ';' -> name is "; Domain", value up to ';'
        CookieUtil.parseCookie("; Domain=.example.com; Path=/", res);
        assertEquals(".example.com", res.cookies.get("; Domain"));
    }

    @Test
    void parseCookie_trimsWhitespaceAndIgnoresAttributes() {
        HttpConnection.Response res = new HttpConnection.Response();

        CookieUtil.parseCookie("spaces = spaced value ; HttpOnly; Secure", res);
        assertEquals("spaced value", res.cookies.get("spaces"),
                "Namn och värde ska trimmas; attribut ignoreras");

        // extra: cookie affected by attributes
        CookieUtil.parseCookie("dup=1", res);
        CookieUtil.parseCookie("dup=2; Secure", res);
        assertEquals("2", res.cookies.get("dup"), "Sista värdet ska vinna vid dubblett");
    }

    @Test
    void parseCookie_regularNameValueWithAttributes() {
        HttpConnection.Response res = new HttpConnection.Response();

        CookieUtil.parseCookie("foo=bar qux; Domain=.example.com; Path=/; Secure", res);
        CookieUtil.parseCookie("bar=foo qux", res);

        assertEquals("bar qux", res.cookies.get("foo"));
        assertEquals("foo qux", res.cookies.get("bar"));
    }
}
