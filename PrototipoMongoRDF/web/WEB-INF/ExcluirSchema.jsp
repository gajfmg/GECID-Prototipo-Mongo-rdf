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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>GECON</title>
    <link href="layout/css/estilo.css" rel="stylesheet">

    <!-- Bootstrap Core CSS -->
    <link href="layout/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="layout/css/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="layout/css/sb-admin-2.css" rel="stylesheet">

  

</head>
<body>
    <div id="wrapper">

        <!-- Navigation -->
        <%@include file="../menu.html" %>
   <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Excluir Schema RDF</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
            <div class="row">
                    <form method="POST" action="Redirec?link=excluirSchema"  class="selecionar" name="formulario">
                   
                        <script type="text/javascript">
                            function valorPass(valor) {
                            var radio = document.forms[0].elements[valor];
                            var retorno ='';
	
                            for(i=0; i < radio.length; i++ ) {
                                    if (radio[i].checked == true) {
                                            retorno = radio[i].value;
                                            document.getElementById(retorno).style.display="block";
                                            
                                    }
                            }
}
                        </script>
                        <p><input type="radio" name="colecao" value="" disabled="true" /> Selecione o Schema a ser excluido:</p>
                      
                        <% 
                      if (colecoes.size()<1){
                      out.print("<p>Não existem Schemas salvos no momento</p>");
                      }
                      for (int i = 0; i < colecoes.size() ;i++) {
                                
                                String col = colecoes.get(i);
                                out.print("<p><input type='radio' name='colecao' value = '"+col+"'/> "+col+"</p>");
                        
                        }
                        %>
                     
                      <p>
                          <input type ="button" class="btn btn-outline btn-primary" name="excluir" value ="Excluir Schema" onClick="valorPass('colecao');">
                           <a href="home.jsp"> <input type="button" value ="Cancelar" class="btn btn-outline btn-primary"></a>
               
                      </p>
                     </form>
                      <% 
                      for (int i = 0; i < colecoes.size() ;i++) {
                                String col = colecoes.get(i);
                       %>
                        <div id="<% out.print(col); %>" class="confirmeMine">
                             <h3>Tem certeza de que deseja excluir "<% out.print(col); %>" ? </h3>
                             <p>
                                 <a href="Controler?op=excluirSchema&colecao=<% out.print(col); %>"> <input type="button" value="Sim ,quero excluir" name="sim"  class="btn btn-outline btn-primary"/></a>
                                 <input type ="button" class="btn btn-outline btn-primary" name="cancelar" value ="Cancelar" onclick="document.getElementById('<% out.print(col); %>').style.display='none';">
                             </p>
                         </div>        
                        <%  }
                            %> 
                     
               
             </div>       
        </div>
        </div>
        </div>
         <!-- jQuery -->
    <script src="layout/js/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="layout/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="layout/js/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="layout/js/sb-admin-2.js"></script>

    </body>
</html>
