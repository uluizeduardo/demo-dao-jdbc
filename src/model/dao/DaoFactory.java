package model.dao;

import db.DB;
import model.dao.implementation.DepartmentDaoJDBC;
import model.dao.implementation.SellerDaoJDBC;

//Classe auxiliar responsável por instânciar o Dao
public class DaoFactory {

    //Operação statica para instanciar o intânciar o SellerDao do tipo SellerDaoJDBC
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }

    //Operação statica para instânciar a instância do DepartmentDao do tipo DepartmentDaoJDBC
    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
