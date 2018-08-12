package org.evergreen.verse.handler;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import org.evergreen.client.AmazonDynamoDBProxy;
import org.evergreen.verse.BooknameLookup;
import org.evergreen.verse.VerseFindRequest;
import org.evergreen.verse.VerseReference;
import org.joda.time.DateTime;
import org.jsoup.helper.Validate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvergreenQTTableHandler {
    private AmazonDynamoDBProxy dynamoDB;
    private DateTime dateTime;

    public static final String TABLE_NAME = "EvergreenQT";
    public static final String RANGE_STRING_ATTRIBUTE = "Range";
    public static final String TEXT_STRINGLIST_ATTRIBUTE = "Text";
    public static final String S3_OBJECT_KEY_STRING_ATTRIBUTE = "S3ObjectKey";
    public static final String PRIMARY_KEY_NAME = "Date";

    private Map<PrimaryKey, Item> cachedItemMap = new LinkedHashMap<>();

    public EvergreenQTTableHandler(final AmazonDynamoDBProxy dynamoDB, final DateTime dateTime) {
        this.dynamoDB = dynamoDB;
        this.dateTime = dateTime;
    }

    public VerseFindRequest generateVerseFindRequestFromDB() {
        final String range = retrieveItem().getString(RANGE_STRING_ATTRIBUTE);
        return generateVerseFindRequestFromRange(range);
    }

    public Optional<List<String>> getTextFromDB() {
        return Optional.ofNullable(retrieveItem().getList(TEXT_STRINGLIST_ATTRIBUTE));
    }

    VerseFindRequest generateVerseFindRequestFromRange(final String range) {
        // e.g. 민 수 기25:01-18
        final Matcher matcher = Pattern.compile("(\\s?\\D*)(\\d*):(\\d*)-?(\\d*)?", Pattern.UNICODE_CHARACTER_CLASS)
                .matcher(range);

        matcher.find();

        final String koreanBookName = matcher.group(1);
        final String chapterString = matcher.group(2);
        final String startVerseString = matcher.group(3);
        final String endVerseString = matcher.group(4);
        Validate.notEmpty(koreanBookName);
        Validate.notEmpty(chapterString);
        Validate.notEmpty(startVerseString);

        final VerseReference.Bookname bookname = BooknameLookup.lookUpByKoreanBookname(koreanBookName);
        final Integer chapter = Integer.parseInt(chapterString);
        final Integer startVerse = Integer.parseInt(startVerseString);
        final Optional<Integer> endVerseOptional = !endVerseString.isEmpty() ? Optional.of(Integer.parseInt(endVerseString)) : Optional.empty();

        return new VerseFindRequest.VerseFindRequestBuilder()
                .withStartVerseReference(
                        new VerseReference.VerseReferenceBuilder()
                                .withBookname(bookname)
                                .withChapter(chapter)
                                .withVerse(startVerse)
                                .build())
                .withEndVerseReference(
                        endVerseOptional.map(
                                i ->  new VerseReference.VerseReferenceBuilder()
                                        .withBookname(bookname)
                                        .withChapter(chapter)
                                        .withVerse(i)
                                        .build())
                        )
                .build();
    }

    public Optional<String> getS3ObjectKey() {
        return Optional.ofNullable(retrieveItem().getString(S3_OBJECT_KEY_STRING_ATTRIBUTE));
    }

    public void putText(final List<String> stringList) {
        updateItemStringListAttribute(TEXT_STRINGLIST_ATTRIBUTE, stringList);
    }

    public void putS3ObjectKey(final String s3ObjectKey) {
        updateItemStringAttribute(S3_OBJECT_KEY_STRING_ATTRIBUTE, s3ObjectKey);
    }

    private void updateItemStringAttribute(final String attributeName, final String attributeValue) {
        dynamoDB.updateItemStringAttribute(TABLE_NAME, getPrimaryKey(), attributeName, attributeValue);
    }

    private void updateItemStringListAttribute(final String attributeName, final List<String> attributeValueList) {
        dynamoDB.updateItemStringListAttribute(TABLE_NAME, getPrimaryKey(), attributeName, attributeValueList);
    }

    private Item retrieveItem() {
        final PrimaryKey primaryKey = getPrimaryKey();
        if (!cachedItemMap.containsKey(primaryKey)) {
            final Item item = dynamoDB.retrieveItem(TABLE_NAME, getPrimaryKey());
            cachedItemMap.put(primaryKey, item);
        }

        return cachedItemMap.get(primaryKey);
    }

    PrimaryKey getPrimaryKey() {
        return new PrimaryKey(PRIMARY_KEY_NAME, String.format("%s-%s-%s", dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth()));
    }

}
