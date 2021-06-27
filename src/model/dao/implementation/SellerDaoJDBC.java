package model.dao.implementation;

import db.DB;
import db.DbExceptions;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {


    private Connection conect;//Atributo do tipo conexão

    //Construtor para forçar injeção de dependencia da conexão
    public SellerDaoJDBC(Connection conect){
        this.conect = conect;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    //Método que pegar um id e consulta no banco de dados se existe esse id lá dento do banco de dados
    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conect.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                Department department = new Department();//Instância do departamento
                department.setId(resultSet.getInt("DepartmentId"));
                department.setNome(resultSet.getString("DepName"));

                Seller objSeller = new Seller();//Instância do vendedor
                objSeller.setId(resultSet.getInt("Id"));
                objSeller.setName(resultSet.getString("Name"));
                objSeller.setEmail(resultSet.getString("Email"));
                objSeller.setBaseSalary(resultSet.getDouble("BaseSalary"));
                objSeller.setBirthDate(resultSet.getDate("BirthDate"));
                objSeller.setDepartment(department);

                return  objSeller;
            }
            return  null;
        }
        catch (SQLException ex){
            throw new DbExceptions(ex.getMessage());
        }
        finally {
           DB.closeStatement(statement);
           DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
