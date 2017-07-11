package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.util.BandInfo;
import hr.fer.zemris.java.hw13.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * pozivom metode {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * otvaraju datoteku s opisima pojedinih bendova i datoteku sa rezultatima
 * glasovanja za pojedine bendove te iz njih stvaraju {@link List} primjeraka
 * razreda {@link BandInfo}. Iz parsirane liste metoda generira .xls datoteku.
 * <p>
 * Ova datoteka imati će samo jednu stranicu na kojoj će biti četiri stupca.
 * Prvi stupac će predstavljati identifikator benda, drugi stupac će
 * predstavljati naziv benda, treći će predstavljati link na najpoznatiju pjesmu
 * tog benda, a četvrti će predstavljati broj glasova.
 * </p>
 * 
 * @see HttpServlet
 * @see BandInfo
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "voting-xls", urlPatterns = { "/glasanje-xls", "voting-xls" })
public class VotingXLSServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Konstanta koja predstavlja poziciju u redku na kojoj se nalazi
	 * identifikator benda
	 */
	private static final int ID_INDEX = 0;

	/**
	 * Konstanta koja predstavlja poziciju u redku na kojoj se nalazi naziv
	 * benda
	 */
	private static final int BAND_INDEX = 1;

	/**
	 * Konstanta koja predstavlja poziciju u redku na kojoj se nalazi link na
	 * najpoznatiju pjesmu benda
	 */
	private static final int REPRESSONG_INDEX = 2;

	/**
	 * Konstanta koja predstavlja poziciju u redku na kojoj se nalazi
	 * broj glasova za bend
	 */
	private static final int VOTES_INDEX = 3;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<BandInfo> bandInfos = ServletUtil.loadDefaultBandInfos(request.getServletContext());

		response.setContentType("application/vnd.ms-excel");
		response.getOutputStream().write(createVotingXLS(bandInfos));
	}

	/**
	 * Pomoćna metoda koja generira .xls datoteku sa rezultatima glasanja iz
	 * predanog parametra <b>bandInfos</b>. Ova datoteka imati će samo jednu
	 * stranicu na kojoj će biti četiri stupca. Prvi stupac će predstavljati
	 * identifikator benda, drugi stupac će predstavljati naziv benda, treći će
	 * predstavljati link na najpoznatiju pjesmu tog benda, a četvrti će
	 * predstavljati broj glasova.
	 * 
	 *
	 * @param bandInfos
	 *            {@link List} primjeraka razreda {@link BandInfo} iz koje se
	 *            stvara .xls datoteka
	 * @return polje okteta koje predstavlja stvorenu .xls datoteku
	 */
	private byte[] createVotingXLS(List<BandInfo> bandInfos) {
		try (HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Voting results");
			createFirstRow(sheet);
			for (int row = 1, noOfRows = bandInfos.size(); row <= noOfRows; row++) {
				createEntry(bandInfos.get(row - 1), sheet.createRow(row));
			}

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);

			return os.toByteArray();
		} catch (IOException ignorable) {
		}
		return null;
	}

	/**
	 * Pomoćna metoda koja stvara jedan redak (koji predstavlja podatke za jedan
	 * bend) .xls dokumenta. Detaljniji opis svakog stupca moguće je pronaći u
	 * dokumentaciji ovog razreda
	 *
	 * @param bandInfo
	 *            primjerak razreda {@link BandInfo} iz kojeg se stvara redak
	 * @param row
	 *            primjerak razreda {@link HSSFRow} koji modelira jedan redak
	 *            stranice .xls dokumenta
	 */
	private void createEntry(BandInfo bandInfo, HSSFRow row) {
		row.createCell(ID_INDEX).setCellValue(String.valueOf(bandInfo.getId()));
		row.createCell(BAND_INDEX).setCellValue(bandInfo.getName());
		row.createCell(REPRESSONG_INDEX).setCellValue(bandInfo.getRepresSong());
		row.createCell(VOTES_INDEX).setCellValue(String.valueOf(bandInfo.getVotes()));
	}

	/**
	 * Pomoćna metoda koja stvara prvi redak datoteke. U prvom redku navedeni su
	 * opisi pojedinih stupaca. Tako će u prvom stupcu biti zapisan "ID", u
	 * drugom "Band" u trećem "Representativ song", a u četvrtom "Number of
	 * votes". Za detaljnija značenja pojedinih stupaca korisnika se navodi na
	 * dokumentaciju ovog razreda.
	 *
	 * @param sheet
	 *            primjerak razreda {@link HSSFSheet} koji modelira jednu
	 *            stranicu .xls datoteke
	 */
	private void createFirstRow(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);

		row.createCell(ID_INDEX).setCellValue("ID");
		row.createCell(BAND_INDEX).setCellValue("Band");
		row.createCell(REPRESSONG_INDEX).setCellValue("Representativ song");
		row.createCell(VOTES_INDEX).setCellValue("Number of votes");
	}

}
