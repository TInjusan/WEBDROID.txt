// This class is the collection of supported HTML keywords
// It will return a value from a given string that define its HTML_CODE

package webdroid; 
import java.util.HashMap;
import webdroid.SymbolTable.XML_node;

public class KeywordList {
    
    
    public enum HTML_CODE {NOT_FOUND, HTML_NONVOID_TAG,HTML_NONVOID_PROPERTY, 
                           HTML_VOID_TAG, HTML_PROPERTY_NAME, HTML_SPECIAL_CHARACTER, HTML_STRING_ELEMENT,
                            CSS_PROPERTY};
    
    //initializing the collection of keywords in key value pair of hashmap
    private  HashMap<String, HTML_CODE> Keyword = new HashMap<>();
    private HashMap<String, XML_node> html_xml = new HashMap<>();
    
    
     //constructor initializing the keywords
     KeywordList(){
            
            Keyword.put("/",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put("<",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put(">",HTML_CODE.HTML_SPECIAL_CHARACTER);
            Keyword.put("!",HTML_CODE.HTML_SPECIAL_CHARACTER);
            
            Keyword.put("action",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("color",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("class",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("placeholder",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("for",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("src",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("alt",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("height",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("href",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("width",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("value", HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("id",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("name",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("type",HTML_CODE.HTML_PROPERTY_NAME);
            Keyword.put("style",HTML_CODE.HTML_NONVOID_PROPERTY);             
                  
            Keyword.put("br",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("link",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("input",HTML_CODE.HTML_VOID_TAG);
            Keyword.put("img",HTML_CODE.HTML_VOID_TAG);
            
            Keyword.put("button",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("div",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("a",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("body",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("font",HTML_CODE.HTML_NONVOID_TAG);              
            Keyword.put("head",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("html",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("form",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("font",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h1",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h2",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("h3",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("label",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("nav",HTML_CODE.HTML_NONVOID_TAG);            
            Keyword.put("option",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("p",HTML_CODE.HTML_NONVOID_TAG);                        
            Keyword.put("select",HTML_CODE.HTML_NONVOID_TAG);
            Keyword.put("textarea",HTML_CODE.HTML_NONVOID_TAG);            
            Keyword.put("title",HTML_CODE.HTML_NONVOID_TAG);
             
            Keyword.put("background-color",HTML_CODE.CSS_PROPERTY); 
            Keyword.put("color",HTML_CODE.CSS_PROPERTY); 
            Keyword.put("font-family",HTML_CODE.CSS_PROPERTY); 
            Keyword.put("text-align",HTML_CODE.CSS_PROPERTY); 
            Keyword.put("font-family",HTML_CODE.CSS_PROPERTY);
            
            
            // initialization of android xml attributes

            
            // translation from HTML tag to Android XML tag
            SymbolTable x = new SymbolTable();
            
            SymbolTable.XML_node ScrollView = x.new XML_node(); 
            ScrollView.setXML_node("ScrollView","011000000000101");
            html_xml.put("html",ScrollView);
            
            SymbolTable.XML_node LinearLayout = x.new XML_node(); 
            LinearLayout.setXML_node("LinearLayout","111000000000110");
            html_xml.put("body", LinearLayout);
            html_xml.put("form", LinearLayout);
            html_xml.put("div", LinearLayout);
            
            SymbolTable.XML_node RadioGroup = x.new XML_node(); 
            RadioGroup.setXML_node("RadioGroup","011000000000000");
            html_xml.put("RadioButtonGroup", RadioGroup);
            
            SymbolTable.XML_node RadioButton = x.new XML_node(); 
            RadioButton.setXML_node("RadioButton","111000000000000");
            html_xml.put("radiobutton", RadioButton);
            
            SymbolTable.XML_node CheckBox = x.new XML_node(); 
            CheckBox.setXML_node("CheckBox","111000000000000");
            html_xml.put("checkbox", CheckBox);
            
            SymbolTable.XML_node Button = x.new XML_node(); 
            Button.setXML_node("Button","111000000000000");
            html_xml.put("button", Button);
            
            
            SymbolTable.XML_node EditText = x.new XML_node();  
            EditText.setXML_node("EditText", "111000001000000");
            html_xml.put("textbox", EditText);
            
            SymbolTable.XML_node TextView = x.new XML_node();  
            TextView.setXML_node("TextView","111010000000000");
            html_xml.put("label", TextView);
            html_xml.put("p", TextView);
            html_xml.put("h1", TextView);
            html_xml.put("h2", TextView);
            html_xml.put("h3", TextView);
            html_xml.put("String_element", TextView);
            
            SymbolTable.XML_node Spinner = x.new XML_node(); 
            Spinner.setXML_node("Spinner","111000000000000");
            html_xml.put("select", Spinner);
            
                                    
   }
     XML_node getXMLNode(String key){         
         return html_xml.get(key);
     }
          
     HTML_CODE SearchKeyword(String key){ 
                   
          if(Keyword.containsKey(key.toLowerCase())) 
            return  Keyword.get(key.toLowerCase());                
          else
            return HTML_CODE.NOT_FOUND;      
     }
     
     
     
  }
     
   
 