<%-- 
    Document   : SelecionarSchema
    Created on : 22/08/2017, 17:45:22
    Author     : Robson
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList"%>
<%@page import="DAO.MongoDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    
                HttpSession sessao = request.getSession();
                Object Scolecao = sessao.getAttribute("colecoes");
                
                List<String> colecoes = new ArrayList<String>();
                colecoes = (List<String>) Scolecao;
                
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" type='text/css' href='CSS/estilo.css'>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="index-menu">
        
            <h1 class="titulo">Visualizar Schema RDF</h1>
            <form method="POST" action="Controler?op=visualizarSchema" class="selecionar">
            <p> Selecione o Schema :
                        <select name='colecao'>
                                                
                        <option value = ''>Selecionar</option>
                        <% 
                        int i = 0 ;
                        while( i < colecoes.size()) {
                                
                                String col = colecoes.get(i);
                                if (col.equals("Usuarios")){
                                i++;
                                }else{
                                    out.print("<option value = '"+col+"'>"+col+"</option>");
                                    i++;
                                }
                        }
                        %>                       
                                                
                        </select>
                         <input type="submit" value="Visualizar" name="Visualizar" id="Visualizar" class="bt"/>
       
                        </p>
               </form>
              <div class="nav">
            <p><a href="Redirec?link=inicio"> voltar oa inicio</a></p> 
            </div>
        </div>
          
    </body>
</html>
