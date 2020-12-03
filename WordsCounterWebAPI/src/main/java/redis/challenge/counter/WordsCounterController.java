package redis.challenge.counter;

import redis.challenge.counter.iterator.FileWordsIterator;
import redis.challenge.counter.iterator.TextWordsIterator;
import redis.challenge.counter.iterator.UrlWordsIterator;
import redis.challenge.counter.iterator.WordsIterator;
import redis.challenge.counter.model.WordAppearancesResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
public class WordsCounterController {

    private final Logger logger = LoggerFactory.getLogger(WordsCounterController.class);

    @Autowired
    WordsCounterService wordsCounterService;

    @PostMapping("/input/text")
    ResponseEntity<?> stringWordsCounter(HttpServletRequest request) {
        try {
            return count(new TextWordsIterator(request.getInputStream()));
        } catch (Exception e) {
            logger.error("Failed to read text", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/input/filepath")
    ResponseEntity<?> filePathWordsCounter(@RequestBody String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ResponseEntity<>("File not exist: " + filePath, HttpStatus.BAD_REQUEST);
            }
            return count(new FileWordsIterator(file));
        } catch (Exception e) {
            logger.error("Failed to read file", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/input/url")
    ResponseEntity<?> urlWordsCounter(@RequestBody String url) {
        try {
            UrlValidator urlValidator = new UrlValidator();
            if (!urlValidator.isValid(url)) {
                return new ResponseEntity<>("Invalid URL: " + url, HttpStatus.BAD_REQUEST);
            }
            return count(new UrlWordsIterator(url));
        } catch (Exception e) {
            logger.error("Failed to scan URL content", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/output/appearances/{word}")
    WordAppearancesResponse wordAppearances(@PathVariable String word) {
        WordAppearancesResponse appearancesResponse = new WordAppearancesResponse();
        appearancesResponse.setWord(word);
        appearancesResponse.setAppearances(wordsCounterService.getAppearances(word));
        return appearancesResponse;
    }

    private ResponseEntity<?> count(WordsIterator iterator) throws IOException {
        iterator.init();
        wordsCounterService.count(iterator);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
