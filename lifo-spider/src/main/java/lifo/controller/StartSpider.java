package lifo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;

import lifo.service.BookService;

@Controller
public class StartSpider {
		
	private static BookService homeService;
	
	public BookService getHomeService() {
		return homeService;
	}

	@Autowired
	public void setHomeService(BookService homeService) {
		StartSpider.homeService = homeService;
	}

	public static void main(String[] args) {
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring/applicationContext*.xml");
		start("linux",1);
	}
	/**
	 * 
	 * @param keyword 搜索关键词
	 * @param RecordNum 搜素总页数，每页正常60条
	 */
	private static void start(String keyword,int pageNum) {
		//"http://search.dangdang.com/?key=java&act=input&show=big&page_index=2";
		if(pageNum < 1) pageNum = 1;
		String url = "";
		for(int i = 1; i <= pageNum; i++) {
			 url = "http://search.dangdang.com/?key="+  keyword + "&act=input&show=big&page_index=" + i;
			 homeService.insertBook(url);
		}
		System.out.println("end");
	}
	
}
