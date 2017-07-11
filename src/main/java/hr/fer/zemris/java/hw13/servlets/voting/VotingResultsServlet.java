package hr.fer.zemris.java.hw13.servlets.voting;

import java.io.IOException;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

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
 * otvaraju datoteku s opisima pojedinih bendova i datoteku sa rezultatima
 * glasovanja za pojedine bendove te iz njih stvaraju {@link List} primjeraka
 * razreda {@link BandInfo}. Potom metoda iz parsirane liste filtrira sve
 * primjerke razreda {@link BandInfo} koji imaju najveći broj glasova u zasebnu
 * {@link List}u. Ove dvije liste metoda postavlja kao atribute zahtjeva i
 * generiranje HTML dokumenta prepušta JSP datoteci
 * "/WEB-INF/pages/votingRes.jsp".
 * 
 * @see HttpServlet
 * @see BandInfo
 * 
 * @author Davor Češljaš
 */
@WebServlet(name = "voting-results", urlPatterns = { "/glasanje-rezultati", "/voting-results" })
public class VotingResultsServlet extends HttpServlet {

	/**
	 * Konstanta koja se koristi prilikom serijalizacije objekata ovog razreda
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<BandInfo> bandInfos = ServletUtil.loadDefaultBandInfos(request.getServletContext());

		bandInfos.sort(ServletUtil.BAND_INFO_COMPARATOR.reversed());
		request.setAttribute("bandInfos", bandInfos);

		List<BandInfo> winners = findWinners(bandInfos);
		request.setAttribute("winners", winners);

		request.getRequestDispatcher("/WEB-INF/pages/votingRes.jsp").forward(request, response);
	}

	/**
	 * Pomoćna metoda koja iz predanog parametra <b>bandInfo</b> dohvaća
	 * reference na one bendove modelirane primjerkom razreda {@link BandInfo}
	 * koji su skupili najveći broj glasova. Dohvaćene reference sprema u novu
	 * {@link List}u i tu listu vraća pozivatelju.
	 *
	 * @param bandInfos
	 *            {@link List} primjeraka razreda {@link BandInfo} iz koje se
	 *            dohvaćaju pobjednici
	 * @return nova {@link List} primjeraka razreda {@link BandInfo} koja sadrži
	 *         bendove pobjednike
	 */
	private List<BandInfo> findWinners(List<BandInfo> bandInfos) {
		int max = findMaxVotes(bandInfos);
		return bandInfos.stream().filter(bi -> bi.getVotes() == max).collect(Collectors.toList());
	}

	/**
	 * Pomoćna metoda koja traži najveći broj glasova koje neki primjerak
	 * razreda {@link BandInfo} iz predane liste ima spremljeno. Potom taj broj
	 * glasova vraća pozivatelju. Maksimalni broj glasova biti će 0 ukoliko
	 * unutar predanog parametra <b>bandInfos</b> ne postoji niti jedan
	 * primjerak razreda {@link BandInfo}, drugim riječima ukoliko je predana
	 * lista prazna
	 *
	 * @param bandInfos
	 *            {@link List} primjeraka razreda {@link BandInfo} iz koje se
	 *            dohvaća najveći broj glasova
	 * @return najveći broj glasova koje je neki bend (ili više njih) uspio
	 *         skupiti
	 */
	private int findMaxVotes(List<BandInfo> bandInfos) {
		OptionalInt optMax = bandInfos.stream().mapToInt(bi -> bi.getVotes()).max();

		if (optMax.isPresent()) {
			return optMax.getAsInt();
		}

		return 0;
	}

}
