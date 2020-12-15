// priority: 0

events.listen('owncrops.crop.registry', event => {
    event.create("rice")
            .edible(10, 3)
            .plantable()
            .addGrowthStage(2)
            .addGrowthStage(16);
    event.create("tomato")
            .edible(10, 3);
    event.create("tomato_seeds")
            .plantable()
            .addGrowthStage(2)
            .addGrowthStage(16);
})