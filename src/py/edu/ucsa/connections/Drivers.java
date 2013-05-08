
package py.edu.ucsa.connections;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;


public class Drivers {
	
	public static void cargarDrivers() throws ClassNotFoundException {

	    // Cargamos el driver de oracle
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		//Cargamos el driver de postgres
        Class.forName("org.postgresql.Driver");
        //Cargamos el driver bridge JDBC-ODBC
		//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");		
	}
	
	public static void main(String[] args) {
	   try {
			cargarDrivers();
			
            Enumeration<Driver> e = DriverManager.getDrivers();
		    while( e.hasMoreElements()) {
                 Driver driver = (Driver)e.nextElement();
		         System.out.println("DRIVER: " + driver);                 
		     }			
		} catch (ClassNotFoundException e) {
			// No se encontr� alguna clase.
			e.printStackTrace();
		}         

	}
}
