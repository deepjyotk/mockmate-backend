package com.mockmate.auth_service.dto.interview;


public class UpcomingInterviewsDto {

    private String id;

    private String interviewTypeId;

    private String interviewLevelId;

    private String interviewSlotId;

    private String interviewDateId;

    private String userId;

    private String peerId; // Nullable, initially null, updated by the matching algorithm

    public UpcomingInterviewsDto() {
    }

    public String getId() {
        return this.id;
    }

    public String getInterviewTypeId() {
        return this.interviewTypeId;
    }


    public String getInterviewLevelId() {
        return this.interviewLevelId;
    }

    public String getInterviewSlotId() {
        return this.interviewSlotId;
    }

    public String getInterviewDateId() {
        return this.interviewDateId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPeerId() {
        return this.peerId;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setInterviewTypeId(String interviewTypeId) {
        this.interviewTypeId = interviewTypeId;
    }

    public void setInterviewLevelId(String interviewLevelId) {
        this.interviewLevelId = interviewLevelId;
    }

    public void setInterviewSlotId(String interviewSlotId) {
        this.interviewSlotId = interviewSlotId;
    }

    public void setInterviewDateId(String interviewDateId) {
        this.interviewDateId = interviewDateId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UpcomingInterviewsDto)) return false;
        final UpcomingInterviewsDto other = (UpcomingInterviewsDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$interviewTypeId = this.getInterviewTypeId();
        final Object other$interviewTypeId = other.getInterviewTypeId();
        if (this$interviewTypeId == null ? other$interviewTypeId != null : !this$interviewTypeId.equals(other$interviewTypeId))
            return false;
        final Object this$interviewLevelId = this.getInterviewLevelId();
        final Object other$interviewLevelId = other.getInterviewLevelId();
        if (this$interviewLevelId == null ? other$interviewLevelId != null : !this$interviewLevelId.equals(other$interviewLevelId))
            return false;
        final Object this$interviewSlotId = this.getInterviewSlotId();
        final Object other$interviewSlotId = other.getInterviewSlotId();
        if (this$interviewSlotId == null ? other$interviewSlotId != null : !this$interviewSlotId.equals(other$interviewSlotId))
            return false;
        final Object this$interviewDateId = this.getInterviewDateId();
        final Object other$interviewDateId = other.getInterviewDateId();
        if (this$interviewDateId == null ? other$interviewDateId != null : !this$interviewDateId.equals(other$interviewDateId))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$peerId = this.getPeerId();
        final Object other$peerId = other.getPeerId();
        if (this$peerId == null ? other$peerId != null : !this$peerId.equals(other$peerId)) return false;

        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UpcomingInterviewsDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $interviewTypeId = this.getInterviewTypeId();
        result = result * PRIME + ($interviewTypeId == null ? 43 : $interviewTypeId.hashCode());

        final Object $interviewLevelId = this.getInterviewLevelId();
        result = result * PRIME + ($interviewLevelId == null ? 43 : $interviewLevelId.hashCode());
        final Object $interviewSlotId = this.getInterviewSlotId();
        result = result * PRIME + ($interviewSlotId == null ? 43 : $interviewSlotId.hashCode());
        final Object $interviewDateId = this.getInterviewDateId();
        result = result * PRIME + ($interviewDateId == null ? 43 : $interviewDateId.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $peerId = this.getPeerId();
        result = result * PRIME + ($peerId == null ? 43 : $peerId.hashCode());

        return result;
    }


    public String toString() {
        return "UpcomingInterviewsDto(id=" + this.getId() + ", interviewTypeId=" + this.getInterviewTypeId() + ", interviewLevelId=" + this.getInterviewLevelId() + ", interviewSlotId=" + this.getInterviewSlotId() + ", interviewDateId=" + this.getInterviewDateId() + ", userId=" + this.getUserId() + ", peerId=" + this.getPeerId() + ")";
    }
}