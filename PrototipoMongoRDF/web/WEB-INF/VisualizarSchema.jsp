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

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Ben 10</title>
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
                        <h1 class="page-header"><% out.print(sessao.getAttribute("nomeSchema")); %></h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
            <div class="row">
                <div class="col-lg-12">
  
        
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

                        <p><input type ="button" class="btn btn-outline btn-primary" name="editar" value ="Editar" onclick="document.getElementById('<% out.print(atual.get("_id")); %>').style.display='block';"> 
                            <input type ="button" class="btn btn-outline btn-primary" name="excluir" value ="Excluir Registro" onclick="document.getElementById('excluir_<% out.print(atual.get("_id")); %>').style.display='block';"> </p>
                        </form>

                        </div>
                            <div id="excluir_<% out.print(atual.get("_id")); %>" class="confirme">
                                <h1 class="page-header"> Tem certeza de que deseja excluir o registro :</h1>
                                 <form method="post" action ="Controler" id='formEditar'>
                                       <input type='hidden' name='id' value ='<% out.print(atual.get("_id")); %>'/>
                                               <label>Nome da classe :</label> <input type ='text' name='labelClasse' value ="<% if (atual.get("label") != null){out.print(atual.get("label"));} %>" size="40"/> </p>
                                               <label>Cometário :</label> <input type ='text' name='comentario' value ="<% if (atual.get("comentario") != null){out.print(atual.get("comentario"));} %>" size="40"/> </p>
                                               <label>URI :</label> <input type ='text' name='uri' value ="<% if (atual.get("uri") != null){out.print(atual.get("uri"));} %>" size="40"/> </p>
                                               <label>Subclasse :</label> <input type ='text' name='subClasse' value ="<% if (sc.get("subclasses") != null){out.print(sc.get("subclasses"));} %>" size="40"/> </p>
                                               <label>Super classe :</label> <input type ='text' name='superClasse' value ="<% if (atual.get("classe_definicao") != null){out.print(atual.get("classe_definicao"));} %>" size="40"/> </p>

                                       <p> 
                                           <input type ='submit' name='op' value ='Sim' class="btn btn-outline btn-primary"/>
                                           <input type ="button" name="cancelar" value ="Cancelar" onclick="document.getElementById('excluir_<% out.print(atual.get("_id")); %>').style.display='none';" class="btn btn-outline btn-primary">
                                           
                                       </p>
                                      
                                       </form>
                            </div>  
                                          
                    <div id = "<% out.print(atual.get("_id")); %>" class="pop">
                        
                                       <h1 class="page-header">Editar Registro do Schema :<% out.print(sessao.getAttribute("nomeSchema")); %></h1>
                                       <form method="post" action ="Controler" id='formEditar'>
                                       <input type='hidden' name='id' value ='<% out.print(atual.get("_id")); %>'/>
                                               <label>Nome da classe :</label> <input type ='text' name='labelClasse' value ="<% if (atual.get("label") != null){out.print(atual.get("label"));} %>" size="40"/> </p>
                                               <label>Cometário :</label> <input type ='text' name='comentario' value ="<% if (atual.get("comentario") != null){out.print(atual.get("comentario"));} %>" size="40"/> </p>
                                               <label>URI :</label> <input type ='text' name='uri' value ="<% if (atual.get("uri") != null){out.print(atual.get("uri"));} %>" size="40"/> </p>
                                               <label>Subclasse :</label> <input type ='text' name='subClasse' value ="<% if (sc.get("subclasses") != null){out.print(sc.get("subclasses"));} %>" size="40"/> </p>
                                               <label>Super classe :</label> <input type ='text' name='superClasse' value ="<% if (atual.get("classe_definicao") != null){out.print(atual.get("classe_definicao"));} %>" size="40"/> </p>

                                       <p> 
                                           <input type ='submit' name='op' value ='Salvar' class="btn btn-outline btn-primary"/>
                                           <input type ='reset' name='limpar' value ="Limpar" class="btn btn-outline btn-primary"/>
                                          <input type ="button" name="cancelar" value ="Cancelar" onclick="document.getElementById('<% out.print(atual.get("_id")); %>').style.display='none';" class="btn btn-outline btn-primary">
                                           
                                       </p>
                                      
                                       </form>



                       </div>  
        
    <% }
    %> 
                </div> 
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
