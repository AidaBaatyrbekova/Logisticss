package com.example.logistics.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private String imageUrl;

    private boolean verified;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Эгер колдонуучуга рольду бересиң болсо, аны Authority катары кайтарсаң болот
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: Эгер Role аркылуу GrantedAuthority түзүлсө, аны кайтаруу керек
        return List.of(); // Азыр бош
    }

    @Override
    public String getUsername() {
        // Кириүү email аркылуу жүрүп жаткандыктан emailди кайтаруу керек
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Колдонуучу эсеби токтотулбаган
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Колдонуучу кулпуланбаган
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Купуя сөзү эскирбеген
    }

    @Override
    public boolean isEnabled() {
        return verified; // Эгер колдонуучу тастыкталган болсо гана активдүү
    }
}