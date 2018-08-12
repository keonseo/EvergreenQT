package org.evergreen.verse;

import org.apache.commons.lang3.Validate;

import java.util.Optional;

public class VerseFindRequest {

    final private VerseReference startVerseReference;
    final private Optional<VerseReference> endVerseReference;

    private VerseFindRequest(final VerseReference startVerseReference, final Optional<VerseReference> endVerseReference) {
        this.startVerseReference = startVerseReference;
        this.endVerseReference = endVerseReference;
    }

    public VerseReference getStartVerseReference() {
        return startVerseReference;
    }

    public Optional<VerseReference> getEndVerseReference() {
        return endVerseReference;
    }

    public static class VerseFindRequestBuilder {
        private VerseReference startVerseReference;
        private Optional<VerseReference> endVerseReferenceOptional = Optional.empty();

        public VerseFindRequestBuilder() { }

        public VerseFindRequestBuilder withStartVerseReference(final VerseReference startVerseReference) {
            Validate.notNull(startVerseReference);
            this.startVerseReference = startVerseReference;
            return this;
        }

        public VerseFindRequestBuilder withEndVerseReference(final Optional<VerseReference> endVerseReferenceOptional) {
            Validate.notNull(endVerseReferenceOptional);
            this.endVerseReferenceOptional = endVerseReferenceOptional;
            return this;
        }

        public VerseFindRequest build() {
            if (endVerseReferenceOptional.isPresent()) {
                Validate.isTrue(startVerseReference.getBookname() == endVerseReferenceOptional.get().getBookname(), "Search across multiple books is not supported");
                Validate.isTrue(startVerseReference.getChapter() == endVerseReferenceOptional.get().getChapter(), "Search across multiple chapters is not supported");
                Validate.isTrue(startVerseReference.getVerse() <= endVerseReferenceOptional.get().getVerse(), "End verse must be greater than start verse");
            }

            return new VerseFindRequest(startVerseReference, endVerseReferenceOptional);
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s", startVerseReference, endVerseReference);
    }
}
