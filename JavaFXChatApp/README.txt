JavaFX Multi-Client Chat App

Features:
- Multiple clients can chat
- Messages are saved in chat_history.txt
- Old messages automatically load when a client connects
- Uses sockets and JavaFX

How To Run:

1. Download JavaFX SDK:
https://openjfx.io

2. Add JavaFX SDK lib folder to your IDE.

3. Add VM options:

--module-path "PATH_TO_FX/lib" --add-modules javafx.controls

Example:
--module-path "C:\javafx-sdk-24\lib" --add-modules javafx.controls

4. Run ChatServer.java FIRST

5. Run ChatClient.java multiple times for different users
