package com.mockmate.auth_service.dto.interview;

import com.mockmate.auth_service.validation.annotation.ValidInterviewLevel;

public class InterviewLevelDto {
    private Long id;

    @ValidInterviewLevel
    private String level;
    private String description;

    public InterviewLevelDto() {
    }

    public InterviewLevelDto(Long id, String level, String description) {
        this.id = id;
        this.level = level;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof InterviewLevelDto)) return false;
        final InterviewLevelDto other = (InterviewLevelDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$level = this.getLevel();
        final Object other$level = other.getLevel();
        if (this$level == null ? other$level != null : !this$level.equals(other$level)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InterviewLevelDto;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        // Properly handle long ID
        long id = this.getId();
        result = result * PRIME + (int) (id ^ (id >>> 32)); // Combines high and low bits

        final Object $level = this.getLevel();
        result = result * PRIME + ($level == null ? 43 : $level.hashCode());

        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());

        return result;
    }
    public String toString() {
        return "InterviewLevelDto(id=" + this.getId() + ", level=" + this.getLevel() + ", description=" + this.getDescription() + ")";
    }
}
