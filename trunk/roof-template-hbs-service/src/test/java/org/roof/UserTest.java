package org.roof;

import java.util.ArrayList;
import java.util.List;

import org.roof.model.User;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

public class UserTest {
    private static final String templateName = "/templates/handlebars/user";

	public static void main(String[] args) {
		
		try {
			List<User> list = new ArrayList<User>();
			list.add(new User("张三",12));
			list.add(new User("李四",5));
			list.add(new User("王五",6));
			Handlebars handlebars = new Handlebars();
			
			    
			Template template = handlebars.compile(templateName);
			System.out.println(template.apply(list));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
