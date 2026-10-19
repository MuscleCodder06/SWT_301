# TÀI LIỆU MA TRẬN THIẾT KẾ KỊCH BẢN KIỂM THỬ (TEST DESIGN SPECIFICATION)

> **Dự án:** Quản lý cửa hàng sửa xe máy (Motorbike Repair Shop)  
> **Kỹ thuật thiết kế:** Phân vùng tương đương (Equivalence Partitioning) & Phân tích giá trị biên (Boundary Value Analysis)  
> **Quy ước:**
> - **Type:** `N` (Normal - Bình thường), `B` (Boundary - Giá trị biên), `A` (Abnormal - Giá trị bất thường/ngoại lệ)
> - **Passed/Failed:** `P` (Passed - Đạt), `F` (Failed - Không đạt / Phát hiện lỗi)
> - Ký hiệu `O`: Thể hiện điều kiện/kết quả được chọn trong ca kiểm thử.

---

## MỤC LỤC
1. [Bảng 1: addService (Thêm mới dịch vụ)](#1-bảng-addservice-thêm-mới-dịch-vụ)
2. [Bảng 2: updateService (Cập nhật dịch vụ)](#2-bảng-updateservice-cập-nhật-dịch-vụ)
3. [Bảng 3: deactivateService (Vô hiệu hóa dịch vụ)](#3-bảng-deactivateservice-vô-hiệu-hóa-dịch-vụ)
4. [Bảng 4: addPart (Thêm mới phụ tùng)](#4-bảng-addpart-thêm-mới-phụ-tùng)
5. [Bảng 5: updatePart (Cập nhật phụ tùng)](#5-bảng-updatepart-cập-nhật-phụ-tùng)
6. [Bảng 6: deactivatePart (Vô hiệu hóa phụ tùng)](#6-bảng-deactivatepart-vô-hiệu-hóa-phụ-tùng)
7. [Bảng tổng hợp lỗi (Defect Report)](#7-bảng-tổng-hợp-lỗi-defect-report)

---

## 1. Bảng: addService (Thêm mới dịch vụ)

| Phân loại | Mục kiểm tra | Điều kiện / Lớp tương đương | Dữ liệu đầu vào | UTCID01 | UTCID02 | UTCID03 | UTCID04 | UTCID05 | UTCID06 | UTCID07 |
| :--- | :--- | :--- | :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Condition** | **Precondition** | Connected to the database / Mock DAO ready | | **O** | **O** | **O** | **O** | **O** | **O** | **O** |
| | **Name** | Valid name (Tên hợp lệ) | `ut_Thay nhot` | **O** | | | | **O** | **O** | **O** |
| | | 100 character (boundary = 100) | `ut_aaa....a` | | **O** | | | | | |
| | | 101 character (boundary = 101) | `ut_aaa....a` | | | **O** | | | | |
| | | null name (Tên trống) | `null` | | | | **O** | | | |
| | **Description** | Valid description (Mô tả hợp lệ) | `desc` | **O** | **O** | **O** | **O** | **O** | **O** | **O** |
| | **Price** | Valid normal price (Đơn giá thông thường) | `120000` | **O** | | | | | | |
| | | Nominal price | `1000` | | **O** | **O** | **O** | | | |
| | | zero price (boundary = 0) | `0` | | | | | | **O** | |
| | | null price (Giá rỗng) | `null` | | | | | **O** | | |
| | | negative price (Giá âm) | `-5000` | | | | | | | **O** |
| **Confirm** | **Return** | ID > 0 (Thành công sinh mã ID) | `ID > 0` | **O** | **O** | | | | **O** | |
| | | -1 (Thất bại / Từ chối dữ liệu) | `-1` | | | **O** | **O** | **O** | | **O** |
| | **Exception** | No unhandled exception | None | | | | | | | |
| | **Log message** | None | | | | | | | | |
| **Result** | **Type** | (N: Normal, B: Boundary, A: Abnormal) | | **N** | **B** | **B** | **A** | **A** | **B** | **A** |
| | **Passed/Failed** | Kết quả thực thi | | **P** | **P** | **P** | **P** | **P** | **P** | **F** |
| | **Executed Date**| Ngày chạy kiểm thử | | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 |
| | **Defect ID** | Mã lỗi ghi nhận | | | | | | | | **Defect_01** |

---

## 2. Bảng: updateService (Cập nhật dịch vụ)

| Phân loại | Mục kiểm tra | Điều kiện / Lớp tương đương | Dữ liệu đầu vào | UTCID01 | UTCID02 | UTCID03 | UTCID04 | UTCID05 |
| :--- | :--- | :--- | :--- | :---: | :---: | :---: | :---: | :---: |
| **Condition** | **Precondition** | Connected to the database / Mock DAO ready | | **O** | **O** | **O** | **O** | **O** |
| | **ID** | Existing ID (ID tồn tại trong hệ thống) | `1` | **O** | | **O** | **O** | **O** |
| | | ID does not exist (ID không tồn tại) | `999999` | | **O** | | | |
| | **Name** | Valid name (Tên hợp lệ) | `ut_new name` | **O** | **O** | | | |
| | | null name (Tên rỗng) | `null` | | | **O** | | |
| | | 100 character (boundary = 100) | `ut_aaa....a` | | | | **O** | |
| | | 101 character (boundary = 101) | `ut_aaa....a` | | | | | **O** |
| | **Description** | Valid description (Mô tả) | `new desc` | **O** | **O** | **O** | **O** | **O** |
| | **Price** | Valid price (Đơn giá hợp lệ) | `250000` | **O** | **O** | **O** | **O** | **O** |
| **Confirm** | **Return** | true (Cập nhật thành công) | `true` | **O** | | | **O** | |
| | | false (Cập nhật thất bại / Bị chặn) | `false` | | **O** | **O** | | **O** |
| | **Exception** | No unhandled exception | None | | | | | |
| | **Log message** | None | | | | | | | |
| **Result** | **Type** | (N: Normal, B: Boundary, A: Abnormal) | | **N** | **A** | **A** | **B** | **B** |
| | **Passed/Failed** | Kết quả thực thi | | **P** | **P** | **P** | **P** | **P** |
| | **Executed Date**| Ngày chạy kiểm thử | | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 |
| | **Defect ID** | Mã lỗi ghi nhận | | | | | | |

---

## 3. Bảng: deactivateService (Vô hiệu hóa dịch vụ)

| Phân loại | Mục kiểm tra | Điều kiện / Lớp tương đương | Dữ liệu đầu vào | UTCID01 | UTCID02 | UTCID03 |
| :--- | :--- | :--- | :--- | :---: | :---: | :---: |
| **Condition** | **Precondition** | Connected to the database / Mock DAO ready | | **O** | **O** | **O** |
| | **ID** | Existing ID (ID dịch vụ tồn tại) | `10` | **O** | | |
| | | ID does not exist (ID không tồn tại) | `999999` | | **O** | |
| | | Boundary ID (ID <= 0) | `0` hoặc `-1` | | | **O** |
| **Confirm** | **Return** | true (Vô hiệu hóa thành công) | `true` | **O** | | |
| | | false (Thất bại / Bị chặn bởi Service) | `false` | | **O** | **O** |
| | **Exception** | No unhandled exception | None | | | |
| | **Log message** | None | | | | | |
| **Result** | **Type** | (N: Normal, B: Boundary, A: Abnormal) | | **N** | **A** | **B** |
| | **Passed/Failed** | Kết quả thực thi | | **P** | **P** | **P** |
| | **Executed Date**| Ngày chạy kiểm thử | | 2026/10/18 | 2026/10/18 | 2026/10/18 |
| | **Defect ID** | Mã lỗi ghi nhận | | | | |

---

## 4. Bảng: addPart (Thêm mới phụ tùng)

| Phân loại | Mục kiểm tra | Điều kiện / Lớp tương đương | Dữ liệu đầu vào | UTCID01 | UTCID02 | UTCID03 | UTCID04 | UTCID05 | UTCID06 | UTCID07 | UTCID08 | UTCID09 |
| :--- | :--- | :--- | :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Condition** | **Precondition** | Connected to the database / Mock DAO ready | | **O** | **O** | **O** | **O** | **O** | **O** | **O** | **O** | **O** |
| | **Name** | Valid name (Tên hợp lệ) | `ut_Loc nhot` | **O** | | | **O** | **O** | | **O** | **O** | **O** |
| | | 100 character (boundary = 100) | `ut_aaa....a` | | **O** | | | | | | | |
| | | 101 character (boundary = 101) | `ut_aaa....a` | | | **O** | | | | | | |
| | | null name (Tên trống) | `null` | | | | | | **O** | | | |
| | **Description** | Valid description | `desc` | **O** | **O** | **O** | **O** | **O** | **O** | **O** | **O** | **O** |
| | **Unit** | Valid unit (Đơn vị tính hợp lệ) | `cai` / `pcs` | **O** | **O** | **O** | | | **O** | **O** | **O** | **O** |
| | | 20 character (boundary = 20) | `uuuuuuuuuuuuuuuuuuuu` | | | | **O** | | | | | |
| | | 21 character (boundary = 21) | `uuuuuuuuuuuuuuuuuuuuu` | | | | | **O** | | | | |
| | **Quantity** | Valid quantity (Số lượng hợp lệ) | `40` hoặc `1` | **O** | **O** | **O** | **O** | **O** | **O** | | | **O** |
| | | zero quantity (boundary = 0) | `0` | | | | | | | **O** | | |
| | | negative quantity (Số lượng âm) | `-5` | | | | | | | | **O** | |
| | **Price** | Valid normal price (Đơn giá hợp lệ) | `35000` hoặc `1000` | **O** | **O** | **O** | **O** | **O** | **O** | **O** | **O** | |
| | | negative price (Đơn giá âm) | `-1000` | | | | | | | | | **O** |
| **Confirm** | **Return** | ID > 0 (Thành công sinh mã ID) | `ID > 0` | **O** | **O** | | **O** | | | **O** | | |
| | | -1 (Thất bại / Bị từ chối) | `-1` | | | **O** | | **O** | **O** | | **O** | **O** |
| | **Exception** | No unhandled exception | None | | | | | | | | | |
| | **Log message** | None | | | | | | | | | | |
| **Result** | **Type** | (N: Normal, B: Boundary, A: Abnormal) | | **N** | **B** | **B** | **B** | **B** | **A** | **B** | **A** | **A** |
| | **Passed/Failed** | Kết quả thực thi | | **P** | **P** | **P** | **P** | **P** | **P** | **P** | **F** | **F** |
| | **Executed Date**| Ngày chạy kiểm thử | | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 |
| | **Defect ID** | Mã lỗi ghi nhận | | | | | | | | | **Defect_02** | **Defect_03** |

---

## 5. Bảng: updatePart (Cập nhật phụ tùng)

| Phân loại | Mục kiểm tra | Điều kiện / Lớp tương đương | Dữ liệu đầu vào | UTCID01 | UTCID02 | UTCID03 | UTCID04 |
| :--- | :--- | :--- | :--- | :---: | :---: | :---: | :---: |
| **Condition** | **Precondition** | Connected to the database / Mock DAO ready | | **O** | **O** | **O** | **O** |
| | **ID** | Existing ID (ID tồn tại trong hệ thống) | `1` | **O** | | **O** | **O** |
| | | ID does not exist (ID không tồn tại) | `999999` | | **O** | | |
| | **Name** | Valid name (Tên hợp lệ) | `ut_new part` | **O** | **O** | | **O** |
| | | null name (Tên rỗng) | `null` | | | **O** | |
| | **Description** | Valid description | `new desc` | **O** | **O** | **O** | **O** |
| | **Unit** | Valid unit (Đơn vị tính hợp lệ) | `bo` / `pcs` | **O** | **O** | **O** | |
| | | 21 character (boundary = 21) | `uuuuuuuuuuuuuuuuuuuuu` | | | | **O** |
| | **Quantity** | Valid quantity | `25` | **O** | **O** | **O** | **O** |
| | **Price** | Valid price | `99000` | **O** | **O** | **O** | **O** |
| **Confirm** | **Return** | true (Cập nhật thành công) | `true` | **O** | | | |
| | | false (Cập nhật thất bại / Bị chặn) | `false` | | **O** | **O** | **O** |
| | **Exception** | No unhandled exception | None | | | | |
| | **Log message** | None | | | | | |
| **Result** | **Type** | (N: Normal, B: Boundary, A: Abnormal) | | **N** | **A** | **A** | **B** |
| | **Passed/Failed** | Kết quả thực thi | | **P** | **P** | **P** | **P** |
| | **Executed Date**| Ngày chạy kiểm thử | | 2026/10/18 | 2026/10/18 | 2026/10/18 | 2026/10/18 |
| | **Defect ID** | Mã lỗi ghi nhận | | | | | |

---

## 6. Bảng: deactivatePart (Vô hiệu hóa phụ tùng)

| Phân loại | Mục kiểm tra | Điều kiện / Lớp tương đương | Dữ liệu đầu vào | UTCID01 | UTCID02 | UTCID03 |
| :--- | :--- | :--- | :--- | :---: | :---: | :---: |
| **Condition** | **Precondition** | Connected to the database / Mock DAO ready | | **O** | **O** | **O** |
| | **ID** | Existing ID (ID phụ tùng tồn tại) | `10` | **O** | | |
| | | ID does not exist (ID không tồn tại) | `999999` | | **O** | |
| | | Boundary ID (ID <= 0) | `0` hoặc `-1` | | | **O** |
| **Confirm** | **Return** | true (Vô hiệu hóa thành công) | `true` | **O** | | |
| | | false (Thất bại / Bị chặn bởi Service) | `false` | | **O** | **O** |
| | **Exception** | No unhandled exception | None | | | |
| | **Log message** | None | | | | | |
| **Result** | **Type** | (N: Normal, B: Boundary, A: Abnormal) | | **N** | **A** | **B** |
| | **Passed/Failed** | Kết quả thực thi | | **P** | **P** | **P** |
| | **Executed Date**| Ngày chạy kiểm thử | | 2026/10/18 | 2026/10/18 | 2026/10/18 |
| | **Defect ID** | Mã lỗi ghi nhận | | | | |

---

## 7. Bảng tổng hợp lỗi (Defect Report)

Bảng ghi nhận các lỗi (Defects) được phát hiện thông qua 3 ca kiểm thử thất bại trên:

| Defect ID | Test Case | Mức độ nghiêm trọng (Severity) | Mô tả lỗi (Defect Description) | Kết quả kỳ vọng (Expected) | Kết quả thực tế (Actual) |
| :--- | :--- | :---: | :--- | :--- | :--- |
| **Defect_01** | `addService UTCID07` | **Medium** | Tầng Service/DAO không validate chặn đơn giá dịch vụ âm (`price < 0`), dẫn đến hệ thống vẫn lưu dịch vụ có giá âm vào CSDL. | Hệ thống từ chối và trả về `-1`. | Hệ thống không báo lỗi, chấp nhận lưu và trả về `ID > 0` (Code `1`). |
| **Defect_02** | `addPart UTCID08` | **High** | Tầng Service/DAO không kiểm tra ràng buộc số lượng tồn kho âm (`quantity < 0`), dẫn đến việc tạo mới linh kiện với tồn kho âm. | Hệ thống từ chối và trả về `-1`. | Hệ thống chấp nhận lưu và trả về `ID > 0` (Code `1`). |
| **Defect_03** | `addPart UTCID09` | **Medium** | Tầng Service/DAO không validate chặn đơn giá phụ tùng âm (`price < 0`), cho phép tạo linh kiện với giá bán nhỏ hơn 0. | Hệ thống từ chối và trả về `-1`. | Hệ thống chấp nhận lưu và trả về `ID > 0` (Code `1`). |
