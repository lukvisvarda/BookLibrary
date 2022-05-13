package sk.stuba.fei.uim.oop.assignment3.list.data;

import lombok.Data;
import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@Entity
public class LendingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Book> book;

    private boolean lended;

    public LendingList() {
        this.book = new ArrayList<>();
        this.lended = false;
    }


}
