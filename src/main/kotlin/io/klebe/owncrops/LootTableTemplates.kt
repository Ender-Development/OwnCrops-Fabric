package io.klebe.owncrops

import com.swordglowsblue.artifice.api.resource.StringResource

fun cropLootTable(cropBlock: String, maxAge: Int, seed: String, crop: String): StringResource{
    return StringResource("""
        {
          "type":"minecraft:block",
          "pools":[
            {
              "rolls":1.0,
              "entries":[
                {
                  "type":"minecraft:alternatives",
                  "children":[
                    {
                      "type":"minecraft:item",
                      "conditions":[
                        {
                          "condition":"minecraft:block_state_property",
                          "block":"$cropBlock",
                          "properties":{
                            "age":"$maxAge"
                          }
                        }
                      ],
                      "name":"$crop"
                    },
                    {
                      "type":"minecraft:item",
                      "name":"$seed"
                    }
                  ]
                }
              ]
            },
            {
              "rolls":1.0,
              "entries":[
                {
                  "type":"minecraft:item",
                  "functions":[
                    {
                      "function":"minecraft:apply_bonus",
                      "enchantment":"minecraft:fortune",
                      "formula":"minecraft:binomial_with_bonus_count",
                      "parameters":{
                        "extra":3,
                        "probability":0.5714286
                      }
                    }
                  ],
                  "name":"$seed"
                }
              ],
              "conditions":[
                {
                  "condition":"minecraft:block_state_property",
                  "block":"$cropBlock",
                  "properties":{
                    "age":"$maxAge"
                  }
                }
              ]
            }
          ],
          "functions":[
            {
              "function":"minecraft:explosion_decay"
            }
          ]
        }
        """
    )
}