package io.klebe.owncrops.kubejs

import dev.latvian.kubejs.KubeJSObjects
import dev.latvian.kubejs.event.EventJS
import io.klebe.owncrops.OwnCrops

object CropRegistryEventJS : EventJS() {
    fun create(id: String): CropBuilder{
        val builder = CropBuilder(id)
        OwnCrops.log.info("Read crop from JS: $id")
        OwnCrops.crops[id] = builder
        return builder
    }
}