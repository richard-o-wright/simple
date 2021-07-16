package net.wrightnz.awt;

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Simple component which displays an image "sample" with an action listener
 * so the image can be clicked on to trigger some action.
 *
 * @author Richard Wright
 */

public class Sample extends Component {

    private BufferedImage img;
    private ActionListener actionListener;
    private String label;

    public Sample(String label) {
        this.setFont(new Font("Helvetica", 0, 10));
        this.label = label;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click();
            }
        });
    }

    public void setImage(BufferedImage image) {
        this.img = image;
        this.setSize(img.getWidth(), img.getHeight());
        repaint();
    }

    public BufferedImage getImage(){
        return this.img;
    }
    
    @Override
    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
        g.setColor(Color.black);
        g.drawString(label, 4, getHeight() + 12);
    }

    public void addActionListener(ActionListener al) {
        actionListener = AWTEventMulticaster.add(actionListener, al);
    }

    public void removeActionListener(ActionListener al) {
        actionListener = AWTEventMulticaster.remove(actionListener, al);
    }

    protected void click() {
        if (actionListener != null) {
            actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Clicked"));
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(32, 32);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight() + 15);
    }
    
}

