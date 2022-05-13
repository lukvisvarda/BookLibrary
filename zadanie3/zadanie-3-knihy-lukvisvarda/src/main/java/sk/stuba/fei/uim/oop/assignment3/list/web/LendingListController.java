package sk.stuba.fei.uim.oop.assignment3.list.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.logic.ILendingListService;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.BookIdRequest;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.ListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class LendingListController {

    @Autowired
    private ILendingListService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ListResponse> getAllLendingList() {
        return this.service.getAll().stream().map(ListResponse::new).collect(Collectors.toList());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse> createList() {
        return new ResponseEntity<>(new ListResponse(this.service.create()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ListResponse getById(@PathVariable("id") Long listId) throws NotFoundException {
        return new ListResponse(this.service.getById(listId));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long listId) throws NotFoundException {
        this.service.delete(listId);
    }

    @PostMapping(value = "/{id}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ListResponse addToList(@PathVariable("id") Long listId, @RequestBody BookIdRequest body) throws NotFoundException, IllegalOperationException {
        return new ListResponse(this.service.addToList(listId, body));
    }

    @DeleteMapping(value = "/{id}/remove")
    public void removeFromList(@PathVariable("id") Long listId, @RequestBody BookIdRequest body) throws NotFoundException, IllegalOperationException {
        this.service.removeFromList(listId, body);
    }

    @GetMapping(value = "/{id}/lend")
    public void lendList(@PathVariable("id") Long listId) throws NotFoundException, IllegalOperationException {
        this.service.lendList(listId);
    }


}
