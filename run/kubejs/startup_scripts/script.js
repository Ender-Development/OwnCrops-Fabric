// priority: 0

events.listen('owncrops.crop.registry', event => {
    event.create("dark_crop")
            .edible(2, 4)

    event.create("dark_seed")
            .plantable("owncrops:dark_crop")
            .addGrowthStage(2)
            .addGrowthStage(16);
})