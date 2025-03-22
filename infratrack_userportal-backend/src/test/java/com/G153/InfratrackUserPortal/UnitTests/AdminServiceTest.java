package com.G153.InfratrackUserPortal.UnitTests;
import com.G153.InfratrackUserPortal.DTO.AdminLoginRequest;
import com.G153.InfratrackUserPortal.DTO.JwtAuthResponse;
import com.G153.InfratrackUserPortal.Entities.Admin;
import com.G153.InfratrackUserPortal.Repositories.AdminRepository;
import com.G153.InfratrackUserPortal.Services.AdminService;
import com.G153.InfratrackUserPortal.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AdminService adminService;

    private AdminLoginRequest loginRequest;
    private Admin admin;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        loginRequest = new AdminLoginRequest();
        loginRequest.setAdminNo("admin123");
        loginRequest.setPassword("password");

        admin = new Admin();
        admin.setAdminNo("admin123");
        encodedPassword = "encodedPassword"; // Define the encoded password
        admin.setPassword(encodedPassword);
    }

    @Test
    void loginAdmin_shouldReturnJwtAuthResponse_whenCredentialsAreValid() {
        when(adminRepository.findByAdminNo(loginRequest.getAdminNo())).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(admin.getAdminNo())).thenReturn("testToken");

        ResponseEntity<?> response = adminService.loginAdmin(loginRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isInstanceOf(JwtAuthResponse.class);

        JwtAuthResponse jwtAuthResponse = (JwtAuthResponse) response.getBody();
        assertThat(jwtAuthResponse.getAccessToken()).isEqualTo("testToken");
    }

    @Test
    void loginAdmin_shouldReturnNotFound_whenAdminNotFound() {
        when(adminRepository.findByAdminNo(loginRequest.getAdminNo())).thenReturn(Optional.empty());

        ResponseEntity<?> response = adminService.loginAdmin(loginRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Admin not found");
    }

    @Test
    void loginAdmin_shouldReturnUnauthorized_whenPasswordDoesNotMatch() {
        Admin wrongPasswordAdmin = new Admin();
        wrongPasswordAdmin.setAdminNo("admin123");
        wrongPasswordAdmin.setPassword("wrongEncodedPassword");
        when(adminRepository.findByAdminNo(loginRequest.getAdminNo())).thenReturn(Optional.of(wrongPasswordAdmin));
        when(passwordEncoder.matches(loginRequest.getPassword(), wrongPasswordAdmin.getPassword())).thenReturn(false);

        ResponseEntity<?> response = adminService.loginAdmin(loginRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("Invalid credentials");
    }

    @Test
    void registerAdmin_shouldReturnOk_whenAdminDoesNotExist() {
        Admin newAdmin = new Admin();
        newAdmin.setAdminNo("newAdmin");
        newAdmin.setPassword("newPassword");
        when(adminRepository.findByAdminNo(newAdmin.getAdminNo())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(newAdmin.getPassword())).thenReturn("encodedPassword");
        when(adminRepository.save(any(Admin.class))).thenReturn(newAdmin);

        ResponseEntity<String> response = adminService.registerAdmin(newAdmin);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Admin registered successfully");
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    void registerAdmin_shouldReturnBadRequest_whenAdminAlreadyExists() {
        Admin existingAdmin = new Admin();
        existingAdmin.setAdminNo("existingAdmin");
        existingAdmin.setPassword("password");
        when(adminRepository.findByAdminNo(existingAdmin.getAdminNo())).thenReturn(Optional.of(existingAdmin));

        ResponseEntity<String> response = adminService.registerAdmin(existingAdmin);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Admin already exists");
        verify(adminRepository, never()).save(any(Admin.class));
    }
}