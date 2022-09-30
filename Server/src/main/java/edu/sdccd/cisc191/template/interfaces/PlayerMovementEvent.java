package edu.sdccd.cisc191.template.interfaces;

import edu.sdccd.cisc191.template.Tile;

public interface PlayerMovementEvent {

    void onPlayerMove(Tile currentTile, Tile previousTile);


}
