import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Request.request_API;


public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//given - to input details
		//when - to submit the API request
		//then - Validate the response received
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		// to enter the query parameter, header, request api
		String response= given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		//.body(request_API.AddPlace())	
				
			//USE OF STATIC JSON FORM LOCAL FILEs	
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Admin\\eclipse-workspace\\Static_Json.json"))))
		//Http method and resource map from url
		.when().log().all().post("maps/api/place/add/json")
		
		//to validate the status code
		//when body header is written with then , it is used to validate the response
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).body("status", equalTo("OK"))
		.header("server", "Apache/2.4.52 (Ubuntu)").header("Content-Type", "application/json;charset=UTF-8").extract().response().asString();
		
		System.out.println(response);
		
		//Using Json to read the output after converting to String
		JsonPath js=new JsonPath(response);
		String place_id=js.getString("place_id");		
		System.out.println("PID:"+place_id);
		
		
		//Update Place
		
		String address="Ashwin Yadav :)";
		
	String update=	given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"place_id\": \""+place_id+"\",\r\n"
				+ "    \"address\": \""+address+"\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}")
		
		//change the method over here "put" . Add update in the url
		.when().log().all().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
		
		
		System.out.println("UPDATE : "+update);
		
		
		//Get Place
		
		String get = given().log().all().queryParam("key", "qaclick123").queryParam("place_id",place_id)
		.when().log().all().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();

		JsonPath js1=new JsonPath(get);
		String newaddress=js1.getString("address");
		System.out.println("New Address:"+newaddress);
}
}
