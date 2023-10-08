# Jackson record deserialization not yet supported, until Jackson lib fix it
```
public record MessageInputEventVo(
    @JsonUnwrapped
    MessageEventId id,
    String message,
    LocalDateTime createdAt
) { }
```
com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot define Creator property "id" as `@JsonUnwrapped`: combination not yet supported

### Solution:
```
public record MessageInputEventVo(
    MessageEventId id,
    String message,
    LocalDateTime createdAt
) {
    @JsonCreator
    protected MessageInputEventVo(
        @JsonProperty("id")
        Long id,
        @JsonProperty("message")
        String message,
        @JsonProperty("createdAt")
        LocalDateTime createdAt
    ) {
        this(
            new MessageEventId(id),
            message,
            createdAt
        );
    }
}
```
But, when serialized it will give:
```
{
    "id": {
        "id": 1
    },
    "message": "Test message",
    "createdAt": "2023-10-07T10:27:05.652537"
}
```
The id is not unwrapped