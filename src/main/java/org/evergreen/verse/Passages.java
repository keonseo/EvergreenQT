package org.evergreen.verse;

import org.apache.commons.lang3.Validate;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Passages {

    private List<AbstractMap.SimpleEntry<VerseReference, String>> entries;

    private Passages(final List<AbstractMap.SimpleEntry<VerseReference, String>> entries) {
        this.entries = entries;
    }

    public String getTexts() {
        return entries.stream()
            .map(e -> e.getValue())
            .collect(Collectors.joining(" "));
    }

    public static class PassagesBuilder {

        private VerseReference startVerseReference;
        private Optional<VerseReference> endVerseReference = Optional.empty();
        private List<String> rawTexts;

        public PassagesBuilder() { }

        public PassagesBuilder withStartVerseReference(final VerseReference startVerseReference) {
            Validate.notNull(startVerseReference);
            this.startVerseReference = startVerseReference;
            return this;
        }

        public PassagesBuilder withEndVerseReference(final Optional<VerseReference> endVerseReference) {
            Validate.notNull(endVerseReference);
            this.endVerseReference = endVerseReference;
            return this;
        }

        public PassagesBuilder withTexts(final List<String> rawTexts) {
            Validate.notNull(rawTexts);
            Validate.notEmpty(rawTexts);
            this.rawTexts = rawTexts;
            return this;
        }

        public Passages build() {
            final List<AbstractMap.SimpleEntry<VerseReference, String>> entries = new LinkedList<>();

            if (endVerseReference.isPresent()) {
                Validate.isTrue(rawTexts.size() == endVerseReference.get().getVerse() - startVerseReference.getVerse());
                Validate.isTrue(startVerseReference.getBookname() == endVerseReference.get().getBookname(), "Search across multiple books is not supported");
                Validate.isTrue(startVerseReference.getChapter() == endVerseReference.get().getChapter(), "Search across multiple chapters is not supported");
                Validate.isTrue(startVerseReference.getVerse() <= endVerseReference.get().getVerse(), "End verse must be greater than start verse");

                for (int i = 0; i < rawTexts.size(); i++) {
                    final VerseReference verseReference = new VerseReference.VerseReferenceBuilder()
                            .withBookname(startVerseReference.getBookname())
                            .withChapter(startVerseReference.getChapter())
                            .withVerse(startVerseReference.getVerse() + i)
                            .build();

                    entries.add(new AbstractMap.SimpleEntry<>(verseReference, rawTexts.get(i)));
                }
            } else {
                Validate.isTrue(rawTexts.size() == 1, "There must be a single entry of text if end verse reference is not provided");
                entries.add(new AbstractMap.SimpleEntry<>(startVerseReference, rawTexts.get(0)));
            }

            return new Passages(entries);
        }
    }

}
