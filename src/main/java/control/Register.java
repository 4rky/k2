package control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DriverManagerConnectionPool;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String indirizzo = request.getParameter("indirizzo");
        String telefono = request.getParameter("telefono");
        String carta = request.getParameter("carta");
        String intestatario = request.getParameter("intestatario");
        String cvv = request.getParameter("cvv");
        String redirectedPage = "/loginPage.jsp";
        try {
            Connection con = DriverManagerConnectionPool.getConnection();
            
            // Hashing della password con SHA-256
            String hashedPassword = hashPassword(password);

            // Query per aggiungere l'utente a UserAccount
            String sql = "INSERT INTO UserAccount(email, passwordUser, nome, cognome, indirizzo, telefono, numero, intestatario, CVV) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, hashedPassword); // Password già hashata
            ps.setString(3, nome);
            ps.setString(4, cognome);
            ps.setString(5, indirizzo);
            ps.setString(6, telefono);
            ps.setString(7, carta);
            ps.setString(8, intestatario);
            ps.setString(9, cvv);
            
            // Esegue l'aggiornamento
            ps.executeUpdate();
            con.commit();

            // Query per aggiungere l'utente a Cliente
            String sql2 = "INSERT INTO Cliente(email) VALUES (?)";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, email);
            ps2.executeUpdate();
            con.commit();

            // Query per aggiungere l'utente a Venditore
            String sql3 = "INSERT INTO Venditore(email) VALUES (?)";
            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.setString(1, email);
            ps3.executeUpdate();
            con.commit();

            // Rilascia la connessione
            DriverManagerConnectionPool.releaseConnection(con);
        } catch (SQLException e) {
            // Gestione degli errori di SQL
            e.printStackTrace();
            request.getSession().setAttribute("register-error", true);
            redirectedPage = "/register-form.jsp";
        }
        // Reindirizza alla pagina appropriata
        response.sendRedirect(request.getContextPath() + redirectedPage);
    }

    /**
     * Metodo per l'hashing della password con SHA-256
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Converte i byte hashati in formato esadecimale
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore durante l'hashing della password", e);
        }
    }
}
