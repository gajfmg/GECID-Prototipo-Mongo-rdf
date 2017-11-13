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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
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
import org.apache.jena.graph.Triple;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
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
                List<Document> schema = criarSchema(nomeColecao);
                HttpSession sessao = request.getSession();
                sessao.setAttribute("schema", schema);
                sessao.setAttribute("nomeSchema", nomeColecao);
                
                request.getRequestDispatcher("WEB-INF/VisualizarSchema.jsp").forward(request, response);
                
                }
                 case "Sim":{
                HttpSession sessao = request.getSession();
                String nomeColecao = (String) sessao.getAttribute("nomeSchema");
                String id = request.getParameter("id");
                //deletar registro
                dao.DeletarRegistro(nomeColecao, id);
                //relistar registros de schema
                List<Document> schema = criarSchema(nomeColecao);
                
                sessao.setAttribute("schema", schema);
                sessao.setAttribute("nomeSchema", nomeColecao);
                
                request.getRequestDispatcher("WEB-INF/VisualizarSchema.jsp").forward(request, response);
                
                }
                 case "excluirSchema":{
                HttpSession sessao = request.getSession();
                String nomeColecao = request.getParameter("colecao");
                
                //deletar coleção
                dao.DeletarColecao(nomeColecao);
                //atualizar lista de schemas
                List<String> colecoes = new ArrayList<>();
                colecoes = dao.ListarColecoes();
                sessao.setAttribute("colecoes", colecoes);
                request.getRequestDispatcher("WEB-INF/ExcluirSchema.jsp").forward(request, response);
                
                }
                case "Salvar":{
                HttpSession sessao = request.getSession();
                String nomeColecao = (String) sessao.getAttribute("nomeSchema");
                String id = request.getParameter("id");
                String labelatual = request.getParameter("labelAtual");
                //pegando dados do formulario de edição
                
                BasicDBObject registro = new BasicDBObject();
                registro.put("label",(String) request.getParameter("labelNovo"));
                registro.put("comentario", (String) request.getParameter("comentario"));
                registro.put("uri",(String) request.getParameter("uri"));
                /*
                registro.put("subclasse", new BasicDBObject("subclasses",(String) request.getParameter("subClasse")) );
                registro.put("superclasse",(String) request.getParameter("superClasse"));
                */
                //editar registro
                dao.EditarRegistro(nomeColecao, id,labelatual, registro);
                //relistar registros de schema
                List<Document> schema = criarSchema(nomeColecao);
                
                sessao.setAttribute("schema", schema);
                sessao.setAttribute("nomeSchema", nomeColecao);
                
                request.getRequestDispatcher("WEB-INF/VisualizarSchema.jsp").forward(request, response);
                
                }
                case "exportarJSON":{
                HttpSession sessao = request.getSession();
                String nomeColecao = request.getParameter("nomeColecao");
                String destino = request.getParameter("destino");
                
                String msg="";
                boolean exportar =dao.exportarParaJSON(destino, nomeColecao);
                
                if (exportar){
                msg = "Schema exportado com exito para : "+destino+"/"+nomeColecao;
                }
                sessao.setAttribute("msg", msg);
                
                request.getRequestDispatcher("WEB-INF/ExportarSchema.jsp").forward(request, response);
                
                
                }
                case"exportarRDF":{
                HttpSession sessao = request.getSession();
                String nomeColecao = request.getParameter("nomeColecao");
                String destino = request.getParameter("destino");
                
                String msg="vazio";
               
                boolean exportar =dao.exportarParaRDF(destino, nomeColecao);
                
                if (exportar){
                msg = "Schema exportado com exito para : "+destino+"/"+nomeColecao;
                }else{
                msg = "erro";
                }
                sessao.setAttribute("msg", msg);
                request.getRequestDispatcher("WEB-INF/ExportarSchema.jsp").forward(request, response);
                
                }
               }
                
    }

        public List<Document> criarSchema(String nomeColecao){
            MongoCursor<Document> cursor = dao.buscarSchema(nomeColecao).iterator();
            List<Document> Schema = new ArrayList<>();
            while(cursor.hasNext()){
                
                Document colecao = cursor.next();
                
                Schema.add(colecao);
                }
                return Schema;
            
        }
        /*public List<DBObject> gerarSchema(String nomeColecao){
            DBCursor cursor = dao.BuscaSchema(nomeColecao);
                 
            List<DBObject> Schema = new ArrayList<>();
                 
                while(cursor.hasNext()){
                DBObject colecao = cursor.next();
                
                Schema.add(colecao);
                }
                return Schema;
        }
        */
       
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
