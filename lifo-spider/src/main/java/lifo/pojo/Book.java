package lifo.pojo;

public class Book {
	private String no;
	private String name;
	private Integer comment;
	private Double price;
	private String img;
	private String des;
	
	public Book() {}
	public Book(String des) {
		this.des = des;
	}
	
	public Book(String no, String name, Integer comment, Double price, String img, String des) {
		super();
		this.no = no;
		this.name = name;
		this.comment = comment;
		this.price = price;
		this.img = img;
		this.des = des;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getComment() {
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}

}
