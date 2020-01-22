# DynamicFly
The modern solution for player flight management.

Conveniently and unobtrusively toggles players' fly in areas where they do and don't have permission.

## Configuration
There is one primary configuration file found in the plugins folder. This file contains every configurable option in the plugin including items and language options.

Configuration files:
- [config.yml](https://github.com/Official-Indigo-Studios/DynamicFly/blob/master/src/main/resources/config.yml "Config.yml")

## Commands and Permissions
Various permissions allow for very controlled flight. There are many configurations to find the right one for you!

Permission | Description
--- | ---
`dynamicfly.command.fly` | Main command for the plugin; gives basic access to `/fly`
`dynamicfly.command.fly.others` | Allows user to toggle another player's flight via `/fly on/off PlayerName`
`dynamicfly.command.fly.reload` | Allows a user to reload this plugin's config file via `/fly reload`
`dynamicfly.type.global` | Gives a player access to fly anywhere regardless of claim
`dynamicfly.type.claims.trusted` | Gives a player access to fly in claims where they are trusted/a member of
`dynamicfly.type.claims` | Gives a player access to fly in claims that they own
`dynamicfly.safelogin` | Automatically toggles a player's fly status when they log in if they are in midair. If they don't have flight permissions there, they will receive slow fall instead

## Tips
Players can still fly without having access to the `/fly` command themselves, through clever usage of outside parties using it instead.
This means that you can make *anyone* fly in a region by running `/fly on PlayerName` when they enter, and `/fly off PlayerName` when they leave.

This plugin adds a `flight` flag to WorldGuard. It defaults to `allow`, which means normal permissions apply. However, if set to `deny`, no one will be able to fly there, even people with the global flight permission! This is good for PvP arenas, where players with flight have a significant advantage over others.

## Support
You can find Indigo Studios and this plugin's developers on our Discord @ https://discord.gg/zVrtQvV
