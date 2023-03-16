package dao;

import connection.ConnectionFactory;
import java.beans.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Salajan Madalina-Maria
 * @Since: Apr 13, 2022
 */

public class AbstractDAO<T> {
    private final Class<T> type;
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * @return: List<T>
     * @param: resultSet
     * Aceasta metoda returneaza o lista de obiecte T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @return: List<T>
     * @param:
     * Aceasta metoda afiseaza toate inregistrarile dintr-un tabel
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * @return: String
     * @param: field
     * Aceasta metoda creeaza o interogare care returneaza toate inregistrarile dintr un tabel
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * @return: T
     * @param: id
     * Aceasta metoda returneaza elementul cu id-ul trimis ca parametru
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    /**
     * @return: String
     * @param: field, id
     * Aceasta metoda creeaza o interogare care face update intr-un tabel la o inregistrare
     */
    private String createUpdateQuery(String field, int id){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE " + type.getSimpleName() + " SET " + field + " =? WHERE id = " + id);
        return sb.toString();
    }

    /**
     * @return: T
     * @param: f, f1, id
     * Aceasta metoda face update la inregistrarea cu id-ul trimis ca parametru
     */
    public T update(String f, String f1, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery(f, id);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, f1);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * @return: String
     * @param: t
     * Aceasta metoda creeaza o interogare care insereaza intr-un tabel o noua linie
     */
    private String createInsertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append("(");

        Field[] allFields = t.getClass().getDeclaredFields();
        StringBuilder values = new StringBuilder();

        try {
            for(int i = 0; i < allFields.length - 1; i ++){
                allFields[i].setAccessible(true);

                sb.append(allFields[i].getName() + ", ");
                Object obj = allFields[i].get(t);

                String fieldType = allFields[i].getType().getSimpleName();
                if (fieldType.equals("String"))
                    values.append("\"").append(obj).append("\"");
                else
                    values.append(obj);
                values.append(", ");

            }

            allFields[allFields.length - 1].setAccessible(true);
            sb.append(allFields[allFields.length - 1].getName());
            Object value = allFields[allFields.length - 1].get(t);
            String fieldType = allFields[allFields.length - 1].getType().getSimpleName();

            if (fieldType.equals("String"))
                values.append("\"").append(value).append("\"");
            else
                values.append(value);

            sb.append(") VALUES (").append(values).append(")");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    /**
     * @return: T
     * @param: t
     * Aceasta metoda insereaza un element in tabel
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:Insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * @return: String
     * @param:
     * Aceasta metoda creeaza o interogare care sterge dintr-un tabel o inregistrare
     */
    private String createDeleteQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM " + type.getSimpleName() + " WHERE id = ?");
        return sb.toString();
    }

    /**
     * @return: T
     * @param: id
     * Aceasta metoda sterge inregistrarea cu id-ul trimis ca parametru dintr-un tabel
     */
    public T delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

}
