# Bài tập Web (JPA) — CRUD Category + Products + Xác thực OTP

Ứng dụng Jakarta EE (Servlet 6 + JSP/JSTL) dùng **JPA 3.1 / Hibernate 6**, SQL Server.
Ngoài CRUD `Category` (bài 2), đã bổ sung: **đăng ký + kích hoạt OTP qua email**, **đăng nhập**,
**quên mật khẩu qua OTP**, và **CRUD `Products`** (1-n với `Category`) kèm trang bán hàng
(trang chủ 10 sản phẩm mới nhất, danh sách phân trang, trang chi tiết).

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

## Tính năng mới (bài tập tiếp theo)

### 1–3. Tài khoản: đăng ký + kích hoạt OTP, đăng nhập, quên mật khẩu
- Bảng `users` (Hibernate tự tạo): `UserId, Fullname, Email (unique), Password (SHA-256), Otp, OtpExpiry, Status`.
- Mật khẩu **băm SHA-256**, không lưu thô. OTP 6 số, hết hạn sau **5 phút**.
- Luồng: `/register` → sinh OTP + gửi mail → `/verify-otp` (kích hoạt, `Status=1`) → `/login`.
- `/forgot-password` → gửi OTP → `/reset-password` đặt lại mật khẩu.
- **Chặn `/admin/*`** bằng `AuthFilter`: chưa đăng nhập sẽ bị chuyển tới `/login` (nhớ URL đích).

### Cấu hình gửi email OTP
File `src/main/resources/email.properties`:
- **Để trống** `username/password` → **chế độ dev**: OTP in ra log server và hiện luôn trên trang (test không cần mail thật).
- Điền **Gmail + App Password** (hoặc đặt biến môi trường `MAIL_USERNAME`, `MAIL_PASSWORD`, `MAIL_FROM`) → gửi mail thật qua SMTP `smtp.gmail.com:587` (STARTTLS).
- ⚠️ Repo public: **không commit mật khẩu thật**, nên dùng biến môi trường.

### 4. Products (1-n với Category)
- Bảng `products`: `ProductId, ProductName, Image, Price, Quantity, Description, Status, CreatedDate, CategoryId (FK)`.
- **CRUD** ở `/admin/products` (thêm/sửa/xóa, upload ảnh hoặc link URL, chọn danh mục).
- **Trang chủ `/home`**: 10 sản phẩm mới nhất (theo `CreatedDate` giảm dần).
- **`/product`**: tất cả sản phẩm, **phân trang 6 sp/trang**.
- **`/product/detail?id=`**: chi tiết 1 sản phẩm (bấm vào sản phẩm ở trang chủ hoặc trang product).

### Danh sách URL
| Public | Admin (cần đăng nhập) |
|---|---|
| `/home`, `/product`, `/product/detail?id=` | `/admin/categories`, `/admin/category/...` |
| `/register`, `/verify-otp`, `/login`, `/logout` | `/admin/products`, `/admin/product/...` |
| `/forgot-password`, `/reset-password` | |
