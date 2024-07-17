import java.io.Console;
import java.sql.*;

public class BaseCommands {

    public static void main(String[] args) {

        Connection connection = conectar(
                "username",
                "password",
                "jdbc:mysql://localhost:3306/db");

        listaProdutos(connection);

        apagaProduto(connection, "7");
        apagaProduto(connection, "8");
        apagaProduto(connection, "9");
        System.out.println("\n");

        listaProdutos(connection);
        System.out.println("\n");

        insereProdutos(connection, "7", "pocopoco", "122220G", 1000, "importado");

        listaProdutos(connection);
        System.out.println("\n");

        alteraValorProduto(connection, "7", 15);

        listaProdutos(connection);
        System.out.println("\n");

        apagaProduto(connection, "7");
        System.out.println("\n");

        listaProdutos(connection);

    }

    public static void listaProdutos(Connection conn){
        PreparedStatement stmt;

        try {
            stmt = (PreparedStatement) conn.prepareStatement("select * from produtos;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
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
                        +infoAdicional);

            }
        }catch (SQLException e){

            System.out.println("Erro ao executar select:" + e);
            e.printStackTrace();
        }
    }
    public static void insereProdutos(Connection conn, String idProduto, String nome,String caracteristicas, Integer preco, String infoAdicional){

        Statement stmt;

        try{
            stmt = (Statement) conn.createStatement();

            String insere = "insert into produtos VALUES ('" +
                    idProduto + "','" +
                    nome + "','" +
                    caracteristicas + "','" +
                    preco + "','" +
                    infoAdicional + "');";

            stmt.executeUpdate(insere);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void alteraValorProduto(Connection conn, String idProduto, Integer preco){

        Statement stmt;

        try{
            stmt = (Statement) conn.createStatement();

            String atualiza = "update produtos set preco = '" +
                    preco + "'where idProdutos = '" +
                    idProduto + "';";

            stmt.executeUpdate(atualiza);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void apagaProduto(Connection conn, String idProduto){
        Statement stmt;

        try{
            stmt = (Statement) conn.createStatement();

            String aparaProduto = "delete from produtos where idProdutos = '"+
                    idProduto + "';";

            stmt.executeUpdate(aparaProduto);

        }catch (SQLException e){
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
    }
}
