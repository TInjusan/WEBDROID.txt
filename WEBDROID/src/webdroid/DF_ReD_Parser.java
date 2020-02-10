 
package webdroid;
import java.util.ArrayList;
 
public class DF_ReD_Parser {
    
    private ArrayList<String> lexemes = new ArrayList<String>();
    private ArrayList<String> Tokens = new ArrayList<String>();
    private ArrayList<String> List = new ArrayList<String>();
    private int i = 0;
    private String lastTmp = new String();
    
       public void setLexeme(String s){
        
       
        String temp;
        TokenScanner scan = new TokenScanner();
        TokenScanner.TOKEN_CODE t;
        KeywordList kw = new KeywordList();
        KeywordList.HTML_CODE kc;
        scan.setSource(s);
               
                do{
                    t = scan.next_token();
                    kc = kw.SearchKeyword(scan.getLexeme());
                    lexemes.add(scan.getLexeme());
                    Tokens.add(scan.getToken().toString());
                    
                   }while(t != TokenScanner.TOKEN_CODE.EOF_TOKEN);
      }
       
       public void HTML(){
           
           if(lexemes.get(i).equals("<"))
               i++;
           else
               Error(0);
          
           if(lexemes.get(i).equals("!"))
               i++;
           else
               Error(0);
          
           if(lexemes.get(i).equals("DOCTYPE"))
               i++;
           else
               Error(0);
            
           if(lexemes.get(i).equals("html"))
               i++;
           else
               Error(0);
 
           if(lexemes.get(i).equals(">"))
               i++;
           else
               Error(0);
           
           Element();
                      
           if(i<lexemes.size())
               HTML();
       } 
       
       public void Element(){
           
           Non_void_element();
           Void_element();
           
       }
       
       
       public void Non_void_element(){
                
                if(lexemes.get(i).equals("<"))
                    i++;
                else
                    Error(0);    
                
                  
       }
       
       public void Void_element(){
           
           
       }
       
            
       public void Error(int code){
           
           switch (code){
               //Generic Error Message:
               case 0: System.out.println("Syntax Error");
               
           }
                   
       }
}
