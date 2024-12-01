package com.mockmate.auth_service.dto.user;

public class UserSpecificInterviewTypeDto {
    private Long interviewTypeId;
    private String interviewTypeTitle;
    private String interviewTypeDescription;
    private int durationMinutes;

    public UserSpecificInterviewTypeDto(Long interviewTypeId, String interviewTypeTitle, String interviewTypeDescription, int durationMinutes) {
        this.interviewTypeId = interviewTypeId;
        this.interviewTypeTitle = interviewTypeTitle;
        this.interviewTypeDescription = interviewTypeDescription;
        this.durationMinutes = durationMinutes;
    }

    public Long getInterviewTypeId() {
        return this.interviewTypeId;
    }

    public String getInterviewTypeTitle() {
        return this.interviewTypeTitle;
    }

    public String getInterviewTypeDescription() {
        return this.interviewTypeDescription;
    }

    public int getDurationMinutes() {
        return this.durationMinutes;
    }

    public void setInterviewTypeId(Long interviewTypeId) {
        this.interviewTypeId = interviewTypeId;
    }

    public void setInterviewTypeTitle(String interviewTypeTitle) {
        this.interviewTypeTitle = interviewTypeTitle;
    }

    public void setInterviewTypeDescription(String interviewTypeDescription) {
        this.interviewTypeDescription = interviewTypeDescription;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserSpecificInterviewTypeDto)) return false;
        final UserSpecificInterviewTypeDto other = (UserSpecificInterviewTypeDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$interviewTypeId = this.getInterviewTypeId();
        final Object other$interviewTypeId = other.getInterviewTypeId();
        if (this$interviewTypeId == null ? other$interviewTypeId != null : !this$interviewTypeId.equals(other$interviewTypeId))
            return false;
        final Object this$interviewTypeTitle = this.getInterviewTypeTitle();
        final Object other$interviewTypeTitle = other.getInterviewTypeTitle();
        if (this$interviewTypeTitle == null ? other$interviewTypeTitle != null : !this$interviewTypeTitle.equals(other$interviewTypeTitle))
            return false;
        final Object this$interviewTypeDescription = this.getInterviewTypeDescription();
        final Object other$interviewTypeDescription = other.getInterviewTypeDescription();
        if (this$interviewTypeDescription == null ? other$interviewTypeDescription != null : !this$interviewTypeDescription.equals(other$interviewTypeDescription))
            return false;
        if (this.getDurationMinutes() != other.getDurationMinutes()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserSpecificInterviewTypeDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $interviewTypeId = this.getInterviewTypeId();
        result = result * PRIME + ($interviewTypeId == null ? 43 : $interviewTypeId.hashCode());
        final Object $interviewTypeTitle = this.getInterviewTypeTitle();
        result = result * PRIME + ($interviewTypeTitle == null ? 43 : $interviewTypeTitle.hashCode());
        final Object $interviewTypeDescription = this.getInterviewTypeDescription();
        result = result * PRIME + ($interviewTypeDescription == null ? 43 : $interviewTypeDescription.hashCode());
        result = result * PRIME + this.getDurationMinutes();
        return result;
    }

    public String toString() {
        return "UserSpecificInterviewTypeDto(interviewTypeId=" + this.getInterviewTypeId() + ", interviewTypeTitle=" + this.getInterviewTypeTitle() + ", interviewTypeDescription=" + this.getInterviewTypeDescription() + ", durationMinutes=" + this.getDurationMinutes() + ")";
    }
}
