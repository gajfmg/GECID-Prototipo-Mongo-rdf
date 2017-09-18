/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testemongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.jena.graph.Graph;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.SAXException;

/**
 *
 * @author Robson
 */
public class TesteMongo {
  
      public static  MongoClient conexao = new MongoClient("localhost", 27017);
       public static MongoDatabase banco = conexao.getDatabase("testedb");
       public static MongoCollection<Document> colecao = banco.getCollection("RDF");
     
    
    public static void main(String[] args) throws JSONException, ParserConfigurationException, SAXException, IOException {
    
            Scanner scan = new Scanner(System.in);
            System.out.println("INFORME O URL DO ARQUIVO A SER INSERIDO ");
            String url = scan.nextLine();
            String texto = url.replace("\\","/");
            System.out.println(texto);
            
            String partes[] = texto.split("/");
            for(int i = 0 ; i < partes.length ; i++){
                System.out.println(partes[i]);
            }
            int tam = partes.length -1;
            System.out.println(partes[tam]);
            
     }
    
    public static void menu(){
      Scanner scan = new Scanner(System.in);
        System.out.println("OQUE VOCE DESEJA FAZER ? ");
        System.out.println("\n 1 - LISTAR REGISTROS"
                + "\n 2 - INSERIR REGISTRO"
                + "\n 3 - ATUALIZAR REGISTRO"
                + "\n 4 - DELETAR REGISTRO"
                + "\n 5 - LISTAR BANCOS EXISTENTES"
                + "\n 6 - SAIR");
        String opcao =  scan.nextLine();
        
        
        
        switch(opcao){
            case "1":
                listarRegistros();
                menu();
                break;
            case "2": 
                inserirRegistro();
                menu();
                break;
            case "3":
                atualizarRegistro();
                menu();
                break;
            case "4":
                deletarRegistro();
                menu();
                break;
            case "5":
                listarBancos();
                menu();
                break;
            case "6":
               conexao.close();
              break;
        }
     
    }
        
    public static void listarRegistros(){
        System.out.println("REGISTROS ATUAIS");
       
        try {
            Mongo m = new Mongo("localhost", 27017);
            DB base = m.getDB("testedb");
            DBCollection col = base.getCollection("RDF");
       
            
            
        BasicDBObject query = new BasicDBObject();
        BasicDBObject colunas = new BasicDBObject();
       // colunas.put("label", 1);
      
        String campo = "_id";
        ObjectId valor = new ObjectId ("5992e54a7b1fe0172068df72");
        
        query.put(campo ,valor );
         List <Object> lista;
                
        DBCursor c = col.find(query);
        
        while (c.hasNext()){
            Map o = c.next().toMap();
            Collection cole = o.values();
            Iterator i = cole.iterator();
            
            while(i.hasNext()){
                System.out.println("___"+i.next()+"\n");
            }
            
            Object obj = o.get("@graph");
            
            System.out.println(obj.toString()); 
        
        }
        
        /*
         FindIterable<Document>registros =  colecao.find(query);
        MongoCursor cursor = registros.iterator();
        
        while(cursor.hasNext()){
          
        Object doc = cursor.next();
        
        System.out.println(doc);
        
        }*/
        }catch(MongoException e){
            System.out.println("ERRO DE EXECUÇÃO :"+ e);
        }
    }
    
    public static  void listarBancos(){
     
        
        List <String> dbs = conexao.getDatabaseNames();
          
          int i = 0;
          System.out.println("Bancos de dados existentes");
          while ( i < dbs.size()){
              
              System.out.println(dbs.get(i));
              i++;
          }
       
    }
    
    public static void inserirRegistro(){
        System.out.println("INSERINDO REGISTRO >>>>>");
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome ");
        String nome = scan.nextLine();
        System.out.println("Digite o email ");
        String email = scan.nextLine();
        System.out.println("Digite a idade");
        String idade = scan.nextLine();
        System.out.println("AGUARDE...");
        
        try{
        Document dc = new Document();
        dc.put("NOME", nome);
        dc.put("EMAIL", email);
        dc.put("IDADE", idade);
        colecao.insertOne(dc);
            System.out.println("REGISTRO INSERIDO COM EXITO");
            listarRegistros();
        
        }catch(MongoException e){
            System.out.println("ERRO NA OPERAÇÃO:"+ e);
            ;
        }
        
    }
    
    public static void trataRDF (){
    
        System.out.println("INSERINDO RDF >>>>>");
        Scanner scan = new Scanner(System.in);
        System.out.println("INFORME O URL DO ARQUIVO A SER INSERIDO ");
        String url = scan.nextLine();
        // lê o arquivo indicado na url e armazena o mesmo em um Model RDF 
       Model rdf = ModelFactory.createDefaultModel().read(url);  
        System.out.println(rdf.getLock());
       OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, rdf);
       
       
       ExtendedIterator ext = ont.listClasses();
        while (ext.hasNext()){
            OntClass classe = (OntClass) ext.next();
            String labelClasse = classe.getLocalName();
            String uriClasse = classe.getURI();
            String definicaoClasse = classe.getNameSpace();
            String descricaoClasse = classe.getComment(null);
           // String subclassede= classe.getSubClass().toString();
             List <String> subClasses = new ArrayList<>();
            if (classe.hasSubClass()){
                Iterator sc = classe.listSubClasses();
                     while(sc.hasNext()){
                     OntClass ocs =(OntClass) sc.next();
                     String ln = ocs.getLocalName();
                     subClasses.add(ln);
                     }   
            }else {
            subClasses.add("Não há subclasse");
            }
          
            System.out.println("Label :"+labelClasse+"\n"+
                                "URI:"+uriClasse+"\n"+
                                "Descrição:"+descricaoClasse+"\n"+
                                "Subclasse:");
                                        for (int i =0 ; i < subClasses.size();i++){
                                                System.out.println("    "+subClasses.get(i));
                                        }
                                        
        }                   
    }
    
    public static void inserirRDF(){
     
        System.out.println("INSERINDO RDF >>>>>");
        Scanner scan = new Scanner(System.in);
        System.out.println("INFORME O URL DO ARQUIVO A SER INSERIDO ");
        String url = scan.nextLine();
        // lê o arquivo indicado na url e armazena o mesmo em um Model RDF 
       Model rdf = ModelFactory.createDefaultModel().read(url, "rj");  
       //ExtendedIterator e = ;
            // convertendo o RDF em JSON 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            RDFDataMgr.write(baos, rdf, RDFFormat.JSONLD);
            String result = baos.toString();
        //    System.out.println(result);
        
        
            try{
            // CRIA O DOCUMENTO A SER INSERIDO N MOGNO JÁ COM INSTRUÇÕES JSON
            Document dc = Document.parse(result);
        
        colecao.insertOne(dc);
            System.out.println("ARQUIVO INSERIDO COM EXITO");
            listarRegistros();
        conexao.close();
        }catch(MongoException e){
            System.out.println("ERRO NA OPERAÇÃO:"+ e);
            ;
        }
        
    }
    
    public static void atualizarRegistro(){
        MongoCollection<Document> colecao = banco.getCollection("mycollection");
        System.out.println("REGISTROS ATUAIS >>>>");
        listarRegistros();
        
        Scanner scan = new Scanner(System.in);
        System.out.println("INFORME O ID DO OBJETO A SER EDTADO ");
        String id = scan.nextLine();
        System.out.println("INFORME O NOME DO CAMPO A SER EDITADO ");
        String campo = scan.nextLine();
        System.out.println("INFORME O VALOR QUE O CAMPO RECEBERA");
        String valor = scan.nextLine();
        System.out.println("AGUARDE...");
        try{
        Document filtro = new Document("_id", id);
        Document atualizacao = new Document(campo, valor);
        Document operacao = new Document("$set", atualizacao);
        colecao.updateOne(filtro, operacao);
        System.out.println("REGISTRO ATUALIZADO COM EXITO");
        
        System.out.println("REGISTROS ATUAIS >>>>");
        listarRegistros();
        
        }catch (MongoException e){
            System.out.println("ERRO NA OPERAÇÃO :"+ e);
        }
    
    }

    public static void deletarRegistro(){
        System.out.println("DELETANDO REGISTRO");
        MongoCollection<Document> colecao = banco.getCollection("mycollection");
        
        System.out.println("REGISTROS ATUAIS >>>>");
        listarRegistros();
        
        Scanner scan = new Scanner(System.in);
        System.out.println("INFORME O ID DO OBJETO A SER DELETADO ");
        String id = scan.nextLine();
        System.out.println("AGUARDE...");
        
        try{
        Document filtro = new Document("_id", id);
        colecao.deleteOne(filtro);
        System.out.println("REGISTRO DELETADO COM EXITO .");
        System.out.println("REGISTROS ATUAIS >>>>");
        listarRegistros();
        
        }catch(MongoException e){
            System.out.println("ERRO NA OPERAÇÃO :"+e);
        }
    }

    public static void leitorXML() throws FileNotFoundException, IOException{
    
    BufferedReader br = new BufferedReader(new FileReader(new File("C:\\ProjetoCESJFTCC\\testeMongo\\ARQ-RDF/arquivo.rdf")));
    String line;
    StringBuilder sb = new StringBuilder();

while((line=br.readLine())!= null){
    sb.append(line.trim());
    System.out.println(sb.toString()+"\n");
}

    }
}
  
       /* LISTAR OBJETOS DO MODEL
       
       NodeIterator lo = rdf.listObjects();
            
      while (lo.hasNext()){
      
          System.out.println(lo.nextNode());
      } 
     */
    