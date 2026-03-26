package com.appointment.service;

import com.appointment.model.Business;
import com.appointment.model.Industry;
import com.appointment.model.Staff;
import com.appointment.model.User;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.IndustryRepository;
import com.appointment.repository.StaffRepository;
import com.appointment.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final BusinessRepository businessRepository;
    private final IndustryRepository industryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            StaffRepository staffRepository,
            BusinessRepository businessRepository,
            IndustryRepository industryRepository
    ) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.businessRepository = businessRepository;
        this.industryRepository = industryRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.getActive()) {
            throw new RuntimeException("User is inactive");
        }

        return user;
    }

    @Transactional
    public User signupCustomer(String email, String password, String firstName, String lastName) {
        return createUser(email, password, firstName, lastName, "customer");
    }

    @Transactional
    public User signupStaff(
            String email,
            String password,
            String firstName,
            String lastName,
            String businessCode,
            String phone,
            String colorHex
    ) {
        User user = createUser(email, password, firstName, lastName, "staff");

        Business business = resolveBusinessFromCode(businessCode);

        Optional<Staff> existingStaffOpt =
                staffRepository.findByBusiness_IdAndEmailIgnoreCase(business.getId(), email);

        if (existingStaffOpt.isPresent()) {
            Staff existingStaff = existingStaffOpt.get();

            if (existingStaff.getUser() != null) {
                throw new RuntimeException("A staff account is already linked for this email and business");
            }

            existingStaff.setUser(user);
            existingStaff.setFirstName(firstName);
            existingStaff.setLastName(lastName);
            existingStaff.setPhone(phone);
            existingStaff.setColorHex(colorHex);
            existingStaff.setActive(true);

            staffRepository.save(existingStaff);
        } else {
            Staff staff = new Staff();
            staff.setBusiness(business);
            staff.setUser(user);
            staff.setFirstName(firstName);
            staff.setLastName(lastName);
            staff.setEmail(email);
            staff.setPhone(phone);
            staff.setColorHex(colorHex);
            staff.setActive(true);

            staffRepository.save(staff);
        }

        return user;
    }

    @Transactional
    public User signupBusiness(
            String ownerEmail,
            String password,
            String ownerFirstName,
            String ownerLastName,
            String businessName,
            Long industryId,
            String phone,
            String businessEmail,
            String timezone,
            String address,
            String logoUrl
    ) {
        User user = createUser(ownerEmail, password, ownerFirstName, ownerLastName, "owner");

        Industry industry = industryRepository.findById(industryId)
                .orElseThrow(() -> new RuntimeException("Industry not found"));

        Business business = new Business();
        business.setOwnerUserId(user.getId());
        business.setName(businessName);
        business.setIndustry(industry);
        business.setPhone(phone);
        business.setEmail(businessEmail);
        business.setTimezone(timezone);
        business.setAddress(address);
        business.setLogoUrl(logoUrl);

        businessRepository.save(business);

        return user;
    }

    private User createUser(String email, String password, String firstName, String lastName, String role) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(role);
        user.setActive(true);

        return userRepository.save(user);
    }

    private Business resolveBusinessFromCode(String businessCode) {
        // IMPORTANT:
        // Your current Business entity/table has no businessCode field.
        // Replace this with real business-code lookup once added.
        try {
            Long businessId = Long.parseLong(businessCode);
            return businessRepository.findById(businessId)
                    .orElseThrow(() -> new RuntimeException("Business not found"));
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid business code");
        }
    }
}