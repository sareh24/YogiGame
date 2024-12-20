🐻 Yogi Bear Adventure Game

Yogi Bear Adventure is a Java-based game where players help Yogi Bear collect picnic baskets in Yellowstone National Park. The game incorporates obstacles such as trees, mountains, and patrolling rangers to increase difficulty, while leveraging modern programming practices like Object-Oriented Programming (OOP) and SQL-based database management for storing and managing high scores.

✨ Key Features

Game Mechanics:

	1.	Objective:
	•	Collect all the picnic baskets on the level while avoiding obstacles and enemies.
	2.	Obstacles & Challenges:
	•	Rangers: Patrol the map (horizontal/vertical) and reduce Yogi’s lives if they get too close (one-unit proximity).
	•	Obstacles: Mountains and trees block paths, requiring strategic movement.
	3.	Lives System:
	•	Yogi starts with three lives.
	•	When a life is lost, Yogi respawns at the park entrance.
	4.	Level Progression:
	•	Automatically load or generate a new level upon collecting all baskets.
	5.	Game Over & High Scores:
    • If all lives are lost, a Game Over popup allows players to enter their name and save their score.
    • If the player successfully completes all 10 levels, the game ends.
    • A congratulatory window is displayed, allowing the player to enter their name and save their score to the database.
    • A high-score menu displays the top 10 players based on scores stored in an SQL database.
  
	6.	Menu Options:
	•	Restart Game: Reset progress and start over.
	•	View High Scores: Fetch and display rankings.

🎓 Technical Overview

    Object-Oriented Programming (OOP) Concepts:

    The project makes extensive use of OOP principles to create modular, reusable, and maintainable code:
	1.	Encapsulation:
	•	Separate classes for managing game elements, such as Player, Ranger, Obstacle, and Basket.
	•	Variables and methods are private or protected, with controlled access via getters and setters.
	2.	Polymorphism:
	•	Dynamic behavior of game objects implemented via method overriding (e.g., ranger movement).
	3.	Abstraction:
	•	Core logic such as movement, collision handling, and score calculation are abstracted into dedicated methods/classes to reduce complexity.

    SQL Integration:

    The game uses SQL for managing high scores:
	•	Tables:
	•	Players (ID, Name, Score)
	•	Functionality:
	•	Save high scores after each game.
	•	Fetch the top 10 scores for the leaderboard.

    Additional Technologies:

	•	GUI: Built using Java Swing for a responsive interface.
	•	Event Handling: For menu interaction and keyboard inputs.
