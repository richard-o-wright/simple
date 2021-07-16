
package net.wrightnz.util.web;

/**
 * Class to display text in an HTML (or XML document). It doesn't really
 * do much except prevent "null" from being displayed in the docment.
 * 
 * @author t466225
 */
public class Text implements java.io.Serializable{
    
    private String text;
        
    public Text(String text){
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String toString(){
        if(text != null && !"null".equalsIgnoreCase(text)){
            return WebUtil.htmlEscape(text);
        }
        else{
            return "";
        }
    }
    
    public static void main(String[] args){
        System.out.println(new Text("name"));
        System.out.println(new Text("null"));
        System.out.println(new Text("Null"));
        System.out.println(new Text(null));
        System.out.println(new Text("Some more text & some more"));       
    }

}
