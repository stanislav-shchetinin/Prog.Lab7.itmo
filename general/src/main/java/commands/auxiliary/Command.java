package commands.auxiliary;

import response.Response;

import java.io.Serializable;

public interface Command extends Description, Serializable {
    Response execute();
}
