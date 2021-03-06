package webdroid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The task of this class is scanning each and every character in the file
 * and identify them if they are digit, letter, etc., 
 * and group them into one string called lexeme.
 * The lexeme is then associated with TOKEN_CODE of which the 
 * string belongs to. The output of this class is mainly the
 * Lexeme and Token stream.
 */
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
    private int newlinecount;
    private Map<Integer, Integer> newline = new LinkedHashMap<>();
    private char cchar;
    private char previous_cchar;
    private String lexeme;
    private ArrayList<String> lexemes = new ArrayList<>();
    private ArrayList<TOKEN_CODE> tokens = new ArrayList<>();
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;

    CharacterScanner() { //constractor initializing the object properties
        source = "";
        cchar = ' ';
        lexeme = "";
        previous_cchar = ' ';
        newlinecount = 1;
    }

    //setter method
    private void setSource(String s) {
        this.source = s;
        cchar_ptr = 0;
        this.cchar = get_source_char();
    }

    private char get_source_char() {

        char c;
        if (cchar_ptr + 1 <= source.length()) {
            c = source.charAt(cchar_ptr);
            cchar_ptr++;
        } else {
            c = 127; //setting to End of File
        }
        return c;
    }

    private char get_previous_char() {
        try {
            return cchar_ptr - 2 < 0?
                   source.charAt(cchar_ptr - 1):
                   source.charAt(cchar_ptr - 2);
        } catch (StringIndexOutOfBoundsException e) {
            return source.charAt(0);
        }
    }

    private void skip_whitespace() {
        if (Character.isWhitespace(cchar)) {
            if (cchar == '\n') {
                //capturing new line in the text.
                //for future error handling purposes.
                newline.put(lexemes.size(), newlinecount);
                newlinecount++; 
            }
            cchar = get_source_char();
            skip_whitespace();
        }   // Recursively go through all next whitespace 
    }

    private TOKEN_CODE next_token() {
        try {
            //preserving the previous character
            //before skipping whitespaace                           
            //for string element extraction use. 
            previous_cchar = get_previous_char(); 
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Blank HTML");
        }

        skip_whitespace();
        //Check statement to extract string element while not end of file
        if (previous_cchar == '>' && cchar != '<' && cchar != 127) { 
            get_string_element();
        } else {
            
            //Grouping the characters into lexemes and associate token
        
            if (Character.isLetter(cchar)) {
                get_word_token();
            } else if (Character.isDigit(cchar)) {
                get_number_token();

            } else if (cchar == '\"') {  // Getting a string enclosed 
                get_string_token();      // in quotations ("")
            } else if (cchar == 127) {
                lexeme = "";
                newline.put(lexemes.size(), newlinecount);
                token = TOKEN_CODE.EOF_TOKEN;  //This characater is the end of the file
            } else if (cchar <= 32 && cchar >= 126) {  // These are the unsupported characters
                token = TOKEN_CODE.ERROR;
                cchar = get_source_char();
            } else {
                lexeme = String.valueOf(cchar);
                token = TOKEN_CODE.SPECIAL_CHARACTER;
                cchar = get_source_char();
            }
            previous_cchar = cchar;
        }
        return token;
    }

    private void get_word_token() {
        lexeme = "";   // Set the first character of the lexeme

        while (Character.isLetter(cchar) || cchar == '-') {
            lexeme += String.valueOf(cchar); //Concatenate the 
            cchar = get_source_char();       //characters into the lexeme
        }

        if (lexeme.equals("h") && Character.isDigit(cchar)) {
            lexeme += String.valueOf(cchar);  
            cchar = get_source_char();        
        }
        token = TOKEN_CODE.WORD; //Setting the Token
    }                            //of the lexeme into WORD

    private void get_string_token() {
        lexeme = String.valueOf(cchar); //Set the first 
                                        //character of the lexeme
        do {
            cchar = get_source_char();
            lexeme += String.valueOf(cchar);            
            
        } while (cchar != '\"' && cchar_ptr + 1 <= source.length());
        lexeme = lexeme.replace("\"", "").trim();         
        cchar = get_source_char();
        token = TOKEN_CODE.STRING;
    }

    private void get_string_element() {

        lexeme = "";
        do{
            lexeme += String.valueOf(cchar);
            cchar = get_source_char();
            
        }
        while (cchar != '<' && cchar != 127);  

        token = TOKEN_CODE.STRING_ELEMENT;
    }

    private void get_number_token() {

        lexeme = String.valueOf(cchar);   // Set the 1st character of the lexeme
        while (Character.isDigit(cchar)) {
            lexeme += String.valueOf(cchar);//Concatenate the characters 
            cchar = get_source_char();      //into the lexeme
        }
        token = TOKEN_CODE.NUMBER;  //Setting the Token of the lexeme into WORD
    }

    //getter method
    public TOKEN_CODE getToken() {   return token;  }
    public String getLexeme()    {   return lexeme; }
    
     CharacterScanner(String s) {
        source = "";
        cchar = ' ';
        lexeme = "";
        previous_cchar = ' ';
        newlinecount = 1;
        setSource(s);
        do {
            t = next_token();
            kc = kw.SearchKeyword(getLexeme());
            lexemes.add(getLexeme());
            tokens.add(getToken());  
          } while (t != TOKEN_CODE.EOF_TOKEN);
       
    }

    public void Run_Style_Scanner(String s) {
        int i = 0;
        do {
            lexeme = "";
            switch (s.charAt(i)) {
                case 127:
                    token = TOKEN_CODE.EOF_TOKEN;  
                    i++;
                    break;
                case ':':
                    lexeme = ":";
                    token = TOKEN_CODE.SPECIAL_CHARACTER;
                    i++;
                    break;
                case ';':
                    lexeme = ";";
                    token = TOKEN_CODE.SPECIAL_CHARACTER;
                    i++;
                    break;
                default:
                    do {
                        lexeme = lexeme + s.charAt(i);
                        i++;
                    } while (i < s.length() && s.charAt(i) != ':' && s.charAt(i) != ';');

                    token = TOKEN_CODE.WORD;
                    break;
            }

            lexemes.add(lexeme.trim());
            tokens.add(token);
            kc = kw.SearchKeyword(lexeme.trim());
        } while (i < s.length());
        lexemes.add("");
        tokens.add(TOKEN_CODE.EOF_TOKEN);
    }

    public ArrayList<String> getLexemeStream() {
        return lexemes;
    }

    public ArrayList<TOKEN_CODE> getTokenStream() {
        return tokens;
    }

    public int getLine(int lexeme_index) {
        lexeme_index++;
        while (newline.get(lexeme_index) == null) {
            lexeme_index++;
        }
        return newline.get(lexeme_index);
    }
}
