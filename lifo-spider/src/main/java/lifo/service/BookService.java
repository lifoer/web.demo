package lifo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lifo.mapper.BookMapper;
import lifo.pojo.Book;

@Service
public class BookService {

	@Autowired
	private BookMapper homeMapper;
	/**
	 * jsoupģ�����󣬻�ȡ���ݴ浽mysql
	 * @param url ��ȡ����
	 */
	public void insertBook(String url) {

		try {
			Connection connect = Jsoup.connect(url);
			connect.header("Accept-Encoding", "gzip, deflate, sdch");
			connect.header("Accept-Language", "zh-CN,zh;q=0.8");
			connect.header("Connection", "keep-alive");
			connect.header("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36 LBBROWSER");
			Document doc = connect.get();
			for (int i = 0; i < 60; i++) {
				try {
					// ����������ҳ
					Element element1 = doc.select("#search_nature_rg ul li .name a").get(i);
					String name = element1.attr("title").substring(1);
					// ��ļ۸�
					Element element2 = doc.select("#search_nature_rg ul li .price .price_r").get(i);
					Double price = Double.parseDouble(element2.text().substring(1));
					// ����
					Element element3 = doc.select("#search_nature_rg ul li .search_hot_word").get(i);
					String des = element3.text();
					// ���ͼƬ����
					Element element4 = doc.select("#search_nature_rg ul li .pic img").get(i);
					String img = "";
					if (i <= 3) {
						img = element4.attr("src");
					} else {
						img = element4.attr("data-original");
					}
					// ����ͼƬ������
					String dir = "d://lifo";
					//uuid����ͼƬ����
					String imgName = UUID.randomUUID().toString() + img.substring(img.lastIndexOf("."));
					imgDown(img, dir, imgName);
					// ����ͼƬ��ַΪ����ͼƬ��������ַ
					img = "http://image.lifo.com/" + imgName;
					// ��������
					Element element5 = doc.select("#search_nature_rg ul li .star a").get(i);
					Integer comment = Integer.parseInt(element5.text().split("��")[0]);
					// ����noΪuuid
					String no = UUID.randomUUID().toString();
					Book book = new Book(no, name, comment, price, img, des);
					homeMapper.insertBook(book);
				} catch (Exception e) {
					System.out.println((i + "\n" + e.getMessage()));
					continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����ͼƬ����
	 * @param img ͼƬ��ַ
	 * @param dir ͼƬ���·��
	 * @param imgName ͼƬ����
	 */
	private void imgDown(String img, String dir, String imgName) {
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			// ��������,���������
			URL url = new URL(img);
			URLConnection connect = url.openConnection();
			connect.setConnectTimeout(1000);
			connect.setReadTimeout(5000);
			connect.connect();
			inputStream = connect.getInputStream();
			// ���ļ��в����ڴ����ļ���
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdir();
			}
			// ���������
			String filename = dir + "/" + imgName;
			fileOutputStream = new FileOutputStream(new File(filename));
			byte[] buffer = new byte[102400];
			int length = 0;
			while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
				fileOutputStream.write(buffer, 0, length);
			}
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					fileOutputStream = null;
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					inputStream = null;
				}
			}
		}

	}
}
