# 🎮🕹 Battleship 
HS-Aalen Battelship Game Team Project

![alt text](assets/images/StartScreen.JPG?raw=true)

# 👾 Features: 
- Singleplayer
- Multiplayer
- AI vs AI
- Game Load
- Sound

# ✨ Repository: 
<ul>
    <li>assets: images, sound, Lib, Fonts</li>
    <li>Docs: java Docs and UML for Dev and for users.</li>
    <li>lib: jar files of the dependencies</li>
    <li>src: java source code</li>
</ul>

# ✨ Dependencies:
<ul>
    <li>Java 8</li>
    <li>Swing GUI</li>
    <li>gson-2.8.6. (.jar files are in lib folder)</li>
</ul>

## ✨ Compiled version

- go into the **binaries** folder
- on windows: just double click the **battleship.exe** file. You need to have a jre (java runtime environment) folder there. You can create a shortcut and add it where you want.
- all platforms: in the console write:
```shell script
java -jar Battleship.jar
```
- You need java and the console must find it
- You can also pass the full path to the java executable

## ✨ How to open the Game in IntelliJ?
- Go to: **File** > **Open** > Select this **Project** > click **OK**
- Setup your SDK to Java 1.8.0
- You have to mark your src directory as Source Root.
  Right-click on the src folder and select Sources Root:
  ![alt text](https://i.stack.imgur.com/r6yjz.png)
- Check the output path by bringing up the Project Structure view.
  Press **Ctrl+Alt+Shift+S**
  ![alt text](https://i.stack.imgur.com/1yD7Y.png)
- Open the Project Structure again and go to **libraries** under **Project Settings**
click the **+** > **java** > **assets** > **lib** > select **gson-2.8.6** > **ok**
and apply and ok.
- Go to: **src** > **Main** > **Game** and run the Main function.

