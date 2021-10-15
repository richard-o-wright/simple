package net.wrightnz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Richard Wright
 * @param <T>
 */
public class Substituter<T> {

  /**
   * Return a message. The message is obtained from a template string, and
   * values are interpolated into the message.
   *
   * @param template the template for the message;
   * @param tagValues A list of KeyValue objects, containing tags and their
   * corresponding values; all instances of the tag in the message will be
   * replaced by the value. The tags will be replaced in the list order -- so
   * earlier tags can contain later tags, and the replacement will work.
   *
   * @return the message as String.
   * @throws TagSubstituterException If a tag cannot be replaced.
   */
  public String substitute(String template, List<KeyValue<T>> tagValues) throws TagSubstituterException {
    String result = template;
    try {
      for (KeyValue<T> entry : tagValues) {
        String tag = entry.getTag();
        T value = entry.getValue();
        Pattern pattern = Pattern.compile(tag);
        Matcher matcher = pattern.matcher(result);
        result = matcher.replaceAll(value.toString());
      }
      return result;
    } catch (NullPointerException npe) {
      throw new TagSubstituterException("NullPointerException: It's likely the tagValues has nulls in it", npe);
    }
  }

  /**
   * Main method to unit test the class.
   *
   * @param arguments Not used
   * @throws net.wrightnz.util.TagSubstituterException
   */
  public static void main(String[] arguments) throws TagSubstituterException {
    List<KeyValue<String>> tandV = new ArrayList<>();
    tandV.add(new KeyValue("<insertgroup>", "#### <groupid> ####"));
    tandV.add(new KeyValue("<groupid>", "1"));
    String template = "<groupid> blah <insertgroup> blah blah <groupid> blah fish chips <groupid> blah <groupid>";
    Substituter<String> substituter = new Substituter<>();
    System.out.println(substituter.substitute(template, tandV));
  }

}
