/**
 * 
 */
package lib.driver.opd.driver_class;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lib.SessionFactoryUtil;
import core.classes.api.user.AdminUser;
import core.classes.opd.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import flexjson.JSONSerializer;
import lib.driver.sync.driver_class.cpsDBDriver;
/**
 * This class control basic CRUD operations of the out patient. it uses the
 * hibernate session factory utility and support for CRUD operation of patient
 * class.
 * 
 * @author
 * @version 1.0
 */
public class PatientDBDriver {

	Session session = SessionFactoryUtil.getSessionFactory()
			.openSession();
	cpsDBDriver cpsDBDriver= new cpsDBDriver();
	/**
	 * This method insert the OPD patient details to the patient table
	 * 
	 * @param patient
	 *            object of a OutPatient class
	 * @return boolean value true if success otherwise false
	 * @throws Method
	 *             throws a {@link RuntimeException} in failing the update,
	 *             throws a {@link HibernateException} on error rolling back
	 *             transaction.
	 */
	public boolean insertPatient(OutPatient patient, int userid,String dob,JSONArray array) {

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AdminUser user = (AdminUser) session.get(AdminUser.class, userid);
			patient.setPatientLastUpdateUser(user);
			patient.setPatientCreateUser(user);
			session.save(patient);
			
			Object result = session.createSQLQuery("SELECT LAST_INSERT_ID()")
                    .uniqueResult();
			tx.commit();
			this.insertGuardiansForPatient(result,array);
			
			
			return(cpsDBDriver.sendNewPatientObjToCPS(patient,dob));
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
					System.out.print(ex.getMessage());
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return false;
			}
		}

	}

	/**
	 * This method update the patient details of the OPD patient
	 * 
	 * @param patient
	 *            object of the OutPatient class
	 * @return boolean value. true on success
	 * @throws Method
	 *             throws a {@link RuntimeException} in failing the update,
	 *             throws a {@link HibernateException} on error rolling back
	 *             transaction.
	 */
	public boolean updatePatient(int patientID, OutPatient pat, int userid,String dob,JSONArray array) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			OutPatient patient = (OutPatient) session.get(OutPatient.class,
					patientID);

			patient.setPatientTitle(pat.getPatientTitle());
			patient.setPatientFullName(pat.getPatientFullName());
			patient.setPatientPersonalUsedName(pat.getPatientPersonalUsedName());
			patient.setPatientNIC(pat.getPatientNIC());
			//patient.setPatientHIN(pat.getPatientHIN());

			if (pat.getPatientPhoto() == null | pat.getPatientPhoto().isEmpty()
					| pat.getPatientPhoto() == "null")
				patient.setPatientPhoto(patient.getPatientPhoto());
			else {

				String photo = pat.getPatientPhoto();
				photo = photo.substring(photo.lastIndexOf("/") + 1, photo.length());
				patient.setPatientPhoto(photo);
 
			}
			patient.setPatientPassport(pat.getPatientPassport());
			//patient.setPatientDateOfBirth(pat.getPatientDateOfBirth());
			
			patient.setPatientGender(pat.getPatientGender());
			
			patient.setPatientAddress1(pat.getPatientAddress1());
			patient.setPatientAddress2(pat.getPatientAddress2());
			patient.setPatientAddress3(pat.getPatientAddress3());
			patient.setPatientCity(pat.getPatientCity());
			patient.setPatientPostalCode(pat.getPatientPostalCode());
			patient.setPatientCivilStatus(pat.getPatientCivilStatus());
		
			patient.setPatientTelephone(pat.getPatientTelephone());
			patient.setPatientPreferredLanguage(pat
					.getPatientPreferredLanguage());
			patient.setPatientCitizenship(pat.getPatientCitizenship());
			patient.setPatientblood(pat.getPatientblood());
			patient.setPatientRemarks(pat.getPatientRemarks());
			patient.setPatientActive(pat.getPatientActive());
			patient.setPatientCreateUser(patient.getPatientCreateUser());
			
			patient.setEmergencyAddress1(pat.getPatientAddress1());
			patient.setEmergencyAddress2(pat.getEmergencyAddress2());
			patient.setEmergencyAddress3(pat.getEmergencyAddress3());
			patient.setEmergencyCity(pat.getEmergencyCity());
			patient.setEmergencyLname(pat.getEmergencyLname());
			patient.setEmergencyMobile(pat.getEmergencyMobile());
			patient.setEmergencyPostalCode(pat.getEmergencyPostalCode());
			patient.setEmergencyTelepone(pat.getEmergencyTelepone());
			patient.setEmergnecyFname(pat.getEmergnecyFname());
			patient.setPatientCreateUser(patient.getPatientCreateUser());

			AdminUser user = (AdminUser) session.get(AdminUser.class, userid);
			patient.setPatientLastUpdateUser(user);

			patient.setPatientLastUpdate(pat.getPatientLastUpdate());

			session.update(patient);
			
			Object result = (Integer) patient.getPatientID();
			tx.commit();
			this.insertGuardiansForPatient(result,array);
			//cpsDBDriver.sendUpdatedPatientObjToCPS(patient,dob);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return false;
			}
		}

	}

	/**
	 * This method returns the all the details a given patient Id.
	 * 
	 * @param patientId
	 * @return patient object of the OutPatient class
	 * @throws Method
	 *             throws a {@link RuntimeException} in failing the update,
	 *             throws a {@link HibernateException} on error rolling back
	 *             transaction.
	 */

	public OutPatient getPatientDetails(int patientId) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("select p from OutPatient as p where p.patientID = :pid order by patientLastUpdate");
			query.setParameter("pid", patientId);

			List patientList = query.list();

			if (patientList.size() == 0)
				return null;

			
			 
			OutPatient patient = (OutPatient) patientList.get(0);
			Set<Exams> patientExams = new HashSet<Exams>();
			Set<AnswerSet> answerSet = new HashSet<AnswerSet>();

			for (Visit v : patient.getVisits()) {
				for (Exams exam : v.getExams()) {
					patientExams.add(exam);
				}
			}

			query = session
					.createQuery("from AnswerSet where visit.patient=:pid");
			query.setParameter("pid", patient);

			List<AnswerSet> answersetList = query.list();
			for (AnswerSet as : answersetList) {
				answerSet.add(as);
			}

			patient.setExams(patientExams);
			patient.setAnswerSets(answerSet);

			tx.commit();
			return patient;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return null;
			}
		}

	}

	/**
	 * This method retrieve a list of current OPD patient
	 * 
	 * @return OPD patient list List<OutPatient>
	 * @throws Method
	 *             throws a {@link RuntimeException} in failing the update,
	 *             throws a {@link HibernateException} on error rolling back
	 *             transaction.
	 */
	public List<OutPatient> getPatientList() {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery("select p from OutPatient as p");
			List<OutPatient> patientList = query.list();
			tx.commit();
			return patientList;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return null;
			}
		}
	}

	/***
	 ***************** Need to Modify
	 * 
	 * @param userID
	 * @param visitType
	 * @return
	 */
	public OutPatient getPatient_By_VisitType(int userID, int visitType) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("Select pt from Patient as pt , Visit as visit where ( pt.patientID = visit.PID AND visit.Doctor = :uid AND visit.Type = '"
							+ ((visitType == 1) ? "OPD" : "Clinic")
							+ "') order by visit.DateOfVisit desc");
			query.setParameter("uid", userID);
			// query.setParameter("visitID", visitType);
			List patientList = query.list();
			OutPatient patient = new OutPatient();
			for (Iterator iter = patientList.iterator(); iter.hasNext();) {
				patient = (OutPatient) iter.next();
			}
			tx.commit();
			return patient;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return null;
			}
		}

	}

	/***
	 ***************** Need to Modify
	 * 
	 * @param userID
	 * @param visitType
	 * @return
	 */
	public List<Visit> getPatientsForDoctor(int userID, int visitType) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			

			Query query = session
					.createQuery("from Visit as v where v.visitDoctor = "+ userID + " AND v.visitType = '"+ ((visitType == 1) ? "OPD" : "Clinic")+ "') order by v.visitDate desc");
			System.out.print(userID);
			System.out.print(visitType);
			List<Visit> patientList = query.list();
			tx.commit();

			return patientList;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					System.out.println(ex.getMessage());
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return null;
			}
		}

	}

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}
	
	/***
	 * 
	 * 
	 * @return patientID
	 */
	public String getMaxPatientID() {
		
		Transaction tx = null;
		String HIN = "";
		
		try {			
			tx = session.beginTransaction();
			Query query = session.createSQLQuery("select MAX(patient_id) from opd_patient");
//			Query query = session.createSQLQuery("Select MAX(p.patientID) from Patient as p");
			List<?> list = query.list();
			HIN = (String)list.get(0).toString();			
			tx.commit();
		} catch (Exception e) {
			System.out.println("555555555555555555555555");
		}
		return HIN;
		
	}

	public boolean insertGuardiansForPatient(Object patient_id, JSONArray array) {
		// TODO Auto-generated method stub

		Transaction tx = null;
		try {
			
		    int Pid = Integer.valueOf(patient_id.toString());

			tx = session.beginTransaction();
			OutPatient patient = (OutPatient) session.get(OutPatient.class,
					Pid);
			
			
			System.out.println(array.length());
			
			
			for (int i = 0; i < array.length(); i++) {
				GuardianForPatient guardian = new GuardianForPatient();
				
				JSONObject obj = array.getJSONObject(i);
				guardian.setGuardianNIC(obj.getString("guardiannic"));
				guardian.setFirstName(obj.getString("guardianfname"));
				guardian.setLastName(obj.getString("guardianlname"));
				guardian.setGender(obj.getString("guardiangender"));
				guardian.setRelationship(obj.getString("guardianrelationship"));
				guardian.setAddress1(obj.getString("guardianaddress1"));
				guardian.setAddress2(obj.getString("guardianaddress2"));
				guardian.setAddress3(obj.getString("guardianaddress3"));
				guardian.setCity(obj.getString("guardiancity"));
				guardian.setPostalcode(obj.getString("guardianpostalcode"));
				guardian.setMobile(obj.getString("guardianmobile"));
				guardian.setTelephone(obj.getString("guardiantelephone"));
				guardian.setPatient(patient);
				

				session.save(guardian);
				guardian = null;
				tx.commit();
				tx = session.beginTransaction();
			
			}
			
			return true;
		} 
		
		catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
					System.out.print(ex.getMessage());
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return false;

	}

	public GuardianForPatient getGuardianDetails(String nic) {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("select p from GuardianForPatient as p where p.GuardianNIC = :nic ");
			query.setParameter("nic", nic);

			List GuardianList = query.list();

			if (GuardianList.size() == 0)
				return null;

			
			 
			GuardianForPatient guardian = (GuardianForPatient) GuardianList.get(0);
			
			tx.commit();
			return guardian;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return null;
			}
		}

	}

	public OutPatient getPatientsPatinentByFeild(String type,String feild) {
		System.out.println(type +""+"" +feild);
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			Query query = null;
			
			if(type.equals("nic")){
				System.out.println(type +""+"" +feild);
				
				query = session
						.createQuery("select p from OutPatient as p where p.patientNIC = :nic ");
				query.setParameter("nic", feild);
			}
			else if(type.equals("hin")){
				query = session
						.createQuery("select p from OutPatient as p where p.patientHIN = :hin ");
				query.setParameter("hin", feild);
			}
			else if(type.equals("name")){
				query = session
						.createQuery("select p from OutPatient as p where p.patientFullName = :patientFullName ");
				query.setParameter("patientFullName", feild);
			}

			List PatientList = query.list();

			if (PatientList.size() == 0)
				return null;

			
			 
			OutPatient  patient = (OutPatient) PatientList.get(0);
			
			tx.commit();
			return patient;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return null;
			}
		}
		
		
		
		
	}

	public List<Visit> getPatientsForDoctor(int userID, int visitType,
			String type, String feild) {
	
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String sql = null;
			
			if(type.equals("NIC")){
				sql = "from Visit as v where v.visitDoctor = "+ userID + " AND v.visitType = '"+ ((visitType == visitType) ? "OPD" : "Clinic")+ "' AND v.patient.patientNIC = '"+feild+"' ) order by v.visitDate desc";
				
			}
			else if(type.equals("HIN")){
				sql = "from Visit as v where v.visitDoctor = "+ userID + " AND v.visitType = '"+ ((visitType == visitType) ? "OPD" : "Clinic")+ "' AND v.patient.patientHIN = '"+feild+"' ) order by v.visitDate desc";

			}
			else if(type.equals("Name")){
				sql = "from Visit as v where v.visitDoctor = "+ userID + " AND v.visitType = '"+ ((visitType == visitType) ? "OPD" : "Clinic")+ "' AND v.patient.patientFullName = '"+feild+"' ) order by v.visitDate desc";

			}
			
			Query query = session
					.createQuery(sql);
			System.out.print(userID);
			System.out.print(visitType);
			List<Visit> patientList = query.list();
			tx.commit();

			return patientList;
		} catch (RuntimeException ex) {
			if (tx != null && tx.isActive()) {
				try {
					System.out.println(ex.getMessage());
					tx.rollback();
				} catch (HibernateException he) {
					System.err.println("Error rolling back transaction");
				}
				throw ex;
			}
			else if(tx == null)
			{
				throw ex;
			}
			else
			{
				return null;
			}
		}
	}

	

}
