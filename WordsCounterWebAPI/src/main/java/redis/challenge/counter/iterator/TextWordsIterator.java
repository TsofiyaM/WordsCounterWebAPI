package redis.challenge.counter.iterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TextWordsIterator extends WordsIterator {

    private final InputStream stream;

    public TextWordsIterator(InputStream inputStream) {
        stream = inputStream;
    }

    @Override
    public void init() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        super.init();
    }

}
