package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.impl.ProductDao;
import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;

public class ProductServiceImpl implements IProductService {

	private final IProductDao productDao = new ProductDao();

	@Override
	public void insert(Product product) {
		productDao.insert(product);
	}

	@Override
	public void update(Product product) {
		productDao.update(product);
	}

	@Override
	public void delete(int productId) {
		try {
			productDao.delete(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product findById(int productId) {
		return productDao.findById(productId);
	}

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	@Override
	public List<Product> findNewest(int limit) {
		return productDao.findNewest(limit);
	}

	@Override
	public List<Product> findAll(int page, int pagesize) {
		return productDao.findAll(page, pagesize);
	}

	@Override
	public int count() {
		return productDao.count();
	}
}
