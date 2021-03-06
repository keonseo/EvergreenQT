package org.evergreen.verse;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class BooknameLookup {

    private static Map<String, VerseReference.Bookname> booknameMap;

    static {
        booknameMap = Arrays.stream(VerseReference.Bookname.values())
            .collect(Collectors.toMap(VerseReference.Bookname::getKoreanBookName, Function.identity()));
    }

    private BooknameLookup() { }

    public static VerseReference.Bookname lookUpByKoreanBookname(final String koreanBookName) {
        return booknameMap.get(koreanBookName.replace(" ", ""));
    }
}
