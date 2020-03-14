package webdroid; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WEBDROID extends Application {
        
    
      public static String HTMLText = "";
      private static String FilePath=""; 
      Button button_browse, button_convert;
      TextArea HTMLTextField, Android_Layout_XML, Android_String_XML, Android_Color_XML;
      private static Stage stage;
      public static boolean ErrorDetected =false;
      
       public static void ErrorMessagePopup(String Error, String Error_Message){
            Label message = new Label(Error_Message);
 
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(message);
 
            Scene secondScene = new Scene(secondaryLayout, 500, 250);
 
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle(Error);
            newWindow.setScene(secondScene);
 
            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);
 
            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(stage);
            Image WEBDROIDIcon = new Image("/ErrorIcon.jpg");
            newWindow.getIcons().add(WEBDROIDIcon);
            // Set position of second window, related to primary window.
            newWindow.setX(stage.getX() + 300);
            newWindow.setY(stage.getY() + 200);
 
            newWindow.show();
            ErrorDetected =false;
    }  
      
      
        @Override
        public void start(Stage primaryStage) {
            stage = primaryStage;
            primaryStage.setTitle("WEBDROID");
            
            Label HTMLLabel = new Label("HTML Code:");
            Label Android_Layout_XML_label = new Label("Android Layout  XML:");
            Label Android_String_XML_label = new Label("Android String  XML:");
            Label Android_Color_XML_label = new Label("Android Color  XML:");
            Group root = new Group();
            Scene scene = new Scene(root, 1250, 700, Color.WHITE);
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);
            
            GridPane gridpane0 = new GridPane();
            gridpane0.setPadding(new Insets(5));
            gridpane0.setHgap(10);
            gridpane0.setVgap(10);
            
            HTMLTextField = new TextArea();
            HTMLTextField.setPrefRowCount(100);
            HTMLTextField.setPrefColumnCount(100);
            HTMLTextField.setWrapText(false);
            HTMLTextField.setPrefWidth(600);
            HTMLTextField.setPrefHeight(600);
             
            GridPane.setHalignment(HTMLTextField, HPos.CENTER);
            gridpane0.add(HTMLLabel, 0, 1);             
            gridpane0.add(HTMLTextField, 0, 2);
          
            root.getChildren().add(gridpane); 
            
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefWidth(600);
            scrollPane.setPrefHeight(600);
            scrollPane.setStyle("-fx-background-color:transparent");
            GridPane subgridpane1 = new GridPane();
               
                subgridpane1.setHgap(10);
                subgridpane1.setVgap(10);
                // Always show vertical scroll bar
                scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

                // Horizontal scroll bar is only displayed when needed
                scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
            
            Android_Layout_XML = new TextArea();
            Android_Layout_XML.setPrefRowCount(100);
            Android_Layout_XML.setPrefColumnCount(100);
            Android_Layout_XML.setWrapText(true);
            Android_Layout_XML.setPrefWidth(550);
            Android_Layout_XML.setPrefHeight(300);
             
            Android_String_XML = new TextArea();
            Android_String_XML.setPrefRowCount(100);
            Android_String_XML.setPrefColumnCount(100);
            Android_String_XML.setWrapText(true);
            Android_String_XML.setPrefWidth(550);
            Android_String_XML.setPrefHeight(300);
             
            Android_Color_XML = new TextArea();
            Android_Color_XML.setPrefRowCount(100);
            Android_Color_XML.setPrefColumnCount(100);
            Android_Color_XML.setWrapText(true);
            Android_Color_XML.setPrefWidth(550);
            Android_Color_XML.setPrefHeight(300);
            GridPane.setHalignment(Android_Color_XML, HPos.CENTER);
            subgridpane1.setHgap(10);
            subgridpane1.setVgap(10);   
            subgridpane1.setPadding(new Insets(5));
            subgridpane1.add(Android_Layout_XML_label, 0, 0);
            subgridpane1.add(Android_Layout_XML, 0, 1);
            subgridpane1.add(Android_String_XML_label, 0, 2);
            subgridpane1.add(Android_String_XML, 0, 3);
            subgridpane1.add(Android_Color_XML_label, 0, 4);
            subgridpane1.add(Android_Color_XML, 0, 5);
            
             scrollPane.setContent(subgridpane1); 
              
                button_browse = new Button("Browse HTML");
                button_browse.setOnAction(e -> handleButtonAction(e));
                button_convert = new Button("Convert");
                button_convert.setOnAction(e -> handleButtonAction(e));
                
                GridPane subgridpane = new GridPane();
                subgridpane.setPadding(new Insets(5));
                subgridpane.setHgap(10);
                subgridpane.setVgap(10);
                subgridpane.add(button_browse, 0, 0);
                subgridpane.add(button_convert, 1, 0);
                gridpane0.add(subgridpane, 0, 3);
            gridpane.add(gridpane0,0,0);
            gridpane.add(scrollPane,1,0);
   
            Image WEBDROIDIcon = new Image("/Webdroid Icon Initial.png");
            primaryStage.getIcons().add(WEBDROIDIcon);

            primaryStage.setScene(scene);
            primaryStage.show();
        }


    public static void main(String[] args) {
        launch(args);        
    }
    
    
        private void handleButtonAction(ActionEvent event){
        if(event.getTarget()==button_browse){
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html, *css)", "*.html","*.css");
                    fileChooser.getExtensionFilters().add(extFilter);
                    
                    //Please change the initial directory if this program is running on a different system
                    fileChooser.setInitialDirectory(new File("D:\\Computer Science\\Senior's Project\\Prototype\\WEBDROID.txt\\WEBDROID\\test\\HTML CSS Files"));
                    File file = fileChooser.showOpenDialog(stage);
                    
                    try{
                          FilePath= file.getAbsoluteFile().toString();
                          HTMLTextField.clear();
                          HTMLTextField.setText( ReadHTMLFile());
                    }catch(NullPointerException e){
                        System.out.println("No file selected");
                    }
                  
                    
        }
        else if (event.getTarget()==button_convert){
                    HTMLText = HTMLTextField.getText();
                    
 
                   //Parsing phase
                   Parser Parser = new Parser();
                   Parser.setHTMLParser(HTMLText);
                   Parser.PARSE();
                   if(!ErrorDetected){
                       SemanticAnalyzer Semantic = new SemanticAnalyzer();
                       Semantic.ExecuteAnalyzer();
                       
                       CodeGenerator CD = new CodeGenerator();
                       CD.directTranslation();
                   }
                         
//                   printing of the output - result
//                   Android_Layout_XML.clear();
//                   Android_Layout_XML.setText( XML_Code.get_layout_xml());
//                   Android_String_XML.clear();
//                   Android_String_XML.setText(XML_Code.get_string_xml());
            
        }
    }
        private static String ReadHTMLFile(){
        String s = "";
        
           try {
                File htmlfile = new File(FilePath);
           try (Scanner myReader = new Scanner(htmlfile)) {  
                while (myReader.hasNextLine()) {
                    //reading the text line per line
                    String data = myReader.nextLine();
                    //concatenating the text as a single string
                    s +=data+"\n";
                }
                
            }
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred while searching the file");
              } 
        
        return s;
    }
         
}

