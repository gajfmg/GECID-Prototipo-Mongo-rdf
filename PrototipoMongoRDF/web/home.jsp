<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>GECON</title>

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
        <%@include file="menu.html" %>

        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">Armazenamento de Schemas RDF</h1>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
				<div class="row">
						<div class="col-lg-4">
                                                    <a href="Redirec?link=carregarRDF">    
                                                    <p style="text-align:center;"> <img src="img/upload.png" width="250px" height="250px"></p>
                                                    <button type="button" class="btn btn-outline btn-primary btn-lg btn-block">Carregar RDF</button>
                                                    </a>
						</div>
						<div class="col-lg-4">
                                                    <a href="Redirec?link=selecionarSchema">
                                                    <p style="text-align:center;"> <img src="img/visualizar.jpg" width="250px" height="250px" ></p>
                                                    <button type="button" class="btn btn-outline btn-primary btn-lg btn-block">Visualizar Schema </button>
                                                    </a>
						</div>
						<div class="col-lg-4">
                                                    <a href="Redirec?link=exportarSchema">
                                                    <p style="text-align:center;"> <img src="img/exportar2.jpg" width="250px" height="250px"></p>
                                                    <button type="button" class="btn btn-outline btn-primary btn-lg btn-block">Exportar Schema </button>
                                                    </a>
						</div>
						<!-- /.col-lg-12 -->
				</div>
                <!-- /.row -->
			</div>
            <!-- /.container-fluid -->


        </div>
        <!-- /#page-wrapper -->
			
    </div>
    <!-- /#wrapper -->

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
