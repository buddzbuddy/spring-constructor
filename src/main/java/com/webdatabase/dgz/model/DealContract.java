package com.webdatabase.dgz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.webdatabase.dgz.model.base.AuditModel;
import com.webdatabase.dgz.query.utils.IsMetaClass;
import com.webdatabase.dgz.query.utils.MetaFieldName;

@Entity
@Table(name = "deal_contracts")
@IsMetaClass(label = "Опыт договоры")
public class DealContract  extends AuditModel {

	@MetaFieldName(label = "ID")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@MetaFieldName(label = "Поставщик", selectClassName = "Supplier")
	@Column(name = "supplier_id", nullable = true)
	private long supplierId;
	
	@MetaFieldName(label = "Заказчик")
	@Column(name = "customer", nullable = true)
	private String customer;
	
	@MetaFieldName(label = "Предмет договора")
	@Column(name = "deal_subject", nullable = true)
	private String dealSubject;
	
	@MetaFieldName(label = "Сроки исполнения договора")
	@Column(name = "deal_period", nullable = true)
	private String dealPeriod;
	
	@MetaFieldName(label = "Сумма договора")
	@Column(name = "deal_price", nullable = true)
	private float dealPrice;

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getDealSubject() {
		return dealSubject;
	}

	public void setDealSubject(String dealSubject) {
		this.dealSubject = dealSubject;
	}

	public String getDealPeriod() {
		return dealPeriod;
	}

	public void setDealPeriod(String dealPeriod) {
		this.dealPeriod = dealPeriod;
	}

	public float getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(float dealPrice) {
		this.dealPrice = dealPrice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
