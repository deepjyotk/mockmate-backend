package com.mockmate.auth_service.dto.question;

public class CompanyDto {
    private Long companyId;
    private String companyName;

    public CompanyDto(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public CompanyDto() {
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CompanyDto)) return false;
        final CompanyDto other = (CompanyDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$companyId = this.getCompanyId();
        final Object other$companyId = other.getCompanyId();
        if (this$companyId == null ? other$companyId != null : !this$companyId.equals(other$companyId)) return false;
        final Object this$companyName = this.getCompanyName();
        final Object other$companyName = other.getCompanyName();
        if (this$companyName == null ? other$companyName != null : !this$companyName.equals(other$companyName))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CompanyDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $companyId = this.getCompanyId();
        result = result * PRIME + ($companyId == null ? 43 : $companyId.hashCode());
        final Object $companyName = this.getCompanyName();
        result = result * PRIME + ($companyName == null ? 43 : $companyName.hashCode());
        return result;
    }

    public String toString() {
        return "CompanyDto(companyId=" + this.getCompanyId() + ", companyName=" + this.getCompanyName() + ")";
    }
}