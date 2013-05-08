package py.edu.ucsa.connections;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ManejadorBD {

    public static Connection con = null;
    private static ResultSet rs = null;
    private static Statement stm  = null;
    private static PreparedStatement pstm = null;
    
	static{
        try {
            Drivers.cargarDrivers();
            con = Conexiones.obtenerConexion(EnConexion.DBMS_TYPE_POSTGRES);
        } catch (ClassNotFoundException e) {          
            System.out.println("No se pudo cargar el driver " + e);
        } catch (SQLException e) {
            System.out.println("No se pudo conectar" + e);            
        }        
    }
    
    //Este metodo puede remplazarse con el de la clase Conexiones
	public static Connection getConnection(EnConexion vendor,String host,String port,
													String userName,String password,String databaseName){
		try {
			Drivers.cargarDrivers();			
			if (vendor == EnConexion.DBMS_TYPE_POSTGRES){
				String url = "jdbc:postgresql://"+host+":"+port+"/"+databaseName;			
				con = DriverManager.getConnection(url, userName, password);	
			}
			System.out.println("Se conecto exitosamente " + con.getAutoCommit());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
			e.printStackTrace();
		}

		return con;
	}
    
    public ResultSet getQueryResult(String query) throws Exception{
    	Statement stmt = null;
    	ResultSet rs;
    	try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {					
			throw new Exception("Error de SQL al realizar la consulta -> " + e.getMessage());
		}		
    	return rs;    	
    }       
        public static void insertarFecha(){
            SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy" );
            Date fecha = null;
            try {
                fecha = new Date(formatter.parse( "10-05-2010" ).getTime( ));
            } catch (ParseException e1) {             
                e1.printStackTrace();
            }
            try {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO probandoDate values (?,?)");
                pstmt.setInt(1,1);
                pstmt.setDate(2,fecha);
                pstmt.executeUpdate();
                System.out.println("Se insert� exitosamente");
            } catch (SQLException e) {             
                e.printStackTrace();
            }
            
        }

        public ResultSet recorreDatos(String sql){
        	
        	try {
				pstm = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, 
						ResultSet.CONCUR_UPDATABLE);
				rs = pstm.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	return rs;
        }
        
        public void actualizarDatos(String sql, opAbm accion) throws SQLException {
        	stm = con.createStatement();
        	switch (accion) {
			case INSERTAR:
					stm.executeUpdate(sql);
				break;
			case BORRAR:
					stm.executeUpdate(sql);
				break;
			case MODIFICAR:
					stm.executeUpdate(sql);
				break;

			}
		}
        
		public static int getFilaActual() throws SQLException {
			int filaActual = rs.getRow();
			System.out.println("Desde el manejador " + filaActual);
			return filaActual;			
		}
}
