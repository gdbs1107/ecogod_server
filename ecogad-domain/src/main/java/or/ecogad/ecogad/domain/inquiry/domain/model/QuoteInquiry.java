package or.ecogad.ecogad.domain.inquiry.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuoteInquiry {

    private final Long id;
    private final String name;
    private final String companyName;
    private final String phone;
    private final String email;
    private final String message;
    private final boolean privacyAgreed;
    private final InquiryStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private QuoteInquiry(
            Long id,
            String name,
            String companyName,
            String phone,
            String email,
            String message,
            boolean privacyAgreed,
            InquiryStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.privacyAgreed = privacyAgreed;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static QuoteInquiry create(
            String name,
            String companyName,
            String phone,
            String email,
            String message,
            boolean privacyAgreed
    ) {
        return new QuoteInquiry(
                null,
                name,
                companyName,
                phone,
                email,
                message,
                privacyAgreed,
                InquiryStatus.RECEIVED,
                null,
                null
        );
    }

    public static QuoteInquiry restore(
            Long id,
            String name,
            String companyName,
            String phone,
            String email,
            String message,
            boolean privacyAgreed,
            InquiryStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new QuoteInquiry(id, name, companyName, phone, email, message, privacyAgreed, status, createdAt, updatedAt);
    }
}
