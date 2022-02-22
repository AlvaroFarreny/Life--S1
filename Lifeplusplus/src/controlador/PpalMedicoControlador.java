package controlador;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.ResourceBundle;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import application.Medico;
import application.Paciente;
import application.Solicitud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import servicio.ServicioAyuda;
import servicio.ServicioLogin;
import servicio.ServicioPacientes;

/*
 * CONTROLADOR PANTALLA PRINCIPAL MEDICO
 */
public class PpalMedicoControlador implements Initializable {

	@FXML
	private JFXButton BtnCerrarSesion_, ModificarPerfil, PedirAyuda, BtnDescargar, EditarPerfil, RespuestaAyuda,
			DescargadeHistorial;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Label labelP0, labelP1, labelP2, labelP3, labelP4, labelP5, labelP6, labelP7, nombreMedico;
	@FXML
	private JFXButton botonP0, botonP1, botonP2, botonP3, botonP4, botonP5, botonP6, botonP7;
	@FXML
	private TextField busquedaPacientes;
	// Pulsaciones
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;
	@FXML
	private LineChart<?, ?> LineChart;
	// Saturación en sangre
	@FXML
	private CategoryAxis XS;
	@FXML
	private NumberAxis YS;
	@FXML
	private LineChart<?, ?> LCSangre;
	@FXML
	private Label txtNombre, txtDni, txtSexo, txtFecha, txtContacto, textoCentral, tituloOxigenacion, tituloPulsaciones;
	@FXML
	protected GoogleMapView mapView;

	private Medico yo;

	/*
	 * Instanciar servicios
	 */
	private ServicioPacientes sp = ServicioPacientes.getInstance();
	private ServicioAyuda sa = ServicioAyuda.getInstance();
	private ServicioLogin sl = ServicioLogin.getInstance();

	private int[] ubiPacientes = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private Label[] labelesPacientes = new Label[8];
	private JFXButton[] botonesPacientes = new JFXButton[8];
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private GoogleMap map;
	private DecimalFormat formatter = new DecimalFormat("###.00000");
	private Paciente elegidoP = null;

	/*
	 * Metodo para cerrar sesion y volver a pantalla login
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
			Stage s_login = (Stage) BtnCerrarSesion_.getScene().getWindow();
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
	 * Metodo para editar perfil como medico
	 */
	public void EditarPerfil() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarUsuario.fxml"));
			EditarUsuarioControlador editarUsuarioControlador = new EditarUsuarioControlador();
			loader.setController(editarUsuarioControlador);
			Parent rootLogin = loader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
			stage.setTitle("Life++");
			stage.setScene(new Scene(rootLogin));
			editarUsuarioControlador.setMedicoEditable(yo);
			editarUsuarioControlador.setMedicoRegreso(yo);
			editarUsuarioControlador.OcultarBaja();
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) BtnCerrarSesion_.getScene().getWindow();
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
	 * Metodo para solicitar ayuda como medico
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
			solicitarAyuda.setMedicoRegreso(yo);
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) BtnCerrarSesion_.getScene().getWindow();
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
	 * Metodo para cargar listado de pacientes en la pantalla de medico
	 * 
	 * @exception IOException
	 */
	public void CargarPacientes(KeyEvent event) throws IOException {
		labelesPacientes[0] = labelP0;
		labelesPacientes[1] = labelP1;
		labelesPacientes[2] = labelP2;
		labelesPacientes[3] = labelP3;
		labelesPacientes[4] = labelP4;
		labelesPacientes[5] = labelP5;
		labelesPacientes[6] = labelP6;
		labelesPacientes[7] = labelP7;

		botonesPacientes[0] = botonP0;
		botonesPacientes[1] = botonP1;
		botonesPacientes[2] = botonP2;
		botonesPacientes[3] = botonP3;
		botonesPacientes[4] = botonP4;
		botonesPacientes[5] = botonP5;
		botonesPacientes[6] = botonP6;
		botonesPacientes[7] = botonP7;

		java.util.List<Paciente> pacientes = sp.getPacientes();

		if (busquedaPacientes.getText().isEmpty()) {
			for (int i = 0; i < labelesPacientes.length; i++) {
				try {
					labelesPacientes[i].setVisible(true);
					labelesPacientes[i].setText(pacientes.get(i).getNombre());
					botonesPacientes[i].setVisible(true);

				} catch (Exception e) {

					labelesPacientes[i].setVisible(false);
					botonesPacientes[i].setVisible(false);
					System.out.println("Hay menos de 8 pacientes. Label[" + i + "] se oculta");
					// e.printStackTrace();
				}
			}
		} else {
			String textoBusquedaP = busquedaPacientes.getText().toLowerCase();
			int longBusquedaP = busquedaPacientes.getText().length();
			int correccionP = 0;

			for (int i = 0; i < labelesPacientes.length; i++) {
				labelesPacientes[i].setVisible(false);
				botonesPacientes[i].setVisible(false);
				labelesPacientes[0].setVisible(true);
				labelesPacientes[0].setText("No hay resultados");
			}

			for (int i = 0; i < pacientes.size(); i++) {
				try {
					boolean coincideP = true;

					for (int j = 0; j < longBusquedaP; j++) {
						if (textoBusquedaP.charAt(j) != pacientes.get(i).getNombre().toLowerCase().charAt(j)) {
							coincideP = false;
						}
					}
					if (coincideP == true) {
						labelesPacientes[i - correccionP].setVisible(true);
						botonesPacientes[i - correccionP].setVisible(true);
						labelesPacientes[i - correccionP].setText(pacientes.get(i).getNombre());
					} else {
						correccionP++;
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Metodo para mostrar los datos del paciente seleccionado por el medico
	 * 
	 * @exception IOException
	 */
	public void mostrarDatos(ActionEvent event) throws IOException {
		JFXButton sourceButton = (JFXButton) event.getSource();
		textoCentral.setText("Ubicación actual");
		tituloOxigenacion.setVisible(true);
		tituloPulsaciones.setVisible(true);
		LCSangre.setVisible(true);
		mapView.setVisible(true);
		LineChart.setVisible(true);

		for (int i = 0; i < botonesPacientes.length; i++) {
			if (sourceButton == botonesPacientes[i]) {
				elegidoP = sp.getPacientes().get(ubiPacientes[i]);

				try {
					String nombrePaciente = elegidoP.getNombre();
					txtNombre.setText(nombrePaciente);
					String dniPaciente = elegidoP.getDni();
					txtDni.setText(dniPaciente);
					boolean sexo = elegidoP.getSexo();
					if (sexo == true) {
						txtSexo.setText("Masculino");
					} else {
						txtSexo.setText("Femenino");
					}
					String correo = elegidoP.getCorreo();
					txtContacto.setText(correo);
					String fecha = sdf.format(elegidoP.getNacimiento());
					txtFecha.setText(fecha);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Metodo para descargar historial medico del paciente
	 * 
	 * @exception IOException
	 */
	@FXML
	public void Descargar(ActionEvent event) throws IOException {
		try {
			File path = new File(System.getProperty("user.dir") + "//certificados//" + elegidoP.getDni() + ".pdf");// Indicar
																													// la
																													// ruta
																													// correspondiente(relativa)
			Desktop.getDesktop().open(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Metodo para editar datos
	 * 
	 * @exception IOException
	 */
	public void EditarUsuario(ActionEvent event) throws IOException {
		if (elegidoP != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarUsuario.fxml"));
				EditarUsuarioControlador editarUsuarioControlador = new EditarUsuarioControlador();
				loader.setController(editarUsuarioControlador);
				Parent rootLogin = loader.load();
				Stage stage = new Stage();
				stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
				stage.setTitle("Life++");
				stage.setScene(new Scene(rootLogin));
				editarUsuarioControlador.setPacienteEditable(elegidoP);
				editarUsuarioControlador.setMedicoRegreso(yo);
				editarUsuarioControlador.OcultarBaja();
				stage.setMinHeight(600);
				stage.setMinWidth(600);
				Stage s_login = (Stage) BtnCerrarSesion_.getScene().getWindow();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tituloOxigenacion.setVisible(false);
		tituloPulsaciones.setVisible(false);
		LCSangre.setVisible(false);
		mapView.setVisible(false);
		LineChart.setVisible(false);
		mapView.addMapInitializedListener(() -> configureMap());
		XYChart.Series series = new XYChart.Series();

		series.getData().add(new XYChart.Data("0", 0));
		series.getData().add(new XYChart.Data("500", 200));
		series.getData().add(new XYChart.Data("1000", 100));
		series.getData().add(new XYChart.Data("1500", 507));
		series.getData().add(new XYChart.Data("2000", 200));
		series.getData().add(new XYChart.Data("2500", 656));
		series.getData().add(new XYChart.Data("3000", 547));
		series.getData().add(new XYChart.Data("3500", 200));
		series.getData().add(new XYChart.Data("4000", 900));
		series.getData().add(new XYChart.Data("4500", 390));
		series.getData().add(new XYChart.Data("5000", 600));
		LineChart.getData().addAll(series);

		XYChart.Series seriess = new XYChart.Series();
		XYChart.Series seriess_ = new XYChart.Series();
		seriess.getData().add(new XYChart.Data("0", 0));
		seriess.getData().add(new XYChart.Data("300", 200));
		seriess.getData().add(new XYChart.Data("400", 100));
		seriess.getData().add(new XYChart.Data("500", 507));
		seriess.getData().add(new XYChart.Data("600", 200));
		seriess.getData().add(new XYChart.Data("700", 656));
		seriess.getData().add(new XYChart.Data("800", 547));
		seriess.getData().add(new XYChart.Data("900", 200));
		seriess.getData().add(new XYChart.Data("1000", 900));

		seriess_.getData().add(new XYChart.Data("0", 0));
		seriess_.getData().add(new XYChart.Data("300", 400));
		seriess_.getData().add(new XYChart.Data("400", 656));
		seriess_.getData().add(new XYChart.Data("500", 547));
		seriess_.getData().add(new XYChart.Data("600", 200));
		seriess_.getData().add(new XYChart.Data("700", 900));
		seriess_.getData().add(new XYChart.Data("800", 390));
		seriess_.getData().add(new XYChart.Data("900", 600));
		seriess_.getData().add(new XYChart.Data("1000", 600));
		LCSangre.getData().addAll(seriess);
		LCSangre.getData().addAll(seriess_);
		try {
			CargarPacientes(null);
		} catch (Exception e) {
			System.out.println("Problemas al cargar medicos o pacientes en el initialize()");
		}
	}

	public void setYo(Medico nuevoYo) {
		yo = nuevoYo;
		try {
			nombreMedico.setText(yo.getNombre());
		} catch (Exception e) {
			System.out.println("No se especifica bien el medico");
		}
	}

	/*
	 * Configuracion del mapa
	 */
	protected void configureMap() {
		MapOptions mapOptions = new MapOptions();

		mapOptions.center(new LatLong(47.6097, -122.3331)).mapType(MapTypeIdEnum.ROADMAP).zoom(9);
		map = mapView.createMap(mapOptions, false);

		map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
			LatLong latLong = event.getLatLong();
		});
	}

	/*
	 * Metodo para ver la respuesta del developer
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

			verrespuesta.setMedicoRegreso(yo);
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) BtnCerrarSesion_.getScene().getWindow();
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