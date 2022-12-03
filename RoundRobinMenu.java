import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

public class RoundRobinMenu extends Application{
    ArrayList<Process> processList = new ArrayList<Process>();
    ArrayList<Process> doneProcessList = new ArrayList<Process>();
    static int processCount = 0;
    int quantum;

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    double width = screenSize.getWidth()*0.4;
    double height = screenSize.getHeight()*0.6;
    double buttonSize = width/4.8;

    DecimalFormat df = new DecimalFormat("#.##");
    static int startCoorX = 10;

    @Override 
    public void start(Stage stage) {        
        // Input Process Scene
        TableView<Process> processInputTable = new TableView<>();
        TableColumn<Process, String> pName = new TableColumn<>("Process");
        pName.setCellValueFactory(new PropertyValueFactory<>("processName"));
        TableColumn<Process, String> pArrivalTime = new TableColumn<>("Arrival Time");
        pArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        TableColumn<Process, String> pBurstTime = new TableColumn<>("Burst Time");
        pBurstTime.setCellValueFactory(new PropertyValueFactory<>("priority"));
        TableColumn<Process, String> pPriority = new TableColumn<>("Priority");
        pPriority.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        processInputTable.getColumns().addAll(Arrays.asList(pName, pArrivalTime, pBurstTime, pPriority));
        processInputTable.setMaxHeight(300);
        pName.prefWidthProperty().bind(processInputTable.widthProperty().multiply(0.25));
        pArrivalTime.prefWidthProperty().bind(processInputTable.widthProperty().multiply(0.25));
        pBurstTime.prefWidthProperty().bind(processInputTable.widthProperty().multiply(0.25));
        pPriority.prefWidthProperty().bind(processInputTable.widthProperty().multiply(0.25));

        GridPane processInputPane = new GridPane();
        TextField arrivalTimeField = new TextField();
        TextField burstTimeField = new TextField();
        TextField priorityField = new TextField();
        Button btEnterProcess = new Button("ENTER PROCESS");
        Button btStartScheduling = new Button("START SCHEDULING");
        Label inputMessageLabel = new Label();
        //arranging all nodes in the grid
        processInputPane.add(new Text("Arrival Time of Process"), 0, 0);
        processInputPane.add(arrivalTimeField, 1, 0);
        processInputPane.add(new Text("Burst Time of Process"), 0, 1);
        processInputPane.add(burstTimeField, 1, 1);
        processInputPane.add(new Text("Priority of Process"), 0, 2);
        processInputPane.add(priorityField, 1, 2);
        processInputPane.add(btEnterProcess, 0, 3);
        processInputPane.add(btStartScheduling, 1, 3);
        processInputPane.add(inputMessageLabel, 1, 4);
        processInputPane.setAlignment(Pos.CENTER);
        processInputPane.setHgap(5);
        processInputPane.setVgap(5);
        VBox processInputBox = new VBox(processInputTable, processInputPane);
        Scene processInputScene = new Scene(processInputBox, width, height);

        // Input Time Quantum Scene
        Label quantumLabel = new Label("Please Enter Time Quantum for Round Robin Scheduling.");
        GridPane quantumInputPane = new GridPane();
        TextField quantumField = new TextField();
        Button btEnterQuantum = new Button("ENTER");
        Label quantumMessageLabel = new Label();
        quantumInputPane.add(new Text("Time Quantum"), 0, 1);
        quantumInputPane.add(quantumField, 1, 1);
        quantumInputPane.add(btEnterQuantum, 2, 2);
        quantumInputPane.setAlignment(Pos.CENTER);
        quantumInputPane.setHgap(8);
        quantumInputPane.setVgap(8);
        VBox quantumInputBox = new VBox(quantumLabel, quantumInputPane, quantumMessageLabel);
        quantumInputBox.setAlignment(Pos.CENTER);
        Scene quantumInputScene = new Scene(quantumInputBox, width, height);

        // Display Scheduling Output Scene
        TableView<Process> processOutputTable = new TableView<>();
        TableColumn<Process, String> pOutName = new TableColumn<>("Process");
        pOutName.setCellValueFactory(new PropertyValueFactory<>("processName"));
        TableColumn<Process, String> pOutArrivalTime = new TableColumn<>("Arrival Time");
        pOutArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        TableColumn<Process, String> pOutBurstTime = new TableColumn<>("Burst Time");
        pOutBurstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        TableColumn<Process, String> pFinishingTime = new TableColumn<>("Finishing Time");
        pFinishingTime.setCellValueFactory(new PropertyValueFactory<>("finishingTime"));
        TableColumn<Process, String> pTurnaroundTime = new TableColumn<>("Turnaround Time");
        pTurnaroundTime.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
        TableColumn<Process, String> pWaitingTime = new TableColumn<>("Waiting Time");
        pWaitingTime.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        processOutputTable.getColumns().addAll(Arrays.asList(pOutName, pOutArrivalTime, pOutBurstTime, pFinishingTime, pTurnaroundTime, pWaitingTime));
        processOutputTable.setMaxHeight(280);
        pOutArrivalTime.prefWidthProperty().bind(processOutputTable.widthProperty().multiply(0.145));
        pOutBurstTime.prefWidthProperty().bind(processOutputTable.widthProperty().multiply(0.145));
        pFinishingTime.prefWidthProperty().bind(processOutputTable.widthProperty().multiply(0.2));
        pTurnaroundTime.prefWidthProperty().bind(processOutputTable.widthProperty().multiply(0.2));
        pWaitingTime.prefWidthProperty().bind(processOutputTable.widthProperty().multiply(0.2));
        
        GridPane processOutputPane = new GridPane();
        Label total_TTLabel = new Label();
        Label avg_TTLabel = new Label();
        Label total_WTLabel = new Label();
        Label avg_WTLabel = new Label();
        processOutputPane.add(total_TTLabel, 0, 0);
        processOutputPane.add(avg_TTLabel, 0, 1);
        processOutputPane.add(total_WTLabel, 1, 0);
        processOutputPane.add(avg_WTLabel, 1, 1);
        processOutputPane.setAlignment(Pos.CENTER);
        processOutputPane.setHgap(8);
        processOutputPane.setVgap(8);
        Group rectangleGroup = new Group();

        VBox processOutputBox = new VBox(processOutputTable, processOutputPane, rectangleGroup);
        Scene processOutputScene = new Scene(processOutputBox, 800, height);     

        // buttons function
        btEnterProcess.setOnAction(e -> {
            try{
                if (processCount > 9){
                    throw new IllegalArgumentException();
                }
                Integer arrivalTime = Integer.valueOf(arrivalTimeField.getText());
                Integer burstTime = Integer.valueOf(burstTimeField.getText());
                Integer priority = Integer.valueOf(priorityField.getText());
                Process newProcess = new Process(processCount, arrivalTime, burstTime, priority);
                processList.add(newProcess);
                processInputTable.getItems().add(newProcess);
                inputMessageLabel.setText("");
                arrivalTimeField.setText("");
                burstTimeField.setText(""); 
                priorityField.setText(""); 
                processCount += 1;   
            }
            catch (NumberFormatException ex){
                inputMessageLabel.setText("ERROR: You must enter a positive integer.");
            }
            catch (IllegalArgumentException ex){
                inputMessageLabel.setText("ERROR: You can enter 10 processes only.\nPlease click 'START' for round robin scheduling.");
            }
        });

        btStartScheduling.setOnAction(e -> {
            try{
                if (processCount < 3){
                    throw new IllegalArgumentException();
                }
                stage.setTitle("Enter Time Quantum"); 
                stage.setScene(quantumInputScene);
            }
            catch (IllegalArgumentException ex){
                inputMessageLabel.setText("ERROR: You need to enter at least 3 processes.");
            }
        });

        btEnterQuantum.setOnAction(e -> {
            try{
                quantum = Integer.valueOf(quantumField.getText());
                quantumMessageLabel.setText("");
                stage.setTitle("Output of Round Robin CPU Scheduling"); 
                stage.setScene(processOutputScene);
                RoundRobin roundRobin = new RoundRobin();
                ArrayList <Pair <Integer, Integer>> doneProcess_Time = roundRobin.findRR(processList, doneProcessList, quantum);
                for (int i = 0; i < doneProcessList.size(); i++){
                    processOutputTable.getItems().add(doneProcessList.get(i));
                }
                float total_TT = roundRobin.findTotal_TT(doneProcessList);
                float total_WT = roundRobin.findTotal_WT(doneProcessList);
                total_TTLabel.setText("Total Turnarround Time = " + df.format(total_TT) + " ms");
                avg_TTLabel.setText("Average Turnarround Time = " + df.format(roundRobin.findAvg(doneProcessList, total_TT)) + " ms");
                total_WTLabel.setText("Total Waiting Time = " + df.format(total_WT) + " ms");
                avg_WTLabel.setText("Average Waiting Time = " + df.format(roundRobin.findAvg(doneProcessList, total_WT)) + " ms");
                Text startTimeText = new Text(String.valueOf(doneProcessList.get(0).getArrivalTime()));
                startTimeText.setX(startCoorX);
                startTimeText.setY(45);
                rectangleGroup.getChildren().add(startTimeText);
                for (int i = 0; i < doneProcess_Time.size(); i++){
                    Pair <Integer, Integer> processToDisplay = doneProcess_Time.get(i);
                    Rectangle rectangle = new Rectangle(60, 30);
                    rectangleGroup.getChildren().add(rectangle);
                    rectangle.setX(startCoorX);
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.BLACK);
                    Text processNameText = new Text("P" + String.valueOf(processToDisplay.getKey()));
                    processNameText.setX(startCoorX + 10);
                    processNameText.setY(20);
                    rectangleGroup.getChildren().add(processNameText);
                    startCoorX += 60;
                    Text processTimeText = new Text(String.valueOf(processToDisplay.getValue()));
                    processTimeText.setX(startCoorX - 5);
                    processTimeText.setY(45);
                    rectangleGroup.getChildren().add(processTimeText);
                }
            }
            catch (NumberFormatException ex){
                quantumMessageLabel.setText("ERROR: You must enter a positive integer.");
            }
        });
        
        // set processInputScene as main scene
        stage.setTitle("Round Robin CPU Scheduling"); 
        stage.setScene(processInputScene); 
        stage.show();
    }

    public static void main(String[] args) { 
        launch(args);
    }
}

