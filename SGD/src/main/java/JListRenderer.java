
import java.awt.Component;
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
        label2.setIcon(entry.getIcon());
        label2.setHorizontalTextPosition(JLabel.RIGHT);
  
        return label2;
   }
}