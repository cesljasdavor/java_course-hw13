package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Razred koji nasljeđuje sučelje {@link ServletContextListener}. Cilj primjerka
 * ovog razreda je da prilikom inicijalizacije čitave aplikacije (metoda
 * {@link #contextInitialized(ServletContextEvent)}) ,a unutar atributa
 * stvorenog primjerka razreda koji implementira {@link ServletContext} pohrani
 * vrijeme inicijalizacije. Na taj način unutar aplikacije moguće je izračunati
 * koliko dugo aplikacija radi. Taj postupak računanja prepušten je
 * "appinfo.jsp" datoteci.
 * 
 * @see ServletContextListener
 * @see AppInfoEvent
 * 
 * @author Davor Češljaš
 */
@WebListener
public class AppInfoListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
