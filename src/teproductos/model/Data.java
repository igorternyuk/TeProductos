package teproductos.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import teproductos.conexion.Conexion;
import teproductos.exceptions.CategoriaNotFoundException;
import teproductos.exceptions.MarcaNotFoundException;
import teproductos.exceptions.ProductoNotFoundException;

/**
 *
 * @author igor
 */
public class Data {
    private Conexion conexion;
    private Connection con;
    private PreparedStatement sql = null;
    private ResultSet resultSet = null;

    public Data() throws SQLException, IOException {
        conexion = new Conexion();
        con = conexion.getConnection();
    }
    
    public Categoria getCategoria(int idCategoria) throws SQLException, CategoriaNotFoundException{
        sql = con.prepareStatement("select * from categoria where id= ?;");
        sql.setInt(1, idCategoria);
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            Categoria cat = new Categoria(resultSet.getInt(1), resultSet.getString(2));
            sql.close();
            return cat;
        } else {
            throw new CategoriaNotFoundException("La categoria con éste id no se encontró");
        }
    }
    
    public ArrayList<Categoria> getListaCategoria() throws SQLException{
        sql = con.prepareStatement("select * from categoria;");
        resultSet = sql.executeQuery();
        ArrayList<Categoria> lista = new ArrayList<>();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String nombre = resultSet.getString(2);
            Categoria cat = new Categoria(id, nombre);
            lista.add(cat);
        }
        sql.close();
        return lista;
    }
    
    public Marca getMarca(int idMarca) throws SQLException, MarcaNotFoundException{
        sql = con.prepareStatement("select * from marca where id=?;");
        sql.setInt(1, idMarca);
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            Marca marca = new Marca(resultSet.getInt(1), resultSet.getString(2));
            sql.close();
            return marca;
        } else {
            throw new MarcaNotFoundException("La marca con éste id no se encontró.");
        }
    }
    
    public ArrayList<Marca> getListaMarca() throws SQLException{
        sql = con.prepareStatement("select * from marca;");
        resultSet = sql.executeQuery();
        ArrayList<Marca> marcas = new ArrayList<>();
        while(resultSet.next()){
            Marca marca = new Marca(resultSet.getInt(1), resultSet.getString(2));
            marcas.add(marca);
        }
        sql.close();
        return marcas;
    }
    
    public Producto getProducto(int id) throws SQLException, ProductoNotFoundException{
        sql = con.prepareStatement("select * from producto where id= ?;");
        sql.setInt(1, id);
        resultSet = sql.executeQuery();
        Producto p = null;
        if(resultSet.next()){
            p = sacarProducto(resultSet);
            sql.close();            
        }
        return p;
    }

    public ArrayList<Producto> getListaProducto() throws SQLException {
        sql = con.prepareStatement("select * from producto;");
        resultSet = sql.executeQuery();
        ArrayList<Producto> lista = new ArrayList<>();
        while(resultSet.next()){
            Producto p = sacarProducto(resultSet);
            lista.add(p);
        }
        sql.close();
        return lista;
    }
    
    public void meterProducto(Producto p) throws SQLException{
        sql = con.prepareStatement("insert into producto values(null,?,?,?,?,?,?,?);");
        sql.setString(1, p.getNombre());
        sql.setInt(2, p.getCategoria());
        sql.setInt(3, p.getMarca());
        sql.setFloat(4, p.getPrecio());
        sql.setString(5, p.getFecha());
        sql.setBoolean(6, p.isTransgenic());
        sql.setBoolean(7, p.isDisponible());
        sql.executeUpdate();
        sql.close();
    }
    
    public void eliminarProducto(int id) throws SQLException{
        sql = con.prepareStatement("delete from producto where id = ?;");
        sql.setInt(1, id);
        sql.executeUpdate();
        sql.close();
    }
    
    public void modificarProducto(Producto p) throws SQLException{
        sql = con.prepareStatement("update producto set nombre= ?,categoria= ?,"
            + "marca= ?, precio= ?, fecha = ?,transgenic= ?, disponible= ? "
            + "where id= ?;");
        sql.setString(1, p.getNombre());
        sql.setInt(2, p.getCategoria());
        sql.setInt(3, p.getMarca());
        sql.setFloat(4, p.getPrecio());
        sql.setString(5, p.getFecha());
        sql.setBoolean(6, p.isTransgenic());
        sql.setBoolean(7, p.isDisponible());
        sql.setInt(8, p.getId());
        sql.executeUpdate();
        sql.close();
    }
    
    public ArrayList<Producto> buscarProductos(String regExp, boolean soloUnaCategoria,
        int id_categoria, boolean soloUnaMarca, int id_marca, boolean precio,
        float precioMin, float precioMax, boolean fecha, String fechaMin,
        String fechaMax, boolean noTrans, boolean disponible, 
        int orden_criterio, boolean ordenDescenso) throws SQLException {        
        StringBuilder sb = new StringBuilder("select producto.id, producto.nombre,"
            + " categoria.id, marca.id, precio, fecha, transgenic,"
            + " disponible from producto, categoria, marca where"
            + " producto.marca = marca.id and producto.categoria = categoria.id ");
        sb.append(" and (producto.nombre like '%");
        sb.append(regExp);
        sb.append("%' or marca.nombre like '%");
        sb.append(regExp);
        sb.append("%')");
        
        if(soloUnaCategoria) {
            sb.append(" and producto.categoria = ");
            sb.append(String.valueOf(id_categoria));
        }
        
        if(soloUnaMarca) {
            sb.append(" and producto.marca = ");
            sb.append(String.valueOf(id_marca));
        }
        
        if(precio){
            sb.append(" and (producto.precio >= ");
            sb.append(String.valueOf(precioMin));
            sb.append(" and ");
            sb.append("producto.precio <= ");
            sb.append(String.valueOf(precioMax));
            sb.append(")");
        }
        
        if(fecha){
            sb.append(" and (fecha >= '");
            sb.append(fechaMin);
            sb.append("' and fecha <= '");
            sb.append(fechaMax);
            sb.append("')");
        }
        
        if(noTrans){
            sb.append(" and transgenic = false");
        }
        
        if(disponible){
            sb.append(" and disponible = true");            
        }
        
        sb.append(" order by producto.");
        switch(orden_criterio){
            case 0:
                sb.append("id");
                break;
            case 1:
                sb.append("nombre");
                break;
            case 2:
                sb.append("categoria");
                break;
            case 3:
                sb.append("marca");
                break;
            case 4:
                sb.append("precio");
                break;
            case 5:
                sb.append("fecha");
                break;
        }
        
        sb.append(ordenDescenso ? " desc;" : ";");
        System.out.println("SQL=" + sb.toString());
        sql = con.prepareStatement(sb.toString());
        resultSet = sql.executeQuery();
        /*sql = con.prepareStatement("select producto.nombre, categoria.nombre, "
            + "marca.nombre, precio, fecha, transgenic, disponible from "
            + "producto, categoria, marca where producto.marca = marca.id "
            + "and producto.categoria = categoria.id "
            + "and (producto.nombre like ? or marca.nombre like ?) "
            + "and producto.fecha > '2017-02-25' and "
            + "producto.fecha < '2017-05-06') and"
            + "(transgenic = true or disponible = false)"
            + "order by producto.precio desc;");*/
        ArrayList<Producto> lista = new ArrayList<>();
        while(resultSet.next()){
            Producto p = sacarProducto(resultSet);
            lista.add(p);
        }
        return lista;
    }
    
    private Producto sacarProducto(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String nombre = resultSet.getString(2);
        int categoria = resultSet.getInt(3);
        int marca = resultSet.getInt(4);
        float precio = resultSet.getFloat(5);
        String fecha = resultSet.getDate(6).toString();
        boolean trans = resultSet.getBoolean(7);
        boolean disponible = resultSet.getBoolean(8);
        Producto p = new Producto(id, nombre, categoria, marca, precio, fecha,
            trans, disponible);
        return p;
    }
}
