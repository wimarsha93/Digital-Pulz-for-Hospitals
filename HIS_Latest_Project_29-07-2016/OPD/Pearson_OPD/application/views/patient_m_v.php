	
<script type="text/javascript">
	document.getElementById('patient_overview_header').style.display = "";
	document.getElementById('patient_info_header').style.display = "";
	document.getElementById('patient_prof_header').style.display = "";
	
	document.getElementsByClassName('patient_overview')[0].style.display = "";
	document.getElementsByClassName('patient_info')[0].style.display = "";

	var cusid_ele = document.getElementsByClassName('patient_prof');  
	for (var i = 0; i < cusid_ele.length; ++i) {
	       document.getElementsByClassName('patient_prof')[i].style.display = "";
	}
	document.getElementsByClassName('patient_prof')[0].className = 'patient_prof active';
</script>

<script>
    

    $(document).ready(function() {

    	//add guardians to the temp table
    	$('#btnGuardianAdd').click(function(){
    		$('#guardianList').prop('hidden', false);

    		var nic = $('#guardianNIC').val();
    		var gFirstName = $('#guardianFirstname').val();
    		var gLastName = $('#guardianLastname').val();
    		var gGender = $('#guardianGender').val();
    		var gRealtionship = $('#guardianRelationship').val();
            var gAddress1 = $('#guardianAddress1').val();
            var gAddress2 = $('#guardianAddress2').val();
            var gAddress3 = $('#guardianAddress3').val();
            var gCity = $('#guardianCity').val();
            var gPostalCode = $('#guardianPostalCode').val();
            var gMobile = $('#guardianMobile').val();
            var gTelephone = $('#guardianTelephone').val();

    		var guardianAppend = '<tr><td><input type="checkbox" name="record"></button></td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianNIC[]" value="'+nic+'" />'+nic+'</td>'+
    		'<td><input class="form-control" type="hidden" name="tableguardianFirstname[]" value="'+gFirstName+'" />' + gFirstName + '</td>'+
    		'<td><input class="form-control" type="hidden" name="tableguardianLastname[]" value="'+gLastName+'" />' + gLastName + '</td>'+
    		'<td><input class="form-control" type="hidden" name="tableguardianGender[]" value="'+gGender+'" />' + gGender + '</td>'+
    		'<td><input class="form-control" type="hidden" name="tableguardianRelationship[]" value="'+gRealtionship+'" />' + gRealtionship + '</td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianAddress1[]" value="'+gAddress1+'" />' + gAddress1 + '</td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianAddress2[]" value="'+gAddress2+'" />' + gAddress2 + '</td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianAddress3[]" value="'+gAddress3+'" />' + gAddress3 + '</td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianCity[]" value="'+gCity+'" />' + gCity + '</td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianPostalCode[]" value="'+gPostalCode+'" />' + gPostalCode + '</td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianMobile[]" value="'+gMobile+'" />' + gMobile + '</td>'+
            '<td><input class="form-control" type="hidden" name="tableguardianTelephone[]" value="'+gTelephone+'" />' + gTelephone + '</td>'+
            '</tr>';

    		$('#gtablerows').val($('#tableGuardians tbody tr').length+1);


    		$('#tableGuardians tbody').append(guardianAppend);

    		$('#guardianNIC').val("");
    		$('#guardianFirstname').val("");
    		$('#guardianLastname').val("");
            $('#guardianAddress1').val("");
            $('#guardianAddress2').val("");
            $('#guardianAddress3').val("");
            $('#guardianCity').val("");
            $('#guardianPostalCode').val("");
            $('#guardianMobile').val("");
            $('#guardianTelephone').val("");

            $('#guardianGender').val("Male");
            $('#guardianRelationship').val("Father");
    	});

    	// Find and remove selected table rows
        $("#btnDeleteGuardian").click(function(){
            $("#tableGuardians tbody").find('input[name="record"]').each(function(){
            	if($(this).is(":checked")){
                    $(this).parents("tr").remove();
                }
            });

            if($('#tableGuardians tbody tr').length == 0)
            {
            	$('#guardianList').prop('hidden', true);
            }
        });

        $("#fullname").keypress(function(event) {
            if ((event.which > 47 && event.which < 58))
            {
                event.preventDefault();
            }
        });

        $("#btnGuardianSearch").click(function(){
           $.ajax({
                type: "GET",
                url: '<?php echo base_url(); ?>index.php/patient_c/getGuardianByNIC/'+$('#guardianNIC').val(),
                dataType: 'json',
                success: function(output) {
                    if(output!= null)
                    {
                        $('#guardianFirstname').val(output['firstName']);
                        $('#guardianLastname').val(output['lastName']);
                        $('#guardianAddress1').val(output['address1']);
                        $('#guardianAddress2').val(output['address2']);
                        $('#guardianAddress3').val(output['address3']);
                        $('#guardianCity').val(output['city']);
                        $('#guardianPostalCode').val(output['postalcode']);
                        $('#guardianMobile').val(output['mobile']);
                        $('#guardianTelephone').val(output['telephone']);


                        $('#guardianGender').val(output['gender']);
                        $('#guardianRelationship').val(output['relationship']);
                        //select dropdown value
                    }
                    
                }
            });
        });

        //Check box toggle to enable and disable the add guardians
        $('#checkboxGuardian').click(function(){
            	if($(this).prop("checked") == true){
                	$('#btnAddGuardian').prop('disabled', false);
            	}
            	else if($(this).prop("checked") == false){
            		if($('#tableGuardians tbody tr').length > 0)
            		{
	            		var result = confirm("Guardian list contain data, Do you want to remove them?");
						if (result == true) {
						   $("#tableGuardians tbody").find('input[name="record"]').each(function(){
                    			$(this).parents("tr").remove();
            				});

						   $('#btnAddGuardian').prop('disabled', true);
						   $('#guardianList').prop('hidden', true);
                           $('#gtablerows').val(0);
						}
					}
                    else
                    {
                        $('#btnAddGuardian').prop('disabled', true);
                        $('#guardianList').prop('hidden', true);
                        $('#gtablerows').val(0);
                    }
                	
            	}
        });

        var allempty = true;
        $('#nic,#hin ,#passport, #photo, #uplddfnm').change(function() {

            $('#nic,#hin ,#passport, #photo, #uplddfnm').each(function() {

                if ($(this).val() != '') {

                    allempty = false;
                }
                if (allempty) {

                    $('#nic,#hin ,#passport, #photo, #uplddfnm').attr('required', 'required');
                } else
                {
                    $('#nic,#hin ,#passport, #photo, #uplddfnm').removeAttr('required');
                }
            });
            allempty = true;
        });


        $('#nic,#hin ,#passport, #photo, #uplddfnm').each(function() {

            if ($(this).val() != '') {

                allempty = false;
            }
            if (allempty) {

                $('#nic,#hin ,#passport, #photo, #uplddfnm').attr('required', 'required');
            } else
            {
                $('#nic,#hin ,#passport, #photo, #uplddfnm').removeAttr('required');
            }
        });


        var previousTitle;

        $("#title").focus(function() {
            previousTitle = this.value;
        }).change(function() {

            if (this.value == "Unknown")
            {
                $('#myform :input').removeAttr('required');
                $('#myform :input').attr('readonly', 'readonly');
                $('#gender').removeAttr('readonly');
                $('#photo').removeAttr('readonly');
                $('#remarks').removeAttr('readonly');

                $('#gender').attr('required', 'required');
                $('#photo').attr('required', 'required');
                $('#remarks').attr('required', 'required');
                $(this).removeAttr('readonly');
            }

            if (this.value == "Rev")
            {
                $('#myform :input').removeAttr('readonly');
                $('#cstatus').attr('readonly', 'readonly');
            }

            if (this.value == "Miss.")
            {
                $('#myform :input').removeAttr('readonly');
                $('#cstatus').attr('readonly', 'readonly');
            }

            if (this.value == "Baby")
            {

                $('#myform :input').removeAttr('readonly');
                $("#lblnic").html("Guardians NIC");

                $('#guarname').attr('required', 'required');
                $('#nic').attr('required', 'required');
                $('#dob').attr('required', 'required');
                $("#dob").html("Date of Birth*");

                $("#lblcontactname").html("Guardians Name*");
                $("#lblcontactno").html("Guardians Telephone*");
                $("#lblpassport").html("Guardians Passport");
                $("#lblhin").html("Childs HIN");
                $("#lblphoto").html("Guardians Photo");

                $('#contactpname').attr('required', 'required');
                $('#contactpno').attr('required', 'required');

                $("#lbladdress").html("Guardians Address*");
                $("#address").attr('required', 'required');

                $("#contactorguardian").insertAfter($("#grpcivil"));

                $("#grplang").hide();
                $("#grptel").hide();
                $('#cstatus').attr('readonly', 'readonly')
            } else
            {
                // refresh
                if (previousTitle == "Baby" | previousTitle == "Rev" | previousTitle == "Unknown" | previousTitle == "Miss.") {
                    document.location.reload(true);
                }
            }
            previousTitle = this.value;
        });

        function makeform(title) {

            if (title == "Rev")
            {
                $('#cstatus').attr('readonly', 'readonly');
            }


            if (title == "Baby")
            {

                $("#lblnic").html("Guardians NIC*");

                $('#guarname').attr('required', 'required');
                $('#nic').attr('required', 'required');
                $('#dob').attr('required', 'required');
                $("#dob").html("Date of Birth*");

                $("#lblcontactname").html("Guardians Name*");
                $("#lblcontactno").html("Guardians Telephone*");

                $('#contactpname').attr('required', 'required');
                $('#contactpno').attr('required', 'required');

                $("#lbladdress").html("Guardians Address*");
                $("#address").attr('required', 'required');

                $("#contactorguardian").insertAfter($("#grpcivil"));

                $("#grplang").hide();
                $("#grptel").hide();
                $('#cstatus').attr('readonly', 'readonly');

            }

        }

        // $(function() {
            
        //     $("#dob").datepicker();
        //     $("#dob").datepicker("option", "dateFormat", "yy-mm-dd");
        //     $("#dob").datepicker("option", "changeMonth", "true");
        //     $("#dob").datepicker("option", "changeYear", "true");

        // });


 /*       $('#dob').change(function() {

            if ($('#title').val() != "Baby" & $('#nic').val() != "") {
                var nic = $('#nic').val();
                var dob = $('#dob').val();
                if (nic[0] != dob[2] | nic[1] != dob[3])
                {
                    alert("NIC doesn't match the value given for Date of Birth");
                    $('#dob').val('');
                }
            }
        });

        $('#nic').change(function() {

            if ($('#title').val() != "Baby" & $('#dob').val() != "") {
                var nic = $('#nic').val();
                var dob = $('#dob').val();
                if (nic[0] != dob[2] | nic[1] != dob[3])
                {
                    alert("NIC doesn't match the value given for Date of Birth");
                    $('#nic').val('');
                }
            }
        });*/

<?php
if (preg_match ( '/Edit/', $title )) {
	
	/*echo "
                        var date=$.datepicker.parseDate('yy-mm-dd', '" . date ( 'y-m-d', $pprofile->patientDateOfBirth / 1000 ) . "');
                        $( '#dob' ).datepicker();
                        $( '#dob' ).datepicker( 'option', 'dateFormat', 'y-m-d' );
                        $( '#dob' ).datepicker( 'option', 'changeMonth', 'true' );
                        $( '#dob' ).datepicker( 'option', 'changeYear','true' );
                        $('#dob').datepicker('setDate', date);";*/
	
	if ($pprofile->patientPhoto != NULL & $pprofile->patientPhoto != "null") {
		echo "
                                        $('#nic').removeAttr('required');
                                        $('#passport').removeAttr('required');
                                        $('#hin').removeAttr('required');
                                        $('#photo').removeAttr('required');
                                ";
	}
}
?>

<?php
if (preg_match ( '/Edit/', $title ) && ($pprofile->patientTitle == 'Baby' | $pprofile->patientTitle == 'Rev')) {
	echo "makeform('" . $pprofile->patientTitle . "')";
}

if (preg_match ( '/Edit/', $title ) && $pprofile->patientActive == '0') {
	echo "$('#myform :input:not(#active):not(.btn-primary)').attr('disabled', true); ";
}
?>


<?php
if (! preg_match ( '/Edit/', $title )) {
	if ($status != null && $status != '0' && $status != "False") {
		$pid = $status;
		echo "window.open('../../index.php/print_c/print_patient_card/" . $pid . "','patientSlip','width=490,height=250');";
		//echo "window.location = '../operator_home_c/viewpatient/" . $pid . "';";
        echo "window.location = '../patient_overview_c/view/" . $pid . "';";
	}
}
?>


    });

</script>
<!-- <div class="modal-example">
<section class="content-header">
	<h1>
		Patient <small> <?php echo $title; ?></small>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		<li><a href="#"><i class="fa fa-wheelchair"></i> Patient</a></li>
		<li class="active"><?php echo $title; ?>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
	</ol>
</section> -->
 	<div class="row">
<?php if($title=="Edit Patient"){?>

		<div class="col-md-6 col-md-offset-3">

			<div class="container">

				<div class="row">
					<div class="col-md-8 col-lg-6">

						<!-- /.box-header -->
						<div class="panel panel-primary" style="margin-top: 8px;">
            <div class="panel-heading">
              <h3 class="panel-title">Patient Information</h3>
            </div>
            <div class="panel-body">
							<div class="box-header with-border">
								<i class="fa  fa-wheelchair"></i>
								<h3 class="box-title">Patient Profile</h3>
							</div>
							<hr>
							<div class="col-sm-12">
								<div class="col-xs-12 col-sm-8">
									<h2><?php
                                    	if ($pprofile->patientTitle == "Baby")
                                    		echo "$pprofile->patientTitle $pprofile->patientFullName";
                                    	else
                                    		echo "$pprofile->patientTitle $pprofile->patientFullName";
                                    	?> </h2>
									<table>
										<tr>
											<td style="padding-right: 40px"><strong>HIN</strong></td>
											<td style="padding-left: 10px">
                    <?php echo "$pprofile->patientHIN"; ?></td>
										</tr>

										<tr>
											<td><strong>Gender</strong></td>
											<td style="padding-left: 10px">
                    <?php echo "$pprofile->patientGender"; ?></td>
										</tr>
										<tr>
											<td style="width: 90px"><strong>Civil Status</strong></td>
											<td style="padding-left: 10px">
				<?php echo "$pprofile->patientCivilStatus";?></td>

										</tr>
										<tr>
											<td style="width: 50px"><strong>Date of Birth</strong></td>
											<td style="padding-left: 10px">   
				<?php date_default_timezone_set('Asia/Colombo');
                echo date("Y-m-d",$pprofile->patientDateOfBirth/1000);  ?></td>
										</tr>

									</table>
								</div>
							</div>
							<div class="row">
								<div class="pull-right col-xs-12 col-sm-4 emphasis"></div>
							</div>
						</div>
					</div>
				</div>
				<!-- /.box -->
			</div>
			<!-- ./col -->
		</div>

	</div>
	<?php }?>


			

			
		<!-- left column -->
		<div class=" col-md-12 ">
     
 <div class = 'modal-example'>
  <div class="panel panel-info">
			<div class="panel-heading">
			
					<h3 class="panel-title"><b><?php echo $title; ?></b></h3>
				</div>
				 <div class="panel-body">
				
<?php
if (preg_match ( '/Edit/', $title )) {
	
	echo form_open_multipart ( 'patient_c/update/' . $pid, array (
			'name' => 'myform',
			'id' => 'myform' 
	) );
} else {
	echo form_open_multipart ( 'patient_c/save', array (
			'name' => 'myform',
			'id' => 'myform' 
	) );
}
?>




				<!-- Message for operation status  ************************************************************** --> 
        <?php
			if ($status != '0') {
				echo "<div class='row'>	<div class='container'>	<div class='col-xs-12'>";
				if (! preg_match ( '/Edit/', $title ) & $status == "True") {
					?>
                    <div class="alert alert-success"
					style="margin-right: 5%">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>Successfull ! Patient added successfully</strong>

				</div>
                <?php } else if (preg_match('/Edit/', $title) & $status == "True") { ?>

                    <div class="alert alert-success"
					style="margin-right: 5%">
					<strong>Successfull ! </strong> Patient updated Successfully
					<button type="button" class="close" data-dismiss="alert">&times;</button>

				</div>

    <?php } ?>


                <?php if ((!preg_match('/Edit/', $title)) & $status == "False") { ?>

                    <div class="alert alert-danger"
					style="height: 50px; margin-right: 5%">
					<strong>Fail !</strong> Failed to add the patient
					<button type="button" class="close" data-dismiss="alert">&times;</button>

				</div>


    <?php } else if (preg_match('/Edit/', $title) & $status == "False") { ?>

            <div class="alert alert-danger"
					style="height: 50px; margin-right: 5%">
					<strong>Fail !</strong> Faild to update the patient
					<button type="button" class="close" data-dismiss="alert">&times;</button>

				</div>
    <?php } ?>
<?php echo "</div></div></div>";} ?>
            <!-- **************************************************************************************** --> 		
            <?php if (preg_match('/Edit/', $title) && $pprofile->patientActive == '0') { ?>
            <div class='row'>
					<div class='modal-example'>
						<div class='col-xs-12'>
							<div class="alert alert-danger">
								The record <strong> is not active </strong>
							</div>
						</div>
					</div>
				</div>
<?php } ?>


				<div class="modal-example">
					<div class="box-header with-border">
						<h3 class="box-title"><b>Patient Personal Details</b></h3>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group">

								<span class="input-group-addon" >Title <span style="color:red">*</span></span> <select
									class="form-control" id="title" name="title"
									required="required">
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientTitle == 'Mr.') {
											echo 'selected';
										}
										?>
										value="Mr.">Mr.</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientTitle == 'Miss.') {
											echo 'selected';
										}
										?>
										value="Miss.">Miss.</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientTitle == 'Mrs.') {
											echo 'selected';
										}
										?>
										value="Mrs.">Mrs.</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientTitle == 'Rev.') {
											echo 'selected';
										}
										?>
										value="Rev">Rev</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientTitle == 'Baby') {
											echo 'selected';
										}
										?>
										value="Baby">Baby</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientTitle == 'Unknown') {
											echo 'selected';
										}
										?>
										value="Unknown">Unknown</option>
								</select>


							</div>


						</div>
						<div class="col-xs-4" >
							<div class="input-group"  >
								<span class="input-group-addon" >Name In Full <span style="color:red">*</span></span> <input
									type="text" class="form-control" pattern="[A-Za-z\s]*"
									id="fullname" name="fullname" required="required"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientFullName;
									}
									?>" />
							</div>

						</div>

						<div class="col-xs-4">
							<div class="input-group">
								<span class="input-group-addon">Other Names </span> <input
									type="text" class="form-control" pattern="[A-Za-z]+"
									id="personalname" name="personalname"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientPersonalUsedName;
									}
									?>" />
							</div>

						</div>
					</div>
					<br>
									<div class="row">

					
						<div class="col-xs-3">
							<div class="input-group, input-group date" data-provide="datepicker" data-date-format="yyyy-mm-dd" >
							
								<span class="input-group-addon">Date Of Birth <span style="color:red ">*</span></span> 
					
								<input class="form-control"  type="text" id="dob" name="dob" required="required" 
                                value="<?php
                                    
                                    date_default_timezone_set('Asia/Colombo');
                                    if (preg_match ( '/Edit/', $title )) {
                                        echo date("Y-m-d", $pprofile->patientDateOfBirth/1000);
                                    }
                                    ?>"/>
								
									
							</div>
						</div>

						<div class="col-xs-2">
							<div class="input-group">
								<span class="input-group-addon" >Gender <span style="color:red">*</span></span> <select
									class="form-control" id="gender" name="gender"
									required="required">
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientGender == 'Male') {
											echo 'selected';
										}
										?>
										value="Male">Male</option>
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientGender == 'Female') {
											echo 'selected';
										}
										?>
										value="Female">Female</option>
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientGender == 'Other') {
											echo 'selected';
										}
										?>
										value="Other">Other</option>
								</select>
							</div>

						</div>
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Civil Status <span style="color:red">*</span></span> <select
									class="form-control " id="cstatus" name="cstatus"
									required="required">
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientCivilStatus == 'Single') {
											echo 'selected';
										}
										?>
										value="Single">Single</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientCivilStatus == 'Married') {
											echo 'selected';
										}
										?>
										value="Married">Married</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientCivilStatus == 'Divorced') {
											echo 'selected';
										}
										?>
										value="Divorced">Divorced</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientCivilStatus == 'Widow') {
											echo 'selected';
										}
										?>
										value="Widow">Widow</option>
									<option
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientCivilStatus == 'Unknown') {
											echo 'selected';
										}
										?>
										value="UnKnown">Unknown</option>
								</select>
							</div>

						</div>
					</div>
					 <br>
					<div class="box-header with-border">
						<h3 class="box-title">Patient Identity Details</h3>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >NIC <span style="color:red">*</span></span><input type="text"
									class="form-control" required="required" id="nic" name="nic"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientNIC;
									}
									?>"
									pattern="[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][vV]" />
							</div>

						</div>
						<div class="col-xs-3">
							<div class="input-group">
								<span class="input-group-addon">Passport</span><input
									type="text" class="form-control" id="passport" name="passport" />
							</div>

						</div>

						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Citizenship <span style="color:red">*</span></span><input
									type="text" class="form-control" pattern="[A-Z a-z]+"
									id="citizen" name="citizen"
									value=" <?php
								
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientCitizenship;

									}
									?>">
							</div>

						</div>

                        
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Blood Group <span style="color:red">*</span></span><select
									class="form-control" id="blood" name="blood">
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientblood == 'A+') {
											echo 'selected';
										}
										?>
										value="A+">A+</option>
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientblood == 'A-') {
											echo 'selected';
										}
										?>
										value="A-">A-</option>
										<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientblood == 'B+') {
											echo 'selected';
										}
										?>
										value="B+">B+</option>
										<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientblood == 'B-') {
											echo 'selected';
										}
										?>
										value="B-">B-</option>
										<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientblood == 'AB') {
											echo 'selected';
										}
										?>
										value="AB">AB</option>
										<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientblood == 'O+') {
											echo 'selected';
										}
										?>
										value="O+">O+</option>
										<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientblood == 'O-') {
											echo 'selected';
										}
										?>
										value="O-">O-</option>
									
								</select>
							</div>

						</div>
                        
                        <br>
                        <br>
                        <br>

						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Preffered Language <span style="color:red">*</span></span> <select
									class="form-control" id="lang" name="lang" required="required">
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientPreferredLanguage == 'Sinhala') {
											echo 'selected';
										}
										?>
										value="Sinhala">Sinhala</option>
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientPreferredLanguage == 'Tamil') {
											echo 'selected';
										}
										?>
										value="Tamil">Tamil</option>
									<option class=""
										<?php
										
										if (preg_match ( '/Edit/', $title ) && $pprofile->patientPreferredLanguage == 'English') {
											echo 'selected';
										}
										?>
										value="English">English</option>
								</select>
							</div>
							
						</div>
						<div class="col-xs-3">

							<span>Guardian Required </span><input type="checkbox" id="checkboxGuardian" checked>
						</div>
					</div>
					 <br>
					<div class="box-header with-border">
						<h3 class="box-title"><b>Patient Contact Details</b></h3>
					</div>
					<h4 class="box-title">Address Details</h4>	
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Address 1<span style="color:red">*</span></span><input
									class="form-control" type="text" id="address1" name="address1"
									required="required"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientAddress1;
									}
									?>" />
							</div>

						</div>
	
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Address 2 </span><input
									class="form-control" type="text" id="address2" name="address2"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientAddress2;
									}
									?>" />
							</div>

						</div>
					
					
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Address 3 </span><input
									class="form-control" type="text" id="address3" name="address3"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientAddress3;
									}
									?>" />
							</div>

						</div>
					
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >City <span style="color:red">*</span></span><input
									class="form-control" id="city" name="city"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientCity;
									}
									?>" />
							</div>
						</div>
					
					</div>
				</div>
				<br/>
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group" >
							<span class="input-group-addon" >Postal Code</span><input
								class="form-control" id="postalCode" name="postalCode"
								value="<?php
								
								if (preg_match ( '/Edit/', $title )) {
									echo $pprofile->patientPostalCode;
								}
								?>" />
						</div>

					</div>
				</div>
				<br/>
				<h4 class="box-title">Other Details</h4>	
				<div class="row">
					<div class="col-xs-3">
						<div class="input-group" >
							<span class="input-group-addon">Phone <span style="color:red">*</span></span><input type="text"
								class="form-control" id="telephone" pattern="\d{10}"
								name="telephone"
								value="<?php
								
								if (preg_match ( '/Edit/', $title )) {
									echo $pprofile->patientTelephone;
								}
								?>">
						</div>

					</div>
				</div>

					<!-- <div class="row">
						<div class="col-xs-4">
							<div class="input-group">
								<span class="input-group-addon">Guardian</span><input
									type="text" class="form-control" pattern="[A-Z a-z]+"
									id="contactpname" name="contactpname"
									value=" <?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientContactPName;
									}
									?>" />
							</div>

						</div>

						<div class="col-xs-4">
							<div class="input-group">
								<span class="input-group-addon">Guardian Tel</span><input
									type="text" class="form-control" id="contactpno"
									name="contactpno"
									value=" <?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientContactPNo;
									}
									?>" />
							</div>

						</div> -->

				
					<br> 

					<div class="box-header with-border">
						<h3 class="box-title"><b>Emergency Contact Details</b></h3>
					</div>

					<br/>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >First Name</span></span><input
									class="form-control" id="emergencyfirstname" name="emergencyfirstname"
									required="required"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergnecyFname;
									}
									?>" />
							</div>

						</div>
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon">Last Name</span></span><input type="text"
									class="form-control" id="emergencylastname" name="emergencylastname"
									required="required"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyLname;
									}
									?>">
							</div>

						</div>
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Mobile</span><input
									class="form-control" id="emergencyMobile" name="emergencyMobile" pattern="\d{10}" 
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyMobile;
									}
									?>" />
							</div>

						</div>
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Telephone</span><input
									class="form-control" id="emergencyTelephone" name="emergencyTelephone" pattern="\d{10}" 
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyTelepone;
									}
									?>" />
							</div>

						</div>
					</div>
					<br>
					<h4 class="box-title">Address Details</h4>	
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Address 1</span></span><input
									class="form-control" id="emergencyaddress1" name="emergencyaddress1"
									required="required"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyAddress1;
									}
									?>" />
							</div>

						</div>

						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Address 2 </span><input
									class="form-control" id="emergencyaddress2" name="emergencyaddress2"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyAddress2;
									}
									?>" />
							</div>

						</div>

						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Address 3 </span><input
									class="form-control" id="emergencyaddress3" name="emergencyaddress3"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyAddress3;
									}
									?>" />
							</div>

						</div>

						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >City <span style="color:red">*</span></span><input
									class="form-control" id="emergencycity" name="emergencycity"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyCity;
									}
									?>" />
							</div>

						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group" >
								<span class="input-group-addon" >Postal Code</span><input
									class="form-control" id="emergencypostalCode" name="emergencypostalCode"
									value="<?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->emergencyPostalCode;
									}
									?>" />
							</div>

						</div>
					</div>
					<br/>

					<div class="box-header with-border">
						<h3 class="box-title"><b>Parent/Guardian Information</b></h3>
					</div>
					<br/>
					<div class="input-group">
								<button type="button" id="btnAddGuardian" class="btn btn-primary" data-toggle="modal" data-target="#GuardianModal">Add Guardians
								</button>
					</div>
					<br/>
					<div class="row">
                        <?php
                                    
                                    if (preg_match ( '/Edit/', $title )) {

                                        if(empty($pprofile->guardians))
                                        {

                                            echo "<div class='col-xs-12' id='guardianList' hidden>";
                                        }
                                        else
                                        {
                                            echo "<div class='col-xs-12' id='guardianList' >";
                                        }
                                    }
                                    else
                                    {
                                        echo "<div class='col-xs-12' id='guardianList' hidden>";
                                    }
                        ?>
						
							<div class="box">
								<div class="box-body">
								
									<input class="form-control" id="gtablerows" name="gtablerows" type="hidden"  value="0" />
									<table class="table table-bordered table-striped table-hover"
										id="tableGuardians">
										<br>
										<thead>
											<tr>
                                                <th>Select</th>
												<th>NIC</th>
												<th>First Name</th>
												<th>Last Name</th>
												<th>Gender</th>
												<th>Relationship</th>
                                                <th>Address 1</th>
                                                <th>Address 2</th>
                                                <th>Address 3</th>
                                                <th>City</th>
                                                <th>Postal Code</th>
                                                <th>Mobile</th>
                                                <th>Telephone</th>
												
											</tr>

										</thead>
										<tbody>
				                             <?php if (preg_match ( '/Edit/', $title )) {
                                                        foreach($pprofile->guardians as $row){ ?>
                                                <tr>
                                                    <td><input type="checkbox" name="record"></button></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianNIC[]" value="<?php echo $row->guardianNIC ?>" /><?php echo $row->guardianNIC ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianFirstname[]" value="<?php echo $row->firstName ?>" /><?php echo $row->firstName ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianLastname[]" value="<?php echo $row->lastName ?>" /><?php echo $row->lastName ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianGender[]" value="<?php echo $row->gender ?>" /><?php echo $row->gender ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianRelationship[]" value="<?php echo $row->relationship ?>" /><?php echo $row->relationship ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianAddress1[]" value="<?php echo $row->address1 ?>" /><?php echo $row->address1 ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianAddress2[]" value="<?php echo $row->address2 ?>" /><?php echo $row->address2 ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianAddress3[]" value="<?php echo $row->address3 ?>" /><?php echo $row->address3 ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianCity[]" value="<?php echo $row->city ?>" /><?php echo $row->city ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianPostalCode[]" value="<?php echo $row->postalcode ?>" /><?php echo $row->postalcode ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianMobile[]" value="<?php echo $row->mobile ?>" /><?php echo $row->mobile ?></td>
                                                    <td><input class="form-control" type="hidden" name="tableguardianTelephone[]" value="<?php echo $row->telephone ?>" /><?php echo $row->telephone ?></td>

                                                <?php } }  ?>
				    					</tbody>

									</table>
								</div>
							</div>
							<br/>
							<div class="input-group">
								<button type="button" id="btnDeleteGuardian" class="btn btn-primary">Delete Guardians
								</button>
							</div>
						</div>
					</div>

					<div id="GuardianModal" class="modal fade" role="dialog">
  						<div class="modal-dialog modal-lg">

    					<!-- Modal content-->
		    				<div class="modal-content">
		      					<div class="modal-header">
		        					<h4 class="modal-title">Parent/Guardian Information</h4>
		      					</div>
		      					<div class="modal-body">
		        					<div class="row">
			        					<div class="col-xs-6">
											<div class="input-group" >
												<span class="input-group-addon" >NIC <span style="color:red">*</span></span><input
													class="form-control" id="guardianNIC" name="guardianNIC"
													value="" pattern="[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][vV]" />
											</div>

										</div>
										<div class="col-xs-3">
											<button type="button" id="btnGuardianSearch" class="btn btn-primary">Search</button>
										</div>
									</div> 
									<br/>
									<div class="row">
										<div class="col-xs-6">
											<div class="input-group" >
												<span class="input-group-addon" >First Name <span style="color:red">*</span></span><input
													class="form-control" id="guardianFirstname" name="guardianFirstname"
													value="" />
											</div>

										</div>
										<div class="col-xs-6">
											<div class="input-group" >
												<span class="input-group-addon">Last Name <span style="color:red">*</span></span><input type="text"
													class="form-control" id="guardianLastname" name="guardianLastname"
													value="">
											</div>

										</div>
									</div>
									<br/>
									<div class="row">
			        					<div class="col-xs-6">
											<div class="input-group" >
												<span class="input-group-addon" >Gender <span style="color:red">*</span></span> <select
													class="form-control" id="guardianGender" name="guardianGender"
													required="required">
													<option class="" value="Male">Male</option>
													<option class="" value="Female">Female</option>
												</select>
											</div>

										</div>
										<div class="col-xs-6">
											<div class="input-group" >
												<span class="input-group-addon" >Relationship <span style="color:red">*</span></span> <select
													class="form-control" id="guardianRelationship" name="guardianRelationship"
													required="required">
													<option class="" value="Father">Father</option>
													<option class="" value="Mother">Mother</option>
													<option class="" value="Uncle">Uncle</option>
													<option class="" value="Aunt">Aunt</option>
													<option class="" value="Other">Other</option>
												</select>
											</div>

										</div>
									</div>
                                    <h4 class="box-title">Address Details</h4>  
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="input-group" >
                                                    <span class="input-group-addon" >Address 1<span style="color:red">*</span></span><input
                                                        class="form-control" id="guardianAddress1" name="guardianAddress1"
                                                        value="" />
                                                </div>

                                            </div>

                                            <div class="col-xs-4">
                                                <div class="input-group" >
                                                    <span class="input-group-addon" >Address 2 </span><input
                                                        class="form-control" id="guardianAddress2" name="guardianAddress2"
                                                        value="" />
                                                </div>

                                            </div>

                                            <div class="col-xs-4">
                                                <div class="input-group" >
                                                    <span class="input-group-addon" >Address 3 </span><input
                                                        class="form-control" id="guardianAddress3" name="guardianAddress3"
                                                        value="" />
                                                </div>

                                            </div>
                                        </div>
                                        <br/>
                                        <div class="row">
                                            
                                           
                                            <div class="col-xs-4">
                                                <div class="input-group" >
                                                    <span class="input-group-addon" >City</span></span><input
                                                        class="form-control" id="guardianCity" name="guardianCity"
                                                        value="" />
                                                </div>

                                            </div>
                                            <div class="col-xs-4">
                                                <div class="input-group" >
                                                    <span class="input-group-addon" >Postal Code</span></span><input
                                                        class="form-control" id="guardianPostalCode" name="guardianPostalCode"
                                                        value="" />
                                                </div>
                                             </div>
                                        </div>
                                        <br>
                                        <h4 class="box-title">Other Details</h4>
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <div class="input-group" >
                                                    <span class="input-group-addon" >Mobile</span><input
                                                        class="form-control" id="guardianMobile" name="guardianMobile" pattern="\d{10}" 
                                                        value="" />
                                                </div>

                                            </div>
                                            <div class="col-xs-3">
                                                <div class="input-group" >
                                                    <span class="input-group-addon" >Telephone</span><input
                                                        class="form-control" id="guardianTelephone" name="guardianTelephone" pattern="\d{10}" 
                                                        value="" />
                                                </div>

                                            </div>
                                        </div>
									<br/>
									<div class="row">
										<div class="col-xs-3">
											<button type="button" id="btnGuardianAdd" class="btn btn-primary">Add</button>
										</div>
									</div>
									<br/>

		      					</div>
		      					<div class="modal-footer">
		        					<button type="button" id='btnCloseGuardian' class="btn btn-default" data-dismiss="modal">Close</button>
		      					</div>
		    				</div>

  							</div>
  						</div>

  					
					<br>

					<div class="box-header with-border">
						<h3 class="box-title"><b>Patient Other Details</b></h3>
					</div>
					<br>
					<div class="row">
						<div class="col-xs-4">
							<div class="input-group">
								<span class="input-group-addon">Remarks</span>
								<textarea class="form-control" id="remarks" name="remarks"
									rows="3"><?php
									
									if (preg_match ( '/Edit/', $title )) {
										echo $pprofile->patientRemarks;
									}
									?></textarea>

							</div>

						</div>
						<div class="col-xs-3">
							<div class="input-group">
								<span class="input-group-addon">Photo</span> <?php
								if (preg_match ( '/Edit/', $title ) && ($pprofile->patientPhoto != NULL & $pprofile->patientPhoto != "null")) {
									echo "<a href=" . base_url () . "/uploads/patient_photos/" . basename ( $pprofile->patientPhoto ) . PHP_EOL . "><div name='uplddfnm' id='uplddfnm'  class='form-control'>" . basename ( $pprofile->patientPhoto ) . PHP_EOL . " </div></a>";
								}
								?>
                				<div class="controls">
									<input type="file" class="form-control" id="photo"
										name="userfile" value="">
								</div>

							</div>
						</div>


					</div>


					<br>
					<br>
					
					<?php if (preg_match('/Edit/', $title)) { ?> 
					<div class="row">

						<div class="col-xs-3">
							<div class="input-group">
								<span class="input-group-addon">Active</span> <select
									id="active" name="active" class="form-control"
									style="width: 80px">
									<option
										<?php
						
						if ($pprofile->patientActive == '1') {
							echo 'selected';
						}
						?>
										value="1">Yes</option>
									<option
										<?php
						
						if ($pprofile->patientActive == '0') {
							echo 'selected';
						}
						?>
										value="0">No</option>
								</select>

							</div>

						</div>


					</div>
					<br>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group">
								<label class="lastmod">Last edit by <?php echo $pprofile->patientLastUpdateUser->userName . " on " . date('Y-m-d h:i:s A', $pprofile->patientLastUpdate / 1000); ?></label>
							</div>
						</div>
					</div>
					<?php } ?>	
					<br> <br>
					<div class="row">
						<div class="col-xs-3">
							<div class="input-group">
								<button type="submit" id="btnsubmit" class="btn btn-primary">   <?php
								if (preg_match ( '/Edit/', $title )) {
									echo "Update";
								} else {
									echo "Save";
								}
								?> </button>
					
					
					<!-- /.box-body -->
		
					<?php echo form_close(); ?>
			

</div>
</div>
</div>
</div>
</div>


