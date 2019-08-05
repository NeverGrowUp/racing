package com.github.hornta.race.commands;

import com.github.hornta.ICommandHandler;
import com.github.hornta.race.RacingManager;
import com.github.hornta.race.Util;
import com.github.hornta.race.enums.RaceState;
import com.github.hornta.race.enums.RaceType;
import com.github.hornta.race.enums.RaceVersion;
import com.github.hornta.race.message.MessageKey;
import com.github.hornta.race.message.MessageManager;
import com.github.hornta.race.objects.Race;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

public class CommandCreateRace extends RacingCommand implements ICommandHandler {
  public CommandCreateRace(RacingManager racingManager) {
    super(racingManager);
  }

  @Override
  public void handle(CommandSender commandSender, String[] args) {
    Player player = (Player) commandSender;

    Race race = new Race(
      UUID.randomUUID(),
      RaceVersion.getLast(),
      args[0],
      Util.centerOnBlockHorizontally(player.getLocation()),
      RaceState.UNDER_CONSTRUCTION,
      Instant.now(),
      Collections.emptyList(),
      Collections.emptyList(),
      RaceType.PLAYER,
      null,
      0,
      0.2F,
      new HashSet<>(),
      new HashSet<>());

    racingManager.createRace(race, () -> {
      MessageManager.setValue("race_name", race.getName());
      MessageManager.sendMessage(player, MessageKey.CREATE_RACE_SUCCESS);
    });
  }
}
