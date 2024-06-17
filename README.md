# UDP File Transfer and Group Chat Application

## Overview

This project is a dual-purpose application implemented in Java. It allows multiple clients to connect to a server for file transfers using the UDP protocol and also provides a group chat feature. The application consists of three main components:

1. **Server:** Handles incoming client connections and manages file transfers.
2. **Client:** Allows users to connect to the server and transfer files.
3. **GroupChat:** Manages the overall group chat logic and user interactions.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- An IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VS Code)

## Getting Started

### Setup

1. **Clone the repository:**

    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Compile the Java files:**

    ```sh
    javac Server.java Client.java GroupChat.java
    ```

### Running the Server

1. **Start the server:**

    ```sh
    java Server
    ```

   The server will start listening for incoming UDP client connections on a specified port.

### Running the Client

1. **Start the client:**

    ```sh
    java Client
    ```

2. **Enter the server's IP address and port number:**

   When prompted, enter the server's IP address and the port number the server is listening on.

3. **Enter your username:**

   Choose a username to identify yourself in the chat.

### Using the Group Chat

- Once connected, you can start sending messages.
- All messages will be broadcasted to all connected clients.
- Type `exit` to leave the chat.

### Using the File Transfer

- You can transfer files between the client and the server using the UDP protocol.
- Specify the file you want to transfer when prompted.

## File Descriptions

### Server.java

This file contains the implementation of the server component. The server listens for incoming client connections and handles file transfers using UDP.

### Client.java

This file contains the implementation of the client component. The client connects to the server and allows the user to transfer files using UDP.

### GroupChat.java

This file contains the main logic for managing the group chat, including handling user inputs and displaying messages.

## Features

- Multiple clients can connect to the server.
- Real-time message broadcasting to all connected clients.
- File transfer using UDP protocol.
- Simple and intuitive command-line interface.

## Limitations

- The current implementation does not support private messaging.
- The application does not handle message encryption or security features.
- The server does not support persistent storage of chat history.
- UDP does not guarantee the order and delivery of packets, which may affect file transfer reliability.

## Future Enhancements

- Add support for private messaging between clients.
- Implement message encryption for secure communication.
- Add a user authentication mechanism.
- Store chat history on the server for later retrieval.
- Improve file transfer reliability by implementing packet sequencing and acknowledgment.

## Contributing

Contributions are welcome! Please fork the repository and submit pull requests for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgements

- This project is inspired by basic network programming concepts and aims to provide a simple example of a UDP file transfer and group chat application using Java.
