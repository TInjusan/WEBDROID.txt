package webdroid; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static webdroid.SymbolTable.ArrayString;
import static webdroid.SymbolTable.string_literals;
import webdroid.SymbolTable.ElementNode;

public class CodeGenerator {
         static Map<Integer, SymbolTable> html_element;
         static SymbolTable root;
         static String string_xml;
         static String layout_xml;
         static String color_xml;
         static String current_select="";
         static List<ElementNode> children;
         static int radiobuttoncount = 0;
         static int radiobuttonparent = -1;
         static String rb = "";
         static String App_name="";
          
         static KeywordList k = new KeywordList();
         public void set_html_element(Map<Integer, SymbolTable> h,SymbolTable r){
            html_element = h;
            root = r;
         }
         
         CodeGenerator(){
             string_xml="";
             layout_xml="";
             color_xml="";
         }
    	 
      
        private void GENERATE_XML(ElementNode element, int level){
            String tab =  "\t";
            String id;
            for (int i = 0; i < level; i++)   tab+="\t";
		    
             
                 KeywordList kw = new KeywordList();
                 SymbolTable x = new SymbolTable();
                 SymbolTable.XML_node xml_node = x.new XML_node();
                 
                 if(kw.getXMLNode(element.getTag())!=null){
                        xml_node = kw.getXMLNode(element.getTag());
                                                
                        System.out.println(tab+xml_node.getXMLTag());
                        
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
                     
                       
                       for (Map.Entry e : element.getUDP().entrySet()) { 
                          System.out.println(tab+"\t"+e.getKey()+" = "+e.getValue());
                        }
                       
                       for (Map.Entry e : xml_node.getXMLUDP().entrySet()) { 
                          System.out.println(tab+"\t"+e.getKey()+" = "+e.getValue());
                        }
                      
                       
                       
                        if(xml_node.getXMLTag().equals("ScrollView")){
                              xml_node.setParent_ID(-1);
                              xml_node.setID(element.getID());
                              SymbolTable.xml_entry.add(xml_node);
                        }
                     
                 }
                 else
                     System.out.println(tab+"No equivalent Android XML: "+element.getTag());
             
              
             children = element.getChildrenElement();
	     System.out.print(" ");
             for (ElementNode child_node : children) {
			 GENERATE_XML(child_node, level + 1);
		 }   
        }
        
        public void directTranslation(){
            GENERATE_XML(SymbolTable.root,0); 
            printString();
        }
        
        private void printString(){
            
            System.out.println("<resources>");
            for (Map.Entry e :   string_literals.entrySet())  
               System.out.println("\t<string name=\""+e.getKey()+"\">"+e.getValue()+"</string>");
            
//            for (Map.Entry e : ArrayString.entrySet()) { 
//                       
//                       System.out.println(e.getValue());
                       
//                         for (String item : e.getValue(). ) { 		      
//                            System.out.println(item); 		
//                       }


                for (Entry<String, ArrayList<String>> entry : ArrayString.entrySet()) {
                      System.out.println("<string-array name=\""+(String)entry.getKey()+"\">");
                      for(String item : entry.getValue()){
                          System.out.println(item); 
                      }
        
                }       
            
           System.out.println("</resources>");           
        }

}
