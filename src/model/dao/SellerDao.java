package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller obj);//Operação para inserir no banco de dados o obj do tipo Department
    void update(Seller obj);//Operação para atualizar no banco de dados o obj do tipo Department
    void deleteById(Integer id);//Operação para deletar no banco de dados um objeto pelo id
    Seller findById(Integer id);//Operação que pegar um id e consulta no banco de dados se tem esse id lá
    List<Seller> findAll();//Operação para retornar todos os dados do Department
    List<Seller> findByDepartment(Department department);//Operaçao de busca por departamento
}
