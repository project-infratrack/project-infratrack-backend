package com.G153.InfratrackUserPortal.UnitTests;

import com.G153.InfratrackUserPortal.DTO.JwtAuthResponse;
import com.G153.InfratrackUserPortal.DTO.UserProfileDetails;
import com.G153.InfratrackUserPortal.DTO.UserRegistrationRequest;
import com.G153.InfratrackUserPortal.Entities.User;
import com.G153.InfratrackUserPortal.Repositories.UserRepository;
import com.G153.InfratrackUserPortal.Services.UserService;
import com.G153.InfratrackUserPortal.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setIdNumber("12345");
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password");
        request.setFirstName("Test");
        request.setLastName("User");
        request.setMobileNumber("1234567890");

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByIdNumber(request.getIdNumber())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        User user = new User();
        user.setIdNumber(request.getIdNumber());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword("encodedPassword");
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobileNumber(request.getMobileNumber());

        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(request);

        assertNotNull(registeredUser);
        assertEquals("encodedPassword", registeredUser.getPassword());
    }

    @Test
    void testLoginUser_Success() {
        String idNumber = "12345";
        String password = "password";
        User user = new User();
        user.setIdNumber(idNumber);
        user.setPassword("encodedPassword");

        when(userRepository.findByIdNumber(idNumber)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(idNumber)).thenReturn("jwtToken");

        ResponseEntity<?> response = userService.loginUser(idNumber, password);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof JwtAuthResponse);
        assertEquals("jwtToken", ((JwtAuthResponse) response.getBody()).getToken());
    }

    @Test
    void testInitiateForgetPassword_Success() {
        String idNumber = "12345";
        User user = new User();
        user.setIdNumber(idNumber);
        user.setEmail("test@example.com");

        when(userRepository.findByIdNumber(idNumber)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedOtp");

        ResponseEntity<?> response = userService.initiateForgetPassword(idNumber);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("OTP has been sent to your registered email", response.getBody());
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testVerifyOtp_Success() {
        String idNumber = "12345";
        String otp = "123456";
        User user = new User();
        user.setIdNumber(idNumber);
        user.setOtp("encodedOtp");
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(15));

        when(userRepository.findByIdNumber(idNumber)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(otp, user.getOtp())).thenReturn(true);
        when(jwtTokenProvider.generateToken(idNumber)).thenReturn("jwtToken");

        ResponseEntity<?> response = userService.verifyOtp(idNumber, otp);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof JwtAuthResponse);
        assertEquals("jwtToken", ((JwtAuthResponse) response.getBody()).getToken());
    }

    @Test
    void testGetUserByIdNumber_Success() {
        String idNumber = "12345";
        User user = new User();
        user.setIdNumber(idNumber);

        when(userRepository.findByIdNumber(idNumber)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByIdNumber(idNumber);

        assertTrue(foundUser.isPresent());
        assertEquals(idNumber, foundUser.get().getIdNumber());
    }

    @Test
    void testGetUserProfileDetails_Success() {
        String idNumber = "12345";
        User user = new User();
        user.setIdNumber(idNumber);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setMobileNumber("1234567890");

        when(userRepository.findByIdNumber(idNumber)).thenReturn(Optional.of(user));

        UserProfileDetails userProfileDetails = userService.getUserProfileDetails(idNumber);

        assertNotNull(userProfileDetails);
        assertEquals("Test User", userProfileDetails.getName());
        assertEquals("testuser", userProfileDetails.getUsername());
        assertEquals("test@example.com", userProfileDetails.getEmail());
        assertEquals("1234567890", userProfileDetails.getMobileNo());
    }
}