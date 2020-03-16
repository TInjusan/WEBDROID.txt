package webdroid; 
import java.util.List;
import java.util.Map;
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
            for (int i = 0; i < level; i++)   tab+="\t";
		    
             
                 KeywordList kw = new KeywordList();
                 SymbolTable x = new SymbolTable();
                 SymbolTable.XML_node xml_node = x.new XML_node();
                 
                 if(kw.getXMLNode(element.getTag())!=null){
                        xml_node = kw.getXMLNode(element.getTag());
                        
                     
                        
                        System.out.println(tab+xml_node.getXMLTag());
                        
                       for (Map.Entry e : xml_node.getXMLUDP().entrySet()) { 
                          System.out.println(tab+"\t"+e.getKey()+" = "+e.getValue());
                        }
                      
                       for (Map.Entry e : element.getUDP().entrySet()) { 
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
        }

}
