package core.classes.pcu;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import core.classes.api.user.AdminUser;
import core.classes.opd.OutPatient;

/**
 * PcuAdmition generated by hbm2java
 */
public class PcuAdmition implements java.io.Serializable {

	private Integer admitionId;
	private OutPatient patientId;
	private Date admitionDate;
	private String status;
	private AdminUser createdBy;
	private Date createdTime;
	private AdminUser modifiedBy;
	private Date modifiedTime;

	public PcuAdmition() {
	}

	public PcuAdmition(OutPatient patientId, Date admitionDate, String status,
			AdminUser createdBy, Date createdTime, AdminUser modifiedBy, Date modifiedTime) {
		this.patientId = patientId;
		this.admitionDate = admitionDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modifiedBy = modifiedBy;
		this.modifiedTime = modifiedTime;
	}

	public Integer getAdmitionId() {
		return this.admitionId;
	}

	public void setAdmitionId(Integer admitionId) {
		this.admitionId = admitionId;
	}

	public OutPatient getPatientId() {
		return this.patientId;
	}

	public void setPatientId(OutPatient patientId) {
		this.patientId = patientId;
	}

	public Date getAdmitionDate() {
		return this.admitionDate;
	}

	public void setAdmitionDate(Date admitionDate) {
		this.admitionDate = admitionDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AdminUser getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(AdminUser createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public AdminUser getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(AdminUser modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}