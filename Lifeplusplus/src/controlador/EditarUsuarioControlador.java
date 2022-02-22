package controlador;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Developer;
import application.Medico;
import application.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import servicio.ServicioDevelopers;
import servicio.ServicioMedicos;
import servicio.ServicioPacientes;

/*
 * CONTROLADOR PANTALLA EDITAR USUARIO
 */

public class EditarUsuarioControlador implements Initializable {
	@FXML
	private JFXTextField txtNombre, txtDni, txtCorreo, txtInstitucion, txtIdentificacion, txtRol;
	@FXML
	private JFXButton btnFecha, btnInstitucion, btnIdentificacion, btnRol, btnRegresar, btnDarBaja, btnAceptarSolicitud,
			btnRechazar;
	@FXML
	private Label tituloFecha, tituloDni, tituloInstitucion, tituloIdentificacion, tituloRol, feedback;
	@FXML
	private DatePicker cuadroFecha;
	@FXML
	private JFXPasswordField fieldContrasena;

	/*
	 * Variables para instanciar objetos especificos
	 */
	private Medico medicoRegreso = null;
	private Paciente pacienteRegreso = null;
	private Developer devRegreso = null;
	private Medico medicoElegido = null;
	private Paciente pacienteElegido = null;
	private Developer devElegido = null;

	/*
	 * Instanciar los servicios
	 */
	private ServicioMedicos sm = ServicioMedicos.getInstance();
	private ServicioPacientes sp = ServicioPacientes.getInstance();
	private ServicioDevelopers sd = ServicioDevelopers.getInstance();

	int posElegido = 0;
	String txtCheck;

	/*
	 * Metodo para obtener una fecha en formato local
	 * 
	 * @param oldDate la fecha en formato Date
	 * 
	 * @return LocalDate la fecha en formato LocalDate
	 */
	public LocalDate DateToLocal(Date oldDate) {
		SimpleDateFormat formatear1 = new SimpleDateFormat("MM-dd-yyy");
		String fecha = formatear1.format(oldDate);
		String[] nums = fecha.split("-");
		LocalDate nuevaLocal = LocalDate.of(Integer.parseInt(nums[2]), Integer.parseInt(nums[0]),
				Integer.parseInt(nums[1]));
		return nuevaLocal;
	}

	/*
	 * Metodo para obtener una fecha en formato Date
	 * 
	 * @param oldLocal la fecha en formato LocalDate
	 * 
	 * @return Date la fecha en formato Date
	 */
	public Date LocalToDate(LocalDate oldLocal) {
		Date nuevaDate = Date.from(oldLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return nuevaDate;
	}

	/*
	 * Metodo para borrar una instancia especifica y persistirlo en el fichero
	 * 
	 * @exception IOException
	 */
	public void DarBaja() throws IOException {
		txtCheck = btnDarBaja.getText();

		if (txtCheck.equals("Dar de baja")) {
			btnDarBaja.setText("Confirmar dar de baja");
		} else {
			if (pacienteElegido == null) {
				sm.getMedicos().remove(posElegido);
				sm.saveMedicos();
			} else {
				sp.getPacientes().remove(posElegido);
				sp.savePacientes();
			}
			Regresar(new ActionEvent());
		}
	}

	/*
	 * Metodo para aceptar una solicitud y persistirla en el fichero
	 * 
	 * @exception IOException
	 */
	public void AceptarSolicitud(ActionEvent event) throws IOException {
		sm.getSolicitudes().remove(posElegido);
		sm.saveSolicitudes();

		Medico nuevoMedico = medicoElegido;
		nuevoMedico.setNombre(txtNombre.getText());
		nuevoMedico.setDni(txtDni.getText());
		nuevoMedico.setCorreo(txtCorreo.getText());
		nuevoMedico.setInstitucion(txtInstitucion.getText());
		nuevoMedico.setIdentificacionInstitucion(Integer.parseInt(txtIdentificacion.getText()));
		nuevoMedico.setRol(txtRol.getText());
		nuevoMedico.setPassword(fieldContrasena.getText());
		nuevoMedico.setNacimiento(LocalToDate(cuadroFecha.getValue()));

		sm.getMedicos().add(nuevoMedico);
		sm.saveMedicos();

		Regresar(new ActionEvent());
	}

	/*
	 * Metodo para rechazar una solicitud y borrarlo del fichero
	 * 
	 * @exception IOException
	 */
	public void RechazarSolicitud(ActionEvent event) throws IOException {
		txtCheck = btnRechazar.getText();

		if (txtCheck.equals("Rechazar")) {
			btnRechazar.setText("Confirmar rechazar");
		} else {
			sm.getSolicitudes().remove(posElegido);
			sm.saveSolicitudes();

			Regresar(new ActionEvent());
		}
	}

	/*
	 * Metodo para regresar a la pantalla anterior
	 * 
	 * @exception IOException
	 */
	public void Regresar(ActionEvent event) throws IOException {
		if (devRegreso != null) {
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
				Stage s_login = (Stage) btnRol.getScene().getWindow();
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
				ppalMedicoControlador.setYo(medicoRegreso);
				loader.setController(ppalMedicoControlador);
				Parent rootLogin = loader.load();
				Stage stage = new Stage();
				stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
				stage.setTitle("Life++");
				stage.setScene(new Scene(rootLogin));
				stage.setMinHeight(600);
				stage.setMinWidth(600);
				Stage s_login = (Stage) btnRol.getScene().getWindow();
				stage.setHeight(s_login.getHeight());
				stage.setWidth(s_login.getWidth());
				stage.setX(s_login.getX());
				stage.setY(s_login.getY());
				stage.show();
				s_login.hide();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (pacienteRegreso != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PpalPaciente.fxml"));
				PpalPacienteControlador ppalPacienteControlador = new PpalPacienteControlador();
				ppalPacienteControlador.setYo(sp.getPaciente(pacienteRegreso.getDni(), pacienteRegreso.getPassword()));
				loader.setController(ppalPacienteControlador);
				Parent rootLogin = loader.load();
				Stage stage = new Stage();
				stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
				stage.setTitle("Life++");
				stage.setScene(new Scene(rootLogin));
				stage.setMinHeight(600);
				stage.setMinWidth(600);
				Stage s_login = (Stage) btnRol.getScene().getWindow();
				stage.setHeight(s_login.getHeight());
				stage.setWidth(s_login.getWidth());
				stage.setX(s_login.getX());
				stage.setY(s_login.getY());
				stage.show();
				s_login.hide();
				;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Metodo para seleccionar el medico que se edita
	 * 
	 * @param elegido Medico, el medico que se va a editar
	 */
	public void setMedicoEditable(Medico elegido) {
		medicoElegido = elegido;

		txtNombre.setText(medicoElegido.getNombre());
		txtDni.setText(medicoElegido.getDni());
		txtCorreo.setText(medicoElegido.getCorreo());
		fieldContrasena.setText(medicoElegido.getPassword());
		txtInstitucion.setText(medicoElegido.getInstitucion());
		txtIdentificacion.setText(String.valueOf(medicoElegido.getIdentificacionInstitucion()));
		txtRol.setText(medicoElegido.getRol());
		LocalDate localDateFecha = DateToLocal(medicoElegido.getNacimiento());
		cuadroFecha.setValue(localDateFecha);
	}

	/*
	 * Metodo para seleccionar el paciente que se edita
	 * 
	 * @param elegido Paciente, el paciente que se va a editar
	 */
	public void setPacienteEditable(Paciente elegido) {
		pacienteElegido = elegido;

		txtNombre.setText(pacienteElegido.getNombre());
		txtDni.setText(pacienteElegido.getDni());
		txtCorreo.setText(pacienteElegido.getCorreo());
		fieldContrasena.setText(pacienteElegido.getPassword());
		LocalDate localDateFecha = DateToLocal(pacienteElegido.getNacimiento());
		cuadroFecha.setValue(localDateFecha);
		txtInstitucion.setVisible(false);
		txtIdentificacion.setVisible(false);
		txtRol.setVisible(false);
		btnInstitucion.setVisible(false);
		btnIdentificacion.setVisible(false);
		btnRol.setVisible(false);
		tituloInstitucion.setVisible(false);
		tituloIdentificacion.setVisible(false);
		tituloRol.setVisible(false);
	}

	/*
	 * Metodo para seleccionar el developer que se va a editar
	 * 
	 * @param personal Developer
	 */
	public void setDatosPersonales(Developer personal) {
		devElegido = personal;

		txtNombre.setText(devElegido.getNombre());
		txtDni.setText(devElegido.getDni());
		txtCorreo.setText(devElegido.getCorreo());
		fieldContrasena.setText(devElegido.getPassword());
		tituloFecha.setVisible(false);
		cuadroFecha.setVisible(false);
		btnFecha.setVisible(false);
		txtInstitucion.setVisible(false);
		txtIdentificacion.setVisible(false);
		txtRol.setVisible(false);
		btnInstitucion.setVisible(false);
		btnIdentificacion.setVisible(false);
		btnRol.setVisible(false);
		tituloInstitucion.setVisible(false);
		tituloIdentificacion.setVisible(false);
		tituloRol.setVisible(false);
		btnDarBaja.setVisible(false);
	}

	/*
	 * Metodo para actualizar datos de la persona que se edite
	 */
	public void Actualizar(ActionEvent event) {
		if (medicoElegido != null) {
			Medico nuevoMedico = medicoElegido;
			nuevoMedico.setNombre(txtNombre.getText());
			nuevoMedico.setDni(txtDni.getText());
			nuevoMedico.setCorreo(txtCorreo.getText());
			nuevoMedico.setInstitucion(txtInstitucion.getText());
			nuevoMedico.setIdentificacionInstitucion(Integer.parseInt(txtIdentificacion.getText()));
			nuevoMedico.setRol(txtRol.getText());
			nuevoMedico.setPassword(fieldContrasena.getText());
			nuevoMedico.setNacimiento(LocalToDate(cuadroFecha.getValue()));

			sm.getMedicos().remove(posElegido);
			sm.getMedicos().add(nuevoMedico);
			posElegido = sm.getMedicos().size() - 1;
			sm.saveMedicos();

		} else if (pacienteElegido != null) {
			Paciente nuevoPaciente = pacienteElegido;
			nuevoPaciente.setNombre(txtNombre.getText());
			nuevoPaciente.setDni(txtDni.getText());
			nuevoPaciente.setCorreo(txtCorreo.getText());
			nuevoPaciente.setPassword(fieldContrasena.getText());
			nuevoPaciente.setNacimiento(LocalToDate(cuadroFecha.getValue()));

			sp.getPacientes().remove(posElegido);
			sp.getPacientes().add(nuevoPaciente);
			posElegido = sp.getPacientes().size() - 1;
			sp.savePacientes();

		} else {
			Developer nuevoDeveloper = devElegido;
			devElegido.setNombre(txtNombre.getText());
			devElegido.setDni(txtDni.getText());
			devElegido.setCorreo(txtCorreo.getText());
			devElegido.setPassword(fieldContrasena.getText());

			sd.getDevelopers().remove(posElegido);
			sd.getDevelopers().add(nuevoDeveloper);
			posElegido = sd.getDevelopers().size() - 1;
			sd.saveDevelopers();
		}

		feedback.setText("Datos actualizados correctamente");
		feedback.setVisible(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		feedback.setVisible(false);
		txtCheck = btnDarBaja.getText();
		btnAceptarSolicitud.setVisible(false);
		btnRechazar.setVisible(false);
	}

	/*
	 * Getters
	 */
	public JFXButton getBtnDarBaja() {
		return btnDarBaja;
	}

	public JFXButton getBtnAceptarSolicitud() {
		return btnAceptarSolicitud;
	}

	public JFXButton getBtnRechazarSolicitud() {
		return btnRechazar;
	}

	/*
	 * Setters
	 */

	public void setMedicoRegreso(Medico nuevoMedicoRegreso) {
		medicoRegreso = nuevoMedicoRegreso;
	}

	public void setPacienteRegreso(Paciente nuevoPacienteRegreso) {
		pacienteRegreso = nuevoPacienteRegreso;
	}

	public void setElegido(int nuevaPos) {
		posElegido = nuevaPos;
	}

	public void setDevRegreso(Developer nuevoDevRegreso) {
		devRegreso = nuevoDevRegreso;
	}

	public void OcultarBaja() {
		btnDarBaja.setVisible(false);
	}

}