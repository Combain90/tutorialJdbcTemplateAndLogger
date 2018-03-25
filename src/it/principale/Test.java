package it.principale;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.principale.dao.Employee;
import it.principale.dao.EmployeeDao;

public class Test {

	private static Logger log = Logger.getLogger(Test.class);
	public static void main(String[] args) {

		log.info("Starting Test class");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
		log.debug("Created Spring context");
		EmployeeDao dao = (EmployeeDao) ctx.getBean("edao");
		log.debug("now we have a dao");
		
		EmployeeDao dao2 = (EmployeeDao) ctx.getBean("edao");
		log.debug("now we have the same instance in dao2");
		
		// int status=dao.saveEmployee(new Employee(115,"Amit",35000));
		// System.out.println(status);

		/*
		 * int status=dao.updateEmployee(new Employee(102,"Sonoo",15000));
		 * System.out.println(status);
		 */

		// Employee e=new Employee();
		// e.setId(102);
		// status=dao.deleteEmployee(e);
		// System.out.println(status);
		//dao.saveEmployeeByPreparedStatement(new Employee(156, "Luigi2", 4590));
		
		dao2.savewithParamater(new Employee(198,"Mario",35044));
		
		List<Employee> list = dao.getAllEmployeesRowMapper();
		for (Employee x : list) {
			System.out.println(x.getId() + " - " + x.getName());
		}

		List<Employee> list2 = dao.getEmployeeByName("Amit");
		System.out.println(" ");
		System.out.println("EMPLOYEE BY NAME Amit");
		for (Employee x : list2) {
			System.out.println(x.getId() + " - " + x.getName());
		}

		log.info("Exiting");
		
		
		List<Employee> list3=dao2.getEmployeeByNamewithParamater("Mario");
		System.out.println(" ");
		System.out.println("EMPLOYEE BY NAME Mario con NamedParameter");
		for(Employee x: list3) {
			System.out.println(x.getId()+ "-------"+x.getName());
		}
	}
}
