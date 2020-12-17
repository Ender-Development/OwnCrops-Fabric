// priority: 0

if (mod.isLoaded('owncrops')) {												// checks if the mod is loaded

  events.listen('owncrops.crop.registry', event => {	// waits for crop register event

  event.create('dark_seed')													// creates a new item called "dark_seed" -> looks for "dark_seed.png" in "*INSTANCE*/kubejs/assets/owncrops/item/"
  .plantable('minecraft:cobblestone')							// makes "dark_seed" a new crop, which also drops cobblestone, when fully grown
  .addGrowthStage(2)															// ".addGrowthStage(x)" adds a new Growthstage to the crop which has a height of x pixel | crop needs to be "plantable" to obtain Growthstages
  .addGrowthStage(16)															// so this crop has 2 Growthstages in total | textures a named "dark_seed_X.png", where X starts with 0
  // the maximum amount of Growthstages is 8 | the textures are located at "*INSTANCE*/kubejs/assets/owncrops/block/"

  event.create('dark_potato')												// creates a new item called "dark_potato"
  .plantable()																		// makes "dark_potato" a new crop, which only drops itself, when harvested
  .edible(2,3)																		// ".edible(x,y)" turns "dark_potato" into a food source with x hunger and y saturation
  .addGrowthStage(2)															// this time the crop has 4 Growthstages
  .addGrowthStage(4)
  .addGrowthStage(6)
  .addGrowthStage(8)
  .harvestOnUse()																	// allows the fully grown crop to be harvested by right-clicking it
  })
}
