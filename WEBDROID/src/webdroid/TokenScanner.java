package webdroid;
 
public class TokenScanner {  
    
    public enum TOKEN_CODE {
                ERROR, 
                WORD, 
                NUMBER, 
                SPECIAL_CHARACTER, 
                STRING, 
                EOF_TOKEN
    }

    public String source;
    public TOKEN_CODE token;
    private int cchar_ptr;
    private char cchar;
    private String lexeme;
    
    TokenScanner(){ //constractor initializing the object properties
        
        source = "";
        cchar = ' ';
        lexeme = "";
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
    
    private void skip_whitespace(){         
        if(Character.isWhitespace(cchar)) {
             cchar = get_source_char();    
             skip_whitespace();
        }    // Recursively go through all next whitespace 
         
    }
    
    public TOKEN_CODE next_token(){
        
        
        skip_whitespace();
         
        //Grouping the characters into lexemes and associate token
        
        if(Character.isLetter(cchar)){ 
                get_letter_token(); 
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
        
             
        return token;
    } 
    
    private void get_letter_token(){
            lexeme = "";   // Set the first character of the lexeme
              
              while(Character.isLetter(cchar)){
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
            
            cchar = get_source_char();   
            token = TOKEN_CODE.STRING;
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
}
