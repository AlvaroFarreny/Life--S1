package servicio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import application.Paciente;
import application.Solicitud;
import controlador.RegistroMedicosControlador;

/*
 * CLASE SERVICIOAYUDA
 */
public class ServicioAyuda {

	private Gson gson;
	private String baseDir;
	// Estructura para almacenar solicitudes
	private List<Solicitud> solicitudes;
	// Variable estatica para guardar una instancia singleton del servicio
	private static ServicioAyuda instance;

	/*
	 * Obtencion de la instancia singleton
	 * 
	 * @return ServicioAyuda
	 */
	public static ServicioAyuda getInstance() {
		if (instance == null) {
			instance = new ServicioAyuda();
		}
		return instance;
	}

	private ServicioAyuda() {
		solicitudes = new ArrayList();
		gson = new Gson();
		baseDir = System.getProperty("user.dir"); // esto me coge donde estoy
		loadSolicitudes();
	}

	/*
	 * Metodo para guardar en el JSON las solicitudes
	 */
	public void saveSolicitudes() {
		JsonArray jSolicitudes = new JsonArray();
		for (Solicitud s : solicitudes) {
			jSolicitudes.add(s.toJson());
		}
		try {
			FileWriter fw = new FileWriter(this.baseDir + "/datos_json/SOLICITUDES_DATA.json");
			gson.toJson(jSolicitudes, fw);
			fw.flush(); // escribe
			fw.close(); // cierra
		} catch (IOException ex) {
			// Logger.getLogger(RegistroMedicosControlador.class.getName()).log(Level.SEVERE,
			// null, ex);
		}

	}

	/*
	 * Metodo para leer el listado de solicitudes del JSON
	 */
	public void loadSolicitudes() {
		this.solicitudes = new ArrayList();
		try {
			FileReader fr = new FileReader(this.baseDir + "/datos_json/SOLICITUDES_DATA.json");
			JsonArray jArrayData = gson.fromJson(fr, JsonArray.class);

			for (JsonElement je : jArrayData) {
				Solicitud solicitud = new Solicitud(je.getAsJsonObject());
				solicitudes.add(solicitud); // en este momento las solicitudes estan cargadas
			}
		} catch (FileNotFoundException ex) {

		} catch (Exception e) {

		}
	}

	/*
	 * Metodo para añadir una solicitud al listado de solicitudes
	 * 
	 * @param s Solicitud
	 */
	public void addSolicitudes(Solicitud s) {
		this.solicitudes.add(s);
	}

	/*
	 * Obtener listado de solicitudes
	 * 
	 * @return List<Solicitud>
	 */
	public List<Solicitud> getAyudas() {
		return solicitudes;
	}

	/*
	 * Metodo para obtener datos de la solicitud que coincida con el dni que se pasa
	 * 
	 * @param nuevoDni para buscar la solicitud
	 * 
	 * @return Solicitud
	 */
	public Solicitud getAyuda(String nuevoDni) {
		Solicitud elegida = null;

		for (Solicitud sol : solicitudes) {
			if (sol.getDni().equals(nuevoDni)) {
				elegida = sol;
			}
		}
		System.out.println(elegida);
		return elegida;
	}

	/*
	 * Metodo para eliminar una solicitud
	 * 
	 * @param dni
	 */
	public void eliminarSolicitud(String dni) {
		Solicitud elegida = null;

		for (Solicitud sol : solicitudes) {
			if (sol.getDni().equals(dni)) {
				elegida = sol;
				solicitudes.remove(sol);
			}
		}
	}

	/*
	 * Metodo para escribir respuesta a solicitud
	 * 
	 * @param dni
	 * 
	 * @param respuesta
	 */
	public void editarRespuesta(String dni, String respuesta) {
		Solicitud elegida = null;

		for (Solicitud sol : solicitudes) {
			if (sol.getDni().equals(dni)) {
				elegida = sol;
				sol.setSolucion(respuesta);
			}
		}
	}

}
