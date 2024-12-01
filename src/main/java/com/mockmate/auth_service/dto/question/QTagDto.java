package com.mockmate.auth_service.dto.question;

public class QTagDto {
    private Long tagId;
    private String questionTagText;

    public QTagDto(Long tagId, String questionTagText) {
        this.tagId = tagId;
        this.questionTagText = questionTagText;
    }

    public QTagDto() {
    }

    public Long getTagId() {
        return this.tagId;
    }

    public String getQuestionTagText() {
        return this.questionTagText;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public void setQuestionTagText(String questionTagText) {
        this.questionTagText = questionTagText;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QTagDto)) return false;
        final QTagDto other = (QTagDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$tagId = this.getTagId();
        final Object other$tagId = other.getTagId();
        if (this$tagId == null ? other$tagId != null : !this$tagId.equals(other$tagId)) return false;
        final Object this$questionTagText = this.getQuestionTagText();
        final Object other$questionTagText = other.getQuestionTagText();
        if (this$questionTagText == null ? other$questionTagText != null : !this$questionTagText.equals(other$questionTagText))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof QTagDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $tagId = this.getTagId();
        result = result * PRIME + ($tagId == null ? 43 : $tagId.hashCode());
        final Object $questionTagText = this.getQuestionTagText();
        result = result * PRIME + ($questionTagText == null ? 43 : $questionTagText.hashCode());
        return result;
    }

    public String toString() {
        return "QTagDto(tagId=" + this.getTagId() + ", questionTagText=" + this.getQuestionTagText() + ")";
    }
}
