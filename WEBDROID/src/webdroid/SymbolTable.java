package webdroid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import webdroid.KeywordList.HTML_CODE;
//Symbol Table is the collection of element, its properties and how they are constructed as trees
// UDP stands for User Defined Properties


public class SymbolTable { 
   
     public static class ElementNode{
            private int ID;
            private String Tag;
            private HTML_CODE Element_type;
            private HashMap<String, String>  UDP;
            private int Parent_ID;
            private List<ElementNode> Children_element;

                        
            public void ElementNodeProperties(int id, String tag, HTML_CODE element_type, HashMap<String, String> udp, int parent_id  ){    
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

    public static List<ElementNode>  entry = new ArrayList<>();
    public static ElementNode root;
    public static HashMap<String, ArrayList<String> > ArrayString;
    
    public void AddToTable(ElementNode e){
        entry.add(e);
    }
    public void setRoot(ElementNode e){

    }
    
    
}
