package org.evergreen.verse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BooknameLookupTest {

    @Test
    public void lookupBooknameByKoreanBooknameString() {
        assertEquals(VerseReference.Bookname.NUMBERS, BooknameLookup.lookUpByKoreanBookname("민수기"));
    }

    @Test
    public void lookupBooknameByKoreanBooknameStringWithEmptySpaces() {
        assertEquals(VerseReference.Bookname.NUMBERS, BooknameLookup.lookUpByKoreanBookname("민 수 기"));
    }
}
