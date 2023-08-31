package Controles;

import Vista.Iniciar_sesion;
import Vista.MenuPrinAdmin;
import Vista.MenuPrinBarbero;
import Vista.MenuPrinCliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author deiby
 */
public class ManejadorBaseDatos {

    private String base_datos;
    private String servidor;
    private String login;
    private String clave;

    Statement sentencia; //ejecutar la sentencias
    ResultSet validacion;
    private ResultSet consulta; // lo deja como matriz para consultar fila por fila

    private Connection conexion;
    private String txt_usuario;

    public ManejadorBaseDatos() {
        base_datos = "bd_barber";
        servidor = "127.0.0.1";
        login = "root";
        clave = "";

    }

    //Metodos importantes para llamar a en la clase principaj
    public void conectar() {
        try {
            //conexion con el driver
            Class.forName("com.mysql.jdbc.Driver");

            //conexion a la base de datos
            conexion = DriverManager.getConnection("jdbc:mysql://" + servidor + "/" + base_datos, login, clave);
            System.out.println("CONEXION EXITOSA");

        } catch (ClassNotFoundException ex) {
            System.out.println("pailas no se encontro el driver");
        } catch (SQLException ex) {
            System.out.println("no se encontro la base de datos ");
        }

    }

    public void CrearUsuario(String sql) {

        try {

            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente");

        } catch (Exception e) {
            System.out.println("error en el metodo CrearUsuario sentencia" + e.getMessage());
        }
    }

    public void ValidarUsuario(JComboBox combo_TipoUsuarios, String sql) {
        int resultado = 0;

        try {

            conectar();
            sentencia = conexion.createStatement();
            ResultSet validacion = sentencia.executeQuery(sql);

            if (validacion.next()) {
                resultado = 1;

                if (resultado == 1) {
                    JOptionPane.showMessageDialog(null, "INICIO DE SESION CON EXITO");

                    if (combo_TipoUsuarios.getSelectedItem().equals("Administrador")) {
                        MenuPrinAdmin win2 = new MenuPrinAdmin();
                        win2.setVisible(true);

                    } else {
                        if (combo_TipoUsuarios.getSelectedItem().equals("Cliente")) {
                            MenuPrinCliente win2 = new MenuPrinCliente();
                            win2.setVisible(true);

                        } else {
                            if (combo_TipoUsuarios.getSelectedItem().equals("Barbero")) {
                                MenuPrinBarbero win2 = new MenuPrinBarbero();
                                win2.setVisible(true);

                            }

                        }

                    }

                }

            } else {
                JOptionPane.showMessageDialog(null, "error de acceso, Usuario NO registrado");

            }

        } catch (Exception e) {
            System.out.println("error en el metodo ValidarUsuario sentencia" + e.getMessage());
        }
    }

    public void eliminar(String sql) {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "ELIMINACION DE USUARIO EXITOSA");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia DELETE" + sql);
        }
    }

    public void modificar(String sql) {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "EL USUARIO HA SIDO MODIFICADO");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia UPDATE" + sql);
        }
    }

    public void consultar(DefaultTableModel modelo) 
    {
        try {
            conectar();
            sentencia = conexion.createStatement();
            consulta = sentencia.executeQuery("select * from ");
            String[] registro = new String[3];

            while (consulta.next()) {
                registro[0] = consulta.getString("id");
                registro[1] = consulta.getString("usuario");
                registro[2] = consulta.getString("contraseña");

                modelo.addRow(registro);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "LA CONSULTA ESTA PAILAS");
        }
    }

    public void desconectar() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("CONEXIO CERRADA");
        }
    }

    public void CrearServicio(String sql) 
    {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "SERVICIO CREADO EXITOSAMENTE");

        } catch (Exception e) {
            System.out.println("error en el metodo CrearServicio sentencia" + e.getMessage());

        }
    }

    public void consultarServicio(DefaultTableModel modelo) 
    {
        try {
            conectar();
            sentencia = conexion.createStatement();
            consulta = sentencia.executeQuery("select * from servicios");
            String[] registro = new String[3];

            while (consulta.next()) {
                registro[0] = consulta.getString("id_servicio");
                registro[1] = consulta.getString("servicio");
                registro[2] = consulta.getString("valor");

                modelo.addRow(registro);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "LA CONSULTA ESTA PAILAS");
        }
    }

    public void eliminarServicio(String sql) 
    {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "ELIMINACION DE SERVICIO EXITOSA");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia DELETE" + sql);
        }
    }

    public void modificarServicio(String sql) 
    {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "EL SERVICIO HA SIDO MODIFICADO");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia UPDATE" + sql);
        }
    }

    public void CrearCita(String sql) 
    {

        try {

            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Cita agregada exitosamente");

        } catch (Exception e) {
            System.out.println("error en el metodo CrearCita sentencia" + e.getMessage());
        }
    }

    public void consultarCita(DefaultTableModel modelo) 
    {
        try {
            conectar();
            sentencia = conexion.createStatement();
            consulta = sentencia.executeQuery("select * from citas");
            String[] registro = new String[4];

            while (consulta.next()) {
                registro[0] = consulta.getString("id_citas");
                registro[1] = consulta.getString("nombre");
                registro[2] = consulta.getString("dia");
                registro[3] = consulta.getString("hora");

                modelo.addRow(registro);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "LA CONSULTA ESTA PAILAS");
        }
    }

    public void modificarCita(String sql) 
    {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "LA CITA HA SIDO MODIFICADO");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia UPDATE" + sql);
        }
    }

    public void eliminarCita(String sql) 
    {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "ELIMINACION DE CITA EXITOSA");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia DELETE" + sql);
        }
    }
    
    public void CrearEmergencia(String sql) {

        try {

            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);

            JOptionPane.showMessageDialog(null, "Emergencia agregada exitosamente");

        } catch (Exception e) {
            System.out.println("error en el metodo CrearEmergencia sentencia" + e.getMessage());
        }
    }
    public void modificarEmergencia(String sql) 
    {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "LA Emergencia HA SIDO MODIFICADO");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia UPDATE" + sql);
        }
    }
    
    public void consultarEmergencia(DefaultTableModel modelo) 
    {
        try {
            conectar();
            sentencia = conexion.createStatement();
            consulta = sentencia.executeQuery("select * from emergencia");
            String[] registro = new String[4];

            while (consulta.next()) {
                registro[0] = consulta.getString("id_persona");
                registro[1] = consulta.getString("nombre");
                registro[2] = consulta.getString("numero");
                registro[3] = consulta.getString("correo");

                modelo.addRow(registro);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "LA CONSULTA ESTA PAILAS");
        }
    }
    public void eliminarEmergencia(String sql) 
    {
        try {
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(null, "ELIMINACION DE Emergencia EXITOSA");

        } catch (SQLException ex) {
            System.out.println("error en la sentencia DELETE" + sql);
        }
    }


}

