package lifo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lifo.log.CustomLog;
import lifo.pojo.Book;
import lifo.service.BookService;


@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	private Logger loggerFile;
	private final static Logger loggerFlume = CustomLog.getInstance().optionFlumeCustomLog("flume", "192.168.43.130", 33333,"");
	
	@RequestMapping("/index")
	public String queryBook(@RequestParam(required = false, defaultValue = "1", value = "pageNum")Integer pageNum, @RequestParam(required=false, defaultValue="false",value="sale")String sale, String word , HttpServletRequest request, Model model) {
		PageInfo<Book> pageInfo = null;
		//判断是否按关键字查询
		if(word == null || word.equals("")) {
			//判断是否销量优先查询
			if(sale.equals("false")) {
				PageHelper.startPage(pageNum, 5);
				List<Book> bookList = bookService.findAllBook();
				pageInfo = new PageInfo<Book>(bookList);
			} else {
				PageHelper.startPage(pageNum, 5);
				List<Book> bookList = bookService.findSale();
				pageInfo = new PageInfo<Book>(bookList);
			}	
		} else {
			//判断是否销量优先查询
			if(sale.equals("false")) {
				PageHelper.startPage(pageNum, 5);
				List<Book> bookList = bookService.queryBook(word);
				pageInfo = new PageInfo<Book>(bookList);
				if(bookList.size() > 0) {
					model.addAttribute("word", word);
				}
			} else {
				PageHelper.startPage(pageNum, 5);
				List<Book> bookList = bookService.querySale(word);
				pageInfo = new PageInfo<Book>(bookList);
				if(bookList.size() > 0) {
					model.addAttribute("word", word);
				}
			}
			
			//用户Ip和搜索关键词存入日志文件
			String filePath = "logs/word/";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fileName = dateFormat.format(new Date());
			loggerFile = CustomLog.getInstance().optionFileCustomLog(filePath, fileName, "", true);
			CustomLog.getInstance().createCustumLog(loggerFile, request.getHeader("X-Real-Ip")+ "\t" + word + "\t" + fileName);
			//用户Ip和搜索关键词写出到Flume
			CustomLog.getInstance().createCustumLog(loggerFlume, request.getHeader("X-Real-Ip")+ "\t" + word + "\t" + fileName);
			
		}
		//销量状态值传回页面
		if(sale.equals("false")) {
			model.addAttribute("sale", "false");
		} else {
			model.addAttribute("sale", "true");
		}		
		model.addAttribute("pageInfo", pageInfo);
		return "/index";
	}
	
}
