## Getting Started

Welcome to the Othello Java AI project. This guide will help you get started with running the project and understanding its structure.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Visual Studio Code or any other Java IDE

## Folder Structure

The workspace contains the following folders:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Running the Project

1. **Clone the repository:**

   ```sh
   git clone https://github.com/yourusername/Othello-java-ai.git
   cd Othello-java-ai
   ```

2. **Open the project in your IDE:**

   - If using Visual Studio Code, open the folder.
   - If using another IDE, import the project as a Java project.

3. **Compile the project:**

   - In Visual Studio Code, use the built-in terminal to run:
     ```sh
     javac -d bin src/**/*.java
     ```

4. **Run the project:**
   - In Visual Studio Code, use the built-in terminal to run:
     ```sh
     java -cp bin Main
     ```

## Dependency Management

The `JAVA PROJECTS` view in Visual Studio Code allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Sample Videos

### AI-1 vs AI-2

https://github.com/user-attachments/assets/be3e973f-c89c-452d-bd90-6327abf07602

### AI-1 vs AI-3

https://github.com/user-attachments/assets/b3827f3c-4c94-4892-82f6-33883c176694

### AI-2 vs AI-3

https://github.com/user-attachments/assets/f7109eee-b37c-46a2-8c7b-19b72a46cc27

### Human vs AI-3

https://github.com/user-attachments/assets/51ec19db-1d40-4ba7-94fd-1f912b23a60a

## Game Instructions

- The game starts with a menu where you can select the type of players (Human or AI).
- Player 1 is always Black, and Player 2 is always White.
- Click on the board to make a move if you are playing as a Human.
- The game ends when neither player can make a valid move.

## AI Players

The project includes different AI players with varying strategies:

- **AI Player 1:** Basic evaluation based on the difference in scores.
- **AI Player 2:** Uses a weighted board evaluation.
- **AI Player 3:** Advanced evaluation using multiple heuristics (corners, stability, coin parity, mobility).

## License

This project is licensed under the MIT License.
