package redis.challenge.counter.iterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileWordsIterator extends WordsIterator {

    private final File filePath;

    public FileWordsIterator(File filePathInput) {
        filePath = filePathInput;
    }

    @Override
    public void init() throws IOException {
        bufferedReader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
        super.init();
    }
}
