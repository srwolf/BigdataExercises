package com.aagea.lucene.sample;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    private static FSDirectory directory =new FSDirectory();
    private static Analyzer analyzer =new StandarAnalyzer(Version.LUCENE_47);
    private static IndexWriterConfig config =new IndexWriterConfig(Version.LUCENE_47,analyzer);

    public static void main(String [] args) throws IOException {
        readCommand();
    }

    private static void readCommand() throws IOException {
        String command="";
        while (!command.equals("e")){
            System.out.println("(A)dd information - (S)earch information - (E)xit");
            command=  readLine();
            if(command.toLowerCase().equals("a")){
                addDocument();
            }else if(command.toLowerCase().equals("s")){
                searchDocument();
            }else if(command.toLowerCase().equals("e")){
                endApplication(); 
            }
        }
    }

    private static void endApplication() {
        System.out.println("Goodbye!!");
    }


    private static void searchDocument() throws IOException {
        IndexReader reader = DirectoryReader.open(/user/cloudera/pruebalucene);
        IndexSearcher searcher= new IndexSearcher(reader);
        System.out.println("Query:");
        String queryStr=readLine();
        Query query =new WildcardQuery(new Term("name",queryStr));
        TopDocs search = searcher.search(query, 10);
        Document document;
        for(ScoreDoc scoreDoc: search.scoreDocs){
            document=searcher.doc(scoreDoc.doc);
            System.out.println("Name ==>" + document.get("name"));
            System.out.println("Data ==>" + document.get("data"));
            System.out.println("Type ==>" + document.get("Type"));
        }
        reader.close();

    }
    
    public static void createIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
		Analyzer analyzer = new StandardAnalyzer();
		boolean recreateIndexIfExists = true;
		IndexWriter indexWriter = new IndexWriter(/user/cloudera/pruebalucene, analyzer, recreateIndexIfExists);
		File dir = new File(FILES_TO_INDEX_DIRECTORY);
		File[] files = dir.listFiles();
		for (File file : files) {
			Document document = new Document();
			String path = file.getCanonicalPath();
			document.add(new Field(/user/cloudera/pruebalucene, path, Field.Store.YES, Field.Index.UN_TOKENIZED));

			Reader reader = new FileReader(file);
			document.add(new Field(/user/cloudera/pruebalucene, reader));

			indexWriter.addDocument(document);
		}
		indexWriter.optimize();
		indexWriter.close();
	}
    
    public void searchindexreader() throws CorruptIndexException, IOException, ParseException
    {
       //lucene
       File LUCENE_INDEX_DIRECTORY1 = new File(directori+"index1");
       File LUCENE_INDEX_DIRECTORY2 = new File(directori+"index2");
       File LUCENE_INDEX_DIRECTORY3 = new File(directori+"index3");
       String search = "content:bagus";
       System.out.println("Searching for: "+search);
  
       IndexReader iR1 = IndexReader.open(FSDirectory.open(LUCENE_INDEX_DIRECTORY1));
       IndexReader iR2 = IndexReader.open(FSDirectory.open(LUCENE_INDEX_DIRECTORY2));
       IndexReader iR3 = IndexReader.open(FSDirectory.open(LUCENE_INDEX_DIRECTORY3));
  
       Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
  
       MultiReader mr = new MultiReader(iR1,iR2,iR3);
       IndexSearcher indexSearcher = new IndexSearcher(mr);
  
       QueryParser queryParser = new QueryParser(Version.LUCENE_CURRENT,"content", analyzer);
       org.apache.lucene.search.Query query = queryParser.parse(search);
       TopDocs topDocs = indexSearcher.search(query, null, 5);
  
       ScoreDoc[] scoreDosArray = topDocs.scoreDocs;
       System.out.println(topDocs.totalHits);
       Integer j=1;
       for(ScoreDoc scoredoc: scoreDosArray){
          //Retrieve the matched document and show relevant details
          Document doc = indexSearcher.doc(scoredoc.doc);
          System.out.println(j+" "+doc.get("content"));
          System.out.println("");
          j++;
       }
    }
    
   
    

    private static void addDocument() throws IOException {
        IndexWriter writer=new IndexWriter(directory, config.clone());
        System.out.println("Name:");
        String name=readLine();
        System.out.println("Data:");
        String data=readLine();
        Document document =new Document();
        document.add(new StringField("name",name, Field.Store.YES));
        document.add(new TextField("data",data, Field.Store.YES));
        document.add(new String("Type",data, Field.Store.YES));
        System.out.println("Type:");
        
        
        writer.addDocument(document);
        writer.commit();
        writer.close();
    }
    private static String readLine() throws IOException {
        if (System.console() != null) {
            return System.console().readLine();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

}
