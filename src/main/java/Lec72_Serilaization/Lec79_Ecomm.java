package Lec72_Serilaization;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

public class Lec79_Ecomm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
	
		
		LoginRequest loginrequest =new LoginRequest();
		loginrequest.setUserEmail("ashwinudemy01@gmail.com");
		loginrequest.setUserPassword("Abc@1234");
		
		RequestSpecification req1=given().log().all().spec(req).body(loginrequest);
		
		System.out.println(req1);	// No output received, why?  --> The output is not received because you are only printing the RequestSpecification object req1, 
		//but you are not sending the request and capturing the response. To send the request and capture the response, 
		//you can use the post() method along with the Response class.
		
		
		LoginResponse loginresponse=req1.when().post("api/ecom/auth/login").then().extract().response()
				.as(LoginResponse.class);
		
		String token = loginresponse.getToken();
		String userID= loginresponse.getUserId();
		
		System.out.println("Token: "+loginresponse.getToken());
		System.out.println("UserID: "+loginresponse.getUserId());
		System.out.println("Message: "+loginresponse.getMessage());
		
		
		//--------------------- ADD PRODUCT Lec 80 ------------------------//
		
		RequestSpecification addProductBaseReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification addrequest=given().log().all().spec(addProductBaseReq)
				.param("productName", "Dress").param("productAddedBy", userID) .param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", 12500)
				.param("productDescription","Dress").param("productFor","women")
				.multiPart("productImage",new File("C:\\Users\\Admin\\Downloads\\Green.jpg"));
		
		String resProductResponse= addrequest.when().post("/api/ecom/product/add-product").then().extract().response().asString();
		JsonPath js=new JsonPath(resProductResponse);
		String productId=js.get("productId");
		
		System.out.println("Product ID : "+productId);
		
		
		
		//-------------------- CREATE PRODUCT Lec 81 ------------------//
		
//		RequestSpecification createOrderBaseReq=	new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
//				.addHeader("authorization", token).setContentType(ContentType.JSON)
//				.build();
//		OrderDetail orderDetail = new OrderDetail();
//		orderDetail.setCountry("India");
//		orderDetail.setProductOrderedId(productId);
//		
//		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail> ();
//		orderDetailList.add(orderDetail);	
//		Orders orders = new Orders();
//		orders.setOrders(orderDetailList);
//		
//	RequestSpecification createOrderReq=given().log().all().spec(createOrderBaseReq).body(orders);
//
//	String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
//	System.out.println(responseAddOrder);


		
	
		RequestSpecification createOrder_req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetail order=new OrderDetail();
		order.setCountry("India");
		order.setProductOrderedId(productId);
		
		 List<OrderDetail> orderDetailList=new ArrayList<OrderDetail>();
		 orderDetailList.add(order);		
		 
		 Orders orders=new Orders();
		 orders.setOrders(orderDetailList);
		
		 RequestSpecification createOrder=given().log().all().spec(createOrder_req).body(orders);
		 
		String createOrder_response= createOrder.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
		System.out.println(createOrder_response);
		
		
		
		//----------------------------DELETE -------------------------//
		
		RequestSpecification deleteeOrder_req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification delete=given().log().all().spec(deleteeOrder_req).pathParam("productId",productId);
		
		String deleteResponse = delete.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
				.extract().response().asString();
		
		//JsonPath js1=new JsonPath(deleteResponse);
		//String a= js1.get("message").toString();
		
		System.out.println(deleteResponse);
		
		
		
		
	}

}
