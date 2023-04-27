# Wishlist Service

The Wishlist Service is a RESTful HTTP backend service that provides functionality for managing wishlists in an e-commerce platform.

## Features

- Add a product into the Wishlist
- Remove a product from the Wishlist
- List all products in the Wishlist
- Check if a specific product is in the Wishlist

## Technologies Used

- Java 17
- Spring Boot
- Gradle
- MongoDB

## Getting Started

To run the Wishlist Service locally, follow these steps:

1. Ensure that you have Docker installed on your machine.
2. Start the MongoDB server using Docker Compose:

```bash 
docker compose up -d
```
3. Build and run the application using Gradle:
```bash 
./gralew bootRun
```
4. The application will start running on http://localhost:8080.

## API Endpoints

The following API endpoints are available:

- `GET /wishlist/{userId}`: Retrieve the wishlist for the specified user.
- `POST /wishlist/{userId}/items`: Add a product to the wishlist.
- `DELETE /wishlist/{userId}/items/{productId}`: Remove a product from the wishlist.
- `GET /wishlist/{userId}/items/{productId}`: Check if a product exists in the wishlist.

## Usage

Make HTTP requests to the appropriate endpoints using your preferred API testing tool (e.g., cURL, Postman).

Example:

- Retrieve wishlist for user "user123":
``` sql
GET http://localhost:8080/wishlist/user123
```

- Add a product to the wishlist for user "user123":

```
POST http://localhost:8080/wishlist/user123/items
Body: {
"productId": "product123"
}
```

- Remove a product with ID "product123" from the wishlist for user "user123":

```
DELETE http://localhost:8080/wishlist/user123/items/product123
```