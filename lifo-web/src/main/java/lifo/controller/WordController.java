package lifo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lifo.service.WordService;

@Controller
public class WordController {
	@Autowired
	private WordService wordService;
	
	@RequestMapping("/view")
	public String view() {
		return "/view";
	}
	
	@RequestMapping("/word")
	@ResponseBody
	public String word(String checkDate){
		String jsonString = wordService.queryWord(checkDate);
		return jsonString;
	}
}
