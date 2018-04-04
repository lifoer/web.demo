package lifo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;


public class FeignRequest {
	
	public static void main(String[] args) {
		String url = "http://lifo.com/index";
		for(int i = 0; i < 10000; i++) {
			doPost(url,randomListToMap());
		}
	}
	
	public static Map<Object, Object> randomListToMap() {
		Map<Object,Object> map = new HashMap<Object, Object>();
		String[] str = {"java","������","c����","hadoop","���ݽṹ","�㷨","�˹�����","����ѧϰ",
				"����","python","���ѧϰ","ps","word","excel","ppt","���簲ȫ","������","android","ios"};
		Random random = new Random();
		int index = random.nextInt(str.length);
		String value = str[index];
		map.put("word", value);
		return map;
	}
	
	private final static Logger logger = Logger.getLogger(FeignRequest.class);
	
	public static void doPost(String url, Map<Object,Object> params) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator<Object> iterator = params.keySet().iterator();
			List<String> tmpStr = new ArrayList<String>(); 
			while(iterator.hasNext()) {
				String name = String.valueOf(iterator.next());
				String value = String.valueOf(params.get(name));
				tmpStr.add(value);
				nvps.add(new BasicNameValuePair(name, value));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			int code = httpResponse.getStatusLine().getStatusCode();
			if(code == 200) {
				for (String word : tmpStr) {
					logger.info("Success:" + new Date() + ":" + word);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
