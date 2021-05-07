package bank;


import java.sql.*;
import java.util.Arrays;

public class Bank {

    /*
        Strings de connection à la base postgres
     */
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5439/postgres";
    private static final String DB_USER = "postgres";




    /*
        Strings de connection à la base mysql, à décommenter et compléter avec votre nom de bdd et de user
     */
    // private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_db";
    // private static final String DB_USER = "bank_user";

    private static final String DB_PASS = "1234";

    private static final String TABLE_NAME = "accounts";

   private Connection c;

    public Bank() {
        initDb();

        // TODO
    }

    private void initDb() {
        try {
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Opened database successfully");

            // TODO Init DB

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void closeDb() {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println("Could not close the database : " + e);
        }
    }

    void dropAllTables() {
        try (Statement s = c.createStatement()) {
            s.executeUpdate(
                       "DROP SCHEMA public CASCADE;" +
                            "CREATE SCHEMA public;" +
                            "GRANT ALL ON SCHEMA public TO postgres;" +
                            "GRANT ALL ON SCHEMA public TO public;");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    public void createNewAccount(String name, int balance, int threshold) {
        try (Statement s = c.createStatement()) {
            if(threshold <= 0){
                String request =
                "INSERT INTO public account " + "VALUE (name, balance, threshold, false)");
                int rowCount = s.executeUpdate(
                        request);
                System.out.println(rowCount);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public String printAllAccounts() {
        String value = "";
        try (Statement s = c.createStatement()) {
            String query = "SELECT * FROM accounts";

            ResultSet rs = s.executeQuery(query);

            while (rs.next())
            {
                Account acc = new Account(rs.getString("name"), rs.getInt("balance"),rs.getInt("threshold"),rs.getBoolean("block"));
                System.out.println(acc.toString());
                value += acc.toString();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return value;
    }

    public void changeBalanceByName(String name, int balanceModifier) {
        try (Statement s = c.createStatement()) {

            String query = "UPDATE accounts "
                    + "SET balance = balance + balanceModifier"
                    + "WHERE name = 'name'"
                    + "AND block = false "
                    + "AND balance + balanceModifier > threshold";

            s.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void blockAccount(String name) {
        try (Statement s = c.createStatement()) {
            String query = "UPDATE accounts"
                    + "SET block = true"
                    + "WHERE name = 'name'";
            System.out.println(query);
            s.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    // For testing purpose
    String getTableDump() {
        String query = "select * from " + TABLE_NAME;
        String res = "";

        try (PreparedStatement s = c.prepareStatement(query)) {
            ResultSet r = s.executeQuery();

            // Getting nb colmun from meta data
            int nbColumns = r.getMetaData().getColumnCount();

            // while there is a next row
            while (r.next()){
                String[] currentRow = new String[nbColumns];

                // For each column in the row
                for (int i = 1 ; i <= nbColumns ; i++) {
                    currentRow[i - 1] = r.getString(i);
                }
                res += Arrays.toString(currentRow);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return res;
    }
}
