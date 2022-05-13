package sk.stuba.fei.uim.oop.assignment3.book.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    private Author author;

    @ManyToOne
    LendingList lendingList;

    private Long pages;
    private int amount;
    private int lendCount;

    public Book(BookRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.pages = request.getPages();
        this.amount = request.getAmount();
        this.lendCount = request.getLendCount();
    }
}
