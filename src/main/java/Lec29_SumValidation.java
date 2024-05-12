import org.testng.annotations.Test;

import Request.request_API;
import io.restassured.path.json.JsonPath;

public class Lec29_SumValidation {

	@Test
	public void sumofcourses()
	{
		JsonPath js=new JsonPath(request_API.Courseprice());
		
		int c=js.getInt("courses.size()");
		int b=0;
		for(int i=0;i<c;i++)
		{
			int a=js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
			
			b=a+b;
			System.out.println(b);
		
		}
	}
}
