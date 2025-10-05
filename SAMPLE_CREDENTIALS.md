# 🔑 Sample User Credentials

## Pre-configured Accounts

Your app now comes with **2 pre-configured accounts** that are automatically created on first launch:

---

## 👨‍💼 Admin Account

### **Login Details:**
```
Username: admin
OR
Email: admin@gmail.com
Password: 123456
```

### **Capabilities:**
- ✅ Access Admin Panel
- ✅ Approve/Reject Users
- ✅ View All Orders
- ✅ Manage Coffee Items
- ✅ Full System Access

### **How to Login:**
1. Open app
2. Click **"Get Started"** button
3. Enter: `admin@gmail.com` or `admin`
4. Password: `123456`
5. ✅ Automatically redirects to Admin Dashboard

---

## 👤 Test User Account

### **Login Details:**
```
Username: test
OR
Email: test@gmail.com
Password: 123456
```

### **Status:**
- ✅ **Already Approved** (can login immediately)
- ✅ Regular user (not admin)
- ✅ Can shop and place orders

### **Capabilities:**
- ✅ Browse Coffee Menu
- ✅ Add Items to Cart
- ✅ Place Orders
- ✅ View Cart

### **How to Login:**
1. Open app
2. Click **"Get Started"**
3. Enter: `test@gmail.com` or `test`
4. Password: `123456`
5. Start shopping!

---

## 🎯 Quick Test Scenarios

### **Scenario 1: Login as Test User**
```
1. Open app → "Get Started"
2. Username: test@gmail.com
3. Password: 123456
4. ✅ Success! Browse coffee menu
```

### **Scenario 2: Login as Admin**
```
1. Open app → "Get Started"
2. Username: admin@gmail.com
3. Password: 123456
4. ✅ Success! Automatically goes to Admin Dashboard
```

### **Scenario 3: Register New User**
```
1. Open app → "Get Started" → "Sign up"
2. Register with new details
3. Try to login → ❌ "Account pending approval"
4. Login as admin → Approve user
5. Login with new user → ✅ Success!
```

---

## 📊 Account Summary

| Account | Username | Email | Password | Status | Type |
|---------|----------|-------|----------|--------|------|
| Admin | `admin` | `admin@gmail.com` | `123456` | ✅ Approved | Admin |
| Test User | `test` | `test@gmail.com` | `123456` | ✅ Approved | User |

---

## 🔐 Login Options

Both accounts support **dual login**:
- Login with **username** OR **email**
- Same password for both methods

### **Examples:**

**Admin Login:**
- `admin` + `123456` ✅
- `admin@gmail.com` + `123456` ✅

**Test User Login:**
- `test` + `123456` ✅
- `test@gmail.com` + `123456` ✅

---

## 🎨 Testing Workflow

### **Complete Test Flow (5 minutes):**

1. **Test User Login:**
   ```
   • Login as: test@gmail.com / 123456
   • Add Espresso x2 to cart
   • Add Latte x1 to cart
   • Go to cart
   • Checkout and place order
   ```

2. **Admin Check:**
   ```
   • Logout (go back to welcome)
   • Admin Login: admin@gmail.com / 123456
   • Click "Order History"
   • See test user's order!
   ```

3. **User Approval Test:**
   ```
   • Register new user
   • Try to login → Fails (not approved)
   • Admin Login → User Approval
   • Approve new user
   • Login with new user → Success!
   ```

---

## 🔄 Reset Sample Data

If you want to reset to default accounts:

1. **Uninstall the app** from device/emulator
2. **Reinstall** the app
3. Sample data will be recreated automatically

OR

**Clear app data:**
- Settings → Apps → CoffeeClick → Clear Data

---

## 🆕 Adding More Users

### **Option 1: Register via App**
- Use the registration screen
- Admin must approve before login

### **Option 2: Pre-approved Users**
- Register via app
- Admin approves immediately
- User can login

---

## 📝 Notes

- **Passwords are plain text** (for demo only)
- **Data stored locally** (SharedPreferences)
- **Not synced** across devices
- **Cleared on uninstall**

---

## ✅ Verification Checklist

Test that sample data works:

- [ ] Admin can login with `admin@gmail.com`
- [ ] Admin can login with `admin` username
- [ ] Test user can login with `test@gmail.com`
- [ ] Test user can login with `test` username
- [ ] Both use password `123456`
- [ ] Test user can shop and checkout
- [ ] Admin can see test user in User Approval
- [ ] Admin can see orders in Order History
- [ ] New users need approval
- [ ] Sample data persists after app restart

---

## 🎉 Ready to Use!

Your app now has:
- ✅ 1 Admin account (admin@gmail.com / 123456)
- ✅ 1 Test user account (test@gmail.com / 123456)
- ✅ Both pre-approved and ready to use
- ✅ No setup required - just login!

**Start testing immediately with these credentials!** 🚀

---

**Last Updated:** 2025-10-05 20:30 IST  
**Version:** 1.0.0 with Sample Data
