# Bài 2 (JPA) — CRUD Category với JPA 3.1 + Hibernate + Jakarta 6

Bản chuyển từ JDBC thủ công sang **JPA (Jakarta Persistence 3.1)**, provider **Hibernate 6**. Entity `Category` (quan hệ 1-n với `Video`), thao tác qua `EntityManager`.

## Yêu cầu môi trường
JDK 17+, Tomcat 10.1+/11, Maven, SQL Server (đã bật `sa` + TCP 1433).

## CSDL
Dùng lại database **`CategoryCRUD`** (đã tạo). **Không cần viết SQL tạo bảng** — Hibernate tự tạo bảng `categories` và `Videos` ở lần chạy đầu (`hibernate.hbm2ddl.auto=update`).
Kết nối cấu hình trong `src/main/resources/META-INF/persistence.xml` (mặc định `localhost:1433 / sa / 1234@a$ / CategoryCRUD`).

## Ảnh mặc định
Khi thêm danh mục không chọn ảnh, hệ thống dùng `avatar.png`. Hãy để 1 file **`avatar.png`** trong `E:\upload` để ảnh mặc định không bị vỡ.

## Chạy
Bấm đúp **`run.bat`** → mở `http://localhost:8081/Bai2JPA/` (tự chuyển tới `/admin/categories`).
Tắt: **`stop.bat`**.

## Cấu trúc (JPA + MVC)
```
src/main/resources/META-INF/persistence.xml   <- cau hinh JPA
src/main/java/vn/iotstar/
  config/JPAConfig.java              <- EntityManagerFactory dung chung
  entity/Category.java, Video.java   <- @Entity
  dao/ICategoryDao.java + dao/impl/CategoryDao.java   <- dung EntityManager
  service/ICategoryService.java + service/impl/CategoryServiceImpl.java
  controller/CategoryController.java <- 1 servlet gom list/add/insert/edit/update/delete
  controller/DownloadImageController.java  (/image)
  util/Constant.java
src/main/webapp/
  index.jsp (chuyen -> /admin/categories)
  css/style.css
  views/admin/ category-list.jsp, category-add.jsp, category-edit.jsp
```

## Điểm khác so với bản JDBC (Bai2_CRUD)
- Không còn `DBConnection`, `PreparedStatement` — thay bằng `EntityManager` (persist/merge/remove/find, JPQL).
- Bảng mới `categories` (CategoryId, CategoryName, Images, status) + `Videos`.
- Ảnh có thể là **link URL (https...)** hoặc **file upload**; có cột trạng thái (Hoạt động/Khóa).
- `JPAConfig` đặt `net.bytebuddy.experimental=true` để Hibernate chạy được trên JDK 26.
