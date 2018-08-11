package org.evergreen.verse.handler;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import org.evergreen.client.AmazonDynamoDBProxy;
import org.evergreen.verse.VerseFindRequest;
import org.evergreen.verse.VerseReference;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EvergreenQTTableHandlerTest {

    @Mock
    AmazonDynamoDBProxy dynamoDB;

    @Mock
    Item item;

    EvergreenQTTableHandler handler;

    private static final String RANGE_1 = "민수기25:01-18";
    private static final String RANGE_2 = "민수기25:01";

    @Before
    public void setup() {
        handler = new EvergreenQTTableHandler(dynamoDB, new DateTime(2018, 8, 8, 0, 0, 0));
    }

    @Test
    public void createPrimaryKeyWithDate() {
        assertEquals(handler.getPrimaryKey(), new PrimaryKey(EvergreenQTTableHandler.PRIMARY_KEY_NAME, "2018-8-8"));
    }

    @Test
    public void generateVerseFindRequestFromRange_WithEndVerse() {
        final VerseFindRequest request = handler.generateVerseFindRequestFromRange(RANGE_1);

        assertEquals(VerseReference.Bookname.NUMBERS, request.getStartVerseReference().getBookname());
        assertEquals(25, request.getStartVerseReference().getChapter());
        assertEquals(1, request.getStartVerseReference().getVerse());

        assertTrue(request.getEndVerseReference().isPresent());
        assertEquals(VerseReference.Bookname.NUMBERS, request.getEndVerseReference().get().getBookname());
        assertEquals(25, request.getEndVerseReference().get().getChapter());
        assertEquals(18, request.getEndVerseReference().get().getVerse());
    }

    @Test
    public void generateVerseFindRequestFromRange_WithoutEndVerse() {
        final VerseFindRequest request = handler.generateVerseFindRequestFromRange(RANGE_2);

        assertEquals(VerseReference.Bookname.NUMBERS, request.getStartVerseReference().getBookname());
        assertEquals(25, request.getStartVerseReference().getChapter());
        assertEquals(1, request.getStartVerseReference().getVerse());

        assertFalse(request.getEndVerseReference().isPresent());
    }
}
