package org.example.miniprojet.services;

import org.example.miniprojet.config.DB;
import org.example.miniprojet.dao.IClientDAO;
import org.example.miniprojet.models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements IClientDAO {

    private Connection cnx = null;
    public ClientDAO() {
        cnx = new DB().getConnection();
    }

    @Override
    public List<Client> selectAll() {
        List<Client> clients = null;
        try {
            PreparedStatement ps = cnx.prepareStatement("select * from jee_clients order by id desc");
            ResultSet rs = ps.executeQuery();
            clients = new ArrayList<Client>();
            while (rs.next()){
                clients.add(new Client(rs.getInt(1),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getString(4)));
            }
        }catch (Exception e){
            return null;
        }
        return clients;
    }

    @Override
    public List<Client> findByKeyword(String keyword) {
        List<Client> clients = null;
        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM jee_clients WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ? ORDER BY id DESC");
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            clients = new ArrayList<Client>();
            while (rs.next()){
                clients.add(new Client(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
        }catch (Exception e){
            return null;
        }
        return clients;
    }

    @Override
    public boolean insert(Client client) {
        try {
            PreparedStatement ps = cnx.prepareStatement("insert into jee_clients(first_name, last_name, email) values(?,?,?)");
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        try {
            PreparedStatement ps = cnx.prepareStatement("update jee_clients set first_name = ?, last_name = ?, email = ? where id = ?");
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setInt(4, client.getId());
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(int client_id) {
        try {
            PreparedStatement ps = cnx.prepareStatement("delete from jee_clients where id = ?");
            ps.setInt(1, client_id);
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
