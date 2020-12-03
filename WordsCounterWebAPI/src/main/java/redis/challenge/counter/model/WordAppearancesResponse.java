package redis.challenge.counter.model;

public class WordAppearancesResponse {

    private String word;
    private Long appearances;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getAppearances() {
        return appearances;
    }

    public void setAppearances(Long appearances) {
        this.appearances = appearances;
    }
}
