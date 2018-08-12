package org.evergreen.verse;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PassagesTest {

    final List<String> rawTextList = ImmutableList.of("Hello", "Hi", "Testing", "Test", "Fifth Entry");

    final VerseReference verseReference1 = new VerseReference.VerseReferenceBuilder()
            .withBookname(VerseReference.Bookname.CHRONICLES_FIRST)
            .withChapter(2)
            .withVerse(5)
            .build();

    final VerseReference verseReference2 = new VerseReference.VerseReferenceBuilder()
            .withBookname(VerseReference.Bookname.CHRONICLES_FIRST)
            .withChapter(2)
            .withVerse(9)
            .build();

    @Test
    public void passageBuildTestMatchingEntries() {
        final Passages passages = new Passages.PassagesBuilder()
                .withTexts(rawTextList)
                .withStartVerseReference(verseReference1)
                .withEndVerseReference(Optional.of(verseReference2))
                .build();

        assertEquals("Hello. Hi. Testing. Test. Fifth Entry", passages.getTexts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void passageBuildTestNotMatchingEntries() {
        new Passages.PassagesBuilder()
                .withTexts(rawTextList)
                .withStartVerseReference(verseReference1)
                .build();
    }
}
