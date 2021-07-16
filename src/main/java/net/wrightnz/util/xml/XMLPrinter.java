/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.xml;

import org.jdom.Attribute;
import org.jdom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Richard Wright
 */
public class XMLPrinter {


  private static Map elementNames = new HashMap();

  /**
   * Recurse through the element of an XML file and print out a nice ascii tree
   * representation on the DOM tree. Handy for debugging etc.
   *
   * @param element the element to start recursing from. Typically the root
   *                element on first call.
   * @param depth   the depth into the XML DOM tree Typically this will be set
   *                to 0 on first call.
   */
  public void expandElement(Element element, int depth) {
    List<Element> elements = element.getChildren();
    for (Element e : elements) {
      printIndent(depth);
      System.out.print("Element: " + e.getName());
      elementNames.put(e.getName(), e.getName());
      String text = e.getText();
      if (text != null && !(text.trim()).equals("")) {
        System.out.println(" = " + text);
      } else {
        System.out.println();
      }
      List<Attribute> attributes = e.getAttributes();
      if (attributes != null && attributes.size() > 0) {
        for (Attribute attribute : attributes) {
          printIndent(depth);
          System.out.println("  |- Attribute: " + attribute.getName() + " = " + attribute.getValue());
        }
      }
      if (e != null) {
        expandElement(e, depth + 1);
      }
    }
  }

  private void printIndent(int depth) {
    for (int i = 0; i < depth; i++) {
      System.out.print("   ");
    }
  }

}
