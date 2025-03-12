package LMS.LibraryMS.Service;
import LMS.LibraryMS.Adice.GlobalExceptionHandler;
import LMS.LibraryMS.DTO.BorrowingDTO;
import LMS.LibraryMS.DTO.UserDTO;
import LMS.LibraryMS.Entity.Role;
import LMS.LibraryMS.Entity.User;
import LMS.LibraryMS.Entity.BorrowingRecord;
import LMS.LibraryMS.Repositories.UserRepository;
import LMS.LibraryMS.Repositories.BorrowingRecordRepository;
import LMS.LibraryMS.Security.AuthRequest;
import LMS.LibraryMS.Security.AuthResponse;
import LMS.LibraryMS.Security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, BorrowingRecordRepository borrowingRecordRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    public UserDTO registerUser(UserDTO userDTO)
    {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);


        return modelMapper.map(savedUser, UserDTO.class);
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest
                        .getUsername(), authRequest
                        .getPassword())
        );

        UserDetails userDetails = loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil
                .generateToken(userDetails.getUsername(), userDetails
                        .getAuthorities().toString());

        return new AuthResponse(token);
    }

    public List<BorrowingDTO> getUserBorrowingHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<BorrowingRecord> borrowingRecords = borrowingRecordRepository.findByUser(user);
        if (borrowingRecords.isEmpty()) {
            return List.of();
        }
        return borrowingRecords.stream()
                .map(record -> new BorrowingDTO(
                        record.getBook().getId(),
                        record.getUser().getUsername(),
                        record.getBorrowDate(),
                        record.getReturnDate(),
                        record.getBook()
                ))
                .collect(Collectors.toList());
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return modelMapper.map(user, UserDTO.class);
    }


    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException(("User not found" + userId))) ;
        return modelMapper.map(user, UserDTO.class);
    }


    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setUsername(updatedUserDTO.getUsername());
        user.setRole(Role.valueOf(updatedUserDTO.getRole())); // Update role if applicable

        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(user);
        return "User deleted successfully!";
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
}
