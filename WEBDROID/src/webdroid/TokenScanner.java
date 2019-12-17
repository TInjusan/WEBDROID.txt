package webdroid;

public class TokenScanner {  
    
    public enum TOKEN_CODE {
    ERROR, 
    WORD, 
    NUMBER, 
    SPECIAL_CHARACTER, 
    STRING, 
    ANGLE_BRACKET_OPEN, 
    ANGLE_BRACKET_CLOSE, 
    FORWARD_SLASH,  
    EOF_TOKEN
    }

    
    
    public String source;
    public TOKEN_CODE token;
    private int cchar_ptr;
    private char cchar;
    private  CharMap table = new CharMap();
    private String lexeme;
    TokenScanner(){
        
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
         
        while(table.getChar() == CharMap.CHAR_TYPE.WHITESPACE){
             cchar = get_source_char();
             table.setChar(cchar);
        }
    }
    
    public TOKEN_CODE next_token(){
        
        table.setChar(cchar);
        
        skip_whitespace();
        CharMap.CHAR_TYPE arg_char_type = CharMap.CHAR_TYPE.ERROR;
        TOKEN_CODE arg_token_code = TOKEN_CODE.ERROR;
        
        switch(table.getChar()){
            case LETTER:
                arg_char_type = CharMap.CHAR_TYPE.LETTER;
                arg_token_code = TOKEN_CODE.WORD;
                get_token(arg_char_type, arg_token_code); 
                break;
            case SPECIAL:
                arg_char_type = CharMap.CHAR_TYPE.SPECIAL;
                arg_token_code = TOKEN_CODE.SPECIAL_CHARACTER;
                get_token(arg_char_type, arg_token_code); 
                break;
            case QOUTE:
                arg_char_type = CharMap.CHAR_TYPE.QOUTE;
                arg_token_code = TOKEN_CODE.STRING;
                get_string_token(arg_char_type, arg_token_code);
                break;
            case ANGLE_BRACKET_OPEN:
                arg_char_type = CharMap.CHAR_TYPE.ANGLE_BRACKET_OPEN;
                arg_token_code = TOKEN_CODE.ANGLE_BRACKET_OPEN;
                get_token(arg_char_type, arg_token_code); 
                break;
            case ANGLE_BRACKET_CLOSE:
                arg_char_type = CharMap.CHAR_TYPE.ANGLE_BRACKET_CLOSE;
                arg_token_code = TOKEN_CODE.ANGLE_BRACKET_CLOSE;
                get_token(arg_char_type, arg_token_code); 
                break;
            case FORWARD_SLASH:
                arg_char_type = CharMap.CHAR_TYPE.FORWARD_SLASH;
                arg_token_code = TOKEN_CODE.FORWARD_SLASH;
                get_token(arg_char_type, arg_token_code); 
                break;
            case DOT_CLASS:
                arg_char_type = CharMap.CHAR_TYPE.DOT_CLASS;
                arg_token_code = TOKEN_CODE.WORD;
                get_token(arg_char_type, arg_token_code); 
                break;
                
            case DIGIT:
                arg_char_type = CharMap.CHAR_TYPE.DIGIT;
                arg_token_code = TOKEN_CODE.NUMBER;
                get_number_token(arg_char_type, arg_token_code); 
                break;
                
            case EOF_TYPE:
                get_eof_token();
                break;
        }
        
     
        return token;
    }
    
    private void get_eof_token(){
            lexeme = "";
            token = TOKEN_CODE.EOF_TOKEN;
    }
    
    private void get_token(CharMap.CHAR_TYPE arg_char_type, TOKEN_CODE arg_token_code){
        
       
        
            lexeme = String.valueOf(cchar);   
            boolean run = true;
            while(run){
            
                cchar = get_source_char();
                table.setChar(cchar);
                if(table.getChar() == arg_char_type){
                    lexeme+=String.valueOf(cchar);
                }
                else
                    run = false;
            }
            
             
        token = arg_token_code;
      }
       
     private void get_string_token(CharMap.CHAR_TYPE arg_char_type, TOKEN_CODE arg_token_code){
        
          
            lexeme = String.valueOf(cchar);   
           
            do{
                cchar = get_source_char();
                table.setChar(cchar);
                lexeme+=String.valueOf(cchar);
                
            }while(table.getChar() != arg_char_type);
            
        cchar = get_source_char();   
        token = arg_token_code;
      }    
     
          private void get_number_token(CharMap.CHAR_TYPE arg_char_type, TOKEN_CODE arg_token_code){
        
            int count_dot = 0;  
            lexeme = String.valueOf(cchar);   
            boolean run = true;
                
            while(run){
            
                cchar = get_source_char();
                table.setChar(cchar);
                
                if(table.getChar() == CharMap.CHAR_TYPE.DOT_CLASS){
                    count_dot++;
                    
                }
                
                if( (table.getChar() == arg_char_type || table.getChar() == CharMap.CHAR_TYPE.DOT_CLASS) && count_dot < 2  ){
                    lexeme+=String.valueOf(cchar);
                 }
                else
                    run = false;
                
                
            }
            
                  
         token = arg_token_code;
      } 
     
     
    //getter method
    public TOKEN_CODE getToken(){
         return token; 
    }
    public String getLexeme(){
                                    
        return lexeme;
        
    }
}
