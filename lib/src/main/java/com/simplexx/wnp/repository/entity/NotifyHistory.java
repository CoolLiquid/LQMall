package com.simplexx.wnp.repository.entity;



import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;


/**
 * Created by wnp on 2018/12/18.
 */

@Entity(nameInDb = "name_history", createInDb = true)
public class NotifyHistory {
    /*
         entity.addStringProperty("id").primaryKey().notNull();
            entity.addStringProperty("msgId").notNull();
            entity.addLongProperty("uid").notNull();
            entity.addLongProperty("version").notNull();
            entity.addDateProperty("created").notNull();*/
    @Id(autoincrement = false)
    public String id;

    @NotNull
    public String msgId;

    @NotNull
    public Long uid;

    @NotNull
    public Long version;

    @NotNull
    public Date create;

    @Generated(hash = 1806170177)
    public NotifyHistory(String id, @NotNull String msgId, @NotNull Long uid,
                         @NotNull Long version, @NotNull Date create) {
        this.id = id;
        this.msgId = msgId;
        this.uid = uid;
        this.version = version;
        this.create = create;
    }

    @Generated(hash = 718919595)
    public NotifyHistory() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreate() {
        return this.create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }
}
