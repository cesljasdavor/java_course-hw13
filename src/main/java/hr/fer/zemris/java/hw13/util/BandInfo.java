package hr.fer.zemris.java.hw13.util;

/**
 * Razred koji služi kao struktura podataka koja modelira informacije o glasanju
 * za jedan bend unutar "voting-aplikacije". Svaki primjerak ovog razreda stoga
 * će sadržavati jedinstveni identifikator "id", naziv benda "name", poveznicu na
 * njihovu najpoznatiju pjesmu "represSong" i broj do sada sakupljenih glasova
 * "votes". Primjerci ovog razreda pogodni su za korištenje u EL-izrazima
 * 
 * @author Davor Češljaš
 */
public class BandInfo {

	/** Članska varijabla koja predstavlja jedinstveni identifikator benda */
	private int id;

	/** Članska varijabla koja predstavlja naziv benda */
	private String name;

	/**
	 * Članska varijabla koja predstavlja poveznicu na najpoznatiju pjesmu ovog benda
	 */
	private String represSong;

	/** Članska varijabla koja predstavlja broj do sada sakupljenih glasova */
	private int votes;

	/**
	 * Konstruktor koji inicijalizira primjerak ovog razreda. Budući da su
	 * primjerci ovog razreda struktura podataka koje modeliraju informacije o
	 * glasanju za neki bend, unutar konstruktora jednostavno se spremaju
	 * reference na sve predane parametre unutar za to predviđene članske
	 * varijable
	 *
	 * @param id
	 *            jedinstveni identifikator benda
	 * @param name
	 *            naziv benda
	 * @param represSong
	 *            poveznica na najpoznatiju pjesmu benda
	 */
	public BandInfo(int id, String name, String represSong) {
		this.id = id;
		this.name = name;
		this.represSong = represSong;
	}

	/**
	 * Metoda koja dohvaća jedinstveni identifikator benda
	 *
	 * @return jedinstveni identifikator benda
	 */
	public int getId() {
		return id;
	}

	/**
	 * Metoda koja dohvaća naziv benda
	 *
	 * @return naziv benda
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metoda koja dohvaća poveznicu na najpoznatiju pjesmu benda
	 *
	 * @return poveznicu na najpoznatiju pjesmu benda
	 */
	public String getRepresSong() {
		return represSong;
	}

	/**
	 * Metoda koja dohvaća broj do sada sakupljenih glasova
	 *
	 * @return broj do sada sakupljenih glasova
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Metoda koja postavlja broj do sada sakupljenih glasova na predanu vrijednost <b>votes</b>
	 *
	 * @param votes
	 *            novi broj do sada sakupljenih glasova
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BandInfo other = (BandInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}

}