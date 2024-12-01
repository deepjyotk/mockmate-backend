package com.mockmate.auth_service.dto.interview;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SlotDto {

    @JsonProperty("slot_id")
    private Long slotId;

    @JsonProperty("slot_date_time")
    private String slotDateTime;

    // Constructors
    public SlotDto() {
    }

    public SlotDto(Long slotId, String slotDateTime) {
        this.slotId = slotId;
        this.slotDateTime = slotDateTime;
    }

    // Getters and Setters
    public Long getSlotId() {
        return slotId;
    }

    public String getSlotDateTime() {
        return slotDateTime;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public void setSlotDateTime(String slotDateTime) {
        this.slotDateTime = slotDateTime;
    }
}
