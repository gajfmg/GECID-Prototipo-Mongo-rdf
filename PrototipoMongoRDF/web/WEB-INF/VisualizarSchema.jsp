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
        %>    
            <div class='<% out.print("linha"+css); %>'>
            <input type ='hidden' name='id' value =' <% out.print(atual.get("_id")); %>'/>
            <input type ='hidden' name='labelClasse' value ='<% out.print(atual.get("label")); %>'/>
            <p> <label>Label Classe: <% out.print(atual.get("label")); %> </label> </p>
            <input type ='hidden' name='comentario' value ='<% out.print(atual.get("comentario")); %>'/>
               <p> <label>Comentário: <% out.print(atual.get("comentario")); %> </label> </p>
            <input type ='hidden' name='uri' value ='<% out.print(atual.get("uri")); %>'/>
                   <label>Comentário: <% out.print(atual.get("uri")); %> </label> </p>
            <% 
            DBObject sc = (DBObject) atual.get("subclasse");
            out.print("<input type ='hidden' name='subclasse' value ='"+sc.get("subclasses")+"'//>");
            out.print("<p><label> Subclasse: "+sc.get("subclasses")+"</label></p>");
            %>
            
            <input type ='hidden' name='SuperClasse' value ='<% out.print(atual.get("classe_definicao")); %>'/>
               <p><label>Comentário: <% out.print(atual.get("classe_definicao")); %> </label> </p>
            
            <p><input type ="button" class ="bt" name="editar" value ="Editar" onclick="document.getElementById('<% out.print(atual.get("_id")); %>').style.display='block';"> 
                <input type ="button" class ="bt" name="operacao" value ="Excluir Registro" onclick="confirmar())"> </p>
            </form>
  
            </div>
       <script>
function confirmar() {
    confirm("Tem certeza de que deseja Excluir o registro do Schema ?");
}
</script>        
         <div id = "<% out.print(atual.get("_id")); %>" class="pop">
                            <h1 class="titulo">Editar Registro do Schema :<% out.print(sessao.getAttribute("nomeSchema")); %></h1>
                            <form method="post" action ="Controler" id='formEditar'>
                            <input type='hidden' name='id' value ='<% out.print(atual.get("_id")); %>'/>
                                    <label>Nome da classe :</label> <input type ='text' name='editclasse' value ="<% if (atual.get("label") != null){out.print(atual.get("label"));} %>" size="40"/> </p>
                                    <label>Cometário :</label> <input type ='text' name='editclasse' value ="<% if (atual.get("comentario") != null){out.print(atual.get("comentario"));} %>" size="40"/> </p>
                                    <label>URI :</label> <input type ='text' name='editclasse' value ="<% if (atual.get("uri") != null){out.print(atual.get("uri"));} %>" size="40"/> </p>
                                    <label>Subclasse :</label> <input type ='text' name='editclasse' value ="<% if (sc.get("subclasses") != null){out.print(sc.get("subclasses"));} %>" size="40"/> </p>
                                    <label>Super classe :</label> <input type ='text' name='editclasse' value ="<% if (atual.get("classe_definicao") != null){out.print(atual.get("classe_definicao"));} %>" size="40"/> </p>
                           
                            <div class="nav-form-edita">
                            <p> <input type ="button" class ="bt" name="cancelar" value ="Cancelar" onclick="document.getElementById('<% out.print(atual.get("_id")); %>').style.display='none';"> <input type ='reset' name='limpar' value ="Limpar"class="bt"/>  <input type ='submit' name='op' value ='Salvar' class="bt"/></p>
                            </div>

                            </form>
                           
                           
                            
            </div>  
        
    <% }
    %> 
        </div> 
    </body>
</html>
