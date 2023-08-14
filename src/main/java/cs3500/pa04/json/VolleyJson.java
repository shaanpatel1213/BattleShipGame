package cs3500.pa04.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * used to represent a List of shots in json
 *
 * @param shots is a series of shots that are the arguments of this json
 *
 */
public record VolleyJson(@JsonProperty("coordinates") List<Coord> shots) {

  /**
   * is used by jackson to translate the json to an object and vice versa
   *
   * @return the list of shots needed to create the json
   */
  public List<Coord> getShots() {
    return shots;
  }

}
