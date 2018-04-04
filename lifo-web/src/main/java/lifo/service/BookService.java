package lifo.service;

import java.util.List;

import lifo.pojo.Book;

public interface BookService {

	public List<Book> findAllBook();

	public List<Book> queryBook(String word);

	public List<Book> findSale();

	public List<Book> querySale(String word);

}
