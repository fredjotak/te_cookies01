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
            mensaje = "Gracias por visitar nuestra Página";
            
        } else {
            mensaje = "Estamos agradecidos por tenerlo nuevamente";
        }
        
        response.setContentType("text/html; charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("<h1>"+mensaje+"</h1>");
        out.println("<a href='index.jsp'>Volver al inicio</a>");
    }
    

}
