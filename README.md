# DarkMenus
A way to create menus to hook into the DarkCitizens Plugin.
At the moment only the basics are implemented, if you have a feature request please contact me, or open an issue.
Same goes for issues, questions or errors.

## Understanding how it works
A menu consist of different options, you define the options, and the menu yourself in the config file.

### But how do I define an option?
All the options have to be defined in the `options` section of the config.
An option may look something like this:
```yaml
giveStick:
    name: Give a stick
    isGuiOption: true
    isPricedOption: false
    isCommandOption: true
    icon: STICK
    command: /give %player_name% stick
```
> Note:  
> At the moment your option has to be a GUI option, and a command option to be used

With different Option types come different property that have to be defined:

> A GUI option requires the icon tag  
> A priced option requires the price tag  
> A command option requires the command tag  

### How do I create commands custom to every player
For this purpose we use the PlaceholderAPI to replace placeholders
in our command, just put the placeholder you want into the command and it will be replaced with its value.
In fact we already did that, take a look at the `command` tags in the options we defined above:
`/give %player_name% stick 10`, the `%player_name%` is a placeholder that will be replaced. To use some placeholders
you have to download them from the Ecloud first, but take a look at the PAPI documentation for that.

### Now I have my options, what do I do with them?
You put them into your menus, simple as that. To do that you have to
create a new menu in the `menus` section of your config.
The same way as options have different types the menu can different types. But let's take a look
at a simple menu first:

```yaml
someMenu:
    name: Some Menu
    isGuiMenu: true
    specificTo: NOTHING
    options: [giveStick]
```

> Note:  
> At the moment only GUI menus are available

You can see the `specificTo` tag, if this is set to `NOTHING` everyone can access this menu at every time.
Another option is `JOB` if it is set on a menu the menu will only be available to a job that was specified.
The last option is `GROUP` if this is set, the menu will be only available to users that are in this group
This are all available specifics:
```
NOTHING
JOB
GROUP
```

> Note:  
> If specificTo is set to JOB you have to set the job property to the jobs name
> If specificTo is set to GROUP you have to set the group property to the groups name

### Great now I have a working menu but how do I use it?
To open a menu in-game just use the: `/darkmenus <menu name>` command

### What happens if I have more then one menu with the same name?
Really good question, to answer that let's quickly define a config:
```yaml
options:
    giveStick:
        name: Give a stick
        isGuiOption: true
        isPricedOption: false
        isCommandOption: true
        icon: STICK
        command: /give %player_name% stick 1
    giveExpensiveStick:
        name: Give an expansive stick
        isGuiOption: true
        isPricedOption: true
        isCommandOption: true
        price: 100
        icon: STICK
        command: /give %player_name% stick 1
    giveTenSticks:
        name: Give a stick
        isGuiOption: true
        isPricedOption: false
        isCommandOption: true
        icon: STICK
        command: /give %player_name% stick 10

menus:
    someMenu:
        name: Some Menu
        isGuiMenu: true
        specificTo: NOTHING
        options: [giveStick, giveExpensiveStick]

    someExclusiveMenu:
        name: Some Menu
        isGuiMenu: true
        specificTo: NOTHING
        job: Stick Dealer
        options: [giveStick, giveExpensiveStick, giveTenSticks]
```

In this case the menu defined first will be called, so on using `/darkmenus Some Menu` a menu with the options
giveStick and giveExpensive stick will be shown. This behavior is different for two menus with the same name but different things they are specific to, let me show you an example config again:
```yaml
options:
    giveStick:
        name: Give a stick
        isGuiOption: true
        isPricedOption: false
        isCommandOption: true
        icon: STICK
        command: /give %player_name% stick 1
    giveExpensiveStick:
        name: Give an expansive stick
        isGuiOption: true
        isPricedOption: true
        isCommandOption: true
        price: 100
        icon: STICK
        command: /give %player_name% stick 1
    giveTenSticks:
        name: Give a stick
        isGuiOption: true
        isPricedOption: false
        isCommandOption: true
        icon: STICK
        command: /give %player_name% stick 10

menus:
    someMenu:
        name: Some Menu
        isGuiMenu: true
        specificTo: JOB
        job: Standard stick dealer
        options: [giveStick, giveExpensiveStick]

    someExclusiveMenu:
        name: Some Menu
        isGuiMenu: true
        specificTo: JOB
        job: Chad stick dealer
        options: [giveStick, giveExpensiveStick, giveTenSticks]
```

In this case if the player has the job `Standard stick dealer` he will see a menu with giveStick and giveExpensiveStick,
but if he has the job `Chad stick dealer` he will see a menu with giveStick, giveExpensiveStick and giveTenSticks