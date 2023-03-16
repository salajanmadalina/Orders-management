package bll;

import dao.ProdusDAO;
import model.Produs;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @Author: Salajan Madalina-Maria
 * @Since: Apr 13, 2022
 */

public class ProdusBLL {
    private ProdusDAO produsDAO;
    public ProdusBLL() {
        produsDAO = new ProdusDAO();
    }

    /**
     * @return: Produs
     * @param: produs
     * Aceasta metoda insereaza produsul in baza de date
     */
    public Produs insertProdus(Produs produs) {
        return produsDAO.insert(produs);
    }

    /**
     * @return: void
     * @param: id
     * Aceasta metoda sterge produsul care are id ul trimis ca parametru
     */
    public void deleteProdus(int id) {
        produsDAO.delete(id);
    }

    /**
     * @return: void
     * @param: field, value, id
     * Aceasta metoda face update la produsul care are id ul primit ca parametru
     */
    public void updateProdus(String field, String value, int id) {
        produsDAO.update(field, value, id);
    }

    /**
     * @return: Produs
     * @param: id
     * Aceasta metoda returneaza produsul care are id ul trimis ca parametru
     */
    public Produs findById(int id) {
        Produs st = produsDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The produs with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * @return: ArrayList<Produs>
     * @param:
     * Aceasta metoda returneaza lista de produse existente in tabel
     */
    public ArrayList<Produs> findAll(){
        ArrayList<Produs> list = new ArrayList<Produs>();
        list = (ArrayList<Produs>) produsDAO.findAll();

        for(int i = 0; i < list.size(); i ++){
            if(list.get(i).getQuantity() == 0){
                list.remove(i);
                i --;
            }
        }

        return list;
    }
}
