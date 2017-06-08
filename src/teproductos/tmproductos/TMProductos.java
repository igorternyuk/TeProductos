package teproductos.tmproductos;

import java.sql.Date;
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
    private static final int COLUMN_ID = 0;
    private static final int COLUMN_NOMBRE = 1;
    private static final int COLUMN_CATEGORIA = 2;
    private static final int COLUMN_MARCA = 3;
    private static final int COLUMN_PRECIO = 4;
    private static final int COLUMN_FECHA = 5;
    private static final int COLUMN_TRANSGENIC = 6;
    private static final int COLUMN_DISPONIBLE = 7;
    
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
        if(lista.isEmpty()){
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto p = lista.get(rowIndex);
        switch (columnIndex) {
            case COLUMN_ID:
                return p.getId();
            case COLUMN_NOMBRE:
                return p.getNombre();
            case COLUMN_MARCA: {
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
            case COLUMN_CATEGORIA: {
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
            case COLUMN_PRECIO:
                //Precio
                return p.getPrecio();
            case COLUMN_FECHA:
                return p.getFecha();
            case COLUMN_TRANSGENIC:
                return p.isTransgenic();
            case COLUMN_DISPONIBLE:
                return p.isDisponible();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Producto p = lista.get(rowIndex);
        switch(columnIndex){
            case COLUMN_ID:
                p.setId((int)aValue);
                break;
            case COLUMN_NOMBRE:
                p.setNombre((String)aValue);
                break;
            case COLUMN_CATEGORIA:
                p.setCategoria((int)aValue);
                break;
            case COLUMN_MARCA:
                p.setMarca((int)aValue);
                break;
            case COLUMN_PRECIO:
                p.setPrecio((float)aValue);
                break;
            case COLUMN_FECHA:
                p.setFecha((Date)aValue);
                break;
            case COLUMN_TRANSGENIC:
                p.setTransgenic((Boolean)aValue);
                break;
            case COLUMN_DISPONIBLE:
                p.setDisponible((Boolean)aValue);
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
