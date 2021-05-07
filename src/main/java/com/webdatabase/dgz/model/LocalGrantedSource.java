package com.webdatabase.dgz.model;

import com.webdatabase.dgz.model.base.AuditModel;

import javax.persistence.*;

@Entity
@Table(name = "local_granted_sources")
public class LocalGrantedSource extends AuditModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "source_type")
    private SourceType sourceType;

    @Column(name = "supplier_id")
    private long supplierId;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="supplier_id", referencedColumnName="id", insertable=false, updatable=false)
    private Supplier supplier;

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
