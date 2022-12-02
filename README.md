# RoundRobinCPUScheduling

How to run the program:
1. Extract 'javafx-sdk-17.0.2.zip' and 'jackson_jar_files.zip' and put them into your C Drive.
2. Open the folder named "RoundRobin" in Visual Studio Code.

If there are no external libraries imported automatically please follow Step 3 and Step 4:
3. Add all jar files of 'javafx-sdk-17.0.2/lib' folder into Referenced Libraries of the Java project in the Explorer tab of the side bar.

4. Compile and run the code in RoundRobinMenu.java. 

If the program did not get exceuted succesfully please try:
- Add the line "vmArgs": "--module-path C:/javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml" in MainMenu's configuration of launch.json in the ".vscode" folder.

