package za.co.RecruitmentZone.storage;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;

public class LuceneExample {
    private static Directory indexDirectory = new RAMDirectory();

    public static void createIndex(String fileName, byte[] content) throws IOException {
        try (IndexWriter writer = new IndexWriter(indexDirectory, new IndexWriterConfig(new StandardAnalyzer()))) {
            Document doc = new Document();
            doc.add(new Field("filename", fileName, Field.Store.YES));
            doc.add(new Field("content", new String(content), Field.Store.YES));
            // Add more fields as needed (e.g., path, metadata)
            writer.addDocument(doc);
        }
    }

    public static void searchIndex(String queryString) throws Exception {
        try (IndexSearcher searcher = new IndexSearcher(indexDirectory)) {
            Query query = new QueryParser("content", new StandardAnalyzer()).parse(queryString);
            TopDocs topDocs = searcher.search(query, 10); // Return top 10 results
            // Process search results and retrieve files from the database
        }
    }

    public static void main(String[] args) {
        // Example usage
        try {
            // Index a file
            String fileName = "example.txt";
            byte[] content = "This is an example content.".getBytes();
            createIndex(fileName, content);

            // Search the index
            searchIndex("example");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
