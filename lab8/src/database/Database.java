package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Square;

public class Database
{

    protected static final String CONNECTION_STRING = "jdbc:postgresql://localhost:5432/Battleships";
    public final Connection connection;
    private static Database database;

    private Database() throws SQLException
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            DriverManager.registerDriver(new org.postgresql.Driver());
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Properties connectionProperties = new Properties(2);
        connectionProperties.put("user", "postgres");
        connectionProperties.put("password", "admin");

        Driver driver = DriverManager.getDriver(CONNECTION_STRING);
        connection = driver.connect(CONNECTION_STRING, connectionProperties);
    }

    public static Database get() throws SQLException
    {
        if (database == null)
        {
            database = new Database();
        }
        return database;
    }

    public List<Square> getSquares() throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM squares");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Square> squares = new ArrayList<>();

        while (resultSet.next())
        {
            squares.add(new Square(
                                resultSet.getInt("x"),
                                resultSet.getInt("y"),
                                resultSet.getBoolean("ship1"),
                                resultSet.getBoolean("ship2"),
                                resultSet.getBoolean("hit1"),
                                resultSet.getBoolean("hit2")
                        )
            );
        }
        return squares;
    }

    public void setSquareShip(int player, int x, int y) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE squares SET ship" + player + "=true WHERE x=? AND y= ?");
        preparedStatement.setInt(1, x);
        preparedStatement.setInt(2, y);
        preparedStatement.execute();
    }

    public String getPlayer1SessionId() throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT session FROM players WHERE player=1");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("session");
    }

    public void setPlayer1SessionId(String sessionId) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE players SET session=? WHERE player=1");
        preparedStatement.setString(1, sessionId);
        preparedStatement.execute();
    }

    public void setPlayerTurn(int player, boolean turn) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET turn=? WHERE player=?");
        preparedStatement.setBoolean(1, turn);
        preparedStatement.setInt(2, player);
        preparedStatement.execute();
    }

    public void setPlayerShips(int player, String ships) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET ships=? WHERE player=?");
        preparedStatement.setString(1, ships);
        preparedStatement.setInt(2, player);
        preparedStatement.execute();
    }

    public String getPlayerShips(int player) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT ships FROM players WHERE player=?");
        preparedStatement.setInt(1, player);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("ships");
    }

    public void setPlayer2SessionId(String sessionId) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE players SET session=? WHERE player=2");
        preparedStatement.setString(1, sessionId);
        preparedStatement.execute();
    }

    public String getPlayer2SessionId() throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT session FROM players WHERE player=2");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("session");
    }

    public void resetGame() throws SQLException
    {
        setPlayer1SessionId("");
        setPlayer2SessionId("");
        setPlayerShips(1, "");
        setPlayerShips(2, "");
        setPlayerTurn(1, false);
        setPlayerTurn(2, false);

        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM squares");
        deleteStatement.execute();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO squares (x, y, ship1, ship2, hit1, hit2)  VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setBoolean(3, false);
        preparedStatement.setBoolean(4, false);
        preparedStatement.setBoolean(5, false);
        preparedStatement.setBoolean(6, false);
        for (int i = 0; i < Square.BOARD_SIZE; i++)
        {
            for (int j = 0; j < Square.BOARD_SIZE; j++)
            {
                preparedStatement.setInt(1, i);
                preparedStatement.setInt(2, j);
                preparedStatement.execute();
            }
        }
    }

    public int getPhase(int player) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT turn FROM players WHERE player=?");
        preparedStatement.setInt(1, player);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getBoolean("turn") ? 1 : 0;
    }

    public void hitSquare(int player, int x, int y) throws SQLException
    {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE squares SET hit" + player + "=true WHERE x=? AND y= ?");
        preparedStatement.setInt(1, x);
        preparedStatement.setInt(2, y);
        preparedStatement.execute();
    }
}
