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

import application.Developer;
import application.Medico;
import application.Paciente;
import application.Persona;
import controlador.RegistroMedicosControlador;
import controlador.RegistroPacientesControlador;

/*
 * CLASE SERVICIOPACIENTES
 */
public class ServicioPacientes {

	// Variable estatica para guardar una instancia singleton del servicio
	private static ServicioPacientes instance;
	// Estructuras para almacenar pacientes
	private List<Paciente> pacientes;

	private Gson gson;
	private String baseDir;

	/*
	 * Obtencion de la instancia singleton
	 * 
	 * @return ServicioPacientes
	 */
	public static ServicioPacientes getInstance() {
		if (instance == null) {
			instance = new ServicioPacientes();
		}
		return instance;
	}

	private ServicioPacientes() {
		pacientes = new ArrayList();
		gson = new Gson();
		baseDir = System.getProperty("user.dir"); // esto me coge donde estoy
		loadPacientes();
	}

	/*
	 * Metodo para guardar en el JSON los pacientes
	 */
	public void savePacientes() {
		JsonArray jPacientes = new JsonArray();
		for (Paciente p : pacientes) { // p es de tipo paciente y lo convierto en json
			jPacientes.add(p.toJson());
		}
		try {
			FileWriter fw = new FileWriter(this.baseDir + "/datos_json/PACIENTES_DATA.json");
			gson.toJson(jPacientes, fw);
			fw.flush(); // escribe
			fw.close(); // cierra
		} catch (IOException ex) {
			Logger.getLogger(RegistroMedicosControlador.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/*
	 * Metodo para leer el listado de pacientes del JSON
	 */
	public void loadPacientes() {
		this.pacientes = new ArrayList();
		try {
			FileReader fr = new FileReader(this.baseDir + "/datos_json/PACIENTES_DATA.json");
			JsonArray jArrayData = gson.fromJson(fr, JsonArray.class);

			for (JsonElement je : jArrayData) {
				Paciente paciente = new Paciente(je.getAsJsonObject());
				pacientes.add(paciente); // en este momento los pacientes estan cargados
			}

		} catch (FileNotFoundException ex) {

		} catch (Exception e) {

		}
	}

	/*
	 * Metodo para añadir un paciente al listado de pacientes
	 * 
	 * @param p Paciente
	 */
	public void addPaciente(Paciente p) {
		this.pacientes.add(p);
	}

	/*
	 * Metodo para encontrar un paciente por dni y password
	 * 
	 * @param dni
	 * 
	 * @param password
	 * 
	 * @return Persona
	 */
	public Persona encontarPersona(String dni, String password) {
		for (Paciente p : this.pacientes) {
			if (p.getDni().equals(dni) && p.getPassword().equals(password)) {
				return p;
			}
		}
		return null;
	}

	/*
	 * Obtener listado de pacientes
	 * 
	 * @return List<Paciente>
	 */
	public List<Paciente> getPacientes() {
		return pacientes;
	}

	/*
	 * Metodo para obtener datos paciente
	 * 
	 * @param nuevoDni
	 * 
	 * @param nuevoPassword
	 * 
	 * @return Paciente
	 */
	public Paciente getPaciente(String nuevoDni, String nuevoPassword) {
		Paciente elegido = null;

		for (Paciente pac : pacientes) {
			if (pac.getDni().equals(nuevoDni) && pac.getPassword().equals(nuevoPassword)) {
				elegido = pac;
			}
		}
		return elegido;
	}

	/*
	 * Metodo para ver si existe o no un paciente
	 * 
	 * @param dni
	 * 
	 * @return boolean
	 */
	public boolean checkIfExist(String dni) {
		for (Paciente pac : pacientes) {

			if (pac.getDni().equalsIgnoreCase(dni)) {
				return true;
			}
		}
		return false;
	}
}
