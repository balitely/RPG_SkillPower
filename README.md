Similar to the RPGMana,This mod adds a bunch of attributes related to SkillPower, including "SkillPower Regen" , "SkillPower Cost" and "Sp regen per attack".

Compared to RPGMana,This mod doesn't have much content, with its core mechanic being that players can restore SP equal to the "Sp regen per attack" attribute value when attacking.

For Spell Engine devs: In order to give your spell a dedicated SP cost, designate a value in the config in "rpgsp/server.json5" and remember to assign a cost item to your Spell.
If you want your spells to consume only SP or Mana, add them to the "blacklist_spell_casting_regex" array in the corresponding server.json5 configuration file.

I developed this mod for my modpack based on the code from RPGMana (with credit to cleanrooster for sharing the source code). 
It has been tested to work alongside RPGMana, and I plan to expand on it with more features in future updates.hope this mod is helpful to you.

Starting from version 1.0.1, this mod requires RPGMana as a dependency and allows creating spells that consume both Mana and SP simultaneously.
