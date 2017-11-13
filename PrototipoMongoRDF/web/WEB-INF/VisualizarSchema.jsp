<%-- 
    Document   : VisualizarSchema
    Created on : 22/08/2017, 17:53:46
    Author     : Robson
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.BasicDBList"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="java.util.List"%>
<%@page import="com.mongodb.DBObject"%>
<%@page import="DAO.MongoDAO"%>
<% 
        HttpSession sessao = request.getSession();
        MongoDAO dao = new MongoDAO();
        
        List<Document> schema = (List<Document>) sessao.getAttribute("schema"); 

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
    <span id="topo"></span>
    <div id="wrapper">
        <p class="botaotopo" id="botaoTopo"><a href="#topo">Voltar ao topo</a></p>
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
                    Document atual = schema.get(i);
                    Document spc = null;
                    int css = 0;
                    css = i % 2;
                %>    
                        <div class='<% out.print("linha"+css); %>'>
                            
                        <input type ='hidden' name='id' value =' <% out.print(atual.get("_id")); %>'/>
                        <input type ='hidden' name='labelClasse' value ='<% out.print(atual.get("label")); %>'/>
                        <p> <label id = '<% out.print(atual.get("label")); %>'>Label Classe: <% out.print(atual.get("label")); %> </label> </p>
                        <input type ='hidden' name='comentario' value ='<% out.print(atual.get("comentario")); %>'/>
                           <p> <label>Comentário: <% out.print(atual.get("comentario")); %> </label> </p>
                        <input type ='hidden' name='uri' value ='<% out.print(atual.get("uri")); %>'/>
                               <label>URI: <% out.print(atual.get("uri")); %> </label> </p>
                        <label>subclasses:</label>
                           
                        <ul> 
                        <% 
                        List<String> sc = (List<String>)atual.get("subclasses");
                       
                        for (int in =0 ; in < sc.size();in++){
                        
                        out.print("<li><a href='#"+sc.get(in).toString()+"'>"+sc.get(in).toString()+"</a></li>");
                        }
                        %>
                        </ul>
                        
                        
                        <input type ='hidden' name='superclasse' value ='<% out.print(atual.get("superclasse")); %>'/>
                        <p><label> Superclasse:<a href='#<% out.print(atual.get("superclasse")); %>'> <% out.print(atual.get("superclasse")); %></label></a></p>
                       
                        <input type ='hidden' name='SuperClasse' value ='<% out.print(atual.get("namespace")); %>'/>
                           <p><label>Name Space: <% out.print(atual.get("namespace")); %> </label> </p>

                        <p><input type ="button" class="btn btn-outline btn-primary" name="editar" value ="Editar registro" onclick="document.getElementById('<% out.print(atual.get("_id")); %>').style.display='block';"> 
                           <!-- <input type ="button" class="btn btn-outline btn-primary" name="excluir" value ="Excluir Registro" onclick="document.getElementById('excluir_<% out.print(atual.get("_id")); %>').style.display='block';"> </p> -->
                        </form>

                        </div>
                            <div id="excluir_<% out.print(atual.get("_id")); %>" class="confirme">
                                <h1 class="page-header"> Tem certeza de que deseja excluir o registro :</h1>
                                 <form method="post" action ="Controler" id='formEditar'>
                                       <input type='hidden' name='id' value ='<% out.print(atual.get("_id")); %>'/>
                                               <label>Nome da classe :</label> <input type ='text' name='labelClasse' value ="<% if (atual.get("label") != null){out.print(atual.get("label"));} %>" size="40"/> </p>
                                               <label>Cometário :</label> <input type ='text' name='comentario' value ="<% if (atual.get("comentario") != null){out.print(atual.get("comentario"));} %>" size="40"/> </p>
                                               <label>URI :</label> <input type ='text' name='uri' value ="<% if (atual.get("uri") != null){out.print(atual.get("uri"));} %>" size="40"/> </p>
                                             
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
                                       <input type='hidden' name='labelAtual' value ='<% out.print(atual.get("label")); %>'/>
                                               <label>Nome da classe :</label> <input type ='text' name='labelNovo' value ="<% if (atual.get("label") != null){out.print(atual.get("label"));} %>" size="40"/> </p>
                                               <label>Cometário :</label> <input type ='text' name='comentario' value ="<% if (atual.get("comentario") != null){out.print(atual.get("comentario"));} %>" size="40"/> </p>
                                               <label>URI :</label> <input type ='text' name='uri' value ="<% if (atual.get("uri") != null){out.print(atual.get("uri"));} %>" size="40"/> </p>
                                             
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
                
                <script type="text/javascript">
 $(document).ready(function(){
    $(window).scroll(function(){
        if ($(this).scrollTop() > 100) {
            document.getElementById("botaoTopo").style.display:"block";
        } else {
        document.getElementById("botaoTopo").style.display:"none";
        }
    });

});
                </script>
         <!-- jQuery -->
    <script src="layout/js/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="layout/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="layout/js/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="layout/js/sb-admin-2.js"></script>

 <a href="#top" class="glyphicon glyphicon-chevron-up"></a> 
    </body>
</html>
