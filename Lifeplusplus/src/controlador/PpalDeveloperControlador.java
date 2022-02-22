package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;

import application.Developer;
import application.Medico;
import application.Paciente;
import application.Solicitud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import servicio.ServicioAyuda;
import servicio.ServicioDevelopers;
import servicio.ServicioMedicos;
import servicio.ServicioPacientes;

/*
 * CONTROLADOR PANTALLA PRINCIPAL DEVELOPER
 */
public class PpalDeveloperControlador implements Initializable {
	@FXML
	private JFXButton btnCerrarSesion, DatosPersonales, elegirMedicos, elegirSolicitudes;
	@FXML
	private Label labelM0, labelM1, labelM2, labelM3, labelM4, labelM5, labelM6, labelM7;
	@FXML
	private JFXButton botonM0, botonM1, botonM2, botonM3, botonM4, botonM5, botonM6, botonM7;
	@FXML
	private Label labelP0, labelP1, labelP2, labelP3, labelP4, labelP5, labelP6, labelP7;
	@FXML
	private JFXButton botonP0, botonP1, botonP2, botonP3, botonP4, botonP5, botonP6, botonP7;
	@FXML
	private TextField busquedaMedicos, busquedaPacientes, busquedaAyudas;
	@FXML
	private Label quienSoy;
	@FXML
	private Label labelA0, labelA1, labelA2, labelA3, labelA4, labelA5, labelA6, labelA7;
	@FXML
	private JFXButton botonA0, botonA1, botonA2, botonA3, botonA4, botonA5, botonA6, botonA7;

	private Developer yo;

	/*
	 * Instanciar los servicios
	 */
	private ServicioMedicos sm = ServicioMedicos.getInstance();
	private ServicioPacientes sp = ServicioPacientes.getInstance();
	private ServicioAyuda sa = ServicioAyuda.getInstance();

	private boolean medicosSonSolicitudes; // Determinar si se muestran medicos confirmadors (false) o solicitudes
											// (true)
	// Estructuras para almacenar medicos para el buscador
	private List<Medico> buscadorMedicos;

	private int[] ubiMedicos = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private int[] ubiPacientes = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	private int[] ubiAyudas = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	private Label[] labelesMedicos = new Label[8];
	private JFXButton[] botonesMedicos = new JFXButton[8];
	private Label[] labelesPacientes = new Label[8];
	private JFXButton[] botonesPacientes = new JFXButton[8];
	private Label[] labelesAyudas = new Label[8];
	private JFXButton[] botonesAyudas = new JFXButton[8];

	/*
	 * Metodo para mostrar listado de medicos
	 * 
	 * @exception IOException
	 */
	public void ElegirMedicos(ActionEvent event) throws IOException {
		medicosSonSolicitudes = false;
		CargarMedicos(null);
	}

	/*
	 * Metodo para mostrar listado de solicitudes de medicos pendientes
	 * 
	 * @exception IOException
	 */
	public void ElegirSolicitudes(ActionEvent event) throws IOException {
		medicosSonSolicitudes = true;
		CargarMedicos(null);
	}

	/*
	 * Metodo para cargar listado de medicos en la pantalla de developer
	 * 
	 * @exception IOException
	 */
	public void CargarMedicos(KeyEvent event) throws IOException {
		buscadorMedicos = new ArrayList();

		if (medicosSonSolicitudes == false) {
			buscadorMedicos = sm.getMedicos();
		} else {
			buscadorMedicos = sm.getSolicitudes();
		}

		if (busquedaMedicos.getText().isEmpty()) {
			for (int i = 0; i < labelesMedicos.length; i++) {
				try {
					labelesMedicos[i].setVisible(true);
					labelesMedicos[i].setText(buscadorMedicos.get(i).getNombre());
					botonesMedicos[i].setVisible(true);

				} catch (Exception e) {

					labelesMedicos[i].setVisible(false);
					botonesMedicos[i].setVisible(false);
					// e.printStackTrace();
				}
			}
		} else {
			String textoBusquedaM = busquedaMedicos.getText().toLowerCase();
			int longBusquedaM = busquedaMedicos.getText().length();
			int correccionM = 0;

			for (int i = 0; i < labelesMedicos.length; i++) {
				labelesMedicos[i].setVisible(false);
				botonesMedicos[i].setVisible(false);
				labelesMedicos[0].setVisible(true);
				labelesMedicos[0].setText("No hay resultados");
			}

			for (int i = 0; i < buscadorMedicos.size(); i++) {
				try {
					boolean coincideM = true;

					for (int j = 0; j < longBusquedaM; j++) {
						if (textoBusquedaM.charAt(j) != buscadorMedicos.get(i).getNombre().toLowerCase().charAt(j)) {
							coincideM = false;
						}
					}
					if (coincideM == true) {
						labelesMedicos[i - correccionM].setVisible(true);
						botonesMedicos[i - correccionM].setVisible(true);
						labelesMedicos[i - correccionM].setText(buscadorMedicos.get(i).getNombre());
						ubiMedicos[i - correccionM] = i;
					} else {
						correccionM++;
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Metodo para cargar listado de pacientes en la pantalla de developer
	 * 
	 * @exception IOException
	 */
	public void CargarPacientes(KeyEvent event) throws IOException {
		if (busquedaPacientes.getText().isEmpty()) {
			for (int i = 0; i < labelesPacientes.length; i++) {
				try {
					labelesPacientes[i].setVisible(true);
					labelesPacientes[i].setText(sp.getPacientes().get(i).getNombre());
					botonesPacientes[i].setVisible(true);

				} catch (Exception e) {

					labelesPacientes[i].setVisible(false);
					botonesPacientes[i].setVisible(false);
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

			for (int i = 0; i < sp.getPacientes().size(); i++) {
				try {
					boolean coincideP = true;

					for (int j = 0; j < longBusquedaP; j++) {
						if (textoBusquedaP.charAt(j) != sp.getPacientes().get(i).getNombre().toLowerCase().charAt(j)) {
							coincideP = false;
						}
					}
					if (coincideP == true) {
						labelesPacientes[i - correccionP].setVisible(true);
						botonesPacientes[i - correccionP].setVisible(true);
						labelesPacientes[i - correccionP].setText(sp.getPacientes().get(i).getNombre());
						ubiPacientes[i - correccionP] = i;
					} else {
						correccionP++;
					}

				} catch (Exception e) {

					// System.out.println("Algo fue mal");
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Metodo para editar datos de un usuario como developer
	 * 
	 * @exception IOException
	 */
	public void EditarUsuario(ActionEvent event) throws IOException {
		buscadorMedicos = new ArrayList();

		if (medicosSonSolicitudes == false) {
			buscadorMedicos = sm.getMedicos();
		} else {
			buscadorMedicos = sm.getSolicitudes();
		}

		JFXButton sourceButton = (JFXButton) event.getSource();
		Medico elegidoM = null;
		Paciente elegidoP = null;
		Solicitud elegidoA = null;
		boolean encontrado = false;

		for (int i = 0; i < botonesMedicos.length; i++) {
			if (sourceButton == botonesMedicos[i]) {
				encontrado = true;
				elegidoM = buscadorMedicos.get(ubiMedicos[i]);

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarUsuario.fxml"));
					EditarUsuarioControlador editarUsuarioControlador = new EditarUsuarioControlador();
					loader.setController(editarUsuarioControlador);
					Parent rootLogin = loader.load();
					Stage stage = new Stage();
					stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
					stage.setTitle("Life++");
					stage.setScene(new Scene(rootLogin));
					editarUsuarioControlador.setMedicoEditable(elegidoM);
					editarUsuarioControlador.setElegido(ubiMedicos[i]);
					if (medicosSonSolicitudes == true) {
						editarUsuarioControlador.getBtnDarBaja().setVisible(false);
						editarUsuarioControlador.getBtnAceptarSolicitud().setVisible(true);
						editarUsuarioControlador.getBtnRechazarSolicitud().setVisible(true);
					}
					editarUsuarioControlador.setDevRegreso(yo);
					stage.setMinHeight(600);
					stage.setMinWidth(600);
					Stage s_login = (Stage) botonP0.getScene().getWindow();
					stage.setHeight(s_login.getHeight());
					stage.setWidth(s_login.getWidth());
					stage.setX(s_login.getX());
					stage.setY(s_login.getY());
					stage.show();
					s_login.hide();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (sourceButton == botonesPacientes[i]) {
				encontrado = true;
				elegidoP = sp.getPacientes().get(ubiPacientes[i]);

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
					editarUsuarioControlador.setElegido(ubiPacientes[i]);
					editarUsuarioControlador.setDevRegreso(yo);
					stage.setMinHeight(600);
					stage.setMinWidth(600);
					Stage s_login = (Stage) botonP0.getScene().getWindow();
					stage.setHeight(s_login.getHeight());
					stage.setWidth(s_login.getWidth());
					stage.setX(s_login.getX());
					stage.setY(s_login.getY());
					stage.show();
					s_login.hide();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (sourceButton == botonesAyudas[i]) {
				encontrado = true;
				elegidoA = sa.getAyudas().get(ubiAyudas[i]);

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/SolicitarAyudaPaciente.fxml"));
					SolicitarAyuda solicitarAyuda = new SolicitarAyuda();
					loader.setController(solicitarAyuda);
					solicitarAyuda.setElegido(ubiAyudas[i]);
					solicitarAyuda.setDevRegreso(yo);
					solicitarAyuda.setYo(elegidoA);
					Parent rootLogin = loader.load();
					Stage stage = new Stage();
					stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
					stage.setTitle("Life++");
					stage.setScene(new Scene(rootLogin));
					stage.setMinHeight(600);
					stage.setMinWidth(600);
					Stage s_login = (Stage) botonP0.getScene().getWindow();
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
		if (encontrado == false) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarUsuario.fxml"));
			EditarUsuarioControlador editarUsuarioControlador = new EditarUsuarioControlador();
			loader.setController(editarUsuarioControlador);
			Parent rootLogin = loader.load();
			Stage stage = new Stage();
			stage.getIcons().add(new Image("/Img/lifeplusplus.png"));
			stage.setTitle("Life++");
			stage.setScene(new Scene(rootLogin));
			editarUsuarioControlador.setDatosPersonales(yo);
			editarUsuarioControlador.setDevRegreso(yo);
			stage.setMinHeight(600);
			stage.setMinWidth(600);
			Stage s_login = (Stage) botonP0.getScene().getWindow();
			stage.setHeight(s_login.getHeight());
			stage.setWidth(s_login.getWidth());
			stage.setX(s_login.getX());
			stage.setY(s_login.getY());
			stage.show();
			s_login.hide();
		}
	}

	/*
	 * Metodo para cerrar sesion y volver a la pantalla de login
	 * 
	 * @exception IOException
	 */
	public void Logout(ActionEvent event) throws IOException {

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
			Stage s_login = (Stage) botonP0.getScene().getWindow();
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
	 * Metodo para cargar las ayudas solicitadas en la pantalla de developer
	 * 
	 * @exception IOException
	 */
	public void CargarAyudas(KeyEvent event) throws IOException {
		if (busquedaAyudas.getText().isEmpty()) {
			for (int i = 0; i < labelesAyudas.length; i++) {
				try {
					labelesAyudas[i].setVisible(true);
					labelesAyudas[i].setText(sa.getAyudas().get(i).getNombre()); // ver que ocurre
					botonesAyudas[i].setVisible(true);

				} catch (Exception e) {

					labelesAyudas[i].setVisible(false);
					botonesAyudas[i].setVisible(false);
				}
			}
		} else {
			String textoBusquedaA = busquedaAyudas.getText().toLowerCase();
			int longBusquedaA = busquedaAyudas.getText().length();
			int correccionA = 0;

			for (int i = 0; i < labelesAyudas.length; i++) {
				labelesAyudas[i].setVisible(false);
				botonesAyudas[i].setVisible(false);
				labelesAyudas[0].setVisible(true);
				labelesAyudas[0].setText("No hay resultados");
			}

			for (int i = 0; i < sa.getAyudas().size(); i++) {
				try {
					boolean coincideA = true;

					for (int j = 0; j < longBusquedaA; j++) {
						if (textoBusquedaA.charAt(j) != sa.getAyudas().get(i).getNombre().toLowerCase().charAt(j)) {
							coincideA = false;
						}
					}
					if (coincideA == true) {
						labelesAyudas[i - correccionA].setVisible(true);
						botonesAyudas[i - correccionA].setVisible(true);
						labelesAyudas[i - correccionA].setText(sa.getAyudas().get(i).getNombre());
						ubiAyudas[i - correccionA] = i;
					} else {
						correccionA++;
					}

				} catch (Exception e) {

					// System.out.println("Algo fue mal");
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		medicosSonSolicitudes = false;
		quienSoy.setText("Trabajando como: " + yo.getNombre());

		labelesMedicos[0] = labelM0;
		labelesMedicos[1] = labelM1;
		labelesMedicos[2] = labelM2;
		labelesMedicos[3] = labelM3;
		labelesMedicos[4] = labelM4;
		labelesMedicos[5] = labelM5;
		labelesMedicos[6] = labelM6;
		labelesMedicos[7] = labelM7;

		botonesMedicos[0] = botonM0;
		botonesMedicos[1] = botonM1;
		botonesMedicos[2] = botonM2;
		botonesMedicos[3] = botonM3;
		botonesMedicos[4] = botonM4;
		botonesMedicos[5] = botonM5;
		botonesMedicos[6] = botonM6;
		botonesMedicos[7] = botonM7;

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

		labelesAyudas[0] = labelA0;
		labelesAyudas[1] = labelA1;
		labelesAyudas[2] = labelA2;
		labelesAyudas[3] = labelA3;
		labelesAyudas[4] = labelA4;
		labelesAyudas[5] = labelA5;
		labelesAyudas[6] = labelA6;
		labelesAyudas[7] = labelA7;

		botonesAyudas[0] = botonA0;
		botonesAyudas[1] = botonA1;
		botonesAyudas[2] = botonA2;
		botonesAyudas[3] = botonA3;
		botonesAyudas[4] = botonA4;
		botonesAyudas[5] = botonA5;
		botonesAyudas[6] = botonA6;
		botonesAyudas[7] = botonA7;

		try {
			CargarMedicos(null);
			CargarPacientes(null);
			CargarAyudas(null);

		} catch (Exception e) {
			System.out.println("Problemas al cargar medicos o pacientes en el initialize()");
			// e.printStackTrace();
		}
	}

	public void setYo(Developer nuevoYo) {
		yo = nuevoYo;
	}
}