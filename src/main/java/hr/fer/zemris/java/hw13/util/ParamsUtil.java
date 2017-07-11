package hr.fer.zemris.java.hw13.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Razred koji služi kao biblioteka za obradu parametara primljenih od
 * korisnika. Ovom razredu ne mogu se stvarati primjerci, a sadrži sljedeće
 * metode:
 * <ul>
 * <li>{@link #getOrDefault(HttpServletRequest, String, int)}</li>
 * <li>{@link #checkAndGetValue(HttpServletRequest, String)}</li>
 * </ul>
 * 
 * @see HttpServletRequest
 * 
 * @author Davor Češljaš
 */
public class ParamsUtil {

	/**
	 * Privatni konstruktor koji služi tome da se primjerci ovog razreda ne mogu
	 * stvarati izvan samog razreda.
	 */
	private ParamsUtil() {
	}

	/**
	 * Metoda koja iz predanog primjerka razreda koji implementira sučelje
	 * {@link HttpServletRequest} dohvaća vrijednost parametra pod nazivom
	 * <b>name</b>. Ukoliko ta vrijednost ne postoji ili ukoliko se ona ne može
	 * pretvoriti u cijeli broj metoda vraća pretpostavljenu vrijednost
	 * <b>defaultValue</b>
	 *
	 * @param request
	 *            klijentov zahtjev modeliran sučeljem
	 *            {@link HttpServletRequest}
	 * @param name
	 *            ključ pod kojim je vrijednost parametra unutar klijentovog
	 *            zahtjeva modeliranog sa {@link HttpServletRequest}
	 * @param defaultValue
	 *            pretpostavljena vrijednost parametra pod naziv <b>name</b>
	 * @return vraća vrijednost mapiranu pod ključem <b>name</b> unutar predanih
	 *         parametara ili <b>defaultValue</b> u slučaju neke od gore
	 *         navedenih pogrešaka
	 */
	public static int getOrDefault(HttpServletRequest request, String name, int defaultValue) {
		Integer value = checkAndGetValue(request, name);

		return value == null ? defaultValue : value;
	}

	/**
	 * Metoda koja iz predanog primjerka razreda koji implementira sučelje
	 * {@link HttpServletRequest} dohvaća vrijednost parametra pod nazivom
	 * <b>name</b>. Ukoliko ta vrijednost ne postoji ili se ne može parsirati u
	 * cijeli broj metoda vraća <code>null</code>.
	 *
	 * @param request
	 *            klijentov zahtjev modeliran sučeljem
	 *            {@link HttpServletRequest}
	 * @param name
	 *            ključ pod kojim je vrijednost parametra unutar klijentovog
	 *            zahtjeva modeliranog sa {@link HttpServletRequest}
	 * @return vrijednost mapiranu pod ključem <b>name</b> unutar parametara
	 *         koje je korisnik predao ili <code>null</code> u slučaju neke od
	 *         gore opisanih pogrešaka
	 */
	public static Integer checkAndGetValue(HttpServletRequest request, String name) {
		String value = request.getParameter(name);

		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException ignorable) {
			}
		}

		return null;
	}
}
