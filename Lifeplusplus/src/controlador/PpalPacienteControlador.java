package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import com.jfoenix.controls.JFXButton;

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
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import servicio.ServicioAyuda;
import servicio.ServicioLogin;
import servicio.ServicioPacientes;

/*
 * CONTROLADOR PANTALLA PRINCIPAL PACIENTE
 */
public class PpalPacienteControlador {

	@FXML
	private MenuButton BotonMenu;
	@FXML
	private JFXButton BtnCerrarSesion, ModificarPerfil;
	@FXML
	private JFXButton certificadoCovidBtn, PedirAyudaBtn, RespuestaAyuda, PedirAyuda;
	@FXML
	private ResourceBundle resources;
	@FXML
	private Label txtNombre, txtCorreo, txtDni, txtFechaNacimiento;
	@FXML
	private URL location;
	@FXML
	protected GoogleMapView mapView;

	/*
	 * Instanciar servicios
	 */
	private ServicioPacientes sp = ServicioPacientes.getInstance();
	private ServicioLogin sl = ServicioLogin.getInstance();
	private ServicioAyuda sa = ServicioAyuda.getInstance();

	private Paciente yo;
	private GoogleMap map;

	private DecimalFormat formatter = new DecimalFormat("###.00000");

	/*
	 * Metodo para mostrar datos del paciente al entrar a la pantalla principal
	 */
	@FXML
	public void mostrarDatos() {
		Persona p = sl.getLogedUser();
		String correoPaciente = sl.getLogedUser().getCorreo();
		txtCorreo.setText(correoPaciente);
		String nombrePaciente = sl.getLogedUser().getNombre();
		txtNombre.setText(nombrePaciente);
		String dniPaciente = sl.getLogedUser().getDni();
		txtDni.setText(dniPaciente);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String fecha = sdf.format(((Paciente) p).getNacimiento());
		txtFechaNacimiento.setText(fecha);
	}

	/*
	 * Metodo para editar datos como paciente
	 * 
	 * @exception IOException
	 */
	public void EditarUsuario(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarUsuario.fxml"));
			EditarUsuarioControlador editarUsuarioControlador = new EditarUsuarioControlador();
			loader.setController(editarUsuarioControlador);
			Parent rootLogin = loader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
			stage.setTitle("Life++");
			stage.setScene(new Scene(rootLogin));
			editarUsuarioControlador.setPacienteEditable(yo);
			editarUsuarioControlador.setPacienteRegreso(yo);
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) BtnCerrarSesion.getScene().getWindow();
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

	/*
	 * Metodo para solicitar ayuda como paciente
	 * 
	 * @exception IOException
	 */
	public void PedirAyuda(ActionEvent event) throws IOException {
		try {
			String dni_ = yo.getDni();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/SolicitarAyudaPaciente.fxml"));
			SolicitarAyuda solicitarAyuda = new SolicitarAyuda();
			loader.setController(solicitarAyuda);
			Parent rootLogin = loader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
			stage.setTitle("Life++");
			stage.setScene(new Scene(rootLogin));
			solicitarAyuda.getSolucionArea().setVisible(false);
			solicitarAyuda.numDni.setText(dni_);
			solicitarAyuda.setPacienteRegreso(yo);
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) BtnCerrarSesion.getScene().getWindow();
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

	/*
	 * Metodo para cerrar sesion y volver a pantalla de login
	 * 
	 * @exception IOException
	 */
	@FXML
	public void CerrarSesion(ActionEvent event) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Login.fxml"));
			LoginControlador controlLogin = new LoginControlador();
			loader.setController(controlLogin);
			Parent rootLogin = loader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
			stage.setTitle("Life++");
			stage.setScene(new Scene(rootLogin));
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) BtnCerrarSesion.getScene().getWindow();
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

	/*
	 * Metodo para subir el certificado covid como pdf
	 */
	@FXML
	public void subirCertificado(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf"));
			File f = fc.showOpenDialog(null);
			String origen = f.getAbsolutePath();
			String destino = System.getProperty("user.dir") + "//certificados//" + sl.getLogedUser().getDni() + ".pdf";
			copyFile(origen, destino);
		} catch (Exception e) {
			System.out.println("No se ha podido cargar el archivo");
		}
	}

	/*
	 * Metodo para descargar el historial medico
	 * 
	 * @exception IOException
	 */
	@FXML
	public void Descargar(ActionEvent event) throws IOException {

		try {

			File path = new File(
					System.getProperty("user.dir") + "//certificados//" + sl.getLogedUser().getDni() + ".pdf");// Indicar
																												// la
																												// ruta
																												// correspondiente(relativa)
			Desktop.getDesktop().open(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Metodo para escribir lo que hay en el fichero de origen en el de destino a
	 * traves de la ruta
	 * 
	 * @param fromFile
	 * 
	 * @param toFile
	 * 
	 * @return boolean
	 */
	private boolean copyFile(String fromFile, String toFile) {
		File origin = new File(fromFile);
		File destination = new File(toFile);
		if (origin.exists()) {
			try {
				InputStream in = new FileInputStream(origin);
				OutputStream out = new FileOutputStream(destination);
				// We use a buffer for the copy (Usamos un buffer para la copia).
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				return true;
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	@FXML
	void initialize() {
		mapView.addMapInitializedListener(() -> configureMap());
		mostrarDatos();
	}

	public void setYo(Paciente nuevoYo) {
		yo = nuevoYo;
	}

	/*
	 * Configuracion mapa
	 */
	protected void configureMap() {
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(40.373298, -3.920070)).mapType(MapTypeIdEnum.ROADMAP).zoom(15);
		map = mapView.createMap(mapOptions, false);

		map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
			LatLong latLong = event.getLatLong();
		});
	}

	/*
	 * Metodo para ver respuesta de solicitud del developer
	 */
	@FXML
	void verRespuesta(ActionEvent event) {
		try {
			String dni_ = yo.getDni();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/SolicitarAyudaPaciente.fxml"));
			VerRespuesta verrespuesta = new VerRespuesta();
			loader.setController(verrespuesta);
			Parent rootLogin = loader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
			stage.setTitle("Life++");
			stage.setScene(new Scene(rootLogin));
			verrespuesta.getBotonEnviar().setVisible(false);
			Solicitud ayudaelegida = sa.getAyuda(dni_);
			verrespuesta.setYo(ayudaelegida);

			verrespuesta.textAreaDescripcion.setText(ayudaelegida.getDescripcion());
			verrespuesta.textAreaSolucion.setText(ayudaelegida.getSolucion());
			verrespuesta.numDni.setText(ayudaelegida.getDni());

			verrespuesta.setPacienteRegreso(yo);
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) BtnCerrarSesion.getScene().getWindow();
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