package sk.stuba.fei.uim.oop.assignment3.list.logic;

import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookIdRequest;

import java.util.List;

public interface ILendingListService { // DOROBIT EXCEPTIONS
    List<LendingList> getAll();

    LendingList create();

    LendingList getById(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    LendingList addToList(long id, BookIdRequest body) throws NotFoundException, IllegalOperationException;

    LendingList removeFromList(long id, BookIdRequest body) throws NotFoundException;

    void lendList(long id) throws NotFoundException, IllegalOperationException;
}
