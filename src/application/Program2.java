package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

//        Connection conect = DB.getConnection();
//        DB.closeConnection();


//        Department dp = new Department(1, "Books");
//
//        Seller seller = new Seller(21, "Bob", "bob@email.com", new Date(), 3000.0, dp);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=========== TESTE 1: Department findById ===========");
        Department department = departmentDao.findById(1);
        System.out.println(department);

        System.out.println("\n=========== TESTE 2: Department findAll ===========");
        List<Department> list = departmentDao.findAll();
        list = departmentDao.findAll();
        for (Department obj : list){
            System.out.println(obj);
        }

        System.out.println("\n=========== TESTE 3: Seller insert ===========");
        Department newDepartment = new Department(null, "Music");
        departmentDao.insert(newDepartment);
        System.out.println("Insert! New Id = " + newDepartment.getId());

        System.out.println("\n=========== TESTE 4: Seller Update ===========");
        Department department2 = departmentDao.findById(1);
        department2.setName("Food");
        departmentDao.update(department2);
        System.out.println("Update Completed!");

        System.out.println("\n=========== TESTE 5: Seller Delete ===========");
        System.out.println("Enter id for delete test: ");
        int id = scan.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completede!");

    }
}
