package org.evergreen.client;

import com.google.common.collect.ImmutableList;
import org.evergreen.verse.Passages;
import org.evergreen.verse.VerseFindRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class IBiblesNetProxy {

    private final static String IBIBLESNET_URI_PREFIX = "http://ibibles.net/quote.php?kor-";

    public IBiblesNetProxy() { }

    public Passages getPassages(final VerseFindRequest request) {
        try {
            //1. Construct and retrieve raw HTML that contains scripture
            final Document document = Jsoup.connect(constructURI(request)).get();

            //2. Parse out the raw text from HTML
            final List<String> rawTextStringList = parseDocument(document);

            return new Passages.PassagesBuilder()
                .withStartVerseReference(request.getStartVerseReference())
                .withEndVerseReference(request.getEndVerseReference())
                .withTexts(rawTextStringList)
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> parseDocument(final Document document) {
        return document.body().textNodes().stream()
                .map(n -> n.toString().trim())
                .filter(s -> !s.isEmpty())
                .collect(ImmutableList.toImmutableList());
    }

    public String constructURI(final VerseFindRequest request) {
        // e.g. http://ibibles.net/quote.php?kor-mat/5:3-12

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(IBIBLESNET_URI_PREFIX);

        final String bookNameCode;

        switch (request.getStartVerseReference().getBookname()) {
            case GENESIS:
                bookNameCode = "ge";
                break;
            case EXODUS:
                bookNameCode = "exo";
                break;
            case LEVITICUS:
                bookNameCode = "lev";
                break;
            case NUMBERS:
                bookNameCode = "num";
                break;
            case DEUTERONOMY:
                bookNameCode = "deu";
                break;
            case JOSHUA:
                bookNameCode = "josh";
                break;
            case JUDGES:
                bookNameCode = "jdgs";
                break;
            case RUTH:
                bookNameCode = "ruth";
                break;
            case SAMUEL_FIRST:
                bookNameCode = "1sm";
                break;
            case SAMUEL_SECOND:
                bookNameCode = "2sm";
                break;
            case KINGS_FIRST:
                bookNameCode = "1ki";
                break;
            case KINGS_SECOND:
                bookNameCode = "2ki";
                break;
            case CHRONICLES_FIRST:
                bookNameCode = "1chr";
                break;
            case CHRONICLES_SECOND:
                bookNameCode = "2chr";
                break;
            case EZRA:
                bookNameCode = "ezra";
                break;
            case NEHEMIA:
                bookNameCode = "neh";
                break;
            case ESTHER:
                bookNameCode = "est";
                break;
            case JOB:
                bookNameCode = "job";
                break;
            case PSALMS:
                bookNameCode = "psa";
                break;
            case PROVERBS:
                bookNameCode = "prv";
                break;
            case ECCLESIATES:
                bookNameCode = "eccl";
                break;
            case SONG_OF_SOLOMON:
                bookNameCode = "ssol";
                break;
            case ISAIAH:
                bookNameCode = "isa";
                break;
            case JEREMIAH:
                bookNameCode = "jer";
                break;
            case LAMENTATIONS:
                bookNameCode = "lam";
                break;
            case EZEKIEL:
                bookNameCode = "eze";
                break;
            case DANIEL:
                bookNameCode = "dan";
                break;
            case HOSEA:
                bookNameCode = "hos";
                break;
            case JOEL:
                bookNameCode = "joel";
                break;
            case AMOS:
                bookNameCode = "amos";
                break;
            case OBADIAH:
                bookNameCode = "obad";
                break;
            case JONAH:
                bookNameCode = "jonah";
                break;
            case MICAH:
                bookNameCode = "mic";
                break;
            case NAHUM:
                bookNameCode = "nahum";
                break;
            case HABAKKUK:
                bookNameCode = "hab";
                break;
            case ZEPHANIAH:
                bookNameCode = "zeph";
                break;
            case HAGGAI:
                bookNameCode = "hag";
                break;
            case ZECHARIAH:
                bookNameCode = "zec";
                break;
            case MALACHI:
                bookNameCode = "mal";
                break;
            case MATTHEW:
                bookNameCode = "mat";
                break;
            case MARK:
                bookNameCode = "mark";
                break;
            case LUKE:
                bookNameCode = "luke";
                break;
            case JOHN:
                bookNameCode = "john";
                break;
            case ACTS:
                bookNameCode = "acts";
                break;
            case ROMANS:
                bookNameCode = "rom";
                break;
            case CORIANTHIANS_FIRST:
                bookNameCode = "1cor";
                break;
            case CORIANTHIANS_SECOND:
                bookNameCode = "2cor";
                break;
            case GALATIANS:
                bookNameCode = "gal";
                break;
            case EPHESIANS:
                bookNameCode = "eph";
                break;
            case PHILIPPIANS:
                bookNameCode = "phi";
                break;
            case COLOSSIANS:
                bookNameCode = "col";
                break;
            case THESSALONIANS_FIRST:
                bookNameCode = "1th";
                break;
            case THESSALONIANS_SECOND:
                bookNameCode = "2th";
                break;
            case TIMOTHY_FIRST:
                bookNameCode = "1tim";
                break;
            case TIMOTHY_SECOND:
                bookNameCode = "2tim";
                break;
            case TITUS:
                bookNameCode = "titus";
                break;
            case PHILEMON:
                bookNameCode = "phmn";
                break;
            case HEBREWS:
                bookNameCode = "heb";
                break;
            case JAMES:
                bookNameCode = "james";
                break;
            case PETER_FIRST:
                bookNameCode = "1pet";
                break;
            case PETER_SECOND:
                bookNameCode = "2pet";
                break;
            case JOHN_FIRST:
                bookNameCode = "1jn";
                break;
            case JOHN_SECOND:
                bookNameCode = "2jn";
                break;
            case JOHN_THIRD:
                bookNameCode = "3jn";
                break;
            case JUDE:
                bookNameCode = "jude";
                break;
            case REVELATION:
                bookNameCode = "rev";
                break;

            default:
                throw new IllegalArgumentException("Received invalid book name " + request.getStartVerseReference().getBookname());
        }

        stringBuilder.append(bookNameCode);
        stringBuilder.append("/");
        stringBuilder.append(request.getStartVerseReference().getChapter());
        stringBuilder.append(":");
        stringBuilder.append(request.getStartVerseReference().getVerse());

        // Relies on validation performed on VerseFindRequest.VerseFindRequestBuilder.build()
        request.getEndVerseReference().ifPresent(i ->
                stringBuilder.append("-" + i.getVerse()));

        return stringBuilder.toString();
    }
}
