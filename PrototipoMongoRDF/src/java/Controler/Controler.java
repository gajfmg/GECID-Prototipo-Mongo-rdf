/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import DAO.MongoDAO;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.bson.Document;

/**
 *
 * @author Robson
 */
@WebServlet(name = "Controler", urlPatterns = {"/Controler"})
public class Controler extends HttpServlet {
    
    private static MongoDAO dao = new MongoDAO();
    private static String msg = null;    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        
            String operacao=request.getParameter("op");
            switch(operacao){
                case "carregar":{
                String url = request.getParameter("arquivo");
                int verificador = dao.CarregarRDF(url);
                String msg = null;    
                    if(verificador == 0){
                        msg = "Problema na leitura do conteudo do Schema.";
                        request.setAttribute("msg", msg);
                        request.getRequestDispatcher("WEB-INF/CarregarArquivo.jsp").forward(request, response);
                    }else if(verificador == 1){
                        msg = "O Schema já existe.";
                        request.setAttribute("msg", msg);
                        request.getRequestDispatcher("WEB-INF/CarregarArquivo.jsp").forward(request, response);
                    }else if(verificador == 2){
                        msg = "Schema salvo com exito.";
                        request.setAttribute("msg", msg);
                        request.getRequestDispatcher("WEB-INF/CarregarArquivo.jsp").forward(request, response);
                    }else {
                    msg = "Erro de url";
                        request.setAttribute("msg", msg);
                        request.getRequestDispatcher("WEB-INF/CarregarArquivo.jsp").forward(request, response);
                    
                    }
                break; 
                
                }
                case "visualizarSchema":{
                String nomeColecao = request.getParameter("colecao");
                List<DBObject> schema = gerarSchema(nomeColecao);
                HttpSession sessao = request.getSession();
                sessao.setAttribute("schema", schema);
                sessao.setAttribute("nomeSchema", nomeColecao);
                
                request.getRequestDispatcher("WEB-INF/VisualizarSchema.jsp").forward(request, response);
                
                }
                 case "excluirRegistro":{
                HttpSession sessao = request.getSession();
                String nomeColecao = (String) sessao.getAttribute("nomeSchema");
                String id = request.getParameter("id");
                //deletar registro
                dao.DeletarRegistro(nomeColecao, id);
                //relistar registros de schema
                List<DBObject> schema = gerarSchema(nomeColecao);
                
                sessao.setAttribute("schema", schema);
                sessao.setAttribute("nomeSchema", nomeColecao);
                
                request.getRequestDispatcher("WEB-INF/VisualizarSchema.jsp").forward(request, response);
                
                }
                case "Salvar":{
                HttpSession sessao = request.getSession();
                String nomeColecao = (String) sessao.getAttribute("nomeSchema");
                String id = request.getParameter("id");
                //pegando dados do formulario de edição
                
                BasicDBObject registro = new BasicDBObject();
                registro.put("label",(String) request.getParameter("labelClasse"));
                registro.put("comentario", (String) request.getParameter("comentario"));
                registro.put("uri",(String) request.getParameter("uri"));
                registro.put("subclasse", new BasicDBObject("subclasses",(String) request.getParameter("subClasse")) );
                registro.put("classe_definicao",(String) request.getParameter("superClasse"));
                
                //editar registro
                dao.EditarRegistro(nomeColecao, id, registro);
                //relistar registros de schema
                List<DBObject> schema = gerarSchema(nomeColecao);
                
                sessao.setAttribute("schema", schema);
                sessao.setAttribute("nomeSchema", nomeColecao);
                
                request.getRequestDispatcher("WEB-INF/VisualizarSchema.jsp").forward(request, response);
                
                }
                case "formEditar":{
                HttpSession sessao = request.getSession();
                String nomeColecao = (String) sessao.getAttribute("nomeSchema");
                String id = request.getParameter("id");
                //pegandoo dados do registro a ser editado
                BasicDBObject registro = new BasicDBObject();
                registro.put("label", request.getParameter("labelClasse"));
                registro.put("comentario", request.getParameter("comentario"));
                registro.put("uri", request.getParameter("uri"));
                registro.put("subclasse", new BasicDBObject("subclasses", request.getParameter("subclasse") ));
                registro.put("classes_definicao", request.getParameter("superClasse"));
                // enviando dados para serem exibidos no formulario
                request.setAttribute("id", id);
                request.setAttribute("labelClasse", registro.get("label"));
                request.setAttribute("comentario", registro.get("comentario"));
                request.setAttribute("uri", registro.get("uri"));
                request.setAttribute("subClasse", registro.get("subClasse"));
                request.setAttribute("superClasse", registro.get("classe_definicao"));
                
                request.getRequestDispatcher("WEB-INF/EditarSchema.jsp").forward(request, response);
                
                }
               }
                
    }


        public List<DBObject> gerarSchema(String nomeColecao){
        
        DBCursor cursor = dao.BuscaSchema(nomeColecao);
                 List<DBObject> Schema = new ArrayList<>();
                while(cursor.hasNext()){
                DBObject colecao = cursor.next();
                
                Schema.add(colecao);
                }
                return Schema;
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
