package vn.iotstar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.util.Constant;

@MultipartConfig
@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/insert",
		"/admin/product/edit", "/admin/product/update", "/admin/product/delete" })
public class ProductController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IProductService productService = new ProductServiceImpl();
	private final ICategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url = req.getRequestURI();
		if (url.contains("/admin/products")) {
			List<Product> list = productService.findAll();
			req.setAttribute("listproduct", list);
			req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
		} else if (url.contains("/admin/product/add")) {
			req.setAttribute("listcate", categoryService.findAll());
			req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
		} else if (url.contains("/admin/product/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			req.setAttribute("prod", productService.findById(id));
			req.setAttribute("listcate", categoryService.findAll());
			req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
		} else { // delete
			int id = Integer.parseInt(req.getParameter("id"));
			productService.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/admin/product/insert")) {
			Product product = new Product();
			bindCommonFields(req, product);
			product.setCreatedDate(new Date());
			product.setImage(resolveImageOnInsert(req));
			productService.insert(product);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		} else if (url.contains("/admin/product/update")) {
			int productid = Integer.parseInt(req.getParameter("productid"));
			Product product = productService.findById(productid);
			if (product == null) {
				resp.sendRedirect(req.getContextPath() + "/admin/products");
				return;
			}
			String fileold = product.getImage();
			bindCommonFields(req, product);
			product.setImage(resolveImageOnUpdate(req, fileold));
			productService.update(product);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	// Gán các trường chung từ form vào entity
	private void bindCommonFields(HttpServletRequest req, Product product) {
		product.setProductName(req.getParameter("productname"));
		product.setPrice(parseDouble(req.getParameter("price")));
		product.setQuantity(parseInt(req.getParameter("quantity")));
		product.setDescription(req.getParameter("description"));
		product.setStatus(parseInt(req.getParameter("status")));

		int categoryid = parseInt(req.getParameter("categoryid"));
		Category category = categoryService.findById(categoryid);
		product.setCategory(category);
	}

	private String resolveImageOnInsert(HttpServletRequest req) throws IOException, ServletException {
		String uploadPath = ensureUploadDir();
		String images = req.getParameter("images");
		Part part = getFilePart(req);
		if (part != null && part.getSize() > 0) {
			return saveUpload(part, uploadPath);
		} else if (images != null && !images.isEmpty()) {
			return images;
		}
		return "avatar.png";
	}

	private String resolveImageOnUpdate(HttpServletRequest req, String fileold)
			throws IOException, ServletException {
		String uploadPath = ensureUploadDir();
		String images = req.getParameter("images");
		Part part = getFilePart(req);
		if (part != null && part.getSize() > 0) {
			if (fileold != null && fileold.length() >= 5 && !fileold.substring(0, 5).equals("https")) {
				try {
					deleteFile(uploadPath + "\\" + fileold);
				} catch (IOException ignored) {
				}
			}
			return saveUpload(part, uploadPath);
		} else if (images != null && !images.isEmpty()) {
			return images;
		}
		return fileold;
	}

	private String ensureUploadDir() {
		String uploadPath = Constant.DIR;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		return uploadPath;
	}

	private Part getFilePart(HttpServletRequest req) throws IOException, ServletException {
		try {
			return req.getPart("images1");
		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
			return null;
		}
	}

	private String saveUpload(Part part, String uploadPath) throws IOException {
		String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		int index = filename.lastIndexOf(".");
		String ext = index >= 0 ? filename.substring(index + 1) : "png";
		String fname = System.currentTimeMillis() + "." + ext;
		part.write(uploadPath + "/" + fname);
		return fname;
	}

	private static int parseInt(String s) {
		try {
			return Integer.parseInt(s.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	private static double parseDouble(String s) {
		try {
			return Double.parseDouble(s.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	public static void deleteFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Files.deleteIfExists(path);
	}
}
