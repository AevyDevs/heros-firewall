package net.herospvp.firewall.database;

import lombok.Getter;

import java.util.TreeSet;
import java.util.UUID;

public class Storage {

    @Getter
    private static final TreeSet<String> onlinePlayers = new TreeSet<>();

    @Getter
    private static final TreeSet<String> grantedIPS = new TreeSet<>();

    @Getter
    private static final TreeSet<UUID> onlineUUIDs = new TreeSet<>();

}
