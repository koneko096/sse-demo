package playground.sse;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.sse.Event;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

@Controller("/author")
public class AuthorController {

  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @Post("/book")
  public void publishBook(@Body Book book) {
    authorService.publish(book);
  }

  @Get("/subscribe")
  Publisher<Event<Book>> events() {
    return authorService.subscribeAll()
        .map(Event::of)
        .subscribeOn(Schedulers.newThread());
  }

  @Get("/subscribe/{name}")
  Publisher<Event<Book>> eventsFrom(String name) {
    return authorService.subscribeFrom(name)
        .map(Event::of)
        .subscribeOn(Schedulers.newThread());
  }
}
