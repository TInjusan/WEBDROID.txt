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
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WEBDROID extends Application {
        
      private String result; 
      
      private static String FilePath=""; 

    public WEBDROID() {
        this.result = "";
    }
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("WEBDROID");
            Label HTMLLabel = new Label("HTML Code:");
            Label CSSLabel = new Label("CSS Code:");
            Group root = new Group();
            Scene scene = new Scene(root, 1225, 600, Color.WHITE);
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
            
            TextArea CSSTextField = new TextArea();
            CSSTextField.setPrefRowCount(100);
            CSSTextField.setPrefColumnCount(100);
            CSSTextField.setWrapText(true);
            CSSTextField.setPrefWidth(600);
            CSSTextField.setPrefHeight(300);
            GridPane.setHalignment(CSSTextField, HPos.CENTER);
            
            gridpane.add(HTMLLabel, 0, 1);
            gridpane.add(CSSLabel, 1, 1);
            gridpane.add(HTMLTextField, 0, 2);
            gridpane.add(CSSTextField, 1, 2);
            
            root.getChildren().add(gridpane); 

              Button button_browse = new Button("Browse HTML");
                button_browse.setOnAction((ActionEvent arg0) -> {
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html, *css)", "*.html","*.css");
                    fileChooser.getExtensionFilters().add(extFilter);
                    
                    //Please change the initial directory if this program is running on a different system
                    fileChooser.setInitialDirectory(new File("D:\\Computer Science\\Senior's Project\\Prototype\\WEBDROID.txt\\WEBDROID\\src"));
                    File file = fileChooser.showOpenDialog(primaryStage);
                    
                    FilePath= file.getAbsoluteFile().toString();
                    HTMLTextField.setText( ReadHTMLFile());
                    String HTMLText = HTMLTextField.getText();
                    System.out.println(HTMLText);
                    Extraction_Method();
            });
                VBox vBox = VBoxBuilder.create().children(button_browse).build();
                root.getChildren().add(vBox);
                
            gridpane.add(button_browse, 0, 3);
           
            Image anotherIcon = new Image("/webdroid/Webdroid Icon Initial.png");
            primaryStage.getIcons().add(anotherIcon);

            primaryStage.setScene(scene);
            primaryStage.show();
        }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    private static void Extraction_Method(){
        
        String s; 
        String temp;
        TokenScanner scan = new TokenScanner();
        TokenScanner.TOKEN_CODE t;
        KeywordList kw = new KeywordList();
        KeywordList.KW_CODE kc;
                
                s = ReadHTMLFile();
                scan.setSource(s);
               
                do{
                    t = scan.next_token();
                    kc = kw.SearchKeyword(scan.getLexeme());
                    temp = scan.getLexeme()+" - "+scan.getToken()+ " - "+ kc;
                    
                     System.out.println(temp);
                   }while(t != TokenScanner.TOKEN_CODE.EOF_TOKEN);
        
         
        
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
