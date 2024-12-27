package com.mockmate.auth_service.dto.user;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackByPeerDTO {

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "communicationSkillsRating", nullable = false)
    private Integer communicationSkillsRating;

    @Min(1)
    @Max(5)
    @Column(name = "technicalSkillsRating", nullable = true)
    private Integer technicalSkillsRating;

    @Column(name = "didWellText", length = 500) // adjust length as needed
    private String didWellText;

    @Column(name = "thingsToImproveText", length = 500)
    private String thingsToImproveText;

    @Column(name = "nextRoundSelection", nullable = false)
    private Boolean nextRoundSelection;

    @Min(1)
    @Max(5)
    @Column(name = "goodMatchForPeerRating", nullable = false)
    private Integer goodMatchForPeerRating;


}
