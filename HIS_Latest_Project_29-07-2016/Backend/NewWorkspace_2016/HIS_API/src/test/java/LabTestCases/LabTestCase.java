package LabTestCases;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This class is for TestNG Integration Test cases related to all
 * functionalities of Laboratory in HIS Project. developed by Udara Samaratunge.
 * 
 * {@link BaseTestCase}
 * @author udara.s
 * 
 */
public class LabTestCase extends BaseTestCase {

	public static final int SUCCESS_STATUS_CODE = 200;

	public String labID, labName, labTypeID, labTypeName;

	/*
	 * Test Case for Insert Lab Test
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */
	@Test(groups = "his.lab.test")
	public void insertLabTestCase() throws IOException, JSONException {

		ArrayList<String> resArrayList = getHTTPResponse(
				properties.getProperty(TestCaseConstants.URL_APPEND_UPDATE_TEST), TestCaseConstants.HTTP_POST,
				RequestUtil.requestByID(TestCaseConstants.URL_APPEND_UPDATE_TEST));

		Assert.assertEquals(Integer.parseInt(resArrayList.get(1)), SUCCESS_STATUS_CODE);
		Assert.assertTrue(Boolean.parseBoolean(resArrayList.get(0)));

	}

	/**
	 * This test case is for get all laboratories
	 * 
	 * @throws IOException
	 */
	@Test(groups = "his.lab.test", dependsOnMethods = { "insertLabTestCase" })
	public void getAllLaboratoriesTestCase() throws IOException {

		ArrayList<String> resArrayList = getHTTPResponse(properties.getProperty(TestCaseConstants.URL_APPEND_LAB),
				TestCaseConstants.HTTP_GET, null);

		Assert.assertEquals(Integer.parseInt(resArrayList.get(1)), SUCCESS_STATUS_CODE);
	}

	/**
	 * This is Add Laboratory Test Case.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred. This
	 *             class is the general class of exceptions produced by failed
	 *             or interrupted I/O operations.
	 * @throws JSONException
	 *             Exception throws when process Json
	 */
	@Test(groups = "his.lab.test")
	public void addLaboratoryTestCase() throws IOException, JSONException {

		// Send POST request to add laboratory
		ArrayList<String> resArrayList = getHTTPResponse(
				properties.getProperty(TestCaseConstants.URL_APPEND_ADD_NEW_LAB), TestCaseConstants.HTTP_POST,
				RequestUtil.requestByID(TestCaseConstants.ADD_NEW_LAB));

		JSONObject jsonObject = new JSONObject(resArrayList.get(0));
		labID = jsonObject.getString("lab_ID");
		labName = jsonObject.getString("lab_Name");
		labTypeID = jsonObject.getJSONObject("flabType_ID").getString("labType_ID");
		labTypeName = jsonObject.getJSONObject("flabType_ID").getString("lab_Type_Name");

		Assert.assertEquals(Integer.parseInt(resArrayList.get(1)), SUCCESS_STATUS_CODE);
		Assert.assertEquals(labName, properties.getProperty(TestCaseConstants.ADD_LAB_NAME));
		Assert.assertEquals(jsonObject.getJSONObject("flabType_ID").getString("lab_Type_Name"), "Inte");

	}

	/**
	 * This test case is for get Laboratory request by ID
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred. This
	 *             class is the general class of exceptions produced by failed
	 *             or interrupted I/O operations.
	 * @throws JSONException
	 *             Exception throws when process Json
	 */
	@Test(groups = "his.lab.test", dependsOnMethods = { "addLaboratoryTestCase" })
	public void getLabTestRequestByID() throws IOException, JSONException {

		ArrayList<String> resArrayList = getHTTPResponse(properties.getProperty(TestCaseConstants.LAB_TYPE_URL)
				+ labTypeID, TestCaseConstants.HTTP_GET, null);

		JSONArray jsonArray = new JSONArray(resArrayList.get(0));
		JSONObject jsonObject = ((JSONObject) jsonArray.get(jsonArray.length() - 1));

		Assert.assertEquals(Integer.parseInt(resArrayList.get(1)), SUCCESS_STATUS_CODE);
		Assert.assertEquals(jsonObject.getString("lab_ID"), labID);
		Assert.assertEquals(jsonObject.getJSONObject("flabType_ID").getString("lab_Type_Name"), labTypeName);

	}

	/**
	 * This test case is to update Laboratory
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred. This
	 *             class is the general class of exceptions produced by failed
	 *             or interrupted I/O operations.
	 * @throws JSONException
	 *             Exception throws when process Json
	 */

	@Test(groups = "his.lab.test", dependsOnMethods = { "addLaboratoryTestCase", "getLabTestRequestByID",
			"getAllLaboratoriesTestCase" })
	public void updateLaboratoryTestCase() throws IOException, JSONException {

		// Get JSON Request body to send LAb update Request
		JSONObject jsonResponseObject = new JSONObject(RequestUtil.requestByID(TestCaseConstants.UPDATE_LAB));
		
		jsonResponseObject.put("lab_ID", labID);
		jsonResponseObject.put("lab_Name", properties.get(TestCaseConstants.UPDATE_LAB_NAME));
		jsonResponseObject.put("lab_Incharge", properties.get(TestCaseConstants.LAB_INCHARGE));
		jsonResponseObject.put("contactNumber1", properties.get(TestCaseConstants.LAB_CONTACT_NUMBER));

		// Send JSON Update Laboratory Request
		ArrayList<String> resArrayList = getHTTPResponse(
				properties.getProperty(TestCaseConstants.URL_APPEND_UPDATE_LAB), TestCaseConstants.HTTP_POST,
				jsonResponseObject.toString());

		//Get Updated Lab request by using lab request ID
		JSONArray jsonArray = new JSONArray(getHTTPResponse(
				properties.getProperty(TestCaseConstants.LAB_TYPE_URL) + jsonResponseObject.getString("flabType_ID"),
				TestCaseConstants.HTTP_GET, null).get(0));
		JSONObject jsonObject = ((JSONObject) jsonArray.get(jsonArray.length() - 1));

		/*
		 * Assert updated details of Test case by selecting test case with get
		 * /Lab request by ID
		 */
		Assert.assertEquals(Integer.parseInt(resArrayList.get(1)), SUCCESS_STATUS_CODE);
		Assert.assertEquals(properties.get(TestCaseConstants.UPDATE_LAB_NAME), jsonObject.getString("lab_Name"));
		Assert.assertEquals(properties.get(TestCaseConstants.LAB_INCHARGE), jsonObject.getString("lab_Incharge"));
		Assert.assertEquals(properties.get(TestCaseConstants.LAB_CONTACT_NUMBER),
				jsonObject.getString("contactNumber1"));

	}

	/**
	 * When execute this test case it deletes Laboratory for the provided ID
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception of some sort has occurred. This
	 *             class is the general class of exceptions produced by failed
	 *             or interrupted I/O operations.
	 */
	@Test(groups = "his.lab.test", dependsOnMethods = { "addLaboratoryTestCase", "getLabTestRequestByID",
			"getAllLaboratoriesTestCase", "updateLaboratoryTestCase" })
	public void deleteLabTestCase() throws IOException {

		// Get response for delete lab test case
		ArrayList<String> resArrayList = getHTTPResponse(properties.getProperty(TestCaseConstants.LAB_DELETE_URL)
				+ labTypeID, TestCaseConstants.HTTP_GET, null);

		System.out.println(resArrayList.get(0));
		Assert.assertEquals(Integer.parseInt(resArrayList.get(1)), SUCCESS_STATUS_CODE);
		Assert.assertTrue(Boolean.parseBoolean(resArrayList.get(0)));
	}

}
