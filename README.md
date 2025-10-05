# Overview

**CoffeeClick** is a full-stack Android mobile application developed using **Java, XML, and Android Studio**, designed to provide a seamless coffee ordering experience. It features **user authentication, real-time cart management, admin controls, and persistent data storage**.  

This project highlights my skills in **mobile app development, database management, UI/UX design, and implementing complex business logic**.

# Key Features

## 🔐 User Features

- ✨ **User Registration & Authentication** – Secure signup, login with MongoDB integration and session handling  
- 🛒 **Smart Shopping Cart** – Add items, adjust quantities with +/- buttons, real-time total calculation  
- 📱 **Beautiful Card-Based UI** – Modern grid layout with coffee cards showing images, ratings, categories, and descriptions  
- 🔍 **Advanced Search & Filter** – Search by name/description and filter by categories (Hot, Cold, Dessert, Special)  
- ⭐ **Product Details Page** – Full coffee details with large images, ratings, and add-to-cart functionality  
- 💳 **Checkout System** – Complete order placement with confirmation dialogs  
- 📦 **Order History** – View past orders and track purchases  
- 👤 **User Profile** – Manage account details with secure logout  
- 🤖 **AI Chatbot** – Interactive customer support  

## 👨‍💼 Admin Features

- 📊 **Admin Dashboard** – Centralized control panel for managing the entire system  
- ➕ **Add Coffee Items** – Create new products with name, price, description, category, rating, and image selection  
- 🗑️ **Delete Items** – Remove products with confirmation dialogs (separate view page)  
- 📋 **View All Items** – Dedicated page showing all 12+ items in scrollable grid with delete buttons  
- 👥 **User Approval System** – Review and approve new user registrations  
- 📈 **Order Management** – View and manage all customer orders  
- 💾 **Persistent Storage** – Admin-added items saved using SharedPreferences with Gson serialization  

# Why This Project Stands Out

## 🎯 Technical Excellence

- 👨‍💻 Built from scratch using Java, XML, and Android SDK  
- 🗄️ Dual Database Approach – MongoDB for users/orders + SharedPreferences for coffee items  
- ⚡ Real-time Updates – Cart badge updates, category filtering, and dynamic UI changes  
- 🔄 CRUD Operations – Full Create, Read, Update, Delete functionality for coffee items  
- 🎨 Material Design – MaterialButton, CardView, ShapeableImageView, BottomAppBar  
- 📦 Modular Architecture – Separate adapters (CoffeeAdapter, CartAdapter, CategoryAdapter)  

## 💡 Advanced Features

- 🔄 **State Management** – Singleton CartManager for global cart state  
- 💾 **Data Persistence** – Items survive app restarts using SharedPreferences + JSON  
- 🎭 **Admin Mode Toggle** – Same adapter shows different UI for admin vs user  
- 🔍 **Smart Filtering** – Category filter with visual feedback and toast messages  
- 📱 **Responsive Design** – Grid layout (2 columns) with scrollable RecyclerViews  
- 🎯 **Navigation System** – Bottom navigation bar with 5 sections (Explore, Cart, Chatbot, Orders, Profile)  
