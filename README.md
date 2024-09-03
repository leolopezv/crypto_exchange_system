# Leonardo's Crypto Exchange System

![UML Diagram](https://github.com/user-attachments/assets/d6c9de8f-235d-44b0-b0e6-8d26a4ed7a1c)

## Overview

The UML diagram above describes the architecture of the **Crypto Exchange System** implemented in this repository. This system facilitates the trading of cryptocurrencies like Bitcoin and Ethereum, allowing users to place buy and sell orders, manage their digital wallets, and view transaction histories.

### Key Components

- **User**: 
  - Represents the individual using the system. A user can register, log in, and interact with the exchange to buy or sell cryptocurrencies. Each user is associated with a `Wallet` that holds their fiat and crypto balances.
  
- **Wallet**: 
  - Manages the user's financial assets, including both fiat currency and cryptocurrency balances. The wallet is updated based on transactions initiated by the user, reflecting deposits, and successful trades.

- **Exchange**: 
  - Acts as the central hub of the system. The `Exchange` is responsible for initializing the market with available cryptocurrencies, managing the market prices, and handling fluctuations in those prices.

- **Crypto**: 
  - Represents the cryptocurrencies available for trading within the system. Each instance of `Crypto` corresponds to a specific type of cryptocurrency, such as Bitcoin (BTC) or Ethereum (ETH), and tracks its market price.

- **Order**: 
  - An abstract class representing a user's intent to either buy or sell a specific amount of cryptocurrency at a certain price. `Order` is extended by concrete classes like `BuyOrder` and `SellOrder` to handle the specifics of each transaction type.

- **OrderBook**: 
  - The `OrderBook` is responsible for matching buy and sell orders placed by users. Once a match is found, the `OrderBook` executes the trade, updating the relevant `Wallet` balances and recording the transaction.

- **Transaction**: 
  - Stores the details of completed trades. Each `Transaction` records the specifics of the trade, including the type of cryptocurrency, amount, price, and whether it was a buy or sell order. Users can later review their transaction history to track their trading activities.

## MVC
### Why did I use Model-View-Controller pattern?
  - By dividing the application into three distinct components, each part of the system can focus on its specific responsibility. The **Model** manages the data and business logic (e.g., handling orders, transactions, and wallet balances). The **View** is responsible for presenting the data to the user, such as displaying menus and asking input to the user. The **Controller** handles user input and interacts with the Model to update the View accordingly.
  - It is important to mention that I furthermore expanded the architecture by implementing Services and Repositories. **Services** help the controllers do a better job and reducing their number of tasks, while **Repositories** securely store the obtained data in the memory, and may deliver them if needed.


## Design Patterns

### Singleton Pattern

- **Intent**: 
  - The Singleton pattern ensures that only one instance of the `Exchange` class exists throughout the system. This single instance is shared among all users, providing a consistent interface for managing market operations and price fluctuations.

### Factory Pattern

- **Intent**: 
  - The Factory pattern is employed in the `AppFactory` class to create and initialize the necessary components when booting up the system.

## SOLID Principles
- This Bootcamp taught me the first two SOLID principles, which have been very useful for me when trying to think on how to separate concerns and keep the application modular.

### Single Responsibility Principle (SRP)

- **Application**: 
  - The **Single Responsibility Principle** is adhered to by ensuring that each class in the system has only one reason to change. For example, the `UserController` class is solely responsible for handling user-related actions such as registration and login, while the `OrderController` manages the order processing logic. `WalletController` focuses on managing the financial status of each user and `PanelController` is connected to the views for displaying the menus.

### Open/Closed Principle (OCP)

- **Application**: 
  - The **Open/Closed Principle** is applied by designing classes and components in a way that they are open for extension but closed for modification. For instance, the `Order` class is an abstract class that can be extended by `BuyOrder` and `SellOrder` to introduce new behaviors without altering the existing codebase. This design allows the addition of new types of orders or cryptocurrencies without changing the core functionality of the system.
 
 
## How to Run the Project
- Follow these steps to run the project:

1. **Clone the project**
2. Open the main file containing the `main` function of the application.
3. Run the project. This will initialize the application and display the main menu.
4. Create an account and log in.
5. Deposit fiat money and buy fresh crypto from the `Exchange`.
6. Start placing a `BuyOrder` or `SellOrder`, which will then match when we find the right user for you.
7. Enjoy your time and come back later to see your history of successful transactions.

Enjoy trading crypto with more users with this application!
- **Note**: At least another account must also be created in the same execution, so two or more users can start trading between them.

## Author
- Leonardo Sebastian Lopez Vallejo 🧑‍🚀
  - Find more about me on my repository! `leolopezv/leolopez`

## Final note
- Special thanks to my Bootcamp teachers: Juan Manuel and Angel, for the incredible reasoning and coding knowledge they have shared along the course, and their support on my first steps into professional life 🚀
