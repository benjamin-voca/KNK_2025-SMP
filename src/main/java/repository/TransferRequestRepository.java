package repository;

import database.DB_Connector;
import models.TransferRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferRequestRepository {
    public boolean save(TransferRequest request) {
        String query = "INSERT INTO transfer_request (id, name, program, targetprogram) VALUES (?, ?, ?, ?)";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, request.getId());
            stmt.setString(2, request.getName());
            stmt.setString(3, request.getProgram());
            stmt.setString(4, request.getTargetProgram());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}