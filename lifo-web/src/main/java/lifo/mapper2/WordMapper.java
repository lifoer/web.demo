package lifo.mapper2;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WordMapper {
	
	public List<Map<Integer, String>> queryWord(@Param("savedate")String checkDate);

}
