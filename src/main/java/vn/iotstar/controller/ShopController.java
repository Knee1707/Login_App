package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.ProductServiceImpl;

/**
 * Trang khách (public):
 *  - /home           : 10 sản phẩm mới nhất
 *  - /product        : tất cả sản phẩm, phân trang 6 sp/trang
 *  - /product/detail : chi tiết 1 sản phẩm
 */
@WebServlet(urlPatterns = { "/home", "/product", "/product/detail" })
public class ShopController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 6;

	private final IProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri = req.getRequestURI();

		if (uri.endsWith("/product/detail")) {
			showDetail(req, resp);
		} else if (uri.endsWith("/product")) {
			showProductPage(req, resp);
		} else { // /home
			showHome(req, resp);
		}
	}

	private void showHome(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Product> newest = productService.findNewest(10);
		req.setAttribute("listnewest", newest);
		req.getRequestDispatcher("/views/shop/home.jsp").forward(req, resp);
	}

	private void showProductPage(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int page = parsePage(req.getParameter("page"));
		int total = productService.count();
		int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);
		if (totalPages == 0) {
			totalPages = 1;
		}
		if (page > totalPages) {
			page = totalPages;
		}

		List<Product> list = productService.findAll(page - 1, PAGE_SIZE);
		req.setAttribute("listproduct", list);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);
		req.getRequestDispatcher("/views/shop/product.jsp").forward(req, resp);
	}

	private void showDetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch (Exception e) {
			resp.sendRedirect(req.getContextPath() + "/home");
			return;
		}
		Product product = productService.findById(id);
		if (product == null) {
			resp.sendRedirect(req.getContextPath() + "/home");
			return;
		}
		req.setAttribute("prod", product);
		req.getRequestDispatcher("/views/shop/product-detail.jsp").forward(req, resp);
	}

	private int parsePage(String s) {
		try {
			int p = Integer.parseInt(s);
			return Math.max(p, 1);
		} catch (Exception e) {
			return 1;
		}
	}
}
