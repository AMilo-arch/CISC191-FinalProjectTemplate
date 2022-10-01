package edu.sdccd.cisc191.template.interfaces;

import edu.sdccd.cisc191.template.Tile;

/**
 * Event is triggered when the player moves, and provides the tile that they moved to, and the tile they moved from.
 */

public interface PlayerMovementEvent {
    void onPlayerMove(Tile currentTile, Tile previousTile);

}
