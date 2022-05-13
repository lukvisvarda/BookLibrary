package sk.stuba.fei.uim.oop.assignment3.list.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingListRepository;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookIdRequest;

import java.util.List;

@Service
public class LendingListService implements ILendingListService{

    @Autowired
    private LendingListRepository repository;

    @Autowired
    private IBookService bookService;

    @Override
    public LendingList create() {
        return this.repository.save(new LendingList());
    }

    @Override
    public List<LendingList> getAll() {
        return repository.findAll();
    }

    @Override
    public LendingList getById(long id) throws NotFoundException {
        LendingList lendingList = this.repository.findLendingListById(id);
        if(lendingList == null) {
            throw new NotFoundException();
        }
        return lendingList;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        LendingList list = this.getById(id);
        for(int i = 0; i < list.getBook().size(); i++) {
            list.getBook().get(i).setLendCount(list.getBook().get(i).getLendCount() - 1);
        }
        this.repository.delete(list);
    }

    @Override
    public LendingList addToList(long id, BookIdRequest body) throws NotFoundException, IllegalOperationException {
        this.getNotLended(id);
        LendingList list = this.isListWithBook(id, body.getId());
        Book book = this.bookService.getById(body.getId());
        list.getBook().add(book);
        return this.repository.save(list);
    }

    @Override
    public LendingList removeFromList(long id, BookIdRequest body) throws NotFoundException {
        Book book = this.bookService.getById(body.getId());
        LendingList list = this.getById(id);
        list.getBook().remove(book);
        return this.repository.save(list);
    }

    @Override
    public void lendList(long id) throws NotFoundException, IllegalOperationException {
        LendingList list = this.getNotLended(id);
        for(int i = 0; i < list.getBook().size(); i++) {
            if(list.getBook().get(i).getLendCount() == list.getBook().get(i).getAmount()) {
                throw new IllegalOperationException();
            }
            list.getBook().get(i).setLendCount(list.getBook().get(i).getLendCount() + 1);
        }
        list.setLended(true);
        this.repository.save(list);
    }

    private LendingList getNotLended(long id) throws NotFoundException, IllegalOperationException {
        LendingList list = this.getById(id);
        if (list.isLended()) {
            throw new IllegalOperationException();
        }
        return list;
    }

    private LendingList isListWithBook(long id, long bookId) throws NotFoundException, IllegalOperationException {
        LendingList list = this.getById(id);
        for(int i = 0; i < list.getBook().size(); i++) {
            if(list.getBook().get(i).getId() == bookId) {
                throw new IllegalOperationException();
            }
        }

        return list;
    }


}
