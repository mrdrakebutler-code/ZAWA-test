package org.zawamod.zawa.world.entity;
public interface VenomousEntity {
    boolean hasVenom();
    void setMilked(int ticks);
    boolean readyToBeMilked();
}
