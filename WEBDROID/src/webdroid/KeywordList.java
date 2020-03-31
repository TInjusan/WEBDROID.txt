package webdroid;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import webdroid.SymbolTable.XML_node;

/**
 * This class is the collection of supported HTML keywords.
 * It will return a value from a given string that define its HTML_CODE.
 * This also contain the XML keywords corresponding to HTML codes implemented
 * in a HashMap collection of java.
 * The raw keyword data are stored in separated CSV file to shorten the code.
 */
public class KeywordList {

    public enum HTML_CODE {
        NOT_FOUND, HTML_NONVOID_TAG, HTML_SPECIAL_CHARACTER, 
        HTML_VOID_TAG, HTML_PROPERTY_NAME, HTML_STRING_ELEMENT,
        CSS_PROPERTY
    };
    public enum XML_CODE {
        XML_NONVOID, XML_VOID
    };

    //initializing the collection of keywords in key value pair of hashmap
    private HashMap<String, String> Keyword = new HashMap<>();
    private HashMap<String, XML_node> html_xml = new HashMap<>();
    private HashMap<String, String> html_xml_attr = new HashMap<>();
    private HashMap<String, String> colors = new HashMap<>();
    private static Map<String, String> xml_properties = new LinkedHashMap<>();
    final String dir = System.getProperty("user.dir");
    String path = dir.replace("/", "\\\\") + "\\others\\";

    //constructor initializing the keywords
    KeywordList() {
   
        Keyword.putAll(getCSVkeyword(path + "Keyword.csv"));
        colors.putAll(getCSVkeyword(path + "Colors.csv"));
        xml_properties.putAll(getCSVkeyword(path + "xml_properties.csv"));
        html_xml_attr.putAll(getCSVkeyword(path + "html_xml_attr.csv"));

        /**
         * Matching of HTML tag to Android XML tag
         * The second parameter binary string represents the default and required
         * XML layout attribute of an XML tag. One is required zero is optional.
         * It is based on the xml_properties hashmap.
         */
        SymbolTable x = new SymbolTable();

        //Instantiation of object from SymbolTable sub class.
        SymbolTable.XML_node ScrollView = x.new XML_node();
        ScrollView.setXML_node("ScrollView", "011000000000101", XML_CODE.XML_NONVOID);
        html_xml.put("html", ScrollView);

        SymbolTable.XML_node LinearLayout = x.new XML_node();
        LinearLayout.setXML_node("LinearLayout", "111000000000110", XML_CODE.XML_NONVOID);
        html_xml.put("body", LinearLayout);
        html_xml.put("form", LinearLayout);
        html_xml.put("div", LinearLayout);

        SymbolTable.XML_node ImageView = x.new XML_node();
        ImageView.setXML_node("ImageView", "111000000000000", XML_CODE.XML_VOID);
        html_xml.put("img", ImageView);

        SymbolTable.XML_node RadioGroup = x.new XML_node();
        RadioGroup.setXML_node("RadioGroup", "011000000000000", XML_CODE.XML_NONVOID);
        html_xml.put("RadioButtonGroup", RadioGroup);

        SymbolTable.XML_node RadioButton = x.new XML_node();
        RadioButton.setXML_node("RadioButton", "111000000000000", XML_CODE.XML_VOID);
        html_xml.put("radiobutton", RadioButton);

        SymbolTable.XML_node CheckBox = x.new XML_node();
        CheckBox.setXML_node("CheckBox", "111000000000000", XML_CODE.XML_VOID);
        html_xml.put("checkbox", CheckBox);

        SymbolTable.XML_node Button = x.new XML_node();
        Button.setXML_node("Button", "111000000000000", XML_CODE.XML_VOID);
        html_xml.put("button", Button);

        SymbolTable.XML_node EditText = x.new XML_node();
        EditText.setXML_node("EditText", "111000001000000", XML_CODE.XML_VOID);
        html_xml.put("textbox", EditText);

        SymbolTable.XML_node TextView = x.new XML_node();
        TextView.setXML_node("TextView", "111010000000000", XML_CODE.XML_VOID);
        html_xml.put("label", TextView);
        html_xml.put("p", TextView);
        html_xml.put("h1", TextView);
        html_xml.put("h2", TextView);
        html_xml.put("h3", TextView);
        html_xml.put("String_element", TextView);

        SymbolTable.XML_node Spinner = x.new XML_node();
        Spinner.setXML_node("Spinner", "111000000000000", XML_CODE.XML_VOID);
        html_xml.put("select", Spinner);

    }

    XML_node getXMLNode(String key) {
        return html_xml.get(key);
    }

    public static Map<String, String> get_xml_properties() {
        return xml_properties;
    }

    public String getXML_Attr(String key) {
        return html_xml_attr.get(key);
    }

    public String getColor(String key) {
           return colors.get(key) != null? 
                  colors.get(key): key;
    }

    public String getColorName(String key) {
        String color = "";
        for (Map.Entry e : colors.entrySet()) {
            if (key.equals(e.getValue().toString()))  
                color = e.getKey().toString();            
        }

        if (color.equals("")) 
            color = key;        
        return color;
    }

    HTML_CODE SearchKeyword(String key) {
        return Keyword.containsKey(key.toLowerCase())?
               HTML_CODE.valueOf(Keyword.get(key.toLowerCase())):
               HTML_CODE.NOT_FOUND;                
    }

    private HashMap<String, String> getCSVkeyword(String file) {
        HashMap<String, String> s = new HashMap<>();
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                s.put(value[0], value[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
