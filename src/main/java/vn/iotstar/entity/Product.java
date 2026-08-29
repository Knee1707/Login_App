package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "products")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p ORDER BY p.createdDate DESC, p.productId DESC")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductId")
	private int productId;

	@Column(name = "ProductName", columnDefinition = "nvarchar(200)", nullable = false)
	@NotEmpty(message = "Không được phép rỗng")
	private String productName;

	@Column(name = "Image", columnDefinition = "nvarchar(500)")
	private String image;

	@Column(name = "Price")
	private double price;

	@Column(name = "Quantity")
	private int quantity;

	@Column(name = "Description", columnDefinition = "nvarchar(2000)")
	private String description;

	// 0 = khóa, 1 = hoạt động
	@Column(name = "Status")
	private int status;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;

	public Product() {
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
