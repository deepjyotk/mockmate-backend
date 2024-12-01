package com.mockmate.auth_service.dto.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CompanyFrequencyDto {
    @JsonProperty("companyId")
    private Long companyId;

    @JsonProperty("frequencyAsked")
    private Integer frequencyAsked;

    @JsonProperty("lastAskedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastAskedDate; // Can be null; handle default date in service layer if needed

    public CompanyFrequencyDto(Long companyId, Integer frequencyAsked, LocalDateTime lastAskedDate) {
        this.companyId = companyId;
        this.frequencyAsked = frequencyAsked;
        this.lastAskedDate = lastAskedDate;
    }

    public CompanyFrequencyDto() {
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
        if (!(o instanceof CompanyFrequencyDto)) return false;
        final CompanyFrequencyDto other = (CompanyFrequencyDto) o;
        if (!other.canEqual((Object) this)) return false;
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
        return other instanceof CompanyFrequencyDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $companyId = this.getCompanyId();
        result = result * PRIME + ($companyId == null ? 43 : $companyId.hashCode());
        final Object $frequencyAsked = this.getFrequencyAsked();
        result = result * PRIME + ($frequencyAsked == null ? 43 : $frequencyAsked.hashCode());
        final Object $lastAskedDate = this.getLastAskedDate();
        result = result * PRIME + ($lastAskedDate == null ? 43 : $lastAskedDate.hashCode());
        return result;
    }

    public String toString() {
        return "CompanyFrequencyDto(companyId=" + this.getCompanyId() + ", frequencyAsked=" + this.getFrequencyAsked() + ", lastAskedDate=" + this.getLastAskedDate() + ")";
    }
}