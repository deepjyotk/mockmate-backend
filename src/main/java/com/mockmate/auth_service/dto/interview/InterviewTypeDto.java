package com.mockmate.auth_service.dto.interview;

import java.util.List;

public class InterviewTypeDto {
    private Long id;
    private String type;
    private String description;
    private int durationMinutes;
    private List<String> time;

    public InterviewTypeDto() {
    }

    public InterviewTypeDto(Long id, String type, String description, int durationMinutes, List<String> time) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.time = time;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof InterviewTypeDto)) return false;
        final InterviewTypeDto other = (InterviewTypeDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        if (this.getDurationMinutes() != other.getDurationMinutes()) return false;
        final Object this$time = this.getTime();
        final Object other$time = other.getTime();
        if (this$time == null ? other$time != null : !this$time.equals(other$time)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof InterviewTypeDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        result = result * PRIME + this.getDurationMinutes();
        final Object $time = this.getTime();
        result = result * PRIME + ($time == null ? 43 : $time.hashCode());
        return result;
    }

    public String toString() {
        return "InterviewTypeDto(id=" + this.getId() + ", type=" + this.getType() + ", description=" + this.getDescription() + ", durationMinutes=" + this.getDurationMinutes() + ", time=" + this.getTime() + ")";
    }
}
