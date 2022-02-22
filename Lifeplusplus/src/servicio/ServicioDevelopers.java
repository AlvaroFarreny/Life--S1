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
import application.Paciente;
import application.Persona;
import controlador.RegistroMedicosControlador;
import controlador.RegistroPacientesControlador;

/*
 * CLASE SERVICIODEVELOPERS
 */
public class ServicioDevelopers {

	// Variable estatica para guardar una instancia singleton del servicio
	private static ServicioDevelopers instance;
	// Estructura para almacenar developers
	private List<Developer> developers;
	private Gson gson;
	private String baseDir;

	/*
	 * Obtencion de la instancia singleton
	 * 
	 * @return ServicioDevelopers
	 */
	public static ServicioDevelopers getInstance() {
		if (instance == null) {
			instance = new ServicioDevelopers();
		}
		return instance;
	}

	private ServicioDevelopers() {
		developers = new ArrayList();
		gson = new Gson();
		baseDir = System.getProperty("user.dir"); // esto me coge donde estoy
		loadDevelopers();
	}

	/*
	 * Metodo para guardar en el JSON los developers
	 */
	public void saveDevelopers() {
		JsonArray jDevelopers = new JsonArray();
		for (Developer d : developers) { // d es de tipo developer y lo convierto en json
			jDevelopers.add(d.toJson());
		}
		try {
			FileWriter fw = new FileWriter(this.baseDir + "/Datos_json/DEVELOPERS_DATA.json");
			gson.toJson(jDevelopers, fw);
			fw.flush(); // escribe
			fw.close(); // cierra
		} catch (IOException ex) {
			Logger.getLogger(RegistroMedicosControlador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 * Metodo para leer el listado de developers del JSON
	 */
	public void loadDevelopers() {
		this.developers = new ArrayList();
		try {
			JsonArray jArrayData = gson.fromJson(new FileReader(this.baseDir + "/datos_json/DEVELOPERS_DATA.json"),
					JsonArray.class);

			for (JsonElement je : jArrayData) {
				Developer developer = new Developer(je.getAsJsonObject());
				developers.add(developer); // en este momento los developers estan cargados
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(RegistroPacientesControlador.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/*
	 * Metodo para añadir un developer al listado de developers
	 * 
	 * @param d Developer
	 */
	public void addDeveloper(Developer d) {
		this.developers.add(d);
	}

	/*
	 * Metodo para encontrar un developer por dni y password
	 * 
	 * @param dni
	 * 
	 * @param password
	 * 
	 * @return Persona
	 */
	public Persona encontarPersona(String dni, String password) {
		for (Developer p : this.developers) {
			if (p.getDni().equals(dni) && p.getPassword().equals(password)) {
				return p;
			}
		}
		return null;
	}

	/*
	 * Obtener listado de developers
	 * 
	 * @return List<Developer>
	 */
	public List<Developer> getDevelopers() {
		return developers;
	}

	/*
	 * Metodo para obtener datos developer
	 * 
	 * @param nuevoDni
	 * 
	 * @param nuevoPassword
	 * 
	 * @return Developer
	 */
	public Developer getDeveloper(String nuevoDni, String nuevoPassword) {
		Developer elegido = null;

		for (Developer dev : developers) {
			if (dev.getDni().equals(nuevoDni) && dev.getPassword().equals(nuevoPassword)) {
				elegido = dev;
			}
		}
		return elegido;
	}
}