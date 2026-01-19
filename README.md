

---

# Minimal E-Commerce Backend API  
**Spring Boot + MongoDB + Mock Payment Service**

---

##  Project Overview

This project implements a **minimal e-commerce backend system** using **Spring Boot** and **MongoDB**.  
It supports core e-commerce workflows such as:

- Product listing
- Cart management
- Order creation
- Payment processing using a **Mock Payment Service**
- Asynchronous webhook-based order status updates

The system demonstrates **REST API design**, **business logic implementation**, **database modeling**, and **webhook-based asynchronous workflows**, similar to real-world payment gateways.

---

## Key Features

- Create and list products
- Add items to cart
- View and clear cart
- Create orders from cart
- Initiate mock payments
- Automatic webhook callback after payment
- Order status updates after payment
- Fully testable using Postman

---

## Architecture Overview

```

Client (Postman)
|
v
E-Commerce API (Spring Boot :8080)
|
|-- Product APIs
|-- Cart APIs
|-- Order APIs
|-- Payment APIs
|
v
Mock Payment Processor
|
v
Webhook Endpoint (/api/webhooks/payment)

```

### Payment Design
- Payment is simulated using a **mock service**
- After payment creation, a background process waits for 3 seconds
- A webhook callback is triggered automatically
- Order and payment statuses are updated asynchronously

---

## Tech Stack

| Layer | Technology |
|-----|-----------|
Backend | Spring Boot 3.x |
Database | MongoDB |
ORM | Spring Data MongoDB |
Build Tool | Maven |
HTTP Client | RestTemplate |
Async Processing | Java Threads |
Testing | Postman |

---

## Project Structure

```

com.example.ecommerce
│
├── controller
│   ├── ProductController.java
│   ├── CartController.java
│   ├── OrderController.java
│   └── PaymentController.java
│
├── service
│   ├── ProductService.java
│   ├── CartService.java
│   ├── OrderService.java
│   └── PaymentService.java
│
├── repository
│   ├── ProductRepository.java
│   ├── CartRepository.java
│   ├── OrderRepository.java
│   ├── OrderItemRepository.java
│   └── PaymentRepository.java
│
├── model
│   ├── User.java
│   ├── Product.java
│   ├── CartItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   └── Payment.java
│
├── dto
│   ├── AddToCartRequest.java
│   ├── CreateOrderRequest.java
│   ├── PaymentRequest.java
│   └── PaymentWebhookRequest.java
│
├── webhook
│   └── PaymentWebhookController.java
│
├── config
│   └── RestTemplateConfig.java
│
└── EcommerceApplication.java

```

---

## Database Design (MongoDB)

### Collections
- `products`
- `cart_items`
- `orders`
- `order_items`
- `payments`

### Relationships
- User → CartItems (1:N)
- User → Orders (1:N)
- Order → OrderItems (1:N)
- Order → Payment (1:1)
- Product → CartItems (1:N)

All IDs are stored as **UUID strings**.

---

## API Endpoints

### Product APIs

#### Create Product
```

POST /api/products

````

```json
{
  "name": "Laptop",
  "description": "Gaming Laptop",
  "price": 50000,
  "stock": 10
}
````

#### Get All Products

```
GET /api/products
```

---

### Cart APIs

#### Add Item to Cart

```
POST /api/cart/add
```

```json
{
  "userId": "user123",
  "productId": "prod123",
  "quantity": 2
}
```

#### View Cart

```
GET /api/cart/{userId}
```

#### Clear Cart

```
DELETE /api/cart/{userId}/clear
```

---

### Order APIs

#### Create Order from Cart

```
POST /api/orders
```

```json
{
  "userId": "user123"
}
```

#### Get Order Details

```
GET /api/orders/{orderId}
```

---

### Payment APIs (Mock Payment)

#### Create Payment

```
POST /api/payments/create
```

```json
{
  "orderId": "order123",
  "amount": 100000
}
```

#### Payment Webhook (Internal)

```
POST /api/webhooks/payment
```

This endpoint is automatically triggered by the mock payment service.

---

## Complete Order Flow

1. Create products
2. Add items to cart
3. View cart
4. Create order
5. Initiate payment
6. Mock payment processes asynchronously
7. Webhook updates payment and order status
8. Order status becomes `PAID`

---

## Testing with Postman

### Recommended Test Order

1. `POST /api/products`
2. `GET /api/products`
3. `POST /api/cart/add`
4. `GET /api/cart/{userId}`
5. `POST /api/orders`
6. `POST /api/payments/create`
7. Wait 3 seconds
8. `GET /api/orders/{orderId}`

Use Postman variables:

* `productId`
* `orderId`
* `userId`

---

## Configuration

### application.yml

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017
      database: ecommerce_db

server:
  port: 8080
```

---

##  Key Concepts Demonstrated

* RESTful API design
* Service-layer business logic
* MongoDB schema modeling
* Stock management
* Async payment processing
* Webhook-based order status updates



##  Assignment Coverage

| Requirement         | Status   |
| ------------------- | -------- |
| Product APIs        | ✅        |
| Cart APIs           | ✅        |
| Order APIs          | ✅        |
| Payment Integration | ✅ (Mock) |
| Webhook Handling    | ✅        |
| Async Processing    | ✅        |
| Postman Testing     | ✅        |
| Clean Architecture  | ✅        |

---

##  Notes

* Mock payment service simulates real payment gateways
* Webhook pattern mirrors Razorpay / Stripe behavior
* No authentication implemented (userId passed explicitly)
* Designed for evaluation and demo readiness

---

## 🏁 Conclusion

This project delivers a complete and minimal e-commerce backend demonstrating real-world backend concepts such as cart-to-order conversion, payment workflows, and asynchronous webhook handling.

```
