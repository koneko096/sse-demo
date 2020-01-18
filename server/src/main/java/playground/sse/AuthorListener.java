package playground.sse;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class AuthorListener {

  private final AuthorService authorService;

  public AuthorListener(AuthorService authorService) {
    this.authorService = authorService;
  }

  @Topic("book-release")
  public void listen(@KafkaKey String author, String title) {
    Book book = new Book();
    book.setAuthor(author);
    book.setTitle(title);
    authorService.publish(book);
  }
}
