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