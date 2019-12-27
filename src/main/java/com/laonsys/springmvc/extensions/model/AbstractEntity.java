package com.laonsys.springmvc.extensions.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * {@link Entity}  에 대한 추상 클래스
 * @author  kaldosa
 * @param  < T  >  entity 제너릭 타입
 */
@XmlAccessorType(XmlAccessType.NONE)
@ToString(exclude={"createdDate", "modifiedDate"})
public abstract class AbstractEntity<T extends Entity<T>> implements Entity<T> {

    @XmlAttribute
    @Getter @Setter
    private int id;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @Getter @Setter
    private Date createdDate;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @Getter @Setter
    private Date modifiedDate;

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        else {
            T that = (T) obj;
            Integer thisId = this.getId();
            Integer thatId = that.getId();
            if (thisId == null || thatId == null) {
                return super.equals(that);
            }
            else {
                return thatId.equals(thisId);
            }
        }
    }

    @Override
    public int hashCode() {
        Integer id = getId();
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public int compareTo(T that) {
        Integer thisId = this.getId();
        Integer thatId = that.getId();

        if (thisId == null) {
            return (thatId == null) ? 0 : 1;
        }
        else if (thatId == null) {
            return -1;
        }
        else {
            return thisId.compareTo(thatId);
        }
    };
}
