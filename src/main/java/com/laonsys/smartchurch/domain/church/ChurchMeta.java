package com.laonsys.smartchurch.domain.church;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.laonsys.springmvc.extensions.model.AbstractEntity;
import com.laonsys.springmvc.extensions.validation.constraints.Contact;
import com.laonsys.springmvc.extensions.validation.constraints.Kor;
import com.laonsys.springmvc.extensions.validation.groups.Create;
import com.laonsys.springmvc.extensions.validation.groups.Update;

public class ChurchMeta extends AbstractEntity<ChurchMeta> {

    @Kor(message="교회이름은 한글만 입력해주세요.", groups = {Create.class, Update.class})
    @Length(min=1, max=20, message="교회명은 {min} ~ {max} 글자 안으로 입력해주세요.", groups = {Create.class, Update.class})    
    private String name;

    @Kor(message="담임목사는 한글만 입력해주세요.", groups = {Create.class, Update.class})
    @Length(min=1, max=20, message="담임목사명은 {min} ~ {max} 글자 안으로 입력해주세요.", groups = {Create.class, Update.class})
    private String pastor;

    @NotEmpty(message="교회주소를 입력해주세요.", groups = {Create.class, Update.class})
    private String address;

    @Contact(message="교회연락처 형식이 올바르지 않습니다. (####-####-####)", groups = {Create.class, Update.class})
    private String contact;

    @Length(max = 250, message = "오시는 길 내용은 250이내로 입력하세요.", groups = {Create.class, Update.class})
    private String traffic;

    private int serviceId;
    
    private ServiceStatus serviceStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPastor() {
        return pastor;
    }

    public void setPastor(String pastor) {
        this.pastor = pastor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact.trim();
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public int getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    
    @Override
    public String toString() {
        return "CHURCH ID: [" + getId() + "], name: [" + name + "], pastor: [" + pastor + "], contact: [" + contact + "], address: [" + address + "]";
    }
}
