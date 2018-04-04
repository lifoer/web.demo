package lifo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lifo.mapper2.WordMapper;

@Service
public class WordServiceImpl implements WordService {

	@Autowired
	private WordMapper wordMapper;

	@Override
	public String queryWord(String checkDate) {
		List<Map<Integer, String>> list = wordMapper.queryWord(checkDate);
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}
}