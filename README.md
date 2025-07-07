# Connect-Hub

**Connect-Hub** is a lightweight Java-based social media platform designed to simulate core features of modern social networking applications. This project showcases user authentication, post/story creation, friend management, and a dynamic news feed.

---

## 🚀 Features

- 🔐 User Authentication (Login & Signup)
- 🧑‍🤝‍🧑 Friend Requests & Friend List Management
- 📰 News Feed with Posts and Stories
- ✍️ Create Posts & Share Stories
- 👤 Profile Management
- 🔒 Password Hashing for secure storage
- 💾 Simulated Backend using JSON files

---

## 📁 Project Structure

 ```
Connect-Hub/
├── src/ # Java source code
│ ├── LoginWindow.java # Login GUI
│ ├── SignUpWindow.java # Signup GUI
│ ├── NewsFeedWindow.java # News Feed GUI
│ ├── FriendManagement.java # Friend system logic
│ ├── Posts.java # Post data handling
│ ├── Stories.java # Story data handling
│ └── ... # Other supporting classes
├── users.json # User data
├── posts.json # Posts data
├── stories.json # Stories data
├── .gitignore # Git ignore file
└── Connect-Hub.iml # IntelliJ project file
```

## 🛠️ Technologies Used

- **Java SE 8+**
- **Swing** for GUI
- **JSON** for data storage
- **IntelliJ IDEA** (Recommended IDE)

---

## 💡 How to Run

1. Clone the repository or download the ZIP.
2. Open the project in [IntelliJ IDEA](https://www.jetbrains.com/idea/).
3. Navigate to `src/Main.java`.
4. Run the `Main` class.

> 📌 Note: The application uses local `.json` files as a mock database, so no external DB setup is needed.

---

