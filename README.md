📱 Android Modularized App

A clean architecture Android project structured with modular layers: app, domain, and data. Built using modern development tools like Jetpack Compose, MVVM, Navigation Component, and Koin for dependency injection.
________________________________________________________________________________________________________________________________________________________________________________________________________________________



🧩 Module Structure

🟦 app Module
Responsible for the presentation layer.
- Built with Jetpack Compose for a modern UI experience
- Follows MVVM architecture pattern
- Uses ViewModel to manage UI state
- Navigation handled using Navigation Graph Component
- Dependency Injection via Koin
- Simulates loading by introducing a deliberate delay in data fetching
________________________________________________________________________________________________________________________________________________




🟨 domain Module
- The core business logic layer.
- Contains Use Cases
- Defines Data Models
- Provides Repository Interfaces
________________________________________________________________________________________________________________________________________________



🟥 data Module
- Defines Entities and implements Repositories
- Fetches data from a local JSON file in the assets/ directory
- Uses Mappers to convert between data and domain models
- Implements a Trie-based strategy for fast search functionality


________________________________________________________________________________________________________________________________________________

🚀 Features

🧱 Clean modular structure: app, domain, data

🖼️ Modern UI using Jetpack Compose

🔍 Fast and efficient search using Trie algorithm

📂 Offline data handling using local assets

⏳ Simulated delay to mimic real loading scenarios

💉 Lightweight dependency injection with Koin

🛠️ Tech Stack

