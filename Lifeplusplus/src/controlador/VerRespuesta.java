package controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.Developer;
import application.Medico;
import application.Paciente;
import application.Solicitud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import servicio.ServicioAyuda;
import servicio.ServicioDevelopers;
import servicio.ServicioLogin;
import servicio.ServicioMedicos;
import servicio.ServicioPacientes;

/*
 * CONTROLADOR PANTALLA VER RESPUESTA DE LA SOLICITUD DE AYUDA
 */
public class VerRespuesta {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	JFXTextArea textAreaDescripcion;
	@FXML
	Label numDni;
	@FXML
	private JFXButton enviarAyudaBtn, btnRegresar;
	@FXML
	TextArea textAreaSolucion;
	@FXML
	private Label txterror;

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

	Solicitud elegido = null;
	private int posElegido;

	/*
	 * Setters
	 */
	public void setElegido(Solicitud nuevoelegido) {
		elegido = nuevoelegido;
	}

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

	public JFXButton getBotonEnviar() {
		return enviarAyudaBtn;
	}

	@FXML
	void ConfirmarEnvioAyuda(ActionEvent event) {
		// no hace nada ya que cancelamos su uso desde un principio
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

	@FXML
	void initialize() {

	}
}
