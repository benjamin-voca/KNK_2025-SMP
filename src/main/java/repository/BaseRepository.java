package repository;

import database.DB_Connector;
import java.sql.*;
import java.util.ArrayList;

public abstract class BaseRepository<Model, CreateModelDto, UpdateModelDto> {
    protected Connection connection;
    private String tableName;

    public BaseRepository(String tableName) {
        this.tableName = tableName;
        try {
            this.connection = DB_Connector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DB_Connector.getConnection();
        }
    }

    abstract Model fromResultSet(ResultSet res) throws SQLException;

    public Model getById(int id) {
        String query = "SELECT * FROM " + this.tableName + " WHERE ID = ?";
        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setInt(1, id);
            ResultSet res = pstm.executeQuery();
            if (res.next()) {
                return this.fromResultSet(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Model> getAll() {
        ArrayList<Model> models = new ArrayList<>();
        String query = "SELECT * FROM " + this.tableName;
        try {
            ensureConnection();
            Statement stm = this.connection.createStatement();
            ResultSet res = stm.executeQuery(query);
            while (res.next()) {
                models.add(this.fromResultSet(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return models;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM " + this.tableName + " WHERE ID = ?";
        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setInt(1, id);
            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public abstract Model create(CreateModelDto createDto);
    public abstract Model update(UpdateModelDto updateDto);
}