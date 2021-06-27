package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {

//        Connection conect = DB.getConnection();
//        DB.closeConnection();


//        Department dp = new Department(1, "Books");
//
//        Seller seller = new Seller(21, "Bob", "bob@email.com", new Date(), 3000.0, dp);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=========== TESTE 1: Seller findById ===========");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=========== TESTE 2: Seller findByDepartment ===========");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller x : list){
            System.out.println(x);
        }

        System.out.println("\n=========== TESTE 3: Seller findAll ===========");
        list = sellerDao.findAll();
        for (Seller obj : list){
            System.out.println(obj);
        }


    }
}
