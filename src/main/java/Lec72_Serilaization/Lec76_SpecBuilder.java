package Lec72_Serilaization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class Lec76_SpecBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		AddPlace p=new AddPlace();
		p.setAccuracy(50);
		p.setAddress("Lokgram, Kalyan(E)");
		p.setLang("French-IN");
		p.setPhone_no("+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Ashwin house");
		
		List<String> m=new ArrayList<String>();
		m.add("shoe");
		m.add("Football");
		p.setTypes(m);
		
		Location l=new Location();
		l.setLat(-38.92146);
		l.setLng(46.34523);
		p.setLocation(l);
		
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		
		ResponseSpecification res=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification req1=given().spec(req).body(p);
		
		Response res1=req1.when().post("/maps/api/place/add/json").then().spec(res).extract().response();
		
		String responseString=res1.asString();
		System.out.println("Output"+responseString);
		
	}

}
