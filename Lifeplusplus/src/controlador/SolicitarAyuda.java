package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.Developer;
import application.Medico;
import application.Paciente;
import application.Persona;
import application.Solicitud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import servicio.ServicioAyuda;
import servicio.ServicioDevelopers;
import servicio.ServicioLogin;
import servicio.ServicioMedicos;
import servicio.ServicioPacientes;

/*
 * CONTROLADOR PARA PANTALLA DE SOLICITAR AYUDA
 */
public class SolicitarAyuda {

	@FXML
	Label numDni;
	@FXML
	TextArea textAreaSolucion;
	@FXML
	private Label txterror;
	@FXML
	private JFXButton btnRegresar;
	@FXML
	private JFXTextField textFieldDni;
	@FXML
	private JFXTextArea textAreaDescripcion;

	/*
	 * Variables para instanciar objetos especificos
	 */
	private Medico medicoRegreso = null;
	private Paciente pacienteRegreso = null;
	private Developer devRegreso = null;

	private Medico medico;
	private Paciente paciente;
	private Solicitud Yo;

	// Instanciar servicios
	private ServicioMedicos sm = ServicioMedicos.getInstance();
	private ServicioPacientes sp = ServicioPacientes.getInstance();
	private ServicioDevelopers sd = ServicioDevelopers.getInstance();
	private ServicioAyuda sa = ServicioAyuda.getInstance();
	private ServicioLogin sl = ServicioLogin.getInstance();

	private int posElegido;
	private String dni_;

	/*
	 * Getters
	 */
	public TextArea getSolucionArea() {
		return textAreaSolucion;
	}

	/*
	 * Setters
	 */
	public void setPacienteRegreso(Paciente nuevoPacienteRegreso) {
		pacienteRegreso = nuevoPacienteRegreso;
	}

	public void setMedicoRegreso(Medico nuevoMedicoRegreso) {
		medicoRegreso = nuevoMedicoRegreso;
	}

	public void setDevRegreso(Developer nuevoDevRegreso) {
		devRegreso = nuevoDevRegreso;
	}

	public void setElegido(int nuevaPos) {
		posElegido = nuevaPos;
	}

	public void setYo(Solicitud nuevoYo) {
		Yo = nuevoYo;
	}

	/*
	 * Metodo para regresar a la pantalla anterior
	 * 
	 * @exception IOException
	 */
	@FXML
	public void Regresar(ActionEvent event) throws IOException {
		if (pacienteRegreso != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PpalPaciente.fxml"));
				PpalPacienteControlador ppalPacienteControlador = new PpalPacienteControlador();
				loader.setController(ppalPacienteControlador);
				Parent rootLogin = loader.load();
				Stage stage = new Stage();
				stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
				stage.setTitle("Life++");
				stage.setScene(new Scene(rootLogin));
				ppalPacienteControlador.setYo(pacienteRegreso);
				stage.setMinHeight(600);
				stage.setMinWidth(600);
				Stage s_login = (Stage) btnRegresar.getScene().getWindow();
				stage.setHeight(s_login.getHeight());
				stage.setWidth(s_login.getWidth());
				stage.setX(s_login.getX());
				stage.setY(s_login.getY());
				stage.show();
				s_login.hide();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (medicoRegreso != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PpalMedico.fxml"));
				PpalMedicoControlador ppalMedicoControlador = new PpalMedicoControlador();
				loader.setController(ppalMedicoControlador);
				Parent rootLogin = loader.load();
				Stage stage = new Stage();
				stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
				stage.setTitle("Life++");
				stage.setScene(new Scene(rootLogin));
				ppalMedicoControlador.setYo(medicoRegreso);
				stage.setMinHeight(600);
				stage.setMinWidth(600);
				Stage s_login = (Stage) btnRegresar.getScene().getWindow();
				stage.setHeight(s_login.getHeight());
				stage.setWidth(s_login.getWidth());
				stage.setX(s_login.getX());
				stage.setY(s_login.getY());
				stage.show();
				s_login.hide();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (devRegreso != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PpalDeveloper.fxml"));
				PpalDeveloperControlador ppalDeveloperControlador = new PpalDeveloperControlador();
				ppalDeveloperControlador.setYo(devRegreso);
				loader.setController(ppalDeveloperControlador);
				Parent rootLogin = loader.load();
				Stage stage = new Stage();
				stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
				stage.setTitle("Life++");
				stage.setScene(new Scene(rootLogin));
				stage.setMinHeight(600);
				stage.setMinWidth(600);
				Stage s_login = (Stage) btnRegresar.getScene().getWindow();
				stage.setHeight(s_login.getHeight());
				stage.setWidth(s_login.getWidth());
				stage.setX(s_login.getX());
				stage.setY(s_login.getY());
				stage.show();
				s_login.hide();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Metodo para mostrar datos de la solicitud
	 */
	@FXML
	public void mostrarDatos() {
		if (devRegreso != null) {
			numDni.setText(Yo.getDni());
			textAreaDescripcion.setText(Yo.getDescripcion());
			textAreaSolucion.setText(Yo.getSolucion());
		}
	}

	@FXML
	void initialize() {
		mostrarDatos();
	}

	/*
	 * Metodo para confirmar el envio de la solicitud
	 * 
	 * @exception ParseException
	 */
	@FXML
	public void ConfirmarEnvioAyuda(ActionEvent event) throws ParseException {
		String Descripcion = textAreaDescripcion.getText();
		String Solucion = textAreaSolucion.getText();
		Boolean Medico = true;
		String dni = sl.getLogedUser().getDni();
		String nombre = sl.getLogedUser().getNombre();
		String password = sl.getLogedUser().getPassword();
		String correo = sl.getLogedUser().getCorreo();

		if (Descripcion.isEmpty()) {
			txterror.setText("El campo de nombre está vacío");
		} else {

			if (pacienteRegreso != null) { // NO ES MEDICO POR TANTO:
				Medico = false;
				Solicitud s = new Solicitud(dni, nombre, password, correo, Descripcion, Solucion, Medico);
				sa.editarRespuesta(numDni.getText(), textAreaSolucion.getText());
				sa.addSolicitudes(s);
				sa.saveSolicitudes();
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PpalPaciente.fxml"));
					PpalPacienteControlador ppalPacienteControlador = new PpalPacienteControlador();
					// ppalPacienteControlador.setYo(sp.getPaciente(pacienteElegido.getDni(),
					// pacienteElegido.getPassword()));
					loader.setController(ppalPacienteControlador);
					Parent rootLogin = loader.load();
					Stage stage = new Stage();
					stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
					stage.setTitle("Life++");
					stage.setScene(new Scene(rootLogin));
					stage.setMinHeight(600);
					stage.setMinWidth(600);
					Stage s_login = (Stage) btnRegresar.getScene().getWindow();
					stage.setHeight(s_login.getHeight());
					stage.setWidth(s_login.getWidth());
					stage.setX(s_login.getX());
					stage.setY(s_login.getY());
					stage.show();
					s_login.hide();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (devRegreso != null) // SOY EL DEVELOPER
			{
				Medico = false;

				sa.editarRespuesta(Yo.getDni(), textAreaSolucion.getText());
				sa.saveSolicitudes();
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PpalDeveloper.fxml"));
					PpalDeveloperControlador ppalDeveloperControlador = new PpalDeveloperControlador();
					ppalDeveloperControlador.setYo(devRegreso);
					loader.setController(ppalDeveloperControlador);
					Parent rootLogin = loader.load();
					Stage stage = new Stage();
					stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
					stage.setTitle("Life++");
					stage.setScene(new Scene(rootLogin));
					stage.setMinHeight(600);
					stage.setMinWidth(600);
					Stage s_login = (Stage) btnRegresar.getScene().getWindow();
					stage.setHeight(s_login.getHeight());
					stage.setWidth(s_login.getWidth());
					stage.setX(s_login.getX());
					stage.setY(s_login.getY());
					stage.show();
					s_login.hide();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else { // SI ES MEDICO!
				Solicitud s = new Solicitud(dni, nombre, password, correo, Descripcion, Solucion, Medico);
				sa.editarRespuesta(numDni.getText(), textAreaSolucion.getText());
				sa.addSolicitudes(s);
				sa.saveSolicitudes();
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PpalMedico.fxml"));
					PpalMedicoControlador ppalMedicoControlador = new PpalMedicoControlador();
					loader.setController(ppalMedicoControlador);
					Parent rootLogin = loader.load();
					Stage stage = new Stage();
					stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
					stage.setTitle("Life++");
					stage.setScene(new Scene(rootLogin));
					ppalMedicoControlador.setYo(medicoRegreso);
					stage.setMinHeight(600);
					stage.setMinWidth(600);
					Stage s_login = (Stage) btnRegresar.getScene().getWindow();
					stage.setHeight(s_login.getHeight());
					stage.setWidth(s_login.getWidth());
					stage.setX(s_login.getX());
					stage.setY(s_login.getY());
					stage.show();
					s_login.hide();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}