<%-- 
    Document   : VisualizarSchema
    Created on : 22/08/2017, 17:53:46
    Author     : Robson
--%>
<%@page import="java.util.List"%>
<%@page import="com.mongodb.DBObject"%>
<%@page import="DAO.MongoDAO"%>
<% 
        HttpSession sessao = request.getSession();
        MongoDAO dao = new MongoDAO();
        List<DBObject> schema = (List<DBObject>) sessao.getAttribute("schema");
        

%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" type='text/css' href='CSS/estilo.css'>
        <title>Visualizar Schema</title>
    </head>
    <body>
        <div class="conteudo">
        <h1 class="titulo"><% out.print(sessao.getAttribute("nomeSchema")); %></h1>
        <p><a href="Redirec?link=inicio"> voltar oa inicio</a></p>
        <form method="post" action="Controler">
            <% 
            for (int i = 0 ; i < schema.size() ; i++){
            DBObject atual = schema.get(i);
            int css = 0;
            css = i % 2;
            
            out.print("<div class='linha"+css+"'>");
            out.print("<input type ='hidden' name='id' value ='"+atual.get("_id")+"'//>");
            out.print("<input type ='hidden' name='labelClasse' value ='"+atual.get("label")+"'//>");
            out.print("<p>Label Classe: "+ atual.get("label")+"</p>");
            
            out.print("<input type ='hidden' name='comentario' value ='"+atual.get("comentario")+"'//>");
            out.print("<p> Comentário: "+ atual.get("comentario")+"</p>");
            
            out.print("<input type ='hidden' name='uri' value ='"+atual.get("uri")+"'//>");
            out.print("<p> URI: "+ atual.get("uri")+"</p>");
            
            DBObject sc = (DBObject) atual.get("subclasse");
            out.print("<input type ='hidden' name='subclasse' value ='"+sc.get("subclasses")+"'//>");
            out.print("<p> Subclasse: "+sc.get("subclasses")+"</p>");
            
            out.print("<input type ='hidden' name='superClasse' value ='"+atual.get("classe_definicao")+"'//>");
            out.print("<p> Super classe: "+ atual.get("classe_definicao")+"</p>");
            out.print("<p>Modificar Schema :<input type='radio' name='op' value='formEditar' id ='op' //><label for='op'>Editar</label> ");
            out.print("<input type='radio' name='op' value='excluirRegistro' id ='op' //><label for='op'>Excluir</label> ");
            
            out.print("<input type='submit' name='operação' value='Realizar' id ='operacao' // ></p>");
            out.print("</div>");
            out.print("</form> ");
        %>
         <div id='pop'>
            <div class="formeditar">
            <h1 class="titulo">Editar Registro do Schema :<% out.print(sessao.getAttribute("nomeSchema")); %></h1>
            <form method="post" action ="Controler" id='formEditar'>
            <input type='hidden' name='id' value ='<% out.print(atual.get("_id")); %>'/>
                    <label>Nome da classe :</label> <input type ='text' name='labelClasse' value ="<% out.print(atual.get("label")); %>" size="40"/> </p>
                   <div class="nav-form-edita">
                   <p> <input type ="button" value ="Cancelar" onClick="history.go(-1)" class="bt" /> <input type ='reset' name='limpar' value ="Limpar"class="bt"/>  <input type ='submit' name='op' value ='Salvar' class="bt"/></p>
                   </div>
            </form>
          <div class="nav">
            <p><a href="Redirec?link=inicio"> voltar oa inicio</a></p> 
              <a href="#" onclick="document.getElementById('pop').style.display='none';">Mostra</a>
      
            </div>
        </div>
        </div>
        
        <a href="#" onclick="document.getElementById('pop').style.display='block';">Mostra</a>
          <%      
                }
            %>
         
        
    </div>
    </body>
</html>
