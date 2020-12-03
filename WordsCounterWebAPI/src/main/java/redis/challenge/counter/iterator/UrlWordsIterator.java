package redis.challenge.counter.iterator;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class UrlWordsIterator extends WordsIterator {

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    private final String url;

    public UrlWordsIterator(String urlInput) {
        url = urlInput;
    }

    @Override
    public void init() throws IOException {
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = HTTP_CLIENT.execute(httpget);
        bufferedReader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
        super.init();
    }
}
