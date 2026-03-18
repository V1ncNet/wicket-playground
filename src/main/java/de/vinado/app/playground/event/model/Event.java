package de.vinado.app.playground.event.model;

import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.UUID;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ToString(onlyExplicitlyIncluded = true)
public class Event implements Persistable<UUID>, Comparable<Event>, Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @NonNull
    @ToString.Include
    private UUID id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "location")
    private String location;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "startInstant", column = @Column(name = "start_date_time", nullable = false)),
        @AttributeOverride(name = "startZoneId", column = @Column(name = "start_zone_id", nullable = false)),
        @AttributeOverride(name = "endInstant", column = @Column(name = "end_date_time", nullable = false)),
        @AttributeOverride(name = "endZoneId", column = @Column(name = "end_zone_id", nullable = false)),
    })
    @NonNull
    private Interval interval;

    @Transient
    private boolean isNew;

    public Event(@NonNull UUID id, @NonNull Interval interval) {
        this.id = id;
        this.interval = interval;
        this.isNew = true;
    }

    @PostPersist
    protected void postPersist() {
        this.isNew = false;
    }

    @Override
    public int compareTo(Event that) {
        return Comparator.comparing(Event::getInterval)
            .compare(this, that);
    }

    // @checkstyle:off: NeedBraces
    @Override
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (this == obj) return true;
        if (!getClass().equals(ProxyUtils.getUserClass(obj))) return false;
        Event that = (Event) obj;
        return this.getId().equals(that.getId());
    }
    // @checkstyle:on: NeedBraces

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += getId().hashCode() * 31;
        return hashCode;
    }


    @Embeddable
    public record Interval(
        @NonNull Instant startInstant,
        @NonNull ZoneId startZoneId,
        @NonNull Instant endInstant,
        @NonNull ZoneId endZoneId
    ) implements Comparable<Interval>, Serializable {

        public Interval {
            ZonedDateTime start = ZonedDateTime.ofInstant(startInstant, startZoneId);
            ZonedDateTime end = ZonedDateTime.ofInstant(endInstant, endZoneId);
            Assert.isTrue(!start.isAfter(end), "start must be before end");
        }

        public ZonedDateTime start() {
            return ZonedDateTime.ofInstant(startInstant, startZoneId);
        }

        public ZonedDateTime end() {
            return ZonedDateTime.ofInstant(endInstant, endZoneId);
        }

        @Override
        public int compareTo(Interval that) {
            return Comparator.comparing(Interval::start)
                .thenComparing(Interval::end)
                .compare(this, that);
        }
    }
}
