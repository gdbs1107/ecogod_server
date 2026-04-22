package or.ecogad.ecogad.infra.persistence.notice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import or.ecogad.ecogad.infra.persistence.base.BaseJpaEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeJpaEntity extends BaseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean published;

    private LocalDateTime publishedAt;

    @Builder(toBuilder = true)
    private NoticeJpaEntity(Long id, String title, String content, boolean published, LocalDateTime publishedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.published = published;
        this.publishedAt = publishedAt;
    }
}
