package com.emergentes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CookieServlet", urlPatterns = {"/CookieServlet"})
public class CookieServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje = null;
        boolean nuevaVisita = true;
        
        // Obtener el arreglo de cookies
        Cookie[] cookies = request.getCookies();
        
        if(cookies != null){
            for(Cookie coo : cookies){
                if(coo.getName().equals("visitante") && coo.getValue().equals("SI")){
                    nuevaVisita = false;
                    break;
                }
            }
        }
        
        // Si es un visitante nuevo
        if(nuevaVisita==true){
            Cookie cooNuevo = new Cookie("visitante", "SI");
            cooNuevo.setMaxAge(120); // durara 120 segundos
            cooNuevo.setComment("Control de nuevos vistantes");
            
            // Añadir el cookieNuevo al response
            response.addCookie(cooNuevo);
            mensaje = "<div class=\"caja fondo-rojo\">\n" +
"                <p>Bienvenido, gracias por realizar tu primera visita a nuestro sitio.</p>\n" +
"            </div>";
            
        } else {
            mensaje = "<div class=\"caja fondo-verde\">\n" +
"                <p>Gracias por confiar de nuevo en nuestro sitio</p>\n" +
"            </div>";
        }
        
        response.setContentType("text/html; charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<link rel=\"stylesheet\" href=\"css/estilos.css\">");
        out.println("<title>Visitas a Cookies</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<section>");
        out.println("<h1>Visitas a Cookies</h1>");
        out.println(mensaje);
        out.println("<p><a href='index.jsp'>Vover al inicio</a></p>");
        out.println("</section>");
        out.println("<footer>7 de Septiembre del 2021</footer>");
        out.println("</body>");
        out.println("</html>");
    }
    

}
