package response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class Response implements Serializable {
    private Status status;
    private String message;
    public Response(Status status){
        this.status = status;
    }
}
