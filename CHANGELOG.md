[2.1.14]

**Added**
* Dishes eaten from a Table Set now make you Well Served for longer: 1 extra minute for the dish and 1 more for each Napkin, Glass, Wine Glass and filled drink on the table setting
* New item tag `candlelight:served_dishes`: dishes in it get the Table Set bonus, add dishes from other mods to it
* Chef's Outfit set bonus: wearing all four pieces gives a 25% chance of an extra portion from the Cooking Pan and Cooking Pot
* Suit and Evening Dress set bonus: wearing the full outfit makes dishes eaten from a Table Set more filling and counts as an extra table setting
* Table Set now shows an info tooltip when you look at it: the dish, the table setting and what you can still add for a longer Well Served
* Table Signs can now be written on: right-click in the air to write a menu or a note, place it, and everyone looking at it sees the text. The text stays on the sign when you break it
* The two creative tabs are now one Candlelight tab with Food & Dining, Stoves, Sinks & Counters and Furniture as side tabs on the left
* Pot Roast: cooked in the Cooking Pot from Beef, Potato, Carrot and Onion. It replaces Khinkali
* Config: effects (Well Served, Refreshed, Gold Ring, Banner), misc settings (Table Set bonuses, dinner menu, villager dinner guests, Chef's Outfit, info tooltips) and the food values of every dish. On Fabric through Mod Menu, on NeoForge in the Mods screen. Food values need a restart
* VanillaBlend: an optional built-in resource pack with muted, vanilla-friendly colors for plates, bowls, dishes, glasses, clothing and Mob Effect icons. Enable it in the Resource Packs menu
* The Table with tablecloth can be dyed with any dye
* Tablecloths: put a carpet on a wooden table to cover it, dye it to change the color, sneak and right-click to take it off
* Sofas can be dyed with any dye
* Side Table: put any item on it and stack up to 4 of the same (2 books), or place a Lamp, Lantern or Soul Lantern that you can turn on and off with right-click. Sneak and right-click to take everything off. What is on it now drops when the Side Table is broken
* Fireflies gather around lit Lamps and Side Tables with a light at night
* Dinner menu: eat a starter, a main course and a dessert from a Table Set one after another to get a much longer Well Served and Refreshed. Dishes are sorted with the new item tags `candlelight:starters`, `candlelight:main_courses` and `candlelight:desserts`
* A lit candle, lantern or lamp next to a Table Set counts as an extra table setting. Add more lights with the block tag `candlelight:table_lights`
* Wine from Vinery can be poured into the Wine Glass of a Table Set, beer from Brewery, grape juice and potions into the Glass. They give the same effects as drinking them normally. Add more drinks with the item tags `candlelight:glass_drinks` and `candlelight:wine_glass_drinks`
* Dishes, desserts, drinks and lanterns from Farm & Charm, Bakery, Brewery, Vinery, Meadow, Beachparty, Alpine Whispers and Lili's Lucky Lures count for the dinner menu, the glasses and the table lights when those mods are installed
* Villagers come to dinner: in the evening after work they may walk to a Table Set with a dish on it and eat it. They then give 10% off all their trades for one day. Nitwits eat too, but don't give anything back
* Hold right-click with a Napkin to dab your mouth (suggested by CR505). Works with the Napkin on a Table Set too: it is taken while you hold and put back when you let go
* Support for Accessories, Curios and Trinkets: the Gold Ring goes into a ring slot, the Necktie into the necklace slot and the Flower Crown into the hat slot

**Changed**
* Pasta with Bolognese and Chicken Alfredo now have their own food values instead of the ones of the Harvest Plate
* Cooking Pan and Cooking Pot now share their cooking logic with the Farm & Charm Roaster and Cooking Pot
* Now requires Farm & Charm 1.1.27 or newer
* The Gold Ring is now worn in a ring slot of Accessories, Curios or Trinkets instead of the chest slot, or held in the off hand

**Fixed**
* Eating from a Table Set now also gives the effects of the ingredients, like eating the dish from your hand
* Table Set no longer crashes when used after reloading the world, and drinks in its glasses keep their effect after reloading
* Cabinets and drawers no longer stay open when a player leaves while looking inside
* Hanging Lamps now have the right hitbox
* Glasses, Wine Glasses and Napkins on a Table Set now have their own hitbox
* Drinks in the glasses of a Table Set no longer flicker
* Glass and Wine Glass of a Table Set no longer share one drink, both keep their own
* Waterlogged tables now let water flow again
* Big Tables have a flat hitbox again, items and blocks can be placed on them
* Stoves no longer give off light while they are off
* Necktie now renders correctly on Fabric and NeoForge
* Removed leftover translations and models of removed items, and fixed recipe advancements that pointed to missing recipes

***

[2.1.13]

**Fixed**
* Ring not being equippable in the off-hand ring slot (thanks to Fyoncle)
* Corrected and updated the Italian translation (thanks to Serenautilus)
* Cooking Pan generating an excessive number of block states, causing world tools like WorldEdit to hang

***

[2.1.12]

**Fixed**
* Small Painting crash when placing the block
* Hat rendering on Fabric causing textures to appear on other body parts

***

[2.1.11]

**Fixed**
* Corrected the item tag from c:strawberry to the proper tag reference

***

[2.1.10]

**Changed**
* Adapted RoseBlock to the updated BonemealableFlowerBlock constructor to prevent a startup crash (thanks to jaoreir)

***

[2.1.9]

**Fixed**
* Fixed a crash that could occur when equipping the Cooking Hat, Necktie, or Rose Crown (thanks to jaoreir)

**Changed**
* Updated Ru_Ru (thanks to tefnya)

***

[2.1.8]

**Fixed**
* Containers such as bottles, bowls and buckets not being returned after cooking
* Clothing being huge when placed inside AlpineWhispers / Meadows wardrobe

**Changed**
* Slightly adjusted Heart texture
* Renamed "Hearth" to "Heart"

**Added**
* You can now write on the Heart Block

***

[2.1.7]

**Fixed**
* A typo in the table_set blockstate that caused excessive log spam due to invalid model references

*** 

[2.1.6]

**Requires Farm & Charm 1.1.15+**

**Added**
* Added two new food effects: Refreshed and Well Served
* Refreshed grants bonus harvest drops for a limited number of crops
* Well Served prevents hunger from dropping below a minimum level

**Changed**
* Removed BakeryIdentifier utility and moved identifier helper directly into the Bakery class

*** 

[2.1.5]

**Fixed**
* Typewriter being offset when placed down
* TableSet not rendering any Food placed on it

***

[2.1.4]

**Fixed**
* Letter GUI background not rendering due to missing shader/texture bind
* Ensured Letter GUI opens correctly on NeoForge
* `ClosedLetterItem` restores that payload when opened, giving back the original written note

**Changed**
* Moved `FlammableBlockRegistry.init()` into commonSetup enqueueWork to ensure safe registration

***

[2.1.3]

**Fixed** 
* correct CookingPan BE type to match CookingPanBlockEntity

***

[2.1.2]

**Fixed**
* `LargeCookingPot` now writes all ingredient effects onto output items (includes base potion effects and custom potion effects)

**Changed**
* Typewriter item texture now matches the new model and functionality
* Typewriter recipe now uses planks to better reflect its new appearance
* Typewriter Copper has been renamed to Typewriter Gold

***

[2.1.1]

**Fixed**
* Added missing FlowerCrown handling and prevented client crash when equipping
* Unified dyeable armor rendering between Fabric and NeoForge
* Corrected resource locations for all armor textures to avoid missing texture warnings
* Fixed distorted rendering on custom models (Dress, Suit) by ensuring proper layer definitions
* Ensured Gold Ring can be equipped without rendering any armor texture
* Corrected mismatched sound subtitle keys (`candlelight.sound.*` → `sound.candlelight.*`) 
* Crash when placing Candlelight food blocks (lasagne, beef wellington, pork ribs, salad, tomato mozzarella) due to wrong block entity mapping.
  → All food blocks now use `CEffectFoodBlockEntity` instead of Farm & Charm’s `EffectFoodBlockEntity`.

***

[2.1.0]

**Welcome to 1.21.1**

*** 

[2.0.5]

**Fixed**
* Normal letter triggering Heart-Burst

*** 

[2.0.5]

**Changed**
* Letters now show not only who they're for, but also who they're from

**Added**
* Japanese translation _(Thanks to PExPE3)_
* Holding a closed Love Letter now emits trailing heart particles while walking – opening it triggers a heart burst

**Fixed**
* Baby Zombies wont spawn with oversized Cooking Clothing anymore
* Fixed letter crafting consuming full stacks of envelopes and note paper instead of just one of each

***

[2.0.4]

**Added**
* Zombies have a really low Chance to spawn wearing a Cooking Hat - Zombies wearing a Cooking Hat are immune to sunlight
* You can now fill Table Set Glasses & Wine Glasses with Potions and Wines
* Gloche can now be removed by Shift Right-Clicking
* Suit & Dress can now be colored again
* Added a small tweak to the bamboo stove - A Bamboo Stove? What an odd idea!

**Changed**
* Adjusted all Recipe .json the the new format

**Fixed**
* StoveBlockEntity now properly processes EffectBlockItem and applies stored effects to the crafted result
* CandlelightHatItem mixin (forge only) now correctly handles tie and crown models via unified getGenericArmorModel override