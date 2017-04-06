 package core.classes.opd;

import core.classes.api.user.AdminUser;

public class GuardianForPatient {
	
	private String GuardianNIC;
	private String FirstName;
	private String LastName;
	private String Gender;
	private String Relationship;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String postalcode;
	private String telephone;
	private String mobile;
	private OutPatient patient;
	
	public String getGuardianNIC() {
		return GuardianNIC;
	}
	public void setGuardianNIC(String guardianNIC) {
		GuardianNIC = guardianNIC;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getRelationship() {
		return Relationship;
	}
	public void setRelationship(String relationship) {
		Relationship = relationship;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public OutPatient getPatient() {
		return patient;
	}
	public void setPatient(OutPatient patient) {
		this.patient = patient;
	}

	
	
	

}
