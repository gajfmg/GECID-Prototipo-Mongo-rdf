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

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Ben 10</title>

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
                        <h1 class="page-header">Carregar RDF</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
            <div class="row">
                <div class="col-lg-12">
            
                    <form method="POST" action="Controler?op=carregar" class="selecionar">
                    Arquivo:
                    <input type="file" name="arquivo" id="arquivo"  />
                    <br/>
                    <% 
                    if (msg != null){
                    out.print("<p class='msg'>"+msg+"</p>");
                    }
                    %>
                    
                    <br/>
                    <input type="submit" value="Enviar" name="Enviar" id="Enviar" class="btn btn-outline btn-primary"/>
                    <a href="home.jsp"> <input type="button" value ="Cancelar" class="btn btn-outline btn-primary"></a>
                </form>
                    
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
