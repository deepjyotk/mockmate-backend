package com.mockmate.auth_service.dto.question;

import java.time.LocalDateTime;

public class QuestionCompanyDto {
    private Long id;
    private Long companyId;
    private Integer frequencyAsked;
    private LocalDateTime lastAskedDate;

    public QuestionCompanyDto(Long id, Long companyId, Integer frequencyAsked, LocalDateTime lastAskedDate) {
        this.id = id;
        this.companyId = companyId;
        this.frequencyAsked = frequencyAsked;
        this.lastAskedDate = lastAskedDate;
    }

    public QuestionCompanyDto() {
    }

    public Long getId() {
        return this.id;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Integer getFrequencyAsked() {
        return this.frequencyAsked;
    }

    public LocalDateTime getLastAskedDate() {
        return this.lastAskedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setFrequencyAsked(Integer frequencyAsked) {
        this.frequencyAsked = frequencyAsked;
    }

    public void setLastAskedDate(LocalDateTime lastAskedDate) {
        this.lastAskedDate = lastAskedDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QuestionCompanyDto)) return false;
        final QuestionCompanyDto other = (QuestionCompanyDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$companyId = this.getCompanyId();
        final Object other$companyId = other.getCompanyId();
        if (this$companyId == null ? other$companyId != null : !this$companyId.equals(other$companyId)) return false;
        final Object this$frequencyAsked = this.getFrequencyAsked();
        final Object other$frequencyAsked = other.getFrequencyAsked();
        if (this$frequencyAsked == null ? other$frequencyAsked != null : !this$frequencyAsked.equals(other$frequencyAsked))
            return false;
        final Object this$lastAskedDate = this.getLastAskedDate();
        final Object other$lastAskedDate = other.getLastAskedDate();
        if (this$lastAskedDate == null ? other$lastAskedDate != null : !this$lastAskedDate.equals(other$lastAskedDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof QuestionCompanyDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $companyId = this.getCompanyId();
        result = result * PRIME + ($companyId == null ? 43 : $companyId.hashCode());
        final Object $frequencyAsked = this.getFrequencyAsked();
        result = result * PRIME + ($frequencyAsked == null ? 43 : $frequencyAsked.hashCode());
        final Object $lastAskedDate = this.getLastAskedDate();
        result = result * PRIME + ($lastAskedDate == null ? 43 : $lastAskedDate.hashCode());
        return result;
    }

    public String toString() {
        return "QuestionCompanyDto(id=" + this.getId() + ", companyId=" + this.getCompanyId() + ", frequencyAsked=" + this.getFrequencyAsked() + ", lastAskedDate=" + this.getLastAskedDate() + ")";
    }
}