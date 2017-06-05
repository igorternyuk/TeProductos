package teproductos.conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author igor
 */
public class Conexion {
    private final String PATH_TO_FILE = "config/config.txt";
    private final Connection con;
    private PreparedStatement sentencia;
    private ResultSet resultSet;
    private String server;
    private String database;
    private String user;
    private String pass;
    
    public Conexion() throws SQLException, IOException{
        leerArchivo(PATH_TO_FILE);
        StringBuilder sb = new StringBuilder("jdbc:mysql://");
        sb.append(server);
        sb.append("/");
        sb.append(database);
        sb.append("?");
        sb.append("user=");        
        sb.append(user);
        sb.append("&");
        sb.append("password=");
        sb.append(pass);
        /*String protocolo = "jdbc:mysql://";
        String lineaUsuario = "user=igor";
        String lineaPass = "password=1319";
        String servidor = "localhost";
        String bd_nombre = "bd_productos";*/
        String url = sb.toString();
        System.out.println("URL = " + url);
        //Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url);
    }
    
    public Connection getConnection(){
        return con;
    }
    
    private enum LoadState{
        SERVER,
        USER,
        PASSWORD,
        DATABASE,
        STOP
    }
    
    private void leerArchivo(String path) throws UnsupportedEncodingException, IOException{
        InputStream ins = getClass().getResourceAsStream(path);
        if(ins == null){
            System.out.println("Не создан водящий поток");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
        LoadState ls;
        String str;
        while((str = br.readLine()) != null){
            if(str.equalsIgnoreCase("[Server]")){
                ls = LoadState.SERVER;
            } else if(str.equalsIgnoreCase("[User]")){
                ls = LoadState.USER;
            } else if(str.equalsIgnoreCase("[Password]")){
                ls = LoadState.PASSWORD;
            } else if(str.equalsIgnoreCase("[Database]")){
                ls = LoadState.DATABASE;
            } else{
                ls = LoadState.STOP;
            }
            str = br.readLine();
            switch(ls){
                case SERVER:
                    server = str;
                    break;
                case USER:
                    user = str;
                    break;
                case PASSWORD:
                    pass = str;
                    break;
                case DATABASE:
                    database = str;
                    break;
                case STOP:
                    break;
            }
        }
    }
}
