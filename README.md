# DynamicFly
The modern solution for player flight management.

Conveniently and unobtrusively toggles players' fly in areas where they do and don't have permission.

## Configuration
There is one configuration file found in the plugins folder. This file contains options for different plugin checks.

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

## API 
Are you a developer that wants to add support for their own claim plugin, or add some extra effects to this one? Lucky for you, we have an API!

Events:
```java
ClaimChangeEvent
ClaimEnterEvent
ClaimLeaveEvent
ClaimTransitionEvent
```
Each event stores the player, to and from locations, and which claim plugin is involved. Note that more than one event may fire at the same time if two different plugins are involved.

Hooking in your own claim plugin:
1. Create a class that extends PluginHook. Your IDE should tell you to implement some methods.
2. Implement the required methods. For the `canFlyHere` method, you should use the `FlightPermission` enums to help you. Check out our implementations for examples.
3. In your main class, register your hook with this plugin: ```PluginHook.registerHook(new yourPluginHook());```

And there you go! If you want it to stop watching for claims, you can always unregister the hook.
## Support
You can find Indigo Studios and this plugin's developers on our Discord @ https://discord.gg/zVrtQvV
