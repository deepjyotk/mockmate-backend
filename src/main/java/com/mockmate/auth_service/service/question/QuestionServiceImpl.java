package com.mockmate.auth_service.service.question;

import com.mockmate.auth_service.dto.question.CompanyFrequencyDto;
import com.mockmate.auth_service.dto.question.QuestionPopulateRequestDto;
import com.mockmate.auth_service.dto.question.QuestionResponseDto;
import com.mockmate.auth_service.entities.interview.InterviewLevel;
import com.mockmate.auth_service.entities.interview.InterviewType;
import com.mockmate.auth_service.entities.interview.UpcomingInterviewUserPreference;
import com.mockmate.auth_service.entities.interview.UpcomingInterviews;
import com.mockmate.auth_service.entities.questions.*;
import com.mockmate.auth_service.exception.custom.ResourceNotFoundException;
import com.mockmate.auth_service.repository.interview.*;
import com.mockmate.auth_service.repository.question.*;
import com.mockmate.auth_service.service.aws.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final InterviewTypeRepository interviewTypeRepository;
    private final InterviewLevelRepository interviewLevelRepository;
    private final QTagRepository qTagRepository;
    private final CompanyRepository companyRepository;
    private final AWSS3Service awss3Service;
    private final UpcomingInterviewRepository upcomingInterviewsRepository;
    private final PastInterviewRepository pastInterviewsRepository;
    private final UpcomingInterviewUserPreferenceRepository upcomingInterviewUserPreferenceRepository ;
    private final QuestionTagComboRepository questionTagComboRepository ;


    @Autowired
    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            InterviewTypeRepository interviewTypeRepository,
            InterviewLevelRepository interviewLevelRepository,
            QTagRepository qTagRepository,
            QuestionTagComboRepository questionTagComboRepository,
            CompanyRepository companyRepository,
            QuestionCompanyRepository questionCompanyRepository,
            AWSS3Service awss3Service, UpcomingInterviewRepository upcomingInterviewsRepository,
            PastInterviewRepository pastInterviewsRepository,
            UpcomingInterviewUserPreferenceRepository upcomingInterviewUserPreferenceRepository) {
        this.questionRepository = questionRepository;
        this.interviewTypeRepository = interviewTypeRepository;
        this.interviewLevelRepository = interviewLevelRepository;
        this.qTagRepository = qTagRepository;
        this.companyRepository = companyRepository;
        this.awss3Service = awss3Service ;
        this.upcomingInterviewsRepository = upcomingInterviewsRepository ;
        this.pastInterviewsRepository = pastInterviewsRepository ;
        this.upcomingInterviewUserPreferenceRepository = upcomingInterviewUserPreferenceRepository ;
        this.questionTagComboRepository = questionTagComboRepository ;
    }

    @Override
    @Transactional
    public QuestionResponseDto populateQuestion(QuestionPopulateRequestDto requestDTO) {
        // Fetch InterviewType
        InterviewType interviewType = interviewTypeRepository.findById(requestDTO.getInterviewTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("InterviewType not found with id: " + requestDTO.getInterviewTypeId()));

        // Fetch InterviewLevel
        InterviewLevel interviewLevel = interviewLevelRepository.findById(requestDTO.getQuestionDifficultyId())
                .orElseThrow(() -> new ResourceNotFoundException("InterviewLevel not found with id: " + requestDTO.getQuestionDifficultyId()));

        // Handle Tags
        List<QTag> qTags = requestDTO.getTags().stream()
                .map(tags -> qTagRepository.findByTagId(tags.getTagID())
                        .orElseThrow(() -> new ResourceNotFoundException("Tag Id not found with id: "+ tags.getTagID())))
                        .toList();

        Set<QuestionCompany> companyAssociations = requestDTO.getCompanies().stream()
                .map(companyDTO -> {
                    Company company = companyRepository.findById(companyDTO.getCompanyId())
                            .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyDTO.getCompanyId()));
                    return new QuestionCompany(
                            null,
                            null, // Question to be set later
                            company,
                            companyDTO.getFrequencyAsked(),
                            Optional.ofNullable(companyDTO.getLastAskedDate())
                                    .orElse(LocalDateTime.of(1900, 1, 1, 0, 0))
                    );
                })
                .collect(Collectors.toSet());

        // Upload markdown file to S3
        MultipartFile markdownFile = requestDTO.getMarkdownFile();
        if (markdownFile == null || markdownFile.isEmpty()) {
            throw new IllegalArgumentException("Markdown file is required.");
        }

        String s3Url;
        try {
            s3Url = awss3Service.uploadMarkdownFile(
                    markdownFile,
                    requestDTO.getInterviewTypeId(),
                    requestDTO.getQuestionTitle(),
                    requestDTO.getQuestionDifficultyId(),
                    requestDTO.getCompanies().isEmpty() ? null : requestDTO.getCompanies().get(0).getCompanyId() // Assuming first company ID for metadata
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload markdown file.", e);
        }

        // Create Question entity
        Question question = new Question();
        question.setQuestionTextS3Url(s3Url); // Updated to store S3 URL
        question.setInterviewType(interviewType);
        question.setInterviewLevel(interviewLevel);
        question.setQuestionTitle(requestDTO.getQuestionTitle());

        var setOfQTagCombo = qTags.stream().map(tag -> {
            QuestionTagCombo questionTagCombo = new QuestionTagCombo();
            questionTagCombo.setTag(tag);
            questionTagCombo.setQuestion(question);
            return questionTagCombo ;
        }).collect(Collectors.toSet()) ;


        question.setTagCombos(setOfQTagCombo);

        // Associate companies and question
        companyAssociations.forEach(association -> association.setQuestion(question));
        question.setCompanyAssociations(companyAssociations);

        // Save Question (cascades to tagCombos and companyAssociations)
        Question savedQuestion = questionRepository.save(question);


        // Prepare Response DTO
        return mapToResponseDTO(savedQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestionById(Long questionId) {
        // Fetch the question from the repository
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

        // Extract the S3 object key from the S3 URL
        String s3Url = question.getQuestionTextS3Url();
        String objectKey = extractObjectKeyFromUrl(s3Url);

        // Define expiration time for the pre-signed URL (e.g., 10 minutes)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        Date expiration = calendar.getTime();

        // Generate pre-signed URL
        String presignedUrl = awss3Service.generatePresignedUrl(objectKey, expiration);

        return new QuestionResponseDto(
                question.getQuestionId(),
                presignedUrl,
                question.getQuestionTitle(),
                question.getInterviewType().getId(),
                question.getInterviewLevel().getId() ,
                question.getTagCombos().stream()
                        .map(combo -> combo.getTag().getQuestionTagText())
                        .collect(Collectors.toList()),
                question.getCompanyAssociations().stream()
                        .map(association -> new CompanyFrequencyDto(
                                association.getCompany().getCompanyId(),
                                association.getCompany().getCompanyName(),
                                association.getFrequencyAsked(),
                                association.getLastAskedDate()))
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public Long getRandomQuestionNotOccuredInPast(Long interviewId, Long interviewTypeId) {
        // Step 1: Fetch Upcoming Interview by interviewId
        UpcomingInterviews upcomingInterview = upcomingInterviewsRepository.findById(interviewId)
                .orElseThrow(() -> new ResourceNotFoundException("UpcomingInterview not found with id: " + interviewId));

        Long userId = upcomingInterview.getUserId();

        // Step 2: Fetch InterviewType to get DifficultyLevel
        InterviewType interviewType = interviewTypeRepository.findById(interviewTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("InterviewType not found with id: " + interviewTypeId));

        UpcomingInterviewUserPreference interviewUserPreference = this.upcomingInterviewUserPreferenceRepository.findByUpcomingInterview_UpcomingInterviewId(interviewId)
                .orElseThrow(() -> new ResourceNotFoundException("InterviewType not found with id: " + interviewTypeId));

        Long preferredLevelId = interviewUserPreference.getPreferenceInterviewLevelId();

        List<Question> preferredPotentialQuestions = questionRepository.findAllByInterviewLevel_IdAndInterviewType_Id(preferredLevelId,interviewTypeId);
        Collections.shuffle(preferredPotentialQuestions);

        for(Question question: preferredPotentialQuestions){
            boolean exists  = pastInterviewsRepository.existsByUserIdAndQuestionId(userId,question.getQuestionId()) ;
            if(!exists){
                return question.getQuestionId() ;
            }
        }

        //increase the level of the interview and get the question
        if(interviewLevelRepository.existsById(preferredLevelId+1)){
            List<Question> slightHarderQuestions = questionRepository.findAllByInterviewLevel_IdAndInterviewType_Id(preferredLevelId+1,interviewTypeId);
            Collections.shuffle(slightHarderQuestions);
            for(Question question: slightHarderQuestions){
                boolean exists  = pastInterviewsRepository.existsByUserIdAndQuestionId(userId,question.getQuestionId()) ;
                if(!exists){
                    return question.getQuestionId() ;
                }
            }
        }

        //decrease the level of the interview and get the question
        if(interviewLevelRepository.existsById(preferredLevelId-1)){
            List<Question> slightlyEasierLevelQuestions = questionRepository.findAllByInterviewLevel_IdAndInterviewType_Id(preferredLevelId-1,interviewTypeId);
            Collections.shuffle(slightlyEasierLevelQuestions);

            for(Question question: slightlyEasierLevelQuestions){
                boolean exists  = pastInterviewsRepository.existsByUserIdAndQuestionId(userId,question.getQuestionId()) ;
                if(!exists){
                    return question.getQuestionId() ;
                }
            }
        }
        var randomQuestion =   questionRepository.findRandomQuestion() ;

        return randomQuestion.map(Question::getQuestionId).orElse(null);

    }

    private String extractObjectKeyFromUrl(String s3Url) {
        // Assuming the URL follows the standard S3 format
        // Example: https://bucket-name.s3.amazonaws.com/interviewTypeId/question-title.md
        try {
            URL url = new URL(s3Url);

            String path = url.getPath();

            if (path.startsWith("/")) {
                path = path.substring(1); // Remove leading '/'
            }

            return path;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid S3 URL.", e);
        }
    }

    private QuestionResponseDto mapToResponseDTO(Question savedQuestion) {
        // Extract Tags
        Set<String> tagTexts = savedQuestion.getTagCombos().stream()
                .map(combo -> combo.getTag().getQuestionTagText())
                .collect(Collectors.toSet());

        // Extract Companies
        Set<CompanyFrequencyDto> companyFrequencyDTOs = savedQuestion.getCompanyAssociations().stream()
                .map(qc -> new CompanyFrequencyDto(
                        qc.getCompany().getCompanyId(),
                        qc.getCompany().getCompanyName(),
                        qc.getFrequencyAsked(),
                        qc.getLastAskedDate()))
                .collect(Collectors.toSet());


        // Build Response DTO
        return new QuestionResponseDto(
                savedQuestion.getQuestionId(),
                savedQuestion.getQuestionTextS3Url(),
                savedQuestion.getQuestionTitle(),
                savedQuestion.getInterviewType().getId(),
                savedQuestion.getInterviewLevel().getId(),
                tagTexts.stream().toList(),
                companyFrequencyDTOs.stream().toList()
        );
    }
}
