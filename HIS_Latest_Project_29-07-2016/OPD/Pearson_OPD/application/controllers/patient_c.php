<?php
session_start();
    class Patient_c  extends CI_Controller{
            var $_site_base_url=SITE_BASE_URL;
			public function __construct()
			{
				parent::__construct();        
				$this->load->helper(array('form', 'url'));				
			}

			
			  
			public function add($status='0')
			{
                            if (isset($_SESSION["user"])) {
                                if ($_SESSION["user"] == -1) {
                                    redirect($this->_site_base_url);
                                }
                                } else {
                                    redirect($this->_site_base_url);
                                }
				$data['status'] = $status;
				  
				$data['visitid'] = '0';
 
				$data['title'] = 'Register New Patient';
 
				$this->load->view('Components/headerInward',$data);
 	            
				// loading left side navigation
                $data['leftnavpage'] = 'new_patient';
				$this->load->view('Components/left_navbar',$data);
				
				//************************************************************************************
				
				$this->load->view('patient_m_v',$data);
				$this->load->view('Components/bottom');
				
			}
			
			public function edit($pid,$status='0')
			{
			if (isset($_SESSION["user"])) {
                                if ($_SESSION["user"] == -1) {
                                    redirect($this->_site_base_url);
                                }
                                } else {
                                    redirect($this->_site_base_url);
                                }
				$data['status'] = $status;
				$data['pid'] = $pid;
				$data['title'] = 'Edit Patient';
				$data['visitid'] = '0';
                                
                                //get the visit_type of the patient from the database according to the passed
                                //patiet id including other required variables.
                                $data['visit_type'] = 1;
				$this->load->view('Components/headerInward',$data);
 	 
				// loading left side navigation
				$data['leftnavpage'] = '';
				$this->load->view('Components/left_navbar',$data);
				//************************************************************************************
				
				
				// show patient profile mini
			 
				$this->load->model('PatientModel','patient');
				$this->patient->set_pid( $pid );
				$data['pprofile'] = json_decode( $this->patient->getPatient() );
				
                $this->load->view('patient_m_v',$data);

				//****************************************************************************
				
				/*$this->load->library('template');
				$this->template->title('Patient');
				$this->template
				->set_layout('panellayout')
				->build('patient_m_v',$data);*/
				 $this->load->view('Components/bottom',$data);
			}
			 	
			

                        
                        
            public function save()
			{
				 if (isset($_SESSION["user"])) {
                                if ($_SESSION["user"] == -1) {
                                    redirect($this->_site_base_url);
                                }
                                } else {
                                    redirect($this->_site_base_url);
                                }
				$ff = $_FILES['userfile']['name']; 

				if(!empty($ff))
				{ 
					$config['upload_path'] = './uploads/patient_photos/';
					$config['allowed_types'] = 'jpg|png';
					$config['max_size']	= '51200';
					$config['remove_spaces']= TRUE;
					$config['overwrite']= TRUE;
					$this->load->library('upload', $config);
				 
					$upload_data;
					
					if (!$this->upload->do_upload())
					{
						 array('error' => $this->upload->display_errors());
					}
					else
					{
						$upload_data = $this->upload->data();
					 
					}
				}else
				{				
					$upload_data['full_path'] = null;
				}
				
					$this->load->model('PatientModel','patient');
					$this->patient->set_title($this->input->post('title'));
					$this->patient->set_fullname($this->input->post('fullname'));
					$this->patient->set_personalname($this->input->post('personalname'));
					$this->patient->set_nic($this->input->post('nic'));
					$this->patient->set_passport($this->input->post('passport'));						
					//$this->patient->set_hin($this->input->post('hin'));
					$this->patient->set_photo( $upload_data['full_path']);
					$this->patient->set_dob($this->input->post('dob'));
					$this->patient->set_gender($this->input->post('gender'));
					//$this->patient->set_contactpname($this->input->post('contactpname'));
					//$this->patient->set_contactpno($this->input->post('contactpno'));
					$this->patient->set_cstatus($this->input->post('cstatus'));
					$this->patient->set_address1($this->input->post('address1'));
					$this->patient->set_address2($this->input->post('address2'));
					$this->patient->set_address3($this->input->post('address3'));
					$this->patient->set_city($this->input->post('city'));
					$this->patient->set_postalcode($this->input->post('postalCode'));

					$this->patient->set_telephone($this->input->post('telephone'));
					$this->patient->set_lang($this->input->post('lang'));
					$this->patient->set_citizen($this->input->post('citizen'));
					$this->patient->set_blood($this->input->post('blood'));

					$this->patient->set_emergnecyfname($this->input->post('emergencyfirstname'));
					$this->patient->set_emergencylname($this->input->post('emergencylastname'));
					$this->patient->set_emergencymobile($this->input->post('emergencyMobile'));
					$this->patient->set_emergencytelepone($this->input->post('emergencyTelephone'));
					$this->patient->set_emergencyaddress1($this->input->post('emergencyaddress1'));
					$this->patient->set_emergencyaddress2($this->input->post('emergencyaddress2'));
					$this->patient->set_emergencyaddress3($this->input->post('emergencyaddress3'));
					$this->patient->set_emergencycity($this->input->post('emergencycity'));
					$this->patient->set_emergencypostalcode($this->input->post('emergencypostalCode'));

					$this->patient->set_remarks($this->input->post('remarks'));
					$this->patient->set_userid( $this->session->userdata("userid"));
					$this->patient->set_active($this->input->post('1'));
					
					for($i = 0; $i< $this->input->post('gtablerows'); $i++)
    				{
    					$this->load->model('GuardianModel','guardian');


    					$this->guardian->set_nic($this->input->post('tableguardianNIC')[$i]);
						$this->guardian->set_fname($this->input->post('tableguardianFirstname')[$i]);
						$this->guardian->set_lname($this->input->post('tableguardianLastname')[$i]);
						$this->guardian->set_gender($this->input->post('tableguardianGender')[$i]);
						$this->guardian->set_relationship($this->input->post('tableguardianRelationship')[$i]);
						$this->guardian->set_address1($this->input->post('tableguardianAddress1')[$i]);
						$this->guardian->set_address2($this->input->post('tableguardianAddress2')[$i]);
						$this->guardian->set_address3($this->input->post('tableguardianAddress3')[$i]);
						$this->guardian->set_city($this->input->post('tableguardianCity')[$i]);
						$this->guardian->set_postalcode($this->input->post('tableguardianPostalCode')[$i]);	
						$this->guardian->set_mobile($this->input->post('tableguardianMobile')[$i]);
						$this->guardian->set_telephone($this->input->post('tableguardianTelephone')[$i]);

    					$this->patient->jsonSearializeGuardian($this->guardian->jsonSerialize());
    					
					}


					$data['status'] =  json_decode( $this->patient->addPatient());
				 
					if($data['status'] == null)
						$this->add("False");
					else
						$this->add($data['status']->patientID);

					/*redirect($this->SITE_BASE_URL. "operator_home_c/viewpatient/"  );*/
			}
			 
			  
			public function update($pid)
			{
				  if (isset($_SESSION["user"])) {
                                if ($_SESSION["user"] == -1) {
                                    redirect($this->_site_base_url);
                                }
                                } else {
                                    redirect($this->_site_base_url);
                                }
				$this->load->model('PatientModel','patient');
				
				$config['upload_path'] = './uploads/patient_photos/';
				$config['allowed_types'] = 'jpg|png';
				$config['max_size']	= '51200';
				$config['remove_spaces']= TRUE;
				$config['overwrite']= TRUE;
				$ff = $_FILES['userfile']['name']; 

				if(!empty($ff))
				{ 
					$upload_config['file_name'] = $this->input->post('uplddfnm');
			   
					$this->load->library('upload', $config);
				 
					$upload_data;
					if (!$this->upload->do_upload())
					{
						array('error' => $this->upload->display_errors());
					}
					else
					{
						$upload_data = $this->upload->data();
					}
				
				}else 
				{			 
					$upload_data['full_path'] = NULL;
				}
				
				$this->patient->set_pid($pid);
				$this->patient->set_title($this->input->post('title'));
				$this->patient->set_fullname($this->input->post('fullname'));
				$this->patient->set_personalname($this->input->post('personalname'));
				$this->patient->set_nic($this->input->post('nic'));
				$this->patient->set_passport($this->input->post('passport'));						
				//$this->patient->set_hin($this->input->post('hin'));
				$this->patient->set_photo( $upload_data['full_path']);
				$this->patient->set_dob($this->input->post('dob'));
				$this->patient->set_gender($this->input->post('gender'));
				//$this->patient->set_contactpname($this->input->post('contactpname'));
				//$this->patient->set_contactpno($this->input->post('contactpno'));
				$this->patient->set_cstatus($this->input->post('cstatus'));
				$this->patient->set_address1($this->input->post('address1'));
				$this->patient->set_address2($this->input->post('address2'));
				$this->patient->set_address3($this->input->post('address3'));
				$this->patient->set_city($this->input->post('city'));
				$this->patient->set_postalcode($this->input->post('postalCode'));

				$this->patient->set_telephone($this->input->post('telephone'));
				$this->patient->set_lang($this->input->post('lang'));
				$this->patient->set_citizen($this->input->post('citizen'));
				$this->patient->set_blood($this->input->post('blood'));

				$this->patient->set_emergnecyfname($this->input->post('emergencyfirstname'));
				$this->patient->set_emergencylname($this->input->post('emergencylastname'));
				$this->patient->set_emergencymobile($this->input->post('emergencyMobile'));
				$this->patient->set_emergencytelepone($this->input->post('emergencyTelephone'));
				$this->patient->set_emergencyaddress1($this->input->post('emergencyaddress1'));
				$this->patient->set_emergencyaddress2($this->input->post('emergencyaddress2'));
				$this->patient->set_emergencyaddress3($this->input->post('emergencyaddress3'));
				$this->patient->set_emergencycity($this->input->post('emergencycity'));
				$this->patient->set_emergencypostalcode($this->input->post('emergencypostalCode'));

				$this->patient->set_remarks($this->input->post('remarks'));
				$this->patient->set_userid( $this->session->userdata("userid"));
				$this->patient->set_active($this->input->post('active'));

				for($i = 0; $i< $this->input->post('gtablerows'); $i++)
				{
					$this->load->model('GuardianModel','guardian');


					$this->guardian->set_nic($this->input->post('tableguardianNIC')[$i]);
					$this->guardian->set_fname($this->input->post('tableguardianFirstname')[$i]);
					$this->guardian->set_lname($this->input->post('tableguardianLastname')[$i]);
					$this->guardian->set_gender($this->input->post('tableguardianGender')[$i]);
					$this->guardian->set_relationship($this->input->post('tableguardianRelationship')[$i]);
					$this->guardian->set_address1($this->input->post('tableguardianAddress1')[$i]);
					$this->guardian->set_address2($this->input->post('tableguardianAddress2')[$i]);
					$this->guardian->set_address3($this->input->post('tableguardianAddress3')[$i]);
					$this->guardian->set_city($this->input->post('tableguardianCity')[$i]);
					$this->guardian->set_postalcode($this->input->post('tableguardianPostalCode')[$i]);	
					$this->guardian->set_mobile($this->input->post('tableguardianMobile')[$i]);
					$this->guardian->set_telephone($this->input->post('tableguardianTelephone')[$i]);

					$this->patient->jsonSearializeGuardian($this->guardian->jsonSerialize());
				}
 
				$data['status'] = $this->patient->updatePatient();
				
				$this->edit($pid,$data['status']); 
			}
			
			public function search()
			{
                            if (isset($_SESSION["user"])) {
                                if ($_SESSION["user"] == -1) {
                                    redirect($this->_site_base_url);
                                }
                                } else {
                                    redirect($this->_site_base_url);
                                }
				$data['status'] = '0';
				$this->load->view('Components/headerInward',$data);


				// loading left side navigation
				$data['leftnavpage'] = 'patient_search';
				$data['visit_type'] = '';
				$this->load->view('Components/left_navbar',$data);
				//************************************************************************************

				// load all patients 
				$this->load->model('PatientModel','patient');
				$data['patients'] = json_decode($this->patient->getAllPatients());
                                //load relavant view
				$this->load->view('patients_full_search_v.php',$data);
				$this->load->view('Components/bottom');
			} 

			
			public function getGuardianByNIC($nic)
			{
				$this->load->model('guardianmodel');
				$result = $this->guardianmodel->getGuardianByNIC($nic);

				echo json_encode($result);

			}
			
        }
?>