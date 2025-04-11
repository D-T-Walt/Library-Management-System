# Library Management System

A Java-based terminal application for managing a digital library system, designed for administrators and patrons.

## Table of Contents
- Introduction
- Features
  - Administrator Functionalities
  - Patron Functionalities
- Getting Started
- System Navigation
- Error Handling
- Exit Instructions
- Credits

---

## 📘 Introduction

This Library Management System is a terminal-based application developed as part of the CMP2006 Data Structures course at the University of Technology, Jamaica. It supports two main user types: administrators and patrons, each with customized access.

## 🔧 Features

### 👨‍💼 Administrator Functionalities

- Add, update, and delete book records
- View book details by ISBN, author, or title
- Track inventory and checkout statistics
- Manage patron records
- Promote patrons to administrators
- Change patron passwords
- Secure login with limited login attempts

### 📚 Patron Functionalities

- View book details (by ISBN, author, title)
- Check-out and return books
- View and manage current checkouts
- Change password
- Secure login with default password and password reset

## 🚀 Getting Started

Upon launching the system:
1. You’ll be prompted with a login screen.
2. Select your user role: Administrator or Patron.
3. Enter credentials (username/card number and password).

If it's your first time running the system, initial storage files will be created.

## 🧭 System Navigation

Menu navigation uses numeric options. Input must be valid integers. Error messages guide incorrect inputs.
To return to a previous menu, select the designated “Previous Menu” option.

## ⚠️ Error Handling

- Non-numeric input prompts a retry.
- Invalid login displays limited attempts warning.
- All data inputs are validated, including:
  - ISBN format
  - Book quantity
  - Patron details

## ❌ Exit Instructions

Users can safely log out or terminate the program from any main menu. On termination, all data is saved to local files.

## 🧑‍🤝‍🧑 Credits

Developed by:
- Diwani Walters
- Kemone Laws
- Olivia McFarlane
- Javone-Anthony Gordon

Instructor: Oral Robinson  
Faculty of Engineering and Computing  
School of Computing and Information Technology  
University of Technology, Jamaica
