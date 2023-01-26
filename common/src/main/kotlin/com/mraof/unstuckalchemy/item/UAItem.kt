package com.mraof.unstuckalchemy.item

import net.minecraft.item.Item

/**
 * Created by mraof on 2021 March 20 at 9:44 PM.
 */
class UAItem(settings: Settings?) : Item(settings) {
    // Automatically makes a getter, overrides getBurnTime for forge
    var burnTime = -1;
}
