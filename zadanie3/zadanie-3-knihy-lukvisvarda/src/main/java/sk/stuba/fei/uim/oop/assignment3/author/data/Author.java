package sk.stuba.fei.uim.oop.assignment3.author.data;

import lombok.*;
import javax.persistence.*;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @OneToMany(orphanRemoval = true)
    private List<Book> books;

    public Author(AuthorRequest request) {
        this.name = request.getName();
        this.surname = request.getSurname();
        this.books = new ArrayList<>();
    }
}
