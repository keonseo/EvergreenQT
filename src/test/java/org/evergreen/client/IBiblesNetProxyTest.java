package org.evergreen.client;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.evergreen.verse.VerseFindRequest;
import org.evergreen.verse.VerseReference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class IBiblesNetProxyTest {

    @Mock
    LambdaLogger lambdaLoggerMock;

    private IBiblesNetProxy iBiblesNetProxy;

    public static final String HTML = "<!doctype html>\n" +
            "<html>\n" +
            " <head> \n" +
            "  <meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"> \n" +
            "  <title>Bible Quote</title> \n" +
            " </head> \n" +
            " <body bgcolor=\"#e0e0e0\"> \n" +
            "  <small>5:3</small> 심령이 가난한 자는 복이 있나니 천국이 저희 것임이요\n" +
            "  <br> \n" +
            "  <small>5:4</small> 애통하는 자는 복이 있나니 저희가 위로를 받을 것임이요\n" +
            "  <br> \n" +
            "  <small>5:5</small> 온유한 자는 복이 있나니 저희가 땅을 기업으로 받을 것임이요\n" +
            "  <br> \n" +
            "  <br> \n" +
            "  <small>5:6</small> 의에 주리고 목마른 자는 복이 있나니 저희가 배부를 것임이요\n" +
            "  <br> \n" +
            "  <small>5:7</small> 긍휼히 여기는 자는 복이 있나니 저희가 긍휼히 여김을 받을 것임이요\n" +
            "  <br> \n" +
            "  <small>5:8</small> 마음이 청결한 자는 복이 있나니 저희가 하나님을 볼 것임이요\n" +
            "  <br> \n" +
            "  <small>5:9</small> 화평케 하는 자는 복이 있나니 저희가 하나님의 아들이라 일컬음을 받을 것임이요\n" +
            "  <br> \n" +
            "  <small>5:10</small> 의를 위하여 핍박을 받은 자는 복이 있나니 천국이 저희 것임이라\n" +
            "  <br> \n" +
            "  <br> \n" +
            "  <small>5:11</small> 나를 인하여 너희를 욕하고 핍박하고 거짓으로 너희를 거스려 모든 악한 말을 할 때에는 너희에게 복이 있나니\n" +
            "  <br> \n" +
            "  <small>5:12</small> 기뻐하고 즐거워하라 ! 하늘에서 너희의 상이 큼이라 너희 전에 있던 선지자들을 이같이 핍박하였느니라\n" +
            "  <br>   \n" +
            " </body>\n" +
            "</html>";

    @Before
    public void setup() {
        iBiblesNetProxy = new IBiblesNetProxy(lambdaLoggerMock);
    }

    @Test
    public void constructURIWithNoEndVerse() {
        final VerseReference reference = new VerseReference.VerseReferenceBuilder()
                .withBookname(VerseReference.Bookname.ACTS)
                .withChapter(4)
                .withVerse(10)
                .build();

        final VerseFindRequest request = new VerseFindRequest.VerseFindRequestBuilder()
                .withStartVerseReference(reference)
                .build();

        final String uri = iBiblesNetProxy.constructURI(request);
        assertEquals("http://ibibles.net/quote.php?kor-acts/4:10", uri);
    }

    @Test
    public void constructURIWithEndVerse() {
        final VerseReference startVerseReference = new VerseReference.VerseReferenceBuilder()
                .withBookname(VerseReference.Bookname.MATTHEW)
                .withChapter(4)
                .withVerse(15)
                .build();

        final VerseReference endVerseReference = new VerseReference.VerseReferenceBuilder()
                .withBookname(VerseReference.Bookname.MATTHEW)
                .withChapter(4)
                .withVerse(20)
                .build();

        final VerseFindRequest request = new VerseFindRequest.VerseFindRequestBuilder()
                .withStartVerseReference(startVerseReference)
                .withEndVerseReference(Optional.of(endVerseReference))
                .build();

        final String uri = iBiblesNetProxy.constructURI(request);
        assertEquals("http://ibibles.net/quote.php?kor-mat/4:15-20", uri);
    }

    @Test
    public void parseDocumentTest() throws Exception {
        final Document document = Jsoup.parse(HTML);

        //[심령이 가난한 자는 복이 있나니 천국이 저희 것임이요,
        // 애통하는 자는 복이 있나니 저희가 위로를 받을 것임이요,
        // 온유한 자는 복이 있나니 저희가 땅을 기업으로 받을 것임이요,
        // 의에 주리고 목마른 자는 복이 있나니 저희가 배부를 것임이요,
        // 긍휼히 여기는 자는 복이 있나니 저희가 긍휼히 여김을 받을 것임이요,
        // 마음이 청결한 자는 복이 있나니 저희가 하나님을 볼 것임이요,
        // 화평케 하는 자는 복이 있나니 저희가 하나님의 아들이라 일컬음을 받을 것임이요,
        // 의를 위하여 핍박을 받은 자는 복이 있나니 천국이 저희 것임이라,
        // 나를 인하여 너희를 욕하고 핍박하고 거짓으로 너희를 거스려 모든 악한 말을 할 때에는 너희에게 복이 있나니,
        // 기뻐하고 즐거워하라 ! 하늘에서 너희의 상이 큼이라 너희 전에 있던 선지자들을 이같이 핍박하였느니라]
        final List<String> texts = iBiblesNetProxy.parseDocument(document);

        assertEquals(10, texts.size());
        assertEquals("심령이 가난한 자는 복이 있나니 천국이 저희 것임이요", texts.get(0));
        assertEquals("마음이 청결한 자는 복이 있나니 저희가 하나님을 볼 것임이요", texts.get(5));
    }
}
