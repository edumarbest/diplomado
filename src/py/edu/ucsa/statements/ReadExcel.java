package py.edu.ucsa.statements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import py.edu.ucsa.connections.Conexiones;
import py.edu.ucsa.connections.Drivers;
import py.edu.ucsa.connections.EnConexion;

public class ReadExcel {

    public static void main(String[] args) {
        try {
            Drivers.cargarDrivers();
            Connection conSource = Conexiones.obtenerConexion(EnConexion.DBMS_TYPE_ODBC_EXCEL);
            System.out.println("Hacemos algo para verificar la conexion: " + conSource.getAutoCommit());
            System.out.println("*********************************************");
            
            String query = "select * from [FuncionariosNuevos$] where activo like 'Si'";
            
            Statement stmt = conSource.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            int count = 1; 
            while(rs.next()){
                System.out.println("N� Orden Resultado: " + (count++));
                System.out.println("Legajo:  "+ rs.getString("Legajo"));
                System.out.println("Fecha Ingreso:  "+ rs.getString("Fecha Ingreso"));
                System.out.println("Titular:  "+ rs.getString("Titular"));
                System.out.println("Departamento:  "+ rs.getString("Departamento"));
                System.out.println("Telefono:  "+ rs.getString("Tel�fono"));
                System.out.println("*********************************************");
            }
            conSource.close();
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

}
