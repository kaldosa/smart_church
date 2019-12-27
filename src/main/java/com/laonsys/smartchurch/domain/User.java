package com.laonsys.smartchurch.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.laonsys.springmvc.extensions.model.Attachment;
import com.laonsys.springmvc.extensions.validation.constraints.Contact;
import com.laonsys.springmvc.extensions.validation.constraints.File;
import com.laonsys.springmvc.extensions.validation.constraints.Password;
import com.laonsys.springmvc.extensions.validation.constraints.ValidEmail;
import com.laonsys.springmvc.extensions.validation.groups.ConfirmMail;
import com.laonsys.springmvc.extensions.validation.groups.FindPw;
import com.laonsys.springmvc.extensions.validation.groups.JoinUser;
import com.laonsys.springmvc.extensions.validation.groups.Update;

@JsonSerialize(using=UserSerializer.class)
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3211705919681319518L;

	private int id;

	@ValidEmail(groups = {JoinUser.class})
	@Email(groups = {ConfirmMail.class, FindPw.class, Update.class})
	private String email;

	@Password(groups = {JoinUser.class, ConfirmMail.class})
	private String password;

	@NotEmpty(groups = {JoinUser.class, FindPw.class}, message = "이름을 입력하세요.")
	@Size(max=20, groups = {JoinUser.class, FindPw.class, Update.class})
	private String name;

	//@MaskFormat("###-####-####")
	@Contact(groups = {JoinUser.class, Update.class})
	private String contact;
	
	private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joinedDate = Calendar.getInstance().getTime();

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date withdrawnDate;
	
	private boolean enabled;

	private Attachment photo;
	
	@JsonIgnore
	@File(contentType = {"image/jpeg", "image/gif", "image/png"}, limit = 1048576)
	private MultipartFile upload;
	
	public User() {
    }
	
    public User(int id) {
        this.id = id;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
        return contact;
    }
	
	public void setContact(String contact) {
        this.contact = contact;
    }
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(GrantedAuthority authority) {
		authorities.add(authority);
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public Date getWithdrawnDate() {
		return withdrawnDate;
	}

	public void setWithdrawnDate(Date withdrawnDate) {
		this.withdrawnDate = withdrawnDate;
	}

	public Attachment getPhoto() {
        return photo;
    }
	
	public void setPhoto(Attachment photo) {
        this.photo = photo;
    }
	
	public MultipartFile getUpload() {
        return upload;
    }
	
	public void setUpload(MultipartFile upload) {
        this.upload = upload;
    }
	
	public String getRoleName() {
	    String roleName = "사용자";
	    for(GrantedAuthority auth : authorities) {
	        String role = auth.getAuthority();
	        if("ROLE_ADMIN".equals(role)) {
	            roleName = "관리자";
	            break;
	        }
	        if("ROLE_CHURCHADMIN".equals(role)) {
	            roleName = "교회관리자";
	            break;
	        }
	        
//	        if(role.matches("^ROLE_[a-zA-Z0-9]+{16}")) {
//	            roleName = "교회관리자";
//	            break;
//	        }
	    }
	    
	    return roleName;
	}
	
	public boolean hasRole(String role) {
	    for(GrantedAuthority auth : authorities) {
	        if(role.equals(auth.getAuthority())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	// implements UserDetails

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return getEmail();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return enabled;
	}

    @Override
    public String toString() {
    	return "USER ID: "
    	        + id
    	        + ", email: "
    	        + email
    	        + ", name: "
    	        + name
    	        + ", contact: "
    	        + contact;
    }	
}
