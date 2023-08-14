package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameType;

/**
 * json file used to join the server
 *
 * @param githubUsername the github username the server will use to keep track of who you are
 * @param gameType the type of game you are playing
 */
public record JoinJson(
    @JsonProperty("name") String githubUsername,
    @JsonProperty("game-type") GameType gameType) {
}
