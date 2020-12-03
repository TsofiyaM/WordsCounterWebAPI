package redis.challenge.counter.iterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;

public class WordsIterator implements Iterator<String>, AutoCloseable {

    BufferedReader bufferedReader;
    int currentCharacter = -1;

    public void init() throws IOException {
        currentCharacter = bufferedReader.read();
    }

    @Override
    public boolean hasNext() {
        try {
            while ((currentCharacter >= 0) && (!isCurrentCharacterLetter())) {
                currentCharacter = bufferedReader.read();
            }
            return (currentCharacter >= 0);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String next() {
        try {
            StringBuilder nextWord = new StringBuilder();
            while (isCurrentCharacterLetter()) {
                nextWord.append(((char) currentCharacter));
                currentCharacter = bufferedReader.read();
            }
            return nextWord.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private boolean isCurrentCharacterLetter() {
        return ((currentCharacter >= 'a' && currentCharacter <= 'z') || (currentCharacter >= 'A' && currentCharacter <= 'Z'));
    }

    @Override
    public void close() throws Exception {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }
}
