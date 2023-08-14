package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Ship;
import java.util.List;

/**
 * is used to make a json to talk to the server with a list of ships
 *
 * @param ships a list of ships that is given to the server for setup
 */
public record FleetJson(@JsonProperty("fleet") List<Ship> ships) {
}
