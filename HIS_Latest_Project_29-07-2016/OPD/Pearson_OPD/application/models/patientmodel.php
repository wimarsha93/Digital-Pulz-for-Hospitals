<?php

include_once 'servicecaller.php';

class PatientModel {
	
	public function PatientModel(){}
	  
	private $pid;
	private $title;
	private $fullname;
	private $personalname;
	private $nic;
	private $passport;  			
	private $hin;
	private $photo;
	private $dob;
	private $gender;
	//private $contactpname;
	//private $contactpno;
	private $cstatus; 
	private $address1;
	private $address2;
	private $address3;
	private $city;
	private $postalcode;
	private $telephone;
	private $lang;
	private $citizen;
	private $blood;

	private $emergnecyfname;
	private $emergencylname;
	private $emergencymobile;
	private $emergencytelepone;
	private $emergencyaddress1;
	private $emergencyaddress2;
	private $emergencyaddress3;
	private $emergencycity;
	private $emergencypostalcode;

	private $guardianlist = array();

	private $remarks;
	private $userid;  
	private $active;
	
	public function get_pid() { return $this->pid; }
	public function get_active() { return $this->active; } 	
	public function get_title() { return $this->title; } 
	public function get_fullname() { return $this->fullname; } 
	public function get_personalname() { return $this->personalname; } 
	public function get_nic() { return $this->nic; } 
	public function get_passport() { return $this->passport; } 
	public function get_hin() { return $this->hin; } 
	public function get_photo() { return $this->photo; } 
	public function get_dob() { return $this->dob; } 
	public function get_gender() { return $this->gender; } 
	//public function get_contactpname() { return $this->contactpname; } 
	//public function get_contactpno() { return $this->contactpno; } 
	public function get_cstatus() { return $this->cstatus; }

	public function get_address1() { return $this->address1; }
	public function get_address2() { return $this->address2; } 
	public function get_address3() { return $this->address3; } 
	public function get_city() { return $this->city; } 
	public function get_postalcode() { return $this->postalcode; } 

	public function get_telephone() { return $this->telephone; } 
	public function get_lang() { return $this->lang; } 
	public function get_citizen() { return $this->citizen; }
	public function get_blood() { return $this->blood; } 

	public function get_emergnecyfname() { return $this->emergnecyfname; }
	public function get_emergencylname() { return $this->emergencylname; } 
	public function get_emergencymobile() { return $this->emergencymobile; } 
	public function get_emergencytelepone() { return $this->emergencytelepone; } 
	public function get_emergencyaddress1() { return $this->emergencyaddress1; }
	public function get_emergencyaddress2() { return $this->emergencyaddress2; } 
	public function get_emergencyaddress3() { return $this->emergencyaddress3; } 
	public function get_emergencycity() { return $this->emergencycity; } 
	public function get_emergencypostalcode() { return $this->emergencypostalcode; } 

	public function get_remarks() { return $this->remarks; } 
	public function get_userid() { return $this->userid; } 
	public function set_title($x) { $this->title = $x; } 
	public function set_fullname($x) { $this->fullname = $x; } 
	public function set_personalname($x) { $this->personalname = $x; } 
	public function set_nic($x) { $this->nic = $x; } 
	public function set_active($x) { $this->active = $x; } 
	public function set_passport($x) { $this->passport = $x; } 
	public function set_hin($x) { $this->hin = $x; } 
	public function set_photo($x) { $this->photo = $x; } 
	public function set_dob($x) { $this->dob = $x; } 
	public function set_gender($x) { $this->gender = $x; } 
	public function set_contactpname($x) { $this->contactpname = $x; } 
	public function set_contactpno($x) { $this->contactpno = $x; } 
	public function set_cstatus($x) { $this->cstatus = $x; }

	public function set_address1($x) { $this->address1 = $x; }
	public function set_address2($x) { $this->address2 = $x; } 
	public function set_address3($x) { $this->address3 = $x; } 
	public function set_city($x) { $this->city = $x; } 
	public function set_postalcode($x) { $this->postalcode = $x; }  

	public function set_telephone($x) { $this->telephone = $x; } 
	public function set_lang($x) { $this->lang = $x; } 
	public function set_citizen($x) { $this->citizen = $x; }
	public function set_blood($x) { $this->blood = $x; } 

	public function set_emergnecyfname($x) { $this->emergnecyfname = $x; }
	public function set_emergencylname($x) { $this->emergencylname = $x; } 
	public function set_emergencymobile($x) { $this->emergencymobile = $x; } 
	public function set_emergencytelepone($x) { $this->emergencytelepone = $x; } 
	public function set_emergencyaddress1($x) { $this->emergencyaddress1 = $x; }  
	public function set_emergencyaddress2($x) { $this->emergencyaddress2 = $x; }
	public function set_emergencyaddress3($x) { $this->emergencyaddress3 = $x; } 
	public function set_emergencycity($x) { $this->emergencycity = $x; } 
	public function set_emergencypostalcode($x) { $this->emergencypostalcode = $x; } 

	public function set_remarks($x) { $this->remarks = $x; } 
	public function set_userid($x) { $this->userid = $x; } 
	public function set_pid($x) { $this->pid = $x; } 


	//guardian array getter and setter
	public function get_guardianlist() { return $this->guardianlist; }
	public function set_guardianlist($x) { $this->guardianlist = $x; } 

	public function addPatient()
	{
		$patientJSON   = json_encode($this->jsonSerialize());
		$service_url = SERVICE_BASE_URL."OutPatient/registerPatient";
		$MediaType = "application/json";
		$curl_request  = new ServiceCaller();
                $response =  $curl_request->curl_POST_Request($service_url,$patientJSON,$MediaType);
		return $response;
	}
	 
	public function updatePatient()
	{
		$patientJSON  = json_encode($this->jsonSerialize());
		$service_url =  SERVICE_BASE_URL."OutPatient/updatePatient";
		$MediaType = "application/json";
		$curl_request  = new ServiceCaller();
	    $response = $curl_request->curl_POST_Request($service_url,$patientJSON,$MediaType);
		return $response;
	} 
    
	public function getPatient()
	{
		$service_url = SERVICE_BASE_URL."OutPatient/getPatientsByPID/".$this->pid;
		$curl_request = new ServiceCaller();
		$response = $curl_request->curl_GET_Request($service_url);
		return $response;
	}
        
        public function getAlleryList()
	{
		$service_url = SERVICE_BASE_URL."LiveSearch/allergyLivesearch";
		$curl_request = new ServiceCaller();
		$response = $curl_request->curl_GET_Request($service_url);
		return $response;
	}
        
	
	public function getDoctorPatients($docid,$visit_type)
	{
		$service_url = SERVICE_BASE_URL."OutPatient/getPatientsForDoctor/".$docid."/".$visit_type;
		$curl_request = new ServiceCaller();
		$response = $curl_request->curl_GET_Request($service_url);
		return $response;
	}
	
	public function getAllPatients()
	{
		$service_url = SERVICE_BASE_URL."OutPatient/getAllPatients";
		$curl_request = new ServiceCaller();
		$response = $curl_request->curl_GET_Request($service_url);
		return $response;
	}

	public function jsonSearializeGuardian($array)
	{
		$this->guardianlist[] = $array;

	}
	 
	public function jsonSerialize()
	{

			$post_data = array(
				"pid" => $this->pid,
				"title" => $this->title,
				"fullname" => $this->fullname,
				"personalname" => $this->personalname,
				"nic" => $this->nic,
				"passport" => $this->passport,						
				"hin" => $this->hin,
				"photo" =>  $this->photo,
				"dob" => $this->dob,
				"gender" => $this->gender,
				"cstatus" => $this->cstatus,
				//"contactpname" => $this->contactpname,
				//"contactpno" => $this->contactpno,
				"address1" => $this->address1,
				"address2" => $this->address2,
				"address3" => $this->address3,
				"city" => $this->city,
				"postalcode" => $this->postalcode,
				"telephone" => $this->telephone,
				"lang" => $this->lang,
				"citizen" => $this->citizen,
				"blood" => $this->blood,

				"emergencyfname" => $this->emergnecyfname,
				"emergencylname" => $this->emergencylname,
				"emergencymobile" => $this->emergencymobile,
				"emergencyTelephone" => $this->emergencytelepone,
				"emergencyaddress1" => $this->emergencyaddress1,
				"emergencyaddress2" => $this->emergencyaddress2,
				"emergencyaddress3" => $this->emergencyaddress3,
				"emergencycity" => $this->emergencycity,
				"emergencypostalcode" => $this->emergencypostalcode,

				

				"guardianlist" => $this->guardianlist,

				"remarks" => $this->remarks,
				"active" => $this->active,
				"userid" =>  $this->userid,

		);
		return $post_data;
	}

	
	 
}
?>