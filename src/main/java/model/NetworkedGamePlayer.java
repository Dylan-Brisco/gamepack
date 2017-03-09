package model;

import java.util.Random;

public abstract class NetworkedGamePlayer<M extends Move> implements GamePlayer<M> {
    protected int uid;

    public NetworkedGamePlayer() {
        uid = new Random().nextInt(Integer.MAX_VALUE);
    }
}
