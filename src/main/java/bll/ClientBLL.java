package bll;

import dao.ClientDAO;
import model.Client;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @Author: Salajan Madalina-Maria
 * @Since: Apr 13, 2022
 */

public class ClientBLL {
    private ClientDAO clientDAO;
    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    /**
     * @return: Client
     * @param: client
     * Aceasta metoda insereaza clientul in baza de date
     */
    public Client insertClient(Client client) {
        return clientDAO.insert(client);
    }

    /**
     * @return: void
     * @param: id
     * Aceasta metoda sterge clientul care are id ul trimis ca parametru
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     * @return: void
     * @param: field, value, id
     * Aceasta metoda face update la clientul care are id ul trimis ca parametru
     */
    public void updateClient(String field, String value, int id) {
        clientDAO.update(field, value, id);
    }

    /**
     * @return: Client
     * @param: id
     * Aceasta metoda returneaza clientul care are id ul trimis ca parametru
     */
    public Client findById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * @return: ArrayList<Client>
     * @param:
     * Aceasta metoda returneaza lista de clienti existenta in tabel
     */
    public ArrayList<Client> findAll(){
        ArrayList<Client> list = new ArrayList<Client>();
        list = (ArrayList<Client>) clientDAO.findAll();

        return list;
    }
}
