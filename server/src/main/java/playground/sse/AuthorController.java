package playground.sse;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.sse.Event;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Controller("/author")
public class AuthorController {
  private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @Post("/book")
  public void publishBook(@Body Book book) {
    authorService.publish(book);
  }

  @Get("/subscribe")
  Publisher<Event<Book>> events(@QueryValue Optional<String> author) {
    LOG.info("subscribing events from {}", author.orElse("all"));
    return Maybe.fromCallable(() -> author.orElse(null))
        .flatMapPublisher(authorService::subscribeFrom)
        .switchIfEmpty(authorService.subscribeAll())
        .map(Event::of)
        .subscribeOn(Schedulers.newThread());
  }
}
