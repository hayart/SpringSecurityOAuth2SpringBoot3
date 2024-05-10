package am.developer.outh.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private Integer id;

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
