# WordsCounterWebAPI
A web-API that gets a text input (text/file/url), counts the appearances of each word. Given a word- it returns the number of appearances.

POST possible requests:
1. "/input/text"- gets a free text in the requestbody and executes analysis on it.
2. "/input/filepath"- gets a full file path in the requestbody and  executes analysis on its content.
3. "/input/url"-  gets a URL path in the requestbody and  executes analysis on its content.

GET possible request:
"/output/appearances/{word}"- gets a word as a path parameter and returns the number of appearances of the specific word in all the previous inputs.

Note:
1. Recognizing only English words
2. Spliting the words by any character that is not a letter
3. Lower/uppercase have no meaning
4. The given text charset is expected to be UTF-8
5. As a file path option- getting just a full path and not a directory
6. The URL represents an http or https source.

You can execute it using: mvnw spring-boot:run
Redis should be running in your environment.
In order to run it with a different redis port, other than the default 6379, you can execute:
mvnw spring-boot:run -Dspring-boot.run.arguments=--redis.port=<port>
(and if needed there is an option to change the hostname, etc. in the same way).

The server is running on port 8080.
