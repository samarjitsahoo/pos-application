# Enterprise-grade Multi-Tenant SaaS Point-of-Sale (POS) Application

## 🧾 POS System – Features

- pos terminal
- search product
- create order
- cart managment
- customer managment
- discount
- order history
- download bill
- shift summary of cashier
- refund managment
- inventory managment
- sales chart
- report chart
- branch dashboard
- store dashboard
- product managment
- super admin for managing stores, mange subscription plan
- store can take subscription
- store onboarding
- login
- forgot password
- dark/light theme
- landing page

## Project Configuration & Dependency

### 🔹 **Spring Boot Starters**

| Dependency | Description |
| --- | --- |
| `spring-boot-starter-data-jpa` | Provides support for working with databases using JPA & Hibernate. |
| `spring-boot-starter-security` | Enables Spring Security for login, role-based access, and authentication. |
| `spring-boot-starter-web` | Required for building RESTful APIs using Spring MVC. |
| `spring-boot-starter-validation` | Enables request body validation using annotations like `@NotNull`, `@Email`, etc. |
| `spring-boot-starter-mail` | Sends email notifications (e.g., verification email, invoice receipt). |

---

### 🧪 **Testing Dependencies**

| Dependency | Description |
| --- | --- |
| `spring-boot-starter-test` | Includes JUnit, Mockito, and Spring Test for writing and running unit/integration tests. |
| `spring-security-test` | Allows testing of secured endpoints (authentication, authorization). |

---

### 🛠️ **Development Tools**

| Dependency | Description |
| --- | --- |
| `spring-boot-devtools` | Automatically restarts the server on code changes; useful for development. |
| `lombok` | Reduces boilerplate code by generating getters, setters, constructors, etc. (Use with `@Getter`, `@Setter`, etc.) |

---

### 🗄️ **Database**

| Dependency | Description |
| --- | --- |
| `mysql-connector-j` | MySQL JDBC driver that connects your Spring Boot application to MySQL database. |

---

### 🔐 **JWT (JSON Web Token)**

| Dependency | Description |
| --- | --- |
| `jjwt-api` | Main JWT API used to create and validate JWT tokens. |
| `jjwt-impl` | Runtime implementation of JWT (required for token signing). |
| `jjwt-jackson` | Integrates JWT with Jackson for JSON parsing of payload. |

---

### 💳 **Payment Gateways**

| Dependency | Description |
| --- | --- |
| `razorpay-java` | Java SDK for integrating Razorpay payments (mostly used in India). |
| `stripe-java` | Java SDK for integrating Stripe payments (global payment platform). |

---

### 🛠️ **Build Plugin**

| Plugin | Description |
| --- | --- |
| `spring-boot-maven-plugin` | Allows you to package and run the application easily with `mvn spring-boot:run`. |

---

### ✅ Summary

This setup enables:

- 🛠 REST APIs (Spring Web)
- 🔐 Auth (Spring Security + JWT)
- 📧 Email services (Spring Mail)
- 📊 Database (JPA + MySQL)
- 💳 Online Payments (Stripe, Razorpay)
- 🧪 Unit Testing
- ⚙️ Dev support (Lombok, DevTools)

## 🧾 POS System – Backend Documentation (Spring Boot)

- Auth Service
    
    
    1. ✅ **Create a new Spring Boot project** from scratch.
    2. ⚙️ **Configure all the necessary settings** like dependencies, packages, and properties.
    3. 🔐 **Build user authentication APIs** — we'll implement the **Login and Register** functionality.
    
    ### 🔐 1. **Authentication Module**
    
    Handles login and registration.
    
    ### 📌 Endpoints:
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | POST | `/auth/signup` | Register a new user (e.g. admin, cashier) |
    | POST | `/auth/login` | Login with email & password |
    
    ### 📄 Models:
    
    ```java
    
    private Long id; // Unique ID for each user (primary key)
    
    private String fullName; // Full name of the user
    
    private String password; // Hashed password for login
    
    private String email; // User's email (must be unique and valid)
    
    private String phone; // Optional phone number
    
    private Store store; // Store to which the user belongs (Many users → One store)
    
    private Branch branch; // Branch of the store (used internally)
    
    private UserRole role; // User role: ADMIN, CASHIER, MANAGER etc.
    
    private LocalDateTime createdAt; // Automatically set when user is created
    
    private LocalDateTime updatedAt; // Automatically updated whenever user is modified
    
    private Boolean verified; // True if email or account is verified
    
    private LocalDateTime lastLogin; // Stores the date and time of the last login
    
    ```
    
    ### ✅ Auth Flow:
    
    - On registration, password is hashed using BCrypt.
    - JWT token is generated on successful login.
    - Token is required for accessing secured APIs.

- User Service
    
    ---
    
    ### 👤 2. **User Module**
    
    Admin can manage users like cashiers and managers.
    
    ### 📄 Model:
    
    ```java
    
    User {
        Long id;
        String fullName;
        String email;
        String password;
        String role; // ADMIN, CASHIER
        Boolean isActive;
        LocalDateTime createdAt;
    }
    
    ```
    
    ### 📌 Endpoints:
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | GET | `/api/user/profile` | Get users Profile |
    | GET | `/api/users/{id}` | get user by id |
    
    ---
    
    ### 🛒 3. **Product Module**
    
    Manage all products available in the store.
    
    ### 📄 Model:
    
    ```java
    
    Product {
        Long id;
        String name;
        String barcode;
        Double price;
        Integer quantity;
        String category;
        Boolean isAvailable;
        LocalDateTime createdAt;
    }
    
    ```
    
    ### 📌 Endpoints:
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | GET | `/api/products` | Get all products |
    | POST | `/api/products` | Add new product |
    | PUT | `/api/products/{id}` | Update product |
    | DELETE | `/api/products/{id}` | Delete product |
    
    ---
    
    ### 📦 4. **Inventory Module**
    
    Track and update stock levels.
    
    ### 📄 Model:
    
    ```java
    java
    CopyEdit
    Inventory {
        Long id;
        Long productId;
        Integer quantityAdded;
        Integer currentStock;
        LocalDateTime updatedAt;
    }
    
    ```
    
    ### 📌 Endpoints:
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | POST | `/api/inventory/add` | Add stock to product |
    
    ---
    
    ### 💳 5. **Sales Module**
    
    Handles sale transactions.
    
    ### 📄 Model:
    
    ```java
    java
    CopyEdit
    Sale {
        Long id;
        Long cashierId;
        Double totalAmount;
        String paymentMethod; // CASH, CARD, UPI
        LocalDateTime saleDate;
        List<SaleItem> items;
    }
    
    SaleItem {
        Long id;
        Long saleId;
        Long productId;
        Integer quantity;
        Double price;
    }
    
    ```
    
    ### 📌 Endpoints:
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | POST | `/api/sales` | Create new sale |
    | GET | `/api/sales` | Get all sales |
    | GET | `/api/sales/{id}` | Get sale details |
    
    ---
    
    ### 🧾 6. **Bill/Receipt Module**
    
    Generate a receipt for each sale (optional PDF or HTML view).
    
    ---
    
    ### 🔁 7. **Service & Mapper Layer**
    
    You’ll have:
    
    - `UserService`, `ProductService`, `SaleService`, etc.
    - `UserServiceImpl` for logic like registration, update user.
    - `UserMapper`, `ProductMapper`: convert between Entity and DTO.
    
    ```java
    java
    CopyEdit
    public interface UserService {
        UserResponse register(UserRequest request);
        UserResponse login(LoginRequest request);
        List<UserResponse> getAllUsers();
        ...
    }
    
    ```
    
    ```java
    java
    CopyEdit
    @Component
    public class UserMapper {
        public UserResponse toResponse(User user) { ... }
        public User toEntity(UserRequest req) { ... }
    }
    
    ```
    
    ---
    
    ### 🔒 8. **Security (JWT)**
    
    - Secures all routes except `/api/auth/**`
    - Role-based access: ADMIN can manage users, CASHIER can create sales.
    
    ---
    
    ### 🧰 Tools & Tech Stack
    
    | Feature | Technology |
    | --- | --- |
    | Framework | Spring Boot |
    | Auth | JWT, Spring Security |
    | ORM | JPA / Hibernate |
    | Database | MySQL or PostgreSQL |
    | Testing | Postman for APIs |
    | Docs | Swagger (optional) |
    
    ---
    
    ### 📊 9. **Future Enhancements (Optional Ideas)**
    
    - Dashboard analytics (daily sales, top products)
    - PDF receipt generation
    - Barcode scanner integration
    - Tax & discount logic
    - Offline mode support (in frontend)
- Store Api
    
    
    ## 📦 Module: `Store`
    
    > This module manages store creation, update, moderation, and employee assignments. Each store has a brand, admin, contact info, and status.
    > 
    
    ---
    
    ### 🧾 Store Model Documentation (`app.posify.modal.Store`)
    
    | Field | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Auto-generated unique ID for the store. |
    | `brand` | `String` | Brand name of the store (required). |
    | `storeAdmin` | `User` | User who created or owns the store (admin). |
    | `createdAt` | `LocalDateTime` | Timestamp when store was created. |
    | `updatedAt` | `LocalDateTime` | Timestamp when store was last updated. |
    | `description` | `String` | Description about the store (optional). |
    | `storeType` | `String` | Type of store (e.g., Grocery, Electronics). |
    | `status` | `StoreStatus` | Store's status: `PENDING`, `APPROVED`, `DECLINED`. |
    | `contact` | `StoreContact` | Embedded object for phone, email, address, etc. |
    
    ---
    
    ## 🌐 StoreController API Documentation
    
    ### 🔐 Protected Routes (some with role restrictions)
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | `POST` | `/api/stores` | ✅ **Create a store** – Requires JWT token |
    | `GET` | `/api/stores/{id}` | 🔍 **Get store by ID** |
    | `PUT` | `/api/stores/{id}` | ✏️ **Update store info** |
    | `DELETE` | `/api/stores` | ❌ **Delete store of the logged-in admin** |
    | `GET` | `/api/stores/admin` | 🧑‍💼 **Get store created by current admin** |
    | `GET` | `/api/stores/employee` | 👥 **Get store associated with logged-in employee** |
    | `GET` | `/api/stores/{storeId}/employee/list` | 📋 **List all employees in a store** (Only Manager/Admin) |
    | `POST` | `/api/stores/add/employee` | ➕ **Add employee to store** (Only Manager/Admin) |
    | `GET` | `/api/stores` | 🌍 **Get all stores** (optional filter by status) |
    | `PUT` | `/api/stores/{storeId}/moderate?action=APPROVED` | ⚖️ **Approve or decline a store** |
    
    ---
    
    ### 🧠 Roles Required for Access
    
    | Endpoint | Required Role |
    | --- | --- |
    | `/employee/list` | `ROLE_STORE_MANAGER`, `ROLE_STORE_ADMIN` |
    | `/add/employee` | `STORE_MANAGER`, `STORE_ADMIN` |
    
    ---
    
    ### 📄 Additional DTOs Used
    
    - `StoreDTO`: carries store data to/from frontend.
    - `UserDTO`: represents employee data when assigning to store.
    
    ---
    
    ### 📝 Summary (Explain to Students)
    
    | Component | Purpose |
    | --- | --- |
    | `Store` | Entity that represents a physical or virtual store. |
    | `StoreController` | API layer to create, fetch, update, delete, and assign employees. |
    | `StoreService` | Business logic for handling all store-related operations. |
    | `StoreStatus` | Enum for store moderation (Pending, Approved, Declined). |
    | `StoreContact` | Embedded object for storing store's contact info. |
- Product Api
    
    
    ## 🧾 PRODUCT Api – POS SYSTEM
    
    ---
    
    ### ✅ 1. `Product` Model – `app.posify.modal.Product`
    
    Represents a product available for sale in a store.
    
    ### 🔸 Fields:
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Auto-generated primary key |
    | `name` | `String` | Product name (required) |
    | `sku` | `String` | Unique identifier for inventory (required) |
    | `description` | `String` | Optional detailed description |
    | `mrp` | `Double` | Maximum Retail Price (required) |
    | `sellingPrice` | `Double` | Price at which product is sold (required) |
    | `brand` | `String` | Brand name (optional) |
    | `image` | `String` | Image URL or path |
    | `category` | `Category` | Associated category (Many-to-One) |
    | `store` | `Store` | Belongs to one store (Many-to-One, required) |
    | `createdAt` | `LocalDateTime` | Timestamp when product is created |
    | `updatedAt` | `LocalDateTime` | Timestamp when product is updated |
    
    ### 🔸 Special Annotations:
    
    - `@Entity`, `@Table(name = "products")`: Marks it as a JPA entity.
    - `@PrePersist`, `@PreUpdate`: Automatically sets timestamps for creation and update.
    
    ---
    
    ### ✅ 2. `ProductController` – `app.posify.controller.ProductController`
    
    Handles all HTTP requests related to product operations.
    
    ### 🔸 Base URL:
    
    ```
    bash
    CopyEdit
    /api/products
    
    ```
    
    ### 🔸 Dependencies:
    
    - `ProductService`: Business logic for product operations.
    - `UserService`: Extracts the user from JWT for authorization.
    
    ---
    
    ### ✅ Endpoints Overview
    
    | Method | Endpoint | Purpose |
    | --- | --- | --- |
    | `POST` | `/api/products` | Create new product |
    | `GET` | `/api/products/{id}` | Get product by ID |
    | `PATCH` | `/api/products/{id}` | Update product by ID |
    | `DELETE` | `/api/products/{id}` | Delete product by ID |
    | `GET` | `/api/products/store/{storeId}` | Get all products of a store |
    | `GET` | `/api/products/store/{storeId}/search?q=xyz` | Search products in a store |
    
    ---
    
    ### ✅ Request & Auth Flow
    
    - All protected endpoints require the `Authorization` header with JWT token.
    - Before performing operations like create/update/delete, the user is extracted using:
        
        ```java
        java
        CopyEdit
        userService.getUserFromJwtToken(jwt);
        
        ```
        
    
    ---
    
    ### ✅ DTO Used
    
    The `ProductDTO` is used as input/output data format for product-related endpoints to ensure abstraction and security.
    
    ---
    
    ### ✅ Error Handling
    
    - Throws `UserException` and `AccessDeniedException` if:
        - JWT is invalid
        - User is not authorized to access/update/delete a product
- Category Api
    
    ## 📦 Category Module – Documentation
    
    ---
    
    ### ✅ Model: `Category`
    
    This model represents product categories specific to each store.
    
    ### 🔑 Fields:
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | Long | Unique ID for each category (auto-generated). |
    | `name` | String | Name of the category (e.g., "Beverages", "Snacks"). |
    | `store` | `Store` | The store that this category belongs to (Many-to-One relationship). |
    
    ---
    
    ### 🧾 Controller: `CategoryController`
    
    This controller manages all operations related to categories.
    
    ### 📌 Endpoint Summary:
    
    | HTTP Method | Endpoint | Description | Access |
    | --- | --- | --- | --- |
    | `POST` | `/api/categories` | Create a new category for a store. | `STORE_MANAGER`, `STORE_ADMIN` |
    | `GET` | `/api/categories/store/{storeId}` | Fetch all categories belonging to a specific store. | Public |
    | `PUT` | `/api/categories/{id}` | Update category details by category ID. | `STORE_MANAGER`, `STORE_ADMIN` |
    | `DELETE` | `/api/categories/{id}` | Delete a category by ID. | `STORE_MANAGER`, `STORE_ADMIN` |
- Branch Api
    
    ## ✅ Model: `Branch`
    
    Represents a physical store branch within the POS system.
    
    ### 🔹 Fields:
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Unique identifier for the branch. |
    | `name` | `String` | Name of the branch. |
    | `address` | `String` | Physical address of the branch. |
    | `phone` | `String` | Contact phone number of the branch. |
    | `email` | `String` | Official email ID of the branch. |
    | `workingDays` | `List<String>` | Days of the week the branch operates (e.g., MONDAY, TUESDAY). |
    | `openTime` | `LocalTime` | Daily opening time. |
    | `closeTime` | `LocalTime` | Daily closing time. |
    | `createdAt` | `LocalDateTime` | Timestamp of when the branch was created. |
    | `updatedAt` | `LocalDateTime` | Timestamp of the last update to the branch. |
    | `store` | `Store` (ManyToOne) | The parent store to which this branch belongs. |
    | `manager` | `User` (OneToOne) | The branch manager assigned to this branch. |
    
    ---
    
    ## ✅ Controller: `BranchController`
    
    Handles all API operations related to branches.
    
    ### 🔹 Endpoints:
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | POST | `/api/branches` | Create a new branch (requires authorization). |
    | GET | `/api/branches/{id}` | Retrieve details of a branch by its ID. |
    | GET | `/api/branches/store/{storeId}` | Get all branches associated with a specific store. |
    | PUT | `/api/branches/{id}` | Update an existing branch (requires authorization). |
    | DELETE | `/api/branches/{id}` | Delete a branch by its ID. |
- Inventory
    
    ## ✅ Inventory Module Documentation
    
    ---
    
    ### 📦 `Inventory` Model
    
    Represents the stock of a specific product available in a specific branch.
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Unique identifier for the inventory record. |
    | `branch` | `Branch` | The branch where the product is stocked. (ManyToOne relationship) |
    | `product` | `Product` | The product being stocked. (ManyToOne relationship) |
    | `quantity` | `Integer` | Number of units available in this branch. |
    | `lastUpdated` | `LocalDateTime` | Timestamp of the last update to this inventory. |
    
    > 🔄 The @PrePersist and @PreUpdate method ensures that lastUpdated is automatically set during creation and updates.
    > 
    
    ---
    
    ### 🧩 `InventoryController` Endpoints
    
    Handles CRUD and search operations for inventory items.
    
    | HTTP Method | Endpoint | Description |
    | --- | --- | --- |
    | `POST` | `/api/inventories` | Create a new inventory record (Store Manager only). |
    | `PUT` | `/api/inventories/{id}` | Update inventory quantity for a specific record (Store Manager only). |
    | `DELETE` | `/api/inventories/{id}` | Delete an inventory record (Store Manager only). |
    | `GET` | `/api/inventories/{id}` | Get inventory details by inventory ID. |
    | `GET` | `/api/inventories/product/{productId}` | Get inventory record for a specific product (across all branches). |
    | `GET` | `/api/inventories/branch/{branchId}` | Get all inventory records for a specific branch. |
- employee api
    
    ### 🧾 **Endpoint Summary**
    
    | Method | Endpoint | Description | Access Roles |
    | --- | --- | --- | --- |
    | `POST` | `/api/employees/store/{storeId}` | Create an employee and assign to a store | `ROLE_STORE_ADMIN`, `ROLE_STORE_MANAGER` |
    | `POST` | `/api/employees/branch/{branchId}` | Create an employee and assign to a branch | `ROLE_BRANCH_ADMIN`, `ROLE_BRANCH_MANAGER` |
    | `PUT` | `/api/employees/{employeeId}` | Update employee details | Store/Branch Admins and Managers |
    | `DELETE` | `/api/employees/{employeeId}` | Delete an employee by ID | `ROLE_STORE_ADMIN`, `ROLE_BRANCH_ADMIN` |
    | `GET` | `/api/employees/{employeeId}` | Get employee details by ID | Store/Branch Admins and Managers |
    | `GET` | `/api/employees/store/{storeId}` | List all employees under a store | `ROLE_STORE_ADMIN`, `ROLE_STORE_MANAGER` |
    | `GET` | `/api/employees/branch/{branchId}?role=MANAGER` | List all employees under a branch, optionally filter by role | `ROLE_BRANCH_ADMIN`, `ROLE_BRANCH_MANAGER` |
- Customer Api
    
    ## 📦 `Customer` Module Documentation (POS System)
    
    This module is responsible for managing **customer data** in the POS system.
    
    ---
    
    ### 🧾 `Customer` Entity Fields
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Auto-generated primary key. |
    | `fullName` | `String` | Name of the customer. Required. |
    | `email` | `String` | Optional customer email (not enforced as unique). |
    | `phone` | `String` | Optional customer phone number. |
    | `createdAt` | `LocalDateTime` | Timestamp when customer record was created. Automatically generated. |
    | `updatedAt` | `LocalDateTime` | Timestamp when customer record was last updated. Automatically updated. |
    
    > 💡 @CreationTimestamp and @UpdateTimestamp are used to handle timestamps automatically.
    > 
    
    ---
    
    ### 📂 Customer Controller Endpoints
    
    The `CustomerController` handles all API operations related to customers:
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | `POST` | `/api/customers` | ✅ Create a new customer. |
    | `PUT` | `/api/customers/{id}` | ✏️ Update an existing customer by ID. |
    | `DELETE` | `/api/customers/{id}` | 🗑 Delete a customer by ID. |
    | `GET` | `/api/customers/{id}` | 🔍 Get a specific customer by ID. |
    | `GET` | `/api/customers` | 📋 Fetch all customers from the system. |
    
    ---
    
    ### ✅ Service Methods (Used Internally)
    
    The controller calls the following service methods:
    
    - `createCustomer(Customer customer)`
    - `updateCustomer(Long id, Customer customer)`
    - `deleteCustomer(Long id)`
    - `getCustomerById(Long id)`
    - `getAllCustomers()`
- order api
    
    ## 🧾 **Order Model – `app.posify.modal.Order`**
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Primary key of the order. |
    | `totalAmount` | `Double` | Total amount of the order. |
    | `createdAt` | `LocalDateTime` | Timestamp when the order was created (auto-filled). |
    | `branch` | `Branch` (ManyToOne) | Branch where the order was placed. (Ignored in JSON) |
    | `cashier` | `User` (ManyToOne) | Cashier (User) who created the order. (Ignored in JSON) |
    | `customer` | `Customer` (ManyToOne) | Customer who placed the order. |
    | `paymentType` | `PaymentType` (Enum) | Payment method used (e.g., CASH, UPI, CARD). |
    | `items` | `List<OrderItem>` (OneToMany) | List of items associated with the order. |
    
    ---
    
    ## 📦 **OrderController – `app.posify.controller.OrderController`**
    
    | Endpoint | HTTP Method | Access Role | Description |
    | --- | --- | --- | --- |
    | `/api/orders` | `POST` | `ROLE_CASHIER` | Create a new order using details like items, customer, and payment type. |
    | `/api/orders/{id}` | `GET` | Public | Get order details by order ID. |
    | `/api/orders/branch/{branchId}` | `GET` | Public | Get orders for a branch with optional filters: `customerId`, `cashierId`, `paymentType`, `status`. |
    | `/api/orders/cashier/{cashierId}` | `GET` | Public | Fetch all orders created by a specific cashier. |
    | `/api/orders/today/branch/{branchId}` | `GET` | Public | Fetch today's orders for a specific branch. |
    | `/api/orders/customer/{customerId}` | `GET` | Public | Get all orders placed by a specific customer. |
    | `/api/orders/recent/{branchId}` | `GET` | `ROLE_BRANCH_MANAGER`, `ROLE_BRANCH_ADMIN` | Get top 5 most recent orders of a branch. |
    | `/api/orders/{id}` | `DELETE` | `ROLE_STORE_MANAGER`, `ROLE_STORE_ADMIN` | Delete an order by ID. |
- refund api
    
    ## 💵 **Refund Model – `app.posify.modal.Refund`**
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Primary key of the refund record. |
    | `order` | `Order` | The order associated with this refund. |
    | `reason` | `String` | Reason provided for the refund. |
    | `amount` | `Double` | Amount that was refunded. |
    | `shiftReport` | `ShiftReport` | Shift report under which this refund was logged. (Ignored in JSON) |
    | `cashier` | `User` | Cashier who processed the refund. |
    | `branch` | `Branch` | Branch where the refund was made. |
    | `createdAt` | `LocalDateTime` | Timestamp when the refund was created. |
    | `paymentType` | `PaymentType` (Enum) | Payment method used in the refund (e.g., CASH, CARD, UPI). |
    
    ---
    
    ## 📥 **RefundController – `app.posify.controller.RefundController`**
    
    | Endpoint | HTTP Method | Description |
    | --- | --- | --- |
    | `/api/refunds` | `POST` | Create a new refund entry based on an order. |
    | `/api/refunds` | `GET` | Retrieve a list of all refunds (usually for admin view). |
    | `/api/refunds/cashier/{cashierId}` | `GET` | Fetch all refunds handled by a specific cashier. |
    | `/api/refunds/branch/{branchId}` | `GET` | Fetch all refunds processed within a specific branch. |
    | `/api/refunds/shift/{shiftReportId}` | `GET` | Get all refunds linked to a particular shift report. |
    | `/api/refunds/cashier/{cashierId}/range?from=...&to=...` | `GET` | Retrieve refunds for a cashier within a given date/time range. |
    | `/api/refunds/{id}` | `GET` | Get a specific refund by its ID. |
    | `/api/refunds/{id}` | `DELETE` | Delete a refund by its ID. |
- Shift Report Api
    
    
    **A cashier shift**
    
    in a POS system refers to the working period assigned to a cashier, like a morning or evening shift.
    
    It helps track sales, cash handling, and staff activity during that specific time frame.
    
    ## 📘 **ShiftReport Model – `app.posify.modal.ShiftReport`**
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Unique identifier for the shift report. |
    | `shiftStart` | `LocalDateTime` | Timestamp when the shift began. |
    | `shiftEnd` | `LocalDateTime` | Timestamp when the shift ended. |
    | `totalSales` | `Double` | Total sales recorded during the shift. |
    | `totalRefunds` | `Double` | Total refund amount issued during the shift. |
    | `netSales` | `Double` | Net sales (`totalSales - totalRefunds`). |
    | `totalOrders` | `int` | Total number of orders placed in the shift. |
    | `cashier` | `User` | The cashier associated with this shift. |
    | `branch` | `Branch` | The branch where this shift took place. |
    | `paymentSummaries` | `List<PaymentSummary>` | Summaries of payment types used (e.g., CASH, CARD, etc.). (Transient field) |
    | `topSellingProducts` | `List<Product>` | List of best-selling products in this shift. |
    | `recentOrders` | `List<Order>` | Recent orders placed during the shift. |
    | `refunds` | `List<Refund>` | List of refunds processed during the shift. |
    
    ---
    
    ## 📊 **ShiftReportController – `app.posify.controller.ShiftReportController`**
    
    | Endpoint | HTTP Method | Description |
    | --- | --- | --- |
    | `/api/shift-reports/start` | `POST` | 🔄 Start a new shift for the current logged-in cashier at a given branch. |
    | `/api/shift-reports/end` | `PATCH` | 🛑 End the current active shift for the logged-in cashier. |
    | `/api/shift-reports/current` | `GET` | 📊 Get live progress details of the currently running shift for the cashier. |
    | `/api/shift-reports/cashier/{cashierId}/by-date?date=` | `GET` | 📅 Get a specific shift report for a cashier on a given date. |
    | `/api/shift-reports/cashier/{cashierId}` | `GET` | 👤 Fetch all shift reports belonging to a specific cashier. |
    | `/api/shift-reports/branch/{branchId}` | `GET` | 🏬 Get all shift reports associated with a particular branch. |
    | `/api/shift-reports` | `GET` | 📋 Retrieve all shift reports in the system (admin view). |
    | `/api/shift-reports/{id}` | `GET` | 🔍 Get details of a specific shift report by ID. |
    | `/api/shift-reports/{id}` | `DELETE` | ❌ Delete a shift report by ID (for admin usage). |
- Admin Dashboard Service
    
    ## 📘 **AdminDashboardController Endpoint Documentation**
    
    ### 🔹 **Base Path:** `/api/super-admin`
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | GET | `/dashboard/summary` | Returns summary stats like total, active, pending, and blocked stores. |
    | GET | `/dashboard/store-registrations` | Returns store registration counts for the last 7 days (for line/bar charts). |
    | GET | `/dashboard/store-status-distribution` | Returns count distribution of store statuses (active, blocked, pending) for pie chart. |
- Branch Analytics
    
    
    ### 📊 Branch Analytics Controller – API Documentation
    
    | **Endpoint** | **Method** | **Description** |
    | --- | --- | --- |
    | `/api/branch-analytics/daily-sales` | `GET` | 🔁 Get **daily sales data** for a branch for the last N days (default is 7). Used to display sales trends in chart format. |
    | `/api/branch-analytics/top-products` | `GET` | 📦 Get top 5 **best-selling products** by quantity, including their **percentage contribution** to total sales. |
    | `/api/branch-analytics/top-cashiers` | `GET` | 👨‍💼 Get top 5 **cashiers ranked by revenue generated**. Useful for performance tracking. |
    | `/api/branch-analytics/category-sales` | `GET` | 📂 Get **category-wise sales summary** for a specific date. Shows how much each category contributed. |
    | `/api/branch-analytics/today-overview` | `GET` | 📅 Get a **complete overview of today's stats** for the selected branch (sales, orders, customers, etc.). |
    | `/api/branch-analytics/payment-breakdown` | `GET` | 💳 Get **payment method breakdown** for a specific date – shows how customers paid (Cash, Card, UPI, etc.). |
    
- Store Analytics
    
    
    ### 📊 Store Analytics Controller — `/api/store/analytics`
    
    | HTTP Method | Endpoint | Description |
    | --- | --- | --- |
    | `GET` | `/api/store/analytics/{storeAdminId}/overview` | 📌 Returns overall KPI summary for the store admin (orders, revenue, customers, etc.). |
    | `GET` | `/api/store/analytics/{storeAdminId}/sales-trends?period=daily | weekly |
    | `GET` | `/api/store/analytics/{storeAdminId}/sales/monthly` | 📅 Returns monthly sales data points for line graph. |
    | `GET` | `/api/store/analytics/{storeAdminId}/sales/daily` | 🗓️ Returns daily sales data points for line graph. |
    | `GET` | `/api/store/analytics/{storeAdminId}/sales/category` | 🧵 Shows sales distribution by product categories (bar/pie chart). |
    | `GET` | `/api/store/analytics/{storeAdminId}/sales/payment-method` | 💳 Displays sales grouped by payment methods like UPI, cash, card. |
    | `GET` | `/api/store/analytics/{storeAdminId}/sales/branch` | 🏬 Shows total sales per branch for the store admin. |
    | `GET` | `/api/store/analytics/{storeAdminId}/payments` | 💵 Detailed breakdown of payment types used in transactions. |
    | `GET` | `/api/store/analytics/{storeAdminId}/branch-performance` | 🏘️ Returns performance metrics (orders, revenue) for each branch. |
    | `GET` | `/api/store/analytics/{storeAdminId}/alerts` | ⚠️ get alerts or issues like low stock, inactive cashiers, etc. |
    
- Subscription Plan Api
    
    ## 📦 `SubscriptionPlan` Model – Field Descriptions
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Primary key for the plan. |
    | `name` | `String` | Name of the subscription plan (e.g., Starter, Pro). |
    | `description` | `String` | Brief summary of the plan's purpose or value. |
    | `price` | `Double` | Cost of the plan per billing cycle. |
    | `billingCycle` | `BillingCycle` | Enum indicating billing period (e.g., MONTHLY, YEARLY). |
    | `maxBranches` | `Integer` | Maximum number of branches allowed. |
    | `maxUsers` | `Integer` | Maximum number of staff or cashier accounts allowed. |
    | `maxProducts` | `Integer` | Maximum number of products user can create. |
    | `enableAdvancedReports` | `Boolean` | Access to detailed/advanced reporting features. |
    | `enableInventory` | `Boolean` | Enables inventory tracking features. |
    | `enableIntegrations` | `Boolean` | Allows integration with third-party apps. |
    | `enableEcommerce` | `Boolean` | Connects POS with online eCommerce platforms. |
    | `enableInvoiceBranding` | `Boolean` | Allows users to customize invoice templates. |
    | `prioritySupport` | `Boolean` | Priority access to support staff. |
    | `extraFeatures` | `List<String>` | List of optional feature flags. |
    | `enableMultiLocation` | `Boolean` | Allows managing multiple store locations. |
    | `createdAt` | `LocalDateTime` | Auto-populated timestamp when the plan is created. |
    | `updatedAt` | `LocalDateTime` | Auto-updated timestamp when the plan is modified. |
    
    ---
    
    ## 🔗 `SubscriptionPlanController` – API Endpoints
    
    | Method | Endpoint | Description |
    | --- | --- | --- |
    | `POST` | `/api/super-admin/subscription-plans` | ➕ Create a new subscription plan with pricing and features. |
    | `PUT` | `/api/super-admin/subscription-plans/{id}` | 🔄 Update an existing subscription plan using its ID. |
    | `GET` | `/api/super-admin/subscription-plans` | 📦 Retrieve a list of all subscription plans. |
    | `GET` | `/api/super-admin/subscription-plans/{id}` | 🔍 Fetch details of a specific subscription plan by ID. |
    | `DELETE` | `/api/super-admin/subscription-plans/{id}` | ❌ Delete a subscription plan by its ID. |
- Subscription Api
    
    ## 📘 **Subscription Model – `app.posify.modal.Subscription`**
    
    | Field Name | Type | Description |
    | --- | --- | --- |
    | `id` | `Long` | Unique identifier for the subscription. |
    | `store` | `Store` | The store associated with the subscription. |
    | `plan` | `SubscriptionPlan` | The plan to which the store is subscribed. |
    | `startDate` | `LocalDate` | Start date of the subscription. |
    | `endDate` | `LocalDate` | End date of the subscription. |
    | `status` | `SubscriptionStatus` | Current status: `TRIAL`, `ACTIVE`, `EXPIRED`, or `CANCELLED`. |
    | `paymentGateway` | `String` | Payment gateway used (e.g., `RAZORPAY`, `STRIPE`). |
    | `transactionId` | `String` | Identifier from the payment gateway for the transaction. |
    | `paymentStatus` | `PaymentStatus` | Status of payment: `PAID`, `PENDING`, `FAILED`, etc. |
    | `createdAt` | `LocalDateTime` | Timestamp when the subscription was created. |
    | `updatedAt` | `LocalDateTime` | Timestamp when the subscription was last updated. |
    
    ---
    
    ## 📡 **SubscriptionController – `app.posify.controller.SubscriptionController`**
    
    | Endpoint | HTTP Method | Description |
    | --- | --- | --- |
    | `/api/subscriptions/subscribe` | `POST` | 🆕 Create a new subscription for a store (trial or first-time plan). |
    | `/api/subscriptions/upgrade` | `POST` | 🔁 Upgrade a store's existing subscription to a new plan. |
    | `/api/subscriptions/{subscriptionId}/activate` | `PUT` | ✅ Admin activates a subscription (usually after payment). |
    | `/api/subscriptions/{subscriptionId}/cancel` | `PUT` | ❌ Admin cancels a subscription manually. |
    | `/api/subscriptions/{subscriptionId}/payment-status` | `PUT` | 💳 Update a subscription's payment status (manual override). |
    | `/api/subscriptions/store/{storeId}` | `GET` | 📦 Get all subscriptions for a specific store, optionally filtered by status. |
    | `/api/subscriptions/admin` | `GET` | 🗂️ Admin: Get all subscriptions in the system, with optional status filter. |
    | `/api/subscriptions/admin/expiring` | `GET` | ⌛ Get subscriptions that are expiring within a specified number of days. |
    | `/api/subscriptions/admin/count` | `GET` | 📊 Count total subscriptions grouped by a given status. |
- Payment Service
- Email Service
