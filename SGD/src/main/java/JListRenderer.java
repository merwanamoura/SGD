
import java.awt.Component;
import java.awt.Image;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hc047736
 */
class ListEntry
{
   private String value;
   private ImageIcon icon;
  
   public ListEntry(String value, ImageIcon icon) {
      this.value = value;
      this.icon = icon;
   }
  
   public String getValue() {
      return value;
   }
  
   public ImageIcon getIcon() {
      return icon;
   }
  
   public String toString() {
      return value;
   }
}
  

 class ListEntryCellRenderer extends DefaultListCellRenderer
{
   public Component getListCellRendererComponent(JList list, Object value,  int index, boolean isSelected,  boolean cellHasFocus) 
   {
        ListEntry entry = (ListEntry) value;

        JLabel label2 = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        ImageIcon imageIcon = new ImageIcon(entry.getIcon().getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT));
        label2.setIcon(imageIcon);
        label2.setText("<html><p>"+ label2.getText() +"</p></html>");
        
        label2.setHorizontalTextPosition(JLabel.RIGHT);
  
        return label2;
   }
}