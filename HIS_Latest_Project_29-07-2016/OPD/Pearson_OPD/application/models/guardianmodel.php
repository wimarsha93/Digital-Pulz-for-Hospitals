<?php

include_once 'servicecaller.php';

class GuardianModel {
	
	public function GuardianModel(){}
	  
	private $nic;
	private $fname;  			
	private $lname;
	private $gender;
	private $relationship;
	private $address1;
	private $address2;
	private $address3;
	private $city;
	private $postalcode;
	private $telephone;
	private $mobile;

	public function get_nic() { return $this->nic; } 
	public function get_fname() { return $this->fname; } 
	public function get_lname() { return $this->lname; } 
	public function get_gender() { return $this->gender; } 
	public function get_relationship() { return $this->relationship; } 

	public function get_address1() { return $this->address1; }
	public function get_address2() { return $this->address2; } 
	public function get_address3() { return $this->address3; } 
	public function get_city() { return $this->city; } 
	public function get_postalcode() { return $this->postalcode; } 

	public function get_telephone() { return $this->telephone; } 
	public function get_lang() { return $this->mobile; } 

	public function set_nic($x) { $this->nic = $x; } 
	public function set_fname($x) { $this->fname = $x; } 
	public function set_lname($x) { $this->lname = $x; } 
	public function set_gender($x) { $this->gender = $x; } 
	public function set_relationship($x) { $this->relationship = $x; } 
	public function set_address1($x) { $this->address1 = $x; }
	public function set_address2($x) { $this->address2 = $x; } 
	public function set_address3($x) { $this->address3 = $x; } 
	public function set_city($x) { $this->city = $x; } 
	public function set_postalcode($x) { $this->postalcode = $x; }  

	public function set_telephone($x) { $this->telephone = $x; } 
	public function set_mobile($x) { $this->mobile = $x; } 
	 
	public function jsonSerialize()
	{
			$post_data = array(
				"guardiannic" => $this->nic,
				"guardianfname" => $this->fname,						
				"guardianlname" => $this->lname,
				"guardiangender" =>  $this->gender,
				"guardianrelationship" => $this->relationship,
				"guardianaddress1" => $this->address1,
				"guardianaddress2" => $this->address2,
				"guardianaddress3" => $this->address3,
				"guardiancity" => $this->city,
				"guardianpostalcode" => $this->postalcode,
				"guardianmobile" => $this->mobile,
				"guardiantelephone" => $this->telephone,

		);
		return $post_data;
	}

	public function getGuardianByNIC($nic)
	{
		$service_url = SERVICE_BASE_URL."OutPatient/getPatientsGuardianByNIC/".$nic;
		$curl_request = new ServiceCaller();
		$response = $curl_request->curl_GET_Request($service_url);
		return json_decode($response);
	}
	 
}
?>