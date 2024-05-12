package Request;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;


public class Sec7_Lec33_DynamicJson {

	@Test(dataProvider="BooksData")
	public void AddBook(String isbn,String aisle){
		
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json").body(request_API.AddBook(isbn,aisle))
		.when().log().all().post("Library/Addbook.php")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.get("ID");
		System.out.println(id);
	}
	
	@Test(dataProvider="BooksData",enabled=true)
	public void DeleteBook(String isbn,String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String delete=given().log().all().header("Content-Type","application/json").body(request_API.DeleteBook(isbn,aisle))
		.when().log().all().post("/Library/DeleteBook.php")
		.then().log().all().extract().response().asString();
		
		JsonPath js1=ReUsableMethods.rawToJson(delete);
		String msg=js1.get("msg");
		System.out.println(msg);
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//Array - Collection of Objects
		//Multi Dimensional Array -  Collection of Array
		//1st obj - isbn	2nd obj - aisle
		return new Object[][] {{"9865","Paris"},{"0909","Veloni"},{"05050","Farzi"}};
		
	}
	
}
