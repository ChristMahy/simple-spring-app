package cmahy.simple.spring.webapp.resource.impl.adapter.actuator;

import cmahy.simple.spring.common.entity.id.EntityId;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Component
@Endpoint(id = "notes")
public class NoteCustomEndPoint {

    private final Set<Note> notes = new HashSet<>();

    @PostConstruct
    public void addSomeNotes() {

        this.notes.add(new Note(
            UUID.randomUUID(),
            "A title",
            "My first note"
        ));

    }

    @WriteOperation
    @Validated
    public Collection<NoteOutputVo> addNote(@Valid NoteInputVo input) {

        this.notes.add(
            new Note(
                UUID.randomUUID(),
                input.title(),
                input.description()
            )
        );

        return notes();

    }

    @DeleteOperation
    @Validated
    public Collection<NoteOutputVo> deleteNote(@NotNull NoteId input) {

        this.notes.removeIf(note -> Objects.equals(note.id(), input.value()));

        return notes();

    }

    @ReadOperation
    public Collection<NoteOutputVo> notes() {

        return notes.stream()
            .sorted((n1, n2) -> StringUtils.compare(n1.id.toString(), n2.id.toString()))
            .map(note -> new NoteOutputVo(
                new NoteId(note.id()),
                note.title(),
                note.description()
            ))
            .toList();

    }

    public record NoteInputVo(
        @NotNull @NotBlank String title,
        @NotNull @NotBlank String description
    ) {

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("title", title())
                .append("description", description())
                .toString();
        }

    }

    public record NoteOutputVo(
        NoteId id,
        String title,
        String description
    ) {

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id())
                .append("title", title())
                .append("description", description())
                .toString();
        }

    }

    public record NoteId(UUID value) implements EntityId<UUID> {

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("value", value())
                .toString();
        }

    }

    private record Note(
        UUID id,
        String title,
        String description
    ) {

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id())
                .append("title", title())
                .append("description", description())
                .toString();
        }

    }
}
