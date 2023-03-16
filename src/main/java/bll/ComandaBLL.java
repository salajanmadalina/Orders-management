package bll;

import dao.ComandaDAO;
import model.Comanda;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @Author: Salajan Madalina-Maria
 * @Since: Apr 13, 2022
 */

public class ComandaBLL {

    private ComandaDAO orderDAO;

    public ComandaBLL() {
        orderDAO = new ComandaDAO();
    }

    /**
     * @return: Comanda
     * @param: order
     * Aceasta metoda insereaza comanda in baza de date
     */
    public Comanda insertOrder(Comanda order) {
        return orderDAO.insert(order);
    }

    /**
     * @return: void
     * @param: id
     * Aceasta metoda sterge comanda care are id ul trimis ca parametru
     */
    public void deleteOrder(int id) {
        orderDAO.delete(id);
    }

    /**
     * @return: void
     * @param: field, value, id
     * Aceasta metoda face update la comanda care are id ul trimis ca parametru
     */
    public void updateOrder(String field, String value, int id) {
        orderDAO.update(field, value, id);
    }


    /**
     * @return: Comanda
     * @param: id
     * Aceasta metoda returneaza comanda care are id ul trimis ca parametru
     */
    public Comanda findById(int id) {
        Comanda st = orderDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return st;
    }


    /**
     * @return: ArrayList<Comanda>
     * @param:
     * Aceasta metoda returneaza lista de comenzi existenta in tabel
     */
    public ArrayList<Comanda> findAll(){
        ArrayList<Comanda> list = new ArrayList<Comanda>();
        list = (ArrayList<Comanda>) orderDAO.findAll();

        return list;
    }
}
