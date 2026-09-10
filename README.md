# RentFlow Mobile (Tenant App)

RentFlow is a comprehensive Rent, Utility, and Tenant Management System designed for independent landlords. This repository contains the **Tenant-facing Android Mobile Application**, built as part of a Final Year Project for a Diploma in Information Technology.

## 🚀 Overview

The RentFlow Mobile App serves as the primary interface for tenants to manage their tenancy. It is designed to provide transparency and ease of use, focusing on automatic rent tracking via M-Pesa.

### Key Features
- **Real-time Balance Tracking:** Instantly see your outstanding rent and utility balances.
- **M-Pesa Automation:** View your unique M-Pesa account number (e.g., `KIRA-A1`) for easy payments.
- **Itemized Billing:** Access detailed bills for rent and utilities (water/electricity).
- **Payment History:** A complete audit trail of all transactions processed via M-Pesa.
- **Instant Notifications:** Get alerted for due dates, overdue bills, and payment confirmations.

## 🛠 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Networking:** [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- **Authentication:** [Firebase Authentication](https://firebase.google.com/docs/auth)
- **UI Components:** Material Design 3, ViewBinding, Jetpack Navigation
- **Concurrency:** Kotlin Coroutines & StateFlow

## ⚙️ Configuration & Setup

### 1. Prerequisites
- Android Studio Jellyfish or newer.
- JDK 17+.
- A Firebase project.

### 2. API Configuration
The app consumes a REST API. The base URL is configured in `com.rentflow.util.Constants`.
- **Development (Emulator):** `http://10.0.2.2:3000/api/v1/`
- **Production:** Update `BASE_URL` to your hosted backend URL.

### 3. Firebase Integration
1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Add a new Android App with the package name `com.rentflow`.
3. Download the `google-services.json` file.
4. Place it in the `app/` directory of this project.
5. Enable **Email/Password** sign-in in the Firebase Auth settings.

### 4. Build Instructions
1. Clone the repository.
2. Open in Android Studio.
3. Sync the project with Gradle files.
4. Build and run on an emulator or physical device.

## 📂 Project Structure

```text
com.rentflow/
├── network/      # Retrofit API definitions and clients
├── repository/   # Data source management (Single Source of Truth)
├── viewmodel/    # Business logic and UI state management
├── model/        # Data classes (Tenant, Bill, Payment, etc.)
├── ui/           # Activities, Fragments, and Adapters
└── util/         # Constants, Helpers, and Session Management
```

## ⚖️ License

This project is part of an academic final year project. All rights reserved by the author (Ehjops).
