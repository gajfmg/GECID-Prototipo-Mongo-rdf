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
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDAO {
    
     private static Mongo conexao = new MongoClient();
     private static DB banco = conexao.getDB("DBPrototipo");
    
     private static MongoClient con = new MongoClient("localhost", 27017);
     private static MongoDatabase pegacolecao =  con.getDatabase("DBPrototipo");
     
     public boolean CriarColecao (String nomeColecao){
        String colecao = nomeColecao; 
        
        CreateCollectionOptions opcaoBanco = new CreateCollectionOptions();
        try{
         banco.createCollection(colecao, null);
         return true; 
        }catch(MongoException e){
            System.out.println("Erro ao tentar criar a coleção "+nomeColecao+" :"+e);
        return false;
        }
        }
     
     public boolean InserirColecaoRDF(BasicDBObject doc, String nomecolecao){
            DBCollection colecao = banco.getCollection(nomecolecao) ;
            
            try{
            colecao.insert(doc);
            return true;
            }catch(MongoException e){
            return false;
            }
            
     } 
        
     public List <String> ListarColecoes (){
          List<String> listadeColecoes = new ArrayList();
          MongoIterable colecoes = pegacolecao.listCollectionNames();
          MongoCursor cursor = colecoes.iterator();
          while(cursor.hasNext()){
              listadeColecoes.add(cursor.next().toString());
          }
          
     return listadeColecoes;
     }
       
     public DBCursor BuscaSchema (String nomeColecao){
     
         DBCollection colecao = banco.getCollection(nomeColecao);
         DBCursor resultado = colecao.find();
     
     
     
     return resultado;
     }

    public boolean DeletarRegistro(String nomeColecao, String idObject){
     
     DBCollection colecao = banco.getCollection(nomeColecao);
     ObjectId id = new ObjectId(idObject);
     DBObject objeto = new BasicDBObject();
     objeto.put("_id", id);
     try {
     colecao.remove(objeto);
     return true;}
     catch(MongoException e){
          return false;
        }
    }
    
    public boolean EditarRegistro(String nomeColecao, String idObject , BasicDBObject atualizacoes){
     
     DBCollection colecao = banco.getCollection(nomeColecao);
     ObjectId id = new ObjectId (idObject);
     // parametro de pesquisa de objeto
     BasicDBObject query = new BasicDBObject();
     query.append("_id", id);
    try { 
     colecao.findAndModify(query, atualizacoes);
     return true;
    }catch (MongoException e) {
    return false;
    }
     
    }
  
    public int CarregarRDF (String url){
       
        int resposta = 0;
        //Recebendo url do arquivo
        String nomeColecao = url.replace("\\", "/");
        //copiando nome do arquivo para nome de coleção 
        String partes[] = nomeColecao.split("/");
        int ultimaParte = partes.length-1;
        nomeColecao = partes[ultimaParte].toUpperCase();
        //criando colecao
        List <String> colecoes = ListarColecoes();
        boolean verificador = verificarColecao(nomeColecao);
                
        if (verificador == true){
            CriarColecao(nomeColecao);
            resposta = 0;
               //tratando rdf 
                Model rdf = ModelFactory.createDefaultModel().read(url);  
            
                OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, rdf);

                ExtendedIterator ext = ont.listClasses();
                        while (ext.hasNext()){
                            OntClass classe = (OntClass) ext.next();

                            BasicDBObject doc = new BasicDBObject();
                            BasicDBObject subclasse = new BasicDBObject();

                            String labelClasse = classe.getLocalName();
                            String uriClasse = classe.getURI();
                            String definicaoClasse = classe.getNameSpace();
                            String comentarioClasse = classe.getComment(null);

                                    if (classe.hasSubClass()){
                                       Iterator sc = classe.listSubClasses();
                                            while(sc.hasNext()){
                                            OntClass ocs =(OntClass) sc.next();
                                            String ln = ocs.getLocalName();
                                            subclasse.put("subclasses", ln);
                                            }   
                                   }else {
                                   subclasse.put("subclasses", "Não há");

                                   }

                            doc.put("label", labelClasse);
                            doc.put("uri", uriClasse);
                            doc.put("classe_definicao", definicaoClasse);
                            doc.put("comentario", comentarioClasse);
                            doc.put("subclasse", subclasse);
                            // INSERINDO O CONTEUDO DO RDF NA COLEÇÃO 
                           if (InserirColecaoRDF(doc, nomeColecao)){ 
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
    boolean retorno = false;    
    for (int i = 0 ; i < colecoesnoBanco.size(); i++){
            if (colecoesnoBanco.get(i).equals(nomeColecao)){
                retorno = false;
                
            }else{ 
                retorno = true;
            }
        }
    return retorno;
    }
    
    }


     


