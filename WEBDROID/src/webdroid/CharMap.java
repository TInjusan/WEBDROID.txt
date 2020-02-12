package webdroid;

public class CharMap {
    
    public enum CHAR_TYPE {
    WHITESPACE, 
    LETTER, 
    DIGIT, 
    SPECIAL, 
    QOUTE, 
    ANGLE_BRACKET_OPEN, 
    ANGLE_BRACKET_CLOSE, 
    FORWARD_SLASH, 
    HASH_ID, 
    DOT_CLASS,
    ERROR, 
    EOF_TYPE 
}

    
    public static final char  EOF_CHAR = 127;
    private static final int ALL_CHAR = 256;
   
    CHAR_TYPE[] char_table = new CHAR_TYPE[ALL_CHAR];
    public char s;
    
    
    CharMap(){  //constructor
        int i;
        
                //initializes all to error
                for(i = 0; i<ALL_CHAR; i++){
                    char_table[i] = CHAR_TYPE.ERROR;
                }
        
                
                  //initialize all special symbol characters
		for(i=0;i<ALL_CHAR;i++){
                if( !(i>=48&&i<=57) && !(i>=65&&i<=90) &&  !(i>=65&&i<=90) && !(i>=97&&i<=122) && (i<126 && i>32 ))
                  char_table[i]=CHAR_TYPE.SPECIAL;
                else if ( i <= 32 && i>=126 )
                  char_table[i]= CHAR_TYPE.ERROR;
            
        }
                
        	//letters 
		for(i='A';i<='Z';i++)
			char_table[i]=CHAR_TYPE.LETTER;

		for(i='a';i<='z';i++) 
			char_table[i] = CHAR_TYPE.LETTER;

		//digits 
		for(i='0';i<='9';i++)
			char_table[i] = CHAR_TYPE.DIGIT;
                        
                //Special Characters used in HTML
                        char_table['\"'] = CHAR_TYPE.QOUTE;
                        char_table['/'] = CHAR_TYPE.FORWARD_SLASH;
                        char_table['<'] = CHAR_TYPE.ANGLE_BRACKET_OPEN;
                        char_table['>'] = CHAR_TYPE.ANGLE_BRACKET_CLOSE;
                        char_table['#'] = CHAR_TYPE.HASH_ID;
                        char_table['.'] = CHAR_TYPE.DOT_CLASS;
                        
		//whitespaces 
			char_table['\n']=CHAR_TYPE.WHITESPACE; 
			char_table['\t']=CHAR_TYPE.WHITESPACE; 
			char_table[' ']=CHAR_TYPE.WHITESPACE;

	 
		//temporary EOF char 
			char_table[EOF_CHAR]=CHAR_TYPE.EOF_TYPE;
        
    }
    
   
    //getter method
    public CHAR_TYPE getChar(){
        return char_table[s];
    }
    
    //setter method
    public void setChar(char c){
        this.s = c;
    }
    
}
