
package py.edu.ucsa.connections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexiones {
	
	public static void main(String [] args){
		try {
			Drivers.cargarDrivers();
			Connection c = obtenerConexion(EnConexion.DBMS_TYPE_POSTGRES);
			System.out.println("Hacemos algo para verificar la conexion: " + c.getAutoCommit());
			
			//Metadatos
			DatabaseMetaData db = c.getMetaData();
		
			//Nombre del producto
			System.out.println("DB PRODUCT NAME: " + db.getDatabaseProductName());
			
            //tenemos que cerrar si todo funciona bien.
			c.close();
			
		} catch (SQLException e) {
			// Ocurrio un error al conectarse
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// No se encontr� la clase del driver
			e.printStackTrace();
		}
	}
		
    //Devuelve la conexi�n de acuerdo tipo de DBMS
	public static Connection obtenerConexion(EnConexion DBMS_TYPE) throws SQLException {
		String url;
		Connection con = null;
		Properties prop = new Properties();
		try {
			prop.load(Conexiones.class.getResourceAsStream("Connection.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String hostName = prop.getProperty("HostName");
        String sid = prop.getProperty("SID");
        String port = prop.getProperty("Port");
        String userName = prop.getProperty("UserName");
        String pass = prop.getProperty("Password");
		
		switch (DBMS_TYPE) {
		case DBMS_TYPE_ORACLE: 
            url = "jdbc:oracle:thin:@"+hostName+":"+port+":"+sid;
		    con = DriverManager.getConnection(url, userName,pass);			
			break;
		case DBMS_TYPE_POSTGRES:
			url = "jdbc:postgresql://"+hostName+":"+port+"/"+sid;			
			con = DriverManager.getConnection(url, userName, pass);			
			break;
        case DBMS_TYPE_ODBC_ORACLE:
            url="jdbc:odbc:OracleODBC";
            con = DriverManager.getConnection(url,"xe","123456");
            break;
        case DBMS_TYPE_ODBC_EXCEL:
            url="jdbc:odbc:funcionarios";
            con = DriverManager.getConnection(url);
            break;
        case DBMS_TYPE_ODBC_TXT:
            url="jdbc:odbc:TextFiles";
            con = DriverManager.getConnection(url);
            break;
		}
		return con;
	}
}
