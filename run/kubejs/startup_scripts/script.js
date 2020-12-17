// priority: 0

/******************************************************************************
 * Here we have prepared a sample script for you.
 * 
 * As an example we add a seed,
 * which drops when harvesting the plant cobblestone.
 * Secondly, we have added a potato whose plant drops itself when
 * harvested and is edible.
 * 
 * If something is still unclear, just write us an issue on Github.
 *****************************************************************************/

// The first thing we do is check if our mod is loaded. You can
// also leave that out if you want.
if (mod.isLoaded('owncrops')) {												

  // This waits for the crop register event.
  // In these, all crops and foods are later described and registered.
  events.listen('owncrops.crop.registry', event => {

    // This creates a new item called "dark_seed"
    // the asset loader looks for "dark_seed.png" in 
    // "*INSTANCE*/kubejs/assets/owncrops/textures/item/"
    // All things are defined via builders. However, a final command is not
    // necessary. All builders will be executed automatically after the
    // script is finished.
    event.create('dark_seed')
        // ".plantable(<dropOnMature>)"
        // This makes "dark_seed" as a new crop, which also
        // drops cobblestone, when fully grown
        .plantable('minecraft:cobblestone')
        // ".addGrowthStage(<plantHeight>)" adds a new growthstage to the
        // crop which has a height of plantHeight bits.
        // A block consists of 16x16x16 bits.
        // A crop needs to be "plantable" to obtain growthstages
        .addGrowthStage(2)
        // so this crop has 2 growthstages in total.
        // Textures a named "dark_seed_X.png", where X is the stage
        // witch starts with 0
        .addGrowthStage(16);
        // There could be problems with more than 8 growthstages.
        // This has not been tested yet.
        // The textures are located at
        // "*INSTANCE*/kubejs/assets/owncrops/textures/block/"

    // Now our first plant is ready.

    // Next we want to add a potato. This consists of only one item.
    // This can be planted as well as eaten.
    //
    // This creates a new item called "dark_potato"
    event.create('dark_potato')
        // This makes "dark_potato" a new crop, which only drops
        // itself, when harvested. If you do not specify a parameters,
        // the crop drops itself as the end product.										  
        .plantable()																		    
        // ".edible(<hunger>, <saturation>)" turns "dark_potato" into a
        // food source with 2 hunger regeneration and 3 saturation
        .edible(2,3)
        // This time the crop has 4 growthstages
        .addGrowthStage(2)
        .addGrowthStage(4)
        .addGrowthStage(6)
        .addGrowthStage(8)
        // Now, this allows the fully grown crop to be
        // harvested by right-clicking it.
        // For compatibility reasons, you must specify this separately.
        // If you omit this, then you have to break down the plant as in vanilla.
        .harvestOnUse();
      
      // Now we can close the function block '}' and the event.listen function ')'.
  });
} // close if (mod.isLoaded('owncrops'))
