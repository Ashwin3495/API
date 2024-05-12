package Lec72_Serilaization;

import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

public class Lec73_Serialize {

	public static void main(String[] args) {
	
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//calling the class Add Place from pojo
		AddPlace p=new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLang("French-IN");
		p.setPhone_no("(+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Frontline house");
		List<String> mylist=new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		
		p.setTypes(mylist);
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		p.setLocation(l);
		
		Response res=given().log().all().queryParam("key", "qaclick123")
		.body(p)
		.when().post("/maps/api/place/add/json").
		then().assertThat().statusCode(200).extract().response();
		
		
		
	}
}
