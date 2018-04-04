package lifo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lifo.mapper.BookMapper;
import lifo.pojo.Book;

@Service
public class BookServiceImpl implements BookService{
	@Autowired
	private BookMapper bookMapper;

	@Override
	public List<Book> findAllBook() {
		List<Book> bookList = bookMapper.findAllBook();
		return bookList;
	}

	@Override
	public List<Book> queryBook(String word) {
		//标签名要和实体类成员名一致，封装
		Book book = new Book();
		book.setName(word);
		book.setDes(word);
		List<Book> bookList = bookMapper.queryBook(book);
		return bookList;
	}

	@Override
	public List<Book> findSale() {
		List<Book> bookList = bookMapper.findSale();
		return bookList;
	}

	@Override
	public List<Book> querySale(String word) {
		Book book = new Book();
		book.setName(word);
		book.setDes(word);
		List<Book> bookList = bookMapper.querySale(book);
		return bookList;
	}


	

}
