package webdroid; 
import java.awt.Desktop; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WEBDROID extends Application {
    
      public static String HTMLText = "";
      private static String FilePath=""; 
      public static Button button_browse, button_convert, button_save_xml, button_browse_android, button_save_html;
      public static TextArea HTMLTextField, Android_Layout_XML, Android_String_XML, Android_Color_XML;
      public static  Hyperlink html_location = new Hyperlink("");
      public static  Hyperlink android_location = new Hyperlink("");
      private static Label HTMLLabel = new Label("HTML Code:");
      private static Stage stage;
      public static boolean ErrorDetected =false;

      
        @Override
        public void start(Stage primaryStage) {
            stage = primaryStage;
            primaryStage.setTitle("WEBDROID");
             
            Label Android_Layout_XML_label = new Label("Android Layout  XML:");
            Label Android_String_XML_label = new Label("Android String  XML:");
            Label Android_Color_XML_label = new Label("Android Color  XML:");
            Label Android_Color_XML_Components = new Label("Android XML Components");
           
            Group root = new Group();
            Scene scene = new Scene(root, 1250, 700, Color.WHITE);
            GridPane gridpane = new GridPane();
            gridpane.setPadding(new Insets(5));
            gridpane.setHgap(10);
            gridpane.setVgap(10);
            
            GridPane gridpaneHTML = new GridPane();
            gridpaneHTML.setPadding(new Insets(5));
            gridpaneHTML.setHgap(10);
            gridpaneHTML.setVgap(10);
            
            HTMLTextField = new TextArea();
            HTMLTextField.setPrefRowCount(100);
            HTMLTextField.setPrefColumnCount(100);
            HTMLTextField.setWrapText(false);
            HTMLTextField.setPrefWidth(600);
            HTMLTextField.setPrefHeight(600);
             
            GridPane.setHalignment(HTMLTextField, HPos.CENTER);
            gridpaneHTML.add(HTMLLabel, 0, 1);             
            gridpaneHTML.add(HTMLTextField, 0, 2);
          
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
            Android_Layout_XML.setPrefWidth(575);
            Android_Layout_XML.setPrefHeight(300);
             
            Android_String_XML = new TextArea();
            Android_String_XML.setPrefRowCount(100);
            Android_String_XML.setPrefColumnCount(100);
            Android_String_XML.setWrapText(true);
            Android_String_XML.setPrefWidth(575);
            Android_String_XML.setPrefHeight(300);
             
            Android_Color_XML = new TextArea();
            Android_Color_XML.setPrefRowCount(100);
            Android_Color_XML.setPrefColumnCount(100);
            Android_Color_XML.setWrapText(true);
            Android_Color_XML.setPrefWidth(575);
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
             scrollPane.setStyle("-fx-background: #25D164");
               
                button_browse = new Button("Browse HTML");
                button_browse.setOnAction(e -> handleButtonAction(e));
                button_convert = new Button("Convert");
                button_convert.setOnAction(e -> handleButtonAction(e));
                button_save_html = new Button("SAVE HTML");
                button_save_html.setOnAction(e -> handleButtonAction(e));
                button_browse_android = new Button("Browse Android Project");
                button_browse_android.setOnAction(e -> handleButtonAction(e));
                button_save_xml = new Button("SAVE XML");
                button_save_xml.setOnAction(e -> handleButtonAction(e));
                
                
                GridPane subgridpaneA = new GridPane();
                subgridpaneA.setPadding(new Insets(5));
                subgridpaneA.setHgap(10);
                subgridpaneA.setVgap(10);
                subgridpaneA.add(button_browse, 0, 0);
                subgridpaneA.add(button_convert, 1, 0);
                subgridpaneA.add(button_save_html, 2, 0);
                subgridpaneA.add(html_location, 3, 0);
                gridpaneHTML.add(subgridpaneA, 0, 3);
                                
                GridPane subgridpaneB = new GridPane();
                subgridpaneB.setPadding(new Insets(5));
                subgridpaneB.setHgap(10);
                subgridpaneB.setVgap(10);               
                subgridpaneB.add(button_browse_android, 0, 0);
                subgridpaneB.add(button_save_xml, 1, 0);
                subgridpaneB.add(android_location, 2, 0);              
                GridPane gridpaneXML = new GridPane();
                gridpaneXML.setPadding(new Insets(5));
                gridpaneXML.setHgap(10);
                gridpaneXML.setVgap(10);
               
                gridpaneXML.add(Android_Color_XML_Components, 0, 1);
                gridpaneXML.add(scrollPane, 0, 2);
                gridpaneXML.add(subgridpaneB, 0, 3);
                
              
            gridpane.add(gridpaneHTML,0,0);
            gridpane.add(gridpaneXML,1,0);
   
            
               html_location.setOnAction(e -> {

                        Desktop desktop = Desktop.getDesktop();
                        String directory = html_location.getText().substring(0, html_location.getText().lastIndexOf('\\'));
                        try{
                            File  dir = new File(directory);
                            desktop.open(dir);
                        }catch(IOException  ee){
                                System.out.println(directory);
                        }
               });
               android_location.setOnAction(e -> {
                         
                            Desktop desktop = Desktop.getDesktop();
                            
                            try{
                                File  dir = new File(android_location.getText());
                                desktop.open(dir);
                            }catch(IOException  ee){
                                System.out.println("No Android Project");    
                            }
                  });
                  
            Image WEBDROIDIcon = new Image("/Webdroid Icon Initial.png");
            primaryStage.getIcons().add(WEBDROIDIcon);

            primaryStage.setScene(scene);
            primaryStage.show();
        }


    public static void main(String[] args) {
        launch(args);        
    }
              
       public static void ErrorMessagePopup(String Error, String Error_Message){
            Label message = new Label(Error_Message);
            ScrollPane scrollPaneErrorMessage = new ScrollPane();
            scrollPaneErrorMessage.setContent(message);
                // Always show vertical scroll bar
                scrollPaneErrorMessage.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

                // Horizontal scroll bar is only displayed when needed
                scrollPaneErrorMessage.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
                
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(scrollPaneErrorMessage);
 
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
       
             
       public static void SavedMessageSuccessful(String FileName){
            Label message = new Label(FileName+"(s) Successfully Saved!");
 
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(message);
 
            Scene secondScene = new Scene(secondaryLayout, 300, 125);
 
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Confirmation");
            newWindow.setScene(secondScene);
 
            // Specifies the modality for new window.
            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.setMaxHeight(125);
            newWindow.setMaxWidth(300);
            
            newWindow.setMinHeight(125);
            newWindow.setMinWidth(300);
            
            // Specifies the owner Window (parent) for new window
            newWindow.initOwner(stage);
            Image WEBDROIDIcon = new Image("/success.png");
            newWindow.getIcons().add(WEBDROIDIcon);
            // Set position of second window, related to primary window.
            newWindow.setX(stage.getX() + 500);
            newWindow.setY(stage.getY() + 200);
 
            newWindow.show();
               
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
                  html_location.setText(FilePath.substring(0, FilePath.lastIndexOf('\\')));
                  HTMLLabel.setText(FilePath.substring (FilePath.lastIndexOf('\\')+1, FilePath.length()));
                  html_location.setPrefWidth(300);
           }
        else if (event.getTarget()==button_convert){
                    HTMLText = HTMLTextField.getText();
                     ClearAll();
                    
                   //Parsing phase
                   Parser Parser = new Parser();
                   Parser.setHTMLParser(HTMLText);
                   Parser.PARSE();
                   if(!ErrorDetected){
                       SemanticAnalyzer Semantic = new SemanticAnalyzer();
                       Semantic.ExecuteAnalyzer();
                       if(!ErrorDetected){
                        CodeGenerator CD = new CodeGenerator();
                        CD.directTranslation();
                       }
                       else
                            ClearAll();
                   }
                   else{
                       ErrorMessagePopup("Syntax Error",Parser.showSyntaxError() ); 
                       ClearAll();
                   }
                     
                  ErrorDetected =false;
                  System.out.println("clicked");
        }
        else if(event.getTarget()==button_browse_android){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("D:\\Computer Science\\Senior's Project\\Test Cases"));
             
             try{
                    File selectedDirectory = directoryChooser.showDialog(stage);
                    android_location.setText(selectedDirectory.toString());
                    android_location.setPrefWidth(300);
                   
              }catch(NullPointerException e){
                        System.out.println("No folder selected");
              }
         
        }
        else if(event.getTarget()==button_save_xml){
                
                File layout = new File(android_location.getText()+"\\app\\src\\main\\res\\layout\\activity_main.xml");
		File string = new File(android_location.getText()+"\\app\\src\\main\\res\\values\\strings.xml");
		File colors = new File(android_location.getText()+"\\app\\src\\main\\res\\values\\colors.xml");
		System.out.println(layout);
                System.out.println(string);
                System.out.println(colors);
                String missingfile = "";
		try ( FileWriter fileWriterLayout = new FileWriter(layout)) {			
			fileWriterLayout.write(Android_Layout_XML.getText());
			fileWriterLayout.close();
		}catch (Exception e) {
                         missingfile+="Android Project activity_main.xml not found.\n";
		}
		
                try ( FileWriter fileWriterString = new FileWriter(string)) {			
			fileWriterString.write(Android_String_XML.getText());
			fileWriterString.close();
		}catch (Exception e) {
                         missingfile+="Android Project strings.xml not found.\n";	
               }
                 try ( FileWriter fileWriterStringColors = new FileWriter(colors)) {			
			fileWriterStringColors.write(Android_Color_XML.getText());
			fileWriterStringColors.close();
		}catch (Exception e) {
                         missingfile+="Android Project color.xml not found.\n";	
                }
               
                 if(missingfile.equals(""))
                      SavedMessageSuccessful("XML");
                 else
                      ErrorMessagePopup("File Error - XML",missingfile);
		
                        
        }
        else if(event.getTarget()==button_save_html){
                    File htmlfile = new File(html_location.getText()+"\\"+HTMLLabel.getText());
                  
                    try ( FileWriter fileWriterLayout = new FileWriter(htmlfile)) {			
			fileWriterLayout.write(HTMLTextField.getText());
			fileWriterLayout.close();
                        SavedMessageSuccessful("HTML");
                        
                    }catch (Exception e) {
                            FileChooser fileChooser = new FileChooser();
  
                            //Set extension filter
                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML file (*.html)", "*.html");
                            fileChooser.getExtensionFilters().add(extFilter);

                            //Show save file dialog
                            File file = fileChooser.showSaveDialog(stage);

                            if(file != null){
                                SaveFile(HTMLTextField.getText(), file);
                                 html_location.setText(FilePath.substring(0, FilePath.lastIndexOf('\\')));
                                 HTMLLabel.setText(FilePath.substring (FilePath.lastIndexOf('\\')+1, FilePath.length()));
                                 html_location.setPrefWidth(300);
                            }   
                    }
		
        }
     
    } 
        private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(WEBDROID.class.getName()).log(Level.SEVERE, null, ex);
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
        private static void ClearAll(){            
                    Android_String_XML.clear();
                    Android_Layout_XML.clear();
                    Android_Color_XML.clear();
                    SymbolTable.ArrayString.clear();
                    SymbolTable.root = null;
                    SymbolTable.string_literals.clear();
                    SymbolTable.xml_entry.clear();
                    SymbolTable.xml_root= null;
                    SymbolTable.ClearHTML();
                    Parser.clearErrorMessage();
        }         
}

