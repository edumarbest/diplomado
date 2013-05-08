package usa.edu.py;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import py.edu.ucsa.connections.ManejadorBD;

public class FrmAlumnos extends JFrame {
	
	private JPanel pnlPrincipal;
	private JPanel contentPane;
	private JPanel pnlTexto;
	private JPanel pnlPie;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtPosicion;
	private JLabel lblCedula;
	private JLabel lblNombre;
	private JLabel lblDireccion;
	private JButton btnInsertar;
	private JButton btnAdelante;
	private JButton btnAtras;
	private JPanel pnlAccion;
	private ManejadorBD manDB = new ManejadorBD();
	private ResultSet res;
	private Statement stm;
	
	//main
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						UIManager.setLookAndFeel(
	                            UIManager.getSystemLookAndFeelClassName());
						FrmAlumnos frame = new FrmAlumnos();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}//fin de main
		
		enum Desplazar{
			ADELANTE, ATRAS;
		}
	
	public FrmAlumnos(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pnlPrincipal = new JPanel(new GridLayout(3, 1));
		pnlTexto = new JPanel(new GridLayout(3,2));
		pnlPie = new JPanel(new FlowLayout());
		
		txtCedula = new JTextField();
		txtCedula.setPreferredSize(new Dimension(200,30));
		lblCedula = new JLabel();
		lblCedula.setText("Cédula");
		
		txtNombre = new JTextField();
		txtNombre.setPreferredSize(new Dimension(200,30));
		lblNombre = new JLabel();
		lblNombre.setText("Nombre");
		
		txtDireccion = new JTextField();
		txtDireccion.setPreferredSize(new Dimension(200,30));
		lblDireccion = new JLabel();
		lblDireccion.setText("Dirección");
		
		txtPosicion = new JTextField();
		txtPosicion.setPreferredSize(new Dimension(30,30));
		
		btnAdelante = new JButton();
		btnAdelante.setText("Adelante");
		btnAdelante.setPreferredSize(new Dimension(80, 30));
		
		btnAtras = new JButton();
		btnAtras.setText("Atras");
		btnAtras.setPreferredSize(new Dimension(80, 30));
		
		btnInsertar = new JButton();
		btnInsertar.setText("Insertar");
		btnInsertar.setPreferredSize(new Dimension(80, 30));
		
		pnlAccion = new JPanel(new FlowLayout());
		pnlAccion.add(btnAtras);
		pnlAccion.add(btnAdelante);
		
		pnlTexto.add(lblCedula);
		pnlTexto.add(txtCedula);
		pnlTexto.add(lblNombre);
		pnlTexto.add(txtNombre);
		pnlTexto.add(lblDireccion);
		pnlTexto.add(txtDireccion);
		
		pnlPie.add(txtPosicion);
		pnlPie.add(btnInsertar);
		
		pnlPrincipal.add(pnlTexto);
		pnlPrincipal.add(pnlAccion);
		pnlPrincipal.add(pnlPie);
		
		contentPane.add(pnlPrincipal);
		
		String sql = "SELECT cedula, nombre, direccion FROM alumnos";
		try {
		stm = ManejadorBD.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_READ_ONLY);
			res = stm.executeQuery(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		btnAdelante.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					mostrarDatos(Desplazar.ADELANTE);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarDatos(Desplazar.ATRAS);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
	}
	
	private void mostrarDatos(Desplazar desplazar) throws SQLException{
		switch (desplazar) {
		case ADELANTE:
			if (res.next()) {
				mostrarText();
			}
			break;
		case ATRAS:
			if (res.previous()) {
				mostrarText();
			}
			break;
		}
		
	}
	
	private void mostrarText() throws SQLException{
		txtCedula.setText(res.getString("cedula"));
		txtNombre.setText(res.getString("nombre"));
		txtDireccion.setText(res.getString("direccion"));
		txtPosicion.setText(res.getRow()+"");
	}

}
