package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.entity.Product;

public interface IProductDao {

	void insert(Product product);

	void update(Product product);

	void delete(int productId) throws Exception;

	Product findById(int productId);

	List<Product> findAll();

	// Sản phẩm mới nhất (theo ngày tạo giảm dần)
	List<Product> findNewest(int limit);

	// Phân trang: page bắt đầu từ 0
	List<Product> findAll(int page, int pagesize);

	int count();
}
