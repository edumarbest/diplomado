package usa.edu.py;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TabableView;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.freixas.jcalendar.JCalendarCombo;

import py.edu.ucsa.connections.Conexiones;
import py.edu.ucsa.connections.ManejadorBD;
import py.edu.ucsa.connections.opAbm;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmFuncionario extends JFrame {

	private JPanel pnlContenedor;
	private JPanel pnlFiltro;
	private JPanel pnlFuncionario;
	private JPanel pnlAcciones;
	private JPanel pnlMover;
	private JPanel pnlABM;
	private JPanel pnlBuscar;
	private JTable table;
	private JButton btnAdelante;
	private JButton btnAtras;
	private JButton btnPrimero;
	private JButton btnUltimo;
	private JButton btnFiltro;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnGuardar;
	private JButton btnSalir;
	private JComboBox cboActivo;
	private DefaultTableModel modelo;
	private JScrollPane scroll;
	private JTextField txtFiltro = new JTextField();
	private JTextField txtLegajo = new JTextField();
	private JTextField txtFecha = new JTextField();
	private JTextField txtTitular = new JTextField();
	private JTextField txtDepartamento = new JTextField();
	private JTextField txtTelefono = new JTextField();
	private static ManejadorBD manDB = new  ManejadorBD();
	private ResultSet res;
	private JPanel pnlTexto;
	private JLabel lblFiltro = new JLabel();
	private JLabel lblLegajo = new JLabel();
	private JLabel lblFecha = new JLabel();
	private JLabel lblTitular = new JLabel();
	private JLabel lblDepartamento = new JLabel();
	private JLabel lblTelefono = new JLabel();
	private JLabel lblActivo = new JLabel();
	private opAbm operacion;
	private JCalendarCombo cboCalFecha = new JCalendarCombo();

	
	
	private enum banderaBoton{
		PRIMERO, ANTERIOR, SIGUIENTE, ULTIMO;
	}
	
	//main
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
					FrmFuncionario frame = new FrmFuncionario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//fin de main

	//constructor
	public FrmFuncionario() {
		setTitle("Buscar Funcionarios");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 640);
		pnlContenedor = new JPanel();
		pnlContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlContenedor);
		pnlContenedor.setLayout(new GridLayout(3, 1));
		
		pnlABM = new JPanel(new FlowLayout());
		pnlMover = new JPanel(new FlowLayout());
		pnlFuncionario = new JPanel();
		pnlFuncionario.setBorder(new TitledBorder(null, "Funcionarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlContenedor.add(pnlFuncionario);
		pnlFuncionario.setLayout(new GridLayout(1, 0, 0, 0));
		
		table = new JTable();
		modelo = new DefaultTableModel();
		configGrilla();
		cargarGrilla();
		scroll = new JScrollPane();
		table.setModel(modelo);	
		scroll.setViewportView(table);
		pnlFuncionario.add(scroll);
		
		btnAdelante = new JButton();
		btnAdelante.setEnabled(false);
		btnAtras = new JButton();
		btnAtras.setEnabled(false);
		btnPrimero = new JButton();
		
		btnPrimero.setEnabled(false);
		btnUltimo = new JButton();
		btnUltimo.setEnabled(false);
		btnFiltro = new JButton();
		btnAgregar = new JButton();
		
		btnModificar = new JButton();
		btnModificar.setEnabled(false);
		btnBorrar = new JButton();
		btnBorrar.setEnabled(false);
		btnSalir = new JButton();
		btnGuardar = new JButton();
		btnGuardar.setEnabled(false);
		cboActivo = new JComboBox(new String[]{"si", "no"});
		cboActivo.setEnabled(false);
		btnAdelante.setText(">");
		btnAdelante.setToolTipText("Siguiente");
		btnAdelante.setPreferredSize(new Dimension(80,30));
		btnAtras.setText("<");
		btnAtras.setToolTipText("Atras");
		btnAtras.setPreferredSize(new Dimension(80,30));
		btnPrimero.setText("<<");
		btnPrimero.setToolTipText("Primero");
		btnPrimero.setPreferredSize(new Dimension(80,30));
		btnUltimo.setText(">>");
		btnUltimo.setToolTipText("Último");
		btnUltimo.setPreferredSize(new Dimension(80,30));
		btnFiltro.setText("Buscar");
		btnFiltro.setToolTipText("presione para buscar");
		
		btnAgregar.setText("Nuevo");
		btnAgregar.setToolTipText("Nuevo Funcionario");
		btnAgregar.setPreferredSize(new Dimension(80,30));
		
		btnModificar.setText("Modificar");
		btnModificar.setToolTipText("Modificar Funcionario");
		btnModificar.setPreferredSize(new Dimension(80,30));
		
		btnBorrar.setText("Borrar");
		btnBorrar.setToolTipText("Borrar Funcionario");
		btnBorrar.setPreferredSize(new Dimension(80,30));

		
		btnSalir.setText("Salir");
		btnSalir.setToolTipText("Salir");
		btnSalir.setPreferredSize(new Dimension(80,30));
		
		btnGuardar.setText("Guardar");
		btnGuardar.setToolTipText("Guardar");
		btnGuardar.setPreferredSize(new Dimension(80,30));
		
		pnlTexto = new JPanel(new GridLayout(6,2));
		lblLegajo.setText("Legajo");
		txtTitular.setEnabled(false);
		txtTitular.setPreferredSize(new Dimension(100,30));
		lblTitular.setText("Titular");
		txtFecha.setEnabled(false);
		txtFecha.setPreferredSize(new Dimension(100,30));
		lblFecha.setText("Fecha");
		txtDepartamento.setEnabled(false);
		txtDepartamento.setPreferredSize(new Dimension(100,30));
		lblDepartamento.setText("Departamento");
		txtTelefono.setEnabled(false);
		txtTelefono.setPreferredSize(new Dimension(100,30));
		lblTelefono.setText("Telefono");
		cboActivo.setEditable(false);
		cboActivo.setPreferredSize(new Dimension(100,30));
		lblActivo.setText("Activo");
		
		pnlTexto.add(lblLegajo);
		txtLegajo.setEnabled(false);
		pnlTexto.add(txtLegajo);
		pnlTexto.add(lblFecha);
		cboCalFecha.setEnabled(false);
		cboCalFecha.setEditable(true);
		//pnlTexto.add(txtFecha);
		pnlTexto.add(cboCalFecha);
		pnlTexto.add(lblTitular);
		pnlTexto.add(txtTitular);
		pnlTexto.add(lblDepartamento);
		pnlTexto.add(txtDepartamento);
		pnlTexto.add(lblTelefono);
		pnlTexto.add(txtTelefono);
		pnlTexto.add(lblActivo);
		pnlTexto.add(cboActivo);
		
		pnlBuscar = new JPanel(new FlowLayout());
		
		pnlABM.add(btnAgregar);
		pnlABM.add(btnBorrar);
		pnlABM.add(btnModificar);
		pnlABM.add(btnSalir);
		pnlABM.add(btnGuardar);
				
		pnlFiltro = new JPanel(new GridLayout(1,1));
		
		
		txtFiltro.setPreferredSize(new Dimension(180,25));
		pnlBuscar.add(txtFiltro);
		pnlBuscar.add(btnFiltro);
		
		pnlFiltro.add(pnlTexto);
		
		pnlAcciones = new JPanel( new FlowLayout());
		pnlAcciones.setBorder(new TitledBorder(null, "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMover.add(btnPrimero);
		pnlMover.add(btnAtras);
		pnlMover.add(btnAdelante);
		pnlMover.add(btnUltimo);
		
		pnlAcciones.add(pnlMover);
		pnlAcciones.add(pnlABM);
		pnlAcciones.add(pnlBuscar);
		pnlContenedor.add(pnlFiltro);
		pnlContenedor.add(pnlAcciones);
		
		accionDesplazar(btnPrimero);
		accionDesplazar(btnAtras);
		accionDesplazar(btnAdelante);
		accionDesplazar(btnUltimo);
		
		txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroKeyReleased(evt);
            }
        });
		
		btnFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroActionPerformed(evt);
            }
        });
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
		
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int op = JOptionPane.showConfirmDialog(null, "queres pio borrar legalmente?");
					if(op == JOptionPane.OK_OPTION){
						abm(opAbm.BORRAR);
						cargarGrilla();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarTexto(true);
				operacion = opAbm.INSERTAR;
				btnGuardar.setEnabled(true);
				activarBotones(false);
				System.out.println(operacion);
			}
		});
		
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activarTexto(true);
				activarBotones(false);
				btnAgregar.setEnabled(false);
				btnGuardar.setEnabled(true);
				operacion = opAbm.MODIFICAR;
				System.out.println(operacion);
			}
		});
		
		accionAbm(btnGuardar);
		accionAbm(btnBorrar);
		
		
	}//fin de contructor

	//metodo para configurar la grilla
	private void configGrilla(){
		modelo.addColumn("Legajo");
		modelo.addColumn("Fecha de Ingreso");
		modelo.addColumn("Titular");
		modelo.addColumn("Departamento");
		modelo.addColumn("Teléfono");
		modelo.addColumn("Activo");
		
	}//fin de configGrilla
	
	//método para cargar la grilla
	private void cargarGrilla(){
		String sql = "SELECT legajos, fecha, titular, departamento, " +
				"telefono, activo FROM funcionarios";
		//Esto es para el filtro
		if(txtFiltro.getText().length() > 0){
			sql = sql+" where titular like '%"+txtFiltro.getText().toString()+"%'";
		}
		System.out.println(sql);	
		String[] datos = new String[6];
		
		try {
			res = manDB.getQueryResult(sql);
			modelo.setRowCount(0);
			while (res.next()) {
				datos[0] = res.getString(1);
				datos[1] = res.getString(2);
				datos[2] = res.getString(3);
				datos[3] = res.getString(4);
				datos[4] = res.getString(5);
				datos[5] = res.getString(6);
				modelo.addRow(datos);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//fin de cargar grilla
	
	private void activarBotones(boolean si){
		btnAdelante.setEnabled(si);
		btnAtras.setEnabled(si);
		btnUltimo.setEnabled(si);
		btnPrimero.setEnabled(si);
		btnModificar.setEnabled(si);
		btnBorrar.setEnabled(si);
	}

	private void verDatos(){
		int fila = table.getSelectedRow();
		String sql = "SELECT legajos, fecha, titular, departamento, telefono, " +
				"activo FROM funcionarios where legajos = "+modelo.getValueAt(fila, 0);
		try {
			res = manDB.getQueryResult(sql);
			while (res.next()) {
				txtLegajo.setText(res.getString(1));
				cboCalFecha.setDate(formatearFecha(res.getString(2)));
				txtTitular.setText(res.getString(3));
				txtDepartamento.setText(res.getString(4));
				txtTelefono.setText(res.getString(5));
				cboActivo.setSelectedItem(res.getString(6));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//fin de ver datos
	

	private void verTexto(banderaBoton accion){
		String sql = "SELECT legajos, fecha, titular, departamento, telefono, " +
				"activo FROM funcionarios";
		try {
			res = manDB.recorreDatos(sql);
			switch (accion) {
			case PRIMERO:
				//res.first();
				//table.changeSelection(0, 1, false, false);
			break;
			case ANTERIOR:
				//res.previous();
				if(table.getSelectedRow() !=0){
					sql = sql+" where legajos = "+modelo.getValueAt(table.getSelectedRow() -1, 0);
					res = manDB.recorreDatos(sql);
					res.next();
					table.changeSelection(table.getSelectedRow() -1, 1, false, false);
				}
			break;
			case SIGUIENTE:				
				if(table.getSelectedRow() < table.getRowCount() -1){
					sql = sql+" where legajos = "+modelo.getValueAt(table.getSelectedRow() +1, 0);
					res = manDB.recorreDatos(sql);
					res.next();
					table.changeSelection(table.getSelectedRow() +1, 1, false, false);						
				}
			break;
			case ULTIMO:
				res.last();
				table.changeSelection(table.getRowCount() -1, 1, false, false);
			break;

		}
			
			txtLegajo.setText(res.getString(1));
			//txtFecha.setText(res.getString(2));
			cboCalFecha.setDate(formatearFecha(res.getString(2)));
			txtTitular.setText(res.getString(3));
			txtDepartamento.setText(res.getString(4));
			txtTelefono.setText(res.getString(5));
			cboActivo.setSelectedItem(res.getString(6));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}//Fin de Ver Texto al Desplazar
	
	private void activarTexto(boolean si){
		txtLegajo.setEnabled(si);
		cboCalFecha.setEnabled(si);
		txtTitular.setEnabled(si);
		txtDepartamento.setEnabled(si);
		txtTelefono.setEnabled(si);
		cboActivo.setEnabled(si);
	}//Fin de Activar Texto
	
	
	private void txtFiltroKeyReleased(java.awt.event.KeyEvent evt) {                                      
        cargarGrilla();
    }//fin de evento filtrar del txt
	
	private void btnFiltroActionPerformed(java.awt.event.ActionEvent evt) {                                         
        cargarGrilla();
    }  //fin de evento filtrar del boton

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {   
		activarBotones(true);
		verDatos();
	}//fin de evento
	
	private void accionDesplazar(JButton boton){
		if(boton.getText().equals("<<")){
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						verTexto(banderaBoton.PRIMERO);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			});
		}
		
		if(boton.getText().equals("<")){
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						verTexto(banderaBoton.ANTERIOR);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			});
		}
		
		if(boton.getText().equals(">")){
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						verTexto(banderaBoton.SIGUIENTE);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			});
		}else{
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						verTexto(banderaBoton.ULTIMO);
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			});
		}
	}//Fin de Deplazar
	
	private void accionAbm(JButton boton){
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(operacion == opAbm.INSERTAR){
						try {
							abm(opAbm.INSERTAR);
							cargarGrilla();
							limpiarTexto();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else if(operacion == opAbm.MODIFICAR){
						try {
							abm(opAbm.MODIFICAR);
							cargarGrilla();
							limpiarTexto();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}	
					
					activarBotones(false);
					btnAgregar.setEnabled(true);
					btnGuardar.setEnabled(false);
					activarTexto(false);
				}
			});
	}
	
	
	private void abm(opAbm abm) throws SQLException{
		String sql ="";
		String fecha = formatearFecha((Date) cboCalFecha.getDate()).toString();
		switch (abm) {
		case INSERTAR:
			sql = "INSERT INTO funcionarios(legajos, fecha, titular, departamento, " +
					"telefono, activo) VALUES ("+Integer.parseInt(txtLegajo.getText())+
					", '"+fecha+"', '" +
					txtTitular.getText()+"', '" +
					txtDepartamento.getText()+"', '" +
					txtTelefono.getText()+"', '" +
					cboActivo.getSelectedItem().toString()+"')";
			System.out.println(sql);
			break;
		case MODIFICAR:
			sql = "UPDATE funcionarios SET " +
					"legajos="+Integer.parseInt(txtLegajo.getText())+", " +
					"fecha='"+fecha+"', " +
					"titular= '"+txtTitular.getText()+"', " +
					"departamento='"+txtDepartamento.getText()+"', " +
					"telefono= '"+txtTelefono.getText()+"', " +
					"activo='"+cboActivo.getSelectedItem().toString()+
					"' WHERE legajos="+Integer.parseInt(txtLegajo.getText());
			break;
		case BORRAR:
			sql ="DELETE FROM funcionarios  WHERE legajos ="+Integer.parseInt(txtLegajo.getText());
			break;
		}
		
		manDB.actualizarDatos(sql, abm);
	}
	
	private Date formatearFecha(String fecha){
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
        Date formatFecha = null;
        try {
			formatFecha = new Date(formatter.parse( fecha ).getTime( ));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return formatFecha;
	}
	
	
	private String formatearFecha(Date fecha){
		java.text.SimpleDateFormat dia = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String Fecha = dia.format(fecha);
        return Fecha;
	}
	
	private void limpiarTexto(){
		txtLegajo.setText(null);
		//cboCalFecha.setD;
		txtTitular.setText(null);
		txtDepartamento.setText(null);
		txtTelefono.setText(null);
	}
	
}
