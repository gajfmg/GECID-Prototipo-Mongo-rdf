/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Robson
 */
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDAO {
    
    
     private static MongoClient con = new MongoClient("localhost", 27017);
     private static MongoDatabase banco =  con.getDatabase("DBPrototipo");
     
     public boolean CriarColecao (String nomeColecao){
        String colecao = nomeColecao; 
        
        try{
         banco.createCollection(colecao);
         return true; 
        }catch(MongoException e){
            System.out.println("Erro ao tentar criar a coleção "+nomeColecao+" :"+e);
        return false;
        }
        }
     
     public boolean InserirColecaoRDF(Document doc, String nomecolecao){
            MongoCollection col = banco.getCollection(nomecolecao);
            try{
            col.insertOne(doc);
            return true;
            }catch(MongoException e){
            return false;
            }
            
     } 
        
     public List <String> ListarColecoes (){
          List<String> listadeColecoes = new ArrayList();
          MongoIterable colecoes = banco.listCollectionNames();
          
          MongoCursor cursor = colecoes.iterator();
          while(cursor.hasNext()){
              listadeColecoes.add(cursor.next().toString());
          }
          
     return listadeColecoes;
     }
     
     public FindIterable buscarSchema(String nomeColecao){
         MongoCollection col = banco.getCollection(nomeColecao);
         
         FindIterable retorno = col.find();
         Document dc = new Document();
         
     return retorno;
     }
     

    public boolean DeletarRegistro(String nomeColecao, String idObject){
    MongoCollection col = banco.getCollection(nomeColecao);
     ObjectId id = new ObjectId(idObject);
     BasicDBObject objeto = new BasicDBObject();
     objeto.put("_id", id);
     try {
     col.deleteOne(objeto);
     //colecao.remove(objeto);
     return true;}
     catch(MongoException e){
          return false;
        }
    }
    
    public boolean EditarRegistro(String nomeColecao, String idObject , String labelbusca, BasicDBObject atualizacoes){
     MongoCollection col = banco.getCollection(nomeColecao);
        
     ObjectId id = new ObjectId (idObject);
     BasicDBObject modificacao = new BasicDBObject();
    if(atualizacoes.get("label")!= null){
    //modificando label da classe editada
     modificacao.append("$set", new BasicDBObject("label", atualizacoes.get("label")));
     BasicDBObject query = new BasicDBObject();
     query.append("_id", id);
     col.updateOne(query, modificacao);
     query.clear();
     // modificando valor de label de subclasses 
    BasicDBObject modificacao1= new BasicDBObject();
    modificacao1.append("$set", new BasicDBObject("subclasses.$", atualizacoes.get("label")));
     
     query.append("subclasses", labelbusca);
     col.updateMany(query, modificacao1);
     query.clear();
     // modificando valor de label de super classes 
    BasicDBObject modificacao2= new BasicDBObject();
    modificacao2.append("$set", new BasicDBObject("superclasse", atualizacoes.get("label")));
     
     query.append("superclasse", labelbusca);
     col.updateMany(query, modificacao2);
     query.clear();
    }
   //Modificando comentário da classe editada
    if(atualizacoes.get("comentario")!= null){
     modificacao.append("$set", new BasicDBObject("comentario", atualizacoes.get("comentario")));
     BasicDBObject query = new BasicDBObject();
     query.append("_id", id);
     col.updateOne(query, modificacao); 
    }
    //Modificando URI da classe editada
    if(atualizacoes.get("uri")!= null){
     modificacao.append("$set", new BasicDBObject("uri", atualizacoes.get("uri")));
     BasicDBObject query = new BasicDBObject();
     query.append("_id", id);
     col.updateOne(query, modificacao);
    }// parametro de pesquisa de objeto
     
    return true;
    
     
    }
  
    public int CarregarRDF (String url){
       
        int resposta = 10;
        //Recebendo url do arquivo
        String nomeColecao = url.replace("\\", "/");
        //copiando nome do arquivo para nome de coleção 
        String partes[] = nomeColecao.split("/");
        int ultimaParte = partes.length-1;
        nomeColecao = partes[ultimaParte].toUpperCase();
        nomeColecao = nomeColecao.substring(0, (nomeColecao.length()-4));
        //criando colecao
        List <String> colecoes = ListarColecoes();
        boolean verificador = verificarColecao(nomeColecao);
                
        if (verificador == true){
            CriarColecao(nomeColecao);
            
               //tratando rdf 
                Model rdf = ModelFactory.createDefaultModel().read(url);  
                
                OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_TRANS_INF, rdf);
                ExtendedIterator ext = ont.listClasses();
                if (!ext.hasNext()){
                ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, rdf);
                ext = ont.listClasses();
                }
                        while (ext.hasNext()){
                            OntClass classe = (OntClass) ext.next();
                            Document dc = new Document();
                            List<Document> ldc = new ArrayList<>();
                            Document sbc = new Document();
                            
                            BasicDBObject doc = new BasicDBObject();
                            List<BasicDBObject> listasc = new ArrayList<>();
                            BasicDBObject subclasse = new BasicDBObject();
                            
                            List <String> subcl = new ArrayList<>();
                            
                            List<BasicDBObject> listaspc = new ArrayList<>();
                            String superclasse = null;
                            
                            String labelClasse = classe.getLocalName();
                            String uriClasse = classe.getURI();
                            String namespace = classe.getNameSpace();
                            String comentarioClasse = classe.getComment(null);
                            

                                    if (classe.hasSubClass()){
                                       Iterator sc = classe.listSubClasses();
                                            while(sc.hasNext()){
                                            OntClass atual =(OntClass) sc.next();
                                            String ln = atual.getLocalName();
                                            subcl.add(ln);
                                            }   
                                   }else {
                                   subcl.add("Não há");
                                   
                                   }
                                   
                                    if (classe.hasSuperClass()){
                                       Iterator sc = classe.listSuperClasses();
                                       int i =0;
                                            while(sc.hasNext()){
                                            OntClass ocs =(OntClass) sc.next();
                                            superclasse = ocs.getLocalName();
                                           
                                            }   
                                   }else {
                                   superclasse= "Não há";
                                   
                                   }
                            dc.put("label", labelClasse);
                            dc.put("uri", uriClasse);
                            dc.put("namespace", namespace);
                            dc.put("comentario", comentarioClasse);
                            
                            dc.put("subclasses", subcl);
                            dc.put("superclasse", superclasse);
                            
                            // INSERINDO O CONTEUDO DO RDF NA COLEÇÃO 
                           if (InserirColecaoRDF(dc, nomeColecao)){ 
                           resposta = 2;
                           }
                        }
            }else {
                resposta = 1;

            }
        return resposta;
        }
    
    private boolean verificarColecao(String nomeColecao){
    
        List <String> colecoesnoBanco = ListarColecoes();
        Boolean retorno = true;
        for (int i = 0 ; i < colecoesnoBanco.size(); i++){
             if (colecoesnoBanco.get(i).equalsIgnoreCase(nomeColecao)){
             retorno = false;
             i = colecoesnoBanco.size();
             }else {
             retorno = true;
             }
            
        }
        return retorno;
    }
    
    public boolean DeletarColecao(String nomeColecao){
    banco.getCollection(nomeColecao).drop();
    //banco.getCollection(nomeColecao).drop();
    return true;
    }
    
    
    public boolean exportarParaJSON (String url , String nomeColecao){
        
        //dados do banco
        String Host = "localhost";
        String Port = "27017";
        String db = "DBPrototipo";
        String col = nomeColecao;
        
        String destino = url+"/"+nomeColecao+".json";

        String command = "C:\\Program Files\\MongoDB\\Server\\3.4\\bin\\mongoexport.exe --host " + Host + " --port " + Port + " --db " + db + " --collection " + col + "  --out " + destino + "";
        boolean retorno = false;
        try {
            System.out.println(command);
            Process process = Runtime.getRuntime().exec(command);
            retorno = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return retorno;
    }
    
    public boolean exportarParaRDF (String url, String nomeColecao){
        boolean retorno = false;
       // pegando colecao no banco
       MongoCollection colecao  = banco.getCollection(nomeColecao);
       FindIterable fi = colecao.find();
        System.out.println("conectado a coleçao");
       //iniciando a criação de ontologia
       OntModel ontologia = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
      
       MongoCursor<Document> cursor = fi.iterator();
       while(cursor.hasNext()){
       // varrendo coleção e crianco classes com registros existentes.
       Document atual = cursor.next();
       
       if (atual.get("uri") != null){
       ontologia.createClass(atual.get("uri").toString());
       System.out.println("classe criada: "+ atual.get("uri"));
       OntClass classe;
           classe = ontologia.getOntClass(atual.get("uri").toString());
       System.out.println("classe localizada");
     if (atual.get("comentario") != null){
     classe.addComment(atual.get("comentario") .toString(), null);
     }      
       if(atual.get("superclasse")!=null){
                String nomeSuperClasse = atual.get("namespace").toString()+atual.get("superclasse").toString();
                System.out.println(nomeSuperClasse);
                OntClass superClasse = ontologia.getOntClass(nomeSuperClasse);
                    if(superClasse!=null){
                        System.out.println("classe existe.");
                        classe.addSuperClass(superClasse);
                    }else{
                        System.out.println("classe não existe.");
                            ontologia.createClass(nomeSuperClasse);
                            System.out.println("nova classe criada.");
                            superClasse = ontologia.getOntClass(nomeSuperClasse);
                            System.out.println("nova classe localizada.");
                            classe.addSuperClass(superClasse);
                            System.out.println("super classe adicionada.");

                    }
                
                
            }
       }     
       }
       System.out.println("criação de ontologia concluida");
        StringWriter sw = new StringWriter();
        
        ontologia.write(sw, "RDF/XML-ABBREV");
            String owlCode = sw.toString();
            String destino = url+"/"+nomeColecao+".rdf";
            File file = new File(destino);
            try {
                FileWriter fw = new FileWriter(file);
                fw.write(owlCode);
                fw.close();
                retorno = true;
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
     
    return retorno;
    }
    }


     


