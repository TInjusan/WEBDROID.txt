package webdroid; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static webdroid.SymbolTable.ArrayString;
import static webdroid.SymbolTable.string_literals;
import webdroid.SymbolTable.ElementNode;
import webdroid.SymbolTable.XML_node;

public class CodeGenerator {
         static Map<Integer, SymbolTable> html_element;
         static SymbolTable root;
         static String string_xml;
         static String layout_xml;
         static String color_xml;
         static String current_select="";
         static List<ElementNode> children;
         static List<XML_node> xmlchildren;
         static int rbcount = 0;
         static int rbparent = -1;
         static int current_rb = -1;
         static int xml_id = SymbolTable.get_html_table().size();
         static KeywordList k = new KeywordList();
      
         
        private void GENERATE_XML(ElementNode element, int level){
                 
                 String id;
                 KeywordList kw = new KeywordList();
                 SymbolTable x = new SymbolTable();
                 SymbolTable.XML_node xml_node = x.new XML_node();
                 SymbolTable.XML_node xml_node_group = x.new XML_node();
                 if(kw.getXMLNode(element.getTag())!=null){
                        xml_node = kw.getXMLNode(element.getTag());
                        xml_node.setID(xml_id);
                      
                        
                        if(element.getTag().equals("radiobutton")){
                           
                            if(rbparent!=element.getParent_ID() && rbcount == 0){
                                rbparent = element.getParent_ID();
                                xml_node_group = kw.getXMLNode("RadioButtonGroup");
                                xml_node_group.setParent_ID(rbparent);
                                xml_node_group.setID(xml_id);
                                current_rb = xml_id;
                                
                                SymbolTable.xml_entry.add(xml_node_group);
                                
                                xml_id++;
                                xml_node.setID(xml_id);
                                xml_node.setParent_ID(current_rb);
                                rbcount++;
                            }
                            else{
                                xml_node.setParent_ID(current_rb);
                            }
                            
                                SymbolTable.xml_entry.add(xml_node);
                                
                        }
                          xml_id++;
                       
                        //lambda expression
                        id =  kw.getXMLNode(element.getTag())!=null? 
                              xml_node.getXMLTag()+element.getID(): 
                              element.getUDP().get("id");
                        
                       xml_node.addXMLUDP("android:id", "\"@id/"+id+"\"");
                       
                       for (Map.Entry e : element.getUDP().entrySet()) { 
                          switch(e.getKey().toString()){
                              case "text":
                                 
                                  string_literals.put(id, e.getValue().toString());
                                  xml_node.addXMLUDP("android:text", "\"@string/"+id+"\"");
                                  break;
                                  
                              case "placeholder":
                                  string_literals.put(id, e.getValue().toString());
                                  xml_node.addXMLUDP("android:hint", "\"@string/"+id+"\"");
                                  break;
                                  
                              default:
                                  
                                  break;
                          }
                        }
                       
                    
                       if(xml_node.getXMLTag().equals("Spinner")){
                            xml_node.addXMLUDP("android:entries", "\"@array/"+element.getUDP().get("name")+"\"");
                       }    
                     
                             
                        if(xml_node.getXMLTag().equals("ScrollView")){
                              xml_node.setParent_ID(-1);
                              xml_node.setID(element.getID());
                              SymbolTable.xml_entry.add(xml_node);
                        }else if(!xml_node.getXMLTag().equals("RadioButton")){
                              xml_node.setParent_ID(element.getParent_ID());
                              xml_node.setID(element.getID());
                              SymbolTable.xml_entry.add(xml_node); 
                        }
                     
                 }
                 else{
                         if(element.getTag().equals("title"))
                         string_literals.put("app_name", element.getUDP().get("text"));
                 }
                     
              if(xml_node.getID()==0)
                  SymbolTable.xml_root = xml_node;
              
             children = element.getChildrenElement();
	      
             for (ElementNode child_node : children) {
			 GENERATE_XML(child_node, level + 1);
		 }   
        }
        
        public void directTranslation(){
            GENERATE_XML(SymbolTable.root,0); 
            buildSymbolTree(SymbolTable.xml_root);
            printSymbolTableXML();
            printLayoutXML(SymbolTable.xml_root,""); 
            printString();
        }
        
         private static void printSymbolTableXML(){
            for (XML_node e : SymbolTable.xml_entry) {
                     String tablerow = "";
                     tablerow = tablerow +  e.getID() +"\t";
                     tablerow = tablerow + e.getXMLTag() + "\t";
                     tablerow = tablerow + e.getParent_ID() +"\t";
                     System.out.println(tablerow);
                     
                    }
        }
         
        private void buildSymbolTree(XML_node xml_node) {
		 xmlchildren = getChildrenById(xml_node.getID());             
                 xml_node.setChildrenElement(xmlchildren);
                    if (xmlchildren.isEmpty())  
                        return;                
                    
                    for (XML_node child_node : xmlchildren)  
                        buildSymbolTree(child_node);
                 
	 }
         
          public  List<XML_node> getChildrenById(int id) {
                List<XML_node> children_ = new ArrayList<>();
                for (XML_node e : SymbolTable.xml_entry) {
                      if (e.getParent_ID() == id) {
                       children_.add(e);

                    }
                }
            return children_;
	 }
        
        
         private void printLayoutXML(XML_node xml_node, String tab){
            tab = tab+"\t";
            
            System.out.println(tab+xml_node.getXMLTag());
            WEBDROID.Android_Layout_XML.appendText(tab+xml_node.getXMLTag().trim()+"\n");


            for (Map.Entry e : xml_node.getXMLUDP().entrySet()) { 
             WEBDROID.Android_Layout_XML.appendText(tab+"\t"+e.getKey()+" = "+e.getValue()+"\n");
            }

             xmlchildren = xml_node.getChildrenElement();
	     
             for (XML_node child_node : xmlchildren)  
                printLayoutXML(child_node, tab);
		             
             
         }
         
         
        private void printString(){
            WEBDROID.Android_String_XML.appendText("<resources>\n");
             
            for (Map.Entry e :   string_literals.entrySet())  
                 WEBDROID.Android_String_XML.appendText(" <string name=\""+e.getKey()+"\">"+e.getValue()+"</string>\n");
    
                for (Entry<String, ArrayList<String>> entry : ArrayString.entrySet()) {
                        WEBDROID.Android_String_XML.appendText(" <string-array name=\""+(String)entry.getKey()+"\">\n");
                      for(String item : entry.getValue()){
                            WEBDROID.Android_String_XML.appendText("\t<item>"+item+"<item>\n"); 
                      }
                        WEBDROID.Android_String_XML.appendText(" </string-array>\n");
                }       
            
             WEBDROID.Android_String_XML.appendText("</resources>\n");           
        }

}
