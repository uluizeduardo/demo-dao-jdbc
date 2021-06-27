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

        System.out.println("\n=========== TESTE 4: Seller insert ===========");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0,department);
        sellerDao.insert(newSeller);
        System.out.println("Insert! New Id = " + newSeller.getId());

        System.out.println("\n=========== TESTE 5: Seller Update ===========");
        seller = sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update Completed!");

    }
}
