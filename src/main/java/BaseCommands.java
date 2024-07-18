import com.mongodb.client.*;
import org.bson.Document;

import java.sql.*;

public class BaseCommands {

    public static void main(String[] args) {

        System.out.println("\n");

        MongoDatabase db = conectar("mongodb://localhost:27017", "inf335");
        MongoCollection<Document> collection = db.getCollection("produtos");

        listaProdutos(collection);
        System.out.println("\n");

        insereProdutos(collection, "7",
                "blackberry",
                "preto",
                122,
                "reliquia");

        listaProdutos(collection);
        System.out.println("\n");

        alteraProduto(collection, "7", 1);
        listaProdutos(collection);
        System.out.println("\n");

        apagaProduto(collection, "7");
        listaProdutos(collection);
        System.out.println("\n");

        /*Connection connection = conectar(
              "username",
            "password",
        "jdbc:mysql://localhost:3306/db");

        listaProdutos(connection);
        System.out.println("\n");

        insereProdutos(connection, "7",
                "pocopoco",
                "122220G",
                1000,
                "importado");

        listaProdutos(connection);
        System.out.println("\n");

        alteraValorProduto(connection, "7", 15);

        listaProdutos(connection);
        System.out.println("\n");

        apagaProduto(connection, "7");
        System.out.println("\n");

        listaProdutos(connection);*/
    }

/*    public static void listaProdutos(Connection conn) {
        PreparedStatement stmt;

        try {
            stmt = (PreparedStatement) conn.prepareStatement("select * from produtos;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String idProduto = rs.getString("idProdutos");
                String nomeProduto = rs.getString("nome");
                String caracteristicas = rs.getString("caracteristicas");
                Integer preco = rs.getInt("preco");
                String infoAdicional = rs.getString("info-adicional");

                System.out.println(idProduto +
                        " ---- "
                        + nomeProduto + " ---- "
                        + caracteristicas + " ---- "
                        + preco + " ---- "
                        + infoAdicional);

            }
        } catch (SQLException e) {

            System.out.println("Erro ao executar select:" + e);
            e.printStackTrace();
        }
    }

    public static void insereProdutos(Connection conn,
                                      String idProduto,
                                      String nome,
                                      String caracteristicas,
                                      Integer preco,
                                      String infoAdicional) {

        Statement stmt;

        try {
            stmt = (Statement) conn.createStatement();

            String insere = "insert into produtos VALUES ('" +
                    idProduto + "','" +
                    nome + "','" +
                    caracteristicas + "','" +
                    preco + "','" +
                    infoAdicional + "');";

            stmt.executeUpdate(insere);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alteraValorProduto(Connection conn, String idProduto, Integer preco) {

        Statement stmt;

        try {
            stmt = (Statement) conn.createStatement();

            String atualiza = "update produtos set preco = '" +
                    preco + "'where idProdutos = '" +
                    idProduto + "';";

            stmt.executeUpdate(atualiza);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void apagaProduto(Connection conn, String idProduto) {
        Statement stmt;

        try {
            stmt = (Statement) conn.createStatement();

            String aparaProduto = "delete from produtos where idProdutos = '" +
                    idProduto + "';";

            stmt.executeUpdate(aparaProduto);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection conectar(String usuario, String senha, String url) {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, usuario, senha);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro de Conexão:" + e);
            e.printStackTrace();
        }

        return conn;
    }*/

    private static MongoDatabase conectar(String url, String databaseName) {

        MongoClient client = MongoClients.create(url);

        return client.getDatabase(databaseName);
    }

    private static String mongoPrint(Document produto) {

        String idProduto = produto.getString("idProduto");
        String nome = produto.getString("nome");
        String caracteristicas = produto.getString("caracteristicas");
        Integer preco = produto.getInteger("preco");
        String infoAdicional = produto.getString("infoAdicional");

        return idProduto + " -- " + nome + " -- " + caracteristicas + " -- " + preco + " -- " + infoAdicional;
    }

    private static void listaProdutos(MongoCollection<Document> collection) {
        Iterable<Document> produtos = collection.find();
        for (Document produto : produtos){
            System.out.println(mongoPrint(produto));
        }
    }
    private static void insereProdutos(MongoCollection<Document> collection,
                                       String idProduto,
                                       String nome,
                                       String caracteristicas,
                                       Integer preco,
                                       String infoAdicional){
        Document document = new Document("idProduto", idProduto)
                                .append("nome", nome)
                                .append("caracteristicas", caracteristicas)
                                .append("preco", preco)
                                .append("infoAdicional", infoAdicional);

        collection.insertOne(document);
    }

    private static void alteraProduto(MongoCollection<Document> collection, String idProduto, Integer preco){
        Document query = new Document("idProduto", idProduto);

        // Find one document that matches the filter
        Document result = collection.find(query).first();
        Document update = new Document("$set", new Document("preco", preco));

        collection.updateOne(query, update);
    }

    public static void apagaProduto(MongoCollection<Document> collection, String idProduto){
        Document query = new Document("idProduto", idProduto);
        collection.deleteOne(query);
    }
}

