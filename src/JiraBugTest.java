import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;	

public class JiraBugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy-team.atlassian.net/";
		
		String createIssueResponse = given()
		.header("Content-Type","application/json")
		.header("Authorization","Basic bWVudG9yQHJhaHVsc2hldHR5YWNhZGVteS5jb206QVRBVFQzeEZmR0YwdFNlOHYzNUtILWQtU3U4NUFMckIyTjdDNXIwY0pJU0djdFIwRFBybUhfZjVlUmg4dE5UUVV6UVp1dTFkMXJHdkRjUzNHRnV4TVE4WklSNU9tdFlPbUszLUxBbVU4OEFTM3JrOGkwODFSYV9kQTlPQ3J5QjRERXlFWldJYXpwWGw3VDFTWnBLY0ZOSDBucjVBMUtLQ3FuWVBldzFLR2JSMWowa2JFdGVNVFZFPUZCMzhFM0JB")
		.body("{\n"
				+ "    \"fields\": {\n"
				+ "       \"project\":\n"
				+ "       {\n"
				+ "          \"key\": \"SCRUM\"\n"
				+ "       },\n"
				+ "       \"summary\": \"Website items are not working- automation Rest Assured\",\n"
				+ "       \"issuetype\": {\n"
				+ "          \"name\": \"Bug\"\n"
				+ "       }\n"
				+ "   }\n"
				+ "}")
		.log().all()
		.when()
		.post("rest/api/3/issue")
		.then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = new JsonPath(createIssueResponse);
		String issueId = js.getString("id");
		
		System.out.println(issueId);
		
		//Add attachment
		given()
		.pathParam("key", issueId)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic bWVudG9yQHJhaHVsc2hldHR5YWNhZGVteS5jb206QVRBVFQzeEZmR0YwdFNlOHYzNUtILWQtU3U4NUFMckIyTjdDNXIwY0pJU0djdFIwRFBybUhfZjVlUmg4dE5UUVV6UVp1dTFkMXJHdkRjUzNHRnV4TVE4WklSNU9tdFlPbUszLUxBbVU4OEFTM3JrOGkwODFSYV9kQTlPQ3J5QjRERXlFWldJYXpwWGw3VDFTWnBLY0ZOSDBucjVBMUtLQ3FuWVBldzFLR2JSMWowa2JFdGVNVFZFPUZCMzhFM0JB")
		.multiPart("file", new File("/home/b/Desktop/REST-API/dummy_img.png"))
		.log().all()
		.when()
		.post("rest/api/3/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
//		
		
	}

}
