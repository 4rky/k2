package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VenditaS")
public class VenditaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeProdotto = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzo = request.getParameter("prezzo");
        String spedizione = request.getParameter("spedizione");

        // Validazione dei campi lato server
        if (nomeProdotto != null && nomeProdotto.matches(".*[<>/\\\"].*")) {
            response.getWriter().println("Errore: Il nome del prodotto contiene caratteri non ammessi.");
            return;
        }
        
        if (descrizione != null && descrizione.matches(".*[<>/\\\"].*")) {
            response.getWriter().println("Errore: La descrizione contiene caratteri non ammessi.");
            return;
        }

        try {
            double prezzoDouble = Double.parseDouble(prezzo);
            if (prezzoDouble <= 0) {
                response.getWriter().println("Errore: Il prezzo deve essere un numero positivo.");
                return;
            }
            
            double spedizioneDouble = Double.parseDouble(spedizione);
            if (spedizioneDouble < 0) {
                response.getWriter().println("Errore: Il costo di spedizione deve essere un numero positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Errore: Prezzo o spese di spedizione non validi.");
            return;
        }

        // Proseguire con l'elaborazione del prodotto
        // Ad esempio, salvarlo nel database
        
        response.getWriter().println("Prodotto aggiunto con successo!");
    }
}
