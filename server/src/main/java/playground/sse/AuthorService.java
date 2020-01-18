package playground.sse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import javax.inject.Singleton;

@Singleton
public class AuthorService {

  private Subject<Book> subject = PublishSubject.create();

  public void publish(Book book) {
    subject.onNext(book);
  }

  public Flowable<Book> subscribeAll() {
    return subject.hide().toFlowable(BackpressureStrategy.BUFFER);
  }

  public Flowable<Book> subscribeFrom(String name) {
    return subject.hide().toFlowable(BackpressureStrategy.BUFFER)
        .filter(book -> name.equals(book.getTitle()));
  }
}
