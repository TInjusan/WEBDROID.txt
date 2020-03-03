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
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WEBDROID extends Application {
        
    
      public static String HTMLText = "";
      private static String FilePath=""; 

     
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("WEBDROID");
            
            Label HTMLLabel = new Label("HTML Code:");
            Label Android_Layout_XML_label = new Label("Android Layout  XML:");
            Label Android_String_XML_label = new Label("Android String  XML:");
            Group root = new Group();
            Scene scene = new Scene(root, 1700, 700, Color.WHITE);
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);

            TextArea HTMLTextField = new TextArea();
            HTMLTextField.setPrefRowCount(100);
            HTMLTextField.setPrefColumnCount(100);
            HTMLTextField.setWrapText(true);
            HTMLTextField.setPrefWidth(600);
            HTMLTextField.setPrefHeight(300);
            GridPane.setHalignment(HTMLTextField, HPos.CENTER);
            
            
            gridpane.add(HTMLLabel, 0, 1);             
            gridpane.add(HTMLTextField, 0, 2);
          
            root.getChildren().add(gridpane); 

            TextArea Android_Layout_XML = new TextArea();
            Android_Layout_XML.setPrefRowCount(100);
            Android_Layout_XML.setPrefColumnCount(100);
            Android_Layout_XML.setWrapText(true);
            Android_Layout_XML.setPrefWidth(600);
            Android_Layout_XML.setPrefHeight(300);
            GridPane.setHalignment(Android_Layout_XML, HPos.CENTER);
            
            gridpane.add(Android_Layout_XML_label, 0, 4);             
            gridpane.add(Android_Layout_XML, 0, 5);
            
            TextArea Android_String_XML = new TextArea();
            Android_String_XML.setPrefRowCount(100);
            Android_String_XML.setPrefColumnCount(100);
            Android_String_XML.setWrapText(true);
            Android_String_XML.setPrefWidth(600);
            Android_String_XML.setPrefHeight(300);
            GridPane.setHalignment(Android_String_XML, HPos.CENTER);
            
            gridpane.add(Android_String_XML_label, 1, 4);             
            gridpane.add(Android_String_XML, 1, 5);
            
                Button button_browse = new Button("Browse HTML");
                button_browse.setOnAction((ActionEvent arg0) -> {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html, *css)", "*.html","*.css");
                    fileChooser.getExtensionFilters().add(extFilter);
                    
                    //Please change the initial directory if this program is running on a different system
                    fileChooser.setInitialDirectory(new File("D:\\Computer Science\\Senior's Project\\Prototype\\WEBDROID.txt\\WEBDROID\\test\\HTML CSS Files"));
                    File file = fileChooser.showOpenDialog(primaryStage);
                    
                    FilePath= file.getAbsoluteFile().toString();
                    HTMLTextField.clear();
                    HTMLTextField.setText( ReadHTMLFile());
                    HTMLText = HTMLTextField.getText();
                    
 
                   //Parsing phase
                   Parser Parser = new Parser();
                   Parser.setHTMLParser(HTMLText);
                   Parser.PARSE();
                   
                   PropertyChecker property = new PropertyChecker();
                   
                   property.set_tree(Parser.getHTML_Elements(), Parser.getroot());
                   property.property_check();
                   
                   //Generating the XML files based from the output of the parser: AST
                   CodeGenerator XML_Code = new CodeGenerator();
                   XML_Code.set_html_element(Parser.getHTML_Elements(), Parser.getroot());
                   XML_Code.Run_Code_Generator();
                   
                   
                   //printing of the output - result
                   Android_Layout_XML.clear();
                   Android_Layout_XML.setText( XML_Code.get_layout_xml());
                   Android_String_XML.clear();
                   Android_String_XML.setText(XML_Code.get_string_xml());
                    
            });
            
             
                
            gridpane.add(button_browse, 1, 2);
               
            Image anotherIcon = new Image("/Webdroid Icon Initial.png");
            primaryStage.getIcons().add(anotherIcon);

            primaryStage.setScene(scene);
            primaryStage.show();
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
    
    public static void main(String[] args) {
        launch(args);        
    }
     
}

