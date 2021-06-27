package model.dao.implementation;

import db.DB;
import db.DbExceptions;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conect;
    public DepartmentDaoJDBC(Connection conect) {
        this.conect = conect;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement statement = null;
        try{
            statement = conect.prepareStatement(
                    "INSERT INTO department "
                    + "(Name)"
                    + "VALUES "
                    + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());

            int linhasAfetadas = statement.executeUpdate();
            if (linhasAfetadas > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            }
            else{
                throw new DbExceptions("Exceção inesperada, nenhuma linha foi afetada! ");
            }
        }
        catch (SQLException ex){
            throw new DbExceptions(ex.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement statement = null;

        try{
            statement = conect.prepareStatement(
                    "UPDATE department "
                    + "SET Name = ? "
                    + "WHERE Id = ?");

            statement.setString(1,obj.getName());
            statement.setInt(2, obj.getId());

            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DbExceptions(ex.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;
        try{
            statement = conect.prepareStatement("DELETE FROM department WHERE Id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        }
        catch (SQLException ex){
            throw new DbExceptions(ex.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            statement = conect.prepareStatement("SELECT * FROM department WHERE Id = ?");
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()){
                Department department = instantiateDepartment(resultSet);
                return department;
            }
            return null;
        }
        catch (SQLException ex){
            throw new DbExceptions(ex.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            statement = conect.prepareStatement("SELECT * FROM department ORDER BY Name");
            resultSet = statement.executeQuery();

            List<Department> list = new ArrayList<>();

            while (resultSet.next()){
                Department department = new Department();
                department.setId(resultSet.getInt("Id"));
                department.setName(resultSet.getString("Name"));
                list.add(department);
            }
            return list;
        }
        catch (SQLException ex){
            throw new DbExceptions(ex.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    //Método auxiliar para instânciar um departamento
    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
