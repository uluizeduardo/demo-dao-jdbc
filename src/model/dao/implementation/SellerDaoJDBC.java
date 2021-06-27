package model.dao.implementation;

import db.DB;
import db.DbExceptions;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {


    private Connection conect;//Atributo do tipo conexão

    //Construtor para forçar injeção de dependencia da conexão
    public SellerDaoJDBC(Connection conect){
        this.conect = conect;
    }

    //Método inserir dados no banco de dados
    @Override
    public void insert(Seller obj) {
        PreparedStatement statement = null;
        try {
        //Insert no banco de dados
            statement = conect.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);//Retorna o id do vendedor

            //Setando as configurações que vai oculpar as interrogações no sql
            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());

            int linhasAfetadas = statement.executeUpdate();//Variável criada para recebe a execução da query do statement

            if (linhasAfetadas > 0) {//Se linhasAfetadas for > 0 significa que inseriu
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);//pega o id gerado na posição 1 ou seja na primeira coluna da tabela
                    obj.setId(id);//Atribuindo o id ao objeto obj
                }
                DB.closeResultSet(resultSet);
            }
            else {
                throw new DbExceptions("Erro inesperado, nenhuma linha afetada");
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
            //Select no banco de dados
            statement = conect.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");

            statement.setInt(1, id);//Valor atribuido a ? do código sql
            resultSet = statement.executeQuery();//resultSet recebe a execução da query do statement
            if(resultSet.next()){
                Department department = instantiateDepartment(resultSet);//Instância do departamento

                Seller objSeller = instantiateSeller(resultSet, department);//Instância do vendedor

                return  objSeller;//Retorna o Vendedor
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

    //Método auxiliar para instânciar um vendedor
    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {

        Seller objSeller = new Seller();
        objSeller.setId(resultSet.getInt("Id"));
        objSeller.setName(resultSet.getString("Name"));
        objSeller.setEmail(resultSet.getString("Email"));
        objSeller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        objSeller.setBirthDate(resultSet.getDate("BirthDate"));
        objSeller.setDepartment(department);
        return objSeller;
    }

    //Método auxiliar para instânciar um departamento
    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setNome(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            //Busca no banco de dados
            statement = conect.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name");

            resultSet = statement.executeQuery();//resultSet recebe a execução da query do statement

            List<Seller> list = new ArrayList<>();//Lista de Funcionários
            Map<Integer, Department> map = new HashMap<>();//Map para fazer uma verificação na lista

            while (resultSet.next()){//enquanto tiver valores
                Department department = map.get(resultSet.getInt("DepartmentId"));//Instância do departamento recebendo o map

                if (department == null){
                    department = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department);//Salvando departamento no map
                }

                Seller seller = instantiateSeller(resultSet, department);//Instância do vendedor
                list.add(seller);//Adicionando os dados na lista de vendedores
            }
            return list;//retorna a lista de vendedores
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
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            //Select no banco de dados
            statement = conect.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name");

            statement.setInt(1, department.getId());//Valor atribuido a ? do código sql

            resultSet = statement.executeQuery();//resultSet recebe a execução da query do statement

            List<Seller> list = new ArrayList<>();//Lista de Funcionários
            Map<Integer, Department> map = new HashMap<>();//Map para fazer uma verificação na lista

            while (resultSet.next()){//enquanto tiver valores

                Department department1 = map.get(resultSet.getInt("DepartmentId"));//Instância do departamento recebendo o map

                if (department1 == null){
                    department1 = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department1);//Salvando departamento no map
                }

                Seller seller = instantiateSeller(resultSet, department1);//Instância do vendedor
                list.add(seller);//Adicionando os dados na lista de vendedores
            }
            return list;//retorna a lista de vendedores
        }
        catch (SQLException ex){
            throw new DbExceptions(ex.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }
}
