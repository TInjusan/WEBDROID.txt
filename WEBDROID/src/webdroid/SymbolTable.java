package webdroid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List; 
import java.util.Map;
import webdroid.KeywordList.HTML_CODE;

//Symbol Table is the collection of element, its properties and how they are constructed as trees
// UDP stands for User Defined Properties


public class SymbolTable { 
   
     public  class ElementNode{
            private int ID;
            private String Tag;
            private HTML_CODE Element_type;
            private HashMap<String, String>  UDP;
            private int Parent_ID;
            private List<ElementNode> Children_element;

                        
            ElementNode(int id, String tag, HTML_CODE element_type, HashMap<String, String> udp, int parent_id  ){    
                            try{   //initialization
                                    this.ID = id;
                                    this.Tag = tag;
                                    this.Element_type = element_type;
                                    this.UDP = udp;
                                    this.Parent_ID = parent_id;
                            }
                            catch (Exception e){
                                             System.err.println("Exception creating element:" + e);
                            }
                }

             // Setters and Getters for each element properties
            //---------------------------------------------------------------------------//
            void    setID(int id)    {  this.ID = id;  }               
            int  getID()             {  return ID;     }
            //--------------------------------------------------------------------------//

            HashMap<String, String> getUDP()         { return UDP;   }    
            void setUDP(HashMap<String, String> udp)   { this.UDP = udp; }
            void addUDP(String property, String value)   { this.UDP.put(property, value); }
            //--------------------------------------------------------------------------//
            void    setTag(String tag){ this.Tag = tag; }
            String  getTag()          { return Tag;     }

            //-------------------------------------------------------------------------//
            void    setElement_type(HTML_CODE element_type){ this.Element_type = element_type; }
            HTML_CODE  getElement_type()          { return Element_type;     }
             //-------------------------------------------------------------------------//

            void   setParent_ID(int Parent_ID){ this.Parent_ID = Parent_ID; }
            int getParent_ID() {        return Parent_ID;     }
           //-------------------------------------------------------------------------// 
            void setChildrenElement (List<ElementNode>  c){        this.Children_element = c;    }
            List<ElementNode>  getChildrenElement(){        return Children_element;    }
    }

     public class XML_node{
         private int xml_id;
         private String xml_tag;
         private Map<String, String>  xml_udp = new LinkedHashMap<>();
         private Map<String, String> xml_properties = new LinkedHashMap<>();
         private int Parent_ID;
         private List<ElementNode> Children_element;
            
         XML_node(){
            this.xml_id=-1;
            xml_properties.put("android:id","");
            xml_properties.put("android:layout_width","\"wrap_content\"");
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
         }
         
         public void setXML_node(String XML_tag, String assigned_properties){
             int i=0;
             this.xml_tag=XML_tag;
                
                for (Map.Entry e : xml_properties.entrySet()) { 
                  if(assigned_properties.charAt(i)=='1'){
                       this.xml_udp.put((String)e.getKey(), (String)e.getValue());
                       }
                  //  System.out.println("\t"+(String)e.getKey()+" = "+(String)e.getValue()); 
                  i++;
                }     
              
         }
         
         
          // Setters and Getters for each element properties
         void    setID(int id)    {  this.xml_id = id;  }               
         int  getID()             {  return xml_id;     }
                    
         void setXMLUDP(HashMap<String, String> udp)   { this.xml_udp = udp; }
         void addXMLUDP(String property, String value)   { this.xml_udp.put(property, value); }
         Map<String, String> getXMLUDP()         { return xml_udp;   }  
         
         void    setXMLTag(String tag){ this.xml_tag = tag; }
         String  getXMLTag()          { return xml_tag;     }
         
          void   setParent_ID(int Parent_ID){ this.Parent_ID = Parent_ID; }
            int getParent_ID() {        return Parent_ID;     }
           //-------------------------------------------------------------------------// 
            void setChildrenElement (List<ElementNode>  c){        this.Children_element = c;    }
            List<ElementNode>  getChildrenElement(){        return Children_element;    }
         //----------------------------------------------                                                    
                  
     }
          
     
    public static List<ElementNode> html_entry = new ArrayList<>();
    public static ElementNode root;
    
    public static HashMap<String, ArrayList<String> > ArrayString = new HashMap<>();
    public static HashMap<String, String> string_literals = new HashMap<>();
    
    public static List<XML_node> xml_entry = new ArrayList<>();
    public static XML_node xml_root;
    
    public void AddToTable(ElementNode e){
        html_entry.add(e);
    }
    public void setRoot(ElementNode e){

    }
    
    
}
