import org.testng.Assert;

import Request.request_API;
import io.restassured.path.json.JsonPath;


/*
1. Print No of courses returned by API

2.Print Purchase Amount

3. Print Title of the first course

4. Print All course titles and their respective Prices

5. Print no of copies sold by RPA Course

6. Verify if Sum of all Course prices matches with Purchase Amount

*/
public class Lec24_ComplexJson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	JsonPath js=new JsonPath(request_API.Courseprice());
	
	int c=js.getInt("courses.size()");
	System.out.println("No of courses returned by API "+c);
	
	int pa=js.getInt("dashboard.purchaseAmount");
	System.out.println("Purchase Amount "+pa);
	
	String fc=js.get("courses[0].title");
	System.out.println("Title of the first course "+fc);
	
	System.out.println("------Print All course titles and their respective Prices-----");
	
	for(int i=0;i<c;i++)
	{
		String title=js.get("courses["+i+"].title");
		System.out.println(title);
		
		
		System.out.println(js.get("courses["+i+"].price").toString());
	}
	
	System.out.println("--");
	System.out.println("-----Print no of copies sold by RPA Course------");
	
	for(int i=0;i<c;i++)
	{
		String title=js.get("courses["+i+"].title");
		if(title.equalsIgnoreCase("RPA"))
		{
			String copies=js.get("courses["+i+"].copies").toString();
			System.out.println(copies);
		}
	}

	System.out.println("=========Verify if Sum of all Course prices matches with Purchase Amount=========");
	int b=0;
	for(int i=0;i<c;i++)
	{
		String title=js.get("courses["+i+"].title");
		int a=js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
		
		b=a+b;
		System.out.println(b);
	
	}
	
	int pa1=js.getInt("dashboard.purchaseAmount");
	if(pa1==b) 
	{
		System.out.println("Amount matched");
	}
	Assert.assertEquals(pa1, b);
	
	
	}
}
