/*
-----------------------------------------------------------------------------------------------------------------------------------
HIS � Health Information System - RESTful  API
-----------------------------------------------------------------------------------------------------------------------------------
This is a free and open source API which can be used to develop and distributed in the hope that it will be useful to develop EMR systems.
You can utilize the services provides by the API to speed up the development process. 
You can modify the API to cater your requirements at your own risk. 
 
-----------------------------------------------------------------------------------------------------------------------------------
Authors: H.L.M.M De Silva, K.V.M Jayadewa, G.A.R Perera, S.I Kodithuwakku
Supevisor: Dr. Koliya Pulasinghe | Dean /Faculty of Graduate Studies |SLIIT
Co-Supervisor: Mr.Indraka Udayakumara | Senior Lecturer | SLIIT
URL: https://sites.google.com/a/my.sliit.lk/his
----------------------------------------------------------------------------------------------------------------------------------
*/
package core.resources.pharmacy;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.print.attribute.standard.Media;
import javax.swing.text.DateFormatter;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import flexjson.*;
import flexjson.transformer.DateTransformer;

import org.codehaus.jettison.json.JSONException;
import org.hibernate.Session;
import org.jboss.logging.Logger;
/*import org.json.simple.JSONObject;
import org.json.simple.JSONArray;*/
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONArray;











//import com.pharmacy.persistence.DrugAction;










import com.sun.research.ws.wadl.Application;

import core.ErrorConstants;
import core.classes.api.user.AdminUser;


import core.classes.opd.Allergy;
import core.classes.opd.Prescription;
import core.classes.pharmacy.MstDrugCategory;
import core.classes.pharmacy.MstDrugDosage;
import core.classes.pharmacy.MstDrugFrequency;
import core.classes.pharmacy.MstDrugsNew;
import core.classes.pharmacy.MstMailHistory;
import core.classes.pharmacy.MstPharmDepartment;
import core.classes.pharmacy.TrnDispenseDrugs;
import core.classes.pharmacy.TrnDrugsSupplied;
import core.classes.pharmacy.TrnDrugsSuppliedId;
import core.classes.pharmacy.TrnRequestDrugs;
import lib.driver.hr.driver_class.HrDepartmentDBDriver;
import lib.driver.api.driver_class.user.UserDBDriver;
import lib.driver.opd.driver_class.PrescriptionDBDriver;
import lib.driver.pharmacy.driver_class.DrugDBDriver;
import lib.SessionFactoryUtil;
/*import com.pharmacy.persistence.PrescriptionAction;
import com.pharmacy.persistence.UserAction;*/

/***
 * This class define all the generic REST Services necessary for handling pharmacy
 * @author Vishwa
 *
 */
@Path("DrugServices")
public class DrugResource {

	final static Logger logger=Logger.getLogger(DrugResource.class);
	
	DrugDBDriver drugDbDriver=new DrugDBDriver();
	PrescriptionDBDriver prescriptionDbDriver = new PrescriptionDBDriver();
	UserDBDriver userDbDriver=new UserDBDriver();
	
	/**
	 * Insert a new Master Drug
	 * @author Vishwa
	 * @param json
	 * @return a string value.True if the Data inserted successfully else it returns false
	 * @throws JSONException 
	 */
	@POST
	@Path("/insertDrug")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertDrug(JSONObject json) throws JSONException{
		logger.info("Entering insertDrug method...");
		Date date = new Date();
		String status = "";
		int catid;
		try {
			
			System.out.println(json);
			MstDrugsNew drug = new MstDrugsNew();

			drug.setdName(json.get("dname").toString());
			drug.setdUnit(json.get("dtype").toString());
			drug.setdPrice(Double.parseDouble(json.get("dprice").toString()));
			drug.setdRemarks(json.get("drem").toString());
			drug.setdCreateDate(date);
			drug.setdLastUpdate(date);			
			drug.setdCreateUser(json.getInt("userid"));
			drug.setdLastUpdateUser(json.getInt("userid"));			
			drug.setdQty(0);
			drug.setStatusDanger(Integer.parseInt(json.get("ddanger").toString()));
			drug.setStatusReOrder(Integer.parseInt(json.get("dreorder").toString()));
			drug.setdActive(false);
			MstDrugDosage dos = new MstDrugDosage();

			dos.setDosId(Integer.parseInt(json.get("ddosageid").toString()));
			dos.setDosage(json.get("ddosage").toString());

			Set<MstDrugDosage> dosages = new HashSet<MstDrugDosage>(0);
			dosages.add(dos);
			drug.setDosages(dosages);

			// drug.getDosages().add(dos);

			MstDrugFrequency freq = new MstDrugFrequency();
			freq.setFreqId(Integer.parseInt(json.get("dfrequencyid").toString()));
			freq.setFrequency(json.get("dfrequency").toString());

			Set<MstDrugFrequency> frequencies = new HashSet<MstDrugFrequency>(0);
			frequencies.add(freq);
			drug.setFrequencies(frequencies);
			// drug.getFrequencies().add(freq);
			catid = Integer.parseInt(json.get("dcatid").toString());

			if (drugDbDriver.insertDrug(drug, catid)) {
				status = "Drug Added Successfully!!!";
				logger.info("Drug inserted ");
			} else {
				status = "fail";
				logger.error("Error in inserting drugs");
			}
			return status;

		} 
		catch(org.hibernate.exception.GenericJDBCException e)
		{
			logger.error("Exception in inserting new Drug"+e.getMessage());
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.ENTRY_ALREADY_EXISTS.getCode());
			jsonErrorObject.put("message", ErrorConstants.ENTRY_ALREADY_EXISTS.getMessage());
			
			return jsonErrorObject.toString();
		}
		catch( java.lang.Error e)
		{
			logger.error("Exception in inserting new Drug"+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(RuntimeException e)
		{
			logger.error("Exception in inserting new Drug"+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(JSONException e)
		{
			logger.error("Exception in inserting new Drug"+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
								
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			logger.error("Exception in inserting new Drug"+e.getMessage());
//			return e.getMessage();
			return e.toString();
		}
		
	}
	
	
	/**
	 * Update an existing Drug
	 * @author Vishwa
	 * @param json
	 * @return a string value.True if the Data Updated successfully else it returns false
	 * @throws JSONException 
	 */
	@POST
	@Path("/updateDrug")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateDrug(JSONObject json) throws JSONException {
		logger.info("Entering updateDrug method...");
		Date date = new Date();
		String status = "";
		int catid;
		try {
			MstDrugsNew drug = new MstDrugsNew();

			drug.setdName(json.get("dname").toString());
			drug.setdUnit(json.get("dtype").toString());
			drug.setdPrice(Double.parseDouble(json.get("dprice").toString()));
			drug.setdRemarks(json.get("drem").toString());
			drug.setdCreateDate(date);
			drug.setdLastUpdate(date);
			drug.setdCreateUser(json.getInt("userid"));
			drug.setdLastUpdateUser(json.getInt("userid"));
			drug.setStatusDanger(Integer.parseInt(json.get("ddanger")
					.toString()));
			drug.setStatusReOrder(Integer.parseInt(json.get("dreorder")
					.toString()));

			
			
			if (drugDbDriver.updateDrugDetails(drug)) {
				status = "Drug Updated Successfully!!!";
				logger.info("Drug updated");
			} else {
				status = "fail";
				logger.error("Drug not updated");
			}

			
			return status;

		} 
		catch(JSONException e)
		{
			logger.error("Exception in updating drugs "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}
		catch(NullPointerException e)
		{
			logger.error("Exception in updating drugs "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.INVALID_ID.getCode());
			jsonErrorObject.put("message", ErrorConstants.INVALID_ID.getMessage());
					
			return jsonErrorObject.toString();
		}
		catch(org.hibernate.exception.JDBCConnectionException e)
		{
			logger.error("Exception in updating drugs "+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch (RuntimeException e) {
			// TODO: handle exception
			logger.error("Exception in updating drugs "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
								
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			logger.error("Exception in updating drugs "+e.getMessage());
			return e.getMessage();
		}

	}
	
	
	/**
	 * Gives the Drug Id for a given Drug Name
	 * @author Vishwa
	 * @param dname
	 * @return A JSON object that contains all the Drug Details
	 * @throws JSONException 
	 */
	@GET
	@Path("/getDrugIdByDrugName/{dname}")
	@Produces (MediaType.APPLICATION_JSON)
	public String getDrugIdByDrugName(@PathParam("dname") String dname) throws JSONException
	{
		logger.info("Entering getDrugIdByDrugName...");
		Integer srNo=0;
		try {
			srNo = drugDbDriver.getDrugIDByDrugName(dname);
			logger.info("Drug id of "+dname+": "+srNo);
			
		} 
		catch(RuntimeException e){
			logger.error("Exception in getting drug id of "+dname+": "+e.getMessage());
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
								
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception in getting drug id of "+dname+": "+e.getMessage());
			return e.getMessage().toString();
//			return e.toString();
		}
		
		
		
		return srNo.toString();
	}
	
	
	/**
	 * Gives the Drug details for the given Drug ID
	 * @author Vishwa
	 * @param srNo
	 * @return A JSON object that contains all the Drug Details
	 * @throws JSONException 
	 */
	@GET
	@Path("/getDrugByID/{srNo}")
	@Produces (MediaType.APPLICATION_JSON)
	public String getDrugByID(@PathParam("srNo") int srNo) throws JSONException
	{
		logger.info("Entering getDrugByID method...");
		
		List<MstDrugsNew> drug = null;
		try {
			
			drug = drugDbDriver.getDrugByID(srNo);
			logger.info("Getting drug of drug Id "+srNo+": "+drug);
			
			JSONSerializer serializer = new JSONSerializer();
			return serializer.include("dSrNo","dName","dUnit","categories.dCategory","dPrice","dQty","frequency.frequency","dosage.dosage","statusDanger","statusReOrder").exclude("*").serialize(drug);
		} 
		catch(RuntimeException e){
			logger.error("Exception in getting drug of drug Id "+srNo+": "+e.getMessage());
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
								
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			
			logger.error("Exception in getting drug of drug Id "+srNo+": "+e.getMessage());
			return e.toString();
		}
		
		
	}
	
	
	/**
	 * Gives the entire drug details which
	 * are to be expired within 90 days
	 * @author Vishwa
	 * @return A JSON object that contains all the Drug Details
	 * @throws JSONException 
	 */
	@GET
	@Path("/getDrug")
	@Produces (MediaType.APPLICATION_JSON)
	public String getDrugs() throws JSONException{
		
		logger.info("Entering getDrugs method...");
		try 
		{

			List<TrnDrugsSupplied> drugs=drugDbDriver.getDrugsByExpDate();
//			List<TrnDrugsSupplied> drugs = drugDbDriver.getDrugsByExpDate();
			//List drugs = session.createQuery("FROM TrnDrugsSupplied as s where DATEDIFF(s.DExpiryDate,NOW())<=7").list(); 
			JSONObject name = new JSONObject();
            JSONObject srNo = new JSONObject();
            JSONObject bqty = new JSONObject();
            JSONObject bNo = new JSONObject();
            JSONObject manD = new JSONObject();
            JSONObject expD = new JSONObject(); 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            
            int i = 1;
            for(Iterator iterator = drugs.iterator();iterator.hasNext();)
            {
                //Cast the Returned Drug object to MstDrugs object
                TrnDrugsSupplied drug = (TrnDrugsSupplied) iterator.next();
                //dateFormat.format(drug.getDManufactDate());
                //Date manDate = dateFormat.parse(drug.getDManufactDate().toString());
                //Store the attributes in the created JSON Objects
                name.put("drug"+i,drug.getdName());
                srNo.put("srNo"+i,drug.getId().getDrugs().getdSrNo());
                bqty.put("bqty"+i,drug.getdQty());
                bNo.put("bNo"+i,drug.getId().getdBatchNo());
                manD.put("manD"+i,dateFormat.format(drug.getdManufactDate()));
                expD.put("expD"+i,dateFormat.format(drug.getdExpiryDate()));
                
                i++;
                
            }
            if(name.length()==0)
            {
            	logger.error("No drug names");
            	return "error";
            }
            else
            {
				JSONObject obj = new JSONObject();
				// Store the above JSON objects in another JSON Object
				obj.put("nameObject", name);
				obj.put("srNoObject", srNo);
				obj.put("bqtyObject", bqty);
				obj.put("bNoObject", bNo);
				obj.put("manDObject", manD);
				obj.put("expDObject", expD);
				// String str = obj.toString();

				
				JSONSerializer serializer = new JSONSerializer();
				//System.out.println(obj);
				//return serializer.serialize(obj);
				return obj.toString();
//			return serializer.exclude("*.class").transform(new DateTransformer("yyyy/MM/dd"), "DManufactDate","DExpiryDate").serialize(drugs);// this will return a json
													// object to the calling
//													 controller.
            }
		} 
		catch(RuntimeException e){
			logger.error("Error in getting drugs "+e.getMessage());
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
								
			return jsonErrorObject.toString();
		}
		catch (Exception e){
			logger.error("Error in getting drugs "+e.getMessage());
			return e.getMessage();
		}
		
		
	}
	
	/**
	 * Gives all the available Categories
	 * @author Vishwa
	 * @return A JSON object that contains all the Drug Categories 
	 * @throws JSONException 
	 */
	@GET
	@Path("/getDrugCategories")
	@Produces (MediaType.APPLICATION_JSON)
	public String getDrugsCatagories() throws JSONException {
		
		logger.info("Entering getDrugsCatagories method...");
		
		try 
		{
			
			List<MstDrugCategory> drugCat = drugDbDriver.getDrugCatagories();
			logger.info("Getting drugs Categories "+drugCat);
			
			JSONObject category = new JSONObject();
          
			JSONSerializer serializer = new JSONSerializer();
			 
		    return serializer.exclude("*.class").serialize(drugCat); // this will return a json object to the calling controller.
		} 
		catch(RuntimeException e){
			
			logger.error("Error in getting drug categories. "+e.getMessage());
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
								
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			 logger.error("Error in getting drug categories. "+e.getMessage());
			 return e.getMessage().toString();
		}
	}
	
	
	/**
	 * Gives all the Drug names of a given Category
	 * @author Vishwa
	 * @param category
	 * @return A JSON object that contains all the Drug Details
	 * @throws JSONException 
	 */
	@GET
	@Path("/getDrugNamesByCategory/{category}")
	@Produces (MediaType.APPLICATION_JSON)
	public String getDrugFromCategory(@PathParam("category") String category) throws JSONException {
		
		logger.info("Entering getDrugFromCategory method...");
		
		try 
		{

			
			List<MstDrugsNew> drugs = drugDbDriver.getDrugFromCategory(category);
			//JSONObject newDrug = new JSONObject();
            
            if(drugs.isEmpty())
            {
            	logger.error("Drugs of the category "+category+" is empty");
            	return "error";
            }
            else {
				
            	logger.info("Getting drugs of category "+category+" :"+drugs);
				JSONSerializer serializer = new JSONSerializer();

				//return serializer.exclude("DSrNo","DRemarks","DCreateDate","DCreateUser","DLastUpdate","DLastUpdateUser","DActive","DUnit","DPrice","DQty","categories","*.class").serialize(drugs); // this will return a json
				return serializer.include("dName","dSrNo").exclude("*").serialize(drugs);
				
			}
		} 
		catch(RuntimeException e)
		{
			logger.error("Exception in getDrugFromCategory method..."+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch (Exception e) 
		{
			 logger.error("Exception in ");
			 return e.getMessage().toString();
		}
	}
	
	
	/**
	 * Gives the Drug details for a given Drug name
	 * @author Vishwa
	 * @param name
	 * @return A JSON object that contains all the Drug Details
	 * @throws JSONException 
	 */
	@GET
	@Path("/getDrugDetailsByDrugName/{drug_name}")
	@Produces (MediaType.APPLICATION_JSON)
	public String getDrugDetails(@PathParam("drug_name") String name) throws JSONException {
		
		logger.info("Entering getDrugDetailsByDrugName mthod...");
		
		try 
		{
		
			List<MstDrugsNew> drugs = drugDbDriver.GetDrugDetailsByName(name);
	        logger.info("Getting drug detail of drug "+name+": "+drugs);
	        
			JSONSerializer serializer = new JSONSerializer();
			return serializer.include("categories.dCategory","dName","dPrice","dQty","dSrNo","dLastUpdate",
						"dCreateUser","dLastUpdateUser").exclude("*").transform(new DateTransformer("yyyy/MM/dd"), "dLastUpdate").serialize(drugs); 
		
		} 
		catch (RuntimeException e)
		{
			logger.error("Exception in getDrugDetails method..."+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch (Exception e) 
		{
			 logger.error("Error in getting drug details "+e.getMessage());
			 return e.getMessage().toString();
		}
	}

	
	/**
	 * Gives the details of the drugs for a given category
	 * @author Vishwa
	 * @param cat Is a string
	 * @return  A JSON object that contains all the Drug Details
	 * @throws JSONException 
	 */
	@GET
	@Path("/getDrugDetailsByCategory/{category}")
	@Produces (MediaType.APPLICATION_JSON)
	public String getDrugDetailsByCategory(@PathParam("category") String cat) throws JSONException {
		logger.info("Entering getDrugDetailsByCategory method...");
		try 
		{
			
			List<MstDrugsNew> drugs = drugDbDriver.getDrugFromCategory(cat); 
			logger.info("Getting drug detail of category "+cat+": "+drugs);
//            if(drugs.isEmpty())
//            {
//            	return "error";
//            }
//            else {
				// String str = obj.toString();

				JSONSerializer serializer = new JSONSerializer();
				
				return serializer.include("dSrNo","dName","categories.dCategory","dPrice","dQty","statusDanger",
						"statusReOrder","frequencies","dosages").exclude("*.class","dActive","dCreateDate","dCreateUser","dLastUpdate","dLastUpdateUser","dRemarks","dUnit").serialize(drugs);
														// controller.
//			}
		} 
		catch(RuntimeException e)
		{
			logger.error("Error in getting drug details by category "+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 
		}
		catch (Exception e) 
		{
			logger.error("Error in getting drug details by category "+e.getMessage());
			e.printStackTrace();
			return e.getMessage().toString();
		}
	}
	
	
	/**
	 * Gives all the Pending drug requests
	 * @author Vishwa
	 * @return A JSON object that contains all the requests Details
	 * @throws JSONException 
	 */
	@GET
	@Path("/getRequest")
	@Produces (MediaType.APPLICATION_JSON)
	public String getRequest() throws JSONException {
		
		logger.info("Entering getRequest method...");
		
		try 
		{
			
	        
	        List<TrnRequestDrugs> details;
	        details = drugDbDriver.getRequest();
             
            if(details.isEmpty())
            {
            	logger.error("No pending drug requests");
            	return "error";
            }
            else {
            	logger.info("Getting Pending drug requests "+details);
				JSONSerializer serializer = new JSONSerializer();

				return serializer.include("drugs.dSrNo","drugs.dName","drugs.dQty").exclude("*.class","drugs.*","processedDate").transform(new DateTransformer("yyyy/MM/dd"), "requestedDate","processedDate").serialize(details); // this will return a json
														// object to the calling
														// controller.
			}
		} 
		catch(RuntimeException e)
		{
			logger.error("Error in getting pending drug requests "+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 
		}
		catch (Exception e) 
		{
			logger.error("Error in getting pending drug requests "+e.getMessage());
			return e.getMessage().toString();
		}
	}


	
	

	/**
	 * Adding a new Batch for a particular drug
	 * @author Vishwa
	 * @param json
	 * @return a string value.True if the Data inserted successfully else it returns false
	 * @throws JSONException 
	 */
	@POST
	@Path("/addBatch")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addBatch(org.codehaus.jettison.json.JSONObject json) throws JSONException {
		System.out.println("add batch inside");
		
		logger.info("Entering add batch method...");
		
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");		
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Colombo"));
        Date date = new Date();
        
        String status;
        int drugSrNo=0;
        int drugQty =0;
        List<MstDrugsNew> drug=null;
        System.out.println("1");
        MstDrugsNew drugNew = null;
        System.out.println("2");
        try {
        	
        	System.out.println(json.get("b_mdate").toString());
			drug = drugDbDriver.GetDrugByDrugName(json.get("dname").toString());
			logger.info("Getting a drug "+drug);
			System.out.println("4");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			logger.error("JSON Exception in adding batch "+e1.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}
        
        int i = 1;
        for(Iterator iterator = drug.iterator();iterator.hasNext();)
        {
                //Cast the Returned Drug object to MstDrugs object
                drugNew = (MstDrugsNew) iterator.next();
                
                //Store the attributes in the created JSON Objects
                drugSrNo = drugNew.getdSrNo();
                drugQty = drugNew.getdQty();
                i++;
                
        }
        
		try {
			System.out.println("66");
			 Date ManDate = dateFormat.parse(json.getString("b_mdate").toString());
			 logger.info("Manufacture date parsed");
	         Date ExpDate = dateFormat.parse(json.getString("b_edate").toString());
	         logger.info("Expiry date parsed");
	         System.out.println(ManDate);
	         
	         String dname = json.get("dname").toString();
	         int qty =Integer.parseInt(json.get("b_qty").toString());
	         String bno = json.get("b_no").toString();
	         
	         TrnDrugsSuppliedId suppId = new TrnDrugsSuppliedId(drugNew,bno);
	         
			TrnDrugsSupplied drugSupp = new TrnDrugsSupplied();

			drugSupp.setId(suppId);
			drugSupp.setdName(dname);
			drugSupp.setdManufactDate(ManDate);
			drugSupp.setdExpiryDate(ExpDate);
			drugSupp.setdCreateDate(date);
			drugSupp.setdQty(qty);
			drugSupp.setdCreateDate(date);
			drugSupp.setdCreateUser(1);
			drugSupp.setBatchStatus("Enabled");
			
			//DrugAction da = new DrugAction();
			if(drugDbDriver.insertDrugBatch(drugSupp,drugSrNo, drugQty, qty))
			{
				status = "New Batch is Added!!!";
				logger.info("New batch added");
			}
			else
			{
				status = "failed to add a batch";
				logger.error("Error in adding new batch");
			}

			//session.getTransaction().commit();
			return status;

		} 
		catch(RuntimeException e)
		{
			logger.error("RuntimeException in adding new batch ..\n"+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(JSONException e)
		{
			logger.error("Exception in adding new batch ..\n "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			logger.error("Exception in adding a new batch "+e.getMessage());
			System.out.println("Exception in addBatch service");
			return e.getMessage();
//			return e.toString();
			
		}

	}
	
	
	/**
	 * Adding new Drug requests
	 * @author Vishwa
	 * @param json
	 * @return a string value.True if the Data inserted successfully else it returns false
	 * @throws JSONException 
	 */
	@POST
	@Path("/requestDrug")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String requestDrug(JSONObject json) throws JSONException {
		
		logger.info("Entering requestDrug method...");
		
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	      Date date = new Date();
	      String status="Default";
	     
	      //String dept = "";
	      List<MstDrugsNew> drug=new ArrayList<MstDrugsNew>();
	      
	     // List<MstUser> userNew=new ArrayList<MstUser>();
	      ArrayList<TrnRequestDrugs> requests=new ArrayList<TrnRequestDrugs>();
	      System.out.println(json);
	      
        //MstDrugsNew drugNew = null;
        //MstUser user =null;
        /*UserAction ua = new UserAction();
        DrugAction da = new DrugAction();*/
		try {
			List<AdminUser> user = userDbDriver.getUserDetailsByUserID(json.getInt("user"));
			int count = json.length() - 1;
			String dnames[] = new String[count];
			//String dept = ((MstPharmDepartment) user.get(0).getHrEmployee().getHrDepartments()).getDeptName();
			String dept ="OPD Pharmacy";
			//String dept = user.get(0).getHrEmployee().getHrDepartments().toString();
			int qtys[] = new int[count];
			int drugSrNo[]=new int[count];
			int j=0;
			for(int i=1; i<=count;i++) 
			{	

				qtys[j] = Integer.parseInt(json.getJSONObject("id"+i).get("qty").toString());
				dnames[j] = json.getJSONObject("id"+i).get("name").toString();
				//dept[j] = "IPD Pharmacy";
				TrnRequestDrugs req = new TrnRequestDrugs();
				req.setDrugs(drugDbDriver.GetDrugByDrugName(dnames[j]).get(0));
				req.setQuantity(qtys[j]);
				req.setDepartment(dept);
				req.setRequestedDate(date);
				req.setProcessed(false);
				
				requests.add(j, req);		
			    j++;   
			}
			if(drugDbDriver.insertDrugRequest(requests))
			{
					status = "Drug Request Sent!!!";
					logger.info("New drug request inserted");
			}
			else
			{
					status = "fail";
					logger.error("New drug request not inserted");
			}

		} 
		
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.error("Error in adding new drug request "+e.getMessage());
			e.printStackTrace();
		} 
		catch(IndexOutOfBoundsException e)
		{
			logger.error("Error in adding new drug request "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.INVALID_ID.getCode());
			jsonErrorObject.put("message", ErrorConstants.INVALID_ID.getMessage());
						
			return jsonErrorObject.toString();
		}
		catch(RuntimeException e)
		{
			logger.error("Error in adding new drug request "+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(JSONException e)
		{
			logger.error("Error in adding new drug request "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}
		
		catch(Exception e){
			logger.error("Error in adding new drug request "+e.getMessage());
//			return e.getMessage()+"requestDrug";
			return e.toString();
		}
		return status;

	}
	
	
	
	/**
	 * Approving the pending drug requests
	 * @author Vishwa
	 * @param json
	 * @return a string value.True if the request approved successfully else it returns false
	 * @throws JSONException 
	 */
	@POST
	@Path("/approveRequest")
	//@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String approveRequest(JSONObject json) throws JSONException {
		
		logger.info("Entering approveRequest method ...");
		
		try {
			
			
			
			int count = json.length() / 2;
			int[] reqId = new int[count];
			int[] appQty = new int[count];
			int i;
			int j=0;
			for(i=1; i<=count;i++) 
			{	
					
					reqId[j] = Integer.parseInt(json.getJSONObject("id"+i).get("id").toString());
					appQty[j] = Integer.parseInt(json.getJSONObject("qty" + i).get("qty").toString());
					j++;   
			}			
			
	        boolean status=drugDbDriver.ApproveRequest(reqId,appQty);  
	        if(status)
	        {
	        	logger.info("Request Approved");
	        	return "Request Approved!!!";
	        }
	        else
	        {
	        	logger.error("Not enough Stock available");
	        	return "Not enough Stock available!!!";
	        }
//			return "Test";
		} 
		catch(RuntimeException e)
		{
			logger.error("Exception in approveRequest method ..."+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(JSONException e)
		{
			logger.error("Exception in approveRequest method ..."+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			logger.error("Error in approveRequest method ..."+e.getMessage());
			return e.getMessage();
		}

	}
	
	
	/**
	 * Dispensing the drugs of a prescription
	 * @author Vishwa
	 * @param json
	 * @return a string value.True if the prescription dispense successfully else it returns false
	 * @throws JSONException 
	 */
	@POST
	@Path("/dispenseDrug")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String dispenseDrug(JSONObject json) throws JSONException {

		logger.info("Entering dispenseDrug method ...");
		
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			String status;
			try {
				
				JSONArray dispenseJSONList = json.getJSONObject("dispense").getJSONArray("dispenseList");
				List<TrnDispenseDrugs> dispenseList = new ArrayList<TrnDispenseDrugs>();
				
				
				for (int i = 0; i < dispenseJSONList.length(); i++) {

					JSONObject innerObject = dispenseJSONList.getJSONObject(i);
					
					
					int drugQuantity = Integer.parseInt(innerObject.get("qty").toString());
					System.out.println(drugQuantity);
					//System.out.println(drug);
					// get MstDrugsNew to set TrnDispenseDrugs
					MstDrugsNew drug = drugDbDriver.getDrugObjectById(Integer
							.parseInt(innerObject.get("DSrNo").toString()));
					if (drug != null) {
						if (drug.getdQty() < drugQuantity) {
							logger.info("Less amount");
							return "less amount";
						}

						drug.setdLastUpdate(date);
						drug.setdLastUpdateUser(Integer.parseInt(json.get("userid").toString()));
						drug.setdQty(drug.getdQty() - drugQuantity);

					} else {
						logger.info("Drug not available");
						return "not available";
					}

					// set dispense drug values
					TrnDispenseDrugs trnDispenseDrugs = new TrnDispenseDrugs();
					trnDispenseDrugs.setDrugs(drug);
					trnDispenseDrugs.setDispensedDate(date);
					trnDispenseDrugs.setQuantity(drugQuantity);
					trnDispenseDrugs.setUserId(Integer.parseInt(json.get("userid").toString()));

					dispenseList.add(trnDispenseDrugs);
				}
				
				//prescription related
				Prescription prescription= prescriptionDbDriver.getPrescription((Integer.parseInt(json.getJSONObject("dispense").get("PrescriptionId").toString())));
				prescription.setPrescriptionStatus(1);
				//prescription related
				
				if (drugDbDriver.dispenseDrugs(dispenseList,prescription)) {
					logger.info("Drug dispensed.");
					status = "Drugs Dispensed!!!";
				} else {
					logger.error("Drug not dispensed");
					status = "fail";
				}

				return status;

			} 
			catch(RuntimeException e)
			{
				logger.error("Exception in dispenseDrug method..."+e.getMessage());
				
				e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
			catch(JSONException e)
			{
				logger.error("Exception in dispenseDrug method..."+e.getMessage());
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
				jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
							
				return jsonErrorObject.toString();
			}
			catch (Exception e) {
				logger.error("Exception in dispenseDrug method..."+e.getMessage());
				return e.getMessage();
			}

	}
	
	/**
	 * Updating a batch of a particular drug
	 * @author Mushi
	 * @param json
	 * @return  a string value.True if the data updated successfully else it returns false
	 * @throws JSONException 
	 */
	 @POST
	 @Path("/updateBatch")
	 @Produces(MediaType.TEXT_PLAIN)
	 @Consumes(MediaType.APPLICATION_JSON)
	 public String updateBatch(JSONObject json) throws JSONException{
		 
		 logger.info("Entering updateBatch method...");
		 
		 try {
				System.out.println(json);
				String dbatchno = json.get("dbatchno").toString();
				String dName = json.get("dname").toString();
				String dCat = json.get("dcat").toString();
				String dUser = json.get("duser").toString();
				String dStatus = json.get("dstatus").toString();
				int dSr = Integer.parseInt(json.get("dsr").toString());
				int dQty = Integer.parseInt(json.get("dqty").toString());
				System.out.println(dStatus);
				drugDbDriver.updateBatch(dName, dbatchno, dQty, dCat, dUser, dSr,dStatus);
				logger.info("Batch updated");
				
				System.out.println(dSr);
				return "Updated Succesfully!!!";
			}
		 catch(RuntimeException e)
			{
			 logger.error("Error updateBatch method..."+e.getMessage());
				
				e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
			catch(JSONException e)
			{
				logger.error("Error updateBatch method..."+e.getMessage());
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
				jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
							
				return jsonErrorObject.toString();
			}
		  catch (Exception e) {
				logger.error("Error updateBatch method..."+e.getMessage());
				return e.toString();
			}
	 }

	 /**
	  * Gives the details of the batches for a given drug
	  * @author Vishwa
	  * @param json
	  * @return
	 * @throws JSONException 
	  */
	 @POST
	 @Path("/getBatchDetailsByDrugName")
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_JSON)
	 public String getDrugdetailsByDrugName(JSONObject json) throws JSONException{
		 logger.info("Entering getDrugdetailsByDrugName method...");
		 List<TrnDrugsSupplied> drug = null;
			List<TrnDrugsSupplied> drugReturn = null;
			JSONArray list1 = new JSONArray();
			try {
				String drugName = json.get("dname").toString();
				String batchId = json.get("dbatch").toString();
				drug = drugDbDriver.getBatchIds(drugName);
				for (Iterator iterator = drug.iterator(); iterator.hasNext();) {
					TrnDrugsSupplied drugs = (TrnDrugsSupplied) iterator.next();

					if ((drugs.getId().getdBatchNo().equalsIgnoreCase(batchId))) {

						list1.put(drugs.getId().getdBatchNo());
						list1.put(drugs.getdName());
						list1.put(drugs.getdQty());
						list1.put(drugs.getBatchStatus());
						list1.put(drugs.getdCreateUser());
						list1.put(drugs.getdLastUpdate());
						list1.put(drugs.getdLastUpdateUser());
					}

				}
				JSONSerializer serializer = new JSONSerializer();
				System.out.println(list1.toString());
				logger.info("getDrugdetailsByDrugName method successfull. drug info: \n"+list1.toString());
				return list1.toString();

			} 
			catch(RuntimeException e)
			{
				logger.error("Exception in getDrugdetailsByDrugName method..."+e.getMessage());
				
				e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
			catch(JSONException e)
			{
				logger.error("Exception in getDrugdetailsByDrugName method..."+e.getMessage());
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
				jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
							
				return jsonErrorObject.toString();
			}
			catch (Exception e) {
				logger.error("Exception in getDrugdetailsByDrugName method... "+e.getMessage());
				return "error";
			}
	 }
	 
	 
	 /**
	  * Gives the List of prescriptions for a given date
	  * @author Vishwa
	  * @param date
	  * @return A JSON object that contains all the prescriptions Details
	 * @throws JSONException 
	  */
	 @GET
	 @Path("/getDescriptionListByDate/{date}")
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getPrescriptionListByDate(@PathParam("date") String date) throws JSONException{
		 
		 logger.info("Entering getPrescriptionListByDate method...");
		 try {

				List<TrnDispenseDrugs> dispenseDrugs = drugDbDriver.getDispenseListByDate(date);
				logger.info("Getting dispense list on "+date +": "+dispenseDrugs);
				
				JSONArray dispenseList = new JSONArray();
				for (int i = 0; i < dispenseDrugs.size(); i++) {
					TrnDispenseDrugs drugs = dispenseDrugs.get(i);
					JSONObject dispense = new JSONObject();
					dispense.put("dispenseID", drugs.getDispenseId());
					dispense.put("dispenseQty", drugs.getQuantity());
					dispense.put("dispenseUser", drugs.getUserId());

					try {
						List<AdminUser> users = userDbDriver.getUserDetailsByUserID(drugs.getUserId());
						logger.info("Getting user details of user ID"+drugs.getUserId()+": "+users);
						
						if (users.size() > 0) {
							AdminUser user = users.get(0);
							dispense.put("dispenseUser", user.getUserName());
							
						} else {
							dispense.put("dispenseUser", "K.A Saman Kumara");
						}

					} catch (Exception e) {
						
						dispense.put("dispenseUser", "K.A Saman Kumara");
					}

					dispense.put("dispenseTime", drugs.getDispensedDate());

					MstDrugsNew drugsNew = drugDbDriver.getDrugObjectById(drugs.getDrugs().getdSrNo());
					dispense.put("drugName", drugsNew.getdName() + "");
					dispenseList.put(dispense);

				}

				/*System.out.println("dispenseList := " + dispenseList.toString());
				if (false) {
					return "error";
				} else {*/
					return dispenseList.toString(); // controller.
				//}
			} 
		 catch(RuntimeException e)
			{
				logger.error("Error in getting prescription by date "+e.getMessage());
				
				e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
		 catch (Exception e) {

				logger.error("Error in getting prescription by date "+e.getMessage());
				return e.getMessage().toString();
			}
	 }
	 
	 /**
	  * Saving Dosages
	  * @author Vishwa
	  * @param json
	  * @return 
	 * @throws JSONException 
	  */
	 @POST
	 @Path("/saveDosages")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.TEXT_PLAIN)
	 public String saveDosages(JSONObject json) throws JSONException{
		 
		 logger.info("entering saveDosages method...");
		 
		 List<MstDrugDosage> dosages = new ArrayList<MstDrugDosage>();
			try {
				System.out.println("Came in!!!");
				
				org.codehaus.jettison.json.JSONArray frequencyJSONList = json
						.getJSONArray("dosageList");
				for (int i = 0; i < frequencyJSONList.length(); i++) {

					org.codehaus.jettison.json.JSONObject innerObject = frequencyJSONList
							.getJSONObject(i);

					MstDrugDosage dosage = new MstDrugDosage();
					try {
						
						dosage.setDosId(Integer.parseInt(innerObject.get("dosId")
								.toString()));
					} catch (Exception e) {
						
					}
					dosage.setDosage(innerObject.get("dosage").toString());
					dosage.setRecordStatus(Integer.parseInt(innerObject.get(
							"recordStatus").toString()));
					dosages.add(dosage);
				}

				if (drugDbDriver.insertDrugDosages(dosages)) {
					logger.info("Saving dosages" +dosages);
					return "success";
				} else {
					logger.error("Error in saving dosages ");
					return "fail !";
				}
			} 
			catch(RuntimeException e)
			{
				logger.error("Exception in saveDosages method "+e.getMessage());
				
				e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
			catch(JSONException e)
			{
				logger.error("Exception in saveDosages method "+e.getMessage());
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
				jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
							
				return jsonErrorObject.toString();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception in saveDosages method "+e.getMessage());
				return "JSON Error ! "+ e.getMessage();
			}
	 }
	 
	 /**
	  * Gives the list of Dosages
	  * @author Vishwa
	  * @return A JSON object that contains all the dosage Details
	 * @throws JSONException 
	  */
	 @GET
	 @Path("/getDosages")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getDosages() throws JSONException{
		 
		 logger.info("Entering getDosages method...");
		 
		 try{
			 List<MstDrugDosage> dosageList = drugDbDriver.getDrugDosages();
			 logger.info("Getting all dosages "+dosageList);
			 
			 JSONSerializer serializer = new JSONSerializer();
			 return  serializer.include("dosId","dosage","recordStatus").exclude("*.class").serialize(dosageList);
				
		 }
		 catch(RuntimeException e)
		 {
			logger.error("Exception in getDosages method... "+e.getMessage());
			e.printStackTrace();

				
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
			return jsonErrorObject.toString(); 	
			 
		 }
		 catch (Exception e) {
		 	logger.error("Exception in getDosages method... "+e.getMessage());
			return e.getMessage();
		} 
	 }
	 
	 
	 /**
	  * Gives the Details of the drugs which are low in stocks
	  * @author Vishwa
	  * @return A JSON object that contains all the Drug Details
	 * @throws JSONException 
	  */
	 @GET
	 @Path("/drugreport")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String drugReport() throws JSONException{
		 
		 logger.info("Entering drugReport method...");
		 try{
			
				List Unames = drugDbDriver.getDrugReport(); 
				logger.info("Getting drug report "+Unames);
				JSONObject JUnames = new JSONObject();

				int i = 1;

				for (Iterator iterator = Unames.iterator(); iterator.hasNext();) {
					// Cast the Returned Drug object to MstDrugs object
					MstDrugsNew names = (MstDrugsNew) iterator.next();
				
					// Store the attributes in the created JSON Objects
					JUnames.put(
							"rot" + i,
							" " + names.getdName() + ":" + names.getdSrNo() + ":"
									+ names.getCategories().getdCategory() + ":"
									+ names.getdPrice() + ":" + names.getdQty()
									+ ":" + names.getdUnit() + ":"
									+ names.getStatusDanger() + ":"
									+ names.getStatusReOrder() + ":");

					// JUnames.put("urd"+i,names.getUserName());
					i++;

				}

				// String str = obj.toString();

				JSONSerializer serializer = new JSONSerializer();

				return JUnames.toString(); // this will return a json
														// object to the calling
														// controller.
			 
		 }
		 catch(RuntimeException e)
		 {
			 	logger.error("Exception in drugReport method..."+e.getMessage());
			 
			 	e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
		 }
		 catch (Exception e) {
			 
			 	logger.error("Exception in drugReport method..."+e.getMessage());
				return e.getMessage();
		 }
	 }
	 
	 
	 /**
	  * Gives the list of available Frequencies
	  * @author Vishwa
	  * @return A JSON object that contains all the Frequencies Details
	 * @throws JSONException 
	  */
	 @GET
	 @Path("/getPharmFrequency")
	 @Produces(MediaType.APPLICATION_JSON)
	 public String getPharmFrequncy() throws JSONException{
		 
		 logger.info("Entering getPharmFrequncy method...");
		 
		 try{
			List<MstDrugFrequency> frequencyList = drugDbDriver.getFrequency();
			logger.info("Getting pharmacy frequency"+frequencyList);
			JSONSerializer serializer = new JSONSerializer();
			return  serializer.include("freqId","frequency").exclude("*.class").serialize(frequencyList);
			 
		 }
		 catch(RuntimeException e)
		 {
			 	logger.error("Error in getting pharmacy frequency "+e.getMessage());
			 
			 	e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
		 }
		 catch (Exception e) {

			 	logger.error("Error in getting pharmacy frequency "+e.getMessage());
				return e.getMessage().toString();
		 }
	 }
	 
	 /**
	  * Updates the frequency
	  * @author Vishwa
	  * @param json
	  * @return
	 * @throws JSONException 
	  */
	 @POST
	 @Path("/updateFrequency")
	 @Produces(MediaType.TEXT_PLAIN)
	 @Consumes(MediaType.APPLICATION_JSON)	 
	 public String updateFrequency(JSONObject json) throws JSONException{
		 //String result="";
		 
		 logger.info("Entering updateFrequency method...");
		 
		 try{
			MstDrugFrequency frequency = new MstDrugFrequency();
			frequency.setFreqId(json.getInt("frequencyId"));
			frequency.setFrequency(json.getString("frequency"));
			drugDbDriver.updateFrequency(frequency);
			logger.info("Update frequency "+frequency);
			
			return "True";
		}
		 catch(NullPointerException e)
		 {
			   logger.error("Exception in updateFrequency method..."+e.getMessage());
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.INVALID_ID.getCode());
				jsonErrorObject.put("message", ErrorConstants.INVALID_ID.getMessage());
							
				return jsonErrorObject.toString();
		 }
		 catch(RuntimeException e)
		{
			 logger.error("Exception in updateFrequency method..."+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(JSONException e)
		{
			logger.error("Exception in updateFrequency method..."+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}
		catch (Exception e) {
			logger.error("Exception in updateFrequency method..."+e.getMessage());
			return e.getMessage();
//			return e.toString();
		}
	 }
	 
	 
	 /**
	  * Adding a new frequency
	  * @author Vishwa 
	  * @param json
	  * @return
	 * @throws JSONException 
	*/
	@POST
	@Path("/addFrequency")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addFrequency(JSONObject json) throws JSONException{
		
		logger.info("Enetering addFrequency method...");
		try {
			MstDrugFrequency frequency = new MstDrugFrequency();
			frequency.setFrequency(json.getString("frequency"));
			drugDbDriver.addFrequency(frequency);
			
			logger.info("Adding frequency...");
			return "True";

		}
		
		catch(RuntimeException e)
		{
			logger.error("Exception in addFrequency method "+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(JSONException e)
		{
			logger.error("Exception in addFrequency method "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}		
		catch (Exception e) {
			logger.error("addFrequency method "+e.getMessage());
			return e.getMessage();
		}
	}
	
	
	/**
	 * Insert Mails
	 * @author Vishwa
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/insertMail")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String insertMail(JSONObject json) throws JSONException{
		
		logger.info("Entering insertMail method...");
		try {
			
			MstMailHistory mailHistroy = new MstMailHistory();
			int drugID=json.getInt("drugid");
			logger.info("Drug Id :"+drugID);
			
			mailHistroy.setMailHistory_Content(json.getString("content"));
			drugDbDriver.insertEmail(drugID, mailHistroy);
			logger.info("Mail inserted ");
			
			return "True";

		}
		
		catch(RuntimeException e)
		{
			logger.error("Exception in inserting new Drug"+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch(JSONException e)
		{
			logger.error("Exception in updating drugs "+e.getMessage());
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.FILL_REQUIRED_FIELDS.getCode());
			jsonErrorObject.put("message", ErrorConstants.FILL_REQUIRED_FIELDS.getMessage());
						
			return jsonErrorObject.toString();
		}		
		catch (Exception e) {
			logger.error("Exception: "+e.getMessage());
			return e.toString();
		}
	}
	
	/**
	 * Gives the history of mails sent for a particular drug
	 * @author Vishwa
	 * @return
	 * @throws JSONException 
	 */
	@GET
	@Path("/getMailHistory")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMailHistroy() throws JSONException{
		
		logger.info("Entering getMailHistory method...");
		
		try{
			List<MstMailHistory> mailList=drugDbDriver.getMailHistroy(); 
			logger.info("Getting mail history "+mailList);
			JSONSerializer serializer = new JSONSerializer();
			return  serializer.include("mailHistory_ID","mailHistory_Content","mailHistory_SendDate","mailHistory_Drug.dSrNo","mailHistory_Drug.dName")
										.exclude("*.class","mailHistory_Drug.*").transform(new DateTransformer("yyyy-MM-dd"),"mailHistory_SendDate")
										.serialize(mailList);
			 
		 }
		catch(RuntimeException e)
		{
			logger.error("Error in getting email history "+e.getMessage());
			
			e.printStackTrace();
			
			JSONObject jsonErrorObject = new JSONObject();
			jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
			jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
			
			return jsonErrorObject.toString(); 	
		}
		catch (Exception e) {
			 	logger.error("Error in getting email history "+e.getMessage());
				return e.getMessage().toString();
		 }
	}
	
		/**
		 * Get The Drug Names
		 * @author Vishwa
		 * @return
		 * @throws JSONException 
		 */
		@GET
		@Path("/getDrugNames")
		@Produces(MediaType.APPLICATION_JSON)
		public String getDrugNames() throws JSONException {
			
			logger.info("Entering getDrugNames method...");
			
			try {
				List<MstDrugsNew> drugName = drugDbDriver.getDrugNames();
				logger.info("Getting drug names "+drugName);
				
				JSONSerializer serializer = new JSONSerializer();
				return serializer.include("dName", "dSrNo").exclude("*")
						.serialize(drugName);
			} 
			catch(RuntimeException e){
				logger.error("Error in getting drug names "+e.getMessage());
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
									
				return jsonErrorObject.toString();
			}
			catch (Exception e) {
				logger.error("Error in getting drug names "+e.getMessage());
				return e.getMessage().toString();
			}
		}

		/**
		 * Get the Drug details
		 * @author Vishwa
		 * @return
		 * @throws JSONException 
		 */
		@GET
		@Path("/getDrugDetails")
		@Produces(MediaType.APPLICATION_JSON)
		public String getDrugDetails() throws JSONException {
			
			logger.info("Entering getDrugDetails method...");
			
			try {
				List<MstDrugsNew> drugDet = drugDbDriver.getDrugDetails();
				logger.info("Getting all drug details "+drugDet);
				
				JSONSerializer serializer = new JSONSerializer();
				return serializer
						.include("dSrNo", "dName", "dSrNo", "dQty",
								"statusReOrder", "statusDanger", "dUnit")
						.exclude("*").serialize(drugDet);
			} 
			catch (RuntimeException e)
			{
				logger.error("Exception in getDrugDetails method..."+e.getMessage());
				
				e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
			catch (Exception e) {
				logger.error("Error in getting all drug details "+e.getMessage());
				return e.getMessage().toString();
			}
		}

		/**
		 * Gives the Details of the Drugs for a given drug name
		 * @author Vishwa
		 * @param dname
		 * @return
		 * @throws JSONException 
		 */
		@GET
		@Path("/getDrugDetailsByDName/{dname}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getDrugDetailsByDName(@PathParam("dname") String dname) throws JSONException {
			logger.info("Entering getDrugDetailsByDName method...");
			
			try {
				List<MstDrugsNew> drugDet = drugDbDriver.getDrugDetailsByDName(dname);
				logger.info("Getting drug details for drug "+dname+": "+drugDet);
				
				JSONSerializer serializer = new JSONSerializer();
				return serializer.include("dName", "dSrNo", "dUnit", "dPrice")
						.exclude("*").serialize(drugDet);
			} 
			catch (RuntimeException e)
			{
				logger.error("Error in getting drug details "+e.getMessage());
				
				e.printStackTrace();
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in getting drug details "+e.getMessage());
				return e.getMessage().toString();
			}
		}

		/**
		 * Gives the drug batches for a given drug Id
		 * @author Vishwa
		 * @param dSrNo
		 * @return
		 * @throws JSONException 
		 */
		@GET
		@Path("/getBatchesBydName/{dSrNo}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getBatchesByDname(@PathParam("dSrNo") int dSrNo) throws JSONException {
			
			logger.info("Entering getBatchesByDname method...");
			
			try {
				List batchNo = drugDbDriver.getDrugBatch(dSrNo);
				logger.info("Getting batches for drug "+dSrNo+": "+batchNo);
				JSONSerializer serializer = new JSONSerializer();
				return serializer.serialize(batchNo);
			} 
			catch(IndexOutOfBoundsException e)
			{
				logger.error("Exception in getBatchesByDname method..."+e.getMessage());
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.INVALID_ID.getCode());
				jsonErrorObject.put("message", ErrorConstants.INVALID_ID.getMessage());
				
				return jsonErrorObject.toString(); 	
				
			}
			catch (RuntimeException e) {
				logger.error("Exception in getBatchesByDname method..."+e.getMessage());
				
				JSONObject jsonErrorObject = new JSONObject();
				jsonErrorObject.put("errorcode", ErrorConstants.NO_CONNECTION.getCode());
				jsonErrorObject.put("message", ErrorConstants.NO_CONNECTION.getMessage());
				
				return jsonErrorObject.toString(); 	
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception in getBatchesByDname method..."+e.getMessage());
				return e.getMessage();
			}
		}
	
	
	 

}
