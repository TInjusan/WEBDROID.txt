package webdroid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import webdroid.SymbolTable.ElementNode;

/**
 * This class is the implementation of Semantic Analysis phase of the translation.
 * It is responsible on validating each element node properties if they acceptable by the WEBDROID.
 * It then simultaneously modify the each element node in the Symbol Table
 * for the Abstract Syntax Tree to become more suitable to translate in Android XML.
 */
public class SemanticAnalyzer {

    private static List<ElementNode> children;
    private static String ErrorMessage = "";
    private List<ElementNode> removal = new ArrayList<>();

    SemanticAnalyzer() {
        buildSymbolTree(SymbolTable.root);  //First build  
    }                                       //of AST after Syntax Analysis

    public void ExecuteAnalyzer() {
        analyze();
        removeElements();
        buildSymbolTree(SymbolTable.root);   
    }

    private void analyze() {

        for (ElementNode e : SymbolTable.get_html_table()) {
            //Semantic Analysis and Modification for HTML attributes
            for (Map.Entry udp : e.getUDP().entrySet()) {   
                String value = udp.getValue().toString();
                String key = udp.getKey().toString();
                if (value.equals("")) {
                    Error(10, key, "");
                } else {
                    if (Character.isDigit(value.charAt(0))
                            && !key.equals("placeholder")
                            && !key.equals("height")
                            && !key.equals("width")) {
                        Error(4, value, key);
                    }
                    switch (udp.getKey().toString()) {
                        case "id":
                            if (searchDuplicate(key, value) > 1) {
                                Error(5, value, "");
                            }
                            break;
                        case "for":
                            if (searchDuplicate(key, value) > 1) {
                                Error(7, value, "");
                            }
                            break;
                    }
                }
            }   
            // Semantic Analysis and Modification for HTML Element     
            switch(e.getTag()){
            case "input": 
                //specifying input elements - replacing the input tag name 
                //with the appropriate one
                if (e.getUDP().get("type") == null || e.getUDP().get("type").equals("text")) {
                    e.setTag("textbox");
                    e.addUDP("type", "text");
                } else {
                    switch (e.getUDP().get("type")) {
                        case "email":
                        case "password":
                            e.setTag("textbox");
                            break;
                        case "radio":
                            e.setTag("radiobutton");
                            e.removeUDP("type");
                            break;
                        case "checkbox":
                            e.setTag("checkbox");
                            e.removeUDP("type");
                            break;
                        case "button":
                        case "submit":
                            try {
                                if (e.getUDP().get("value").equals("")) {
                                    Error(6, "","");
                                } else { //put the value of value property to text so that the code generator will only take
                                    //text property in xml
                                    e.addUDP("text", e.getUDP().get("value"));
                                    e.setTag("button");
                                    e.removeUDP("type");
                                    e.removeUDP("value");
                                }

                            } catch (NullPointerException ee) {
                                Error(6, "","");
                            }
                            break;

                        default:
                            Error(0, e.getUDP().get("type"),"");
                            break;
                    }
                }
                    break;
            case "p":           
            case "title":
            case "button":
                    childStringValidation(e);               
                    break;
            case "label":
                // Assigning the text to the element using the for attribute
                    childStringValidation(e);
                    if (e.getUDP().get("for") != null) {
                        String id = e.getUDP().get("for");
                        String text = e.getUDP().get("text");

                        if (searchDuplicate("for", id) > 0) {
                            setText(id, text);
                            removal.add(e);
                        } else {
                            Error(1, id, "");
                        }
                    }
                    break;
            case "h1":
                    e.addUDP("font-size", "25sp");   //Size modification
                    e.addUDP("font-weight", "700");  //Setting text as bold
                    childStringValidation(e);
                    break;
            case "h2":                    
                    e.addUDP("font-size", "20sp");  //Size modification
                    e.addUDP("font-weight", "700"); //Setting text as bold
                    childStringValidation(e);
                    break;
            case "h3":
                    e.addUDP("font-size", "15sp");  //Size modification
                    e.addUDP("font-weight", "700"); //Setting text as bold
                    childStringValidation(e);
                    break;
            case "img":
                String full_source = ""; 
                if (e.getUDP().get("src") == null) {
                    Error(8, "","");
                } else {
                     full_source = WEBDROID.html_location.getText() + "\\" + e.getUDP().get("src").trim();
                     File file = new File(full_source);
                     try (Scanner reader = new Scanner(file)) {
                           e.addUDP("src", full_source);
                     }
                     catch (FileNotFoundException o) {
                           Error(9,full_source,"");
                     }    
                } 
                break;
            case "select":
                ArrayList<String> R = new ArrayList<>();
                if (e.getUDP().get("name") == null) {
                    Error(2, "","");
                } else {
                    String selectname = e.getUDP().get("name");

                    children = getChildrenById(e.getID());
                    for (ElementNode child_node : children) {
                        R.add(getChildString(child_node));
                        removal.add(child_node);
                    }
                    SymbolTable.ArrayString.put(selectname, R);
                } 
                break;
            case "br":
                removal.add(e);
                break;
            }
        }
    }

    private void removeElements() {
        for (ElementNode remove : removal) {
            SymbolTable.RemoveTag(remove);
        }
    }
    
    private void childStringValidation(ElementNode e){
                children = getChildrenById(e.getID());
                if (children.size() > 1) {
                     Error(3, "","");
                } 
                else { //Setting the text from its child string element
                    e.addUDP("text", children.get(0).getUDP().get("text"));
                    removal.add(children.get(0));
                }

    }
    
    private int searchDuplicate(String key, String value) {
        int i = 0;
        for (ElementNode e : SymbolTable.get_html_table()) {
            if (e.getUDP().get(key) != null) {
                if (value.equals(e.getUDP().get(key))) {
                    i++;
                }
            }
        }
        return i;
    }

    private void setText(String ID, String Text) {
        for (ElementNode e : SymbolTable.get_html_table()) {
            if (e.getUDP().get("id") != null) {
                if (ID.equals(e.getUDP().get("id"))) {
                    e.addUDP("text", Text);
                }
            }
        }
    }

    public String getChildString(ElementNode element) {
        String s = "";
        children = element.getChildrenElement();
        for (ElementNode child_node : children) {
            s = child_node.getUDP().get("text");
            removal.add(child_node);
        }
        return s;
    }

    public void buildSymbolTree(ElementNode element) {
        children = getChildrenById(element.getID());
        element.setChildrenElement(children);
        if (children.isEmpty()) {
            return;
        }
        for (ElementNode child_node : children) {
            buildSymbolTree(child_node);
        }
    }

    public List<ElementNode> getChildrenById(int id) {
        List<ElementNode> children_ = new ArrayList<>();
        for (ElementNode e : SymbolTable.get_html_table()) {
            if (e.getParent_ID() == id) {
                children_.add(e);
            }
        }
        return children_;
    }

    private void Error(int code, String s1, String s2) {

        switch (code) {
           case 0: ErrorMessage += "Input type: "+s1+" is not a supported type for input tag."; break;
           case 1: ErrorMessage += "Element reference of Label with attribute \"for: "+s1+"\". Doesn't exist"; break;
           case 2: ErrorMessage += "Please make sure to assign a name to each select tag."; break;
           case 3: ErrorMessage += "Text Tags should only have one child."; break; 
           case 4: ErrorMessage += "Invalid "+s2+": "+s1+". First character should not be a digit"; break;
           case 5: ErrorMessage += "Multiple usage of id: "+s1+" is not allowed."; break;
           case 6: ErrorMessage += "Please make sure to set the value property of a button as it is the text of button itself"; break;
           case 7: ErrorMessage += "Multiple usage of id ["+s1+"] as a value of \"for\" attribute is not allowed."; break;
           case 8: ErrorMessage += "Image tag should always have a source value."; break;
           case 9: ErrorMessage += "Image tag source value not found: "+s1+"."; break;
           case 10: ErrorMessage += "Blank value of attribute: "+s1+" is not allowed."; break;
        }
        WEBDROID.ErrorDetected = true;
        ErrorMessage += "\n";
    }

    public String showSemanticError() {
        return ErrorMessage;
    }

    public void clearErrorMessage() {
        ErrorMessage = "";
    }
}
