import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private GraphicsContext gc;
    private String currGPA;

    static double[] sgpa1;
    static double[] sgpa2;
    static double[] sgpa3;
    static double[] cgpa;
    static String[] orgNames;

    private static ArrayList<Student> students = new ArrayList<>();
    private final List<Student> originalOrder = new ArrayList<>();


    @Override
    public void start(Stage stage) {
        
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10; -fx-spacing: 10;");

        HBox controls = new HBox();
        controls.setStyle("-fx-spacing: 10; -fx-alignment: center;");

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        insertData();
        sgpa1 = new double[students.size()];
        sgpa2 = new double[students.size()];
        sgpa3 = new double[students.size()];
        cgpa = new double[students.size()];
        orgNames = new String[students.size()];

        for(int i=0;i<Student.getObjectCount();i++){
            sgpa1[i] = students.get(i).sgpa[0];
            sgpa2[i] = students.get(i).sgpa[1];
            sgpa3[i] = students.get(i).sgpa[2];
            cgpa[i] = students.get(i).getCGPA();
            orgNames[i] = students.get(i).getName();
        }

        Button showCGPA = createStyledButton("CGPA", "#F44336", "#FFFFFF");
        Button showSGPA1 = createStyledButton("1st Semester SGPA", "#F44336", "#FFFFFF");
        Button showSGPA2 = createStyledButton("2nd Semester SGPA", "#F44336", "#FFFFFF");
        Button showSGPA3 = createStyledButton("3rd Semester SGPA", "#F44336", "#FFFFFF");
        Button resetButton = createStyledButton("Reset Data", "#4CAF50", "#FFFFFF");
        Button bubbleSort = createStyledButton("Bubble Sort", "#FBBC04", "#FFFFFF");
        Button selectionSort = createStyledButton("Selection Sort", "#FBBC04", "#FFFFFF");
        Button insertionSort = createStyledButton("Insertion Sort", "#FBBC04", "#FFFFFF");
        Button mergeSort = createStyledButton("Merge Sort", "#FBBC04", "#FFFFFF");

        
        Slider speedSlider = new Slider(1, 80, 40);
        speedSlider.setStyle("-fx-accent: #2196F3;");

        controls.getChildren().addAll(showCGPA, showSGPA1, showSGPA2, showSGPA3, resetButton, speedSlider, bubbleSort, selectionSort, insertionSort, mergeSort);

        root.getChildren().addAll(controls, canvas);

        resetButton.setOnAction(e -> {
            resetGPA();
        });

        bubbleSort.setOnAction(e -> {
            switch (currGPA) {
                case "CGPA":
                    bubbleSortGPA(cgpa, (int) speedSlider.getValue());
                    break;
                case "SGPA1":
                    bubbleSortGPA(sgpa1, (int) speedSlider.getValue());
                    break;
                case "SGPA2":
                    bubbleSortGPA(sgpa2, (int) speedSlider.getValue());
                    break;
                case "SGPA3":
                bubbleSortGPA(sgpa3, (int) speedSlider.getValue());
                break;
            }
        });

        selectionSort.setOnAction(e -> {
            switch (currGPA) {
                case "CGPA":
                    selectionSortGPA(cgpa, (int) speedSlider.getValue());
                    break;
                case "SGPA1":
                    selectionSortGPA(sgpa1, (int) speedSlider.getValue());
                    break;
                case "SGPA2":
                    selectionSortGPA(sgpa2, (int) speedSlider.getValue());
                    break;
                case "SGPA3":
                    selectionSortGPA(sgpa3, (int) speedSlider.getValue());
                    break;
            }
        });

        insertionSort.setOnAction(e -> {
            switch (currGPA) {
                case "CGPA":
                    insertionSortGPA(cgpa, (int) speedSlider.getValue());
                    break;
                case "SGPA1":
                    insertionSortGPA(sgpa1, (int) speedSlider.getValue());
                    break;
                case "SGPA2":
                    insertionSortGPA(sgpa2, (int) speedSlider.getValue());
                    break;
                case "SGPA3":
                insertionSortGPA(sgpa3, (int) speedSlider.getValue());
                break;
            }
        });

        mergeSort.setOnAction(e -> {
            switch (currGPA) {
                case "CGPA":
                    mergeSortGPA(cgpa, (int) speedSlider.getValue());
                    break;
                case "SGPA1":
                    mergeSortGPA(sgpa1, (int) speedSlider.getValue());
                    break;
                case "SGPA2":
                    mergeSortGPA(sgpa2, (int) speedSlider.getValue());
                    break;
                case "SGPA3":
                    mergeSortGPA(sgpa3, (int) speedSlider.getValue());
                    break;
            }
        });

        showCGPA.setOnAction(e -> {
            currGPA = "CGPA";
            drawGPA(cgpa, orgNames);
        });
        showSGPA1.setOnAction(e -> {
            currGPA = "SGPA1";
            drawGPA(sgpa1, orgNames);
        });
        showSGPA2.setOnAction(e -> {
            currGPA = "SGPA2";
            drawGPA(sgpa2, orgNames);
        });
        showSGPA3.setOnAction(e -> {
            currGPA = "SGPA3";
            drawGPA(sgpa3, orgNames);
        });

        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        stage.setScene(new Scene(root));
        stage.setTitle("Data Visualizer");
        stage.show();
    }

    private Button createStyledButton(String text, String backgroundColor, String textColor) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: " + backgroundColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;"
        );
        return button;
    }

    private void resetGPA() {
        students.clear();
        students.addAll(originalOrder);
        switch (currGPA) {
            case "CGPA":
                drawGPA(cgpa, orgNames);
                break;
            case "SGPA1":
                drawGPA(sgpa1, orgNames);
                break;
            case "SGPA2":
                drawGPA(sgpa2, orgNames);
                break;
            case "SGPA3":
                drawGPA(sgpa3, orgNames);
                break;
        }
    }

    private void drawGPA(double[] arr, String[] names) {

        gc.setFill(Color.WHITE); 
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeLine(50, HEIGHT - 50, 50, 50); // Y-axis
        gc.strokeLine(50, HEIGHT - 50, WIDTH - 50, HEIGHT - 50); // X-axis
    
        for (int i = 0; i <= 40; i++) {
            double gpaValue = i / 10.0; // Calculate GPA value (e.g., 1.0, 1.1)
            double y = HEIGHT - 50 - (gpaValue * (HEIGHT - 100) / 4);
            if (i % 10 == 0) {
                // Major ticks and labels
                gc.fillText(String.format("%.1f", gpaValue), 30, y + 5);
                gc.strokeLine(45, y, 50, y);
            } else {
                // Minor ticks only
                gc.strokeLine(47, y, 50, y);
            }
        }
        
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        if("SGPA1".equals(currGPA)){
            gc.strokeLine(50, 406, WIDTH, 406);
        }else{
            gc.strokeLine(50, 360, WIDTH, 360);
        }
        // Draw bars for GPA values
        double validCount = 0;
        for (double gpa : arr) {
            if (gpa > 0) {
                validCount++;
            }
        }
        double barSpacing = (WIDTH - 100) / validCount;
        double currentX = 50;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                continue;
            }

            double gpa = arr[i];
            double barHeight = gpa * (HEIGHT - 100) / 4;

            if(gpa>=1.70 && "SGPA1".equals(currGPA)){
                gc.setFill(Color.DODGERBLUE);
            }else if(gpa>=2.00){
                gc.setFill(Color.DODGERBLUE);
            }else{
                gc.setFill(Color.RED);
            }

            gc.fillRect(currentX, HEIGHT - 50 - barHeight, barSpacing - 5, barHeight);
            // Draw student names inside bars, rotated 90 degrees
            gc.setFill(Color.BLACK);
            gc.setFont(javafx.scene.text.Font.font(12));
            gc.save(); // Save the current state
            gc.translate(currentX + (barSpacing - 5) / 2, HEIGHT - 50 - barHeight / 2);
            gc.rotate(90); // Rotate text 90 degrees clockwise
            gc.fillText(names[i], 0, 5);
            gc.restore(); // Restore to the saved state

            currentX += barSpacing;
        }
    }

    private void bubbleSortGPA(double[] orgArr, int speed) {

        double[] arr = orgArr.clone();
        String[] names = orgNames.clone();
        new Thread(() -> {
            try {
                for (int i = 0; i < students.size() - 1; i++) {
                    for (int j = 0; j < students.size() - i - 1; j++) {
                        if (arr[j] < arr[j + 1]) {
                            // Swap students
                            double temp = arr[j];
                            arr[j] = arr[j+1];
                            arr[j+1] = temp;

                            String temp2 = names[j];
                            names[j] = names[j+1];
                            names[j+1] = temp2;

                            // Visualize the sorting process
                            Platform.runLater(() -> drawGPA(arr, names));
                            Thread.sleep(80-speed); // Pause for visualization
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void selectionSortGPA(double[] orgArr, int speed) {

        double[] arr = orgArr.clone();
        String[] names = orgNames.clone();
        new Thread(() -> {
            try {
                for (int i = 0; i < students.size() - 1; i++) {
                    int maxIdx = i;
                    for (int j = i + 1; j < students.size(); j++) {
                        if (arr[j] > arr[maxIdx]) {
                            maxIdx = j;
                        }
                    }
                    // Swap students
                    double temp = arr[maxIdx];
                    arr[maxIdx] = arr[i];
                    arr[i] = temp;
    
                    String temp2 = names[maxIdx];
                    names[maxIdx] = names[i];
                    names[i] = temp2;
    
                    // Visualize the sorting process
                    Platform.runLater(() -> drawGPA(arr, names));
                    Thread.sleep(120-speed); // Pause for visualization
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void insertionSortGPA(double[] orgArr, int speed) {

        double[] arr = orgArr.clone();
        String[] names = orgNames.clone();
        new Thread(() -> {
            try {
                for (int i = 1; i < students.size(); i++) {
                    double key = arr[i];
                    String keyName = names[i];
                    int j = i - 1;
                    
                    // Move elements of arr[0..i-1], that are greater than key,
                    // to one position ahead of their current position
                    while (j >= 0 && arr[j] < key) {
                        arr[j + 1] = arr[j];
                        names[j + 1] = names[j];
                        j = j - 1;
    
                        // Visualize the sorting process
                    }
                    arr[j + 1] = key;
                    names[j + 1] = keyName;
                    Platform.runLater(() -> drawGPA(arr, names));
                    Thread.sleep(160-speed); // Pause for visualization
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void mergeSortGPA(double[] orgArr, int speed) {

        double[] arr = orgArr.clone();
        String[] names = orgNames.clone();
        new Thread(() -> {
            try {
                mergeSort(arr, names, 0, students.size() - 1, speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void mergeSort(double[] arr, String[] names, int left, int right, int speed) throws InterruptedException {
        if (left < right) {
            int mid = (left + right) / 2;
    
            mergeSort(arr, names, left, mid, speed);
            mergeSort(arr, names, mid + 1, right, speed);
    
            merge(arr, names, left, mid, right, speed);
        }
    }
    
    private void merge(double[] arr, String[] names, int left, int mid, int right, int speed) throws InterruptedException {
        int n1 = mid - left + 1;
        int n2 = right - mid;
    
        double[] L = new double[n1];
        double[] R = new double[n2];
        String[] LNames = new String[n1];
        String[] RNames = new String[n2];
    
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[left + i];
            LNames[i] = names[left + i];
        }
        for (int i = 0; i < n2; ++i) {
            R[i] = arr[mid + 1 + i];
            RNames[i] = names[mid + 1 + i];
        }
    
        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] >= R[j]) {
                arr[k] = L[i];
                names[k] = LNames[i];
                i++;
            } else {
                arr[k] = R[j];
                names[k] = RNames[j];
                j++;
            }
            k++;
    
            // Visualize the merging process
            Platform.runLater(() -> drawGPA(arr, names));
            Thread.sleep(80-speed); // Pause for visualization
        }
    
        while (i < n1) {
            arr[k] = L[i];
            names[k] = LNames[i];
            i++;
            k++;
    
            // Visualize the merging process
            Platform.runLater(() -> drawGPA(arr, names));
            Thread.sleep(80-speed); // Pause for visualization
        }
    
        while (j < n2) {
            arr[k] = R[j];
            names[k] = RNames[j];
            j++;
            k++;
    
            // Visualize the merging process
            Platform.runLater(() -> drawGPA(arr, names));
            Thread.sleep(80-speed); // Pause for visualization
        }
    }
    
    private void insertData(){
        originalOrder.add(new Student("MOAZ TARIQ", "F23BDOCS1M01001", 3.15, 2.49, 3.21));
        originalOrder.add(new Student("MANAHAL ARSHAD", "F23BDOCS1M01002", 2.66, 2.88, 3.63));
        originalOrder.add(new Student("MUHAMMAD ALI RAZA", "F23BDOCS1M01003", 3.68, 3.16, 3.43));
        originalOrder.add(new Student("FAIZA ASHRAF", "F23BDOCS1M01004", 3.70, 2.87, 3.71));
        originalOrder.add(new Student("MUHAMMAD BABAR", "F23BDOCS1M01005", 2.81, 2.93, 3.42));
        originalOrder.add(new Student("ZAINAB FATIMA", "F23BDOCS1M01006", 2.81, 3.21, 3.33));
        originalOrder.add(new Student("ASMENAIN", "F23BDOCS1M01007", 1.7, 1.48, 0));
        originalOrder.add(new Student("SADIA ABDULLAH", "F23BDOCS1M01008", 3.13, 2.18, 3.18));
        originalOrder.add(new Student("AMEER BAKHSH", "F23BDOCS1M01009", 2.89, 2.28, 3.41));
        originalOrder.add(new Student("ZOYA ZULFQAR", "F23BDOCS1M01010", 2.87, 3.18, 3.36));
        originalOrder.add(new Student("TALHA ALI", "F23BDOCS1M01011", 3.14, 2.64, 3.37));
        originalOrder.add(new Student("SAMIA IMTIAZ", "F23BDOCS1M01012", 3.04, 2.35, 3.46));
        originalOrder.add(new Student("ZAIN UL HASSAN", "F23BDOCS1M01013", 2.89, 2.11, 3.11));
        originalOrder.add(new Student("AMINA HAMAD", "F23BDOCS1M01014", 2.87, 3.23, 3.67));
        originalOrder.add(new Student("RABIA NAZEER", "F23BDOCS1M01016", 3.16, 3.39, 3.92));
        originalOrder.add(new Student("SOHAIL AHMAD", "F23BDOCS1M01017", 3.49, 3.45, 3.86));
        originalOrder.add(new Student("ALI HASSAN ABBASI", "F23BDOCS1M01019", 3.03, 3.89, 4.00));
        originalOrder.add(new Student("MARYAM IFTIKHAR", "F23BDOCS1M01020", 2.63, 2.65, 3.73));
        originalOrder.add(new Student("SAMNA ASHRAF", "F23BDOCS1M01022", 3.68, 3.62, 3.73));
        originalOrder.add(new Student("MUHAMMAD ABDULLAH", "F23BDOCS1M01023", 1.76, 2.79, 3.87));
        originalOrder.add(new Student("USWA LATIF", "F23BDOCS1M01024", 3.37, 3.15, 3.72));
        originalOrder.add(new Student("AZAM FIDA", "F23BDOCS1M01025", 2.97, 2.81, 3.72));
        originalOrder.add(new Student("FATIMA SAEED", "F23BDOCS1M01076", 3.15, 3.15, 3.59));
        originalOrder.add(new Student("AHMAD HASSAN", "F23BDOCS1M01089", 4.0, 4.0, 4.00));
        originalOrder.add(new Student("ANAS BIN SAEED", "F23BDOCS1M01099", 3.68, 3.05, 2.96));
        originalOrder.add(new Student("MUHAMMAD HASSAN", "F23BDOCS1M01107", 2.38, 2.24, 0));
        originalOrder.add(new Student("HUZAIFA", "F23BDOCS1M01108", 2.04, 0.66, 0));
        originalOrder.add(new Student("MUHAMMAD .", "F23BDOCS1M01110", 3.52, 3.82, 3.86));
        originalOrder.add(new Student("MUHAMMAD SHAHBAN", "F23BDOCS1M01111", 3.29, 3.19, 3.45));
        originalOrder.add(new Student("MUHAMMAD KASHIF", "F23BDOCS1M01112", 2.74, 2.67, 0));
        originalOrder.add(new Student("WAQAS AHMAD", "F23BDOCS1M01113", 3.01, 2.22, 3.02));
        originalOrder.add(new Student("MUNEEB UR REHMAN", "F23BDOCS1M01114", 2.82, 2.21, 3.24));
        originalOrder.add(new Student("RANA MUHAMAD ATIQ", "F23BDOCS1M01115", 2.74, 2.09, 2.24));
        originalOrder.add(new Student("MUHAMMAD ASAD ULLAH", "F23BDOCS1M01116", 3.08, 2.84, 3.51));
        originalOrder.add(new Student("FAISAL ALTAF", "F23BDOCS1M01017", 2.02, 1.14, 0));
        originalOrder.add(new Student("WAQAR YOUNAS", "F23BDOCS1M01118", 3.15, 2.79, 3.26));
        originalOrder.add(new Student("ALI HAIDER", "F23BDOCS1M01119", 2.85, 2.45, 3.37));
        originalOrder.add(new Student("MUHAMMAD YOUNIS", "F23BDOCS1M01120", 2.91, 3.23, 3.65));
        originalOrder.add(new Student("MUHAMMAD NASIR", "F23BDOCS1M01121", 3.07, 2.7, 3.66));
        originalOrder.add(new Student("MUHAMMAD SHAFEEQ", "F23BDOCS1M01122", 3.34, 2.37, 3.35));
        originalOrder.add(new Student("SYED ASIM JAWAD", "F23BDOCS1M01123", 2.78, 2.75, 3.10));
        originalOrder.add(new Student("MUHAMMAD ATIF ARSHAD", "F23BDOCS1M01125", 3.67, 3.66, 3.92));
        originalOrder.add(new Student("TEHSEEN NAWAZ", "F23BDOCS1M01191", 2.65, 1.61, 3.22));
        originalOrder.add(new Student("QURRATULAIN", "F23BDOCS1M01212", 2.40, 2.06, 2.66));

        students.addAll(originalOrder);
    }
    
    public static void main(String[] args) {
        launch();
    }
}
