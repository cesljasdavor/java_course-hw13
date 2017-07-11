package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.util.BandInfo;
import hr.fer.zemris.java.hw13.util.ServletUtil;

/**
 * Razred koji nasljeđuje razred {@link HttpServlet}. Primjerci ovog razreda
 * pozivom metode {@link #doGet(HttpServletRequest, HttpServletResponse)}
 * ažuriraj broj glasova bendu čiji je identifikator predan kao klijentov
 * parametar "id". Ažuriranje se neće dogoditi ukoliko vrijednost u ključ "id"
 * nije cijeli broj ili ukoliko taj cijeli broj nije jedan od poznatih
 * identifikatora bendova
 * 
 * @see HttpServlet
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "voting-vote", urlPatterns = { "/voting-vote", "/glasanje-glasaj" })
public class VotingVoteServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filePath = request.getServletContext().getRealPath("/WEB-INF/voting-results.txt");
		List<BandInfo> bandInfos = (List<BandInfo>) request.getSession().getAttribute("bandInfos");

		if (bandInfos == null) {
			request.getRequestDispatcher("WEB-INF/pages/voting-error.jsp").forward(request, response);
			return;
		}

		addVoteAndRedirect(request, response, bandInfos, filePath);
	}

	/**
	 * Pomoćna metoda koja dodaje glas bendu čiji je identifikator poslan kroz
	 * klijentov parametra "id". Ukoliko vrijednost u ključ "id" nije cijeli
	 * broj ili ukoliko taj cijeli broj nije jedan od poznatih identifikatora
	 * bendova unutar predane {@link List}e primjeraka razreda {@link BandInfo}
	 * metoda jednostavno klijenta preusmjerava na stranicu "/voting-results". U
	 * protivnom metoda prije preusmjeravanja ažurira broj glasova koje je band
	 * sa tim identifikatorom dobio
	 *
	 * @param request
	 *            predstavlja primjerak sučelja {@link HttpServletRequest} koji
	 *            modelira zahtjev korisnika
	 * @param response
	 *            predstavlja primjerak sučelja {@link HttpServletResponse} koji
	 *            modelira odgovor koji poslužitelj šalje klijentu
	 * @param bandInfos
	 *            {@link List} primjeraka razreda {@link BandInfo} koji
	 *            modeliraju informacije i broj glasova o bendu
	 * @param filePath
	 *            <b>apsolutna</b> putanja do datoteke sa glasovima za svaki od
	 *            pojedinih bendova
	 * @throws IOException
	 *             ukoliko se generiranje odgovora ne može prusmjeriti
	 */
	private void addVoteAndRedirect(HttpServletRequest request, HttpServletResponse response, List<BandInfo> bandInfos,
			String filePath) throws IOException {
		ServletUtil.parseBandVotes(bandInfos, filePath);

		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Consumer<BandInfo> voteAppender = bi -> bi.setVotes(bi.getVotes() + 1);

			ServletUtil.findAndConsume(bandInfos, id, voteAppender);
		} catch (NumberFormatException ignorable) {
		}

		ServletUtil.createResults(bandInfos, Paths.get(filePath));

		response.sendRedirect(request.getContextPath() + "/voting-results");
	}
}
