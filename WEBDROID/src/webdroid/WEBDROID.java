 
package webdroid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class WEBDROID {

    
    public static void main(String[] args) {
        String s = "";
        TokenScanner scan = new TokenScanner();
        TokenScanner.TOKEN_CODE t;
        KeywordList kw = new KeywordList();
        KeywordList.KW_CODE kc;
        
            try {
                File htmlfile = new File("D:\\Computer Science\\Senior's Project\\Prototype\\WEBDROID\\src\\HTML FILE.html");
            try (Scanner myReader = new Scanner(htmlfile)) {  
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    s +=data;
                }
                scan.setSource(s);
                do{
                    t = scan.next_token();
                    kc = kw.SearchKeyword(scan.getLexeme());
                    System.out.println(scan.getToken()+ " - "+scan.getLexeme()+" - "+  kc);
                    
                }while(t != TokenScanner.TOKEN_CODE.EOF_TOKEN);
            }
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
              } 
        
    }
    
}
