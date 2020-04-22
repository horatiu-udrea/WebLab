import java.sql.SQLException;

import database.Database;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        Database database = new Database();
        database.resetGame();
    }
}
