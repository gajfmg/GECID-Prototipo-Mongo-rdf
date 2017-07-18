/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testemongo;

import com.mongodb.DBObject;
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
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.bson.Document;
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
        
        FindIterable<Document>registros =  colecao.find();
        MongoCursor cursor = registros.iterator();
        while(cursor.hasNext()){
          
        Object doc = cursor.next();
        
        System.out.println(doc);
        }
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
    
    public static void inserirRDF(){
     
        System.out.println("INSERINDO RDF >>>>>");
        Scanner scan = new Scanner(System.in);
        System.out.println("INFORME O URL DO ARQUIVO A SER INSERIDO ");
        String url = scan.nextLine();
        
       Model rdf = ModelFactory.createDefaultModel().read(url, "rj");  
       
    
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            RDFDataMgr.write(baos, rdf, RDFFormat.JSONLD);
            
            String result = baos.toString();
        //    System.out.println(result);
        DBObject dob =(DBObject)com.mongodb.util.JSON.parse(result);
        
            try{
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
    