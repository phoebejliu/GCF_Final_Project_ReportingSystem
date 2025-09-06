# PB Client Reporting Subscription System

你好～我现在是 FX Prime Brokerage 团队的技术培训生，
我参加的 Java Bootcamp 要求提交一个 Final Project，
我决定选一个和我们组业务契合的课题：

---

✅ **项目题目**：PB Client Reporting Subscription System

---

## 📌 目标

构建一个 Web 系统用于：
- 管理所有 PB 客户的 报告订阅情况
- 每个客户可以订阅多个报告
- 每个报告可以设置：
  - 报告类型（如 Trade Confirm、Options Expiry）
  - 频率（如 Daily, Weekly）
  - 格式（PDF, CSV）
  - 发送方式（Email, UI, FTP）

---

## 📌 技术要求

该项目需要使用：
- Spring Boot
- JPA / Hibernate
- Thymeleaf（或 REST API + Postman）
- Entity 关系设计（OneToMany, ManyToOne）
- 控制器、表单页面、保存展示功能
- Swagger 文档（Bonus）
- 简单的验证、异常处理（Bonus）

---

## 项目结构（建议）

```
pb-client-reporting-subscription
├─ pom.xml
├─ src/main/java/com/phoebe/pbsub
│  ├─ PbSubApplication.java
│  ├─ config
│  │  └─ OpenApiConfig.java
│  ├─ domain
│  │  ├─ Client.java
│  │  ├─ ReportSubscription.java
│  │  ├─ enums
│  │  │  ├─ ReportType.java
│  │  │  ├─ Frequency.java
│  │  │  ├─ ReportFormat.java
│  │  │  └─ DeliveryMethod.java
│  ├─ repo
│  │  ├─ ClientRepository.java
│  │  └─ ReportSubscriptionRepository.java
│  ├─ service
│  │  ├─ ClientService.java
│  │  └─ ReportSubscriptionService.java
│  ├─ web
│  │  ├─ ClientController.java        // Thymeleaf 页面
│  │  ├─ SubscriptionController.java  // Thymeleaf 页面
│  │  └─ api
│  │     ├─ ClientApi.java            // REST + Swagger
│  │     └─ SubscriptionApi.java
│  └─ webadvice
│     └─ GlobalExceptionHandler.java
├─ src/main/resources
│  ├─ application.yml
│  └─ templates
│     ├─ clients.html
│     ├─ client-form.html
│     ├─ client-detail.html
│     └─ subscription-form.html
└─ src/test/java/... (可后补)
```

---

## POM（Spring Boot 3.5.x / JDK 17）

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.4</version>
  </parent>

  <groupId>com.phoebe</groupId>
  <artifactId>pb-client-reporting-subscription</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <properties>
    <java.version>17</java.version>
  </properties>

  <dependencies>
    <!-- Web + Thymeleaf + JPA + Validation -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- H2 便于演示 -->
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>

    <!-- Swagger / OpenAPI (springdoc) -->
    <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.6.0</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
</project>
```

---

## application.yml（H2 + JPA）

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:pbsub;DB_CLOSE_DELAY=-1;MODE=LEGACY
    driverClassName: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate.format_sql: true
  thymeleaf:
    cache: false

# 访问 H2 控制台: http://localhost:8080/h2-console
spring.h2.console.enabled: true
spring.h2.console.path: /h2-console
```

---

## 枚举（业务维度）

```java
public enum ReportType { TRADE_CONFIRM, OPTIONS_EXPIRY, MARGIN_CALL, STATEMENT, DAILY_PNL }
public enum Frequency { DAILY, WEEKLY, MONTHLY }
public enum ReportFormat { PDF, CSV }
public enum DeliveryMethod { EMAIL, UI, FTP }
```

---

## 实体（JPA / Hibernate）

### Client.java

```java
@Entity
public class Client {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @Email
  private String primaryContactEmail;

  private String ftpPath;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ReportSubscription> subscriptions = new ArrayList<>();
}
```

### ReportSubscription.java

```java
@Entity
public class ReportSubscription {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Client client;

  @NotNull @Enumerated(EnumType.STRING)
  private ReportType reportType;

  @NotNull @Enumerated(EnumType.STRING)
  private Frequency frequency;

  @NotNull @Enumerated(EnumType.STRING)
  private ReportFormat format;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<DeliveryMethod> deliveryMethods;

  private String overrideEmail;
  private String overrideFtpPath;
  private boolean active = true;
}
```

---

## Repository

```java
public interface ClientRepository extends JpaRepository<Client, Long> { }
public interface ReportSubscriptionRepository extends JpaRepository<ReportSubscription, Long> {
  List<ReportSubscription> findByClientId(Long clientId);
}
```

---

## Service

### ClientService

```java
@Service
public class ClientService {
  private final ClientRepository repo;
  public ClientService(ClientRepository repo) { this.repo = repo; }

  public List<Client> findAll() { return repo.findAll(); }
  public Client get(Long id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Client not found")); }
  public Client save(Client c) { return repo.save(c); }
  public void delete(Long id) { repo.deleteById(id); }
}
```

### ReportSubscriptionService

```java
@Service
public class ReportSubscriptionService {
  private final ReportSubscriptionRepository repo;
  public ReportSubscriptionService(ReportSubscriptionRepository repo) { this.repo = repo; }

  public List<ReportSubscription> byClient(Long clientId) { return repo.findByClientId(clientId); }
  public ReportSubscription get(Long id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Subscription not found")); }
  public ReportSubscription save(ReportSubscription s) { return repo.save(s); }
  public void delete(Long id) { repo.deleteById(id); }
}
```

---

## Controller (Thymeleaf 示例)

### ClientController

```java
@Controller
@RequestMapping("/clients")
public class ClientController {
  @GetMapping
  public String list(Model model) { ... }
  @GetMapping("/new")
  public String createForm(Model model) { ... }
  @PostMapping
  public String create(@Valid @ModelAttribute("client") Client client, BindingResult result) { ... }
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) { ... }
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id) { ... }
}
```

### SubscriptionController

```java
@Controller
@RequestMapping("/clients/{clientId}/subs")
public class SubscriptionController {
  @GetMapping("/new")
  public String createForm(@PathVariable Long clientId, Model model) { ... }
  @PostMapping
  public String create(@PathVariable Long clientId, @Valid @ModelAttribute("sub") ReportSubscription sub, BindingResult result, Model model) { ... }
  @PostMapping("/{subId}/delete")
  public String delete(@PathVariable Long clientId, @PathVariable Long subId) { ... }
}
```

---

## REST + Swagger

访问 Swagger UI: `http://localhost:8080/swagger-ui.html`

示例接口：
- `GET /api/clients`
- `POST /api/clients`
- `GET /api/subscriptions/by-client/{id}`

---

## 全局异常（简单版）

```java
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  public String handleIllegalArg(IllegalArgumentException ex, Model model) {
    model.addAttribute("message", ex.getMessage());
    return "error";
  }
}
```

---

## 页面模板（Thymeleaf 雏形）

### clients.html

```html
<h1>PB Clients</h1>
<a th:href="@{/clients/new}">+ New Client</a>
<table>
  <tr><th>ID</th><th>Name</th><th>Email</th><th>Actions</th></tr>
  <tr th:each="c: ${clients}">
    <td th:text="${c.id}"></td>
    <td><a th:href="@{|/clients/${c.id}|}" th:text="${c.name}"></a></td>
    <td th:text="${c.primaryContactEmail}"></td>
    <td>
      <form th:action="@{|/clients/${c.id}/delete|}" method="post">
        <button>Delete</button>
      </form>
    </td>
  </tr>
</table>
```

---

## ER 图（ASCII）

```
+------------------+            1        * +-------------------------+
|      Client      |---------------------->|   ReportSubscription    |
+------------------+                       +-------------------------+
| id (PK)          |                       | id (PK)                 |
| name             |                       | client_id (FK -> Client)|
| primaryEmail     |                       | reportType (enum)       |
| ftpPath          |                       | frequency (enum)        |
+------------------+                       | format (enum)           |
                                          | deliveryMethods (set)   |
                                          | overrideEmail           |
                                          | overrideFtpPath         |
                                          | active (bool)           |
                                          +-------------------------+
```

---

## 页面结构 & 用户流

1. Clients 列表 (`/clients`)  
   → 点击进入 Client Detail  

2. Client Detail (`/clients/{id}`)  
   → 展示客户及订阅列表  
   → 可新增订阅  

3. Subscription Form  
   → 选择 ReportType / Frequency / Format / DeliveryMethod  

4. REST API + Swagger 文档演示
