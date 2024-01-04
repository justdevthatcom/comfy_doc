package ru.gmdev.comfy_doc.core;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public abstract class Document implements DocumentIntf {
    public static final String TYPE_NAME = "DOCUMENT";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_SEQ")
    @SequenceGenerator(name = "APPLICATION_SEQ", sequenceName = "APPLICATION_SEQ", allocationSize = 1)
    @Column(name = "ID")
    Long id;

    @Nullable
    private Timestamp changeDate;
    @Nullable
    private Timestamp createDate;
    @Nullable
    private Date docDate;
    @Nullable
    private String description;
    @NotNull
    private Integer docstatusid;
    @Nullable
    private String docstatusCaption;
    @Nullable
    private Integer documentclassid;
    @Nullable
    private String documentclassCaption;
    @Nullable
    private String docInfo;
    @Nullable
    private String docNumber;
    @Nullable
    private String metaobjectname;
    @Nullable
    private String metaobjectCaption;
    /** ФИО автора */
    @Nullable
    private String suserFio;
    /** ФИО изменившего */
    @Nullable
    private String suserFioChange;
    @Nullable
    private Long suserId;
    @Nullable
    private Long suserIdChange;

}
