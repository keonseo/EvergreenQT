package org.evergreen.verse;

import org.jsoup.helper.Validate;

public class VerseReference {

    final private Bookname bookname;
    final private Integer chapter;
    final private Integer verse;

    private VerseReference(final Bookname bookname, final Integer chapter, final Integer verse) {
        Validate.notNull(chapter);
        Validate.notNull(verse);
        this.bookname = bookname;
        this.chapter = chapter;
        this.verse = verse;
    }

    public Bookname getBookname() {
        return bookname;
    }

    public int getChapter() {
        return chapter;
    }

    public int getVerse() {
        return verse;
    }

    public enum Bookname {
        // Old Testament
        GENESIS("Genesis", "창세기"),
        EXODUS("Exodus", "출애굽기"),
        LEVITICUS("Leviticus", "레위기"),
        NUMBERS("Numbers", "민수기"),
        DEUTERONOMY("Deuteronomy", "신명기"),
        JOSHUA("Joshua", "여호수아"),
        JUDGES("Judges", "사사기"),
        RUTH("Ruth", "룻기"),
        SAMUEL_FIRST("1 Samuel", "사무엘상"),
        SAMUEL_SECOND("2 Samuel", "사무엘하"),
        KINGS_FIRST("1 Kings", "열왕기상"),
        KINGS_SECOND("2 Kings", "열왕기하"),
        CHRONICLES_FIRST("1 Chronicles", "역대기상"),
        CHRONICLES_SECOND("2 Chronicles", "역대기하"),
        EZRA("Ezra", "에스라"),
        NEHEMIA("Nehemiah", "느헤미야"),
        ESTHER("Esther", "에스더"),
        JOB("Job", "욥기"),
        PSALMS("Psalms", "시편"),
        PROVERBS("Proverbs", "잠언"),
        ECCLESIATES("Ecclesiastes", "전도서"),
        SONG_OF_SOLOMON("Song of Solomon", "아가"),
        ISAIAH("Isaiah", "이사야"),
        JEREMIAH("Jeremiah", "예레미아"),
        LAMENTATIONS("Lamentations", "예레미아애가"),
        EZEKIEL("Ezekiel", "에스겔"),
        DANIEL("Daniel", "다니엘"),
        HOSEA("Hosea", "호세아"),
        JOEL("Joel", "요엘"),
        AMOS("Amos", "아모스"),
        OBADIAH("Obadiah", "오바댜"),
        JONAH("Jonah", "요나"),
        MICAH("Micah", "미가"),
        NAHUM("Nahum", "나훔"),
        HABAKKUK("Habakkuk", "하박국"),
        ZEPHANIAH("Zephaniah", "스바냐"),
        HAGGAI("Haggai", "학개"),
        ZECHARIAH("Zechariah", "스가랴"),
        MALACHI("Malachi", "말라기"),

        //New Testament
        MATTHEW("Matthew", "마태복음"),
        MARK("Mark", "마가복음"),
        LUKE("Luke", "누가복음"),
        JOHN("John", "요한복음"),
        ACTS("Acts", "사도행전"),
        ROMANS("Romans", "로마서"),
        CORIANTHIANS_FIRST("1 Corinthians", "고린도전서"),
        CORIANTHIANS_SECOND("2 Corinthians", "고린도후서"),
        GALATIANS("Galatians", "갈라디아서"),
        EPHESIANS("Ephesians", "에베소서"),
        PHILIPPIANS("Philippians", "빌립보서"),
        COLOSSIANS("Colossians", "골로새서"),
        THESSALONIANS_FIRST("1 Thessalonians", "데살로니가전서"),
        THESSALONIANS_SECOND("2 Thessalonians", "데살로니가후서"),
        TIMOTHY_FIRST("1 Timothy", "디모데전서"),
        TIMOTHY_SECOND("2 Timothy", "디모데후서"),
        TITUS("Titus", "디도서"),
        PHILEMON("Philemon", "빌레몬서"),
        HEBREWS("Hebrews", "히브리서"),
        JAMES("James", "야고보서"),
        PETER_FIRST("1 Peter", "베드로전서"),
        PETER_SECOND("2 Peter", "베드로후서"),
        JOHN_FIRST("1 John", "요한1서"),
        JOHN_SECOND("2 John", "요한2서"),
        JOHN_THIRD("3 John", "요한3서"),
        JUDE("Jude", "유다서"),
        REVELATION("Revelation", "요한계시록");

        private String englishBookName;
        private String koreanBookName;

        Bookname(final String englishBookName, final String koreanBookName) {
            this.englishBookName = englishBookName;
            this.koreanBookName = koreanBookName;
        }

        public String getEnglishBookName() {
            return englishBookName;
        }

        public String getKoreanBookName() {
            return koreanBookName;
        }
    }

    public static class VerseReferenceBuilder {
        private Bookname bookname;
        private Integer chapter;
        private Integer verse;

        public VerseReferenceBuilder() { }

        public VerseReferenceBuilder withBookname(final Bookname bookname) {
            this.bookname = bookname;
            return this;
        }

        public VerseReferenceBuilder withChapter(final Integer chapter) {
            this.chapter = chapter;
            return this;
        }

        public VerseReferenceBuilder withVerse(final Integer verse) {
            this.verse = verse;
            return this;
        }

        public VerseReference build() {
            return new VerseReference(bookname, chapter, verse);
        }
    }
}
