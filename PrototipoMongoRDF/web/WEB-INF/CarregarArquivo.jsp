<%-- 
    Document   : CarregarArquivo
    Created on : 21/08/2017, 11:22:07
    Author     : Robson
--%>
<% 

String msg = (String)request.getAttribute("msg");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" type='text/css' href='CSS/estilo.css'>
        <title>Carregar arquivo</title>
    </head>
    <body>
        <div class='index-menu'>  <h1 class='titulo'>Carregar arquivo RDF</h1>
            <form method="POST" action="Controler?op=carregar" class="selecionar">
            Arquivo:
            <input type="file" name="arquivo" id="arquivo"  /> <br/><br/>
            <input type="submit" value="Enviar" name="Enviar" id="Enviar" class="bt" />
            
        </form>
            <% 
            if (msg != null){
            out.print("<p class='msg'>"+msg+"</p>");
            }
            %>
            <div class="nav">
            <p><a href="Redirec?link=inicio"> voltar oa inicio</a></p> 
            </div>
        </div>
    </body>
</html>
