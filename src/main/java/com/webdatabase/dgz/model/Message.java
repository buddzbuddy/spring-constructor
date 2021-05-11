package com.webdatabase.dgz.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.webdatabase.dgz.model.base.AuditModel;

@Entity
@Table(name = "messages")
public class Message extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "content")
    private String content;
    @Column(name = "visible")
    private boolean visible = false;
    @Column(name = "make_visible_at")
    private long makeVisibleAt;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public long getMakeVisibleAt() {
        return makeVisibleAt;
    }

    public void setMakeVisibleAt(long makeVisibleAt) {
        this.makeVisibleAt = makeVisibleAt;
    }

}