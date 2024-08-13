import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;

public class Basics {

    public static void main(String[] args) throws IOException {
    	
        // Set base URI
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Validate if the Add Place API is working as expected
        //Add place -> update place with new address -> Get place to validate if new address is present in response
        
        //Given - all input details
        //When - submit the API  - resources, http method
        //Then - validate the response
        //Content of the file to string -> content of file can convert into Byte -> Byte data to string
        
        
        String addPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(new String (Files.readAllBytes(Paths.get("/home/b/Desktop/REST-API/addPlace/addPlace.json"))))
                .when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .extract().response().asString();

        System.out.println("Add place response: " + addPlaceResponse);

        JsonPath js = new JsonPath(addPlaceResponse); // for parsing Json
        String placeId = js.getString("place_id");

        if (placeId == null || placeId.isEmpty()) {
            throw new RuntimeException("Place ID not found in Add Place response");
        }

        System.out.println("Place ID: " + placeId);

        
        
        // Update place
        String newAddress = "Summer walk, Africa";

        Response updateResponse = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" + "    \"place_id\": \"" + placeId + "\",\n" + "    \"address\": \"" + newAddress + "\"\n"
                        + "}")
                .when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200).extract()
                .response();

        // Check if the response body is empty
        String updateResponseBody = updateResponse.asString();
        System.out.println("Update Place Response: " + updateResponseBody);

        
        // Get place
        String GetPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
                .response().asString();

        JsonPath js1 = new JsonPath(GetPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println("Actual Address: " + actualAddress);
        Assert.assertEquals(actualAddress, newAddress);
    }
}
