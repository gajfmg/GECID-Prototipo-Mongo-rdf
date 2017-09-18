<%-- 
    Document   : EditarSchema
    Created on : 24/08/2017, 10:08:39
    Author     : Robson
--%>
<%  HttpSession sessao = request.getSession();
        
    String id = (String)request.getAttribute("id");
    String label = (String)request.getAttribute("labelClasse");
    String comentario = (String)request.getAttribute("comentario");
    String uri = (String)request.getAttribute("uri");
    String subclasse = (String)request.getAttribute("subClasse");
    String superclasse = (String)request.getAttribute("superClasse");
    

%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type='text/css' href='CSS/estilo.css'>
       
        <title>JSP Page</title>
    </head>
    <body>
        <div id='pop'>
        <div class="formeditar">
        <h1 class="titulo">Editar Registro do Schema :<% out.print(sessao.getAttribute("nomeSchema")); %></h1>
        <form method="post" action ="Controler" id='formEditar'>
                    <input type='hidden' name='id' value ='<% out.print(id); %>'/>
                    <label>Nome da classe :</label> <input type ='text' name='labelClasse' value ="<% if(label != null){ out.print(label); }%>" size="40"/> </p>
        <label>Comentário :</label> <br/> <p style='margin-left: 40px;'><textarea rows ='4' cols="49" name='comentario' form = 'formEditar'> <% if(comentario != null){ out.print(comentario);} %>" </textarea> </p> 
                   <label>URI :</label> <input type ='text' name='uri' value ="<% if(uri != null){ out.print(uri);} %>" size="57"/> </p>
                   <label>Sub classe :</label> <input type ='text' name='subClasse' value ="<% if(subclasse != null){ out.print(subclasse);} %>" size="47" /> </p>
                   <label>Super classe :</label> <input type ='text' name='superClasse' value ="<% if(superclasse != null){ out.print(superclasse); }%>" size="44" /> </p>
                   <div class="nav-form-edita">
                   <p> <input type ="button" value ="Cancelar" onClick="history.go(-1)" class="bt" /> <input type ='reset' name='limpar' value ="Limpar"class="bt"/>  <input type ='submit' name='op' value ='Salvar' class="bt"/></p>
                   </div>
        </form>
          <div class="nav">
            <p><a href="Redirec?link=inicio"> voltar oa inicio</a></p> 
            </div>
        </div>
        </div>
    </body>
</html>
