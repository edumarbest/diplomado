package usa.edu.py;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.freixas.jcalendar.JCalendar;
import org.freixas.jcalendar.JCalendarCombo;

import py.edu.ucsa.connections.ManejadorBD;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FrmPersona extends JFrame {
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JLabel	lblEdad;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JCalendarCombo cboEdad;
	private JPanel pnlPrincipal;
	private ArrayList<Object> personaje = new ArrayList<Object>();
	private JPanel contentPane;
	private JPanel pnlAccion;
	private JPanel pnlTexto;
	private JPanel pnlTabla;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	private JCalendarCombo cboInicio;
	private JCalendarCombo cboFin;
	private JTable tabla;
	private DefaultTableModel modelo;
	private JScrollPane scroll;
	private ManejadorBD manDB = new ManejadorBD();
	private ResultSet res;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPersona frame = new FrmPersona();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public FrmPersona() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		tabla = new JTable();
		modelo = new DefaultTableModel();
		tabla.setModel(modelo);
		scroll = new JScrollPane();
		configGrilla();
		scroll.setViewportView(tabla);
		
		lblApellido = new JLabel();
		lblNombre = new JLabel();
		lblEdad = new JLabel();
		txtNombre = new JTextField();
		txtNombre.setEnabled(false);
		txtApellido = new JTextField();
		txtApellido.setEnabled(false);
		txtBuscar = new JTextField();
		cboEdad = new JCalendarCombo();
		cboEdad.setEnabled(false);
		cboInicio = new JCalendarCombo();
		cboFin = new JCalendarCombo();
		btnBuscar = new JButton();
		
		txtNombre.setPreferredSize(new Dimension(100,30));
		txtApellido.setPreferredSize(new Dimension(100,30));
		txtBuscar.setPreferredSize(new Dimension(100,30));
		btnBuscar.setPreferredSize(new Dimension(80,30));
		btnBuscar.setText("Buscar");
		
		lblApellido = new JLabel();
		lblNombre = new JLabel();
		lblEdad = new JLabel();
		
		lblApellido.setText("Apellido");
		lblNombre.setText("Nombre");
		lblEdad.setText("Fecha nac");
		
		
		pnlPrincipal = new JPanel(new GridLayout(3,1));
		pnlTexto = new JPanel(new GridLayout(3,2));
		pnlAccion = new JPanel(new FlowLayout());
		pnlAccion.setBorder(new TitledBorder(null, "Acci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		pnlAccion.add(cboInicio);
		pnlAccion.add(cboFin);
		pnlAccion.add(txtBuscar);
		pnlAccion.add(btnBuscar);
		
		pnlTexto.add(lblNombre);
		pnlTexto.add(txtNombre);
		pnlTexto.add(lblApellido);
		pnlTexto.add(txtApellido);
		pnlTexto.add(lblEdad);
		pnlTexto.add(cboEdad);
		pnlPrincipal.add(scroll);
		pnlPrincipal.add(pnlTexto);
		pnlPrincipal.add(pnlAccion);
		contentPane.add(pnlPrincipal);
		
		btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modelo.setRowCount(0);
					personaje.clear();
					cargarPersona();
					cargaGrilla();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}

	private void configGrilla(){
		modelo.addColumn("Id");
		modelo.addColumn("Nombre");
		modelo.addColumn("Apellido");
		modelo.addColumn("Fecha nacional");
		
	}//fin de configGrilla
	
	private void cargarPersona() throws Exception{
		String sql = "SELECT id, nombre, apellido, fecha_nac FROM personas where fecha_nac between '"+
				formatearFecha(cboInicio.getDate())+"' and '"+formatearFecha(cboFin.getDate())+"'";
		if(txtBuscar.getText().length() > 0)
			sql = sql+" and nombre like '%"+txtBuscar.getText().toString()+"%'";
		System.out.println(sql);
		res = manDB.getQueryResult(sql);
		while (res.next()) {
			ArrayList<Object> newRow = new ArrayList<Object>();
			//persona = new Persona(res.getString(1),res.getString(2),res.getString(3),res.getString(4));
			newRow.add(res.getString(1));
			newRow.add(res.getString(2));
			newRow.add(res.getString(3));
			newRow.add(res.getString(4));
			personaje.add(newRow);
		}
		
	}
	
	private String formatearFecha(Date fecha){
		java.text.SimpleDateFormat dia = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String Fecha = dia.format(fecha);
        return Fecha;
	}
	
	private Date formatearFecha(String fecha){
		java.text.SimpleDateFormat dia = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date fechaformat = null;
        try {
			fechaformat = dia.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return fechaformat;
	}
	
	private void  cargaGrilla() throws Exception{
		
		Iterator it = personaje.iterator();
		while(it.hasNext()){
			//Fatallity para mostrar en el modelo
			String[] personin = it.next().toString().replace("[", "").replace("]", "").split(",");
			modelo.addRow(personin);
		}
	
	}
}
