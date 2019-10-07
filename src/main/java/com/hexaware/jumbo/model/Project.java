package com.hexaware.jumbo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * project POJO.
 */
@Entity
@Table (name = "PROJECT")
public class Project {
    /**
     * Objectmapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @Column (name = "PRJ_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "PRJ_NAME")
    private String prjName;
    @Column (name = "CREATED_BY")
    private String createdBy;
    @Column (name = "CREATED_DATE")
    private Date createdDate;
    /**
     * Project.
     */
    public Project() {
    }
    /**
     * @param paramId paramId.
     * @param paramPrjName paramPrjName.
     * @param paramCreatedBy paramCreatedBy.
     * @param paramCreatedDate paramCreatedDate.
     */
    public Project(final int paramId, final String paramPrjName, final String paramCreatedBy,
        final Date paramCreatedDate) {
        this.setId(paramId);
        this.setPrjName(paramPrjName);
        this.setCreatedBy(paramCreatedBy);
        this.setCreatedDate(paramCreatedDate);
    }
    /**
     * @return id.
     */
    public int getId() {
        return this.id;
    }
    /**
     * @param paramId paramId.
     */
    public void setId(final int paramId) {
        this.id = paramId;
    }
    /**
     * @return prjName
     */
    public String getPrjName() {
        return this.prjName;
    }
    /**
     * @param paramPrjName paramPrjName
     */
    public void setPrjName(final String paramPrjName) {
        this.prjName = paramPrjName;
    }
    /**
     * @return createdBY.
     */
    public String getCreatedBy() {
        return this.createdBy;
    }
    /**
     * @param paramCreatedBy paramCreatedBy.
     */
    public void setCreatedBy(final String paramCreatedBy) {
        this.createdBy = paramCreatedBy;
    }
    /**
     * @return createdDate.
     */
    public Date getCreatedDate() {
        if (this.createdDate != null) {
            return (Date) this.createdDate.clone();
        } else {
            return null;
        }
    }
    /**
     * @param paramCreatedDate paramCreatedDate.
     */
    public void setCreatedDate(final Date paramCreatedDate) {
        if (paramCreatedDate != null) {
            this.createdDate = (Date) paramCreatedDate.clone();
        } else {
            this.createdDate = null;
        }
    }
    /**
     * @return objectMapper.
     */
    public final String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException j) {
            throw new RuntimeException("Error jsonifying Project Object", j);
        }
    }
}
