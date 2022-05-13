package sk.stuba.fei.uim.oop.assignment3.book.web.bodies;

import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

@Getter
public class BookResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final Long author;
    private final Long pages;
    private final int amount;
    private final int lendCount;

    public BookResponse(Book b) {
        this.id = b.getId();
        this.name = b.getName();
        this.description = b.getDescription();
        this.author = b.getAuthor() != null ? b.getAuthor().getId() : null;
        this.pages = b.getPages();
        this.amount = b.getAmount();
        this.lendCount = b.getLendCount();
    }
}
