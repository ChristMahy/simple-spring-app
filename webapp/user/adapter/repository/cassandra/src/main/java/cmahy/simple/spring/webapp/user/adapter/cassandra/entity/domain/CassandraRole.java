package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain;

import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.List;
import java.util.UUID;

@Table("role")
public class CassandraRole {

    @PrimaryKey
    protected UUID id;
    protected String name;

    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.UUID)
    protected List<RightId> cassandraRightIds;

    public UUID getId() {
        return id;
    }

    public CassandraRole setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CassandraRole setName(String name) {
        this.name = name;
        return this;
    }

    public List<RightId> getCassandraRightIds() {
        return cassandraRightIds;
    }

    public CassandraRole setCassandraRightIds(List<RightId> cassandraRightIds) {
        this.cassandraRightIds = cassandraRightIds;

        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", getId())
            .append("name", getName())
            .toString();
    }
}
