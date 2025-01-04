
# ShortURL Service

This project is a URL shortening service built with Spring Boot. It provides endpoints to create, retrieve, update, and delete shortened URLs.

https://roadmap.sh/projects/url-shortening-service

## Endpoints

### Create a Shortened URL

- **URL:** `/shorten`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "url": "https://www.example.com/some/long/url"
  }

- **Response:**
  ```json
  {
    "id": 1,
    "url": "https://www.example.com/some/long/url",
    "shortCode": "QVR667",
    "createdAt": "2024-12-29T19:11:27.8634857",
    "updatedAt": "2024-12-29T19:11:27.8634857"
  }
  ```

### Retrieve a URL by Short Code

- **URL:** `/shorten?shortCode={shortCode}`
- **Method:** `GET`
- **Request Parameters:**
  - `shortCode` (required): The short code of the URL to retrieve.
- **Response:**
  ```json
  {
    "id": 1,
    "url": "https://www.example.com/some/long/url",
    "shortCode": "QVR667",
    "createdAt": "2024-12-29T19:11:27.8634857",
    "updatedAt": "2024-12-29T19:11:27.8634857"
  }
  ```
- **Error Response:**
  - Status: `404 Not Found`
  - Body: `"Short code not found"`

### Update a URL by Short Code

- **URL:** `/shorten/{shortCode}`
- **Method:** `PUT`
- **Request Parameters:**
  - `shortCode` (required): The short code of the URL to update.
- **Request Body:**
  ```json
  {
    "url": "https://www.example.com/updated/url"
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "url": "https://www.example.com/updated/url",
    "shortCode": "QVR667",
    "createdAt": "2024-12-29T19:11:27.8634857",
    "updatedAt": "2024-12-29T19:11:27.8634857"
  }
  ```
- **Error Response:**
  - Status: `404 Not Found`
  - Body: `"Short code not found"`

### Delete a URL by Short Code

- **URL:** `/shorten/{shortCode}`
- **Method:** `DELETE`
- **Request Parameters:**
  - `shortCode` (required): The short code of the URL to delete.
- **Response:**
  - Status: `204 No Content`
- **Error Response:**
  - Status: `404 Not Found`
  - Body: `"Short code not found"`

### Count URL by Short Code

- **URL:** `/shorten/count/{shortCode}`
- **Method:** `GET`
- **Request Parameters:**
  - `shortCode` (required): The short code of the URL to count.

- **Response:**
  ```json
  {
    "id": 1,
    "url": "https://www.example.com/some/long/url",
    "shortCode": "QVR667",
    "createdAt": "2024-12-29T19:11:27.8634857",
    "updatedAt": "2024-12-29T19:11:27.8634857",
    "count": 1
  }
  ```
- **Error Response:**
  - Status: `404 Not Found`
  - Body: `"Short code not found"`
