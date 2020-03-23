// This class is the collection of supported HTML keywords
// It will return a value from a given string that define its HTML_CODE

package webdroid; 
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import webdroid.SymbolTable.XML_node;

public class KeywordList {
    
    
    public enum HTML_CODE {NOT_FOUND, HTML_NONVOID_TAG,HTML_NONVOID_PROPERTY, 
                           HTML_VOID_TAG, HTML_PROPERTY_NAME, HTML_SPECIAL_CHARACTER, HTML_STRING_ELEMENT,
                            CSS_PROPERTY};
    
    public enum XML_CODE { XML_NONVOID, XML_VOID };
                                    
    //initializing the collection of keywords in key value pair of hashmap
    private  HashMap<String, HTML_CODE> Keyword = new HashMap<>();
    private HashMap<String, XML_node> html_xml = new HashMap<>();
    private HashMap<String, String> html_xml_attr = new HashMap<>();
    private HashMap<String, String> colors = new HashMap<>();
    private static Map<String, String> xml_properties = new LinkedHashMap<>();
    
     //constructor initializing the keywords
     KeywordList(){
            
         
            Keyword.put("/",HTML_CODE.valueOf("HTML_SPECIAL_CHARACTER"));
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
            Keyword.put("html",HTML_CODE.valueOf("HTML_NONVOID_TAG"));
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
            xml_properties.put("android:id","");
            xml_properties.put("android:layout_width","\"match_parent\"");
            xml_properties.put("android:layout_height","\"wrap_content\"");
            xml_properties.put("android:text","");
            xml_properties.put("android:textIsSelectable","\"false\"");
            xml_properties.put("android:textStyle","");
            xml_properties.put("android:textSize","");
            xml_properties.put("android:textColor","");
            xml_properties.put("android:importantForAutofill","\"no\"");
            xml_properties.put("android:hint","");
            xml_properties.put("android:inputType","");
            xml_properties.put("android:entries","");
            xml_properties.put("android:orientation","\"vertical\"");
            xml_properties.put("android:layout_gravity","\"top\"");
            xml_properties.put("xmlns:android","\"http://schemas.android.com/apk/res/android\"");
            
            colors.put("white","#FFFFFF");
            colors.put("azure","#F0FFFF");
            colors.put("mintcream","#F5FFFA");
            colors.put("snow","#FFFAFA");
            colors.put("ivory","#FFFFF0");
            colors.put("ghostwhite","#F8F8FF");
            colors.put("floralwhite","#FFFAF0");
            colors.put("aliceblue","#F0F8FF");
            colors.put("lightcyan","#E0FFFF");
            colors.put("honeydew","#F0FFF0");
            colors.put("lightyellow","#FFFFE0");
            colors.put("seashell","#FFF5EE");
            colors.put("lavenderblush","#FFF0F5");
            colors.put("whitesmoke","#F5F5F5");
            colors.put("oldlace","#FDF5E6");
            colors.put("cornsilk","#FFF8DC");
            colors.put("linen","#FAF0E6");
            colors.put("lightgoldenrodyellow","#FAFAD2");
            colors.put("lemonchiffon","#FFFACD");
            colors.put("beige","#F5F5DC");
            colors.put("lavender","#E6E6FA");
            colors.put("papayawhip","#FFEFD5");
            colors.put("mistyrose","#FFE4E1");
            colors.put("antiquewhite","#FAEBD7");
            colors.put("blanchedalmond","#FFEBCD");
            colors.put("bisque","#FFE4C4");
            colors.put("paleturquoise","#AFEEEE");
            colors.put("moccasin","#FFE4B5");
            colors.put("gainsboro","#DCDCDC");
            colors.put("peachpuff","#FFDAB9");
            colors.put("navajowhite","#FFDEAD");
            colors.put("palegoldenrod","#EEE8AA");
            colors.put("wheat","#F5DEB3");
            colors.put("powderblue","#B0E0E6");
            colors.put("aquamarine","#7FFFD4");
            colors.put("lightgrey","#D3D3D3");
            colors.put("pink","#FFC0CB");
            colors.put("lightblue","#ADD8E6");
            colors.put("thistle","#D8BFD8");
            colors.put("lightpink","#FFB6C1");
            colors.put("lightskyblue","#87CEFA");
            colors.put("palegreen","#98FB98");
            colors.put("lightsteelblue","#B0C4DE");
            colors.put("khaki","#F0D58C");
            colors.put("skyblue","#87CEEB");
            colors.put("aqua","#00FFFF");
            colors.put("cyan","#00FFFF");
            colors.put("silver","#C0C0C0");
            colors.put("plum","#DDA0DD");
            colors.put("gray","#BEBEBE");
            colors.put("lightgreen","#90EE90");
            colors.put("violet","#EE82EE");
            colors.put("yellow","#FFFF00");
            colors.put("turquoise","#40E0D0");
            colors.put("burlywood","#DEB887");
            colors.put("greenyellow","#ADFF2F");
            colors.put("tan","#D2B48C");
            colors.put("mediumturquoise","#48D1CC");
            colors.put("lightsalmon","#FFA07A");
            colors.put("mediumaquamarine","#66CDAA");
            colors.put("darkgray","#A9A9A9");
            colors.put("orchid","#DA70D6");
            colors.put("darkseagreen","#8FBC8F");
            colors.put("deepskyblue","#00BFFF");
            colors.put("sandybrown","#F4A460");
            colors.put("gold","#FFD700");
            colors.put("mediumspringgreen","#00FA9A");
            colors.put("darkkhaki","#BDB76B");
            colors.put("cornflowerblue","#6495ED");
            colors.put("hotpink","#FF69B4");
            colors.put("darksalmon","#E9967A");
            colors.put("darkturquoise","#00CED1");
            colors.put("springgreen","#00FF7F");
            colors.put("lightcoral","#F08080");
            colors.put("rosybrown","#BC8F8F");
            colors.put("salmon","#FA8072");
            colors.put("chartreuse","#7FFF00");
            colors.put("mediumpurple","#9370DB");
            colors.put("lawngreen","#7CFC00");
            colors.put("dodgerblue","#1E90FF");
            colors.put("yellowgreen","#9ACD32");
            colors.put("palevioletred","#DB7093");
            colors.put("mediumslateblue","#7B68EE");
            colors.put("mediumorchid","#BA55D3");
            colors.put("coral","#FF7F50");
            colors.put("cadetblue","#5F9EA0");
            colors.put("lightseagreen","#20B2AA");
            colors.put("goldenrod","#DAA520");
            colors.put("orange","#FFA500");
            colors.put("lightslategray","#778899");
            colors.put("fuchsia","#FF00FF");
            colors.put("magenta","#FF00FF");
            colors.put("mediumseagreen","#3CB371");
            colors.put("peru","#CD853F");
            colors.put("steelblue","#4682B4");
            colors.put("royalblue","#4169E1");
            colors.put("slategray","#708090");
            colors.put("tomato","#FF6347");
            colors.put("darkorange","#FF8C00");
            colors.put("slateblue","#6A5ACD");
            colors.put("limegreen","#32CD32");
            colors.put("lime","#00FF00");
            colors.put("indianred","#CD5C5C");
            colors.put("darkorchid","#9932CC");
            colors.put("blueviolet","#8A2BE2");
            colors.put("deeppink","#FF1493");
            colors.put("darkgoldenrod","#B8860B");
            colors.put("chocolate","#D2691E");
            colors.put("darkcyan","#008B8B");
            colors.put("dimgray","#696969");
            colors.put("olivedrab","#6B8E23");
            colors.put("seagreen","#2E8B57");
            colors.put("teal","#008080");
            colors.put("darkviolet","#9400D3");
            colors.put("mediumvioletred","#C71585");
            colors.put("orangered","#FF4500");
            colors.put("olive","#808000");
            colors.put("sienna","#A0522D");
            colors.put("darkslateblue","#483D8B");
            colors.put("darkolivegreen","#556B2F");
            colors.put("forestgreen","#228B22");
            colors.put("crimson","#DC143C");
            colors.put("blue","#0000FF");
            colors.put("darkmagenta","#8B008B");
            colors.put("darkslategray","#2F4F4F");
            colors.put("saddlebrown","#8B4513");
            colors.put("brown","#A52A2A");
            colors.put("firebrick","#B22222");
            colors.put("purple","#800080");
            colors.put("green","#008000");
            colors.put("red","#FF0000");
            colors.put("mediumblue","#0000CD");
            colors.put("indigo","#4B0082");
            colors.put("midnightblue","#191970");
            colors.put("darkgreen","#006400");
            colors.put("darkblue","#00008B");
            colors.put("navy","#000080");
            colors.put("darkred","#8B0000");
            colors.put("maroon","#800000");
            colors.put("black","#000000");
           
            
            // translation from HTML tag to Android XML tag
            SymbolTable x = new SymbolTable();
            
            SymbolTable.XML_node ScrollView = x.new XML_node(); 
            ScrollView.setXML_node("ScrollView","011000000000101", XML_CODE.XML_NONVOID );
            html_xml.put("html",ScrollView);
            
            SymbolTable.XML_node LinearLayout = x.new XML_node(); 
            LinearLayout.setXML_node("LinearLayout","111000000000110", XML_CODE.XML_NONVOID);
            html_xml.put("body", LinearLayout);
            html_xml.put("form", LinearLayout);
            html_xml.put("div", LinearLayout);
            
            SymbolTable.XML_node RadioGroup = x.new XML_node(); 
            RadioGroup.setXML_node("RadioGroup","011000000000000", XML_CODE.XML_NONVOID);
            html_xml.put("RadioButtonGroup", RadioGroup);
            
            SymbolTable.XML_node RadioButton = x.new XML_node(); 
            RadioButton.setXML_node("RadioButton","111000000000000", XML_CODE.XML_VOID);
            html_xml.put("radiobutton", RadioButton);
            
            SymbolTable.XML_node CheckBox = x.new XML_node(); 
            CheckBox.setXML_node("CheckBox","111000000000000", XML_CODE.XML_VOID);
            html_xml.put("checkbox", CheckBox);
            
            SymbolTable.XML_node Button = x.new XML_node(); 
            Button.setXML_node("Button","111000000000000", XML_CODE.XML_VOID);
            html_xml.put("button", Button);
            
            
            SymbolTable.XML_node EditText = x.new XML_node();  
            EditText.setXML_node("EditText", "111000001000000", XML_CODE.XML_VOID);
            html_xml.put("textbox", EditText);
            
            SymbolTable.XML_node TextView = x.new XML_node();  
            TextView.setXML_node("TextView","111010000000000", XML_CODE.XML_VOID);
            html_xml.put("label", TextView);
            html_xml.put("p", TextView);
            html_xml.put("h1", TextView);
            html_xml.put("h2", TextView);
            html_xml.put("h3", TextView);
            html_xml.put("String_element", TextView);
            
            SymbolTable.XML_node Spinner = x.new XML_node(); 
            Spinner.setXML_node("Spinner","111000000000000", XML_CODE.XML_VOID);
            html_xml.put("select", Spinner);
                        
            html_xml_attr.put("text", "text");
            html_xml_attr.put("placeholder", "hint");
            html_xml_attr.put("password", "textPassword");
            html_xml_attr.put("email", "textEmailAddress");
            html_xml_attr.put("type", "inputType");
            html_xml_attr.put("background-color", "background"); 
            html_xml_attr.put("color", "textColor"); 
                       
   }
     XML_node getXMLNode(String key){         
         return html_xml.get(key);
     }
    public static Map<String, String> get_xml_properties(){        
        return xml_properties;
    }       
    public String getXML_Attr(String key){         
         return html_xml_attr.get(key);
     }
    
    public String getColor(String key){
        String color="";
        if(colors.get(key)!=null) 
         color = colors.get(key);
        else
         color = key;
        
        return color;
    }
    public String getColorName(String key){
        
        String color="";
        for (Map.Entry e : colors.entrySet()) {                      
             if( key.equals(e.getValue().toString()))
                color =   e.getKey().toString();
        }
           
        if(color.equals("")) 
            color=key;
        
        return color;
    } 
    
     HTML_CODE SearchKeyword(String key){ 
                   
          if(Keyword.containsKey(key.toLowerCase())) 
            return  Keyword.get(key.toLowerCase());                
          else
            return HTML_CODE.NOT_FOUND;      
     }
  }
     
   
 