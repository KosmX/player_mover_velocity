# Player Mover Velocity

Player Mover Velocity is a Minecraft plugin designed to facilitate player movement across servers within a Velocity network using server-side commands. It enables seamless transportation of players between servers, allowing for efficient server management and gameplay experiences.

## Features

- Facilitates player movement across servers within a Velocity network.
- Compatible with server-side commands, enabling execution from datapacks and command blocks.
- Allows servers to send players to other servers seamlessly.

## Compatibility

Player Mover Velocity needs to be installed on both Velocity and the Minecraft server to function properly. Ensure that the correct JAR file is used for installation.

## Download

Downloadable files for Player Mover Velocity are available in the [Release tab](https://github.com/KosmX/player_mover_velocity/releases) of the GitHub repository.

## Build Instructions

To build Player Mover Velocity, follow these steps:

1. Clone the repository from [GitHub](https://github.com/KosmX/player_mover_velocity).
2. Navigate to the cloned directory.
3. Execute the following command using Gradle Wrapper (`gradlew`):

    ```sh
    chmod +x ./gradlew # on unix systems
    ./gradlew build
    ```

4. Once the build is successful, locate the output file in the `[Velocity - Paper - Fabric]/build/libs/` directory. This file is typically the largest file in the directory and will have a `.jar` extension.
