/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicos;

import DAO.MongoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Robson
 */
@WebServlet(name = "Redirecionador", urlPatterns = {"/Redirec"})
public class Redirecionador extends HttpServlet {

    private static MongoDAO dao = new MongoDAO();
                
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String link = request.getParameter("link");
        
        switch (link){
            case "inicio":{
                request.getRequestDispatcher("index.html").forward(request, response);
                break;}
            case "carregarRDF":{
                request.getRequestDispatcher("WEB-INF/CarregarArquivo.jsp").forward(request, response);
                break;}
             case "visualizarSchema":{
                
                String nomeColecao = request.getParameter("colecao");
                
                request.getRequestDispatcher("WEB-INF/VisualizarSchema.jsp").forward(request, response);
                break;}
            case "selecionarSchema":{
               List<String> colecoes = new ArrayList<String>();
                colecoes = dao.ListarColecoes();
                
                HttpSession sessao = request.getSession();
                sessao.setAttribute("colecoes", colecoes);
                request.getRequestDispatcher("WEB-INF/SelecionarSchema.jsp").forward(request, response);
                break;}    
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
