package sk.stuba.fei.uim.oop.assignment3.list.web.bodies;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookResponse;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter

public class ListResponse {
    private Long id;
    private List<BookResponse> lendingList;
    private boolean lended;

    public ListResponse(LendingList lendingList) {
        this.id = lendingList.getId();
        this.lendingList = lendingList.getBook().stream().map(BookResponse::new).collect(Collectors.toList());
        this.lended = lendingList.isLended();
    }
}
