// The task of this class is scanning each and every character in the file
// and identify them if they are digit, letter, etc., and group them into one string called lexeme
// The lexeme is then associated with TOKEN_CODE of which the string belongs to.
package webdroid;

import java.util.ArrayList;

public class CharacterScanner {  
    
    public enum TOKEN_CODE {
                ERROR, 
                WORD, 
                NUMBER, 
                SPECIAL_CHARACTER, 
                STRING, 
                STRING_ELEMENT,
                WHITESPACE,
                EOF_TOKEN
    }

    public String source;
    public TOKEN_CODE token;
    private TOKEN_CODE t;
    private int cchar_ptr;
    private char cchar;
    private char previous_cchar;
    private String lexeme;
    private ArrayList<String> lexemes = new ArrayList<>();
    private ArrayList<TOKEN_CODE> tokens = new ArrayList<>();
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;
    
    CharacterScanner(){ //constractor initializing the object properties
        source = "";
        cchar = ' ';
        lexeme = "";
        previous_cchar = ' ';
    }
    
    //setter method
    public void setSource(String s){
        this.source = s;
        cchar_ptr = 0;
        this.cchar = get_source_char();
    }
    
    
    private char get_source_char(){
        
        char  c;
        if(cchar_ptr + 1 <= source.length()){
            c = source.charAt(cchar_ptr);
            cchar_ptr++;
        }
        else            
            c = 127; //setting to End of File
        
      return c;  
    }
    
    private char get_previous_char(){
        if(cchar_ptr-2<0){
            return source.charAt(cchar_ptr-1);
        }
        else
            return source.charAt(cchar_ptr-2);
    }   
    
    private void skip_whitespace(){         
        if(Character.isWhitespace(cchar)) {
             cchar = get_source_char();    
             skip_whitespace();
        }    // Recursively go through all next whitespace 
         
    }
    
    /**
     * 
     * @return 
     */
    
    public TOKEN_CODE next_token(){
      
        previous_cchar= get_previous_char(); //preserving the previous character before skipping whitespaace for string element extraction use.
        skip_whitespace(); 
        //Grouping the characters into lexemes and associate token
 
         if(previous_cchar=='>'&&cchar!='<'&&cchar!=127){ //Check statement to extract string element while not end of file
            get_string_element();
        }
        else{

            if(Character.isLetter(cchar)){ 
                    get_word_token(); 
            }
            else if(Character.isDigit(cchar)){
                    get_number_token(); 

            }
            else if(cchar=='\"'){  // Getting a string enclosed in quotations ("")
                    get_string_token();
            }

            else if(cchar==127){
                lexeme = "";
                token = TOKEN_CODE.EOF_TOKEN;  //This characater is the end of the file
            }
            else if(cchar <= 32 && cchar >=126 ){  // These are the unsupported characters
                token = TOKEN_CODE.ERROR;
                cchar = get_source_char();
            }
            else {
                lexeme = String.valueOf(cchar);
                token  = TOKEN_CODE.SPECIAL_CHARACTER;
                cchar  = get_source_char();
            }  
            previous_cchar = cchar;
          
        }
        return token;
    } 
    
    private void get_word_token(){
            lexeme = "";   // Set the first character of the lexeme
              
              while(Character.isLetter(cchar) || cchar == '-'){
                    lexeme+=String.valueOf(cchar); //Concatenate the characters into the lexeme
                    cchar = get_source_char();
              }
              
            if(lexeme.equals("h")&&Character.isDigit(cchar)){
                    lexeme+=String.valueOf(cchar); //Concatenate the characters into the lexeme
                    cchar = get_source_char();
            }  
            token = TOKEN_CODE.WORD;  //Setting the Token of the lexeme into WORD
      }
       
     private void get_string_token(){  
            lexeme = String.valueOf(cchar);    // Set the first character of the lexeme
           
                do{
                    cchar = get_source_char();
                    lexeme+=String.valueOf(cchar);

                }while(cchar != '\"');
            lexeme=lexeme.replace("\"", "");
            cchar = get_source_char();   
            token = TOKEN_CODE.STRING;
      }   
      private void get_string_element(){  
               
          
                lexeme = String.valueOf(cchar);
                while(cchar!='<'){
                    cchar = get_source_char();
                    
                    if(cchar!='<')
                    lexeme+=String.valueOf(cchar);
                } 
                
         
             token = TOKEN_CODE.STRING_ELEMENT;   
      } 
     
          
      private void get_number_token(){
        
            lexeme = String.valueOf(cchar);   // Set the first character of the lexeme
              
                while(Character.isDigit(cchar)){
                      lexeme+=String.valueOf(cchar); //Concatenate the characters into the lexeme
                      cchar = get_source_char();
                }
              
            token = TOKEN_CODE.NUMBER;  //Setting the Token of the lexeme into WORD
      } 
      
    //getter method
    public TOKEN_CODE getToken(){
         return token; 
    }
    public String getLexeme(){                                    
        return lexeme;        
    }
    
    public void Run_Character_Scanner(String s){
        
                setSource(s);
               
                do{
                    t = next_token();
                    kc = kw.SearchKeyword(getLexeme());
                     //Printing of token stream // Testing
                     //  System.out.println(getLexeme()+"\t\t\t\t\t"+"- "+getToken()+"\t\t\t\t\t\t"+"- "+ kc);
                                 
                    lexemes.add(getLexeme());
                    tokens.add(getToken());
                   
                   }while(t != TOKEN_CODE.EOF_TOKEN);
                
    }
        
    public ArrayList<String> getLexemeStream(){
        return lexemes;
    }
    public ArrayList<TOKEN_CODE> getTokenStream(){
        return tokens;
    }
    
    
}
