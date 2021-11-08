package br.ufrn.imd.promocon.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufrn.imd.promocon.enums.EnumRoles;

@Table(name="users")
@Entity
public class User extends GenericEntity implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
	@SequenceGenerator(name = "SEQ_USER", sequenceName = "seq_user", allocationSize = 1)
	private Long id;
	
	private String login;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private EnumRoles role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EnumRoles getRole() {
		return role;
	}

	public void setRole(EnumRoles role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(role.getCode()));
		
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.getActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.getActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.getActive();
	}

	@Override
	public boolean isEnabled() {
		return this.getActive();
	}
}
