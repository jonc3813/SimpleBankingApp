Simple Banking App

A simple banking application that allows users to log in, sign up, manage their balance, and view their transaction history. Users can deposit and withdraw funds, and the app will store transaction records with timestamps.
Features

    User Authentication: Users can sign up with a username and password, and log in to access the app.

    Initial Balance Setup: During sign-up, users can set their initial balance.

    Deposit and Withdrawal: Users can deposit and withdraw funds, with transaction records being stored.

    Transaction History: The app maintains a history of transactions with timestamps for each transaction.

Technologies Used

    Kotlin: The app is written in Kotlin, making it easy to read and maintain.

    SharedPreferences: User data, including login credentials and balance information, are stored securely using SharedPreferences.

    Android SDK: Native Android development using the Android SDK.

Setup Instructions
Prerequisites

    Android Studio (latest version)

    Android Emulator or a physical device for testing

Installation

    Clone the Repository:

    git clone https://github.com/yourusername/simple-banking-app.git

    Open the Project in Android Studio:

        Open Android Studio.

        Click on Open an existing project and select the folder where you cloned the repository.

    Build and Run the App:

        Click on the Run button (green triangle) in Android Studio.

        Choose the device or emulator where you'd like to test the app.

        The app will launch on the chosen device or emulator.

App Flow

    Login:

        On the first launch, users can sign up by providing a username and password.

        After signing up, they will be redirected to a page to fill in the initial balance of the account.

    Main Activity:

        After logging in, users can see their current balance and transaction history.

        They can deposit or withdraw funds, and the transaction will be recorded with a timestamp.


    Transaction History:

        Each deposit and withdrawal is recorded with the timestamp and saved to SharedPreferences.

        Users can view their transaction history, including the date and amount.
