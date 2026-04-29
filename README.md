# Phone Store Manager - Java Swing + SQLite

Ứng dụng desktop quản lý cửa hàng điện thoại, chạy bằng **Java Swing** và **SQLite**.
Có thể mở bằng **VS Code** hoặc IntelliJ IDEA.

## Chức năng chính
- Quản lý danh mục / thương hiệu
- Quản lý sản phẩm điện thoại và phụ kiện
- Quản lý khách hàng
- Quản lý nhà cung cấp
- Quản lý nhân viên
- Quản lý nhập hàng
- Quản lý bán hàng / hóa đơn
- Quản lý bảo hành / đổi trả
- Thống kê nhanh: doanh thu, tồn kho, số sản phẩm, số khách hàng

## Yêu cầu
- JDK 17+
- Maven 3.9+

## Cách chạy bằng VS Code
1. Cài **Extension Pack for Java**
2. Mở thư mục project này trong VS Code
3. Mở terminal tại thư mục project
4. Chạy:
   ```bash
   mvn clean compile
   mvn exec:java
   ```

## Database
- File SQLite sẽ tự tạo ở:
  `data/phone_store.db`

## Tài khoản mẫu
Ứng dụng hiện là desktop nội bộ, chưa ép đăng nhập.
Bạn có thể mở rộng thêm form Login nếu muốn.

## Gợi ý mở rộng
- In hóa đơn PDF
- Upload ảnh sản phẩm
- Phân quyền đăng nhập
- Xuất Excel báo cáo
- Tìm kiếm nâng cao
