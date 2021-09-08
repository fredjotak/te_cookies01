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
            
            // Cookie para conmtrolar el número de Visitas
            Cookie cooNroVisitas = new Cookie("contador", "1"); // momento que se crea : visita 1
            cooNroVisitas.setMaxAge(120); // también durará 120 segundos
            cooNroVisitas.setComment("Control del número de visitas");
            
            // Añadir los cookieNuevo al response
            response.addCookie(cooNuevo);
            response.addCookie(cooNroVisitas);
            
            mensaje = "<div class=\"caja fondo-rojo\">\n" +
"                <p>Bienvenido, gracias por realizar tu primera visita a nuestro sitio.</p>\n" +
"            </div>";
            
        } else {
            // Recorrer el cookie
            int nroVisita = 0;
            for(Cookie coc : cookies){
                if(coc.getName().equals("contador")){
                    try {
                        nroVisita = Integer.parseInt(coc.getValue());
                        // System.out.println("Vale --> "+nroVisita);
                        nroVisita++; // Incrementmos la visita en + 1
                        
                        // Sobreescribimos la cookie 
                        Cookie cooNroVisitas = new Cookie("contador", nroVisita+"");
                        cooNroVisitas.setMaxAge(120); // también dura 120 segundos, pero se reinicia
                        cooNroVisitas.setComment("Control del número de visitas");
                        
                        // Añadimos nuevamnente la cookie actualizada
                        response.addCookie(cooNroVisitas);
                        
                    } catch(NumberFormatException e){
                        System.out.println("Ocurrio un error en la conversión");
                    }
                    break;
                }
            }
            mensaje = "<div class=\"caja fondo-verde\">\n" +
"                <p>Gracias por confiar de nuevo en nuestro sitio</p>\n" +
"                <div class=\"visita\">visita nro. "+nroVisita+"</div>\n" +
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
