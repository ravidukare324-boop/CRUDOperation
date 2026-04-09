package com.in.Entitty;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
	@Entity
	@Table(name = "users")
public class Users implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	@Column(unique = true )
	private String email;
	private  String name ; 
	private  String password;
	private String image;
	private boolean enabled = true;
	private Instant createdAt = Instant.now();
	private Instant updatedAt = Instant.now();
	
	 @ManyToMany(fetch = FetchType.EAGER)
	 @JoinTable(
			 name = "joinTables",
			 joinColumns = @JoinColumn(name = "user_id"),
       inverseJoinColumns = @JoinColumn(name = "role_id")
			 )
	private Set<Role> roles = new HashSet<>();
	 
	@Enumerated(EnumType.STRING)
  private Provider provider = Provider.LOCAL;
	
	@PrePersist
	protected void onCreate() {
		if (createdAt == null) createdAt = Instant.now();
		updatedAt = Instant.now();
	}
	@PreUpdate
	protected void onUpdate() {
		updatedAt = Instant.now();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	  
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
}
