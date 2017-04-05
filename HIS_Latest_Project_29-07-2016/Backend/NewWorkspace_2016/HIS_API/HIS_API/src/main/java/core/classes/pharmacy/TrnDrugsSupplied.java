// default package
// Generated Jul 17, 2013 11:31:01 AM by Hibernate Tools 3.4.0.CR1
package core.classes.pharmacy;


import java.util.Date;

/**
 * TrnDrugsSupplied generated by hbm2java
 */
public class TrnDrugsSupplied implements java.io.Serializable {

	private TrnDrugsSuppliedId id;
	private String dName;
	private Integer dQty;
	private String dManufacturer;
	private Date dManufactDate;
	private Date dExpiryDate;
	private Date dCreateDate;
	private int dCreateUser;
	private Date dLastUpdate;
	private int dLastUpdateUser;
	private String batchStatus;

	public TrnDrugsSupplied() {
	}

	public TrnDrugsSupplied(TrnDrugsSuppliedId id, String DName) {
		this.id = id;
		this.dName = DName;
	}

	public TrnDrugsSupplied(TrnDrugsSuppliedId id, String DName, Integer DQty,
			String DManufacturer, Date DManufactDate, Date DExpiryDate,
			Date DCreateDate, int DCreateUser, Date DLastUpdate,
			int DLastUpdateUser) {
		this.id = id;
		this.dName = DName;
		this.dQty = DQty;
		this.dManufacturer = DManufacturer;
		this.dManufactDate = DManufactDate;
		this.dExpiryDate = DExpiryDate;
		this.dCreateDate = DCreateDate;
		this.dCreateUser = DCreateUser;
		this.dLastUpdate = DLastUpdate;
		this.dLastUpdateUser = DLastUpdateUser;
	}

	public TrnDrugsSuppliedId getId() {
		return id;
	}

	public void setId(TrnDrugsSuppliedId id) {
		this.id = id;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public Integer getdQty() {
		return dQty;
	}

	public void setdQty(Integer dQty) {
		this.dQty = dQty;
	}

	public String getdManufacturer() {
		return dManufacturer;
	}

	public void setdManufacturer(String dManufacturer) {
		this.dManufacturer = dManufacturer;
	}

	public Date getdManufactDate() {
		return dManufactDate;
	}

	public void setdManufactDate(Date dManufactDate) {
		this.dManufactDate = dManufactDate;
	}

	public Date getdExpiryDate() {
		return dExpiryDate;
	}

	public void setdExpiryDate(Date dExpiryDate) {
		this.dExpiryDate = dExpiryDate;
	}

	public Date getdCreateDate() {
		return dCreateDate;
	}

	public void setdCreateDate(Date dCreateDate) {
		this.dCreateDate = dCreateDate;
	}

	
	public Date getdLastUpdate() {
		return dLastUpdate;
	}

	public void setdLastUpdate(Date dLastUpdate) {
		this.dLastUpdate = dLastUpdate;
	}

	public int getdCreateUser() {
		return dCreateUser;
	}

	public void setdCreateUser(int dCreateUser) {
		this.dCreateUser = dCreateUser;
	}

	public int getdLastUpdateUser() {
		return dLastUpdateUser;
	}

	public void setdLastUpdateUser(int dLastUpdateUser) {
		this.dLastUpdateUser = dLastUpdateUser;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	
	

	
	
}
