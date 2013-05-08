package py.edu.ucsa.connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Test {

    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        Connection con  = null;
        try {
    /*        Class.forName("org.postgresql.Driver");            
            String url = "jdbc:postgresql://localhost:5432/diplomado";         
            con = DriverManager.getConnection(url, "postgres", "postgres");  */    
            
            
            /*DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTypeInfo();
            System.out.println("INTEGER " + Types.INTEGER);
            System.out.println("BOOLEAN " + Types.BOOLEAN);
            while(rs.next()){
                System.out.println("Nombre del Tipo " + rs.getString("TYPE_NAME") + 
                        " Tipo de Dato " + rs.getString("DATA_TYPE"));
                
            }*/
            
            Drivers.cargarDrivers();
            //Connection conOracle = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_ORACLE);
            Connection conPostgres = Conexiones.obtenerConexion(EnConexion.DBMS_TYPE_POSTGRES);
            /*Statement stmt = conPostgres.createStatement();
            ResultSet rs = stmt.executeQuery("select * from alumnos");
            
            while(rs.next()){
                System.out.println("Cedula " + rs.getInt("cedula"));
                System.out.println("nombre " + rs.getString("nombre"));
                
                System.out.println("direccion " + rs.getString("direccion"));
                System.out.println("telefono " + rs.getString("telefono"));
            }*/
            PreparedStatement pstmt = conPostgres.prepareStatement("insert into alumnos (cedula,nombre,direccion) values(?,?,?)");
            pstmt.setInt(1,234);
            pstmt.setString(2,"Maria Benitez");
            pstmt.setNull(3,Types.VARCHAR);
            pstmt.executeUpdate();
            
        } catch (ClassNotFoundException e) {         
            System.out.println("No se encontro el driver");
            e.printStackTrace();         
        } catch (SQLException e) {
            System.out.println("No se pudo conectar" +  e.getMessage());
            e.printStackTrace();
        }
    }
}
