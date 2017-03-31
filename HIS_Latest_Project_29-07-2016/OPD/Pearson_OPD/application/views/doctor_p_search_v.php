<section class="content-header">
    <h1>
        Patient Records
      
    </h1>
    
</section>
</br>

<section class="content">
    <!-- Small boxes (Stat box) -->
    <div class="row">
        <div class="col-md-12" >
            <div class="box box-solid" style="box-shadow:0px 0px 2px rgba(0, 0, 0, 0.2)">
                <div class="box-header with-border">
                    <i class="fa fa-bookmark"></i>
                    <h3 class="box-title">Search/Register Patient</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                    <div class="col-xs-3">
                        <select class="form-control " id="searchType" name="searchType">
                            <option value="HIN">HIN</option>
                            <option value="HIN">NIC</option>
                            <option value="HIN">Name</option>
                        </select>
                    </div>
                     <div class="col-xs-3">
                        <input type="text" class="form-control" id="txtSearch" name="text search" placeholder="Search" />
                    </div>
                    <div class="col-xs-3">
                        <button type="button" id="btnPatientSearch" class="btn btn-primary">Search</button>
                    </div>
                    <div class="col-xs-3">
                        <button type="button" id="btnPatientCreate" class="btn btn-primary" onclick="location.href = '<?php echo site_url("/patient_c/add"); ?>'">Register New Patient
                        </button>
                    </div>
                </div><!-- /.box-body -->
            </div><!-- /.box -->
        </div><!-- ./col -->


        

    </div>




    <div class="row">
        <div class="box-body">
            <div class="box-header with-border bg-aqua">
                <i class="fa fa-medkit"></i>
                <h3 class="box-title">Patient Details</h3>
            </div><!-- /.box-header -->

            <br>

            <div class="row">
                <div class="col-xs-12" id="patientDetailsList" hidden>
                    <div class="box">
                        <div class="box-body">
                            <table class="table table-bordered table-striped table-hover"
                                id="tabletestp">
                                <br>
                                <thead>
                                    <tr>
                                        <th width="25px">#</th>
                                        <th>HIN</th>
                                        <th>Name</th>
                                        <th>Visit Date</th>
                                        <th>Complaint</th>
                                        <th>Details</th>
                                    </tr>

                                </thead>
                                <tbody>
                                    <?php $i=0; foreach($patients as $row){ ?>
                                    <tr
                                        onClick=<?php $i++; echo "window.location='".base_url()."index.php/patient_visit_c/view/".$row->patient->patientID."/".$row->visitID."'"; ?>>
                                        <td><?php echo $i; ?></td>
                                        <td><?php echo $row->patient->patientHIN; ?></td>
                                        <td><?php   if($row->patient->patientTitle=="Baby" | $row->patient->patientTitle=="Rev") echo  $row->patient->patientTitle." ".$row->patient->patientFullName;else echo $row->patient->patientTitle.$row->patient->patientFullName; ?></td>
                                        <td><?php echo date('Y-m-d H:i:s a',$row->visitDate/1000);  ?></td>
                                        <td><?php echo  $row->visitComplaint ;  ?></td>

                                        <td><?php echo (date('Y') - date('Y',strtotime($row->patient->patientDateOfBirth)))."Yrs / ". $row->patient->patientGender." / ".$row->patient->patientCivilStatus." / ".$row->patient->patientAddress ?></td>
                                    </tr>
                                    <?php }  ?>
                                </tbody>

                            </table>
                        </div>
                    </div>
                    <br/>
                    <div class="col-xs-3">
                        <button type="button" id="btnPatientCreate" class="btn btn-primary" >Print</button>
                    </div>
                </div>
            </div>


    <br>

        </div>
    </div><!-- /.row -->
    <!-- Main row -->

</section>

<br>        

<script src="<?= base_url('/Bootstrap/js/jquery-1.9.1.min.js'); ?>"></script>
<script>

    $(document).ready(function() {
        $('#btnPatientSearch').click(function(){

            $('#patientDetailsList').prop('hidden', false);

            //ajax method for search and filling the table
            
        });


    });
    
</script>






