package teproductos.tmproductos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import teproductos.model.Data;
import teproductos.model.Producto;
import teproductos.exceptions.CategoriaNotFoundException;
import teproductos.exceptions.MarcaNotFoundException;

/**
 *
 * @author igor
 */
public class TMProductos implements TableModel {
    private final ArrayList<Producto> lista;
    private final Data dao;
    private final String[] titulos = {"ID", "Nombre", "Categoria", "Marca",
        "Precio", "Fecha", "Transgenic", "Disponible"};

    public TMProductos(ArrayList<Producto> lista, Data dao) {
        this.lista = lista;
        this.dao = dao;
    }
    
    public Producto getProducto(int row){
        return lista.get(row);
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return titulos[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return (columnIndex == 6 || columnIndex == 7) ? Boolean.class :
            Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto p = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getId();
            case 1:
                return p.getNombre();
            case 2: {
                try {
                    //Marca
                    return dao.getCategoria(p.getCategoria()).getNombre();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "SQLError", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(TMProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CategoriaNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "CategoriaNotFound", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(TMProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case 3: {
                try {
                    //Categoria
                    return dao.getMarca(p.getMarca()).getNombre();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "SQLError", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(TMProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MarcaNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "MarcaNotFound", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(TMProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case 4:
                //Precio
                return p.getPrecio();
            case 5:
                return p.getFecha();
            case 6:
                return p.isTransgenic();
            case 7:
                return p.isDisponible();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Producto p = lista.get(rowIndex);
        switch(columnIndex){
            case 0:
                p.setId((int)aValue);
                break;
            case 1:
                p.setNombre((String)aValue);
                break;
            case 2:
                p.setCategoria((int)aValue);
                break;
            case 3:
                p.setMarca((int)aValue);
                break;
            case 4:
                p.setPrecio((float)aValue);
                break;
            case 5:
                p.setFecha((String)aValue);
                break;
            case 6:
                p.setTransgenic((boolean)aValue);
                break;
            case 7:
                p.setDisponible((boolean)aValue);
                break;
        }
    }
    
    //Eso no lo necesitamos

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
