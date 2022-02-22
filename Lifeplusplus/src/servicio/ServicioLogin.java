package servicio;

import java.util.List;

import com.google.gson.Gson;

import application.Developer;
import application.Medico;
import application.Paciente;
import application.Persona;
import application.TipoPersona;

/*
 * CLASE SERVICIOLOGIN
 */
public class ServicioLogin {

	// Variable estatica para guardar una instancia singleton del servicio
	private static ServicioLogin instance;
	// Intancias de servicios
	private ServicioMedicos sm;
	private ServicioPacientes sp;
	private ServicioDevelopers sd;
	// Usuario que ha iniciado sesion
	private Persona logedUser;

	private TipoPersona tipoPersona;

	/*
	 * Obtencion de la instancia singleton
	 * 
	 * @return ServicioLogin
	 */
	public static ServicioLogin getInstance() {
		if (instance == null) {
			instance = new ServicioLogin();
		}
		return instance;
	}

	/*
	 * Constructor Inicializa los servicios
	 */
	public ServicioLogin() {
		sm = ServicioMedicos.getInstance();
		sp = ServicioPacientes.getInstance();
		sd = ServicioDevelopers.getInstance();
	}

	/*
	 * Metodo para iniciar sesion con el dni y password
	 * 
	 * @param dni
	 * 
	 * @param password
	 * 
	 * @return boolean
	 */
	public boolean doLogin(String dni, String password) {
		Paciente p = (Paciente) sp.encontarPersona(dni, password);
		if (p != null) {
			logedUser = p;
			tipoPersona = TipoPersona.PACIENTE;
			return true;
		} else {
			Medico m = (Medico) sm.encontarPersona(dni, password);
			if (m != null) {
				logedUser = m;
				tipoPersona = TipoPersona.MEDICO;
				return true;
			} else {
				Developer d = (Developer) sd.encontarPersona(dni, password);
				if (d != null) {
					logedUser = d;
					tipoPersona = TipoPersona.DEVELOPER;
					return true;
				}
			}
		}
		return false;

	}

	/*
	 * Getters
	 */
	public ServicioMedicos getSm() {
		return sm;
	}

	public ServicioPacientes getSp() {
		return sp;
	}

	public ServicioDevelopers getSd() {
		return sd;
	}

	public Persona getLogedUser() {
		return logedUser;
	}

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

}
