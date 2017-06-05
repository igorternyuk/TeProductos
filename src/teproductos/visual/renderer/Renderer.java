package teproductos.visual.renderer;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author igor
 */
public class Renderer extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column){
        if(value instanceof JCheckBox){
            JCheckBox checkBox = (JCheckBox)value;
            return checkBox;
        }        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
