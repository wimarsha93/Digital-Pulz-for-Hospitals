package core.resources.inward.treat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;






import lib.driver.inward.driver_class.treat.DischargeDBDrive;

import org.codehaus.jettison.json.JSONObject;

import core.classes.inward.treat.Discharge;
import flexjson.JSONSerializer;

@Path("Discharge")
public class DischargeResource {
	
	DischargeDBDrive requestdbDriver = new DischargeDBDrive();
	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

	@POST
	@Path("/addDischarge")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addDischarge(JSONObject wJson)
	{
				
		try {
			Discharge newterm  =  new Discharge();
										
			String bht_no=wJson.getString("bht_no");					
			int user=wJson.getInt("create_user");
			newterm.setRemark(wJson.getString("remark"));
			newterm.setImage(wJson.getString("image"));	
			newterm.setCreate_date_time(new Date());
			requestdbDriver.addDischarge(newterm,bht_no,user);			 			
			
			return "true";
		} catch (Exception e) {
			 System.out.println(e.getMessage());
			 
			return e.getMessage().toString(); 
		}
	}
	
	@GET
	@Path("/getDischargeByBHTNo/{bhtNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDischargeByBHTNo(@PathParam("bhtNo")  String bhtNo)
	{
				 String result="";
		 List<Discharge> req =requestdbDriver.getDischargeByBHTNo(bhtNo);
		 JSONSerializer serializor=new JSONSerializer();
		 result= serializor.serialize(req);
		 return result;
	}
}
