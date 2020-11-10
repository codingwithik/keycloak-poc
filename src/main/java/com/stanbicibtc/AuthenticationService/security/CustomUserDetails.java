package com.stanbicibtc.AuthenticationService.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.stanbicibtc.AuthenticationService.entities.User;
import com.stanbicibtc.AuthenticationService.util.Constant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserDetails extends User implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomUserDetails() {
		super();	
	}

	public CustomUserDetails(User user) {
		
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

    	Collection<SimpleGrantedAuthority> c = new ArrayList<>();
    	c.add(new SimpleGrantedAuthority(Constant.ROLE_USER));
    	return c;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.getEnable();
    }
   
    
}