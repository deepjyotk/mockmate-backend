package com.mockmate.auth_service.service.user_service;

import com.mockmate.auth_service.dto.interview.InitResponseDto;
import com.mockmate.auth_service.dto.interview.InterviewDto;
import com.mockmate.auth_service.dto.interview.InterviewLevelDto;
import com.mockmate.auth_service.dto.interview.SlotDto;
import com.mockmate.auth_service.dto.login.LoginRequestDto;
import com.mockmate.auth_service.dto.login.LoginResponseDto;
import com.mockmate.auth_service.dto.register.RegisterRequestDto;
import com.mockmate.auth_service.dto.register.RegisterResponseDto;
import com.mockmate.auth_service.dto.user.*;
import com.mockmate.auth_service.entities.Role;
import com.mockmate.auth_service.entities.UserEntity;
import com.mockmate.auth_service.entities.interview.*;
import com.mockmate.auth_service.entities.questions.Question;
import com.mockmate.auth_service.exception.custom.EmailAlreadyExistsException;
import com.mockmate.auth_service.exception.custom.ResourceNotFoundException;
import com.mockmate.auth_service.exception.custom.UsernameAlreadyExistsException;
import com.mockmate.auth_service.repository.interview.*;
import com.mockmate.auth_service.repository.question.QuestionRepository;
import com.mockmate.auth_service.repository.user.RoleRepository;
import com.mockmate.auth_service.repository.user.UserRepository;
import com.mockmate.auth_service.security.jwt.JwtTokenUtil;
import com.mockmate.auth_service.utils.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtTokenUtil jwtUtils;

    private final InterviewLevelRepository interviewLevelRepository ;
    private final InterviewTypeRepository interviewTypeRepository ;
    private final InterviewSlotRepository interviewSlotRepository ;
    private final QuestionRepository questionRepository ;
    private final UpcomingInterviewRepository upcomingInterviewRepository ;
    private final PastInterviewRepository pastInterviewRepository ;


    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, JwtTokenUtil jwtUtils,
                       InterviewLevelRepository interviewLevelRepository,
                       InterviewTypeRepository interviewTypeRepository,
                       InterviewSlotRepository interviewSlotRepository,
                       QuestionRepository questionRepository  ,
                       UpcomingInterviewRepository upcomingInterviewRepository ,
                       PastInterviewRepository pastInterviewRepository
                       ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.interviewLevelRepository = interviewLevelRepository ;
        this.interviewTypeRepository = interviewTypeRepository ;
        this.interviewSlotRepository = interviewSlotRepository ;
        this.questionRepository = questionRepository  ;
        this.upcomingInterviewRepository = upcomingInterviewRepository ;
        this.pastInterviewRepository = pastInterviewRepository ;
    }


    @Override
    public RegisterResponseDto register(RegisterRequestDto request) {
        // Check if the user already exists
        Optional<UserEntity> existingUser = userRepository.findByUsername(request.getUsername());
        Optional<UserEntity> existingEmail = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent() && existingEmail.isPresent() ) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }else if(existingEmail.isPresent()){
            throw new EmailAlreadyExistsException(request.getEmail());
        }else if(existingUser.isPresent()){
            throw new EmailAlreadyExistsException(request.getUsername());
        }

        // Convert RegisterRequest to UserEntity
        UserEntity user = UserMapper.toEntity(request);

        // Hash the password
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // Assign role (default to "FREE_USER" if not provided)
        Role role = roleRepository.findByName("ROLE_" +
                        (request.getRole() != null ? request.getRole(): "FREE_USER"))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));

        // Save the user
        userRepository.save(user);

        // Generate JWT token
        String token = jwtUtils.generateToken(user);

        // Convert UserEntity to RegisterResponse
        return UserMapper.toRegisterResponse(user, token);

    }
    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        String userOrEmail = request.getUserOrEmail();
        Optional<UserEntity> existingUser;

        // Determine if the input is an email or username
        if (userOrEmail.contains("@")) {
            existingUser = userRepository.findByEmail(userOrEmail);
        } else {
            existingUser = userRepository.findByUsername(userOrEmail);
        }

        if (existingUser.isEmpty()) {
            throw new RuntimeException("Invalid username/email or password!");
        }

        UserEntity user = existingUser.get();

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid username/email or password!");
        }

        // Generate JWT token
        String token = jwtUtils.generateToken(user);

        // Return LoginResponse with token, message, and username/email
        return new LoginResponseDto(
                token,
                "Login successful",
                user.getUsername() // You can choose to return email or username based on your requirements
        );
    }


    public InitResponseDto getInitData() {
        InitResponseDto initResponseDTO = new InitResponseDto();

        // Fetch and map Interview Levels
        List<InterviewLevel> interviewLevels = interviewLevelRepository.findAll();

        List<InterviewLevelDto> interviewLevelDTOs = interviewLevels.stream()
                .map(level -> new InterviewLevelDto(
                        level.getId(),
                        level.getLevel(),
                        level.getDescription()))
                .collect(Collectors.toList());

        initResponseDTO.setInterviewLevels(interviewLevelDTOs);

        // Fetch all Interview Slots with associated Interview Types
        List<InterviewSlot> interviewSlots = interviewSlotRepository.findAllWithInterviewType();

        // Group slots by InterviewType ID
        Map<Long, List<InterviewSlot>> slotsByInterviewTypeId = interviewSlots.stream()
                .collect(Collectors.groupingBy(slot -> slot.getInterviewTypeTime().getInterviewType().getId()));

        // Fetch all Interview Types
        List<InterviewType> interviewTypes = interviewTypeRepository.findAll();

        List<InterviewDto> interviewDTOs = new ArrayList<>();

        for (InterviewType interviewType : interviewTypes) {
            Long interviewId = interviewType.getId();
            String type = interviewType.getType();
            String description = interviewType.getDescription();
            Integer durationMinutes = interviewType.getDurationMinutes();

            List<InterviewSlot> slots = slotsByInterviewTypeId.get(interviewId);

            List<SlotDto> slotDTOs = new ArrayList<>();

            ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
            if (slots != null) {
                slotDTOs = slots.stream()
                        .filter(slot -> {
                            LocalDate date = slot.getInterviewDate();
                            String time = slot.getInterviewTypeTime().getTime();

                            // Parse time and combine with date
                            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
                            LocalDateTime localDateTime = LocalDateTime.of(date, localTime);
                            ZonedDateTime utcDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);

                            // Only keep slots that are in the future
                            return utcDateTime.compareTo(currentDateTime) >= 0;
                        })
                        .map(slot -> {
                            Long slotId = slot.getSlotId();
                            LocalDate date = slot.getInterviewDate();
                            String time = slot.getInterviewTypeTime().getTime();

                            // Parse time and combine with date
                            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
                            LocalDateTime localDateTime = LocalDateTime.of(date, localTime);
                            ZonedDateTime utcDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);

                            String slotDateTimeStr = utcDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                            return new SlotDto(slotId, slotDateTimeStr);
                        })
                        .collect(Collectors.toList());
            }

            InterviewDto interviewDTO = new InterviewDto(
                    interviewId,
                    type,
                    description,
                    durationMinutes,
                    slotDTOs);

            interviewDTOs.add(interviewDTO);
        }

        initResponseDTO.setInterviews(interviewDTOs);

        return initResponseDTO;
    }


    @Override
    public UserSpecificResponseDto getUserSpecificData(Long userId, int currentPage, int limit) {
        // Set default values if not provided
        if (currentPage < 1) currentPage = 1;
        if (limit < 1) limit = 1;

        // Fetch Upcoming Interviews
        List<UpcomingInterviews> upcomingInterviews = upcomingInterviewRepository.findByUserId(userId);
        List<UserSpecificUpcomingInterviewDto> upcomingInterviewDtos = upcomingInterviews.stream().map(this::mapUpcomingInterview).collect(Collectors.toList());

        // Fetch Past Interviews with Pagination
        PageRequest pageRequest = PageRequest.of(currentPage - 1, limit);
        Page<PastInterviews> pastInterviewsPage = pastInterviewRepository.findByUserIdOrderByDateAndTimeDesc(userId, pageRequest);
        List<PastInterviewDto> pastInterviewDtos = pastInterviewsPage.getContent().stream().map(this::mapPastInterview).collect(Collectors.toList());

        PastInterviewsPageDto pastInterviewsPageDto = new PastInterviewsPageDto();
        pastInterviewsPageDto.setPage(currentPage);
        pastInterviewsPageDto.setLimit(limit);
        pastInterviewsPageDto.setTotalListings(pastInterviewsPage.getTotalElements());
        pastInterviewsPageDto.setTotalPages(pastInterviewsPage.getTotalPages());
        pastInterviewsPageDto.setResults(pastInterviewDtos);

        // Construct UserSpecificResponseDto
        UserSpecificResponseDto responseDto = new UserSpecificResponseDto();
        responseDto.setUpcomingInterviews(upcomingInterviewDtos);
        responseDto.setPastInterviews(pastInterviewsPageDto);

        return responseDto;
    }

    private OffsetDateTime getDateAndTime(InterviewSlot interviewSlot, InterviewTypeTime interviewTypeTime){
        LocalDate interviewDate = interviewSlot.getInterviewDate();
        String interviewTime = interviewTypeTime.getTime(); // Assuming ISO UTC format (e.g., "15:30:00Z")

        // Parse the time as an OffsetTime (ensuring it's treated as UTC)
        OffsetTime offsetTime = OffsetTime.parse(interviewTime);

        // Combine the LocalDate and OffsetTime into an OffsetDateTime
        return OffsetDateTime.of(interviewDate, offsetTime.toLocalTime(), ZoneOffset.UTC);
    }

    @Transactional
    private UserSpecificUpcomingInterviewDto mapUpcomingInterview(UpcomingInterviews interview) {
        UserSpecificUpcomingInterviewDto dto = new UserSpecificUpcomingInterviewDto();
        dto.setInterviewId(interview.getUpcomingInterviewId());
        InterviewSlot interviewSlot = interviewSlotRepository.
                 findBySlotId(interview.getSlotId() ).orElseThrow(()->
                         new ResourceNotFoundException("Slot Id: "+interview.getSlotId()+" not found"));

        var interviewTypeTime = interviewSlot.getInterviewTypeTime() ;
        InterviewType interviewType = interviewTypeTime.getInterviewType() ;

        dto.setInterviewType(new UserSpecificInterviewTypeDto(
                interviewType.getId(),interviewType.getType(), interviewType.getDescription(), interviewType.getDurationMinutes()
        ));
        dto.setInterviewDateAndTime(getDateAndTime(interviewSlot,interviewTypeTime));

        if (interview.getPeerInterview() != null) {
            questionRepository.findById(interview.getQuestionIDForPeer()).ifPresent(question -> dto.setUpcomingInterviewQuestionForPeer(new UserSpecificUpcomingInterviewQuestionDto(
                    question.getQuestionId(),
                    question.getQuestionTitle(),
                    question.getDescription()
            )));
        }
        return dto;
    }

    private PastInterviewDto mapPastInterview(PastInterviews interview) {
        PastInterviewDto dto = new PastInterviewDto();
        dto.setPastInterviewID(interview.getPastInterviewId());
        dto.setPastInterviewDateAndTime(interview.getDateAndTime());
        dto.setPastInterviewType(interview.getInterviewType().getType());

        // Fetch QuestionForMe
        if (interview.getQuestionId() != null) {
            Question question = questionRepository.findById(interview.getQuestionId()).orElse(null);
            if (question != null) {
                QuestionForMeDto questionDto = new QuestionForMeDto();
                questionDto.setQuestionId(question.getQuestionId());
                questionDto.setQuestionText(question.getQuestionTextS3Url()); // Assuming text is stored here
                dto.setQuestionForMe(questionDto);
            }
        }

        // Fetch PeerUser
        if (interview.getPeerInterview() != null) {
            Long peerUserId = interview.getPeerInterview().getUserId();
            // Assuming UserRepository has a method to find user by ID
            UserEntity peerUser = userRepository.findById(peerUserId).orElse(null);
            if (peerUser != null) {
                PeerUserDto peerUserDto = new PeerUserDto(
                        peerUser.getId(),
                        peerUser.getUsername()
                );
                dto.setPeerUser(peerUserDto);
            }
        }
        return dto;
    }

}
