package teproductos.model;

import java.sql.Date;

/**
 *
 * @author igor
 */
public class Producto {
    int id;
    String nombre;
    int categoria;
    int marca;
    float precio;
    //String fecha;
    Date fecha;
    boolean transgenic;
    boolean disponible;

    public Producto() {
    }

    public Producto(String nombre, int categoria, int marca, float precio,
           Date fecha, boolean transgenic, boolean disponible) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
        this.fecha = fecha;
        this.transgenic = transgenic;
        this.disponible = disponible;
    }

    public Producto(int id, String nombre, int categoria, int marca, float precio,
                    Date fecha, boolean transgenic, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
        this.precio = precio;
        this.fecha = fecha;
        this.transgenic = transgenic;
        this.disponible = disponible;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public boolean isTransgenic() {
        return transgenic;
    }

    public void setTransgenic(boolean transgenic) {
        this.transgenic = transgenic;
    }
}
