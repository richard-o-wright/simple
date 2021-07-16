/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.awt;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author T466225
 */
class ComboKeyHandler extends KeyAdapter {

    private final JComboBox comboBox;
    private boolean shouldHide = false;
    private final List<String> list = new ArrayList();

    public ComboKeyHandler(JComboBox combo) {
        this.comboBox = combo;
        for (int i = 0; i < comboBox.getModel().getSize(); i++) {
            list.add((String) comboBox.getItemAt(i));
        }
    }
    

    @Override
    public void keyTyped(final KeyEvent e) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                String text = ((JTextField) e.getSource()).getText();
                if (text.length() == 0) {
                    setSuggestionModel(comboBox, new DefaultComboBoxModel(list.toArray()), "");
                    comboBox.hidePopup();
                } else {
                    ComboBoxModel m = getSuggestedModel(list, text);
                    if (m.getSize() == 0 || shouldHide) {
                        comboBox.hidePopup();
                    } else {
                        setSuggestionModel(comboBox, m, text);
                        comboBox.showPopup();
                    }
                }
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        JTextField textField = (JTextField) e.getSource();
        String text = textField.getText();
        shouldHide = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                for (String s : list) {
                    if (s.startsWith(text)) {
                        textField.setText(s);
                        return;
                    }
                }
                break;
            case KeyEvent.VK_ENTER:
                if (!list.contains(text)) {
                    list.add(text);
                    Collections.sort(list);
                    setSuggestionModel(comboBox, getSuggestedModel(list, text), text);
                }
                shouldHide = true;
                break;
            case KeyEvent.VK_ESCAPE:
                shouldHide = true;
                break;
        }
    }

    private static void setSuggestionModel(JComboBox comboBox, ComboBoxModel mdl, String str) {
        comboBox.setModel(mdl);
        comboBox.setSelectedIndex(-1);
        ((JTextField) comboBox.getEditor().getEditorComponent()).setText(str);
    }

    private static ComboBoxModel getSuggestedModel(List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (String s : list) {
            if (s.startsWith(text)) {
                m.addElement(s);
            }
        }
        return m;
    }
}
