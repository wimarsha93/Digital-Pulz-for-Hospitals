package core.resources.lims;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import core.ErrorConstants;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.ObjectNotFoundException;
import org.jboss.logging.Logger;

import core.classes.lims.Category;
import core.classes.lims.LabDepartments;
import core.classes.lims.LabTestRequest;
import core.classes.lims.Laboratories;
import core.classes.lims.ParentTestFields;
import core.classes.lims.SampleCenters;
import core.classes.lims.SpecimenRetentionType;
import core.classes.lims.SpecimenType;
import core.classes.lims.SubCategory;
import core.classes.lims.TestNames;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import lib.driver.lims.driver_class.CategoryDBDriver;
import lib.driver.lims.driver_class.LaboratoriesDBDriver;
import lib.driver.lims.driver_class.SampleCentersDBDriver;
import lib.driver.lims.driver_class.SpecimenRetentionTypeDBDriver;
import lib.driver.lims.driver_class.SpecimenTypeDBDriver;
import lib.driver.lims.driver_class.TestNamesDBDriver;

@Path("Laboratories")
public class LabResource{

	LaboratoriesDBDriver labDBDriver= new LaboratoriesDBDriver();
DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
DateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	
final static Logger log = Logger.getLogger(LabResource.class);

@POST
@Path("/addNewLaboratory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public String addNewLaboratory(JSONObject pJson) throws JSONException
{
	log.info("Entering the add laboratory method");
	
	try {
		Laboratories labs  =  new Laboratories();
		
		int labTypeID = pJson.getInt("flabType_ID");
		int labDeptID = pJson.getInt("flabDept_ID");
	
		int labDeptCount = pJson.getInt("lab_Dept_Count");
		int userid = pJson.getInt("flab_AddedUserID");
		
		
		labs.setLab_Name(pJson.getString("lab_Name").toString());
		labs.setLab_Incharge(pJson.getString("lab_Incharge").toString());
		labs.setLocation(pJson.getString("location").toString());
		labs.setEmail(pJson.getString("email").toString());
		labs.setContactNumber1(pJson.getString("contactNumber1").toString());
		labs.setContactNumber2(pJson.getString("contactNumber2").toString());
		labs.setLab_Dept_Count(labDeptCount);
		labs.setLab_AddedDate(new Date());
		labs.setLab_LastUpdatedDate(new Date());
		
		labDBDriver.insertNewLab(labs, labTypeID, labDeptID, userid);
		
		

		
		JSONSerializer jsonSerializer = new JSONSerializer();
		return jsonSerializer.include("lab_ID").serialize(labs);
	} 
	catch(JSONException e){
		log.error("JSON exception in adding Laboratory, message:"+ e.getMessage());
		JSONObject jSONError = new JSONObject();
		jSONError.put("Error Code",ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
		jSONError.put("Message",ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
		return jSONError.toString();
	}
	catch (RuntimeException e)
	{
		log.error("JSON exception in adding Laboratory, message:"+ e.getMessage());
		JSONObject jSONError = new JSONObject();
		jSONError.put("Error Code",ErrorConstants.NO_CONNECTION.getCode());
		jSONError.put("Message",ErrorConstants.NO_CONNECTION.getMessage());
		return jSONError.toString();
	}
	catch (Exception e) {
		log.error("Add laboratory error : "+ e.getMessage());
		System.out.println(e.getMessage());
		return null; 
	}

}
		

	
	
	@GET
	@Path("/getAllLaboratories")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllLaboratories() throws JSONException
	{
		log.info("Entering the get all laboratories method");
		try{
		List<Laboratories> labsList =   labDBDriver.getNewLabsList();
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("flabType_ID.lab_Type_Name","flabDept_ID.labDept_Name","flab_AddedUserID.userName","flabLast_UpdatedUserID.userName","labPhone.id.phone","location").exclude("*.class","flabType_ID.*","flabDept_ID.*","labPhone.*","flab_AddedUserID.*","flabLast_UpdatedUserID.*").transform(new DateTransformer("yyyy-MM-dd"),"lab_AddedDate","lab_LastUpdatedDate").serialize(labsList);
		}
		catch (RuntimeException e)
		{
			log.error("JSON exception in adding Laboratory, message:"+ e.getMessage());
			JSONObject jSONError = new JSONObject();
			jSONError.put("Error Code",ErrorConstants.NO_CONNECTION.getCode());
			jSONError.put("Message",ErrorConstants.NO_CONNECTION.getMessage());
			return jSONError.toString();
		}
		catch (Exception e)
		{
			log.error("get laboratory error : "+ e.getMessage());
			System.out.println(e.getMessage());
			return null; 
		}
		
	}
	
	@GET
	@Path("/getLaboratoriesByLabType/{typeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllLaboratoriesByLabType(@PathParam("typeID")int typeID) throws JSONException
	{
		log.info("Entering the get laboratory by lab Type method");
		try{
		List<Laboratories> labsList =   labDBDriver.getLaboratoriesByLabType(typeID);
		JSONSerializer serializer = new JSONSerializer();
		return  serializer.include("flabType_ID.lab_Type_Name","flabDept_ID.labDept_Name","flab_AddedUserID.userName","flabLast_UpdatedUserID.userName","labPhone.id.phone","location").exclude("*.class","flabType_ID.*","flabDept_ID.*","labPhone.*","flab_AddedUserID.*","flabLast_UpdatedUserID.*").transform(new DateTransformer("yyyy-MM-dd"),"lab_AddedDate","lab_LastUpdatedDate").serialize(labsList);
		}
		catch(IndexOutOfBoundsException e)
		{
			log.error("JSON exception in getting Laboratory, message:"+ e.getMessage());
			JSONObject jSONError = new JSONObject();
			jSONError.put("Error Code",ErrorConstants.INVALID_ID.getCode());
			jSONError.put("Message",ErrorConstants.INVALID_ID.getMessage());
			return jSONError.toString();
		}
		catch(RuntimeException e)
		{
			log.error("JSON exception in getting Laboratory, message:"+ e.getMessage());
			JSONObject jSONError = new JSONObject();
			jSONError.put("Error Code",ErrorConstants.NO_CONNECTION.getCode());
			jSONError.put("Message",ErrorConstants.NO_CONNECTION.getMessage());
			return jSONError.toString();
		}
		catch(Exception e)
		{
			
			log.error("get laboratory error : "+ e.getMessage());
			System.out.println(e.getMessage());
			return null; 
		}
	}

	
	@POST
	@Path("/updateLaboratories")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateLabDetails(JSONObject pJson) throws JSONException
	{
		log.info("Entering the update laboratory method");
		
		try {
			//System.out.println(pJson.toString());
			
			Laboratories labs = new Laboratories();
			
			int labTypeID = pJson.getInt("flabType_ID");
			int labDeptID = pJson.getInt("flabDept_ID");
		
			int labDeptCount = pJson.getInt("lab_Dept_Count");
			int userid = pJson.getInt("flabLast_UpdatedUserID");
			//int userid = pJson.getInt("flabLast_UpdatedUserID");
			int labid = pJson.getInt("lab_ID");
			

			labs.setLab_Name(pJson.getString("lab_Name").toString());
			labs.setLab_Incharge(pJson.getString("lab_Incharge").toString());
			labs.setLocation(pJson.getString("location").toString());
			labs.setEmail(pJson.getString("email").toString());
			labs.setContactNumber1(pJson.getString("contactNumber1").toString());
			labs.setContactNumber2(pJson.getString("contactNumber2").toString());
			labs.setLab_Dept_Count(labDeptCount);
			labs.setLab_AddedDate(new Date());
			labs.setLab_LastUpdatedDate(new Date());
			
			labDBDriver.updateLaboratories(labid, labs, labTypeID, labDeptID, userid);
			
			List<Laboratories> labsList =   labDBDriver.getNewLabsList();
			String id = labsList.get(labsList.size() - 1).getLab_ID() + "";
			return id;	
			
		}
		catch(RuntimeException e)
		{
			log.error("JSON exception in updating Laboratory, message:"+ e.getMessage());
			JSONObject jSONError = new JSONObject();
			jSONError.put("Error Code",ErrorConstants.NO_CONNECTION.getCode());
			jSONError.put("Message",ErrorConstants.NO_CONNECTION.getMessage());
			return jSONError.toString();
		}
		catch(JSONException e){
			log.error("JSON exception in updating Laboratory, message:"+ e.getMessage());
			JSONObject jSONError = new JSONObject();
			jSONError.put("Error Code",ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jSONError.put("Message",ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
			return jSONError.toString();
		}
		catch (Exception e) {
			 
			log.error(e.getMessage());
			return e.getMessage();
		}
	}
	
	@GET
	@Path("/deleteLab/{labid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteLab(@PathParam("labid") int labid) throws JSONException{
		//String status="";
		log.info("Entering the Delete laboratory method");
		try {
			
			labDBDriver.DeleteLab(labid);
			
			return "True";	
		}
		
		catch (ObjectNotFoundException e)
		{
			log.error("JSON exception in deleting Laboratory, message:"+ e.getMessage());
			JSONObject jSONError = new JSONObject();
			jSONError.put("Error Code",ErrorConstants.INVALID_ID.getCode());
			jSONError.put("Message",ErrorConstants.INVALID_ID.getMessage());
			return jSONError.toString();
		}
		
		catch (Exception e) {
			log.error(e.getMessage());
			return e.getMessage();
		}
	}
	
	}
	


