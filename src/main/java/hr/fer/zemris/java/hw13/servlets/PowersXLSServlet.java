package hr.fer.zemris.java.hw13.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.util.ParamsUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * pozivom metode {@link #doGet(HttpServletRequest, HttpServletResponse)} ,a
 * ukoliko su svi predani parametri predani i u odgovarajućim rasponima,
 * generiraju .xls dokument. Unutar tog .xls dokumenta, a unutar svake stranice,
 * nalazi se predani raspon brojeva i <i>i</i>-te potencije tog broja. Varijabla
 * <i>i</i> ovdje ide 1,...,<i>n</i> gdje je <i>n</i> jedan od parametara koje
 * je korisnik predao (uz <i>a</i> i <i>b</i>).
 * <p>
 * Ukoliko su parametri <i>a</i> ,<i>b</i> ili <i>n</i> van svojih raspona
 * [{@value #A_MIN}, {@value #A_MAX}], [{@value #B_MIN}, {@value #B_MAX}] i
 * [{@value #N_MIN}, {@value #N_MAX}]. Korisniku se prikazuje JSP dokument koji
 * detaljno opisuje što je korisnik krivo unio.
 * </p>
 * 
 * @see HttpServlet
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "powers", urlPatterns = { "/powers" })
public class PowersXLSServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstanta koja predstavlja minimalni broj koji smije poprimita parametar
	 * "a"
	 */
	private static final int A_MIN = -100;

	/**
	 * Konstanta koja predstavlja maksimalni broj koji smije poprimita parametar
	 * "a"
	 */
	private static final int A_MAX = 100;

	/**
	 * Konstanta koja predstavlja minimalni broj koji smije poprimita parametar
	 * "b"
	 */
	private static final int B_MIN = -100;

	/**
	 * Konstanta koja predstavlja maksimalni broj koji smije poprimita parametar
	 * "b"
	 */
	private static final int B_MAX = 100;

	/**
	 * Konstanta koja predstavlja minimalni broj koji smije poprimita parametar
	 * "n"
	 */
	private static final int N_MIN = 1;

	/**
	 * Konstanta koja predstavlja maksimalni broj koji smije poprimita parametar
	 * "n"
	 */
	private static final int N_MAX = 5;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer a = ParamsUtil.checkAndGetValue(request, "a");
		Integer b = ParamsUtil.checkAndGetValue(request, "b");
		Integer n = ParamsUtil.checkAndGetValue(request, "n");

		if (!checkParams(request, a, b, n)) {
			request.getRequestDispatcher("WEB-INF/pages/wrong-params.jsp").forward(request, response);
		}

		response.setContentType("application/vnd.ms-excel");
		response.getOutputStream().write(createPowersXLS(a, b, n));
	}

	/**
	 * Pomoćna metoda koja provjerava jesu li svi parametri prisutni. Ukoliko je
	 * to slučaj metoda za svaki parametar (<i>a</i>,<i>b</i>,<i>n</i>) ispituje
	 * je li u rasponu u kojem treba biti, pozivima metode
	 * {@link #checkRange(Integer, int, int)} za svaki od predanih parametara i
	 * sa njihovim rasponima.
	 *
	 * @param request
	 *            predstavlja primjerak sučelja {@link HttpServletRequest} koji
	 *            modelira zahtjev korisnika
	 * @param a
	 *            Predana vrijednost parametra a
	 * @param b
	 *            Predana vrijednost parametra b
	 * @param n
	 *            Predana vrijednost parametra n
	 * @return <code>true</code> ukoliko su svi parametri zadovoljili sve gore
	 *         opisane uvjete, <code>false</code> inače
	 */
	private boolean checkParams(HttpServletRequest request, Integer a, Integer b, Integer n) {
		List<ParamInfo> paramInfos = new ArrayList<>();
		if (a == null || !checkRange(a, A_MIN, A_MAX)) {
			paramInfos.add(new ParamInfo("a", a, A_MIN, A_MAX));
		}

		if (b == null || !checkRange(b, B_MIN, B_MAX)) {
			paramInfos.add(new ParamInfo("b", b, B_MIN, B_MAX));
		}

		if (n == null || !checkRange(n, N_MIN, N_MAX)) {
			paramInfos.add(new ParamInfo("n", n, N_MIN, N_MAX));
		}

		if (paramInfos.isEmpty()) {
			return true;
		}

		request.setAttribute("ParamInfos", paramInfos);
		return false;
	}

	/**
	 * Pomoćna metoda koje provjerava je li vrijednost predanog parametra
	 * <b>paramValue</b> unutar raspona [<b>min</b>,<b>max</b>].
	 *
	 * @param paramValue
	 *            vrijednost parametra koji se ispituje
	 * @param min
	 *            minimalna dozvoljena vrijednost parametra <b>paramValue</b>
	 * @param max
	 *            maksimalna dozvoljena vrijednost parametra <b>paramValue</b>
	 * @return <code>true</code> ako je vrijednost <b>paramValue</b> unutar
	 *         raspona, <code>false</code> inače
	 */
	private boolean checkRange(Integer paramValue, int min, int max) {
		return paramValue >= min && paramValue <= max;
	}

	/**
	 * Pomoćna metoda koje stvara .xls datoteku sa predanim parametrima
	 * <b>a</b>, <b>b</b> i <b>n</b>. Metoda za generiranje .xls datoteke
	 * koristi paket <b>org.apache.poi.</b>. Metoda će stvoriti ukupno <b>n</b>
	 * stranica ovog dokumenta. <i>i</i>-ta stranica će u prvom stupcu imati
	 * raspon [<b>a</b>, <b>b</b>], a u drugom stupcu <i>i</i>-tu potenciju
	 * svakog od broja iz raspona.
	 * <p>
	 * Ukoliko je <b>a</b> > <b>b</b> raspon je padajući
	 * </p>
	 *
	 * @param a
	 *            prvi parametar raspona
	 * @param b
	 *            drugi parametar raspona
	 * @param n
	 *            broj stranica i ujedino i najveća potencija
	 * @return polje okteta koje predstavlja stvoreni dokument
	 */
	private byte[] createPowersXLS(int a, int b, int n) {
		try (HSSFWorkbook workbook = new HSSFWorkbook()) {

			int len = Math.abs(b - a);
			int factor = (int) Math.signum(b - a);

			for (int power = 1; power <= n; power++) {
				HSSFSheet sheet = workbook.createSheet(String.valueOf(power));
				for (int i = 0; i <= len; i++) {
					int number = a + factor * i;

					HSSFRow rowInColumn = sheet.createRow(i);
					rowInColumn.createCell(0).setCellValue(number);
					rowInColumn.createCell(1).setCellValue(Math.pow(number, power));
				}
			}

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);

			return os.toByteArray();
		} catch (IOException igonorable) {
		}

		return null;
	}

	/**
	 * Pomoćni razred koji se koristi za detaljan opis pogrešne predaje
	 * parametara. Ukoliko se unutar metode
	 * {@link PowersXLSServlet#checkParams(HttpServletRequest, Integer, Integer, Integer)}
	 * pronađu parametri (jedan ili više njih) koji nisu korektno zadani, za
	 * svaki od njih stvara se primjerak ovog razreda te se {@link List} svih
	 * stvorenih primjeraka šalje stranici za ispis pogreške.
	 * 
	 * @author Davor Češljaš
	 */
	public static class ParamInfo {

		/**
		 * Članska varijabla koja predstavlja naziv (ključ) pod kojim je stigao
		 * parametar
		 */
		private String name;

		/**
		 * Članska varijabla koja predstavlja vrijednost dobivenu pod ključem
		 * {@link #name}
		 */
		private Integer value;

		/**
		 * Članksa varijabla koja predstavlja donju granicu ispod koje parametar
		 * ne smije ići
		 */
		private int min;

		/**
		 * Članksa varijabla koja predstavlja gornju granicu ispod koje
		 * parametar ne smije ići
		 */
		private int max;

		/**
		 * Konstruktor koji inicijalizira primjerak ovog razreda. Budući da
		 * primjerci ovog razreda predstavljaju strukture podataka koje
		 * modeliraju informacije o nekom parametru, cilj ovog konstruktora je
		 * sve predane parametre spremiti u odgovarajuće članske varijable
		 *
		 * @param name
		 *            naziv (ključ) pod kojim je stigao parametar
		 * @param value
		 *            vrijednost dobivena pod ključem <b>name</b>
		 * @param min
		 *            donja granicu ispod koje parametar ne smije ići
		 * @param max
		 *            gornja granicu ispod koje parametar ne smije ići
		 */
		public ParamInfo(String name, Integer value, int min, int max) {
			this.name = name;
			this.value = value;
			this.min = min;
			this.max = max;
		}

		/**
		 * Metoda koja dohvaća naziv (ključ) pod kojim je stigao parametar
		 *
		 * @return naziv (ključ) pod kojim je stigao parametar
		 */
		public String getName() {
			return name;
		}

		/**
		 * Metoda koja dohvaća vrijednost dobivena pod ključem <b>name</b>
		 *
		 * @return the value
		 */
		public Integer getValue() {
			return value;
		}

		/**
		 * Metoda koja donja granicu ispod koje parametar ne smije ići
		 *
		 * @return donja granicu ispod koje parametar ne smije ići
		 */
		public int getMin() {
			return min;
		}

		/**
		 * Metoda koja gornju granicu ispod koje parametar ne smije ići
		 *
		 * @return gornju granicu ispod koje parametar ne smije ići
		 */
		public int getMax() {
			return max;
		}
	}

}
