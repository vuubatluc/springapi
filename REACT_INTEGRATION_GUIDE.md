# Spring API - React Integration Guide

## Cấu hình đã được thực hiện

### 1. WebConfig.java
- Cấu hình CORS cho phép kết nối từ React frontend
- Hỗ trợ các port phổ biến: 3000 (Create React App), 5173 (Vite)
- Cấu hình static resource handlers

### 2. SecurityConfig.java
- Đã thêm CORS configuration source
- Cập nhật security filter chain để enable CORS
- Thêm public endpoints cho testing

### 3. application.yml
- Thêm cấu hình server port: 8080
- Context path: /api
- Static resources configuration
- Logging configuration

### 4. HomeController.java
- Endpoint `/api-test` để test kết nối với React
- Endpoint `/health` để health check
- CORS annotation cho các endpoint cụ thể

## Cách sử dụng với React

### 1. Base URL
```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

### 2. Test Connection
```javascript
// Test kết nối
fetch(`${API_BASE_URL}/api-test`)
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));

// Health check
fetch(`${API_BASE_URL}/health`)
  .then(response => response.json())
  .then(data => console.log('Health:', data));
```

### 3. Authentication với JWT
```javascript
// Login
const login = async (credentials) => {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(credentials)
  });
  return response.json();
};

// Sử dụng token
const fetchWithAuth = async (url, token) => {
  const response = await fetch(url, {
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
  return response.json();
};
```

### 4. CRUD Operations
```javascript
// Get users
const getUsers = async (token) => {
  return fetchWithAuth(`${API_BASE_URL}/users`, token);
};

// Create user
const createUser = async (userData, token) => {
  const response = await fetch(`${API_BASE_URL}/users`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(userData)
  });
  return response.json();
};
```

## Cấu hình React (axios example)

### Install axios
```bash
npm install axios
```

### Create API service
```javascript
// services/api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Add auth token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
```

### Use in React components
```javascript
// components/UserList.js
import React, { useEffect, useState } from 'react';
import api from '../services/api';

const UserList = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await api.get('/users');
        setUsers(response.data.result);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    fetchUsers();
  }, []);

  return (
    <div>
      <h2>Users</h2>
      {users.map(user => (
        <div key={user.id}>
          <p>{user.firstName} {user.lastName}</p>
        </div>
      ))}
    </div>
  );
};

export default UserList;
```

## Các endpoints có sẵn

### Public endpoints
- `GET /home` - Hello world
- `GET /api-test` - Test API connection  
- `GET /health` - Health check
- `POST /users` - Create user (registration)
- `POST /auth/login` - Login
- `POST /auth/introspect` - Verify token

### Protected endpoints
- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user
- `GET /users/myinfo` - Get current user info

## Lưu ý
1. Đảm bảo MySQL đang chạy trên port 3306
2. Đảm bảo Redis đang chạy trên port 6379
3. Cập nhật domain production trong WebConfig và SecurityConfig
4. Token JWT có thể cần refresh, implement refresh token logic nếu cần
