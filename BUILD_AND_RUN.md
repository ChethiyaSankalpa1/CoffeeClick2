# 🚀 Quick Build & Run Guide

## Step-by-Step Instructions

### **1. Sync Gradle (IMPORTANT!)**

**In Android Studio:**
1. Click **File** → **Sync Project with Gradle Files**
2. Or click the 🐘 **Gradle Sync** icon in the toolbar
3. Wait for sync to complete (may take 1-2 minutes)

**Or via Command Line:**
```bash
cd "C:\Users\ACER\Downloads\CoffeeClick (3)\CoffeeClick"
.\gradlew.bat --refresh-dependencies
```

---

### **2. Clean & Rebuild**

**In Android Studio:**
1. Click **Build** → **Clean Project**
2. Wait for it to finish
3. Click **Build** → **Rebuild Project**

**Or via Command Line:**
```bash
.\gradlew.bat clean build
```

---

### **3. Run the App**

**In Android Studio:**
1. Connect your Android device OR start an emulator
2. Click the green ▶️ **Run** button
3. Select your device/emulator
4. Wait for app to install and launch

**Or via Command Line:**
```bash
.\gradlew.bat installDebug
```

---

## ⚡ Quick Test Flow

Once app is running:

1. **Click "Admin Login"**
   - Username: `admin`
   - Password: `admin123`

2. **Go to "User Approval"**
   - If no users, go back and register one first

3. **Register a User:**
   - Go back → "Get Started" → "Sign up"
   - Register with any details
   - Try to login → Will fail (not approved)

4. **Approve User as Admin:**
   - Admin Login → User Approval → Click "Approve"

5. **Login as User:**
   - Now login will work!
   - Browse coffee → Add to cart → Checkout

6. **Check Order History:**
   - Admin Login → Order History
   - See your order!

---

## 🐛 If You See Errors

### **Gradle Sync Failed:**
```bash
# Clear Gradle cache
.\gradlew.bat clean --refresh-dependencies
```

### **Build Failed:**
1. Check internet connection (downloads dependencies)
2. Make sure you have Java 11 or higher
3. Try: File → Invalidate Caches / Restart

### **App Crashes:**
1. Check LogCat in Android Studio
2. Look for red error messages
3. Most common: Missing layout file or activity not in manifest

---

## ✅ Success Indicators

You'll know it's working when:
- ✅ Gradle sync completes without errors
- ✅ Build succeeds (green checkmark)
- ✅ App launches on device/emulator
- ✅ Welcome screen shows with 2 buttons
- ✅ Admin login works
- ✅ User registration works

---

## 📱 Minimum Requirements

- **Android Studio**: Arctic Fox or newer
- **Android SDK**: API 21+ (Android 5.0+)
- **Java**: Version 11
- **Gradle**: 8.0+
- **Internet**: For downloading dependencies

---

## 🎯 All Features to Test

1. ✅ Welcome screen (2 buttons)
2. ✅ User registration
3. ✅ User login (with approval check)
4. ✅ Admin login
5. ✅ Admin dashboard (4 options)
6. ✅ User approval system
7. ✅ Coffee menu browsing
8. ✅ Add to cart
9. ✅ Cart view & management
10. ✅ Checkout
11. ✅ Order history (admin)
12. ✅ Manage items (admin)

---

**Ready to go! Just sync Gradle and run!** 🚀
