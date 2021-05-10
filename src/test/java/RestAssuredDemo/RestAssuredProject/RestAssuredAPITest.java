package RestAssuredDemo.RestAssuredProject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
//import static io.restassured.RestAssured.*;//now we can directly use the methods than creating an object
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
//we need to do the static imports of restassured library

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

public class RestAssuredAPITest {
	
	 
// Get Method Request 
	
public static Response doGetRequest(String endpoint) {
    RestAssured.defaultParser = Parser.JSON;

    return
        given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
            when().get(endpoint).
            then().contentType(ContentType.JSON).extract().response();
}

//Post Method Request 

public static Response doDeleteRequest(String endpoint) {
    RestAssured.defaultParser = Parser.JSON;

    return
        given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
            when().delete(endpoint).
            then().contentType(ContentType.JSON).extract().response();
}


/* First Test case 
 * 
 * GET and DELETE Methods to check whether the Status code are Successful
 * 200 - GET request expected response
 * 404 - Delete expected response as resources are deleted
 * 
 */

	@Test(priority=1)
	void Test_01() {
		
			RestAssuredAPITest restapi = new RestAssuredAPITest();
			System.out.println(restapi.getandDeleteEmployees(1432));	
		
		
	}

	public String getandDeleteEmployees(int id) {
		
			Response response1 =RestAssured.get("http://dummy.restapiexample.com/api/v1/employees/");
			
			int get_status_code = response1.getStatusCode();
			//System.out.println("SuccessfullyFetched Employees");
			Assert.assertEquals("Not Successfully Fetched Employees",200, get_status_code);
			
			
			Response response2 =RestAssured.get("http://dummy.restapiexample.com/api/v1/delete/"+id);
				
			int delete_status_code = response2.getStatusCode();
			Assert.assertEquals("Deleted the Employee Successfully", 404, delete_status_code);
				
			String status_message = ((get_status_code == 200) && (delete_status_code == 404)) ? "Successfully Fetched Employees and Deleted Employee" :" Issue Fetching or Deleting Employees from the API";
			
			return status_message;
		
		}
	
	/* Second Test case 
	 * 
	 * GET Methods to fetch specific employee details 
	 * 200 - GET request expected response and also getting the message body with details
	 * 
	 */

	@Test(priority=2)
	void Test_02() {
		RestAssuredAPITest restapi = new RestAssuredAPITest();
		System.out.println(restapi.getSpecificEmployee(1));
		
	}
	
	
	public String getSpecificEmployee(int id) {
		
		Response response3 = doGetRequest("http://dummy.restapiexample.com/api/v1/employee/"+id+"");
		
		String getEmployeeresponse = response3.getBody().asString();
		
	
		
		Assert.assertEquals("Successfully Fetched" +id+ " Employee","{\"status\":\"success\",\"data\":{\"id\":1,\"employee_name\":\"Tiger Nixon\",\"employee_salary\":320800,\"employee_age\":61,"
				+ "\"profile_image\":\"\"},\"message\":\"Successfully! Record has been fetched.\"}", getEmployeeresponse);
		
			
		String status_message1 = (getEmployeeresponse != null) ? "Employee Details are : " + getEmployeeresponse :" Issue in fetching " +id+ "Employee from the API";
		
		return status_message1;
		
		}
	
	/* Third Test case 
	 * 
	 * DELETE Method to delete record of specific id
	 * 200 - Return message "Successfully! Record has been deleted"
	 * 
	 */
	@Test(priority=3)
	public void Test_03() {	
		
		RestAssuredAPITest restapi = new RestAssuredAPITest();
		System.out.println(restapi.DeleteEmployee(2));
		
	}
	
	public String DeleteEmployee(int id) {
		
		Response response = doDeleteRequest("http://dummy.restapiexample.com/api/v1/delete/"+id);
	
	    String deleteResponse = response.jsonPath().getString("message");
	    
	    Assert.assertEquals("Deleted Employee Record","Successfully! Record has been deleted" , deleteResponse);
	    
	    
	    return deleteResponse;
		
		}



}

	