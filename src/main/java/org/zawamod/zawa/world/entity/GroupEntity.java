package org.zawamod.zawa.world.entity;

import net.minecraft.world.entity.Mob;

/** Shared group/leader contract used by ZAWA schooling and herd species. */
public interface GroupEntity<T extends Mob> {
    T getGroupLeader();
    void setGroupLeader(T leader);
    int getMaxGroupSize();
    int getGroupSize();
    void setGroupSize(int size);
    default void setGroupLeader(Mob leader) { setGroupLeader((T) leader); }
    default Mob getGroupLeaderMob() { return getGroupLeader(); }
}
