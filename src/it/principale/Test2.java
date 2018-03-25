package it.principale;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import it.principale.dao.Employee;
import it.principale.dao.EmployeeDao;

@SuppressWarnings("deprecation")
public class Test2 {

	public static void main(String[] args) {
		Resource r=new ClassPathResource("applicationContext.xml");  
		BeanFactory factory=new XmlBeanFactory(r);  
	    
	    EmployeeDao dao=(EmployeeDao)factory.getBean("edao");
	    

		List<Employee> list3=dao.findAll();
		System.out.println(" ");
		System.out.println("EMPLOYEE BY NAME Mario con NamedParameter");
		for(Employee x: list3) {
			System.out.println(x.getId()+ "-------"+x.getName());
		}
	    
	}

}
