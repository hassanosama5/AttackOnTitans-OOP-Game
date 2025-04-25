# AttackOnTitans-OOP-Game

## Description
**Attack on Titan: Utopia** is a single-player endless tower defense game developed using **Java 17** and **JavaFX**. The game leverages **Object-Oriented Programming (OOP)** principles, including **Encapsulation**, **Inheritance**, **Polymorphism**, and **Abstraction**. It was created as part of the **Programming II (2485)** course at **GIU**.

In the game, players must defend the **Utopia District** from waves of titans using a variety of weapons. The game offers strategic lane-based combat, where players need to manage resources, purchase weapons, and strategically deploy them to stop the titans. 

The project demonstrates key software engineering concepts through:
- **Resource Management**: Players must manage in-game resources to purchase and upgrade weapons.
- **Weapon Purchasing and Deployment**: Players can strategically place weapons on the battlefield to defend their district.
- **Strategic Lane Defense**: Each wave brings more challenging titans, requiring smart weapon placement and strategic decisions.

![Screen 1](./screen%201.png)



### Technical Implementation:
- **Java 17 & JavaFX**: The game is built using Java 17 and **JavaFX** for the graphical user interface, providing a seamless, interactive gameplay experience.
  
- **Modular Architecture**: The game follows a modular approach, separating key components for better organization and scalability:
  - **Titan, Weapon, WallPart, and Lane** classes: These manage the core game logic and data state, such as weapon behavior, titan movements, and lane setups.
  - **BattleManager**: Handles the turn-based game flow, titan spawning, and battle phase transitions.
  - **GameUIController**: Manages UI updates, integrates with SceneBuilder for UI layout design, and processes player actions.
  
- **SceneBuilder**: Used to design the game’s user interface in a modular, easy-to-manage way with **FXML**, making it straightforward to adjust the layout without directly modifying Java code.

- **Custom Enums**: The game uses custom enums to clearly manage and control:
  - Game phases (e.g., setup, battle, and victory)
  - Weapon and titan types
  - Lane statuses and states
  
- **OOP Principles**: Best practices are followed throughout the game:
  - **Encapsulation** of weapon behaviors using strategy-like method overrides for different attack styles.
  - Reusable logic for **titan movement**, **targeting**, and **combat resolution**.
  - Observable properties keep the **game state synchronized** with the UI, ensuring a smooth and interactive experience for the player.

- **Dynamic Turn Engine**: The game engine dynamically handles various in-game mechanics:
  - **Titan Movement**: Titans move through the lanes in each turn.
  - **Weapon and Titan Attacks**: Weapons and titans take turns attacking one another.
  - **Lane Danger Level**: Calculates and updates the threat level of each lane in real-time.
  - **Titan Spawning**: Titans are spawned based on game phases and lane balance.

## Installation

To run **Attack on Titan: Utopia** locally, follow the steps below:

### Prerequisites:
- Java 17 or higher
- JavaFX (for the graphical user interface)

### Steps:
1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/AttackOnTitans-OOP-Game.git