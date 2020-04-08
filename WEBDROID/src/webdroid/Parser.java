package webdroid;
import java.util.ArrayList;
import java.util.*;
import webdroid.CharacterScanner.TOKEN_CODE;
import webdroid.KeywordList.HTML_CODE;
import webdroid.SymbolTable.ElementNode;

/**
 * This class is the implementation of Recursive Descent Parsing Algorithm
 * with stack data structure to track the closing of non-void tags.
 * Included here is the parsing of style attribute to extract additional
 * property of the HTML element node.
 */
public class Parser {

    private ArrayList<String> lexeme = new ArrayList<>();
    private ArrayList<TOKEN_CODE> token = new ArrayList<>();
    private int i = 0;  //iteration variable to select each lexeme in the stream.  
    private int id = 0; //id for each HTML Element Node.
    private Stack<ElementNode> html_element_stack = new Stack<>(); //For non-void  
    private int current_parent_id;
    private static String ErrorMessage = "";
    KeywordList kw = new KeywordList();
    KeywordList.HTML_CODE kc;
    CharacterScanner HTML_Scanner;
    
    public void setHTMLParser(String s) {
        HTML_Scanner = new CharacterScanner(s);
        lexeme = HTML_Scanner.getLexemeStream();
        token = HTML_Scanner.getTokenStream();
    }

    public void PARSE() {        
            HTML();  
    }

    public void HTML() {        
        try {
        if (lexeme.get(i + 1).equals("!")) {  //lookahead if there's 
            if ((lexeme.get(i) +              //a Doctype declaration
                 lexeme.get(i + 1) +
                 lexeme.get(i + 2) + " " + 
                 lexeme.get(i + 3) + 
                 lexeme.get(i + 4)).
                 equals("<!DOCTYPE html>")) 
                 i += 4;  // proceed to the first element
           else {
                Error(-1, "");
            }
        } else {  // If no DOCTYPE declaration then deduct one so the
            i--;  // checking of element starts with zero
        }
        
            Element();
        } catch (IndexOutOfBoundsException d) {
            Error(5, "");
            //Lookahead goes beyond the size of Lexeme stream.
        }
    }

    public void Element() {

        if (lexeme.get(i + 1).equals("<")) { // next Lexeme can either be
            nextLexeme();                    //Void or Non-void tag

            kc = kw.SearchKeyword(lexeme.get(i + 1)); //Getting the nextLexeme keyword.
            switch (kc) {                              
                case HTML_NONVOID_TAG: 
                    try {
                        current_parent_id = html_element_stack.peek().getID();
                    } catch (EmptyStackException e) {
                        current_parent_id = -1; //setting the root node id
                    }
                    Non_void_element(current_parent_id);
                    break;
                case HTML_VOID_TAG:  
                    Void_element();
                    break;
                case HTML_SPECIAL_CHARACTER:
                    //Do nothing because it is a supported special character
                    break;
                default:
                    Error(0, lexeme.get(i + 1));
                    break;
            }
        } else {
            //next Lexeme is a String element
            nextLexeme();

            HashMap<String, String> Attribute = new HashMap<>();
            Attribute.put("text", lexeme.get(i));
            SymbolTable x = new SymbolTable();

            try {
                SymbolTable.ElementNode node = x.new ElementNode(id, "String_element", HTML_CODE.HTML_STRING_ELEMENT, Attribute, html_element_stack.peek().getID());
                SymbolTable.AddToTable(node);
            } catch (EmptyStackException e) {
                Error(5, "");
            }
            id++;
        }

    }

    public void Non_void_element(int current_parent) {
        HashMap<String, String> Attribute = new HashMap<>();

        nextLexeme(); //to move to the next lexeme to get the tag name
        String current_tag = lexeme.get(i);
        do {
            nextLexeme();
            kc = kw.SearchKeyword(lexeme.get(i));
            Attribute.putAll(Attribute());  // Calling of Attribute method and getting the properties
        } while (kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME);

        //instantiate html node tag node properties
        SymbolTable x = new SymbolTable();
        //Initialization of properties of the html node
        SymbolTable.ElementNode node = x.new ElementNode(id, current_tag, 
                                                         kw.SearchKeyword(current_tag), 
                                                         Attribute,
                                                         current_parent);     
        //Adding the html node object to the Symbol Table 
        SymbolTable.AddToTable(node);

        if (node.getParent_ID() == -1) 
            SymbolTable.root = node; // Setting root node
        
        //Storing the node to the stack for non-void hierarchy implementation
        html_element_stack.push(node);
        id++;

        if (!(lexeme.get(i).equals(">")))  // Ending of the opening tag
            Error(1, current_tag);
      
        try {
            while (!(lexeme.get(i) + lexeme.get(i + 1)).equals("</")) {  
                // this is where the recursion happens - repeating element.
                Element();
            }
        } catch (IndexOutOfBoundsException d) {
            //Lookahead goes beyond the size of Lexeme stream.
            //This means that it was not able to found the closing tag
            Error(4,"");
            
        }
        
        nextLexeme();
        try {
            // Get the current tag which is on top of the stack 
            if (current_tag.equals(lexeme.get(i + 1))) {  // Compare the name of the tag vs the one on top of the stack                      
                html_element_stack.pop();         // Removing of the top tag because it has been closed.
                nextLexeme();
                
                try {
                    if (lexeme.get(i + 1).equals(">")) {
                        nextLexeme();  //Move to the next lexeme which can be new element or string element
                    } else {
                        Error(1, current_tag);
                    }
                } catch (IndexOutOfBoundsException d) {
                    //Lookahead goes beyond the size of Lexeme stream.
                }
            } else {
                Error(3, "");
            }
        } catch (IndexOutOfBoundsException d) {
            //Lookahead goes beyond the size of Lexeme stream.
        }

       

    }

    public void Void_element() {
        HashMap<String, String> Attribute = new HashMap<>();
        nextLexeme();
        String current_tag = lexeme.get(i);

        do {
            nextLexeme();
            kc = kw.SearchKeyword(lexeme.get(i));

            Attribute.putAll(Attribute());

        } while (kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME);

        SymbolTable x = new SymbolTable();
        SymbolTable.ElementNode 
        node = x.new ElementNode
                    (id, current_tag, kw.SearchKeyword(current_tag), 
                    Attribute, html_element_stack.peek().getID());
        SymbolTable.AddToTable(node);
        id++;
        if (!(lexeme.get(i).equals(">"))) {
            Error(1, current_tag); //Move to the next lexeme which 
        }                          //can be new element or string element

    }

    private HashMap<String, String> Attribute() {
        HashMap<String, String> Attribute = new HashMap<>();
        String property;

        if (kc == KeywordList.HTML_CODE.HTML_PROPERTY_NAME) {
            property = lexeme.get(i);   //storing the property name to 
                                        //be passed to the hashmap return key
            nextLexeme(); 
             // Look for the equal sign if the lexeme is a property  
            if (lexeme.get(i).equals("="))  
                nextLexeme();
            else  
                Error(2, property);           

            // Checking if the next lexeme is a string
            if (!(token.get(i) == TOKEN_CODE.STRING))  
                Error(6, "");
            else if (property.equals("style")) 
                Attribute.putAll(Styles(lexeme.get(i)));
            else  
                Attribute.put(property, lexeme.get(i));
        }
        return Attribute;
    }

    private HashMap<String, String> Styles(String style_string) {
        HashMap<String, String> style_value = new HashMap<>();
        CharacterScanner CSS_Scanner = new CharacterScanner();
        CSS_Scanner.Run_Style_Scanner(style_string);
        ArrayList<String> style_lexeme = CSS_Scanner.getLexemeStream();
        ArrayList<TOKEN_CODE> style_token = CSS_Scanner.getTokenStream();

        int j = 0;
        String style_name = "";
        KeywordList.HTML_CODE kk;

        do {
            kk = kw.SearchKeyword(style_lexeme.get(j));
            if (kk == KeywordList.HTML_CODE.CSS_PROPERTY) {
                style_name = style_lexeme.get(j);
                j++; //Next Lexeme

                if (style_lexeme.get(j).equals(":")) {
                    j++;
                    style_value.put(style_name, style_lexeme.get(j));
                } else {
                    Error(8, style_name);
                }

            } else {
                Error(7, style_lexeme.get(j));
            }
            j++; //Next Lexeme

            if (style_token.get(j) != TOKEN_CODE.EOF_TOKEN) {

                if (style_lexeme.get(j).equals(";")) 
                    j++; //Next Lexeme
                else  
                    Error(9, style_name);
                 
            }
        } while (style_token.get(j) != TOKEN_CODE.EOF_TOKEN);

        return style_value;
    }
    //Moving from one lexeme to the next one.
    private void nextLexeme() {   i++;   }  

    private void Error(int code, String s) {

        switch (code) {
            case -1: ErrorMessage += "Line " + HTML_Scanner.getLine(i) 
                    + ": Incorrect/Unsupported DOCTYPE declaration.";
                break;
            case 0:  ErrorMessage += "Line " + HTML_Scanner.getLine(i)
                    + ": Unsupported tag \"" + s + "\"";
                break;
            case 1:  ErrorMessage += "Line " + HTML_Scanner.getLine(i) 
                    + ": Missing closing angle bracket '>' for the tag: " + s;
                break;
            case 2:  ErrorMessage += "Line " + HTML_Scanner.getLine(i) 
                    + ": Missing equal sign '=' for an attribute of " + s;
                break;
            case 3:  ErrorMessage += "Line " + HTML_Scanner.getLine(i) 
                    + ": Closing tag " + lexeme.get(i + 1) 
                    + ". Incorrect tag name to close. It should be: " 
                    + html_element_stack.peek().getTag();
                break;
            case 4: ErrorMessage += "Last Line:"
                    + ": Missing closing tag for " + html_element_stack.peek().getTag();
                    html_element_stack.pop();
                break;
            case 5: ErrorMessage += 
                    "HTML file should at least have one and/or start with a non-void tag.";
                break;
            case 6: ErrorMessage +=  "Line " + HTML_Scanner.getLine(i) 
                    + ": Property value should be a string enclosed in quotation marks (\") ";
                break;
            case 7: ErrorMessage +=  "Line " + HTML_Scanner.getLine(i) 
                    + ": Unsupported style attribute (" + s + ")";
                break;
            case 8: ErrorMessage +=  "Line " + HTML_Scanner.getLine(i) 
                    + ": Missing colon ':' for the style " + s;
                break;
            case 9: ErrorMessage +=  "Line " + HTML_Scanner.getLine(i) 
                    + ": Missing semi-colon ';' for the style " + s;
                break;
        }
        WEBDROID.ErrorDetected = true;
        ErrorMessage += "\n";
    }
    public String showSyntaxError()        { return ErrorMessage; }
    public static void clearErrorMessage() { ErrorMessage = "";   }
}
