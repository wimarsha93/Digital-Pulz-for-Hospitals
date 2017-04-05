package core.classes.opd;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.OrderBy;

import core.classes.api.user.AdminUser;

/***
 * Patient class contains all the attributes and there getters and setters.
 * @author MIYURU
 *
 */
public abstract class Patient  {

	private int patientID;
	private String patientTitle;
	private String patientFullName;
	private String patientPersonalUsedName;
	private String patientNIC;
	private String patientHIN;
	private String patientPassport;
	private String patientPhoto;
	private Date patientDateOfBirth;
	private String patientTelephone;
	private String patientGender;
	private String patientCivilStatus;
	private String patientPreferredLanguage;
	private String patientCitizenship;
	private String patientblood;/*
	private String patientContactPName;
	private String patientContactPNo;	*/
	private String patientAddress1;
	private String patientAddress2;
	private String patientAddress3;
	private String patientCity;
	private String patientPostalCode;
	private Date patientCreateDate;
	private AdminUser patientCreateUser;
	private Date patientLastUpdate;
	private AdminUser patientLastUpdateUser;
	private String patientRemarks;
	private int patientActive;
	
	private String emergnecyFname;
	private String emergencyLname;
	private String emergencyMobile;
	private String emergencyTelepone;
	private String emergencyAddress1;
	private String emergencyAddress2;
	private String emergencyAddress3;
	private String emergencyCity;
	private String emergencyPostalCode;
 
	private Set<Visit> visits = new HashSet<Visit>();
	private Set<Allergy> allergies = new HashSet<Allergy>();
	private Set<Attachments> attachments = new HashSet<Attachments>();
	private Set<Exams> exams = new HashSet<Exams>();
	private Set<Record> records = new HashSet<Record>(); 
	private Set<AnswerSet> answerSets = new HashSet<AnswerSet>();
	private Set<GuardianForPatient> guardians = new HashSet<GuardianForPatient>();

	public String getPatientblood() {
		return patientblood;
	}
	public void setPatientblood(String patientblood) {
		this.patientblood = patientblood;
	}
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public String getPatientTitle() {
		return patientTitle;
	}
	public void setPatientTitle(String patientTitle) {
		this.patientTitle = patientTitle;
	}
	public String getPatientFullName() {
		return patientFullName;
	}
	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}
	public String getPatientPersonalUsedName() {
		return patientPersonalUsedName;
	}
	public void setPatientPersonalUsedName(String patientPersonalUsedName) {
		this.patientPersonalUsedName = patientPersonalUsedName;
	}
	public String getPatientNIC() {
		return patientNIC;
	}
	public void setPatientNIC(String patientNIC) {
		this.patientNIC = patientNIC;
	}
	public String getPatientHIN() {
		return patientHIN;
	}
	public void setPatientHIN(String patientHIN) {
		this.patientHIN = patientHIN;
	}
	public String getPatientPassport() {
		return patientPassport;
	}
	public void setPatientPassport(String patientPassport) {
		this.patientPassport = patientPassport;
	}
	public String getPatientPhoto() {
		return patientPhoto;
	}
	public void setPatientPhoto(String patientPhoto) {
		this.patientPhoto = patientPhoto;
	}
	public Date getPatientDateOfBirth() {
		return patientDateOfBirth;
	}
	public void setPatientDateOfBirth(Date patientDateOfBirth) {
		this.patientDateOfBirth = patientDateOfBirth;
	}
	public String getPatientTelephone() {
		return patientTelephone;
	}
	public void setPatientTelephone(String patientTelephone) {
		this.patientTelephone = patientTelephone;
	}
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}
	public String getPatientCivilStatus() {
		return patientCivilStatus;
	}
	public void setPatientCivilStatus(String patientCivilStatus) {
		this.patientCivilStatus = patientCivilStatus;
	}
	public String getPatientPreferredLanguage() {
		return patientPreferredLanguage;
	}
	public void setPatientPreferredLanguage(String patientPreferredLanguage) {
		this.patientPreferredLanguage = patientPreferredLanguage;
	}
	public String getPatientCitizenship() {
		return patientCitizenship;
	}
	public void setPatientCitizenship(String patientCitizenship) {
		this.patientCitizenship = patientCitizenship;
	}
	/*public String getPatientContactPName() {
		return patientContactPName;
	}
	public void setPatientContactPName(String patientContactPName) {
		this.patientContactPName = patientContactPName;
	}
	public String getPatientContactPNo() {
		return patientContactPNo;
	}
	public void setPatientContactPNo(String patientContactPNo) {
		this.patientContactPNo = patientContactPNo;
	}
	*/
	public String getPatientAddress1() {
		return patientAddress1;
	}
	public void setPatientAddress1(String patientAddress1) {
		this.patientAddress1 = patientAddress1;
	}
	public String getPatientAddress2() {
		return patientAddress2;
	}
	public void setPatientAddress2(String patientAddress2) {
		this.patientAddress2 = patientAddress2;
	}
	public String getPatientAddress3() {
		return patientAddress3;
	}
	public void setPatientAddress3(String patientAddress3) {
		this.patientAddress3 = patientAddress3;
	}
	public String getPatientCity() {
		return patientCity;
	}
	public void setPatientCity(String patientCity) {
		this.patientCity = patientCity;
	}
	public String getPatientPostalCode() {
		return patientPostalCode;
	}
	public void setPatientPostalCode(String patientPostalCode) {
		this.patientPostalCode = patientPostalCode;
	}
	public Date getPatientCreateDate() {
		return patientCreateDate;
	}
	public void setPatientCreateDate(Date patientCreateDate) {
		this.patientCreateDate = patientCreateDate;
	}
	public AdminUser getPatientCreateUser() {
		return patientCreateUser;
	}
	public void setPatientCreateUser(AdminUser patientCreateUser) {
		this.patientCreateUser = patientCreateUser;
	}
	public Date getPatientLastUpdate() {
		return patientLastUpdate;
	}
	public void setPatientLastUpdate(Date patientLastUpdate) {
		this.patientLastUpdate = patientLastUpdate;
	}
	public AdminUser getPatientLastUpdateUser() {
		return patientLastUpdateUser;
	}
	public void setPatientLastUpdateUser(AdminUser patientLastUpdateUser) {
		this.patientLastUpdateUser = patientLastUpdateUser;
	}
	public String getPatientRemarks() {
		return patientRemarks;
	}
	public void setPatientRemarks(String patientRemarks) {
		this.patientRemarks = patientRemarks;
	}
	public int getPatientActive() {
		return patientActive;
	}
	public void setPatientActive(int patientActive) {
		this.patientActive = patientActive;
	}
	public String getEmergnecyFname() {
		return emergnecyFname;
	}
	public void setEmergnecyFname(String emergnecyFname) {
		this.emergnecyFname = emergnecyFname;
	}
	public String getEmergencyLname() {
		return emergencyLname;
	}
	public void setEmergencyLname(String emergencyLname) {
		this.emergencyLname = emergencyLname;
	}
	public String getEmergencyMobile() {
		return emergencyMobile;
	}
	public void setEmergencyMobile(String emergencyMobile) {
		this.emergencyMobile = emergencyMobile;
	}
	public String getEmergencyTelepone() {
		return emergencyTelepone;
	}
	public void setEmergencyTelepone(String emergencyTelepone) {
		this.emergencyTelepone = emergencyTelepone;
	}
	public String getEmergencyAddress1() {
		return emergencyAddress1;
	}
	public void setEmergencyAddress1(String emergencyAddress1) {
		this.emergencyAddress1 = emergencyAddress1;
	}
	public String getEmergencyAddress2() {
		return emergencyAddress2;
	}
	public void setEmergencyAddress2(String emergencyAddress2) {
		this.emergencyAddress2 = emergencyAddress2;
	}
	public String getEmergencyAddress3() {
		return emergencyAddress3;
	}
	public void setEmergencyAddress3(String emergencyAddress3) {
		this.emergencyAddress3 = emergencyAddress3;
	}
	public String getEmergencyCity() {
		return emergencyCity;
	}
	public void setEmergencyCity(String emergencyCity) {
		this.emergencyCity = emergencyCity;
	}
	public String getEmergencyPostalCode() {
		return emergencyPostalCode;
	}
	public void setEmergencyPostalCode(String emergencyPostalCode) {
		this.emergencyPostalCode = emergencyPostalCode;
	}
	public Set<Visit> getVisits() {
		return visits;
	}
	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}
	public Set<Allergy> getAllergies() {
		return allergies;
	}
	public void setAllergies(Set<Allergy> allergies) {
		this.allergies = allergies;
	}
	public Set<Attachments> getAttachments() {
		return attachments;
	}
	public void setAttachments(Set<Attachments> attachments) {
		this.attachments = attachments;
	}
	public Set<Exams> getExams() {
		return exams;
	}
	public void setExams(Set<Exams> exams) {
		this.exams = exams;
	}
	public Set<Record> getRecords() {
		return records;
	}
	public void setRecords(Set<Record> records) {
		this.records = records;
	}
	public Set<AnswerSet> getAnswerSets() {
		return answerSets;
	}
	public void setAnswerSets(Set<AnswerSet> answerSets) {
		this.answerSets = answerSets;
	}
	public Set<GuardianForPatient> getGuardians() {
		return guardians;
	}
	public void setGuardians(Set<GuardianForPatient> guardians) {
		this.guardians = guardians;
	}
}