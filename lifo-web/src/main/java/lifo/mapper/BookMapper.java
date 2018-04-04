package lifo.mapper;

import java.util.List;

import lifo.pojo.Book;

public interface BookMapper {

	public List<Book> findAllBook();

	public List<Book> queryBook(Book book);

	public List<Book> findSale();

	public List<Book> querySale(Book book);
	
}
