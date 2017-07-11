package hr.fer.zemris.java.hw13.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Razred koji se koristi kao pomoćna biblioteka za sve servlete unutar ove
 * aplikacije. Ovom razredu ne mogu se stvarati primjerci. Razred nudi sljedeće
 * metode:
 * <ul>
 * <li>{@link #createPieChart(String, PieDataset)}</li>
 * <li>{@link #loadDefaultBandInfos(ServletContext)}</li>
 * <li>{@link #loadBandInfos(String, String)}</li>
 * <li>{@link #parseBandInfo(String)}</li>
 * <li>{@link #parseBandVotes(List, String)}</li>
 * <li>{@link #findAndConsume(List, int, Consumer)}</li>
 * <li>{@link #createResults(List, Path)}</li>
 * </ul>
 * 
 * @see BandInfo
 * @see JFreeChart
 * 
 * @author Davor Češljaš
 */
public class ServletUtil {

	/**
	 * Konstanta koja predstavlja relativnu stazu do pretpostavljene datoteke sa
	 * rezultatima glasovanja za bendove
	 */
	public static final String VOTES_FILE_PATH = "/WEB-INF/voting-results.txt";

	/**
	 * Konstanta koja predstavlja relativnu stazu do pretpostavljene datoteke sa
	 * opisom bendova
	 */
	public static final String BANDS_FILE_PATH = "/WEB-INF/voting-definition.txt";

	/**
	 * Konstanta koja predstavlja primjerak razreda koji implementira sučelje
	 * {@link Comparator}. Ovaj komparator primjerke razreda {@link BandInfo}
	 * uspoređuje na temelju broja prikupljenih glasova
	 */
	public static final Comparator<BandInfo> BAND_INFO_COMPARATOR = (b1,
			b2) -> (int) Math.signum(b1.getVotes() - b2.getVotes());

	/**
	 * Konstanta koja predstavlja ekstenziju datoteke slike koja se stvara u
	 * okviru metode {@link #createPieChart(String, PieDataset)}
	 */
	private static final String IMAGE_EXTENSION = "png";

	/**
	 * Privatni konstruktor koji služi tome da se primjerci ovog razreda ne mogu
	 * stvarati izvan samog razreda.
	 */
	private ServletUtil() {
	}

	/**
	 * Metoda koja se koristi za stvaranje primjerka razreda {@link JFreeChart}
	 * koji predstavlja kružni dijagram. Podatke koji će se iscrtati kao i
	 * naslov kružnog dijagrama metoda dobiva kroz parametre <b>dataset</b> i
	 * <b>chartTitle</b>. Nakon što se uspješno stvori primjerak razreda
	 * {@link JFreeChart}, on se pretvara u sliku formata
	 * {@value #IMAGE_EXTENSION}.
	 *
	 * @param chartTitle
	 *            naslov koji će biti ispisan uz kružni dijagaram
	 * @param dataset
	 *            podaci iz kojih se generira kružni dijagram
	 * @return polje okteta koje predsatvlja sliku generiranog kružnog dijagrama
	 *         u formatu {@value #IMAGE_EXTENSION}
	 */
	public static byte[] createPieChart(String chartTitle, PieDataset dataset) {
		JFreeChart pieChart = ChartFactory.createPieChart3D(chartTitle, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) pieChart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(1.0f);

		return toImageBytes(pieChart);
	}

	/**
	 * Pomoćna metoda koja iz predanog parametra koji je primjerak razreda
	 * {@link JFreeChart} stvara polje okteta koje predstavlja sliku kružnog
	 * dijagrama formata {@value #IMAGE_EXTENSION}. Slika će biti široka 600, a
	 * visoka 500 piksela i ovaj podatak nije moguće mijenjati
	 *
	 * @param pieChart
	 *            primjerak razreda {@link JFreeChart} koji modelira kružni
	 *            dijagram koji se pretvara u sliku
	 * @return polje okteta koje predstavlja sliku kružnog dijagrama formata
	 *         {@value #IMAGE_EXTENSION}. Slika će biti široka 600, a visoka 500
	 *         piksela i ovaj podatak nije moguće mijenjati
	 */
	private static byte[] toImageBytes(JFreeChart pieChart) {
		BufferedImage bim = pieChart.createBufferedImage(600, 500);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			ImageIO.write(bim, IMAGE_EXTENSION, os);
		} catch (IOException e) {
			return null;
		}

		return os.toByteArray();
	}

	/**
	 * Metoda koja poziva metodu {@link #loadBandInfos(String, String)} sa
	 * parametrima {@link #BANDS_FILE_PATH} i {@link #VOTES_FILE_PATH}.
	 * Parametar koji metoda prima <b>context</b> služi za određivanje apsolutne
	 * putanje do datotka koje se učitavaju
	 *
	 * @param context
	 *            primjerak razreda koji implementira sučelje
	 *            {@link ServletContext}, a koji se koristi za određivanje
	 *            apsolutnih putanja do datoteka s glasovima i s opisima bendova
	 * @return {@link List} primjeraka razreda {@link BandInfo} koji su
	 *         parsirani iz datoteka predstavljenih relativnim putanjama
	 *         {@link #BANDS_FILE_PATH} i {@link #VOTES_FILE_PATH}
	 */
	public static List<BandInfo> loadDefaultBandInfos(ServletContext context) {
		return loadBandInfos(context.getRealPath(BANDS_FILE_PATH), context.getRealPath(VOTES_FILE_PATH));
	}

	/**
	 * Metoda koja prima dva parametra. Prvi parametar je <b>apsolutna</b>
	 * putanja do datoteke sa informacijama o bendovima <b>bandPathName</b>, a
	 * drugi parametar je <b>apsolutna</b> putanja do datoteke sa glasovima za
	 * svaki od pojedinih bendova <b>votesPathName</b>. Metoda potom ostatak
	 * posla delegira metodama {@link #parseBandInfo(String)} i
	 * {@link #parseBandVotes(List, String)} te vraća dobiveni rezultat
	 *
	 * @param bandPathName
	 *            <b>apsolutna</b> putanja do datoteke sa informacijama o
	 *            bendovima
	 * @param votesPathName
	 *            <b>apsolutna</b> putanja do datoteke sa glasovima za svaki od
	 *            pojedinih bendova
	 * @return {@link List} primjeraka razreda {@link BandInfo} koji su
	 *         parsirani iz datoteka predstavljenih apsolutnim putanjama
	 *         <b>bandPathName</b> i <b>votesPathName</b>
	 */
	public static List<BandInfo> loadBandInfos(String bandPathName, String votesPathName) {
		List<BandInfo> bandInfos = parseBandInfo(bandPathName);

		parseBandVotes(bandInfos, votesPathName);

		return bandInfos;
	}

	/**
	 * Metoda koja kao parametar prima <b>apsolutnu</b> putanju do datotke sa
	 * informacija o bendovima, otvara tu datoteku (ukoliko je u mogućnosti) te
	 * iz nje parsira {@link List}u primjeraka razreda {@link BandInfo}.
	 *
	 * @param filePath
	 *            <b>apsolutna</b> putanja do datoteke sa informacijama o
	 *            bendovima
	 * @return {@link List} primjeraka razreda {@link BandInfo} koji su
	 *         parsirani iz datoteka predstavljenih apsolutnom putanjom
	 *         <b>filePath</b>.
	 *         <p>
	 *         Napomena: ukoliko je datoteka prazna ili ukoliko datoteka ne
	 *         postoji ili ukoliko nije dopušteno njeno čitanje rezultat ove
	 *         naredbe biti će prazna lista
	 *         </p>
	 */
	public static List<BandInfo> parseBandInfo(String filePath) {
		List<BandInfo> bandInfos = new ArrayList<>();

		try {
			for (String line : Files.readAllLines(Paths.get(filePath))) {
				line = line.trim();
				String[] splitted = line.split("\t");

				if (splitted.length != 3) {
					continue;
				}

				bandInfos.add(
						new BandInfo(Integer.parseInt(splitted[0].trim()), splitted[1].trim(), splitted[2].trim()));
			}
		} catch (IOException igonrable) {
		}

		return bandInfos;
	}

	/**
	 * Metoda koja prima <b>apsolutnu</b> putanju do datotke s glasovima za
	 * pojedine bendove, parsira ju i ažurira broj glasova za sve bendove čiji
	 * su identifikatori spremljeni unutar ove datoteke. Ukoliko datoteka ne
	 * postoji ili nije dopušteno njeno čitanje neće biti ažurirani glasovi te
	 * će svi bendovi imati 0 glasova!
	 *
	 * @param bandInfos
	 *            {@link List} primjeraka razreda {@link BandInfo} kojima se
	 *            ažuriraju glasovi
	 * @param votesFileName
	 *            <b>apsolutna</b> putanja do datoteke sa glasovima za svaki od
	 *            pojedinih bendova
	 */
	public static void parseBandVotes(List<BandInfo> bandInfos, String votesFileName) {
		try {
			Path filePath = Paths.get(votesFileName);

			if (!Files.exists(filePath)) {
				createResults(bandInfos, filePath);
			}

			List<String> lines = Files.readAllLines(Paths.get(votesFileName));
			for (int i = 0, len = lines.size(); i < len; i++) {
				String line = lines.get(i).trim();
				String[] splitted = line.split("\t");

				if (splitted.length != 2) {
					continue;
				}
				Consumer<BandInfo> votesConsumer = bi -> bi.setVotes(Integer.parseInt(splitted[1].trim()));
				findAndConsume(bandInfos, Integer.parseInt(splitted[0].trim()), votesConsumer);
			}
		} catch (NumberFormatException | IOException e) {
		}
	}

	/**
	 * Metoda koja prima tri parametra. Prvi parametar predstavlja {@link List}
	 * primjeraka razreda {@link BandInfo} koji se pretražuju i nad kojima se
	 * ispituje uvjet, je li njihov identifikator jednak predanom
	 * parametru<b>id</b>. Samo nad onim primjercima za koje je uvjet zadovoljen
	 * poziva se {@link Consumer#accept(Object)} od predanog parametra
	 * <b>consumer</b>.
	 *
	 * @param bandInfos
	 *            {@link List} primjeraka razreda {@link BandInfo} koji se
	 *            pretražuju
	 * @param id
	 *            traženi identifikator primjeraka razreda {@link BandInfo}
	 * @param consumer
	 *            strategija koja implementira sučelje {@link Consumer} i koja
	 *            modelira ažuriranje primjerka razreda {@link BandInfo} koji
	 *            zadovoljava opisani uvjet
	 * 
	 * @see Consumer
	 */
	public static void findAndConsume(List<BandInfo> bandInfos, int id, Consumer<BandInfo> consumer) {
		bandInfos.stream()
						.filter(bi -> bi.getId() == id)
						.forEach(consumer);
	}

	/**
	 * Metoda koja se koristi za spremanje rezultata glasanja za bendove
	 * predstavljene primjercima razreda {@link BandInfo} predanih kroz
	 * parametar <b>bandInfos</b> u datoteku čija je putanja modelirana predanim
	 * primjerkom razreda koji implementira sučelje {@link Path}
	 *
	 * @param bandInfos
	 *            {@link List} primjeraka razreda {@link BandInfo} čiji se
	 *            identifikatori i glasovi spremaju u datoteku
	 * @param filePath
	 *            primjerak razreda koji implementira sučelje {@link Path} ,a
	 *            koji predstavlja putanja do datoteke u koju se spremaju
	 *            rezultati glasanja
	 */
	public static void createResults(List<BandInfo> bandInfos, Path filePath) {
		StringJoiner sj = new StringJoiner("\n");
		for (BandInfo bandInfo : bandInfos) {
			sj.add(String.format("%d\t%d", bandInfo.getId(), bandInfo.getVotes()));
		}
		try {
			Files.write(filePath, sj.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException ignorable) {
		}
	}
}
