package control;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderSicurezza implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inizializzazione del filtro, se necessario
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            // Aggiungi header di sicurezza
            httpResponse.setHeader("X-Frame-Options", "DENY");
            httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
            httpResponse.setHeader("X-Content-Type-Options", "nosniff");
            httpResponse.setHeader("Content-Security-Policy", "default-src 'self'");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Pulizia del filtro, se necessario
    }
}
