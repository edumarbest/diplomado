package py.edu.ucsa.statements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import py.edu.ucsa.connections.Conexiones;
import py.edu.ucsa.connections.Drivers;
import py.edu.ucsa.connections.EnConexion;

public class ReadTxt {

   public static void main(String[] args) {
       try {
           Drivers.cargarDrivers();
           Connection conSource = Conexiones.obtenerConexion(EnConexion.DBMS_TYPE_ODBC_TXT);
           System.out.println("Hacemos algo para verificar la conexion: " + conSource.getAutoCommit());
           System.out.println("*********************************************");
           
/*          DatabaseMetaData dbmd = conSource.getMetaData();
          ResultSet rsTables = dbmd.getTables(null, null, null, null);
          while (rsTables.next()){
               String nameOriginTable = rsTables.getString("TABLE_NAME");
               System.out.println("******************************************************");
               System.out.println("Nombre: " + nameOriginTable);
               
               ResultSet rsColumns = dbmd.getColumns(null,null,nameOriginTable,null);
               while (rsColumns.next()){
                   String nameColumn = rsColumns.getString("COLUMN_NAME");
                   System.out.println("******************************************************");
                   System.out.println("Columna: " + nameColumn);
               }
               rsColumns.close();
               
           }
           rsTables.close();*/
           
           Statement stmt = conSource.createStatement();
           String query = "select * from FuncionariosNuevos.csv" ;
           ResultSet rs = stmt.executeQuery(query);
           while(rs.next()){
        	   System.out.println("Legajo:  "+ rs.getString(1));
               System.out.println("Fecha Ingreso:  "+ rs.getString("Fecha Ingreso"));
               System.out.println("Titular:  "+ rs.getString("Titular"));
               System.out.println("Departamento:  "+ rs.getString("Departamento"));
               System.out.println("Telefono:  "+ rs.getString("Tel�fono"));
               System.out.println("*********************************************");
           }
               
       } catch (ClassNotFoundException e) {
        e.printStackTrace();
       } catch (SQLException e) {
        e.printStackTrace();
    }
      
   }
}
