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
                        <h1 class="page-header">Visualizar Schema RDF</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
            <div class="row">
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
                         <input type="submit" value="Visualizar" name="Visualizar" id="Visualizar" class="btn btn-outline btn-primary"/>
       
                        </p>
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
