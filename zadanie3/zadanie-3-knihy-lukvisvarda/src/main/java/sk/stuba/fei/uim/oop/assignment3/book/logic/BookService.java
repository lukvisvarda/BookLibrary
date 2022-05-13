package sk.stuba.fei.uim.oop.assignment3.book.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.logic.IAuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.BookRepository;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepository repository;

    @Autowired
    private IAuthorService authorService;

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }


    @Override
    public Book create(BookRequest request) throws NotFoundException {
        Book book = new Book(request);
        Author author = this.authorService.getById(request.getAuthor());
        book.setAuthor(author);
        author.getBooks().add(book);
        return this.repository.save(book);
    }

    @Override
    public Book getById(Long id) throws NotFoundException {
        Book b = this.repository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        return b;
    }

    @Override
    public Book update(long id, BookUpdateRequest request) throws NotFoundException {
        Book b = this.getById(id);
        if (request.getName() != null) {
            b.setName((request.getName()));
        }
        if (request.getDescription() != null) {
            b.setDescription(request.getDescription());
        }
        if (request.getAuthor() != 0) {
            Author a = authorService.getById(b.getAuthor().getId());
            b.setAuthor(authorService.getById(request.getAuthor()));
            Author aNew = authorService.getById(b.getAuthor().getId());
            for(int i = 0; i < a.getBooks().size(); i++) {
                if(a.getBooks().get(i).getId().equals(b.getId())) {
                    a.getBooks().remove(b);
                    aNew.getBooks().add(b);
                    break;
                }
            }
        }
        if (request.getPages() != 0) {
            b.setPages(request.getPages());
        }
        return this.repository.save(b);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        for (int i = 0; i < authorService.getAll().size(); i++) {
            for(int j = 0; j < authorService.getAll().get(i).getBooks().size(); j++) {
                if (authorService.getAll().get(i).getBooks().get(j).getId().equals(id)) {
                    authorService.getAll().get(i).getBooks().remove(getById(id));
                }
            }
        }
        this.repository.delete(this.getById(id));
    }

    @Override
    public int getAmount(long id) throws NotFoundException {
        return this.getById(id).getAmount();
    }

    @Override
    public int addAmount(long id, int increment) throws NotFoundException {
        Book b = this.getById(id);
        b.setAmount(b.getAmount() + increment);
        this.repository.save(b);
        return b.getAmount();
    }

    @Override
    public int getLendCount(long id) throws NotFoundException {
        return this.getById(id).getLendCount();
    }

    @Override
    public int removeAmount(long id, int decrement) throws NotFoundException, IllegalOperationException {
        Book b = this.getById(id);
        if (b.getAmount() < decrement) {
            throw new IllegalOperationException();
        }
        b.setAmount(b.getAmount() - decrement);
        this.repository.save(b);
        return b.getAmount();
    }
}
