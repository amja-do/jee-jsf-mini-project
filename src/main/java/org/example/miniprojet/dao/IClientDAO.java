package org.example.miniprojet.dao;

import org.example.miniprojet.models.Client;

import java.util.List;

public interface IClientDAO {
    List<Client> selectAll();
    List<Client> findByKeyword(String keyword);
    boolean insert(Client client);
    boolean update(Client client);
    boolean delete(int client_id);

}
