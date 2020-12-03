package redis.challenge.counter;

import redis.challenge.counter.iterator.WordsIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class WordsCounterService {

    @Autowired
    RedisTemplate<String, Long> redisTemplate;

    public void count(WordsIterator wordsIterator) {
        Map<String, Long> map = new HashMap<>();
        while (wordsIterator.hasNext()) {
            String word = wordsIterator.next().toLowerCase();
            Long wordVal = map.get(word);

            if (wordVal != null) {
                map.put(word, wordVal + 1);
            } else {
                map.put(word, 1L);
            }

            if (map.size() > 100000) {
                persist(map);
                map = new HashMap<>();
            }
        }
        persist(map);
    }

    private void persist(Map<String, Long> map) {
        redisTemplate.opsForValue().getOperations().executePipelined((RedisCallback<Object>) connection -> {
            StringRedisSerializer serializer = new StringRedisSerializer();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                connection.incrBy(Objects.requireNonNull(serializer.serialize(entry.getKey())), entry.getValue());
            }
            return null;
        });
    }

    public Long getAppearances(String word) {
        Long appearances = redisTemplate.opsForValue().get(word.toLowerCase());
        return appearances == null ? 0 : appearances;
    }
}
