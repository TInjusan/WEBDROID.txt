package webdroid;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import webdroid.KeywordList.XML_CODE;
import webdroid.SymbolTable.ElementNode;
import webdroid.SymbolTable.XML_node;
import static webdroid.WEBDROID.android_location;
import static webdroid.SymbolTable.string_xml;
import static webdroid.SymbolTable.color_xml;
import static webdroid.SymbolTable.ArrayString;
/**
 * This class is where the translation takes place to printing of the translated output.
 * The CodeGenerator will first get the modified abstract syntax tree from 
 * SymbolTable after Semantic Analysis.
 * The HTML abstract syntax tree will then be converted to 
 * Intermediate Representation Tree for XML using XMLNode class from Symbol Table.
 * Once the IRT XML is properly generated it will then be used to
 * print the XML Layout, XML String and XML colors.
 * 
 */
public class CodeGenerator {

    static Map<Integer, SymbolTable> html_element;
    static SymbolTable root;
    static List<ElementNode> children;
    static List<XML_node> xmlchildren;
    static int rbcount;
    static int rbparent;
    static int current_rb;
    static int xml_id;
    static KeywordList k = new KeywordList();
    
    CodeGenerator(){
            rbcount = 0;
            rbparent = -1;
            current_rb = -1;
            xml_id = SymbolTable.get_html_table().size();        
    }
    private void GENERATE_XML(ElementNode element) {

        String id;
        KeywordList kw = new KeywordList();
        SymbolTable x = new SymbolTable();
        SymbolTable.XML_node xml_node = x.new XML_node();
        SymbolTable.XML_node xml_node_group = x.new XML_node();

        if (kw.getXMLNode(element.getTag()) != null) {
            xml_node = kw.getXMLNode(element.getTag());
            xml_node.setID(xml_id);

            
            //lambda expression setting up UDP id
            //this is visible android XML layout
            id = element.getUDP().get("id")== null
                    ? xml_node.getXMLTag() + element.getID()
                    : element.getUDP().get("id");

            xml_node.addXMLUDP("android:id", "\"@+id/" + id + "\"");

            for (Map.Entry e : element.getUDP().entrySet()) {
                switch (e.getKey().toString()) {
                    case "text":
                    case "placeholder":
                        string_xml.put(id, e.getValue().toString().trim());
                        xml_node.addXMLUDP("android:" + kw.getXML_Attr(e.getKey().toString()), "\"@string/" + id + "\"");
                        break;
                    case "type":
                        xml_node.addXMLUDP("android:" + kw.getXML_Attr(e.getKey().toString()), "\"" + kw.getXML_Attr(e.getValue().toString()) + "\"");
                        break;
                    case "font-size":
                    case "font-weight":
                        xml_node.addXMLUDP("android:" + kw.getXML_Attr(e.getKey().toString()), "\"" + e.getValue().toString() + "\"");
                        break;
                    case "background-color":
                    case "color":
                        String colorname = kw.getColorName(e.getValue().toString());
                        String colorvalue = kw.getColor(e.getValue().toString());
                        colorname = colorname.equals(colorvalue) ? id : colorname;
                        color_xml.put(colorname, colorvalue);
                        xml_node.addXMLUDP("android:" + kw.getXML_Attr(e.getKey().toString()), "\"@color/" + colorname + "\"");
                        break;
                    case "src":
                        String xml_filename = copyimage(e.getValue().toString());
                        xml_node.addXMLUDP("android:" + kw.getXML_Attr(e.getKey().toString()), "\"@drawable/" + xml_filename.substring(0, xml_filename.indexOf('.')) + "\"");
                        break;
                    default:
                        break;
                }
            }

            switch(xml_node.getXMLTag()){
                case "RadioButton":
                    if (rbparent != element.getParent_ID() && rbcount == 0) {
                        rbparent = element.getParent_ID();
                        xml_node_group = kw.getXMLNode("RadioButtonGroup");
                        xml_node_group.setParent_ID(rbparent);
                        xml_node_group.setID(xml_id);
                        current_rb = xml_id;

                        SymbolTable.layout_xml.add(xml_node_group);

                        xml_id++;
                        xml_node.setID(xml_id);
                        xml_node.setParent_ID(current_rb);
                        rbcount++;
                    } else {
                        xml_node.setParent_ID(current_rb);
                    }

                    SymbolTable.layout_xml.add(xml_node);

                    break;
                case "Spinner":
                    xml_node.addXMLUDP("android:entries", "\"@array/" + element.getUDP().get("name") + "\"");
                    xml_node.setParent_ID(element.getParent_ID());
                    xml_node.setID(element.getID());
                    SymbolTable.layout_xml.add(xml_node);
                    break;
                case "ScrollView":
                    xml_node.setParent_ID(-1);
                    xml_node.setID(element.getID());
                    SymbolTable.layout_xml.add(xml_node); 
                    SymbolTable.layout_xml_root = xml_node;
                    break;
                default:
                    xml_node.setParent_ID(element.getParent_ID());
                    xml_node.setID(element.getID());
                    SymbolTable.layout_xml.add(xml_node);
                break;
            }
            xml_id++;
        
        } else if (element.getTag().equals("title")) {
            string_xml.put("app_name", element.getUDP().get("text"));
        }

        children = element.getChildrenElement();

        for (ElementNode child_node : children) {
            GENERATE_XML(child_node);
        }
    }
      
    public void directTranslation() {
        GENERATE_XML(SymbolTable.root);
        buildSymbolTree(SymbolTable.layout_xml_root);
        printLayoutXML(SymbolTable.layout_xml_root, "");
        printStringXML();
        printColorsXML();
    }

    private String copyimage(String source_file) {

        File source = new File(source_file);
        String file_name = source_file.substring(source_file.lastIndexOf('\\') + 1, source_file.length());
        file_name = file_name.toLowerCase();
        file_name = file_name.replaceAll(" ", "");
        File destination = new File(android_location.getText() + "\\app\\src\\main\\res\\drawable\\" + file_name);

        try {
            if (!android_location.getText().equals("")) {
                Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
            }
        } catch (FileAlreadyExistsException e) {
            //File already exists.
        } catch (IOException e) {
        }

        return file_name;
    }

    private void buildSymbolTree(XML_node xml_node) {
        xmlchildren = getChildrenById(xml_node.getID());
        xml_node.setChildrenElement(xmlchildren);
        if (xmlchildren.isEmpty()) {
            return;
        }

        for (XML_node child_node : xmlchildren) {
            buildSymbolTree(child_node);
        }

    }

    public List<XML_node> getChildrenById(int id) {
        List<XML_node> children_ = new ArrayList<>();
        for (XML_node e : SymbolTable.layout_xml) {
            if (e.getParent_ID() == id) {
                children_.add(e);
            }
        }
        return children_;
    }

    private void printLayoutXML(XML_node xml_node, String tab) {
        tab = tab + "\t";
        WEBDROID.Android_Layout_XML.appendText(tab + "<" + xml_node.getXMLTag().trim() + "\n");

        int i = 0;
        for (Map.Entry e : xml_node.getXMLUDP().entrySet()) {
            WEBDROID.Android_Layout_XML.appendText(tab + "\t" + e.getKey() + " = " + e.getValue());
            xml_node.getXMLUDP().size();

            WEBDROID.Android_Layout_XML.appendText(
                    i + 1 == xml_node.getXMLUDP().size()
                            ? xml_node.getXML_type() == XML_CODE.XML_VOID ? "/>\n" : ">\n"
                            : "\n"
            );
            i++;

        }

        xmlchildren = xml_node.getChildrenElement();
        for (XML_node child_node : xmlchildren) {
            printLayoutXML(child_node, tab);
        }

        //Closing tag for Non-void
        WEBDROID.Android_Layout_XML.appendText(
                xml_node.getXML_type() == XML_CODE.XML_NONVOID
                        ? tab + "</" + xml_node.getXMLTag() + ">\n" : ""
        );
    }

    private void printStringXML() {
        WEBDROID.Android_String_XML.appendText("<resources>\n");

        for (Map.Entry e : string_xml.entrySet()) {
            WEBDROID.Android_String_XML.appendText(" <string name=\"" + e.getKey() + "\">" + e.getValue() + "</string>\n");
        }

        for (Entry<String, ArrayList<String>> entry : ArrayString.entrySet()) {
            WEBDROID.Android_String_XML.appendText(" <string-array name=\"" + (String) entry.getKey() + "\">\n");
            for (String item : entry.getValue()) {
                WEBDROID.Android_String_XML.appendText("\t<item>" + item + "</item>\n");
            }
            WEBDROID.Android_String_XML.appendText(" </string-array>\n");
        }

        WEBDROID.Android_String_XML.appendText("</resources>\n");
    }

    private void printColorsXML() {
        WEBDROID.Android_Color_XML.appendText("<resources>\n");

        for (Map.Entry e : color_xml.entrySet()) {
            WEBDROID.Android_Color_XML.appendText(" <color name=\"" + e.getKey() + "\">" + e.getValue() + "</color>\n");
        }

        WEBDROID.Android_Color_XML.appendText("</resources>\n");
    }

}
