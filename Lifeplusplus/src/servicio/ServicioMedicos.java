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

import application.Medico;
import application.Paciente;
import application.Persona;
import controlador.RegistroMedicosControlador;
import controlador.RegistroPacientesControlador;

/*
 * CLASE SERVICIOMEDICOS
 */
public class ServicioMedicos {

	// Variable estatica para guardar una instancia singleton del servicio
	private static ServicioMedicos instance;
	// Estructuras para almacenar solicitudes y medicos
	private List<Medico> medicos;
	private List<Medico> solicitudes;

	private Gson gson;
	private String baseDir;

	/*
	 * Obtencion de la instancia singleton
	 * 
	 * @return ServicioMedicos
	 */
	public static ServicioMedicos getInstance() {
		if (instance == null) {
			instance = new ServicioMedicos();
		}
		return instance;
	}

	private ServicioMedicos() {
		medicos = new ArrayList();
		solicitudes = new ArrayList();
		gson = new Gson();
		baseDir = System.getProperty("user.dir"); // esto me coge donde estoy
		loadMedicos();
		loadSolicitudes();
	}

	/*
	 * Metodo para guardar en el JSON los medicos
	 */
	public void saveMedicos() {
		JsonArray jMedicos = new JsonArray();
		for (Medico m : medicos) { // m es de tipo medico y lo convierto en json
			jMedicos.add(m.toJson());
		}
		try {
			FileWriter fw = new FileWriter(this.baseDir + "/datos_json/MEDICOS_DATA.json");
			gson.toJson(jMedicos, fw);
			fw.flush(); // escribe
			fw.close(); // cierra
		} catch (IOException ex) {
			Logger.getLogger(RegistroMedicosControlador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 * Metodo para guardar en el JSON las solicitudes
	 */
	public void saveSolicitudes() {
		JsonArray jMedicos = new JsonArray();
		for (Medico m : solicitudes) { // m es de tipo medico y lo convierto en json
			jMedicos.add(m.toJson());
		}
		try {
			FileWriter fw = new FileWriter(this.baseDir + "/datos_json/SOLICITUDES_MEDICOS_DATA.json");
			gson.toJson(jMedicos, fw);
			fw.flush(); // escribe
			fw.close(); // cierra
		} catch (IOException ex) {
			Logger.getLogger(RegistroMedicosControlador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 * Metodo para leer el listado de medicos del JSON
	 */
	public void loadMedicos() {
		this.medicos = new ArrayList();
		try {
			JsonArray jArrayData = gson.fromJson(new FileReader(this.baseDir + "/datos_json/MEDICOS_DATA.json"),
					JsonArray.class);

			for (JsonElement je : jArrayData) {
				Medico medico = new Medico(je.getAsJsonObject());
				medicos.add(medico); // en este momento los medicos estan cargados
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(RegistroMedicosControlador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 * Metodo para leer las solicitudes del JSON
	 */
	public void loadSolicitudes() {
		this.solicitudes = new ArrayList();
		try {
			JsonArray jArrayData = gson.fromJson(
					new FileReader(this.baseDir + "/datos_json/SOLICITUDES_MEDICOS_DATA.json"), JsonArray.class);

			for (JsonElement je : jArrayData) {
				Medico medico = new Medico(je.getAsJsonObject());
				solicitudes.add(medico); // en este momento los medicos estan cargados
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(RegistroMedicosControlador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 * Metodo para añadir un medico al listado de medicos
	 * 
	 * @param m Medico
	 */
	public void addMedico(Medico m) {
		this.medicos.add(m);
	}

	/*
	 * Metodo para añadir una solicitud al listado de solicitudes de registro de
	 * medicos
	 * 
	 * @param m Medico
	 */
	public void addSolicitud(Medico m) {
		this.solicitudes.add(m);
	}

	/*
	 * Metodo para encontrar un medico por dni y password
	 * 
	 * @param dni
	 * 
	 * @param password
	 * 
	 * @return Persona
	 */
	public Persona encontarPersona(String dni, String password) {
		for (Medico m : this.medicos) {
			if (m.getDni().equals(dni) && m.getPassword().equals(password)) {
				return m;
			}
		}
		return null;
	}

	/*
	 * Metodo para encontrar una solicitud de registro de medico por dni y password
	 * 
	 * @param dni
	 * 
	 * @param password
	 * 
	 * @return Persona
	 */
	public Persona encontarSolicitud(String dni, String password) {
		for (Medico m : this.solicitudes) {
			if (m.getDni().equals(dni) && m.getPassword().equals(password)) {
				return m;
			}
		}
		return null;
	}

	/*
	 * Obtener listado de medicos
	 * 
	 * @return List<Medico>
	 */
	public List<Medico> getMedicos() {
		return medicos;
	}

	/*
	 * Obtener listado de solicitudes para registrarse como medico
	 * 
	 * @return List<Medico>
	 */
	public List<Medico> getSolicitudes() {
		return solicitudes;
	}

	/*
	 * Metodo para obtener datos medico
	 * 
	 * @param nuevoDni
	 * 
	 * @param nuevoPassword
	 * 
	 * @return Medico
	 */
	public Medico getMedico(String nuevoDni, String nuevoPassword) {
		Medico elegido = null;

		for (Medico med : medicos) {
			if (med.getDni().equals(nuevoDni) && med.getPassword().equals(nuevoPassword)) {
				elegido = med;
			}
		}
		return elegido;
	}

	/*
	 * Metodo para ver si existe o no un medico
	 * 
	 * @param dni
	 * 
	 * @return boolean
	 */
	public boolean checkIfExist(String dni) {
		for (Medico m : medicos) {

			if (m.getDni().equalsIgnoreCase(dni)) {
				return true;
			}
		}
		return false;
	}
}