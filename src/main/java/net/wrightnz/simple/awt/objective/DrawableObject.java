package net.wrightnz.simple.awt.objective;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard Wright
 */
public class DrawableObject {

  private static final float LINE_WIDTH = 2.0F;
  private static final float LINE_WIDTH_WIDE = 4.0F;
  private static final int RADIUS = 20;
  private static RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

  protected static BasicStroke stoke = new BasicStroke(LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
  protected static BasicStroke stokeWide = new BasicStroke(LINE_WIDTH_WIDE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

  public static final int DEFAULT_WIDTH = 64;
  public static final int DEFAULT_HEIGHT = 4;

  private Rectangle bounds = new Rectangle(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

  private boolean selected = false;

  private Color backgoundColour = Color.WHITE;
  private Color foregroundColour = Color.BLACK;
  private String description;

  private Rectangle2D paddingRect;


  public DrawableObject() {
    qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
  }

  public void paint(Graphics2D g) {
    Graphics2D graphics = (Graphics2D) g;
    setSizeFromDescription(graphics);
    setupGraphics(graphics);
    paintBoarder(graphics);
    paintBackground(graphics);
    paintDescription(graphics);
  }

  protected void setupGraphics(Graphics2D graphics) {
    graphics.setRenderingHints(qualityHints);
  }

  protected void setSizeFromDescription(Graphics graphics) {
    Font font = graphics.getFont();
    FontMetrics fm = graphics.getFontMetrics(font);
    int lineNumber = 0;
    List<String> lines = formatDescription();
    paddingRect = fm.getStringBounds("M", graphics);

    int maxWidth = DEFAULT_WIDTH;
    for (String line : lines) {
      Rectangle2D rect = fm.getStringBounds(line, graphics);
      int textWidth = (int) (rect.getWidth());
      if (textWidth > DEFAULT_WIDTH) {
        if (textWidth > maxWidth) {
          maxWidth = textWidth + ((int) paddingRect.getWidth() / 2);
          this.setWidth(maxWidth);
        }
      }
      int textHeight = (int) (rect.getHeight());
      int totalTextHeight = textHeight * lines.size();
      if (totalTextHeight > DEFAULT_HEIGHT) {
        this.setHeight(totalTextHeight + ((int) paddingRect.getHeight() / 3));
      }
      lineNumber++;
    }
  }

  protected void paintDescription(Graphics2D graphics) {
    graphics.setStroke(stoke);
    Font font = graphics.getFont();
    FontMetrics fm = graphics.getFontMetrics(font);
    graphics.setColor(getForegroundColour());
    int lineNumber = 0;
    List<String> lines = formatDescription();
    for (String line : lines) {
      Rectangle2D rect = fm.getStringBounds(line, graphics);
      int textHeight = (int) (rect.getHeight());
      int textWidth = (int) (rect.getWidth());
      // Center text horizontally
      int x = getX() + ((getWidth() - textWidth) / 2);

      int y = getY() + textHeight + (textHeight * lineNumber);
      graphics.drawString(line, x, y);
      lineNumber++;
    }
  }

  protected void paintBackground(Graphics2D graphics) {
    graphics.setStroke(stoke);
    graphics.setColor(getBackgoundColour());
    graphics.fillRoundRect(getX(), getY(), getWidth(), getHeight(), RADIUS, RADIUS);
  }


  public Color getBackgoundColour() {
    if (this.isSelected()) {
      return Color.ORANGE;
    }
    return backgoundColour;
  }


  protected void paintBoarder(Graphics2D graphics) {
    graphics.setStroke(stokeWide);
    graphics.setColor(getForegroundColour());
    graphics.drawRoundRect(getX(), getY(), getWidth(), getHeight(), RADIUS, RADIUS);
  }

  public Dimension getMinimumSize() {
    return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  /**
   * @return the bounds
   */
  public Rectangle getBounds() {
    return bounds;
  }

  public int getX() {
    return (int) bounds.getX();
  }

  public int getY() {
    return (int) bounds.getY();
  }

  public int getWidth() {
    return (int) bounds.getWidth();
  }

  public int getHeight() {
    return (int) bounds.getHeight();
  }

  public void setLocation(Point location) {
    bounds.setLocation(location);
  }

  public void setWidth(int width) {
    bounds.setSize(width, getHeight());
  }

  public void setHeight(int height) {
    bounds.setSize(getWidth(), height);
  }

  /**
   * @param bounds the bounds to set
   */
  public void setBounds(Rectangle bounds) {
    this.bounds = bounds;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    if (description == null) {
      return this.getClass().getSimpleName();
    }
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  public void notify(MouseEvent e) {
    if (getBounds().contains(e.getPoint())) {
      int type = e.getID();
      if (type == MouseEvent.MOUSE_CLICKED) {
        handleMouseClicked(e);
      } else if (type == MouseEvent.MOUSE_DRAGGED && isSelected()) {
        Point pointerLocation = e.getPoint();
        int x = (int) pointerLocation.getX() - (getWidth() / 2);
        int y = (int) pointerLocation.getY() - (getHeight() / 2);
        Point location = new Point(x, y);
        bounds.setLocation(location);
      }
    }
  }

  public void communicate(List<DrawableObject> objects) {
    for (DrawableObject obj : objects) {
      if (obj != this) {
        System.out.println("Hello: " + obj.getDescription() + " from " + this.getDescription());
      }
    }
  }

  protected void handleMouseClicked(MouseEvent e) {
    setSelected(!isSelected());
  }

  public static void main(String[] args) {
    DrawableObject obj = new DrawableObject();
    System.out.println(obj.getDescription());
    List<String> formatedDescription = obj.formatDescription();
    // net.wrightnz.util.Util.printList(formatedDescription);
  }

  /**
   * @return the selected
   */
  public boolean isSelected() {
    return selected;
  }

  /**
   * @param selected the selected to set
   */
  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public int getCenterX() {
    return this.getX() + (this.getWidth() / 2);
  }

  public int getCenterY() {
    return this.getY() + (this.getHeight() / 2);
  }

  public int getBottonY() {
    return this.getY() + this.getHeight();
  }

  public int getRightX() {
    return this.getX() + this.getWidth();
  }


  /**
   * @param backgoundColour the backgoundColour to set
   */
  public void setBackgoundColour(Color backgoundColour) {
    this.backgoundColour = backgoundColour;
  }

  /**
   * @return the foregroundColour
   */
  public Color getForegroundColour() {
    return foregroundColour;
  }

  /**
   * @param foregroundColour the foregroundColour to set
   */
  public void setForegroundColour(Color foregroundColour) {
    this.foregroundColour = foregroundColour;
  }

  protected List<String> formatDescription() {
    List<String> lines = new ArrayList<String>();
    StringBuilder sb = new StringBuilder();
    char[] chars = getDescription().toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '\n') {
        lines.add(sb.toString());
        sb = new StringBuilder();
      } else {
        sb.append(chars[i]);
      }
    }
    lines.add(sb.toString());
    return lines;
  }

}
