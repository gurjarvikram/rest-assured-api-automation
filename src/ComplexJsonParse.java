import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//Print number of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Print purchase amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print title of fourth course
		String titleFirstCourse = js.getString("courses[3].title");
		System.out.println(titleFirstCourse);
		
		//Print all course titles and their respective prices
		for(int i=0;i<count;i++)
		{
			String courseTitles=js.get("courses["+i+"].title");
			System.out.println(js.get("courses["+i+"].price").toString());
			
			System.out.println(courseTitles);
		}
		
		System.out.println("Print no of copies sold by RPA Course");
		for(int i=0;i<count;i++)
		{
			String courseTitles=js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA"))
			{
				//Copies sold
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}

		}
	}

}
